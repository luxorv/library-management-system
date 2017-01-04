package com.gcit.lms;

import com.gcit.lms.model.*;
import com.gcit.lms.service.BookLoanService;
import com.gcit.lms.service.BookService;
import com.gcit.lms.service.BorrowerService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/borrowers")
public class BorrowerController {

    @Autowired
    BorrowerService borrowerService;

    @Autowired
    BookLoanService loanService;

    @Autowired
    BookService bookService;

    private boolean hasOperated;

    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String query(@RequestParam("q") String query, @RequestParam("pageNo") Integer pageNo) {

        query = query.trim();

        ArrayList<Borrower> borrowers = borrowerService.getAllBorrowers(query, pageNo);
        ArrayList<JSONObject> formattedBorrowers = borrowerService.getBorrowersInfo(borrowers);

        JSONObject response = new JSONObject();

        response.put("borrowers", formattedBorrowers);
        response.put("pageNo", pageNo);
        response.put("fetchSize", borrowerService.getBorrowerFetchSize());

        if(borrowerService.getAlert() != null) {
            response.put("alert", borrowerService.getAlert());
        }

        if(this.hasOperated) {
            bookService.setAlert("");
            this.hasOperated = false;
        }

        return response.toString();
    }

    @RequestMapping(value = "/queryloans", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String queryLoans(@RequestParam("q") String query,
                             @RequestParam("q1") String query1,
                             @RequestParam("branchId") Integer branchId,
                             @RequestParam("cardNo") Integer cardNo,
                             @RequestParam("pageNo") Integer pageNo,
                             @RequestParam("pageNo1") Integer pageNo1) {

        query = query.trim();
        query1 = query1.trim();

        Borrower borrower = new Borrower();
        borrower.setCardNo(cardNo);

        LibraryBranch branch = new LibraryBranch();
        branch.setBranchId(branchId);

        ArrayList<Book> books = bookService.getPossibleLoansFromBorrower(borrower, branch, query, pageNo);
        ArrayList<BookLoan> loans = loanService.getLoansFromBorrowerInBranch(borrower, branch, query1, pageNo1);
        ArrayList<JSONObject> bookInfo = bookService.getBooksInfo(books);
        ArrayList<JSONObject> loanInfo = loanService.getLoanInfo(loans);

        JSONObject result = new JSONObject();

        if(!borrowerService.validateBorrower(borrower)) {

            String alert = "<div class='alert alert-danger alert-dismissible' role='alert'>" +
                    "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                    "<span aria-hidden='true'>&times;</span></button>" +
                    "The card number "+ cardNo +" is not valid</div>";

            result.put("cardNotValidAlert", alert);
        } else {
            borrower = borrowerService.getBorrower(borrower);

            String alert = "<div class='alert alert-success alert-dismissible' role='alert'>" +
                    "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                    "<span aria-hidden='true'>&times;</span></button>" +
                    "Welcome "+ borrower.getName() +" let's loan some books today! </div>";

            result.put("cardOkAlert", alert);
        }

        result.put("books", bookInfo);
        result.put("loans", loanInfo);
        result.put("cardNo", cardNo);
        result.put("branchId", branchId);

        return result.toString();
    }

    @RequestMapping(value = "/loan", method = RequestMethod.GET)
    @ResponseBody
    public String loanBook(@RequestParam("bookId") Integer bookId,
                           @RequestParam("cardNo") Integer cardNo,
                           @RequestParam("branchId") Integer branchId) {

        BookLoan loan = this.fillBookLoan(
                cardNo,
                bookId,
                branchId
        );

        loanService.loanBook(loan);

        loan = loanService.getLoan(loan);

        String alert = "<div class='alert alert-success alert-dismissible' role='alert'>" +
                "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                "<span aria-hidden='true'>&times;</span></button>" +
                "Great "+ loan.getBook().getTitle() +" has been loaned with due date "+ loan.getDueDate().toString() +" </div>";

        return alert;
    }

    @RequestMapping(value = "/return", method = RequestMethod.GET)
    @ResponseBody
    public String returnBook(@RequestParam("bookId") Integer bookId,
                           @RequestParam("cardNo") Integer cardNo,
                           @RequestParam("branchId") Integer branchId) {

        BookLoan loan = this.fillBookLoan(
                cardNo,
                bookId,
                branchId
        );

        loan = loanService.getLoan(loan);

        loanService.returnBook(loan);

        String alert = "<div class='alert alert-success alert-dismissible' role='alert'>" +
                "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                "<span aria-hidden='true'>&times;</span></button>" +
                "Great "+ loan.getBook().getTitle() +" has been returned! </div>";

        return alert;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response) {
        return "borrowers/list";
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView detail(@RequestParam("cardNo") Integer cardNo) {

        Map<String, Object> result = new HashMap<>();
        Borrower borrower = new Borrower();

        borrower.setCardNo(cardNo);
        borrower = borrowerService.getBorrower(borrower);

        if(cardNo > 0) {
            ArrayList<BookLoan> loans = loanService.getLoansFromBorrower(borrower);

            result.put("loans", loans);
        }

        result.put("borrower", borrower);

        return new ModelAndView("borrowers/view", result) ;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RedirectView save(@RequestParam("borrowerName") String borrowerName,
                             @RequestParam("borrowerAddress") String borrowerAddress,
                             @RequestParam("borrowerPhone") String borrowerPhone,
                             @RequestParam("cardNo") Integer cardNo) {

        Borrower borrower = this.fillBorrower(
                borrowerName,
                borrowerAddress,
                borrowerPhone,
                cardNo
        );

        borrowerService.createBorrower(borrower);
        borrowerService.setAlert("Good");

        this.hasOperated = true;

        return new RedirectView("/lms/borrowers");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RedirectView update(@RequestParam("borrowerName") String borrowerName,
                               @RequestParam("borrowerAddress") String borrowerAddress,
                               @RequestParam("borrowerPhone") String borrowerPhone,
                               @RequestParam("cardNo") Integer cardNo) {

        Borrower borrower = this.fillBorrower(
                borrowerName,
                borrowerAddress,
                borrowerPhone,
                cardNo
        );

        borrowerService.updateBorrower(borrower);
        borrowerService.setAlert("Good");

        this.hasOperated = true;

        return new RedirectView("/lms/borrowers");
    }

    @RequestMapping(value = "/updateloan", method = RequestMethod.GET)
    @ResponseBody
    public String updateLoan(@RequestParam("cardNo") Integer cardNo,
                             @RequestParam("bookId") Integer bookId,
                             @RequestParam("branchId") Integer branchId,
                             @RequestParam("dueDate") String updatedDate) {

        BookLoan loan = this.fillBookLoan(
                cardNo,
                bookId,
                branchId
        );

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new Date(System.currentTimeMillis());

        try {
            date = format.parse(updatedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        java.sql.Date dueDate = new Date(date.getTime());

        loan.setDueDate(dueDate);

        loanService.updateLoan(loan);

        String alert = "<div class='alert alert-success alert-dismissible' role='alert'>" +
                "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                "<span aria-hidden='true'>&times;</span></button>" +
                "Great! loan's due date updated to "+loan.getDueDate().toString()+" </div>";

        return alert;
    }

    private BookLoan fillBookLoan(Integer cardNo, Integer bookId, Integer branchId) {
        BookLoan loan = new BookLoan();

        LibraryBranch branch = new LibraryBranch();
        Book book = new Book();
        Borrower borrower = new Borrower();

        branch.setBranchId(branchId);
        book.setBookId(bookId);
        borrower.setCardNo(cardNo);

        loan.setBook(book);
        loan.setBranch(branch);
        loan.setBorrower(borrower);

        return loan;
    }

    private Borrower fillBorrower(String borrowerName,
                                    String borrowerAddress,
                                    String borrowerPhone,
                                    Integer cardNo) {

        Borrower borrower = new Borrower();

        if(cardNo != null && cardNo > 0) {
            borrower.setCardNo(cardNo);
        }

        borrower.setName(borrowerName);
        borrower.setAddress(borrowerAddress);
        borrower.setPhone(borrowerPhone);

        return borrower;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public RedirectView delete(@RequestParam("cardNo") Integer cardNo) {

        Borrower borrower = new Borrower();
        borrower.setCardNo(cardNo);

        borrowerService.setAlert("Good");
        borrowerService.deleteBorrower(borrower);

        this.hasOperated = true;

        return new RedirectView("/lms/borrowers");
    }
}

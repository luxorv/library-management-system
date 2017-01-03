package com.gcit.lms;

import com.gcit.lms.model.Book;
import com.gcit.lms.model.BookCopies;
import com.gcit.lms.model.LibraryBranch;
import com.gcit.lms.service.BookCopiesService;
import com.gcit.lms.service.BookService;
import com.gcit.lms.service.BranchService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

    @Autowired
    BookCopiesService copiesService;

    @Autowired
    BranchService branchService;

    @Autowired
    BookService bookService;

    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String query(@RequestParam("q") String query,
                        @RequestParam("branchId") Integer branchId,
                        @RequestParam("pageNo") Integer pageNo) {

        BookCopies copy = new BookCopies();
        LibraryBranch branch = new LibraryBranch();

        branch.setBranchId(branchId);
        copy.setBranch(branch);

        ArrayList<BookCopies> copies = copiesService.getAllCopiesFromBranch(copy, query, pageNo);
        ArrayList<JSONObject> formattedCopies = copiesService.getCopiesInfo(copies);

        JSONObject response = new JSONObject();

        response.put("copies", formattedCopies);
        response.put("pageNo", pageNo);
        response.put("fetchSize", copiesService.getCopiesFetchSize());

        if(copiesService.getAlert() != null) {
            response.put("alert", copiesService.getAlert());
        }

        return response.toString();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView librarian(Locale locale, Model model) {

        Map<String, ArrayList<LibraryBranch>> result = new HashMap<>();

        result.put("branches", branchService.getAllBranches("", 0));

        return new ModelAndView("librarian", result);
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view(@RequestParam("branchId") Integer branchId,
                             @RequestParam("bookId") Integer bookId) {

        Map<String, Object> result = new HashMap<>();
        BookCopies copy = new BookCopies();

        if(bookId > 0 && branchId > 0) {
            copy = this.fillCopies(branchId, bookId, 0);
            copy = copiesService.getBookCopies(copy);
        } else {
            LibraryBranch branch = new LibraryBranch();
            branch.setBranchId(branchId);

            copy.setBranch(branch);
            copy.setNoOfCopies(0);

            ArrayList<Book> books = bookService.getBooksNotInBranch(branch);
            result.put("books", books);
        }

        result.put("copy", copy);

        return new ModelAndView("copydetail", result);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RedirectView create(@RequestParam("branchId") Integer branchId,
                               @RequestParam("bookId") Integer bookId,
                               @RequestParam("noOfCopies") Integer noOfCopies) {

        BookCopies copy = this.fillCopies(bookId, branchId, noOfCopies);

        copiesService.createCopies(copy);

        copiesService.setAlert("Good");

        return new RedirectView("/lms/librarian");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RedirectView update(@RequestParam("branchId") Integer branchId,
                               @RequestParam("bookId") Integer bookId,
                               @RequestParam("noOfCopies") Integer noOfCopies) {

        BookCopies copy = this.fillCopies(bookId, branchId, noOfCopies);

        copiesService.updateCopies(copy);

        copiesService.setAlert("Good");

        return new RedirectView("/lms/librarian");
    }

    private BookCopies fillCopies(Integer bookId, Integer branchId, Integer noOfCopies) {
        BookCopies copy = new BookCopies();

        Book book = new Book();
        book.setBookId(bookId);

        LibraryBranch branch = new LibraryBranch();
        branch.setBranchId(branchId);

        copy.setBook(book);
        copy.setBranch(branch);

        copy.setNoOfCopies(noOfCopies);

        return copy;
    }
}

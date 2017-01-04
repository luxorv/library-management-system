package com.gcit.lms;

import com.gcit.lms.model.Author;
import com.gcit.lms.model.Book;
import com.gcit.lms.service.AuthorService;
import com.gcit.lms.service.BookService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @Autowired
    BookService bookService;

    private boolean hasOperated;

    @RequestMapping(value="/query", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String query(@RequestParam("q") String query, @RequestParam("pageNo") Integer pageNo) {

        query = query.trim();

        ArrayList<Author> authors = authorService.getAllAuthors(query, pageNo);
        ArrayList<JSONObject> formattedAuthors = authorService.getAuthorsInfo(authors);

        JSONObject response = new JSONObject();

        response.put("authors", formattedAuthors);
        response.put("pageNo", pageNo);
        response.put("fetchSize", authorService.getAuthorFetchSize());

        if(authorService.getAlert() != null) {
            response.put("alert", authorService.getAlert());
        }

        if(this.hasOperated) {
            authorService.setAlert("");
            this.hasOperated = false;
        }

        return response.toString();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response) {
        return "authors/list";
    }

    @RequestMapping(value="/view", method=RequestMethod.GET)
    public ModelAndView view(@RequestParam("authorId") Integer authorId) {

        Map<String, Object> result = new HashMap<>();
        ArrayList<Book> books = bookService.getBooksBy("", "Title", 0);
        Author author = new Author();

        author.setAuthorId(authorId);
        author = authorService.getAuthor(author);

        result.put("books", books);
        result.put("author", author);

        return new ModelAndView("authors/view", result);
    }

    @RequestMapping(value = "/create", method=RequestMethod.POST)
    public RedirectView create(@RequestParam("authorName") String authorName, Integer authorId,
                               @RequestParam("books") String[] books) {

        Author author = this.fillAuthor(authorName, authorId, books);

        authorService.createAuthor(author);

        String alert = "<div class='alert alert-success alert-dismissible' role='alert'>" +
                "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                "<span aria-hidden='true'>&times;</span></button>" +
                "Great! Author "+ authorName +" was added! </div>";

        authorService.setAlert(alert);
        this.hasOperated = true;

        return new RedirectView("/lms/authors");
    }

    @RequestMapping(value = "/update", method=RequestMethod.POST)
    public RedirectView update(@RequestParam("authorName") String authorName, Integer authorId,
                               @RequestParam("books") String[] books) {

        Author author = this.fillAuthor(authorName, authorId, books);

        authorService.updateAuthor(author);

        String alert = "<div class='alert alert-success alert-dismissible' role='alert'>" +
                "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                "<span aria-hidden='true'>&times;</span></button>" +
                "Great! Author "+ authorName +" was updated! </div>";

        this.hasOperated = true;

        authorService.setAlert(alert);

        return new RedirectView("/lms/authors");
    }

    private Author fillAuthor(String authorName, Integer authorId, String[] books) {
        Author author = new Author();

        author.setAuthorId(authorId);
        author.setAuthorName(authorName);

        ArrayList<Book> books1 = new ArrayList<>();
        for(String bookId: books) {
            Book book = new Book();
            book.setBookId(Integer.parseInt(bookId));

            books1.add(book);
        }

        author.setBooks(books1);

        return author;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public RedirectView delete(@RequestParam("authorId") Integer authorId) {
        Author author = new Author();
        author.setAuthorId(authorId);

        String authorName = authorService.getAuthor(author).getAuthorName();

        authorService.deleteAuthor(author);

        String alert = "<div class='alert alert-success alert-dismissible' role='alert'>" +
                "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                "<span aria-hidden='true'>&times;</span></button>" +
                "Great! Author "+ authorName +" was added! </div>";

        authorService.setAlert(alert);
        this.hasOperated = true;

        return new RedirectView("/lms/authors");
    }
}

package com.gcit.lms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.gcit.lms.model.*;
import com.gcit.lms.service.*;
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

@Controller
@RequestMapping("/books")
public class BookController {

	@Autowired
	BookService bookService;

	@Autowired
	AuthorService authorService;

	@Autowired
	PublisherService publisherService;

	@Autowired
	GenreService genreService;

	@Autowired
	BookLoanService loanService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value= "/query", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public String query(@RequestParam("q") String query, @RequestParam("searchBy") String searchBy,
						@RequestParam("pageNo") Integer pageNo) {

		ArrayList<Book> books = bookService.getBooksBy(query, searchBy, pageNo);
		ArrayList<JSONObject> formattedBooks = bookService.getBooksInfo(books);
		
		JSONObject response = new JSONObject();
		
		response.put("books", formattedBooks);
		response.put("pageNo", pageNo);
		response.put("fetchSize", bookService.getBookFetchSize());

		if(bookService.getAlert() != null) {
			response.put("alert", bookService.getAlert());
		}

		return response.toString();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(HttpServletRequest request, HttpServletResponse response) {

		if(request.getParameter("redirected") == null) {
			bookService.setAlert("");
		}

		return "books/list";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam("bookId") Integer bookId) {

		Map<String, Object> result = new HashMap<>();

		Book book = new Book();
		ArrayList<Author> authors = authorService.getAllAuthors("", 0);
		ArrayList<Publisher> publishers = publisherService.getAllPublishers("", 0);
		ArrayList<Genre> genres = genreService.getAllGenres("");

		book.setBookId(bookId);

		book = bookService.getBook(book);

		result.put("book", book);
		result.put("authors", authors);
		result.put("genres", genres);
		result.put("publishers", publishers);

		return new ModelAndView("books/view", result) ;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public RedirectView save(@RequestParam("title") String title, @RequestParam("bookId") Integer bookId, @RequestParam("authors") String[] authors,
							   @RequestParam("genres") String[] genres, @RequestParam("publisherId") Integer publisherId) {

		Book book = this.fillBook(title, bookId, authors, genres, publisherId);

		bookService.createBook(book);
		bookService.setAlert("Good");

		return new RedirectView("/lms/books?redirected=true");
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RedirectView update(@RequestParam("title") String title, @RequestParam("bookId") Integer bookId, @RequestParam("authors") String[] authors,
			@RequestParam("genres") String[] genres, @RequestParam("publisherId") Integer publisherId) {

		Book book = this.fillBook(title, bookId, authors, genres, publisherId);

		bookService.updateBook(book);
		bookService.setAlert("Good");

		return new RedirectView("/lms/books?redirected=true");
	}

	private Book fillBook(String title, Integer bookId, String[] authors, String[] genres, Integer publisherId) {
		Book book = new Book();

		book.setTitle(title);
		book.setBookId(bookId);

		ArrayList<Author> authors1 = new ArrayList<>();
		for (String authorId: authors) {
			Author author = new Author();

			author.setAuthorId(Integer.parseInt(authorId));
			authors1.add(author);
		}

		book.setAuthors(authors1);

		ArrayList<Genre> genres1 = new ArrayList<>();
		for (String genreId: genres) {
			Genre genre = new Genre();

			genre.setGenreId(Integer.parseInt(genreId));
			genres1.add(genre);
		}

		book.setGenres(genres1);

		Publisher publisher = new Publisher();
		publisher.setPublisherId(publisherId);

		book.setPublisher(publisher);

		return book;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public RedirectView delete(@RequestParam("bookId") Integer bookId) {

		Book book = new Book();
		book.setBookId(bookId);

		ArrayList<BookLoan> loans = loanService.getLoansFromBook(book);

		if(loans != null && loans.size() != 0) {
			bookService.setAlert("Bad");
		} else {
			bookService.setAlert("Good");
			bookService.deleteBook(book);
		}

		return new RedirectView("/lms/books?redirected=true");
	}
}

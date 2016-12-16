package com.gcit.library.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.gcit.library.dao.AuthorDAO;
import com.gcit.library.dao.BaseDAO;
import com.gcit.library.dao.BookCopiesDAO;
import com.gcit.library.dao.BookDAO;
import com.gcit.library.dao.BookLoanDAO;
import com.gcit.library.dao.BorrowerDAO;
import com.gcit.library.dao.GenreDAO;
import com.gcit.library.dao.LibraryBranchDAO;
import com.gcit.library.dao.PublisherDAO;
import com.gcit.library.dao.QueryHelper;
import com.gcit.library.model.Author;
import com.gcit.library.model.Book;
import com.gcit.library.model.BookCopies;
import com.gcit.library.model.BookLoan;
import com.gcit.library.model.Borrower;
import com.gcit.library.model.DatabaseManager;
import com.gcit.library.model.Genre;
import com.gcit.library.model.LibraryBranch;
import com.gcit.library.model.Publisher;

public class Application {
	private static Scanner scanner;

	public static void main(String[] args) {
		try {
			BaseDAO.setConnection(DatabaseManager.getInstance().getCurrentConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void testReduceCopies() {
		LibraryBranchDAO branchDAO = new LibraryBranchDAO();
		BookDAO bookDAO = new BookDAO();
		BookCopiesDAO copiesDAO = new BookCopiesDAO();
		
		Book book = (Book) bookDAO.getAll(QueryHelper.BOOK_WITH_ID, false, new Object[]{9}).get(0);
		LibraryBranch branch = (LibraryBranch) branchDAO.getAll(QueryHelper.BRANCH_WITH_ID, false, new Object[]{2}).get(0);
		
		ArrayList<BookCopies> copies = copiesDAO.getAll(
			QueryHelper.ALL_COPIES_OF_BOOK_FROM_BRANCH,
			false,
			new Object[]{book.getBookId(), branch.getBranchId()}
		);
		
		for (BookCopies copy : copies) {
			System.out.println(copy.getBook());
			System.out.println(copy.getBranch());
			System.out.println(copy.getNoOfCopies());
		}
		
		copies.get(0).setNoOfCopies(copies.get(0).getNoOfCopies() - 5);
		
		copiesDAO.update(copies.get(0));
		
		for (BookCopies copy : copies) {
			System.out.println(copy.getBook());
			System.out.println(copy.getBranch());
			System.out.println(copy.getNoOfCopies());
		}
	}
	
	public static void testBookLoan() {
		
		LibraryBranchDAO branchDAO = new LibraryBranchDAO();
		BookDAO bookDAO = new BookDAO();
		BorrowerDAO borrowerDAO = new BorrowerDAO();
		BookLoanDAO bookLoanDAO = new BookLoanDAO();
		
		ArrayList<Borrower> borrowers = borrowerDAO.getAll(QueryHelper.ALL_BORROWERS, false, null);
		ArrayList<LibraryBranch> branches = branchDAO.getAll(QueryHelper.ALL_BRANCHES, false, null);
		
		ArrayList<Book> books = bookDAO.getAll(
			QueryHelper.ALL_BOOKS,
			false,
			null
		);
		
		for (Borrower borrower : borrowers) {
			System.out.println(borrower.toString());
		}
		
		System.out.println();
		
		for (LibraryBranch branch : branches) {
			System.out.println(branch.toString());
		}
		
		System.out.println();
		
		for (Book book : books) {
			System.out.println(book.toString());
		}
		
		BookLoan loan = new BookLoan();
		
		loan.setDateOut(BookLoan.dateFromNow(0));
		loan.setDueDate(BookLoan.dateFromNow(7));
		loan.setBook(books.get(6));
		loan.setBranch(branches.get(2));
		loan.setBorrower(borrowers.get(2));
		
		bookLoanDAO.save(loan);
		
		BookLoan newLoan = (BookLoan) bookLoanDAO.getAll(
			QueryHelper.LOAN_WITH_ID,
			true,
			new Object[]{loan.getBook().getBookId(), loan.getBranch().getBranchId(), loan.getBorrower().getCardNo()}
		).get(0);
		
		System.out.println(newLoan.toString());
		
		bookLoanDAO.delete(newLoan);
	}

	public void testAddABook() {

		BookDAO bookDAO = new BookDAO();
		AuthorDAO authorDAO = new AuthorDAO();
		PublisherDAO pubDAO = new PublisherDAO();
		GenreDAO genreDAO = new GenreDAO();
		scanner = new Scanner(System.in);
		Book book = new Book();
		
		ArrayList<Author> authors = authorDAO.getAll(QueryHelper.ALL_AUTHORS, false, null);
		
		for (int i = 0; i < authors.size(); i++) {
			System.out.println(authors.get(i).toString());
		}
		
		int n = 2;
		
		ArrayList<Author> newAuthors = new ArrayList<Author>();
		
		for (int i = 0; i < n; i++) {
			int authorId = scanner.nextInt();
			
			newAuthors.add(authors.get(authorId));
		}
		
		ArrayList<Publisher> publishers = pubDAO.getAll(QueryHelper.ALL_PUBLISHERS, false, null);
		
		for (int i = 0; i < publishers.size(); i++) {
			System.out.println(publishers.get(i).toString());
		}
		
		int publisherId = scanner.nextInt();
		
		ArrayList<Genre> genres = genreDAO.getAll(QueryHelper.ALL_GENRES, false, null);
		
		for (int i = 0; i < genres.size(); i++) {
			System.out.println(genres.get(i).toString());
		}
		
		ArrayList<Genre> newGenres = new ArrayList<Genre>();
		
		for (int i = 0; i < n; i++) {
			int genreId = scanner.nextInt();
			
			newGenres.add(genres.get(genreId));
		}
		
		System.out.println("Title: ");
		
		scanner.nextLine();
		String title = scanner.nextLine();
		
		book.setTitle(title);
		book.setAuthors(newAuthors);
		book.setGenres(newGenres);
		book.setPublisher(publishers.get(publisherId));
		
		int bookId = bookDAO.save(book);
		
		book.setBookId(bookId);
		
		bookDAO.relateBook(book);
		
		System.out.println(bookDAO.getAll(QueryHelper.BOOK_WITH_ID, true, new Object[]{bookId}).get(0));
		
		System.out.println(book.getPublisher().toString());
		authors = book.getAuthors();
		
		for (int i = 0; i < authors.size(); i++) {
			System.out.println(authors.get(i).toString());
		}
		
		genres = book.getGenres();
		
		for (int i = 0; i < genres.size(); i++) {
			System.out.println(genres.get(i).toString());
		}
	}
}

package com.gcit.library.dao;

public class QueryHelper {

	/* 
	 * Class for query constants: 
	 * 
	 * This class contains constants for all models and DAOs on the Application.
	 * 
	 *  
	 * 1) Books:
	 * 		- SELECT ALL
	 * 		- SELECT SPECIFIC
	 *  	- INSERT
	 *  	- UPDATE
	 * 		- DELETE
	 * 
	 */
	
	// Get all books.
	public final static String ALL_BOOKS = "SELECT * " +
			 				         "FROM tbl_book b";

	// Get book with certain ID.
	public final static String BOOK_WITH_ID = ALL_BOOKS + " WHERE b.bookId = ?";

	// Get books from a genre.
	public final static String ALL_BOOKS_FROM_AUTHOR = ALL_BOOKS + " WHERE b.bookId IN (" +
														   " SELECT ba.bookId " +
														   " FROM tbl_book_genres ba" +
														   " WHERE bg.authorId = ?)";
	
	// Get books from a genre.
	public final static String ALL_BOOKS_FROM_GENRE = ALL_BOOKS + " WHERE b.bookId IN (" +
														   " SELECT bg.bookId " +
														   " FROM tbl_book_genres bg" +
														   " WHERE bg.genre_id = ?)";

	// Get all books from a branch.
	public final static String ALL_BOOKS_FROM_BRANCH = "SELECT * " +
			   "FROM tbl_book " +
			   "INNER JOIN tbl_book_copies " +
			   "ON tbl_book_copies.bookId = tbl_book.bookId " +
			   "INNER JOIN tbl_library_branch " +
			   "ON tbl_library_branch.branchId = tbl_book_copies.branchId " +
			   "WHERE tbl_book_copies.noOfCopies > 0 AND tbl_library_branch.branchId = ?";

	// Get books loaned from a branch.
	public final static String ALL_BOOKS_LOANED_FROM_BRANCH = "SELECT * " +
			   "FROM tbl_book_loans " +
			   "INNER JOIN tbl_book " +
			   "ON tbl_book.bookId = tbl_book_loans.bookId " +
			   "WHERE tbl_book_loans.cardNo = ? AND tbl_book_loans.branchId = ? AND tbl_book_loans.dateIn IS NULL";

	// Get books not loaned from a branch.
	public final static String ALL_BOOKS_NOT_LOANED_FROM_BRANCH = "SELECT tbl_book.title, tbl_book.bookId, tbl_library_branch.branchId " +
			   "FROM tbl_book " +
			   "INNER JOIN tbl_book_copies " +
			   "ON tbl_book_copies.bookId = tbl_book.bookId " +
			   "INNER JOIN tbl_library_branch " +
			   "ON tbl_library_branch.branchId = tbl_book_copies.branchId " +
			   "WHERE tbl_book_copies.noOfCopies > 0 AND tbl_library_branch.branchId = ? AND tbl_book.bookId NOT IN ( " +
			   "SELECT bl.bookId " +
			   "FROM tbl_book_loans bl " +
			   "WHERE bl.cardNo = ?)";
	
	// Get books from a publisher.
	public final static String ALL_BOOKS_FROM_PUBLISHER = "SELECT * " +
														  "FROM tbl_book b" +
														  "WHERE b.pubId = ?";

	// Insert a book.
	public final static String INSERT_BOOK = "INSERT INTO tbl_book " +
					                   "(title, pubId) " +
									   "VALUES(?, ?)";

	// Update a book.
	public final static String UPDATE_BOOK = "UPDATE tbl_book b " +
	        							"SET b.title = ? " +
	        							"WHERE b.bookId = ?";

	// Delete a book.
	public final static String DELETE_BOOK = "DELETE FROM tbl_book b " +
						               "WHERE b.bookId = ?";
	
	// Relate book with author.
	public final static String RELATE_BOOK_WITH_AUTHOR = "INSERT INTO tbl_book_authors " +
														 "(bookId, authorId) " +
														 "VALUES(?, ?)";
	
	// Relate book with author.
	public final static String RELATE_BOOK_WITH_GENRE = "INSERT INTO tbl_book_genres " +
														 "(bookId, genre_id) " +
														 "VALUES(?, ?)";
	
	/*		
	 * 2) Authors:
	 * 		- SELECT ALL
	 * 		- SELECT SPECIFIC
	 * 		- SELECT AUTHORS FROM BOOK
	 *  	- INSERT
	 *  	- UPDATE
	 * 		- DELETE
	 * 
	 */
	
	// Getting all authors.
	public final static String ALL_AUTHORS = "SELECT * " +
									   "FROM tbl_author a";
	
	// Getting an author with certain ID.
	public final static String AUTHOR_WITH_ID = ALL_AUTHORS + " WHERE a.authorId = ?";
	
	// Getting authors from a book.
	public final static String ALL_AUTHORS_FROM_BOOK = ALL_AUTHORS + " WHERE a.authorId IN (" +
														   " SELECT ba.authorId " +
														   " FROM tbl_book_authors ba" +
														   " WHERE ba.bookId = ?)";
	
	// Insert an author.
	public final static String INSERT_AUTHOR = "INSERT INTO tbl_author " +
						                 "(authorName) " +
										 "VALUES(?)";
	
	// Delete an author.
	public final static String DELETE_AUTHOR = "DELETE FROM tbl_author a " +
						                 "WHERE a.authorId = ?";
	
	// Update an author.
	public final static String UPDATE_AUTHOR = "UPDATE tbl_author a " +
	        							 "SET a.authorName = ? " +
	        							 "WHERE a.authorId = ?";
	/*
	 * 3) Genres:
	 * 		- SELECT ALL
	 * 		- SELECT SPECIFIC
	 * 		- SELECT GENRES FROM BOOK
	 *  	- INSERT
	 *  	- UPDATE
	 * 		- DELETE
	 */
	
	// Get all genres.
	public final static String ALL_GENRES = "SELECT * " +
									  "FROM tbl_genre g";
	
	// Get an genre with certain ID.
	public final static String GENRE_WITH_ID = ALL_GENRES + " WHERE g.genre_id = ?";
	
	// Get genres from a book.
	public final static String ALL_GENRES_FROM_BOOK = ALL_GENRES + " WHERE g.genre_id IN (" +
														   " SELECT bg.genre_id " +
														   " FROM tbl_book_genres bg" +
														   " WHERE bg.bookId = ?)";
	
	// Insert a genre.
	public static String INSERT_GENRE = "INSERT INTO tbl_genre " +
						                "(genre_name) " +
										"VALUES(?)";
	
	// Delete a genre.
	public static String DELETE_GENRE = "DELETE FROM tbl_genre g " +
						                 "WHERE g.genre_id = ?";
	
	// Update a genre.
	public static String UPDATE_GENRE = "UPDATE tbl_genre g " +
	        							 "SET g.genre_name = ? " +
	        							 "WHERE g.genre_id = ?";
	
	/*
	 * 4) Publisher:
	 * 		- SELECT ALL
	 * 		- SELECT SPECIFIC
	 * 		- SELECT PUBLISHERS FROM BOOK
	 *  	- INSERT
	 *  	- UPDATE
	 * 		- DELETE
	 */
	
	// Get all publishers.
	public final static String ALL_PUBLISHERS = "SELECT * " +
			   "FROM tbl_publisher";

	// Get a publisher with certain ID.
	public final static String PUBLISHER_WITH_ID = "SELECT * " +
			   "FROM tbl_publisher " +
			   "WHERE publisherId = ?";

	// Insert a publisher.
	public final static String INSERT_PUBLISHER = "INSERT INTO tbl_publisher " +
			   "(publisherName, publisherAddress, publisherPhone) " +
		       "VALUES(?, ?, ?)";

	// Delete a publisher.
	public final static String DELETE_PUBLISHER = "DELETE FROM tbl_publisher "+
			   "WHERE publisherId = ?";

	// Update a publisher.
	public final static String UPDATE_PUBLISHER = "UPDATE tbl_publisher "+
			   "SET publisherName = ?, publisherAddress = ?, publisherPhone = ? " +
			   "WHERE publisherId = ?";

	/*
	 * 5) Borrowers:
	 * 		- SELECT ALL
	 * 		- SELECT SPECIFIC
	 *  	- INSERT
	 *  	- UPDATE
	 * 		- DELETE
	 */

	public final static String ALL_BORROWERS = "SELECT * " +
			   "FROM tbl_borrower";
	
	public final static String BORROWER_WITH_CARDNO = "SELECT * " +
			"FROM tbl_borrower " +
			"WHERE cardNo = ?";
	
	public final static String UPDATE_BORROWER = "UPDATE tbl_borrower "+
			   "SET name = ?, address = ?, phone = ? " +
			   "WHERE cardNo = ?";
	
	public final static String DELETE_BORROWER = "DELETE FROM tbl_borrower "+
			   "WHERE cardNo = ?";

	public final static String INSERT_BORROWER = "INSERT INTO tbl_borrower " +
			   "(name, address, phone) " +
			   "VALUES(?, ?, ?)";
	
	/*
	 * 6) Library Branch:
	 * 		- SELECT ALL
	 * 		- SELECT SPECIFIC
	 * 		- SELECT AUTHORS FROM BOOK
	 *  	- INSERT
	 *  	- UPDATE
	 * 		- DELETE
	 */
	
	// Get all branches.
	public final static String ALL_BRANCHES = "SELECT * " +
			"FROM tbl_library_branch";

	// Get branch with ID.
	public final static String BRANCH_WITH_ID = ALL_BRANCHES + " WHERE branchId = ?";
	
	// Insert branch.
	public final static String INSERT_BRANCH = "INSERT INTO tbl_library_branch " +
											   "(branchName, branchAddress) " +
											   "VALUES(?, ?)";
	
	// Update branch.
	public final static String UPDATE_BRANCH = "UPDATE tbl_library_branch " +
			   "SET branchName = ?, branchAddress = ? " +
			   "WHERE branchId = ?";
	
	// Delete branch.
	public final static String DELETE_BRANCH = "DELETE FROM tbl_library_branch "+
			   "WHERE branchId = ?";
	

	
	/*
	 * 7) Book Loans:
	 * 		- SELECT ALL
	 * 		- SELECT SPECIFIC
	 * 		- SELECT AUTHORS FROM BOOK
	 *  	- INSERT
	 *  	- UPDATE
	 * 		- DELETE
	 */

	
	public final static String ALL_LOANS_FROM_BORROWER = "SELECT * " +
			   											 "FROM tbl_book_loans " +
			   											 "WHERE cardNo = ?";
	
	public final static String UPDATE_LOAN = "UPDATE tbl_book_loans " +
											 "SET dueDate = ? " +
											 "WHERE bookId = ? AND branchId = ? AND cardNo = ?";
	
	public final static String UPDATE_LOANS_FROM_BORROWER_IN_BRANCH = "UPDATE tbl_book_loans "+
			   														  "SET bookId = ?, branchId = ?, cardNo = ?,"+
			   														  "dueDate = ?, dateOut = ? " +
			   														  "WHERE bookId = ? AND branchId = ? AND cardNo = ?";

	public final static String DELETE_LOAN = "DELETE FROM tbl_book_loans "+
			   								 "WHERE bookId = ? AND branchId = ? AND cardNo = ?";

	public final static String INSERT_LOAN = "INSERT INTO tbl_book_loans " +
			   "(bookId, branchId, cardNo, dueDate, dateOut) " +
			   "VALUES (?, ?, ?, ?, ?)";
	
	public final static String LOAN_WITH_ID = "SELECT * " +
			   "FROM tbl_book_loans " +
			   "WHERE bookId = ? AND branchId = ? AND cardNo = ?";


	/*
	 * 8) Book Copies:
	 * 		- SELECT ALL COPIES FROM BRANCH
	 *  	- INSERT
	 *  	- UPDATE
	 */
	

	// Get all copies of a book from a branch.
	public final static String ALL_COPIES_OF_BOOK_FROM_BRANCH = "SELECT * " +
			   								"FROM tbl_book_copies " +
			   								"WHERE bookId = ? AND branchId = ?";

	// Insert into copies.
	public final static String INSERT_COPIES = "INSERT INTO tbl_book_copies " +
			   "VALUES(?, ?, ?) ";

	// Update copies' noOfCopies.
	public final static String UPDATE_COPIES = "UPDATE tbl_book_copies " +
		   	   								   "SET noOfCopies = ? " +
		   	   								   "WHERE bookId = ? AND branchId = ?";
}

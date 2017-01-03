package com.gcit.lms.dao;

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
			 				         "FROM tbl_book b ";

	// Get book with certain ID.
	public final static String BOOK_WITH_ID = ALL_BOOKS + " WHERE b.bookId = ?";

	// Get books from a genre.
	public final static String ALL_BOOKS_FROM_AUTHOR = ALL_BOOKS + " WHERE b.bookId IN (" +
														   " SELECT ba.bookId " +
														   " FROM tbl_book_authors ba " +
														   " WHERE ba.authorId = ?)";
	
	// Get books from a genre.
	public final static String ALL_BOOKS_FROM_GENRE = ALL_BOOKS + " WHERE b.bookId IN (" +
														   " SELECT bg.bookId " +
														   " FROM tbl_book_genres bg " +
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
			   "WHERE tbl_book_loans.branchId = ? AND tbl_book_loans.cardNo = ?";

	public final static String ALL_BOOKS_NOT_IN_BRANCH = "SELECT * " +
														 "FROM tbl_book b " +
														 "WHERE b.bookId NOT IN (" +
														 "SELECT bc.bookId " +
														 "FROM tbl_book_copies bc " +
														 "WHERE bc.branchId = ?)";
	
	// Get query on books.
	public static String ALL_BOOKS_BY_TITLE = "SELECT * " +
										  	  "FROM tbl_book " +
										  	  "WHERE title LIKE ?";
	
	// Get query on books.
	public static String ALL_BOOKS_BY_AUTHOR = "SELECT * " +
											   "FROM tbl_book b " +
											   "INNER JOIN tbl_book_authors ba " +
											   "ON b.bookId = ba.bookId " +
											   "INNER JOIN tbl_author a " +
											   "ON a.authorId = ba.authorId " +
											   "WHERE a.authorName LIKE ?";
	
	// Get query on books.
	public static String ALL_BOOKS_BY_GENRE = "SELECT * " +
											   "FROM tbl_book b " +
											   "INNER JOIN tbl_book_genres bg " +
											   "ON b.bookId = bg.bookId " +
											   "INNER JOIN tbl_genre g " +
											   "ON g.genre_id = bg.genre_id " +
											   "WHERE g.genre_name LIKE ?";
		
	// Get query on books.
	public static String ALL_BOOKS_BY_PUBLISHER = "SELECT * " +
											  	  "FROM tbl_book b " +
											  	  "INNER JOIN tbl_publisher p " +
											  	  "ON b.pubId = p.publisherId " +
											  	  "WHERE p.publisherName LIKE ?";

	// Get books not loaned from a branch.
	public final static String ALL_BOOKS_NOT_LOANED_FROM_BRANCH = "SELECT b.* " +
			   "FROM tbl_book b " +
			   "INNER JOIN tbl_book_copies " +
			   "ON tbl_book_copies.bookId = b.bookId " +
			   "INNER JOIN tbl_library_branch " +
			   "ON tbl_library_branch.branchId = tbl_book_copies.branchId " +
			   "WHERE tbl_book_copies.noOfCopies > 0 AND tbl_library_branch.branchId = ? AND b.bookId NOT IN ( " +
			   "SELECT bl.bookId " +
			   "FROM tbl_book_loans bl " +
			   "WHERE bl.cardNo = ?)";

	// Get books not loaned from a branch.
	public final static String ALL_BOOKS_NOT_LOANED_FROM_BRANCH_COUNT = "SELECT count(*) " +
			"FROM tbl_book b " +
			"INNER JOIN tbl_book_copies " +
			"ON tbl_book_copies.bookId = b.bookId " +
			"INNER JOIN tbl_library_branch " +
			"ON tbl_library_branch.branchId = tbl_book_copies.branchId " +
			"WHERE tbl_book_copies.noOfCopies > 0 AND tbl_library_branch.branchId = ? AND b.bookId NOT IN ( " +
			"SELECT bl.bookId " +
			"FROM tbl_book_loans bl " +
			"WHERE bl.cardNo = ?)";

	// Get books not loaned from a branch.
	public final static String ALL_BOOKS_NOT_LOANED_FROM_BRANCH_LIKE = "SELECT b.* " +
			"FROM tbl_book b " +
			"INNER JOIN tbl_book_copies " +
			"ON tbl_book_copies.bookId = b.bookId " +
			"INNER JOIN tbl_library_branch " +
			"ON tbl_library_branch.branchId = tbl_book_copies.branchId " +
			"WHERE b.title LIKE ? AND tbl_book_copies.noOfCopies > 0 AND tbl_library_branch.branchId = ? AND b.bookId NOT IN ( " +
			"SELECT bl.bookId " +
			"FROM tbl_book_loans bl " +
			"WHERE bl.cardNo = ?)";

	public final static String ALL_BOOKS_NOT_LOANED_FROM_BRANCH_LIKE_COUNT = "SELECT count(*) " +
			"FROM tbl_book b " +
			"INNER JOIN tbl_book_copies " +
			"ON tbl_book_copies.bookId = b.bookId " +
			"INNER JOIN tbl_library_branch " +
			"ON tbl_library_branch.branchId = tbl_book_copies.branchId " +
			"WHERE b.title LIKE ? AND tbl_book_copies.noOfCopies > 0 AND tbl_library_branch.branchId = ? AND b.bookId NOT IN ( " +
			"SELECT bl.bookId " +
			"FROM tbl_book_loans bl " +
			"WHERE bl.cardNo = ?)";
	
	// Get books from a publisher.
	public final static String ALL_BOOKS_FROM_PUBLISHER = "SELECT * " +
														  "FROM tbl_book b " +
														  "WHERE b.pubId = ?";
	
	// Get books with paging
	public final static String BOOK_COUNT = "SELECT count(*) AS COUNT " + 
										    "FROM tbl_book";

	// Insert a book.
	public final static String INSERT_BOOK = "INSERT INTO tbl_book " +
					                   "(title, pubId) " +
									   "VALUES(?, ?)";

	// Update a book.
	public final static String UPDATE_BOOK = "UPDATE tbl_book " +
	        								 "SET title = ?, pubId = ? " +
	        								 "WHERE bookId = ?";

	// Delete a book.
	public final static String DELETE_BOOK = "DELETE FROM tbl_book " +
						               		 "WHERE bookId = ?";
	
	// Relate book from author.
	public final static String RELATE_BOOK_WITH_AUTHOR = "INSERT INTO tbl_book_authors " +
														 "(bookId, authorId) " +
														 "VALUES(?, ?)";
	
	// Relate book from genre.
	public final static String RELATE_BOOK_WITH_GENRE = "INSERT INTO tbl_book_genres " +
														 "(bookId, genre_id) " +
														 "VALUES(?, ?)";
	// Unrelate book from author.
	public final static String UNRELATE_BOOK_FROM_AUTHORS = "DELETE FROM tbl_book_authors " +
														   "WHERE bookId = ?";

	// Unrelate author from book.
	public final static String UNRELATE_AUTHOR_FROM_BOOKS = "DELETE FROM tbl_book_authors " +
												   			"WHERE authorId = ?";
	
	// Unrelate book from author.
	public final static String UNRELATE_BOOK_FROM_GENRES = "DELETE FROM tbl_book_genres " +
														  "WHERE bookId = ?";
	
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
	
	// Get query on authors.
	public final static String ALL_AUTHORS_LIKE = "SELECT * " +
												  "FROM tbl_author " +
												  "WHERE authorName LIKE ?";
	
	// Get count of authors.
	public final static String AUTHOR_COUNT = "SELECT count(*) AS COUNT " + 
											    "FROM tbl_author";

	
	// Getting an author with certain ID.
	public final static String AUTHOR_WITH_ID = ALL_AUTHORS + " WHERE a.authorId = ?";
	
	// Getting authors from a book.
	public final static String ALL_AUTHORS_FROM_BOOK = ALL_AUTHORS + " WHERE a.authorId IN (" +
														   " SELECT ba.authorId " +
														   " FROM tbl_book_authors ba " +
														   " WHERE ba.bookId = ?)";
	
	// Insert an author.
	public final static String INSERT_AUTHOR = "INSERT INTO tbl_author " +
						                 "(authorName) " +
										 "VALUES(?)";
	
	// Delete an author.
	public final static String DELETE_AUTHOR = "DELETE FROM tbl_author " +
						                 	   "WHERE authorId = ?";
	
	// Update an author.
	public final static String UPDATE_AUTHOR = "UPDATE tbl_author " +
	        							 	   "SET authorName = ? " +
	        							       "WHERE authorId = ?";
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
	
	// Get genres like string.
	public final static String ALL_GENRES_LIKE = "SELECT * " +
												 "FROM tbl_genre " +
												 "WHERE genre_name LIKE ?";
	
	// Get genre count.
	public final static String GENRE_COUNT = "SELECT count(*) AS COUNT " +
	                                   "FROM tbl_genre";
	
	// Get genres from a book.
	public final static String ALL_GENRES_FROM_BOOK = ALL_GENRES + " WHERE g.genre_id IN (" +
														   " SELECT bg.genre_id " +
														   " FROM tbl_book_genres bg " +
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
	
	// Get publisher from string.
	public final static String ALL_PUBLISHERS_LIKE = "SELECT * " +
	                                   "FROM tbl_publisher " +
	                                   "WHERE publisherName LIKE ?";
	
	// Get pub count.
	public final static String PUBLISHER_COUNT = "SELECT count(*) AS COUNT " +
	                                   "FROM tbl_publisher";

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
	
	public final static String ALL_BORROWERS_LIKE = "SELECT * " +
											   "FROM tbl_borrower " +
											   "WHERE name LIKE ?";
	
	public final static String BORROWER_COUNT = "SELECT count(*) AS COUNT " +
												"FROM tbl_borrower";
	
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
	
	// Get branches from string.
	public final static String ALL_BRANCHES_LIKE = "SELECT * " +
												   "FROM tbl_library_branch " +
												   "WHERE branchName LIKE ?";
	
	// Get branch count.
	public final static String BRANCH_COUNT = "SELECT count(*) AS COUNT " +
	                                   "FROM tbl_library_branch";
	
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

	public final static String ALL_LOANS_FROM_BORROWER_IN_BRANCH = "SELECT * " +
															       "FROM tbl_book_loans " +
																   "WHERE branchId = ? AND cardNo = ?";

	public final static String ALL_LOANS_FROM_BORROWER_IN_BRANCH_LIKE = "SELECT * " +
																   "FROM tbl_book_loans " +
																   "INNER JOIN tbl_book " +
																   "ON tbl_book.bookId = tbl_book_loans.bookId " +
													               "WHERE title LIKE ? AND branchId = ? AND cardNo = ?";

	public final static String ALL_LOANS_FROM_BORROWER_LIKE = "SELECT * " +
															  "FROM tbl_book_loans " +
															  "INNER JOIN tbl_book " +
															  "ON tbl_book.bookId = tbl_book_loans.bookId " +
															  "WHERE tbl_book.title LIKE ? AND tbl_book_loans.cardNo = ?";

	// Get loans from book.
	public final static String ALL_LOANS_FROM_BOOK = "SELECT * " +
													 "FROM tbl_book_loans " +
													 "WHERE bookId = ?";
	
	// Get loans from search string.
	public final static String ALL_LOANS_LIKE = "SELECT * " +
												"FROM tbl_book_loans " +
												"INNER JOIN tbl_book " +
												"ON tbl_book_loans.bookId = tbl_book.bookId " +
												"WHERE tbl_book_loans.cardNo = ? AND tbl_book.title LIKE ?";
	
	// Get loan count.
	public final static String LOAN_COUNT = "SELECT count(*) AS COUNT " +
											"FROM tbl_book_loans";
	
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

    // Get all book copies in branch
	public final static String ALL_COPIES_IN_BRANCH = "SELECT * " +
			   										  "FROM tbl_book_copies " +
			   										  "WHERE branchId = ?";
	
    // Get all book copies in branch
	public final static String ALL_COPIES_LIKE = "SELECT * " +
			   									 "FROM tbl_book_copies " +
			   									 "INNER JOIN tbl_book " +
			   									 "ON tbl_book.bookId = tbl_book_copies.bookId " +
			   									 "WHERE tbl_book_copies.branchId = ? AND tbl_book.title LIKE ?";

	// Get copy.
	public final static String COPY_WITH_ID = "SELECT * " +
											  "FROM tbl_book_copies " +
											  "WHERE tbl_book_copies.bookId = ? AND tbl_book_copies.branchId = ?";
	
	// Get books with paging
	public final static String COPIES_COUNT = "SELECT count(*) AS COUNT " + 
											    "FROM tbl_book_copies";

	// Insert into copies.
	public final static String INSERT_COPIES = "INSERT INTO tbl_book_copies " +
			   								   "VALUES(?, ?, ?) ";

	// Update copies' noOfCopies.
	public final static String UPDATE_COPIES = "UPDATE tbl_book_copies " +
		   	   								   "SET noOfCopies = ? " +
		   	   								   "WHERE bookId = ? AND branchId = ?";
}

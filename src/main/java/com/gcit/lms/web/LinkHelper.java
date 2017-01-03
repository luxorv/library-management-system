package com.gcit.lms.web;

public class LinkHelper {
	
	/* 
	 * Class for link constants: 
	 * 
	 * This class contains constants for all Controllers on the Application.
	 * 
	 *  
	 * 1) Books:
	 * 		- list
	 * 		- view
	 *  	- add
	 *  	- edit
	 * 
	 */
	
	public static final String BORROWERS_MENU = "/lms/borrowers/menu.jsp";
	
	public static final String PICK_BRANCH = "/lms/branches/pickbranch";
	
	public static final String LOAN_BOOK = "/lms/borrowers/loan";
	
	public static final String LIST_BOOKS_FOR_BORROWER = "/lms/borrowers/pickbookforloan";
	
	public static final String LIST_BRANCHES_FOR_BORROWER = "/lms/borrowers/pickbranchforloan";
	
	public static final String PICK_BOOK_FOR_BORROWER = "/lms/borrowers/pickbookforloan.jsp";
	
	public static final String INDEX = "/lms/";
	
	public static final String LIBRARIAN_MENU = "/lms/branches/menu.jsp";
	
	public static final String SHOW_BRANCH_INFO = "/lms/branches/showinfo";
	
	public static final String UPDATE_BRANCH = "/lms/branches/update";
	
	public static final String LIST_BOOKS_FOR_COPIES = "/lms/branches/pickbookforcopies";

	public static final String UPDATE_COPY = "/lms/branches/updatecopy";
	
	public static final String ALL_PUBLISHERS = "/lms/admin/pickpublisher";
	
	public static final String NEW_PUBLISHER_FORM = "/lms/admin/shownewpublisher";
	
	public static final String ADD_PUBLISHER = "/lms/admin/addpublisher";
	
	public static final String SHOW_PUBLISHER_INFO = "/lms/admin/showpublisherinfo";
	
	public static final String UPDATE_PUBLISHER = "/lms/admin/editpublisher";
	
	public static final String DELETE_PUBLISHER = "/lms/admin/deletepublisher";
	
	public static final String LIST_BRANCHES = "/lms/admin/pickbranch";
	
	public static final String NEW_BRANCH_FORM = "/lms/admin/shownewbranchinfo";
	
	public static final String SHOW_BRANCH_INFO_ADMIN = "/lms/admin/showbranchinfo";
	
	public static final String ADD_BRANCH = "/lms/admin/addbranch";
	
	public static final String UPDATE_BRANCH_INFO = "/lms/admin/updatebranch";
	
	public static final String DELETE_BRANCH = "/lms/admin/deletebranch";
	
	public static final String ALL_BORROWERS = "/lms/admin/pickborrower";
	
	public static final String NEW_BORROWER_FORM = "/lms/admin/shownewborrowerinfo";
	
	public static final String SHOW_BORROWER_INFO = "/lms/admin/showborrowerinfo";
	
	public static final String UPDATE_BORROWER = "/lms/admin/editborrower";
	
	public static final String ADD_BORROWER = "/lms/admin/addborrower";
	
	public static final String DELETE_BORROWER = "/lms/admin/deleteborrower";

	public static final String ALL_BOOKS = "/lms/admin/pagebooks?pageNo=1";
	
	public static final String SHOW_BOOK_INFO = "/lms/admin/showbookinfo";
	
	public static final String NEW_BOOK_FORM = "/lms/admin/shownewbookinfo";
	
	public static final String UPDATE_BOOK = "/lms/admin/updatebook";
	
	public static final String DELETE_BOOK = "/lms/admin/deletebook";

	public static final String ADD_BOOK = "/lms/admin/addbook";
	
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
	
	public static final String ALL_AUTHORS = "/lms/admin/pickauthor";
	
	public static final String SHOW_AUTHOR_INFO = "/lms/admin/showauthorinfo";
	
	public static final String NEW_AUTHOR_FORM = "/lms/admin/shownewauthorinfo";
	
	public static final String UPDATE_AUTHOR = "/lms/admin/updateauthor";
	
	public static final String DELETE_AUTHOR = "/lms/admin/deleteauthor";
	
	public static final String ADD_AUTHOR = "/lms/admin/addauthor";
	
	// TODO
	
	public static final String LIST_BORROWERS = "/lms/admin/pickborrowerforloans";
	
	public static final String ALL_LOANS = "/lms/admin/pickloans";
	
	public static final String UPDATE_LOAN = "/lms/admin/updateloan";
}

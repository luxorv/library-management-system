package com.gcit.library.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.library.dao.AuthorDAO;
import com.gcit.library.dao.BaseDAO;
import com.gcit.library.dao.QueryHelper;
import com.gcit.library.model.Author;
import com.gcit.library.model.DatabaseManager;

/**
 * Servlet implementation class AuthorServlet
 */
@WebServlet({
	"/admin/pickauthor",
	"/admin/showauthorinfo",
	"/admin/shownewauthorinfo",
	"/admin/updateauthor",
	"/admin/deleteauthor",
	"/admin/addauthor",
	"/admin/queryauthors"
})
public class AuthorServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthorServlet() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	this.req = req;
    	this.resp = resp;
    	
    	if(req.getParameter("isAjax") == null) {
    		dispatch(req, resp, this.execute());
    	} else {
    		this.executeAjax();
    	}
    }
    
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doGet(req, resp);
    }

	@Override
	public String execute() {
		
		try {
			BaseDAO.setConnection(DatabaseManager.getInstance().getCurrentConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		switch (this.getUrl()) {
			case "/admin/pickauthor":
				return this.listAuthors();
			case "/admin/showauthorinfo":
				return this.showAuthorInfo();
			case "/admin/shownewauthorinfo":
				return this.showAuthorInfo();
			case "/admin/updateauthor":
				return this.editAuthor();
			case "/admin/deleteauthor":
				return this.deleteAuthor();
			case "/admin/addauthor":
				return this.saveAuthor();
			
		}
		
		return PAGE_NOT_FOUND;
	}
	
	private void executeAjax() {
		
		try {
			BaseDAO.setConnection(DatabaseManager.getInstance().getCurrentConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(this.getUrl().equals("/admin/queryauthors")) {
			this.queryAuthors();
		}
	}
	
	private void queryAuthors() {
		try {
			String searchString = req.getParameter("searchString");
			
			ArrayList<Author> authors = new AuthorDAO().queryAuthors(searchString);
			StringBuilder builder = new StringBuilder();
			
			System.out.println("AUTHORS: " + authors);
			
			builder.append("<tr><th>#</th><th>Author Name</th><th>Delete Author</th></tr>");
			
			for (Author author : authors) {
				builder.append("<tr><td>"+authors.indexOf(author) + 1+"</td>");
				builder.append("<td><a href='" + LinkHelper.SHOW_AUTHOR_INFO);
				builder.append("?authorId="+ author.getAuthorId()+ "'>"+ author.getAuthorName()+"</a></td>");
				builder.append("<td><a class='btn btn-danger' href='"+ LinkHelper.DELETE_AUTHOR);
				builder.append("?authorId="+ author.getAuthorId() +"'>Delete</a></td></tr>");
			}
			
			resp.getWriter().append(builder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String showAuthorInfo() {
		
		int authorId = -1;
		
		Author author = new Author();
		
		if(this.getUrl().equals("/admin/showauthorinfo")) {
			authorId = Integer.parseInt(req.getParameter("authorId"));
			
			author = (Author) new AuthorDAO().getAll(
				QueryHelper.AUTHOR_WITH_ID,
				false,
				new Object[]{authorId}
			).get(0);
		} else {
			author.setAuthorName("");
		}
		
		System.out.println(author);
		
		req.setAttribute("author", author);
		
		return "updateauthor.jsp";
	}

	private String listAuthors() {
		
		ArrayList<Author> authors = new AuthorDAO().getAll(
			QueryHelper.ALL_AUTHORS,
			false,
			null
		);
		
		System.out.println(authors);
		
		req.setAttribute("authors", authors);
		
		return "pickauthor.jsp";
	}

	private String editAuthor() {
		
		int authorId = Integer.parseInt(req.getParameter("authorId"));
		String authorName = req.getParameter("authorName");
		
		Author author = new Author();
		
		author.setAuthorId(authorId);
		
		if(authorName != null && authorName.length() > 0) {
			author.setAuthorName(authorName);
		}
		
		System.out.println(author);
		
		new AuthorDAO().update(author);
		
		return "pickauthor";
	}

	private String deleteAuthor() {
		
		int authorId = Integer.parseInt(req.getParameter("authorId"));
		
		Author author = new Author();
		
		author.setAuthorId(authorId);
		
		new AuthorDAO().delete(author);
		
		return "pickauthor";
	}

	private String saveAuthor() {
		
		String authorName = req.getParameter("authorName");
		
		Author author = new Author();
		
		author.setAuthorName(authorName);
		
		int authorId = new AuthorDAO().save(author);
		
		System.out.println("New Author Id: " + authorId);
		
		return "pickauthor";
	}

}

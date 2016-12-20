package com.gcit.library.web;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import com.gcit.library.dao.BaseDAO;
import com.gcit.library.dao.PublisherDAO;
import com.gcit.library.dao.QueryHelper;
import com.gcit.library.model.DatabaseManager;
import com.gcit.library.model.Publisher;

/**
 * Servlet implementation class PublisherServlet
 */
@WebServlet({ 
	"/admin/pickpublisher",
	"/admin/showpublisherinfo",
	"/admin/shownewpublisher",
	"/admin/addpublisher",
	"/admin/editpublisher",
	"/admin/deletepublisher"
})
public class PublisherServlet extends BaseServlet {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -9100582334561160733L;

	/**
     * Default constructor. 
     */
    public PublisherServlet() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public String execute() {
		
		try {
			BaseDAO.setConnection(DatabaseManager.getInstance().getCurrentConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		switch (this.getUrl()) {
			case "/admin/pickpublisher":
				return this.listPublishers();
			case "/admin/addpublisher":
				return this.addPublisher();
			case "/admin/shownewpublisher":
				return this.showPublisherInfo();
			case "/admin/showpublisherinfo":
				return this.showPublisherInfo();
			case "/admin/editpublisher":
				return this.editPublisher();
			case "/admin/deletepublisher":
				return this.deletePublihser();
		}
		
		return PAGE_NOT_FOUND;
	}

	private String showPublisherInfo() {
		
		int publisherId = -1;
		Publisher publisher = new Publisher();
		
		if(this.getUrl().equals("/admin/showpublisherinfo")) {
			publisherId = Integer.parseInt(req.getParameter("publisherId"));
			
			publisher = (Publisher) new PublisherDAO().getAll(
					QueryHelper.PUBLISHER_WITH_ID,
					false,
					new Object[]{publisherId}
				).get(0);
		} else {
			publisher.setPublisherName("");
			publisher.setPublisherAddress("");
			publisher.setPublisherPhone("");
		}
		
		System.out.println(publisher);
		
		req.setAttribute("publisher", publisher);
		
		return "updatepublisher.jsp";
	}
	
	private String addPublisher() {
		
		String publisherName = req.getParameter("publisherName");
		String publisherAddress = req.getParameter("publisherAddress");
		String publisherPhone = req.getParameter("publisherPhone");
		
		Publisher publisher = new Publisher();
		
		publisher.setPublisherName(publisherName);
		publisher.setPublisherAddress(publisherAddress);
		publisher.setPublisherPhone(publisherPhone);
		
		int pubId = new PublisherDAO().save(publisher);
		
		System.out.println("New Publisher Id: " + pubId);
		
		return "pickpublisher";
	}

	private String editPublisher() {
		
		int publisherId = Integer.parseInt(req.getParameter("publisherId"));
		String publisherName = req.getParameter("publisherName");
		String publisherAddress = req.getParameter("publisherAddress");
		String publisherPhone = req.getParameter("publisherPhone");
		
		Publisher publisher = new Publisher();
		
		publisher.setPublisherId(publisherId);
		
		if(publisherName != null && publisherName.length() > 0) {
			publisher.setPublisherName(publisherName);
		}
		
		if(publisherAddress != null && publisherAddress.length() > 0) {
			publisher.setPublisherAddress(publisherAddress);
		}
		
		if(publisherPhone != null && publisherPhone.length() > 0) {
			publisher.setPublisherPhone(publisherPhone);
		}
		
		System.out.println(publisher);
		
		new PublisherDAO().update(publisher);
		
		return "pickpublisher";
	}
	
	private String deletePublihser() {
		int publisherId = Integer.parseInt(req.getParameter("publisherId"));
		
		Publisher publisher = new Publisher();
		
		publisher.setPublisherId(publisherId);
		
		new PublisherDAO().delete(publisher);
		
		return "pickpublisher";
	}

	private String listPublishers() {
		
		try {
			BaseDAO.setConnection(DatabaseManager.getInstance().getCurrentConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ArrayList<Publisher> publishers = new PublisherDAO().getAll(
			QueryHelper.ALL_PUBLISHERS,
			false,
			null
		);
		
		System.out.println(publishers);
		
		req.setAttribute("publishers", publishers);
		
		return "pickpublisher.jsp";
	}
}

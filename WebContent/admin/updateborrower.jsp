<%@ include file="../template.html" %>
<%@page import="com.gcit.library.web.LinkHelper"%>
<%@page import="com.gcit.library.model.Borrower"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Borrower borrower = (Borrower) request.getAttribute("borrower");

	String url = "";
	
	if(borrower.getName().length() == 0) {
		url = LinkHelper.ADD_BORROWER;
	} else {
		url = LinkHelper.UPDATE_BORROWER;
	}
%>
<div class="container theme-showcase" role="main">
	<div class="jumbotron">
		<h2>Borrower Info</h2><br><br>
		<form action="<%=url%>">
			<input type="hidden" name="cardNo" value="<%=borrower.getCardNo()%>" />
			<label>Borrower Name</label><br>
			<input type="text" name="name" value="<%=borrower.getName()%>" /><br><br>
			<label>Borrower Address</label><br>
			<input type="text" name="address" value="<%=borrower.getAddress()%>" /><br><br>
			<label>Borrower Phone</label><br>
			<input type="text" name="phone" value="<%=borrower.getPhone()%>" /><br><br><br><br>
			<button class="btn btn-info" type="submit">Save Borrower</button>
			<a href="<%=LinkHelper.ALL_BORROWERS%>" class="btn btn-danger" type="cancel">Cancel</a>
		</form>
	</div>
</div>
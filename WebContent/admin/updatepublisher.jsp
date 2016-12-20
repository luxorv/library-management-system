<%@ include file="../template.html" %>
<%@page import="com.gcit.library.web.LinkHelper"%>
<%@page import="com.gcit.library.model.Publisher"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Publisher publisher = (Publisher) request.getAttribute("publisher");

	String url = "";
	
	if(publisher.getPublisherName().length() == 0) {
		url = LinkHelper.ADD_PUBLISHER;
	} else {
		url = LinkHelper.UPDATE_PUBLISHER;
	}
%>
<div class="container theme-showcase" role="main">
	<div class="jumbotron">
		<h2>Branch Info</h2><br><br>
		<form action="<%=url%>">
			<input type="hidden" name="publisherId" value="<%=publisher.getPublisherId()%>" />
			<label>Publisher Name</label><br>
			<input type="text" name="publisherName" value="<%=publisher.getPublisherName()%>" /><br><br>
			<label>Publisher Address</label><br>
			<input type="text" name="publisherAddress" value="<%=publisher.getPublisherAddress()%>" /><br><br>
			<label>Publisher Phone</label><br>
			<input type="text" name="publisherPhone" value="<%=publisher.getPublisherPhone()%>" /><br><br><br><br>
			<button class="btn btn-info" type="submit">Save Publisher</button>
			<a href="<%=LinkHelper.ALL_PUBLISHERS%>" class="btn btn-danger" type="cancel">Cancel</a>
		</form>
	</div>
</div>
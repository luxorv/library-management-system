<%@ include file="../template.html" %>
<%@page import="com.gcit.library.web.LinkHelper"%>
<%@page import="com.gcit.library.model.Author"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Author author = (Author) request.getAttribute("author");

	String url = "";
	
	if(author.getAuthorName().length() == 0) {
		url = LinkHelper.ADD_AUTHOR;
	} else {
		url = LinkHelper.UPDATE_AUTHOR;
	}
%>
<div class="container theme-showcase" role="main">
	<div class="jumbotron">
		<h2>Branch Info</h2><br><br>
		<form action="<%=url%>">
			<input type="hidden" name="authorId" value="<%=author.getAuthorId()%>" />
			<label>Author Name</label><br>
			<input type="text" name="authorName" value="<%=author.getAuthorName()%>" /><br><br>
			<button class="btn btn-info" type="submit">Save Author</button>
			<a href="<%=LinkHelper.ALL_AUTHORS%>" class="btn btn-danger" type="cancel">Cancel</a>
		</form>
	</div>
</div>
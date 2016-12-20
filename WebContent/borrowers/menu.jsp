<%@ include file="../template.html" %>
<%@page import="com.gcit.library.web.LinkHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	int cardNo = -1;

	if(request.getParameter("cardNo") != null) {
		cardNo = Integer.parseInt(request.getParameter("cardNo"));
	}
%>
<div class="container theme-showcase" role="main">
	<div class="jumbotron">
		<h2>Pick Your Option </h2><br><br>
		<a href="<%=LinkHelper.LIST_BRANCHES_FOR_BORROWER%>?option=1&cardNo=<%=cardNo%>">Checkout a Book</a><br><br>
		<a href="<%=LinkHelper.LIST_BRANCHES_FOR_BORROWER%>?option=2&cardNo=<%=cardNo%>">Return a Book</a>
	</div>
</div>
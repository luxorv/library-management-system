<%@ include file="../template.html" %>
<%@page import="com.gcit.library.web.LinkHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	int branchId = -1;

	if(request.getParameter("branchId") != null) {
		branchId = Integer.parseInt(request.getParameter("branchId"));
	}
%>
<div class="container theme-showcase" role="main">
	<div class="jumbotron">
		<h2>Pick Your Option</h2><br><br>
		<a href="<%=LinkHelper.SHOW_BRANCH_INFO%>?branchId=<%=branchId%>">Update branch</a><br><br>
		<a href="<%=LinkHelper.LIST_BOOKS_FOR_COPIES%>?branchId=<%=branchId%>">Add Copies to Branch</a>	
	</div>
</div>
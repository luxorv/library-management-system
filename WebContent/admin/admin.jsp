<%@ include file="../template.html" %>
<%@page import="com.gcit.library.web.LinkHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="container theme-showcase" role="main">
	<div class="jumbotron">
		<h2>Pick Your Option</h2><br><br>
		<div class="list-group">
			<a href="<%=LinkHelper.ALL_BOOKS%>" class="list-group-item list-group-item-action active">
				<h4 class="list-group-item-heading">Book</h4>
				<p class="list-group-item-text">Here you can Add, Update and Delete a Book
				and it's attribute.</p>
			</a> 
			<a href="<%=LinkHelper.ALL_AUTHORS%>" class="list-group-item list-group-item-action">
				<h4 class="list-group-item-heading">Authors</h4>
				<p class="list-group-item-text">Here you can Add, Update and Delete an Author
				and it's attributes.</p>
			</a>
			<a href="<%=LinkHelper.ALL_PUBLISHERS%>" class="list-group-item list-group-item-action">
				<h4 class="list-group-item-heading">Publishers</h4>
				<p class="list-group-item-text">Here you can Add, Update and Delete a Publisher
				and it's attributes.</p>
			</a>
			<a href="<%=LinkHelper.LIST_BRANCHES%>" class="list-group-item list-group-item-action">
				<h4 class="list-group-item-heading">Library Branch</h4>
				<p class="list-group-item-text">Here you can Add, Update and Delete a Library Branch
				and it's attributes.</p>
			</a>
			</a> 
			<a href="<%=LinkHelper.ALL_BORROWERS%>" class="list-group-item list-group-item-action">
				<h4 class="list-group-item-heading">Borrower</h4>
				<p class="list-group-item-text">Here you can Add, Update and Delete a Borrower
				and it's attributes.</p>
			</a>
			</a> 
			<a href="<%=LinkHelper.LIST_BORROWERS%>" class="list-group-item list-group-item-action">
				<h4 class="list-group-item-heading">Book Loan</h4>
				<p class="list-group-item-text">Here you can Override the due date of a Book Loan
				for an expecific Borrower.</p>
			</a>
		</div>
	</div>
</div>
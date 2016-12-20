<%@ include file="../template.html" %>
<%@page import="com.gcit.library.web.LinkHelper"%>
<%@page import="com.gcit.library.model.LibraryBranch"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	LibraryBranch branch = (LibraryBranch) request.getAttribute("branch");
	System.out.println(branch);
	
	String url = "";
	
	if(branch.getBranchName().length() == 0) {
		url = LinkHelper.ADD_BRANCH;
	} else {
		url = LinkHelper.UPDATE_BRANCH_INFO;
	}

%>
<div class="container theme-showcase" role="main">
	<div class="jumbotron">
		<h2>Branch Info</h2><br><br>
		<form action="<%=url%>">
			<input type="hidden" name="branchId" value="<%=branch.getBranchId()%>" />
			<label>Branch Name</label><br>
			<input type="text" name="branchName" value="<%=branch.getBranchName()%>" /><br><br>
			<label>Branch Address</label><br>
			<input type="text" name="branchAddress" value="<%=branch.getBranchAddress()%>" /><br><br><br><br>
			<button class="btn btn-info" type="submit">Save Branch</button>
			<a href="<%=LinkHelper.LIST_BRANCHES%>" class="btn btn-danger" type="cancel">Cancel</a>
		</form>
	</div>
</div>
<%@include file="../template.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.library.model.LibraryBranch"%>
<%@page import="com.gcit.library.web.LinkHelper"%>

<% 
	ArrayList<LibraryBranch> branches = (ArrayList<LibraryBranch>) request.getAttribute("branches");
	System.out.println("branches: " + branches);
	
	int cardNo = -1, option = -1;
	
	if(request.getAttribute("cardNo") != null) {
		cardNo = (int) request.getAttribute("cardNo");
	}
	
	if(request.getAttribute("option") != null) {
		option = (int) request.getAttribute("option");
	}
	
%>

<div class="container theme-showcase" role="main">
	<div class="jumbotron">
		<h2>Pick Branch </h2><br><br>
		<table class="table">
			<tr>
				<th>#</th>
				<th>Branch Name</th>
			</tr>
			<%for(LibraryBranch branch : branches) { %>
				<tr>
					<td><%=branches.indexOf(branch) + 1%></td>
					<td>
					<a href="<%=LinkHelper.LIST_BOOKS_FOR_BORROWER%>?branchId=<%= branch.getBranchId() %>&cardNo=<%= cardNo %>&option=<%=option%>"><%=branch.getBranchName()%></a></td>
				</tr>
			<% } %>
		</table>
	</div>
</div>
<%@include file="../template.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.library.model.LibraryBranch"%>
<%@page import="com.gcit.library.web.LinkHelper"%>

<% 
	ArrayList<LibraryBranch> branches = (ArrayList<LibraryBranch>) request.getAttribute("branches");
	System.out.println("branches: " + branches);
%>

<div class="container theme-showcase" role="main">
	<div class="jumbotron">
		<h2>Pick Branch</h2><br><br>
		<table class="table">
			<tr>
				<th>#</th>
				<th>Branch Name</th>
				<th>Branch Address</th>
			</tr>
			<%for(LibraryBranch branch : branches) { %>
				<tr>
					<td><%=branches.indexOf(branch) + 1%></td>
					<td>
					<a href="<%=LinkHelper.LIBRARIAN_MENU%>?branchId=<%= branch.getBranchId() %>"><%=branch.getBranchName()%></a></td>
					<td><%=branch.getBranchAddress()%></td>
				</tr>
			<% } %>
		</table>
	</div>
</div>
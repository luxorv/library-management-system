<%@include file="../template.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.library.model.BookCopies"%>
<%@page import="com.gcit.library.web.LinkHelper"%>

<% 
	ArrayList<BookCopies> copies = (ArrayList<BookCopies>) request.getAttribute("copies");
	System.out.println("copies: " + copies);
%>

<div class="container theme-showcase" role="main">
	<div class="jumbotron">
		<h2>Pick Book</h2><br><br>
		<table class="table">
			<tr>
				<th>#</th>
				<th>Book Title</th>
				<th>No Of Copies</th>
				<th>Save</th>
			</tr>
			<%for(BookCopies copy : copies) { %>
				<form action="<%=LinkHelper.UPDATE_COPY%>">
					<tr>
						<td><%=copies.indexOf(copy) + 1%></td>
						<td><%=copy.getBook().getTitle()%></td>
						<input type="hidden" name="bookId" value="<%=copy.getBook().getBookId()%>"/>
						<input type="hidden" name="branchId" value="<%=copy.getBranch().getBranchId()%>"/>
						<td><input type="number" name="noOfCopies" value="<%=copy.getNoOfCopies()%>"/></td>
						<td><button class="btn btn-success" type="submit">Save</button></td>
					</tr>
				</form>
				<% } %>
		</table>
	</div>
</div>
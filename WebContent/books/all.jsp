<%@include file="../template.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.library.model.Book"%>

<% 
	List<Book> books = new ArrayList<Book>();
	books = (List<Book>) request.getAttribute("books");
	
%>

<div class="container theme-showcase" role="main">
	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="jumbotron">
		<h1>GCIT Library Management System</h1>
		<p>Welcome to GCIT Library Management System. Have Fun Shopping!</p>
		<h3>Hello Admin! What do you want to do?</h3>
		<table class="table">
			<tr>
				<th>#</th>
				<th>Book Name</th>
				<th>Edit Book</th>
				<th>Delete Book</th>
			</tr>
			<%
				for (Book book : books) {
			%>
			<tr>
				<td><%=books.indexOf(book) + 1%></td>
				<td><%=book.getTitle()%></td>
				<td><button class="btn btn-success" data-toggle="modal"
						data-target="#editbookmodal"
						href="books/editBook?bookId=<%=book.getBookId()%>">Edit</button></td>
				<td><button class="btn btn-danger"
						onclick="javascript:location.href='books/deleteBook?bookId=<%=book.getBookId()%>'">Delete</button></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
</div>

<div class="modal fade bs-example-modal-lg" id="editbookmodal"
	tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content"></div>
	</div>
</div>
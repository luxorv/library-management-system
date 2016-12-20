<%@include file="../template.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.library.model.Book"%>
<%@page import="com.gcit.library.web.LinkHelper"%>

<% 
	List<Book> books = new ArrayList<Book>();
	books = (List<Book>) request.getAttribute("books");
	
	Integer count = (Integer) request.getAttribute("count");
	Integer pageSize = (Integer) request.getAttribute("pageSize");
	Integer pageNo = (Integer) request.getAttribute("pageNo");
	Integer pages = 1;
	
	if (count % 10 > 0) {
		pages += (count / 10) + 1;
	} else {
		pages += (count / 10);
	}
	
	System.out.println(pages);
%>

<div class="container theme-showcase" role="main">
	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="jumbotron">
		<h2>Pick a Book</h2><br><br>
		<table class="table">
			<tr><a href="<%=LinkHelper.NEW_BOOK_FORM%>">Add new Book</a></tr><br>
			<tr><nav aria-label="Page navigation">
				<ul class="pagination">
					<%
						for (int i = 1; i <= pages; i++) {
					%>
					<li><a href="pagebooks?pageNo=<%=i%>"><%=i%></a></li>
					<%
						}
					%>
				</ul>
			</nav></tr>
			<tr>
				<th>#</th>
				<th>Book Name</th>
				<th>Delete Book</th>
			</tr>
			<%
				for (Book book : books) {
			%>
			<tr>
				<td><%=(books.indexOf(book) + 1) + ((pageNo - 1)* pageSize)%></td>
				<td><a href="<%=LinkHelper.SHOW_BOOK_INFO%>?bookId=<%=book.getBookId()%>"><%=book.getTitle()%></a></td>
				<td><button class="btn btn-danger"
						onclick="javascript:location.href='<%=LinkHelper.DELETE_BOOK %>?bookId=<%=book.getBookId()%>'">Delete</button></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
</div>
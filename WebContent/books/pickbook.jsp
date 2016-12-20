<%@include file="../template.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.library.model.Book"%>
<%@page import="com.gcit.library.web.LinkHelper"%>

<% 
	ArrayList<Book> books = (ArrayList<Book>) request.getAttribute("books");
	System.out.println("books: " + books);
	
	int cardNo = -1, branchId = -1, option = -1;
	
	if(request.getAttribute("cardNo") != null) {
		cardNo = (int) request.getAttribute("cardNo");
	}
	
	if(request.getAttribute("branchId") != null) {
		branchId = (int) request.getAttribute("branchId");
	}
	
	if(request.getAttribute("option") != null) {
		option = (int) request.getAttribute("option");
	}
%>

<div class="container theme-showcase" role="main">
	<div class="jumbotron">
		<table class="table">
			<tr>
				<th>#</th>
				<th>Book Title</th>
			</tr>
			<%for(Book book : books) { %>
				<tr>
					<td><%=books.indexOf(book) + 1%></td>
					<td><a href="<%=LinkHelper.LOAN_BOOK%>?bookId=<%= book.getBookId()%>&cardNo=<%=cardNo%>&branchId=<%=branchId%>&option=<%=option%>"><%=book.getTitle()%></a></td>
				</tr>
			<% } %>
		</table>
	</div>
</div>
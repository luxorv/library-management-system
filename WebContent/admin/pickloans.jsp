<%@include file="../template.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.library.model.BookLoan"%>
<%@page import="com.gcit.library.web.LinkHelper"%>

<% 
	ArrayList<BookLoan> loans = (ArrayList<BookLoan>) request.getAttribute("loans");
	System.out.println("loans: " + loans);
%>

<div class="container theme-showcase" role="main">
	<div class="jumbotron">
		<h2>Pick Loan</h2><br><br>
		<table class="table">
			<tr>
				<th>#</th>
				<th>Book Title</th>
				<th>Due Date</th>
				<th>Save Due Date</th>
			</tr>
			<%for(BookLoan loan : loans) { %>
				<form action="<%=LinkHelper.UPDATE_LOAN%>">
					<tr>
						<td><%=loans.indexOf(loan) + 1%></td>
						<td><%=loan.getBook().getTitle()%></td>
						<input type="hidden" name="cardNo" value="<%=loan.getBorrower().getCardNo()%>"/>
						<input type="hidden" name="bookId" value="<%=loan.getBook().getBookId()%>"/>
						<input type="hidden" name="branchId" value="<%=loan.getBranch().getBranchId()%>"/>
						<td><input type="date" name="dueDate" value="<%=loan.getDueDate()%>"/></td>
						<td><button class="btn btn-success" type="submit">Save</button></td>
					</tr>
				</form>
				<% } %>
		</table>
	</div>
</div>
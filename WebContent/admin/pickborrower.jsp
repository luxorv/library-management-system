<%@include file="../template.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.library.model.Borrower"%>
<%@page import="com.gcit.library.web.LinkHelper"%>

<% 
	ArrayList<Borrower> borrowers = (ArrayList<Borrower>) request.getAttribute("borrowers");
	System.out.println("borrower: " + borrowers);
%>

<div class="container theme-showcase" role="main">
	<div class="jumbotron">
		<h2>Pick Borrower</h2><br><br>
		<table class="table">
			<tr><a href="<%=LinkHelper.NEW_BORROWER_FORM%>">Add new Borrower</a></tr><br><br>
			<tr>	
				<th>#</th>
				<th>Borrower Name</th>
			</tr>
			<%for(Borrower borrower : borrowers) { %>
				<tr>
					<td><%=borrowers.indexOf(borrower) + 1%></td>
					<td><a href="<%=LinkHelper.SHOW_BORROWER_INFO%>?cardNo=<%=borrower.getCardNo()%>"><%=borrower.getName()%></a></td>
					<td><button class="btn btn-danger"
						onclick="javascript:location.href='<%=LinkHelper.DELETE_BORROWER%>?cardNo=<%=borrower.getCardNo()%>'">Delete</button></td>
				</tr>
			<% } %>
		</table>
	</div>
</div>
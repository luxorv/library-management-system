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
			<tr>	
				<th>#</th>
				<th>Borrower Name</th>
			</tr>
			<%for(Borrower borrower : borrowers) { %>
				<tr>
					<td><%=borrowers.indexOf(borrower) + 1%></td>
					<td><a href="<%=LinkHelper.BORROWERS_MENU%>?cardNo=<%=borrower.getCardNo()%>"><%=borrower.getName()%></a></td>
				</tr>
			<% } %>
		</table>
	</div>
</div>
<%@include file="../template.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.library.model.Publisher"%>
<%@page import="com.gcit.library.web.LinkHelper"%>

<% 
	List<Publisher> publishers = new ArrayList<Publisher>();
	publishers = (List<Publisher>) request.getAttribute("publishers");
	System.out.println(publishers);
%>

<div class="container theme-showcase" role="main">
	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="jumbotron">
		<h2>Pick Publisher</h2><br><br>
		<table class="table">
			<tr><a href="<%=LinkHelper.NEW_PUBLISHER_FORM%>">Add new Publisher</a></tr><br><br>
			<tr>
				<th>#</th>
				<th>Publisher Name</th>
				<th>Delete Publisher</th>
			</tr>
			<%
				for (Publisher publisher : publishers) {
			%>
			<tr>
				<td><%=publishers.indexOf(publisher) + 1%></td>
				<td><a href="<%=LinkHelper.SHOW_PUBLISHER_INFO%>?publisherId=<%=publisher.getPublisherId()%>"><%= publisher.getPublisherName() %></a></td>
				<td><button class="btn btn-danger"
						onclick="javascript:location.href='<%=LinkHelper.DELETE_PUBLISHER%>?publisherId=<%=publisher.getPublisherId()%>'">Delete</button></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
</div>

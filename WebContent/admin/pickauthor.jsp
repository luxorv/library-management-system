<%@include file="../template.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.library.model.Author"%>
<%@page import="com.gcit.library.web.LinkHelper"%>

<% 
	List<Author> authors = new ArrayList<Author>();
	authors = (List<Author>) request.getAttribute("authors");
	System.out.println(authors);
%>

<script type="text/javascript">
function searchAuthors(){
	
	$.ajax({
		  url: "queryauthors",
		  data: 
		  { 
			  searchString: $('#searchString').val(),
			  isAjax: true
		  }
		}).done(function(data) {
			
			var newHtml = "<tr><td><a href='/LibraryManagementSystem/admin/shownewauthorinfo'";
			newHtml += ">Add new Author</a></td></tr><br><br>";
			newHtml += data;
			
		 	$("#authorsTable").html(newHtml);
		});
}
</script>

<div class="container theme-showcase" role="main">
	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="jumbotron">
		<h2>Pick Author</h2><br><br>
		<div class="input-group input-group-lg">
			<span class="input-group-addon" id="sizing-addon1">Search</span> <input
				type="text" class="form-control" placeholder="Autho Name"
				aria-describedby="sizing-addon1" name="searchString" id="searchString" onkeydown="searchAuthors()">
		</div><br><br>
		<table class="table" id="authorsTable">
		</table>
	</div>
</div>

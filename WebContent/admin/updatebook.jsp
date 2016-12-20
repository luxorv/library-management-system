<%@page import="com.gcit.library.web.LinkHelper"%>
<%@include file="../template.html"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.library.model.Book"%>
<%@page import="com.gcit.library.model.Genre"%>
<%@page import="com.gcit.library.model.Author"%>
<%@page import="com.gcit.library.model.Publisher"%>
<%
	ArrayList<Author> authors = (ArrayList<Author>) request.getAttribute("authors");
	ArrayList<Genre> genres = (ArrayList<Genre>) request.getAttribute("genres");
	ArrayList<Publisher> publishers = (ArrayList<Publisher>) request.getAttribute("publishers");
	
	Book book = (Book) request.getAttribute("book");
	String url = "";
	
	if(book.getTitle().length() == 0) {
		url = LinkHelper.ADD_BOOK;
	} else {
		url = LinkHelper.UPDATE_BOOK;
	}
%>
<div class="container theme-showcase" role="main">
	<div class="jumbotron">
		<h2>Book Info</h2><br><br>
		<form action="<%=url%>" method="post">
			<input name="bookId" type="hidden" value="<%=book.getBookId()%>">
			<label>Book Title</label><br>
			<input name="title" type="text" value="<%=book.getTitle()%>"><br><br>
			<label>Authors</label><br>
			<select name="authors" class="selectpicker" data-live-search="true" multiple>
					<%
						for (Author author : authors) {
					%>
					<%
							if(book.getAuthors() != null && book.getAuthors().contains(author)) {
					%>
								<option name="authorId" value="<%=author.getAuthorId()%>" selected><%=author.getAuthorName()%></option>
					<%
							} else {
					%>
								<option name="authorId" value="<%=author.getAuthorId()%>"><%=author.getAuthorName()%></option>
					<%
							}
					%>
					<%
						}
					%>
			</select><br><br>
			<label>Publisher</label><br>
			<select name="pubId" class="selectpicker" data-live-search="true">
					<%
						for (Publisher publisher : publishers) {
					%>
					<%
							if (book.getPublisher() != null && book.getPublisher().getPublisherId() == publisher.getPublisherId()) {
					%>
								<option value="<%=publisher.getPublisherId()%>" selected><%=publisher.getPublisherName()%></option>
					<%
							} else {
					%>
								<option value="<%=publisher.getPublisherId()%>"><%=publisher.getPublisherName()%></option>
					<%
							}
					%>
					<%
						}
					%>
				</select><br><br>
				<label>Genres</label><br>
				<select name="genres" class="selectpicker" data-live-search="true" multiple>
					<%
							for (Genre genre : genres) {
					%>
					<%
							if (book.getGenres() != null && book.getGenres().contains(genre)) {
					%>
								<option value="<%=genre.getGenreId()%>" selected><%=genre.getGenreName()%></option>
					<%
							} else {
					%>
								<option value="<%=genre.getGenreId()%>"><%=genre.getGenreName()%></option>
					<%
							}
					%>
					<%
						}
					%>
				</select><br><br><br>
			<button class="btn btn-info" type="submit">Save Book</button>
			<a href="<%=LinkHelper.ALL_BOOKS%>" class="btn btn-danger" type="cancel">Cancel</a>
		</form>
	</div>
</div>
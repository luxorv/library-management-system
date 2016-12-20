<%@page import="com.gcit.library.service.AdminService"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.library.model.Book"%>
<%@page import="com.gcit.library.model.Genre"%>
<%@page import="com.gcit.library.model.Author"%>
<%@page import="com.gcit.library.model.Publisher"%>
<%
	Book book;
	Boolean isAdding = (Boolean) request.getAttribute("isAdding");
	ArrayList<Author> authors = (ArrayList<Author>) request.getAttribute("authors");
	ArrayList<Genre> genres = (ArrayList<Genre>) request.getAttribute("genres");
	ArrayList<Publisher> publishers = (ArrayList<Publisher>) request.getAttribute("publishers");
	
	if(!isAdding) {
		book = (Book) request.getAttribute("book");
		
		System.out.println("Book title: " + book.getTitle());
	} else {
		book = new Book();
	}
%>

<form action="books/saveBook" method="post">
	<input name="title" type="text" value="<%= book.getTitle() %>">
	<select name="authors" class="selectpicker" multiple>
		<optgroup label="Authors">
			<% for(Author author: authors) { %>
				<option><%= author.getAuthorName() %></option>
			<% } %>
		</optgroup>
		<optgroup name="publishers" label="Publisher" data-max-options="1">
			<% for(Publisher publisher: publishers) { %>
				<% if(!isAdding && book.getPublisher().getPublisherId() == publisher.getPublisherId()) { %>
					<option selected><%= publisher.getPublisherName() %></option>
				<% } else { %>
					<option><%= publisher.getPublisherName() %></option>
				<% } %>
			<% } %>
		</optgroup>
		<optgroup label="Genres" name="genres">
			<% for(Genre genre: genres) { %>
				<% if(!isAdding && book.getGenres().contains(genre)) { %>
					<option selected><%= genre.getGenreName() %></option>
				<% } else { %>
					<option><%= genre.getGenreName() %></option>
				<% } %>
			<% } %>
		</optgroup>
</select>
<button class="btn btn-info" type="submit">Save Book</button>
</form>
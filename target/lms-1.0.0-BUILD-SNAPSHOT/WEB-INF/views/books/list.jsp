<%@ include file="../template.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
    function queryBooks(pageNumber) {

        $.ajax({
            url: "/lms/books/query",
            data: {
                q: $('#searchInput').val() || "",
                searchBy: $('#searchInput').attr('placeholder') || 'Title',
                pageNo: ''+pageNumber
            }
        }).done(fillTable);
    }

    function fillTable(data) {

        if(data.alert !== null) {
            $('#bookAlert').html(data.alert)
			$('#bookAlert').hide(2000);
		}

        var pageNo = data.pageNo;
        var content = "<table class='table'>";

        content += "<tr><th>#</th><th>Title</th><th>Authors</th><th>Genres</th><th>Publisher</th></tr>";

        for(var i=0;i<data.books.length;i++) {
            content += "<tr>";
            content += "<td>" + ((i + 1) + ((pageNo - 1) * 10)) + "</td>";
            content += "<td><a href='#' data-toggle='modal' data-target='#editBookModal' ";
            content += "onclick='getBookDetails("+data.books[i].bookId+")'>";
            content += data.books[i].title + "</a></td><td>";

            for(var j=0;j<data.books[i].authors.length;j++) {
                content += "<a href='#' data-toggle='modal' data-target='#editBookModal' ";
                content += "onclick='getAuthorDetails("+data.books[i].authors[j][0]+")'>";
                content += data.books[i].authors[j][1] + "</a>";

                if(j < data.books[i].authors.length - 1) {
                    content += ", ";
                }
            }

            content += "</td><td>";

            for(var j=0;j<data.books[i].genres.length;j++) {
                content += data.books[i].genres[j][1];

                if(j < data.books[i].genres.length - 1) {
                    content += ", ";
                }
            }

            content += "</td>";

            content += "<td><a href='#' data-toggle='modal' data-target='#editBookModal' ";
            content += "onclick='getPublisherDetails("+data.books[i].publisher[0]+")'>";
            content += data.books[i].publisher[1]  + "</a></td>";
            content += "</tr>";
        }

        content += "</table>";


        var size = data.fetchSize;
        var pages = 1;

        if(size % 10 > 1) {
            pages = (size/10) + 1;
        } else {
            pages = size/10;
        }

        content += "<nav aria-label='Page navigation'>";
        content += "<ul class='pagination'>";

        for(var i=1;i<=pages;i++) {
            content += "<li><a href='javascript:queryBooks("+i+")'>"+i+"</a></li>";
        }

        content += "</ul></nav>";

        $("#booksTable").html(content);
    }

    function changePlaceholder(queryType) {
        $("#searchInput").attr("placeholder", queryType);
    }
    
    function getBookDetails(bookId) {
        $.ajax({
			url: "/lms/books/view",
			data: {
			    bookId: bookId
			}
		}).done(function (response) {
            $('.editBookModalBody').html(response);
        });
    }

    function getPublisherDetails(publisherId) {
        $.ajax({
            url: "/lms/publishers/view",
            data: {
                publisherId: publisherId
            }
        }).done(function (response) {
            $('.editBookModalBody').html(response);
        });
    }

    function getAuthorDetails(authorId) {
        $.ajax({
            url: "/lms/authors/view",
            data: {
                authorId: authorId
            }
        }).done(function (response) {
            $('.editBookModalBody').html(response);
        });
    }

    queryBooks(1);

</script>

<style>
	.inline {
		display: inline;
	}
	.right {
		float: right;
	}
</style>

<div class="container theme-showcase" role="main">
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<div>
					<br><h2 class="inline">Books</h2>
					<div class="inline right input-group input-group-lg">
						<!-- <span class="input-group-addon" id="sizing-addon1"> -->
						<div>
							<div class="dropdown inline">
								<button class="btn btn-default dropdown-toggle" type="button"
										id="searchBy" data-toggle="dropdown" aria-haspopup="true"
										aria-expanded="true">Search by <span class="caret"></span>
								</button>
								<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
									<li><a href="javascript:changePlaceholder('Title')">Title</a></li>
									<li><a href="javascript:changePlaceholder('Author')">Author</a></li>
									<li><a href="javascript:changePlaceholder('Genre')">Genre</a></li>
									<li><a href="javascript:changePlaceholder('Publisher')">Publisher</a></li>
								</ul>
							</div>
							<div class="inline">
								<input type="text" class="form-control" placeholder="Title"
							   	name="searchString" id="searchInput" onkeydown="queryBooks(1)">
							</div>
						</div>
					</div>
					</div><br><br>
				</div>
				<div class="panel-body">
					<a href='#' data-toggle='modal' data-target='#editBookModal' onclick="getBookDetails(0)">Add New Book</a>
					<div id="bookAlert"></div>
				</div>
				<div id="booksTable"></div>

			</div>
		</div>
</div>

<div class="modal fade bs-example-modal-lg" id="editBookModal" role="dialog">
	    <div class="modal-dialog modal-lg" role="document">
	        <div class="modal-content">
	            <div class="editBookModalBody">

	            </div>
	        </div>
	    </div>
</div>

<!-- 
books: [{
		title: "New title",
		authors: [
			[3, "JK Rowling"],
			[5, "New Author"]
		],
		genres: [
			[1, "Romance"],
			[4, "Horror"]
		],
		publisher: [9, "Best Publisher"]
}, {
		title: "The Reckoning",
		authors: [
			[3, "JK Rowling"],
			[5, "New Author"]
		],
		genres: [
			[1, "Romance"],
			[4, "Horror"]
		],
		publisher: [9, "Best Publisher"]
}]

publishers: [{
	publisherName: "name",
	publisherId: 3,
	books: [{
		title: "New title",
		authors: [
			[3, "JK Rowling"],
			[5, "New Author"]
		],
		genres: [
			[1, "Romance"],
			[4, "Horror"]
		],
	}, {
		title: "New title",
		authors: [
			[3, "JK Rowling"],
			[5, "New Author"]
		],
		genres: [
			[1, "Romance"],
			[4, "Horror"]
		],
	}]
}]
-->

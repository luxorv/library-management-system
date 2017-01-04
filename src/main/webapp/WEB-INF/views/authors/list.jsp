<%@ include file="../template.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
    function queryAuthors(pageNumber) {

        $.ajax({
            url: "/lms/authors/query",
            data: {
                q: $('#searchInput').val() || "",
                pageNo: ''+pageNumber
            }
        }).done(fillTable);
    }

    function fillTable(data) {

        if(data.alert !== null) {
            $('#authorAlert').show();
            $('#authorAlert').html(data.alert);
        }

        console.log(data);

        var pageNo = data.pageNo;
        var content = "";
        var bookLimit = 2;

        content += "<table class='table'>";
        content += "<tr><th>#</th><th>Author Name</th><th>Books</th></tr>";

        for(var i=0;i<data.authors.length;i++) {
            content += "<tr>";
            content += "<td>" + ((i + 1) + ((pageNo - 1) * 10)) + "</td>";
            content += "<td><a href='#' data-toggle='modal' data-target='#editAuthorModal' ";
            content += "onclick='getAuthorDetails("+data.authors[i].authorId+")'>";
            content += data.authors[i].authorName + "</a></td><td>";

            for(var j=0;j<data.authors[i].books.length;j++) {
                content += "<a href='#' data-toggle='modal' data-target='#editAuthorModal' ";
                content += "onclick='getBookDetails("+data.authors[i].books[j][0]+")'>";
                content += data.authors[i].books[j][1] + "</a>";

                if(j === bookLimit) {
                    content += "<a href='/lms/books'>, ...</a>";
                    break;
                }

                if(j < data.authors[i].books.length - 1) {
                    content += ", ";
                }
            }

            content += "</td></tr>";
        }

        content += "</table>";


        var size = data.fetchSize;
        var pages = 1;

        if(size % 10 > 0) {
            pages = Math.floor(size/10) + 1;
        } else {
            pages = Math.floor(size/10);
        }

        content += "<nav aria-label='Page navigation'>";
        content += "<ul class='pagination'>";

        for(var i=1;i<=pages;i++) {
            content += "<li><a href='javascript:queryAuthors("+i+")'>"+i+"</a></li>";
        }

        content += "</ul></nav>";

        $("#authorsTable").html(content);
    }

    function getAuthorDetails(authorId) {
        $.ajax({
            url: "/lms/authors/view",
            data: {
                authorId: authorId
            }
        }).done(function (response) {
            $('.editAuthorModalBody').html(response);
        });
    }

    function getBookDetails(bookId) {
        $.ajax({
            url: "/lms/books/view",
            data: {
                bookId: bookId
            }
        }).done(function (response) {
            $('.editAuthorModalBody').html(response);
        });
    }

    queryAuthors(1);

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
                <br><h2 class="inline">Authors</h2>
                <div class="right input-group input-group-lg">
                    <input type="text" class="form-control" placeholder="Name"
                           name="searchString" id="searchInput" onkeydown="queryAuthors(1)">
                </div>
            </div><br><br>
        </div>
        <div class="panel-body">
            <a href='#' data-toggle='modal' data-target='#editAuthorModal' onclick="getAuthorDetails(0)">Add New Author</a>
            <div id="authorAlert"></div>
        </div>
        <ul id="authorsTable"></ul>
    </div>
</div>
</div>

<div class="modal fade bs-example-modal-lg" id="editAuthorModal" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="editAuthorModalBody">

                </div>
            </div>
        </div>
</div>
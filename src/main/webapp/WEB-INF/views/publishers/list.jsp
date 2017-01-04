<%@ include file="../template.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
    function queryPublishers(pageNumber) {

        $.ajax({
            url: "/lms/publishers/query",
            data: {
                q: $('#searchInput').val() || "",
                pageNo: ''+pageNumber
            }
        }).done(fillTable);
    }

    function fillTable(data) {

        console.log(data);

        if(data.alert !== null) {
            $('#publisherAlert').show();
            $('#publisherAlert').html(data.alert);
        }

        console.log(data);

        var pageNo = data.pageNo;
        var content = "";
        var bookLimit = 2;

        content += "<table class='table'>";
        content += "<tr><th>#</th><th>Publisher Name</th><th>Publisher Address</th><th>Publisher Phone</th><th>Books</th></tr>";

        for(var i=0;i<data.publishers.length;i++) {
            content += "<tr>";
            content += "<td>" + ((i + 1) + ((pageNo - 1) * 10)) + "</td>";
            content += "<td><a href='#' data-toggle='modal' data-target='#editPublisherModal' ";
            content += "onclick='getPublisherDetails("+data.publishers[i].publisherId+")'>";
            content += data.publishers[i].publisherName + "</a></td>";
            content += "<td>"+data.publishers[i].publisherAddress + "</td>";
            content += "<td>"+data.publishers[i].publisherPhone + "</td><td>";

            for(var j=0;j<data.publishers[i].books.length;j++) {
                content += "<a href='#' data-toggle='modal' data-target='#editPublisherModal' ";
                content += "onclick='getBookDetails("+data.publishers[i].books[j][0]+")'>";
                content += data.publishers[i].books[j][1] + "</a>";

                if(j === bookLimit) {
                    content += "<a href='/lms/books'>, ...</a>";
                    break;
                }

                if(j < data.publishers[i].books.length - 1) {
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
            content += "<li><a href='javascript:queryPublishers("+i+")'>"+i+"</a></li>";
        }

        content += "</ul></nav>";

        $("#publishersTable").html(content);
    }

    function getPublisherDetails(publisherId) {
        $.ajax({
            url: "/lms/publishers/view",
            data: {
                publisherId: publisherId
            }
        }).done(function (response) {
            $('.editPublisherModalBody').html(response);
        });
    }

    function getBookDetails(bookId) {
        $.ajax({
            url: "/lms/books/view",
            data: {
                bookId: bookId
            }
        }).done(function (response) {
            $('.editPublisherModalBody').html(response);
        });
    }

    queryPublishers(1);

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
                <h2 class="inline">Publishers</h2>
                <div class="right input-group input-group-lg">
                    <input type="text" class="form-control" placeholder="Name"
                           name="searchString" id="searchInput" onkeyup="queryPublishers(1)">
                </div>
            </div><br><br>
        </div>
        <div class="panel-body">
            <a href='#' data-toggle='modal' data-target='#editPublisherModal' onclick="getPublisherDetails(0)">Add New Publisher</a>
            <div id="publisherAlert"></div>
        </div>
        <ul id="publishersTable"></ul>
    </div>
</div>
</div>

<div class="modal fade bs-example-modal-lg" id="editPublisherModal" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="editPublisherModalBody">

                </div>
            </div>
        </div>
</div>
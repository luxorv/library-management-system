<%@ include file="template.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
    function queryBookCopies(pageNumber) {

        var branchId = $('option:selected').val();

        console.log(branchId);

        $.ajax({
            url: "/lms/librarian/query",
            data: {
                q: $('#searchInput').val() || "",
                branchId: branchId,
                pageNo: ''+pageNumber
            }
        }).done(fillTable);
    }

    function fillTable(data) {

        console.log(data);

        if(data.alert !== null) {
            $('#copiesAlert').html(data.alert)
            $('#copiesAlert').hide(2000);
        }

        var pageNo = data.pageNo;
        var content = "<table class='table'>";

        content += "<tr><th>#</th><th>Book</th><th>Branch</th><th>No Of Copies</th></tr>";

        for(var i=0;i<data.copies.length;i++) {
            content += "<tr>";
            content += "<td>" + ((i + 1) + ((pageNo - 1) * 10)) + "</td>";
            content += "<td>" + data.copies[i].book[1] + "</td>";
            content += "<td><a href='#' data-toggle='modal' data-target='#editCopiesModal' ";
            content += "onclick='getBranchDetails("+data.copies[i].branch[0]+")'>";
            content += data.copies[i].branch[1]+"<a/></td>";
            content += "<td><a href='#' data-toggle='modal' data-target='#editCopiesModal' ";
            content += "onclick='getCopiesDetails("+data.copies[i].branch[0]+", "+data.copies[i].book[0]+")'>";
            content += data.copies[i].noOfCopies+"</td>";
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
            content += "<li><a href='javascript:queryBookCopies("+i+")'>"+i+"</a></li>";
        }

        content += "</ul></nav>";

        $("#copiesTable").html(content);
    }

    function getCopiesDetails(branchId, bookId) {

        if(branchId == 0) {
            branchId = $('option:selected').val();
        }

        $.ajax({
            url: "/lms/librarian/view",
            data: {
                bookId: bookId,
                branchId: branchId
            }
        }).done(function (response) {
            $('.editCopiesModalBody').html(response);
        });
    }

    function getBookDetails(bookId) {
        $.ajax({
            url: "/lms/books/view",
            data: {
                bookId: bookId
            }
        }).done(function (response) {
            $('.editCopiesModalBody').html(response);
        });
    }

    function getBranchDetails(branchId) {
        $.ajax({
            url: "/lms/branches/view",
            data: {
                branchId: branchId
            }
        }).done(function (response) {
            $('.editCopiesModalBody').html(response);
        });
    }

    $(document).ready(function(){
        queryBookCopies(1);
    });

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
                <br><h3 class="inline">Libraries</h3><br><br><br>
                <div class="right input-group input-group-lg">
                    <select onchange="queryBookCopies(1)" name="branchId" class="selectpicker" data-live-search="true">
                        <c:forEach var="branch" items="${branches}">
                            <option value="${branch.getBranchId()}">${branch.getBranchName()} </option>
                        </c:forEach>
                    </select>
                    <input type="text" class="form-control" placeholder="Title"
                           name="searchString" id="searchInput" onkeydown="queryBookCopies(1)">
                </div><br>
            </div><br><br>
        </div>
        <div class="panel-body">
            <a href='#' data-toggle='modal' data-target='#editCopiesModal' onclick="getCopiesDetails(0, 0)">Add New Copies</a>
            <div id="copiesAlert"></div>
        </div>
        <div id="copiesTable"></div>

    </div>
</div>
</div>

<div class="modal fade bs-example-modal-lg" id="editCopiesModal" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="editCopiesModalBody">

                </div>
            </div>
        </div>
</div>
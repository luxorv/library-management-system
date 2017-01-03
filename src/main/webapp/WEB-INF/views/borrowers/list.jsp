<%@ include file="../template.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
    function queryBorrowers(pageNumber) {

        $.ajax({
            url: "/lms/borrowers/query",
            data: {
                q: $('#searchInput').val() || "",
                pageNo: ''+pageNumber
            }
        }).done(fillTable);
    }

    function fillTable(data) {
        console.log(data);

        if(data.alert !== null) {
            $('#borrowerAlert').html(data.alert)
            $('#borrowerAlert').hide(3000);
        }

        console.log(data);

        var pageNo = data.pageNo;
        var content = "";

        content += "<table class='table'>";
        content += "<tr><th>#</th><th>Borrower Name</th><th>Borrower Address</th><th>Borrower Phone</th></tr>";

        for(var i=0;i<data.borrowers.length;i++) {
            content += "<tr>";
            content += "<td>" + ((i + 1) + ((pageNo - 1) * 10)) + "</td>";
            content += "<td><a href='#' data-toggle='modal' data-target='#editBorrowerModal' ";
            content += "onclick='getBorrowerDetails("+data.borrowers[i].cardNo+")'>";
            content += data.borrowers[i].borrowerName + "</a></td>";
            content += "<td>"+data.borrowers[i].borrowerAddress + "</td>";
            content += "<td>"+data.borrowers[i].borrowerPhone + "</td></tr>";
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
            content += "<li><a href='javascript:queryBorrowers("+i+")'>"+i+"</a></li>";
        }

        content += "</ul></nav>";

        $("#borrowersTable").html(content);
    }

    function getBorrowerDetails(cardNo) {
        $.ajax({
            url: "/lms/borrowers/view",
            data: {
                cardNo: cardNo
            }
        }).done(function (response) {
            $('.editBorrowerModalBody').html(response);
        });
    }

    queryBorrowers(1);

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
                <br><h2 class="inline">Borrowers</h2>
                <div class="right input-group input-group-lg">
                    <input type="text" class="form-control" placeholder="Name"
                           name="searchString" id="searchInput" onkeydown="queryBorrowers(1)">
                </div>
            </div><br><br>
        </div>
        <div class="panel-body">
            <a href='#' data-toggle='modal' data-target='#editBorrowerModal' onclick="getBorrowerDetails(0)">Add New Borrower</a>
            <div id="borrowerAlert"></div>
        </div>
        <ul id="borrowersTable"></ul>
    </div>
</div>
</div>

<div class="modal fade bs-example-modal-lg" id="editBorrowerModal" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="editBorrowerModalBody">

                </div>
            </div>
        </div>
</div>
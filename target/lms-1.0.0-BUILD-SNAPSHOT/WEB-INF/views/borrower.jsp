<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="template.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    
    function queryBookLoans(pageNo, pageNo1) {
        var cardNo = $('#cardNo').val() || $('#hiddenCardNo').val();
        var branchId = $('option:selected').val();

        $.ajax({
            url: "/lms/borrowers/queryloans",
            data: {
                cardNo: cardNo,
                branchId: branchId,
                q: $('#bookSearchInput').val() || "",
                q1: $('#loanSearchInput').val() || "",
                pageNo: pageNo,
                pageNo1: pageNo1
            }
        }).done(function (data) {

            if(data.cardNotValidAlert) {
                console.log(data.cardNotValidAlert);
                $('#cardAlert').html(data.cardNotValidAlert);
                return;
            }

            if(data.cardOkAlert) {
                $('#cardAlert').html(data.cardOkAlert);
            }

            var books = data.books;
            var loans = data.loans;

            $('#loansTable').show();
            $('#booksTable').show();
            $('#loanHeader').show();
            $('#bookHeader').show();

            if(!books || books.length === 0) {
                $('#booksTable').hide();
                $('#bookHeader').hide();
            }

            if(!loans || loans.length === 0) {
                $('#loansTable').hide();
                $('#loanHeader').hide();
            }

            $('#hiddenCardNo').val(data.cardNo);

            var bookContent = "<table class='table'>";

            bookContent += "<tr><th>#</th><th>Title</th><th>Authors</th><th>Genres</th><th>Publisher</th><th>Loan Book</th></tr>";

            for(var i=0;i<books.length;i++) {
                bookContent += "<tr>";
                bookContent += "<td>" + ((i + 1) + ((pageNo - 1) * 10)) + "</td>";
                bookContent += "<td>"+books[i].title + "</td><td>";

                var authorLimit = 1;

                for(var j=0;j<books[i].authors.length;j++) {
                    bookContent += data.books[i].authors[j][1];

                    if(j >= authorLimit) {
                        bookContent += ", ...";
                        break;
                    }

                    if(j < data.books[i].authors.length - 1) {
                        bookContent += ", ";
                    }
                }

                bookContent += "</td><td>";

                for(var j=0;j<books[i].genres.length;j++) {
                    bookContent += books[i].genres[j][1];

                    if(j < books[i].genres.length - 1) {
                        bookContent += ", ";
                    }
                }

                var publisherName = books[i].publisher[1];

                if(publisherName.length > 10) {
                    publisherName = publisherName.substring(0, 7);
                    publisherName += "...";
                }

                bookContent += "</td>";
                bookContent += "<td>"+ publisherName  + "</td>";
                bookContent += "<td><a href='#' class='btn btn-primary active'";
                bookContent += "onclick='loanBook("+cardNo+", "+ branchId + ", "+ books[i].bookId + ")'>Loan</a></td>";
                bookContent += "</tr>";
            }

            bookContent += "</table>";

            var loanContent = "<table class='table'><tr>";
            loanContent += "<th>Book</th><th>Due Date</th><th>Return Book</th>";
            loanContent += "</tr>";

            for(var i=0;i<loans.length;i++) {
                loanContent += "<tr>";

                loanContent += "<td>"+loans[i].book.title+"</td>";
                loanContent += "<td>"+loans[i].dueDate+"</td>";
                loanContent += "<td><a href='#' class='btn btn-primary active'";
                loanContent += "onclick='returnBook("+cardNo+", "+ branchId + ", "+ loans[i].book.bookId + ")'>Return</a></td>";
                loanContent += "</tr>";
            }

            $('#loansTable').html(loanContent);
            $('#booksTable').html(bookContent);
        });
    }
    
    function loanBook(cardNo, branchId, bookId) {
        $.ajax({
            url: "/lms/borrowers/loan",
            data: {
                cardNo: cardNo,
                branchId: branchId,
                bookId: bookId
            }
        }).done(function (data) {
            $('#loanOperationAlert').html(data);
            queryBookLoans(1, 1);
        });
    }

    function returnBook(cardNo, branchId, bookId) {
        $.ajax({
            url: "/lms/borrowers/return",
            data: {
                cardNo: cardNo,
                branchId: branchId,
                bookId: bookId
            }
        }).done(function (data) {
            $('#loanOperationAlert').html(data);
            queryBookLoans(1, 1);
        });
    }

</script>
<div class="container" style="min-width: 80%;" role="main">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Enter your Info</h3>
        </div>
        <div class="panel-body">
            <div id="cardAlert"></div>
            <div class="row">
                <div class="col-sm-3"><label>Branch: </label>
                    <select onchange="queryBookLoans(1, 1)" name="branchId" class="selectpicker" data-live-search="true">
                        <c:forEach var="branch" items="${branches}">
                            <option value="${branch.getBranchId()}">${branch.getBranchName()} </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-3">
                    <input type="hidden" id="hiddenCardNo">
                    <input type="text" id="cardNo" placeholder="Card Number" class="form-control" aria-label="...">
                </div>
                <div class="col-sm-6">
                    <a class="btn btn-primary active" href="#" onclick="queryBookLoans(1, 1)">Validate</a>
                </div>
            </div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Loans and Books</h3>
        </div>
        <div class="panel-body">
            <div class="row">
                <div id="loanOperationAlert"></div>
                <div id="loanHeader" class="col-md-4"><h4>Loans</h4></div>
                <div id="bookHeader" class="col-md-8"><h4>Books</h4></div>
            </div>
        </div>
        <hr>
        <div class="row">
            <div id="loansTable" class="col-md-4"></div>
            <div id="booksTable" class="col-md-8"></div>
        </div>
    </div>
</div>
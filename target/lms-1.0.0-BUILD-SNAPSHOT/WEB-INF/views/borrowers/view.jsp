<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>

    .buttons {
        float: right;
    }

    .inline {
        display: inline;
    }

</style>

<script>
    $(document).ready(function () {
        $('.selectpicker').selectpicker({
            style: 'btn-info',
            size: 4
        });
    });

    function updateLoan(cardNo, branchId, bookId) {
        var updatedDate = $('#dueDateInput'+cardNo+'-'+branchId+'-'+bookId).val();

        $.ajax({
            url: "borrowers/updateloan",
            data: {
                cardNo: cardNo,
                bookId: bookId,
                branchId: branchId,
                dueDate: updatedDate
            }
        }).done(function (data) {
            $('#operationAlert').show();
            $('#operationAlert').html(data);
        });
    }
</script>

<div class="container theme-showcase" role="main">
    <c:choose>
    <c:when test="${borrower == null || borrower.getCardNo() == null}">
    <form action="borrowers/create" method="post">
        </c:when>
        <c:otherwise>
        <form action="borrowers/update" method="post">
            </c:otherwise>
            </c:choose>
            <div class="panel panel-default" style="width: 850px;">
                <div class="panel-heading">
                    <div>
                        <h3 class="inline">Borrower Detail</h3>
                        <div class="inline buttons">
                            <button class="btn btn-primary active" type="submit">Save Borrower</button>
                            <c:if  test="${borrower != null || borrower.getCardNo() != null}">
                                <a href="borrowers/delete?cardNo=${borrower.getCardNo()}" type="button" class="btn btn-danger active">Delete</a>
                            </c:if>
                        </div>
                    </div><br>

                </div>
                <div class="panel-body">
                </div>
                <ul class="list-group">
                    <li class="list-group-item"><h4>Name: </h4>
                        <input name="cardNo" type="hidden" class="form-control" value="${borrower.getCardNo()}">
                        <input name="borrowerName" type="text" class="form-control" placeholder="Name" value="${borrower.getName()}">
                    </li>
                    <li class="list-group-item"><h4>Address: </h4>
                        <input name="borrowerAddress" type="text" class="form-control" placeholder="Address" value="${borrower.getAddress()}">
                    </li>
                    <li class="list-group-item"><h4>Phone: </h4>
                        <input name="borrowerPhone" type="text" class="form-control" placeholder="Phone" value="${borrower.getPhone()}">
                    </li>
                    <c:if test="${loans != null && loans.size() > 0}">
                    <li class="list-group-item"><h4>Loans: </h4><br>
                        <div id="operationAlert"></div>
                        <table class="table">
                            <tr>
                                <th>Book</th>
                                <th>Branch</th>
                                <th>Date Out</th>
                                <th>Due Date</th>
                                <th>Update Loan</th>
                            </tr>

                            <c:forEach var="loan" items="${loans}">
                            <tr>
                                <td>${loan.getBook().getTitle()}</td>
                                <td>${loan.getBranch().getBranchName()}</td>
                                <td>${loan.getDateOut()}</td>
                                <td><input type="date" id="dueDateInput${borrower.getCardNo()}-${loan.getBranch().getBranchId()}-${loan.getBook().getBookId()}" name="dueDate" value="${loan.getDueDate()}"></td>
                                <input type="hidden" name="bookId" value="${loan.getBook().getBookId()}">
                                <td><a href="#" class="btn btn-primary active" onclick="updateLoan(${borrower.getCardNo()}, ${loan.getBranch().getBranchId()}, ${loan.getBook().getBookId()})">Update Due Date</a></td>
                            <tr>
                            </c:forEach>
                        </table>
                    </li>
                    </c:if>
                </ul>
            </div>
        </form>
</div>
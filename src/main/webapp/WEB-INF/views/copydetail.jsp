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
</script>

<div class="container theme-showcase" role="main">
    <c:choose>
        <c:when test="${books != null}">
            <form action="librarian/create" method="post">
        </c:when>
        <c:otherwise>
            <form action="librarian/update" method="post">
        </c:otherwise>
    </c:choose>
            <div class="panel panel-default" style="width: 850px;">
                <div class="panel-heading">
                    <div>
                        <h3 class="inline">Book Copies Detail</h3>
                        <div class="inline buttons">
                            <button class="btn btn-primary active" type="submit">Save Copies</button>
                        </div>
                    </div><br>
                </div>
                <div class="panel-body">
                </div>
                <ul class="list-group">
                    <c:choose>
                        <c:when test="${books != null}">
                            <li class="list-group-item"><h4>Books: </h4>
                                <select name="bookId" class="selectpicker" data-live-search="true">
                                    <c:forEach var="book" items="${books}">
                                        <option value="${book.getBookId()}">${book.getTitle()}</option>
                                    </c:forEach>
                                </select>
                            </li>
                            <li class="list-group-item"><h4>No Of Copies: </h4>
                                <input name="branchId" type="hidden" value="${copy.getBranch().getBranchId()}">
                                <input name="noOfCopies" type="number" class="form-control" placeholder="No Of Copies" value="${copy.getNoOfCopies()}">
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="list-group-item"><h4>No Of Copies: </h4>
                                <input name="bookId" type="hidden" class="form-control" value="${copy.getBook().getBookId()}">
                                <input name="branchId" type="hidden" class="form-control" value="${copy.getBranch().getBranchId()}">
                                <input name="noOfCopies" type="number" class="form-control" placeholder="No Of Copies" value="${copy.getNoOfCopies()}">
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </form>
</div>
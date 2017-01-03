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
        <c:when test="${branch == null || branch.getBranchId() == null}">
            <form action="branches/create" method="post">
        </c:when>
        <c:otherwise>
            <form action="branches/update" method="post">
        </c:otherwise>
    </c:choose>
            <div class="panel panel-default" style="width: 850px;">
                <div class="panel-heading">
                    <div>
                        <h3 class="inline">Branch Detail</h3>
                        <div class="inline buttons">
                            <button class="btn btn-primary active" type="submit">Save Branch</button>
                            <c:if  test="${branch != null || branch.getBranchId() != null}">
                                <a href="branches/delete?branchId=${branch.getBranchId()}" type="button" class="btn btn-danger active">Delete</a>
                            </c:if>
                        </div>
                    </div><br>

                </div>
                <div class="panel-body">
                </div>
                <ul class="list-group">
                    <li class="list-group-item"><h4>Name: </h4>
                        <input name="branchId" type="hidden" class="form-control" value="${branch.getBranchId()}">
                        <input name="branchName" type="text" class="form-control" placeholder="Name" value="${branch.getBranchName()}">
                    </li>
                    <li class="list-group-item"><h4>Address: </h4>
                        <input name="branchAddress" type="text" class="form-control" placeholder="Address" value="${branch.getBranchAddress()}">
                    </li>
                </ul>
            </div>
        </form>
</div>
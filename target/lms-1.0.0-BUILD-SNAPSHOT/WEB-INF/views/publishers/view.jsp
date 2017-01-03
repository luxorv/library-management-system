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
    <c:when test="${publisher == null || publisher.getPublisherId() == null}">
    <form action="publishers/create" method="post">
        </c:when>
        <c:otherwise>
        <form action="publishers/update" method="post">
            </c:otherwise>
            </c:choose>
            <div class="panel panel-default" style="width: 850px;">
                <div class="panel-heading">
                    <div>
                        <h3 class="inline">Publisher Detail</h3>
                        <div class="inline buttons">
                            <button class="btn btn-primary active" type="submit">Save Publisher</button>
                            <c:if  test="${publisher != null || publisher.getPublisherId() != null}">
                                <a href="publishers/delete?publisherId=${publisher.getPublisherId()}" type="button" class="btn btn-danger active">Delete</a>
                            </c:if>
                        </div>
                    </div><br>

                </div>
                <div class="panel-body">
                </div>
                <ul class="list-group">
                    <li class="list-group-item"><h4>Name: </h4>
                        <input name="publisherId" type="hidden" class="form-control" value="${publisher.getPublisherId()}">
                        <input name="publisherName" type="text" class="form-control" placeholder="Name" value="${publisher.getPublisherName()}">
                    </li>
                    <li class="list-group-item"><h4>Address: </h4>
                        <input name="publisherAddress" type="text" class="form-control" placeholder="Address" value="${publisher.getPublisherAddress()}">
                    </li>
                    <li class="list-group-item"><h4>Phone: </h4>
                        <input name="publisherPhone" type="text" class="form-control" placeholder="Phone" value="${publisher.getPublisherPhone()}">
                    </li>
                </ul>
            </div>
        </form>
</div>
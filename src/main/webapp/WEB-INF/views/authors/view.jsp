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
        <c:when test="${author == null || author.getAuthorId() == null}">
            <form action="authors/create" method="post">
        </c:when>
        <c:otherwise>
            <form action="authors/update" method="post">
        </c:otherwise>
    </c:choose>
            <div class="panel panel-default" style="width: 850px;">
                <div class="panel-heading">
                    <div>
                        <h3 class="inline">Author Detail</h3>
                        <div class="inline buttons">
                            <button class="btn btn-primary active" type="submit">Save Author</button>
                            <c:if  test="${author != null || author.getAuthorId() != null}">
                                <a href="authors/delete?authorId=${author.getAuthorId()}" type="button" class="btn btn-danger active">Delete</a>
                            </c:if>
                        </div>
                    </div><br>

                </div>
                <div class="panel-body">
                </div>
                <ul class="list-group">
                    <li class="list-group-item"><h4>Name: </h4>
                        <input name="authorId" type="hidden" class="form-control" value="${author.getAuthorId()}">
                        <input name="authorName" type="text" class="form-control" placeholder="Name" value="${author.getAuthorName()}">
                    </li>
                    <li class="list-group-item"><h4>Books</h4>
                        <select name="books" class="selectpicker" data-live-search="true" multiple>
                            <c:forEach var="book" items="${books}">
                                <c:choose>
                                    <c:when test="${author.getBooks().contains(book)}">
                                        <option value="${book.getBookId()}" selected>${book.getTitle()}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${book.getBookId()}">${book.getTitle()}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </li>
                </ul>
            </div>
        </form>
</div>
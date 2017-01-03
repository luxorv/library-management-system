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
        <c:when test="${book == null || book.getBookId() == null}">
            <form action="books/create" method="post">
        </c:when>
        <c:otherwise>
            <form action="books/update" method="post">
        </c:otherwise>
    </c:choose>
    
        <div class="panel panel-default" style="width: 850px;">
            <div class="panel-heading">
                <div>
                    <h3 class="inline">Book Detail</h3>
                    <div class="inline buttons">
                        <button class="btn btn-primary active" type="submit">Save Book</button>
                        <c:if test="${book != null && book.getBookId() != 0}">
                            <a href="books/delete?bookId=${book.getBookId()}" type="button" class="btn btn-danger active">Delete</a>
                        </c:if>
                    </div>
                </div><br>

            </div>
            <div class="panel-body">
            </div>
            <ul class="list-group">
                <li class="list-group-item"><h4>Title: </h4>
                    <input name="bookId" type="hidden" class="form-control" value="${book.getBookId()}">
                    <input name="title" type="text" class="form-control" placeholder="Title" value="${book.getTitle()}">
                </li>
                <li class="list-group-item"><h4>Authors</h4>
                        <select name="authors" class="selectpicker" data-live-search="true" multiple>
                            <c:forEach var="author" items="${authors}">
                                <c:choose>
                                    <c:when test="${book.getAuthors().contains(author)}">
                                        <option name="authorId" value="${author.getAuthorId()}" selected>${author.getAuthorName()}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option name="authorId" value="${author.getAuthorId()}">${author.getAuthorName()}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                </li>
                <li class="list-group-item"><h4>Genres</h4>
                        <select name="genres" class="selectpicker" data-live-search="true" multiple>
                            <c:forEach var="genre" items="${genres}">
                                <c:choose>
                                    <c:when test="${book.getGenres().contains(genre)}">
                                        <option value="${genre.getGenreId()}" selected>${genre.getGenreName()}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${genre.getGenreId()}">${genre.getGenreName()}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    <br>
                </li>

                <li class="list-group-item"><h4>Publisher</h4>
                        <select name="publisherId" class="selectpicker" data-live-search="true">
                            <c:forEach var="publisher" items="${publishers}">
                                <c:choose>
                                    <c:when test="${book.getPublisher().getPublisherId() == publisher.getPublisherId()}">
                                        <option value="${publisher.getPublisherId()}" selected>${publisher.getPublisherName()}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${publisher.getPublisherId()}">${publisher.getPublisherName()}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    <br>
                </li>
            </ul>
        </div>
    </form>
</div>
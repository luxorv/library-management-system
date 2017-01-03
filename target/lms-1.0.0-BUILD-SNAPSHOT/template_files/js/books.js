function queryBooks(pageNumber) {

    $.ajax({
        url: "query",
        data: {
            q: $('#searchInput').val() || "",
            searchBy: $('#searchInput').attr('placeholder') || 'Title',
            pageNo: ''+pageNumber
        }
    }).done(fillTable);
}

function fillTable(data) {

    var pageNo = data.pageNo;
    var content = "<table class='table'>";

    content += "<tr><th>#</th><th>Title</th><th>Authors</th><th>Genres</th><th>Publisher</th></tr>";

    for(var i=0;i<data.books.length;i++) {
        content += "<tr>";
        content += "<td>" + ((i + 1) + ((pageNo - 1) * 10)) + "</td>";
        content += "<td><a href='/lms/books/view?bookId="+ data.books[i].bookId +"'>"
        content += data.books[i].title + "</a></td><td>";

        for(var j=0;j<data.books[i].authors.length;j++) {
            content += "<a href='/lms/authors/view?authorId="+ data.books[i].authors[j][0] +"'>";
            content += data.books[i].authors[j][1] + "</a>";

            if(j < data.books[i].authors.length - 1) {
                content += ", ";
            }
        }

        content += "</td><td>";

        for(var j=0;j<data.books[i].genres.length;j++) {
            content += "<a href='/lms/genres/view?gerneId="+ data.books[i].genres[j][0] +"'>";
            content += data.books[i].genres[j][1] + "</a>";

            if(j < data.books[i].genres.length - 1) {
                content += ", ";
            }
        }

        content += "</td>";

        content += "<td><a href='/lms/publishers/view?publisherId=" + data.books[i].publisher + "'>";
        content += data.books[i].publisher[1]  + "</a></td>";
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
        content += "<li><a href='javascript:queryBooks("+i+")'>"+i+"</a></li>";
    }

    content += "</ul></nav>";

    $("#booksTable").html(content);
}

function changePlaceholder(queryType) {
    $("#searchInput").attr("placeholder", queryType);
}

queryBooks(1);

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <spring:url value="/template_files/bootstrap.min.css" var="bootstrapMinCSS" />
    <spring:url value="/template_files/bootstrap-theme.min.css" var="bootstrapThemeMinCSS" />
    <spring:url value="/template_files/theme.css" var="themeMinCSS" />

    <title>LMS</title>

    <!-- Bootstrap core CSS -->
    <link href="${bootstrapMinCSS}" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="${bootstrapThemeMinCSS}" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${themeMinCSS}" rel="stylesheet">

  </head>

  <body>

    <!-- Fixed navbar -->
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/lms">LMS</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-left">
            <li><a href="/lms/admin">Admin</a></li>
            <li><a href="/lms/librarian">Librarian</a></li>
            <li><a href="/lms/borrower">Borrower</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

    <spring:url value="/template_files/jquery.min.js.download" var="jqueryMinDownloadJs" />
    <spring:url value="/template_files/bootstrap.min.js.download" var="bootstrapMinDownloadJs" />
    <spring:url value="/template_files/docs.min.js.download" var="docsMinDownloadJs" />

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${jqueryMinDownloadJs}"></script>
    <script src="${bootstrapMinDownloadJs}"></script>
    <script src="${docsMinDownloadJs}"></script>

	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/css/bootstrap-select.min.css"/>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/bootstrap-select.min.js"></script>
	  

</body></html>
<%@include file="template.jsp"%>

<spring:url value="/template_files/img/user.png" var="adminImg" />
<spring:url value="/template_files/img/librarian.png" var="librarianImg" />
<spring:url value="/template_files/img/borrower.png" var="borrowerImg" />

<style>
	<!--
	.colm {
		display: inline;
	}

	.role {
		max-height: 200px;
		max-width: 100px;
	}
	-->
</style>
<div class="container cont theme-showcase" role="main">
	<div class="jumbotron">
		<div class="row text-center">
			<h2>GCIT Library Management System</h2><br>
			<p>Welcome to GCIT Library Management System. Have Fun Reading!</p>
			<br>
			<br>
			<br>

			<div class="row ">
				<div class="col-xs-12 col-md-4">
					<label>Admin</label>
					<a href="/lms/admin" class="thumbnail">
						<img  class="role" src="${adminImg}" alt="">
					</a>
				</div>

				<div class="col-xs-12 col-md-4">
					<label>Borrower</label>
					<a href="/lms/borrower" class="thumbnail">
						<img class="role" src="${borrowerImg}" alt="">
					</a>
				</div>
				<div class="col-xs-12 col-md-4">
					<label>Librarian</label>
					<a href="/lms/librarian" class="thumbnail">
						<img  class="role" src="${librarianImg}" alt="">
					</a>
				</div>
			</div>
		</div>
	</div>
</div>

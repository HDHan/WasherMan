<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html lang="en">
<head>

<!-- Basic Page Needs
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<meta charset="utf-8">
<title>Your page title here :)</title>
<meta name="description" content="">
<meta name="author" content="">

<!-- Mobile Specific Metas
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- FONT
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<link href="http://fonts.googleapis.com/css?family=Raleway:400,300,600"
	rel="stylesheet" type="text/css">

<!-- CSS
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<link rel="stylesheet" href="<c:url value="/css/normalize.css"/>">
<link rel="stylesheet" href="<c:url value="/css/skeleton.css"/>">
<link rel="stylesheet" href="<c:url value="/css/custom.css"/>">

<!-- Favicon
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<link rel="icon" type="image/png" href="/images/favicon.png">

</head>
<body>

	<!-- Primary Page Layout
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
	<div class="container">
		<section class="row header">
			<h1>Login</h1>
		</section>
		<div class="docs-section">
			<div style="text-align: center;">
				<p>
					Not a member yet? Please <a href="<c:url value="/User/Register/"/>">join
						us</a>.
				</p>
				<p>
					${result}
				</p>
				<form method="post" action="<c:url value="/User/Login/"/>">
					<div>
						<label class="field-label">Id</label> <input id="id" name="id"
							type="text" required="required">
					</div>
					<div>
						<label class="field-label">Password</label> <input id="password"
							name="password" type="password" required="required"
							autocomplete="off">
					</div>
					<input class="button" type="submit" value="Log in"> <a
						class="button" href="<c:url value="/"/>">Home</a>
				</form>
			</div>
		</div>

	</div>

	<!-- End Document
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
</body>
</html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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

<!-- JavaScript
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<!-- embed jQuery javascript file here -->


<!-- Favicon
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<link rel="icon" type="image/png" href="images/favicon.png">

</head>
<body>




	<!-- Primary Page Layout
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
	<div class="container">
		<section class="row header">
			<h1>Your Nodes</h1>
			<%@ include file="/WEB-INF/views/header.jsp"%>
		</section>

		<!-- Inflate reservations here -->
		<div>
			<table class="u-full-width">
				<thead>
					<tr>
						<th>Node Name</th>
						<th>Serial Number</th>
						<th>Product Name</th>
						<th>Links</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach items="${nodes}" var="entry" varStatus="status">
						<tr>
							<td><c:out value="${entry.nodeName}"></c:out></td>
							<td><c:out value="${entry.serialNumber}"></c:out></td>
							<td><c:out value="${entry.productName}"></c:out></td>
							<td><a href="<c:url value="/Node/${entry.NID}/Detail"/>">Detail</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
		</div>




	</div>

	<!-- End Document
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
</body>
</html>

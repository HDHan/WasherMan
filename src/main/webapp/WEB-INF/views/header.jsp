<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="container">

	<a class="button" href="<c:url value="/"/>">Home</a>
	<a class="button" href="<c:url value="/Node/List"/>">Node List</a>
<%
	//Get login status from session attribute
	Boolean isLoggedIn = (Boolean) session.getAttribute("logininfo");
	String userid = (String) session.getAttribute("userid");

	if (isLoggedIn != null) {
%>
	<a class="button" >Welcome <%= userid %>.</a>
	<a class="button" href="<c:url value="/User/Logout"/>">Logout</a>
<%
	} else {
%>
	<a class="button" href="<c:url value="/User/Login"/>">Login</a>
<%
	}
%>
	</p>
</div>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration Form</title>
<link href="<c:url value='/css/bootstrap.css' />" rel="stylesheet"></link>
<link href="<c:url value='/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
	<div class="success">
		Confirmation message : ${success} <br> Would you like to <a
			href="<c:url value='/newUser' />">Add More Users</a>? <br /> Go to <a
			href="<c:url value='/admin' />">Admin Page</a> OR <a
			href="<c:url value="/logout" />">Logout</a>
	</div>
</body>
</html>
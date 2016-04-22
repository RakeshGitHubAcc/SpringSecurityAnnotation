<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin page</title>
 <link href="<c:url value='/css/bootstrap.css' />" rel="stylesheet"></link>
 <link href="<c:url value='/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
	<div class="success">
		Dear <strong>${user}</strong>, Welcome to Admin Page. 
		<br />  
	<sec:authorize access="isFullyAuthenticated()">
        <label><a href="./newUser">Create New User</a> | <a href="./list">View existing Users</a></label>
    </sec:authorize>
    <sec:authorize access="isRememberMe()">
        <label><a href="#">View existing Users</a></label>
    </sec:authorize>
    <br /> 
    <a href="<c:url value="/logout" />">Logout</a>
	</div>

</body>
</html>
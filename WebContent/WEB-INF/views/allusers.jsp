<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User List</title>
<link href="<c:url value='/css/bootstrap.css' />" rel="stylesheet"></link>
<link href="<c:url value='/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
	<div class="container">
		<table class="table">
			<thead>
				<tr>
					<th>Username</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${users}" varStatus="status">
					<tr>
						<td>${user.username}</td>
						<td>${user.firstName}</td>
						<td>${user.lastName}</td>
						<td><sec:authorize access="hasRole('ADMIN')">
								<a href="./edit-user-${user.id}">Edit</a>
	                       		&nbsp;&nbsp;&nbsp;&nbsp;
								<sec:authorize access="hasRole('DBA')">
									<a href="./delete-user-${user.id}">Delete</a>
								</sec:authorize>
							</sec:authorize>
					   </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html>
	<head>
		
	</head>
	<body>
		<h2>Hello</h2>
		<form method="GET" action="<c:url value="/page/login"/>">
			<button type="submit">Login</button>
		</form>
	
	</body>
</html>
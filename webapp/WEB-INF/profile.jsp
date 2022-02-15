<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="profile"/>

<!DOCTYPE html>
<html>
	<head>
		<style>
			<%@ include file="common/general.css" %>
			<%@ include file="static/profile.css" %>
		</style>
	</head>
	<body>
		<jsp:include page="common/header.jsp" />
		<div>
			<table id="user_table">
				<tr>
					<td><fmt:message key="user.login"/></td>
					<td id="login">${user.login}</td></tr>
				<tr>
					<td><fmt:message key="user.role"/></td>
					<td id="role_id">${user.role}</td></tr>
				<tr>
					<td><fmt:message key="user.surename"/></td>
					<td id="surename">${user.surename}</td></tr>
				<tr>
					<td><fmt:message key="user.name"/></td>
					<td id="name">${user.name}</td></tr>
				<tr>
					<td><fmt:message key="user.patronymic"/></td>
					<td id="patronymic">${user.patronymic}</td></tr>
				<tr>
					<td><fmt:message key="user.birthDate"/></td>
					<td id="birth_date">${user.birthDate}</td></tr>
				<tr>
					<td><fmt:message key="user.phoneNumber"/></td>
					<td id="phone_number">${user.phoneNumber}</td></tr>
				<tr>
					<td><fmt:message key="user.registrationDateTime"/></td>
					<td id="registration_timestamp">${user.registrationDateTime}</td>
				</tr>
				<tr>
					<td><fmt:message key="user.description"/></td>
					<td id="description">${user.description}</td>
				</tr>
			</table>
			<br>
		</div>
		<%@ include file="common/footer.html" %>
	</body>
</html>
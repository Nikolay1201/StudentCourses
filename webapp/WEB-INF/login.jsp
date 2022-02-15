<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="login"/>

<!DOCTYPE html>
<html>
	<head>
		<script>
			let loginForm;
			let errorMessage;
			document.addEventListener("DOMContentLoaded", () => {
				loginForm = document.getElementById("login_form");
				errorMessage = document.getElementById('error_message');
			});
			let xhr = new XMLHttpRequest();
			xhr.responseType = "text";
			xhr.onload = () => {
				if (xhr.status != 200) {
					//errorMessage = document.createElement("div");
					//errorMessage.innerHTML = xhr.response;
					//document.body.prepend(errorMessage);
					errorMessage.innerHtml = xhr.response;
					
				} else {
					localStorage.setItem("isAuthorized", "true");
					close();
				}
			}
			
			function submitLoginForm() {
				submitForm(loginForm);
			}
			function submitForm(form) {
				let params = "";
				for (field of form) {
					params += field.name + "=" + field.value +"&";
				}
				console.log(params);
				xhr.open("GET", "<c:url value="/data/auth?"/>" + params, true);
				xhr.send();	
			}
		</script>
	</head>
<body>
	<div id="error_message">		
	</div>
	<form id="login_form">
		<table>
			<tr>
				<td><label for="login"><fmt:message key="login" /></label></td>
				<td><input type="text" name="login"></td>
			</tr>
			<tr>
				<td><label for="password"><fmt:message key="password" /></label></td>
				<td><input type="text" name="password"></td>
			</tr>
		</table>
	</form>
	<button onclick="submitLoginForm()">
		<fmt:message key="buttons.login" />
	</button>
</body>
</html>
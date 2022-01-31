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
			document.addEventListener("DOMContentLoaded", () => {
				loginForm = document.getElementById("login_form");
				
			});
			let xhr = new XMLHttpRequest();
			xhr.responseType = "text";
			xhr.onload = () => {
				if (xhr.status != 200) {
					errorMessage = document.createElement("div");
					errorMessage.innerHTML = xhr.response;
					document.body.prepend(errorMessage);
				} else {
					window.location.href = '<c:url value="/page/profile"/>';
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
		<form id="login_form">
			<label for="login"><fmt:message key="login"/></label> 
			<input type="text" name="login"><br>
			<label for="password"><fmt:message key="password"/></label>
			<input type="text" name="password"><br>
		</form>
		<button onclick="submitLoginForm()"><fmt:message key="buttons.login"/></button>
	
	</body>
</html>
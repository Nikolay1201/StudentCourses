<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="error"/>

<html>
	<head>
		<style>
			<%@ include file="error.css" %>
		</style>
	</head>
	<body>
		<jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>
		<div id="main">
			<div id="error_message_box">
				<div id="error_message_title">${errmessagetitle}</div>
				<div id="error_message">${errmessage}</div>
				<div>
					<button onclick="history.back()"><fmt:message key="button.goBack" /></button>
					<button onclick= 'location.href = `<c:url value="/page/index"/>`'>
						<fmt:message key="button.gotoMainPage"/>
					</button>
				</div>
			</div>
		</div>
		<%@ include file="/WEB-INF/common/footer.html" %>
	</body>
</html>

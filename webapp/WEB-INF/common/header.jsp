<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="header"/>

<div id="header">
	<style><%@ include file="header.css"%></style>
	<script>
		function changeLocale(newLocale) {
			xhr = new XMLHttpRequest();
		    xhr.open("GET", '<c:url value="/data/lang?lang="/>' + newLocale);
		    xhr.send();
			xhr.onload = () => {
			    location.reload();
			}
		}
		
		window.addEventListener("storage", () => {
			if (localStorage.getItem("isAuthorized") == "true") {
				localStorage.removeItem("isAuthorized");
				location.reload();
			}
		});
		
		function openLoginWindow() {
			w = window.open("<c:url value="/page/login"/>", 
				'Login',
				'width=360,height=100,location=no');
			console.log(w);
			w.onload = () => {
				w.document.title = "<fmt:message key="link.login"></fmt:message>";
			}			
			
		}
	</script>
	<div class="header_link">
		<a href="<c:url value="/page/index"/>"><fmt:message key="link.index"></fmt:message></a>
	</div>
	<div class="header_link">
		<a href="<c:url value="/page/courses"/>"><fmt:message key="link.courses"></fmt:message></a>
	</div>	
	<div class="header_link">
		<a href="<c:url value="/page/coursesplans"/>"><fmt:message key="link.coursesplans"></fmt:message></a>
	</div>
	<c:if test="${not empty user.role }">
		<div class="header_link">
			<a href="<c:url value="/page/lessons"/>"><fmt:message key="link.lessons"></fmt:message></a>
		</div>
		<c:if test="${user.role.getId() == 1}">
			<div class="header_link">
				<a href="<c:url value="/page/users"/>"><fmt:message key="link.users"></fmt:message></a>
			</div>
			<div class="header_link"> 
				<a href="<c:url value="/page/students_and_courseplans"/>"><fmt:message key="link.student_and_courseplans"></fmt:message></a>
			</div>
		</c:if>
	</c:if>
	
	
	<c:if test="${empty user.role }">
		<div class="header_link right">
			<a href="#" onclick="openLoginWindow()">
				<fmt:message key="link.login"></fmt:message>
			</a>
		</div>
	</c:if>
	<c:if test="${not empty user.role }">
		<div class="header_link right">
			<a href="<c:url value="/data/logout"/>"><fmt:message key="link.logout"></fmt:message></a>
		</div>
	</c:if>
	<div class="header_link right"><a href="" onclick="changeLocale('ru')">РУС</a></div>
	<div class="header_link right"><a href="" onclick="changeLocale('en')">EN</a></div>
	<c:if test="${not empty user.role }">
		<div class="user_info header_link right">
			${user.login} [${user.role}]
			<br>
			<a href="<c:url value="/page/profile"/>" id="goto_profile_link">
				<fmt:message key="link.profile"></fmt:message>
			</a>
		</div>
	</c:if>
</div>
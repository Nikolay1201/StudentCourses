<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="courses"/>

<!DOCTYPE html>
<html>
	<head>
		<style>
			<%@ include file="common/static/general.css" %>
			<%@ include file="common/static/courses.css" %>
		</style>
		<script>
			let form;
			let confirm_button;
			let password_tr;
			xhr = new XMLHttpRequest();
			xhr.onload = () => {
				if (xhr.status != 200) {
					errorMessage = document.createElement("div");
					errorMessage.innerHTML = xhr.response;
					document.body.prepend(errorMessage);
				} else {
					location.reload();
				}
			}
			
			function fillForm(tr_id) {
				row = document.getElementById(tr_id);
				document.getElementById("id_inputfield").value = 
					row.getElementsByClassName("course_id")[0].innerHTML;
				document.getElementById("name_inputfield").value = 
					row.getElementsByClassName("name")[0].innerHTML;
				document.getElementById("duration_inputfield").value = 
					row.getElementsByClassName("duration")[0].innerHTML;
				document.getElementById("description_inputfield").value = 
					row.getElementsByClassName("description")[0].innerHTML;
				
				confirm_button.innerHTML = "<fmt:message key="action.saveChanges"/>";
				form.action = "<c:url value="/data/changecourse"/>";
			}
			
			function sendForm() {
				let params = new URLSearchParams(new FormData(form)).toString();
				xhr.open("GET", form.action + "?" + params);
				xhr.send();
			}
			
			function deleteCourse(course_id) {
				xhr.open("GET", "<c:url value="/data/deletecourse"/>?course_id=" + course_id, true);
				xhr.send();
			}
			
			function findCourses() {
				p = new URLSearchParams();
				let params = "";
				id_input_value = document.getElementById("id_filter_input").value;
				if (id_input_value.length != 0) {
					p.append("k", "course_id");
					p.append("t", "=");
					p.append("v", id_input_value);
				}
				name_input_value = document.getElementById("name_finter_input").value;
				if (name_input_value != length) {
					filtrationCond = document.getElementById("name_filterType").value;
					p.append("k", "name");
					p.append("t", filtrationCond);
					p.append("v", name_input_value);
				}
				location.search = p.toString();
			}
			
			function sendRequestForCourse() {
				
			}
			
			window.onload = () => {
				confirm_button = document.getElementById("confirm_course_button");
				form = document.getElementById("course_form");
				confirm_button.innerHTML = "<fmt:message key="action.addCourse"/>";
				document.getElementById("id_inputfield").oninput = function () {
					if (this.value == "" || this.value == null) {
						confirm_button.innerHTML = "<fmt:message key="action.addCourse"/>";
						form.action = "<c:url value="/data/addcourse"/>";
						password_tr.style.display = "";
					} else {
						confirm_button.innerHTML = "<fmt:message key="action.saveChanges"/>";
						form.action = "<c:url value="/data/changecourse"/>";
						password_tr.style.display = "none";
					}
				}
			}
			
		</script>
	</head>
	<body>
		<jsp:include page="common/header.jsp"/>
		<c:if test="${user.role.getId() == 1}">
			<div id="form_block">
				<form id="course_form" method="get" enctype="text/html" action="<c:url value="/data/addcourse"/>">
					<table>
						<tr>
							<td><label for="id"><fmt:message key="course.id"/></label></td>
							<td><input type="text" name="course_id" id="id_inputfield"><br></td>
						</tr>
						<tr>
							<td><label for="name"><fmt:message key="course.name"/></label></td>
							<td><input type="text" name="name" id="name_inputfield"><br></td>
						</tr>
						<tr>
							<td><label for="duration"><fmt:message key="course.duration"/></label></td>
							<td><input type="text" name="duration" id="duration_inputfield"><br></td>
						</tr>
						<tr>
							<td><label for="description"><fmt:message key="course.description"/></label></td>
							<td><input type="text" name="description" id="description_inputfield"><br></td>
						</tr>
					</table>
				</form>
				<button id="confirm_course_button" onclick="sendForm()"></button>
			</div>
		</c:if>
		<div id="filter_block">
			<table>
			<tr>
				<td><label><fmt:message key="filter.id"/></label></td>
				<td><input type="text" id="id_filter_input"/></td>
			</tr>
			<tr>
				<td><label><fmt:message key="filter.name"/></label></td>
				<td>
					<select id="name_filterType">
						<option value="="><fmt:message key="filterType.equals"/></option>
						<option value="LIKE"><fmt:message key="filterType.like"/></option>
					</select>
					<input type="text" id="name_finter_input"/>
				</td>
			</table>
			<button onclick="findCourses()"><fmt:message key="action.findCourses"/></button>
		</div>
		<br><br>
		<div>
			<table id="courses_table">
				<tr>		
					<td><fmt:message key="course.id"/></td>
					<td><fmt:message key="course.name"/></td>
					<td><fmt:message key="course.duration"/></td>
					<td><fmt:message key="course.description"/></td>			
				</tr>
				<c:forEach var="course" items="${entitiesList}">
					<tr id="${course.id}_tr">
						<td class="course_id">${course.id}</td>
						<td class="name">${course.name}</td>
						<td class="duration">${course.duration}</td>
						<td class="description">${course.description}</td>
						<c:if test="${user.role.getId() == 1}">
							<td><button onclick="fillForm('${course.id}_tr')"><fmt:message key="action.edit"/></button>
							<td><button onclick="deleteCourse('${course.id}')">X</button></td>	
						</c:if>
					</tr>
				</c:forEach>
			</table>
		</div>
		<%@ include file="common/static/footer.html" %>
	</body>
</html>
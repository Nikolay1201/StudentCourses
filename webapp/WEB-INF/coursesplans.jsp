<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="coursesplans"/>

<!DOCTYPE html>
<html>
	<head>
		<style>
			<%@ include file="common/static/general.css" %>
			<%@ include file="common/static/coursesplans.css" %>
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
					row.getElementsByClassName("course_plan_id")[0].innerHTML;
				document.getElementById("course_id_inputfield").value = 
					row.getElementsByClassName("course_id")[0].firstElementChild.innerText;
				document.getElementById("trainer_user_id_inputfield").value = 
					row.getElementsByClassName("trainer_user_id")[0].firstElementChild.innerText;
				document.getElementById("status_select").value = 
					row.getElementsByClassName("status")[0].getAttribute("data-value");
				document.getElementById("description_inputfield").value = 
					row.getElementsByClassName("description")[0].innerHTML;
				
				confirm_button.innerHTML = "<fmt:message key="action.saveChanges"/>";
				form.action = "<c:url value="/data/changecourseplan"/>";
			}
			
			function sendForm() {
				let params = new URLSearchParams(new FormData(form)).toString();
				xhr.open("GET", form.action + "?" + params);
				xhr.send();
			}
			
			function deleteCoursePlan(course_plan_id) {
				xhr.open("GET", "<c:url value="/data/deletecourseplan"/>?course_plan_id=" + course_plan_id, true);
				xhr.send();
			}
			
			function findCoursesPlans() {
				p = new URLSearchParams();
				let params = "";
				id_input_value = document.getElementById("id_filter_input").value;
				if (id_input_value.length != 0) {
					p.append("k", "course_plan_id");
					p.append("t", "=");
					p.append("v", id_input_value);
				}
				location.search = p.toString();
			}
			
			window.onload = () => {
				confirm_button = document.getElementById("confirm_course_plan_button");
				form = document.getElementById("course_plan_form");
				confirm_button.innerHTML = "<fmt:message key="action.addCoursePlan"/>";
				document.getElementById("id_inputfield").oninput = function () {
					if (this.value == "" || this.value == null) {
						confirm_button.innerHTML = "<fmt:message key="action.addCoursePlan"/>";
						form.action = "<c:url value="/data/addcourseplan"/>";
					} else {
						confirm_button.innerHTML = "<fmt:message key="action.saveChanges"/>";
						form.action = "<c:url value="/data/changecourseplan"/>";
					}
				}
			}
			
		</script>
	</head>
	<body>
		<jsp:include page="common/header.jsp"/>
		<div id="form_block">
			<form id="course_plan_form" method="get" enctype="text/html" action="<c:url value="/data/addcourseplan"/>">
				<table>
					<tr>
						<td><label for="id"><fmt:message key="coursePlan.id"/></label></td>
						<td><input type="text" name="course_plan_id" id="id_inputfield"><br></td>
					</tr>
					<tr>
						<td><label for="course_id"><fmt:message key="coursePlan.courseId"/></label></td>
						<td><input type="text" name="course_id" id="course_id_inputfield"><br></td>
					</tr>
					<tr>
						<td><label for="trainer_user_id"><fmt:message key="coursePlan.trainerUserId"/></label></td>
						<td><input type="text" name="trainer_id" id="trainer_user_id_inputfield"><br></td>
					</tr>
					<tr>
						<td><label for="description"><fmt:message key="coursePlan.description"/></label></td>
						<td><input type="text" name="description" id="description_inputfield"><br></td>
					</tr>
					<tr>
						<td><label for="status"><fmt:message key="coursePlan.status"/></label></td>
						<td>
							<select name="status_id" id="status_select" form="course_plan_form">
								<option value="1"><fmt:message key="coursePlan.statuses.notStarted"/></option>
								<option value="2"><fmt:message key="coursePlan.statuses.active"/></option>
								<option value="3"><fmt:message key="coursePlan.statuses.suspended"/></option>
								<option value="4"><fmt:message key="coursePlan.statuses.finished"/></option>
								<option value="5"><fmt:message key="coursePlan.statuses.cancelled"/></option>
							</select>
						</td>
					</tr>
				</table>
			</form>
			<button id="confirm_course_plan_button" onclick="sendForm()"></button>
		</div>
		<div id="filter_block">
			<table>
				<tr>
					<td><label><fmt:message key="filter.id"/></label></td>
					<td><input type="text" id="id_filter_input"/></td>
				</tr>
			</table>
			<button onclick="findCoursesPlans()"><fmt:message key="action.findCoursesPlans"/></button>
		</div>
		<div>
			<table id="courses_plans_table">
				<tr>		
					<td><fmt:message key="coursePlan.id"/></td>
					<td><fmt:message key="coursePlan.courseId"/></td>
					<td><fmt:message key="coursePlan.trainerUserId"/></td>
					<td><fmt:message key="coursePlan.status"/></td>		
					<td><fmt:message key="coursePlan.description"/></td>	
				</tr>
				<c:forEach var="coursePlan" items="${entitiesList}">
					<tr id="${coursePlan.id}_tr">
						<td class="course_plan_id">${coursePlan.id}</td>
						<td class="course_id">
							<a href="<c:url value="/page/courses?k=course_id&t=%3D&v=${coursePlan.course.getId()}"/>">
								${coursePlan.course.getId()}
							</a>
						</td>
						<td class="trainer_user_id">
							<a href="<c:url value="/page/users?k=user_id&t=%3D&v=${coursePlan.trainer.getId()}"/>">
								${coursePlan.trainer.getId()}
							</a>
						</td>
						<td class="status" data-value="${coursePlan.status.getValue()}">${coursePlan.status}</td>
						<td class="description">${coursePlan.description}</td>
						<c:if test="${user.role.getId() == 1}">
							<td><button onclick="fillForm('${coursePlan.id}_tr')"><fmt:message key="action.edit"/></button>
							<td><button onclick="deleteCoursePlan('${coursePlan.id}')">X</button></td>	
						</c:if>
						<c:if test="${user.role.getId() == 3}">
							<td><button onclick="sendRequestForCoursePlan('${coursePlan.id}')">WANNA THIS</button></td>	
						</c:if>
					</tr>
				</c:forEach>
			</table>
		</div>
		<%@ include file="common/static/footer.html" %>
	</body>
</html>
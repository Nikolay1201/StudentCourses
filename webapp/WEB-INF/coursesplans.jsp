<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="coursesplans"/>

<!DOCTYPE html>
<html>
	<head>
		<style>
			<%@ include file="static/general.css" %>
			<%@ include file="static/coursesplans.css" %>
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
			
			function addToLocalStorage(id) {
				localStorage.setItem("entity_id", id);
			}
			
			let inputfieldBeingSelected;
			window.addEventListener("storage", () => {
				inputfieldBeingSelected.value = localStorage.getItem("entity_id");
			});
			
			function fillForm(tr_id) {
				row = document.getElementById(tr_id);
				document.getElementById("id_inputfield").value = 
					parseInt(row.getElementsByClassName("course_plan_id")[0].innerHTML);
				document.getElementById("course_id_inputfield").value = 
					parseInt(row.getElementsByClassName("course_id")[0].firstElementChild.innerText);
				document.getElementById("trainer_user_id_inputfield").value = 
					parseInt(row.getElementsByClassName("trainer_user_id")[0].firstElementChild.innerText);
				document.getElementById("status_select").value = 
					row.getElementsByClassName("status")[0].getAttribute("data-value");
				document.getElementById("description_inputfield").value = 
					row.getElementsByClassName("description")[0].innerHTML;
				document.getElementById("start_date_inputfield").value = 
					row.getElementsByClassName("start_date")[0].innerHTML;
				
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
				id_input_value = document.getElementById("id_filter_input").value;
				if (id_input_value.length != 0) {
					p.append("k", "course_plan_id");
					p.append("t", "=");
					p.append("v", id_input_value);
				}
				course_id_input_value = document.getElementById("course_id_filter_input").value;
				if (course_id_input_value.length != 0) {
					p.append("k", "course_id");
					p.append("t", "=");
					p.append("v", course_id_input_value);
				}
				<c:if test="${not empty user.role}">
					isOnlyMine = document.getElementById("mode_checkbox");
					p.append("onlyMine", isOnlyMine.checked);
				</c:if>
				<c:if test="${selectMode eq 'modalwin'}"> 
					p.append("selectMode", "modalwin");
				</c:if>
				location.search = p.toString();
			}
			
			function addUserToCoursePlan(tr_id) {
				row = document.getElementById(tr_id);
				//row.style.display="none";
				p = new URLSearchParams();
				p.append("course_plan_id", row.getElementsByClassName("course_plan_id")[0].innerHTML);
				p.append("student_user_id", "${user.id}");
				p.append("id", "");
				xhr.open("GET", "<c:url value="/data/add_usercourseplan"/>?" + p.toString());
				xhr.send();
			}
			
			function findCourseModal(inputFieldId) {
				inputfieldBeingSelected = document.getElementById(inputFieldId);
				window.open("<c:url value="/page/courses?selectMode=modalwin"/>", 'Find a course', 'location=yes');
			}
			
			function findTrainerModal() {
				inputfieldBeingSelected = document.getElementById("trainer_user_id_inputfield");
				window.open("<c:url value="/page/users?selectMode=modalwin"/>", 'Find a trainer', 'location=yes');
			}
			
			<c:if test="${user.role.getId() == 1}">
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
			</c:if>
			
		</script>
	</head>
	<body>
	<c:if test="${selectMode ne 'modalwin'}">
		<jsp:include page="common/header.jsp" />
	</c:if>
	<c:if test="${user.role.getId() == 1 and selectMode ne 'modalwin'}">
			<div id="form_block">
				<form id="course_plan_form" method="get" enctype="text/html" action="<c:url value="/data/addcourseplan"/>">
					<table>
						<tr>
							<td><label for="id"><fmt:message key="coursePlan.id"/></label></td>
							<td><input type="number" min="0" name="course_plan_id" id="id_inputfield"><br></td>
						</tr>
						<tr>
							<td><label for="course_id"><fmt:message key="coursePlan.courseId"/></label></td>
							<td><input type="number" min="0" name="course_id" id="course_id_inputfield"><br></td>
							<td><button type="button" onclick="findCourseModal('course_id_inputfield')"><fmt:message key="action.find"/></button></td>
						</tr>
						<tr>
							<td><label for="trainer_user_id"><fmt:message key="coursePlan.trainerUserId"/></label></td>
							<td><input type="number" min="0" name="trainer_id" id="trainer_user_id_inputfield"><br></td>
							<td><button type="button" onclick="findTrainerModal()"><fmt:message key="action.find"/></button></td>
						</tr>
						<tr>
							<td><label for="start_date"><fmt:message key="coursePlan.startDate"/></label></td>
							<td><input type="date" name="start_date" id="start_date_inputfield"><br></td>
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
		</c:if>
		<div id="filter_block">
			<table>
				<tr>
					<td><label><fmt:message key="filter.id"/></label></td>
					<td><input type="number" min="0" id="id_filter_input"/></td>
				</tr>
				<tr>
					<td><label><fmt:message key="filter.courseId"/></label></td>
					<td><input type="number" min="0" id="course_id_filter_input"/></td>
					<td><button type="button" onclick="findCourseModal('course_id_filter_input')">
						<fmt:message key="action.find"/></button>
					</td>
				</tr>
				<c:if test="${user.role.getId() == 2 or user.role.getId() == 3}">
					<label for="mode"><fmt:message key="action.onlyMine"/></label>
					<input id="mode_checkbox" name="mode" type="checkbox" 
					<c:if test="${not empty onlyMyCoursesPlans}"> checked</c:if>/>
				</c:if>
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
					<td><fmt:message key="coursePlan.startDate"/></td>			
					<td><fmt:message key="coursePlan.description"/></td>	
				</tr>
				<c:forEach var="coursePlan" items="${entitiesList}" varStatus="loop">
					<tr id="${coursePlan.id}_tr">
						<td class="course_plan_id">${coursePlan.id}</td>
						<td class="course_id">
							[<a href="<c:url value="/page/courses?k=course_id&t=%3D&v=${coursePlan.courseId}"/>">
								${coursePlan.courseId}
							</a>]
							${coursesNamesList.get(loop.index)}
						</td>
						<td class="trainer_user_id">
							<c:if test="${user.role.getId() == 1}">
								[<a href="<c:url value="/page/users?k=user_id&t=%3D&v=${coursePlan.trainerUserId}"/>">
									${coursePlan.trainerUserId}
								</a>]
							</c:if>
							<c:if test="${user.role.getId() == 3}">[${coursePlan.trainerUserId}]</c:if>
							${trainersNamesList.get(loop.index)}
						</td>
						<td class="status" data-value="${coursePlan.status.getValue()}">${coursePlan.status}</td>
						<td class="start_date">${coursePlan.startDate}</td>
						<td class="description">${coursePlan.description}</td>
						<c:if test="${selectMode ne 'modalwin'}">
							<c:if test="${user.role.getId() == 1}">
								<td><button onclick="fillForm('${coursePlan.id}_tr')"><fmt:message key="action.edit"/></button>
								<td><button onclick="deleteCoursePlan('${coursePlan.id}')">X</button></td>	
							</c:if>
							<c:if test="${user.role.getId() == 3}">
								<td><button onclick="addUserToCoursePlan('${coursePlan.id}_tr')">WANNA THIS</button></td>	
							</c:if>
							<c:if test="${user.role.getId() == 2}">
								<td>
									<a href="<c:url value="/page/lessons?k=courses_plan_id&t=%3D&v=${coursePlan.id}"/>">
											<fmt:message key="link.gotoLessons" />
									</a>
								</td>
							</c:if>
						</c:if>
						<c:if test="${selectMode eq 'modalwin'}"> 
							<td><button onclick="addToLocalStorage(${coursePlan.id})">
							<fmt:message key="action.select"/></button>
						</c:if>

					</tr>
				</c:forEach>
			</table>
		</div>
		<%@ include file="common/footer.html" %>
	</body>
</html>
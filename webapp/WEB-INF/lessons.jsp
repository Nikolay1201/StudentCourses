<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="lessons"/>

<!DOCTYPE html>
<html>
	<head>
		<style>
			<%@ include file="common/general.css" %>
			<%@ include file="static/lessons.css" %>
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
			
			let inputfieldBeingSelected;
			window.addEventListener("storage", () => {
				inputfieldBeingSelected.value = localStorage.getItem("entity_id");
			});
			
			function fillForm(tr_id) {
				row = document.getElementById(tr_id);
				document.getElementById("id_inputfield").value = 
					parseInt(row.getElementsByClassName("id")[0].innerHTML);
				document.getElementById("courses_plan_id_inputfield").value = 
					parseInt(row.getElementsByClassName("courses_plan_id")[0].firstElementChild.innerText);
				document.getElementById("start_time_inputfield").value = 
					row.getElementsByClassName("start_time")[0].innerHTML;
				document.getElementById("duration_inputfield").value = 
					row.getElementsByClassName("duration")[0].innerHTML;
				document.getElementById("classroom_number_inputfield").value = 
					row.getElementsByClassName("classroom_number")[0].innerHTML;
				document.getElementById("completeness_select").value = 
					row.getElementsByClassName("is_completed")[0].innerHTML;
				document.getElementById("remarks_inputfield").value = 
					row.getElementsByClassName("remarks")[0].innerHTML;

				confirm_button.innerHTML = "<fmt:message key="action.saveChanges"/>";
				form.action = "<c:url value="/data/changelesson"/>";
			}
			
			function sendForm() {
				let params = new URLSearchParams(new FormData(form)).toString();
				xhr.open("GET", form.action + "?" + params);
				xhr.send();
			}
			
			function deleteLesson(lesson_id) {
				xhr.open("GET", "<c:url value="/data/deletelesson"/>?lesson_id=" + lesson_id, true);
				xhr.send();
			}
			
			function findLessons() {
				p = new URLSearchParams();	
				id_input_value = document.getElementById("id_filter_input").value;
				if (id_input_value.length != 0) {
					p.append("k", "lesson_id");
					p.append("t", "=");
					p.append("v", id_input_value);
				}
				isOnlyMine = document.getElementById("mode_checkbox");
				p.append("onlyMine", isOnlyMine.checked);
				location.search = p.toString();
			}
			
			
			function findCoursePlanModal() {
				inputfieldBeingSelected = document.getElementById("courses_plan_id_inputfield");
				window.open("<c:url value="/page/coursesplans?selectMode=modalwin"/>", 'Find a course', 'location=yes');
			}
			
			<c:if test="${user.role.getId() == 2}">
				window.onload = () => {
					confirm_button = document.getElementById("confirm_lesson_button");
					form = document.getElementById("lesson_form");
					confirm_button.innerHTML = "<fmt:message key="action.addLesson"/>";
					document.getElementById("id_inputfield").oninput = function () {
						if (this.value == "" || this.value == null) {
							confirm_button.innerHTML = "<fmt:message key="action.addLesson"/>";
							form.action = "<c:url value="/data/addlesson"/>";
						} else {
							confirm_button.innerHTML = "<fmt:message key="action.saveChanges"/>";
							form.action = "<c:url value="/data/changelesson"/>";
						}
					}
				}
			</c:if>
			
		</script>
	</head>
	<body>
		<jsp:include page="common/header.jsp"/>
		<c:if test="${user.role.getId() == 2}">
			<div id="form_block">
				<form id="lesson_form" method="get" enctype="text/html" action="<c:url value="/data/addlesson"/>">
					<table>
						<tr>
							<td><label for="id"><fmt:message key="lesson.id"/></label></td>
							<td><input type="number" min="0" name="lesson_id" id="id_inputfield"><br></td>
						</tr>
						<tr>
							<td><label for="courses_plan_id"><fmt:message key="lesson.coursesPlanId"/></label></td>
							<td><input type="number" min="0" name="courses_plan_id" id="courses_plan_id_inputfield"><br></td>
							<td><button type="button" onclick="findCoursePlanModal()"><fmt:message key="action.find"/></button></td>
						</tr>
						<tr>
							<td><label for="start_time"><fmt:message key="lesson.startTime"/></label></td>
							<td><input type="datetime-local" name="start_time" id="start_time_inputfield"><br></td>
						</tr>
						<tr>
							<td><label for="duration"><fmt:message key="lesson.duration"/></label></td>
							<td><input type="text" name="duration" id="duration_inputfield"><br></td>
						</tr>
						<tr>
							<td><label for="classroom_number"><fmt:message key="lesson.classroomNumber"/></label></td>
							<td><input type="number" min="0" name="classroom_number" id="classroom_number_inputfield"><br></td>
						</tr>
						<tr>
							<td><label for="is_completed"><fmt:message key="lesson.isCompleted"/></label></td>
							<td>
								<select name="is_completed" id="completeness_select" form="lesson_form">
									<option value="true"><fmt:message key="lesson.completeness.yes"/></option>
									<option value="false"><fmt:message key="lesson.completeness.no"/></option>
								</select>
							</td>
						</tr>
						<tr>
							<td><label for="remarks"><fmt:message key="lesson.remarks"/></label></td>
							<td><input type="text" name="remarks" id="remarks_inputfield"><br></td>
						</tr>
					</table>
				</form>
				<button id="confirm_lesson_button" onclick="sendForm()"></button>
			</div>
		</c:if>
		<div id="filter_block">
			<table>
				<tr>
					<td><label><fmt:message key="filter.id"/></label></td>
					<td><input type="text" id="id_filter_input"/></td>
				</tr>
				<tr>
					<td><label><fmt:message key="filter.startTime"/></label></td>
					<td><input type="datetime-local" id="start_time_input_filter"/></td>
				</tr>
				<c:if test="${user.role.getId() == 2 or user.role.getId() == 3}">
					<label for="mode"><fmt:message key="action.onlyMine"/></label>
					<input id="mode_checkbox" name="mode" type="checkbox" 
					<c:if test="${not empty onlyMyLessons}"> checked</c:if>/>
				</c:if>
			</table>
			<button onclick="findLessons()"><fmt:message key="action.findLessons"/></button>
		</div>
		<div>
			<table class="entity_table">
				<tr>
					<td><fmt:message key="lesson.id" /></td>
					<td><fmt:message key="lesson.coursesPlanId" /></td>
					<td><fmt:message key="lesson.startTime" /></td>
					<td><fmt:message key="lesson.duration" /></td>
					<td><fmt:message key="lesson.classroomNumber" /></td>
					<td><fmt:message key="lesson.isCompleted" /></td>
					<td><fmt:message key="lesson.remarks" /></td>
				</tr>
				<c:forEach var="lesson" items="${entitiesList}" varStatus="loop">
					<tr id="${lesson.id}_tr">
						<td class="id">${lesson.id}</td>
						<td class="courses_plan_id">
							[<a href="<c:url value="/page/coursesplans?k=course_plan_id&t=%3D&v=${lesson.coursePlanId}"/>">
								${lesson.coursePlanId}
							</a>]
							${coursesPlansNamesList.get(loop.index)}
						</td>
						<td class=start_time>${lesson.startTime}</td>
						<td class="duration">${lesson.duration}</td>
						<td class="classroom_number">${lesson.classroomNumber}</td>
						<td class="is_completed">${lesson.isCompleted()}</td>
						<td class="remarks">${lesson.remarks}</td>
						<c:if test="${user.role.getId() == 2}">
							<td><button onclick="fillForm('${lesson.id}_tr')"><fmt:message key="action.edit"/></button>
							<td><button onclick="deleteLesson('${lesson.id}')">X</button></td>	
						</c:if>
					</tr>
				</c:forEach>
			</table>
		</div>
		<%@ include file="common/footer.html" %>
	</body>
</html>
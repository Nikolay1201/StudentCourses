<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="coursesplans"/>
<fmt:setBundle basename="u_and_cp"/>

<!DOCTYPE html>
<html>
	<head>
		<style>
			<%@ include file="common/static/general.css" %>
		</style>
		<script>
			let form;
			let confirm_button;
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
					row.getElementsByClassName("id")[0].innerHTML;
				document.getElementById("course_plan_id_inputfield").value = 
					row.getElementsByClassName("course_plan_id")[0].firstElementChild.innerText;
				document.getElementById("student_user_id_inputfield").value = 
					row.getElementsByClassName("student_user_id")[0].firstElementChild.innerText;
				document.getElementById("mark_inputfield").value = 
					row.getElementsByClassName("mark")[0].innerHTML;
				document.getElementById("review_inputfield").value = 
					row.getElementsByClassName("review")[0].innerHTML;
				confirm_button.innerHTML = "<fmt:message key="action.saveChanges"/>";
				form.action = "<c:url value="/data/change_usercourseplan"/>";
			}
			
			function sendForm() {
				let params = new URLSearchParams(new FormData(form)).toString();
				xhr.open("GET", form.action + "?" + params);
				xhr.send();
			}
			
			function deleteRecord(course_plan_id) {
				xhr.open("GET", "<c:url value="/data/delete_usercourseplan"/>?course_plan_id=" + course_plan_id, true);
				xhr.send();
			}
			
			function findCoursesPlans() {
				p = new URLSearchParams();
				let params = "";
				id_input_value = document.getElementById("course_plan_id_inputfield").value;
				if (id_input_value.length != 0) {
					p.append("k", "course_plan_id");
					p.append("t", "=");
					p.append("v", id_input_value);
				}
				location.search = p.toString();
			}
			
			window.onload = () => {
				confirm_button = document.getElementById("confirm_button");
				form = document.getElementById("form");
				confirm_button.innerHTML = "<fmt:message key="action.add"/>";
				document.getElementById("id_inputfield").oninput = function () {
					if (this.value == "" || this.value == null) {
						confirm_button.innerHTML = "<fmt:message key="action.add"/>";
						form.action = "<c:url value="/data/add_usercourseplan"/>";
					} else {
						confirm_button.innerHTML = "<fmt:message key="action.saveChanges"/>";
						form.action = "<c:url value="/data/change_usercourseplan"/>";
					}
				}
			}
			
		</script>
	</head>
	<body>
		<jsp:include page="common/header.jsp"/>
		<div id="form_block">
			<form id="form" method="get" enctype="text/html" action="<c:url value="/data/add_usercourseplan"/>">
				<table>
					<tr>
						<td><label for="id"><fmt:message key="u_and_cp.id"/></label></td>
						<td><input type="text" name="id" id="id_inputfield"><br></td>
					</tr>
					<tr>
						<td><label for="course_plan_id"><fmt:message key="u_and_cp.cp_id"/></label></td>
						<td><input type="text" name="course_plan_id" id="course_plan_id_inputfield"><br></td>
					</tr>
					<tr>
						<td><label for="student_user_id"><fmt:message key="u_and_cp.student_id"/></label></td>
						<td><input type="text" name="student_user_id" id="student_user_id_inputfield"><br></td>
					</tr>
					<tr>
						<td><label for="review"><fmt:message key="u_and_cp.review"/></label></td>
						<td><input type="text" name="review" id="review_inputfield"><br></td>
					</tr>
					<tr>
						<td><label for="mark"><fmt:message key="u_and_cp.mark"/></label></td>
						<td><input type="text" name="mark" id="mark_inputfield"><br></td>
					</tr>
				
				</table>
			</form>
			<button id="confirm_button" onclick="sendForm()"></button>
		</div>
		<br>
		<div id="filter_block">
			<table>
				<tr>
					<td><label><fmt:message key="filter.id"/></label></td>
					<td><input type="text" id="id_filter_input"/></td>
				</tr>
			</table>
			<button onclick="findRecords()"><fmt:message key="action.find"/></button>
		</div>
		<div>
			<table id="table" class="entity_table">
				<tr>		
					<td><fmt:message key="u_and_cp.id"/></td>
					<td><fmt:message key="u_and_cp.cp_id"/></td>
					<td><fmt:message key="u_and_cp.student_id"/></td>
					<td><fmt:message key="u_and_cp.mark"/></td>
					<td><fmt:message key="u_and_cp.review"/></td>		
				</tr>
				<c:forEach var="u_and_cp" items="${entitiesList}">
					<tr id="${u_and_cp.id}_tr">
						<td class="id">${u_and_cp.id}</td>
						<td class="course_plan_id">
							<a href="<c:url value="/page/coursesplans?k=course_plan_id&t=%3D&v=${u_and_cp.coursePlanId}"/>">
								${u_and_cp.getCoursePlanId()}
							</a>
						</td>
						<td class="student_user_id">
							<a href="<c:url value="/page/users?k=user_id&t=%3D&v=${u_and_cp.getStudentUserId()}"/>">
								${u_and_cp.getStudentUserId()}
							</a>
						</td>
						<td class="mark">${u_and_cp.mark}</td>
						<td class="review">${u_and_cp.review}</td>
						<c:if test="${user.role.getId() == 1}">
							<td><button onclick="fillForm('${u_and_cp.id}_tr')"><fmt:message key="action.edit"/></button>
							<td><button onclick="deleteCoursePlan('${u_and_cp.id}')">X</button></td>	
						</c:if>
					</tr>
				</c:forEach>
			</table>
		</div>
		<%@ include file="common/static/footer.html" %>
	</body>
</html>
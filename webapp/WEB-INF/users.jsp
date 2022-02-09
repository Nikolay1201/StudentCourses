<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="users"/>

<!DOCTYPE html>
<html>
	<head>
		<style>
			<%@ include file="common/static/general.css" %>
			<%@ include file="common/static/users.css" %>
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
					row.getElementsByClassName("id")[0].innerHTML;
				document.getElementById("login_inputfield").value = 
					row.getElementsByClassName("login")[0].innerHTML;
				document.getElementById("surename_inputfield").value = 
					row.getElementsByClassName("surename")[0].innerHTML;
				document.getElementById("name_inputfield").value = 
					row.getElementsByClassName("name")[0].innerHTML;
				document.getElementById("patronymic_inputfield").value = 
					row.getElementsByClassName("patronymic")[0].innerHTML;
				document.getElementById("birth_date_inputfield").value = 
					row.getElementsByClassName("birth_date")[0].innerHTML;
				document.getElementById("phone_number_inputfield").value = 
					row.getElementsByClassName("phone_number")[0].innerHTML;
				document.getElementById("email_inputfield").value = 
					row.getElementsByClassName("email")[0].innerHTML;
				document.getElementById("description_inputfield").value = 
					row.getElementsByClassName("description")[0].innerHTML;
				document.getElementById("role_select").value = 
					row.getElementsByClassName("role")[0].innerHTML;
				
				
				confirm_button.innerHTML = "<fmt:message key="action.saveChanges"/>";
				form.action = "<c:url value="/data/changeuser"/>";
				password_tr.style.display = "none";
			}
			
			function sendForm() {
				let params = new URLSearchParams(new FormData(form)).toString();
				xhr.open("GET", form.action + "?" + params);
				xhr.send();
			}
			
			function resetForm() {
				form.reset();
			}
			
			function deleteUser(user_id) {
				xhr.open("GET", "<c:url value="/data/deleteuser"/>?user_id=" + user_id, true);
				xhr.send();
			}
			
			function findUsers() {
				p = new URLSearchParams();
				id_input_value = document.getElementById("id_filter_input").value;
				if (id_input_value.length != 0) {
					p.append("k", "user_id");
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
			
			window.onload = () => {
				password_tr = document.getElementById("password_tr");
				confirm_button = document.getElementById("confirm_user_button");
				form = document.getElementById("user_form");
				confirm_button.innerHTML = "<fmt:message key="action.addUser"/>";
				document.getElementById("id_inputfield").oninput = function () {
					if (this.value == "" || this.value == null) {
						confirm_button.innerHTML = "<fmt:message key="action.addUser"/>";
						form.action = "<c:url value="/data/adduser"/>";
						password_tr.style.display = "";
					} else {
						confirm_button.innerHTML = "<fmt:message key="action.saveChanges"/>";
						form.action = "<c:url value="/data/changeuser"/>";
						password_tr.style.display = "none";
					}
				}
			}
			
		</script>
	</head>
	<body>
		<jsp:include page="common/header.jsp"/>
		<div id="form_block">
			<form id="user_form" method="get" enctype="text/html" action="<c:url value="/data/adduser"/>">
				<table>
					<tr>
						<td><label for="id"><fmt:message key="user.id"/></label></td>
						<td><input type="text" name="user_id" id="id_inputfield"><br></td>
					</tr>
					<tr>
						<td><label for="login"><fmt:message key="user.login"/></label></td>
						<td><input type="text" name="login" id="login_inputfield"><br></td>
					</tr>
					<tr id="password_tr">
						<td><label for="password" id="password_label"><fmt:message key="user.password"/></label></td>
						<td><input type="text" name="password" id="password_inputfield"><br></td>
					</tr>
					<tr>
						<td><label for="surename"><fmt:message key="user.surename"/></label></td>
						<td><input type="text" name="surename" id="surename_inputfield"><br></td>
					</tr>
					<tr>
						<td><label for="name"><fmt:message key="user.name"/></label></td>
						<td><input type="text" name="name" id="name_inputfield"><br></td>
					</tr>
					<tr>
						<td><label for="patronymic"><fmt:message key="user.patronymic"/></label></td>
						<td><input type="text" name="patronymic" id="patronymic_inputfield"><br></td>
					</tr>
					<tr>
						<td><label for="birth_date"><fmt:message key="user.birthDate"/></label></td>
						<td><input type="date" name="birth_date" id="birth_date_inputfield"><br></td>
					</tr>
					<tr>
						<td><label for="phone_number"><fmt:message key="user.phoneNumber"/></label></td>
						<td><input type="text" name="phone_number" id="phone_number_inputfield"><br></td>
					</tr>
					<tr>
						<td><label for="email"><fmt:message key="user.email"/></label></td>
						<td><input type="text" name="email" id="email_inputfield"><br></td>
					</tr>
					<tr>
						<td><label for="description"><fmt:message key="user.description"/></label></td>
						<td><input type="text" name="description" id="description_inputfield"><br></td>
					</tr>
					<tr>
						<td><label><fmt:message key="user.role"/></label></td>
						<td><select name="role_id" form="user_form" id="role_select">
							<option value="STUDENT">STUDENT</option>
							<option value="TRAINER">TRAINER</option>
							<option value="ADMIN">ADMIN</option>
						</select></td>
					</tr>
				</table>
			</form>
			<button id="confirm_user_button" onclick="sendForm()"><fmt:message key="action.addUser"/></button>
			<button id="clear_form_button" onclick="resetForm()"><fmt:message key="action.resetForm"/></button>
		</div>
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
			<button onclick="findUsers()"><fmt:message key="action.findUsers"/></button>
		</div>
		<br>
		<div>
			<table id="users_table">
				<tr>		
					<td><fmt:message key="user.id"/></td>
					<td><fmt:message key="user.login"/></td>
					<td><fmt:message key="user.role"/></td>
					<td><fmt:message key="user.name"/></td>
					<td><fmt:message key="user.surename"/></td>
					<td><fmt:message key="user.patronymic"/></td>
					<td><fmt:message key="user.birthDate"/></td>
					<td><fmt:message key="user.phoneNumber"/></td>
					<td><fmt:message key="user.email"/></td>	
					<td><fmt:message key="user.description"/></td>	
					<td><fmt:message key="user.registrationDateTime"/></td>	
					<td></td>
					<td></td>				
				</tr>
				<c:forEach var="user" items="${entitiesList}">
					<tr id="${user.id}_tr">
						<td class="id">${user.id}</td>
						<td class="login">${user.login}</td>
						<td class="role">${user.role}</td>
						<td class="name">${user.name}</td>
						<td class="surename">${user.surename}</td>
						<td class="patronymic">${user.patronymic}</td>
						<td class="birth_date">${user.birthDate}</td>
						<td class="phone_number">${user.phoneNumber}</td>
						<td class="email">${user.email}</td>
						<td class="description">${user.description}</td>
						<td class="registration_timestamp">${user.registrationDateTime}</td>
						<td><button onclick="fillForm('${user.id}_tr')"><fmt:message key="action.edit"/></button>
						<td><button onclick="deleteUser('${user.id}')">X</button></td>					
					</tr>
				</c:forEach>
			</table>
		</div>
		<%@ include file="common/static/footer.html" %>
	</body>
</html>
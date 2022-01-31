<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
	#error_message_box {
		position:fixed; 
		left:40%; 
		top:40%; 
		background-color: brown;
	}
	#close_error_message_button {
		float:right;
	}
</style>
<div id="error_message_box">
	<div id="error_message">${errmessage}</div>
	<button id="close_error_message_button"
	onclick="document.getElementById('error_message_box').remove();">close</button>
</div>

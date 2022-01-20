<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script>
	function test123() {
		x = new XMLHttpRequest();
		x.open("GET", "http://localhost:8080/DataAcessController?type=get&attr_name1=id&ftype1=eq&attr_val=1");
		x.send();
		x.onload = () => {
			console.log(x.response);
		}
	}
</script>
</head>
<body>
	<h1>lalalla-test228337</h1>
	<form action="DataAcessController" method="get">
		<input type="submit" value="Press me"/>
	</form>
</body>
</html>
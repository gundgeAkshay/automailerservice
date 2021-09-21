<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page</title>
</head>
<body>
	<h1>Login Page</h1>

	<label for="fname">First name:</label>
	<input type="text" id="fname" name="fname">
	<br>
	<br>
	<label for="lname">Last name:</label>
	<input type="text" id="lname" name="lname">
	<br>
	<br>

	<input type="button" id="login" onclick="location.href='/home';" value="Login" />
</body>
</html>
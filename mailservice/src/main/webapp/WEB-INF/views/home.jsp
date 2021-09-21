<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>This is Home Page</h1>

<form action="mailto:contact@yourdomain.com"
method="POST"
enctype="multipart/form-data"
name="EmailForm">
    From:<br>
    <input type="text" id="fromId" size="19" name="fromId"><br><br>
    To:<br>
    <input type="email" id="toId" name="toId"><br><br> 
    
     Subject:<br>
    <input type="text" id="subject" name="subject" size="50"><br><br>
    
   <label for="myfile">Attachement</label>
  <input type="file" id="myfile" name="myfile"><br><br>
    Message:<br> 
  <%--   <textarea name="Contact-Message" cols="100" rows="20″ > --%>
    <textarea name="Contact-Message"  id="mailBody"rows="4" cols="50" maxlength="10000">
    </textarea><br><br> 
    <button type="submit" value="Submit">Send</button>
    <input type="button" id="logout" onclick="location.href='/index';" value="Logout" />
</form>
</body>
</html>
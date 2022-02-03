<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
</head>
<body>
<%
String user = "";
response.setHeader("Cache-Control", "no-chache, no-store, must-revalidate");
if(session.getAttribute("username") != null){
	user = session.getAttribute("username").toString();
	out.println("<h1>Hi "+user+"</h1>");
} 
else {
	response.sendRedirect("Login.jsp");
}%>
<a href = "logout">logout</a>
</body>
</html>
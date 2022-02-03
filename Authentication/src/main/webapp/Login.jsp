<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
</head>
<body>
<%	
	if(session.getAttribute("loginerror") != null ){
		String error = session.getAttribute("loginerror").toString();
		out.println(error);
		session.removeAttribute("loginerror");
	}
%>
<form action="login" method="post">
UserName: <input type="text" name="username"><br>
Password: <input type="text" name="password"><br>
<input type="submit">
</form>
<a href="Register.jsp">To Create New Account</a>
</body>
</html>
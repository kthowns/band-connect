<%@page import="entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@page import="entity.User"%>
<%@page import="java.util.List"%>
<%
	User user = (User) request.getAttribute("user");
	List<User> users = (List<User>) request.getAttribute("users");
	out.print(user.getUsername());
	out.print(users.toString());
%>
</body>
</html>
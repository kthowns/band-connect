<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>New Recruit</title>
</head>
<body>
	<form method="post" action="/newRecruit">
		<label for="title">Title : </label>
		<input type="text" name = "title" placeholder="title"><br>
		<label for="content">Content : </label>
		<input type="text" name = "content" placeholder="content"><br>
		<label for="title">Title : </label>
		<input type="text" name = "title" placeholder="title"><br>
		<label for="part">Part : </label>
		<input type="text" name = "part" placeholder="part"><br>
		<input type="submit" value="작성">
	</form>
</body>
</html>
<%@page import="entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login - BandConnect</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>
	<header class="header">
		<div class="logo">🎵 BandConnect</div>
		<nav>
			<div class="nav-links">
				<a href="/main">홈</a> <a href="/login">로그인/회원가입</a>
			</div>
		</nav>
	</header>

	<form class="form" method="post" action="/login">
		<label for="username">아이디</label> <input type="text" id="username"
			name="username" required> <label for="password">비밀번호</label>
		<input type="password" id="password" name="password" required>

		<button type="submit" id="loginButton">로그인</button>

		<script>
		<%
			String message = (String) request.getAttribute("message");
			if (message != null) {
		%>
			alert("<%=message%>");
		<%
			}
		%>
		</script>
		<!-- Sign-up 텍스트 -->
		<p class="sign-up-text">
			회원이 아니신가요? <a href="/register">회원가입 하기</a>
		</p>
	</form>


	<footer class="footer">
		<p>© 2024 BandConnect. All Rights Reserved.</p>
	</footer>
</body>
</html>
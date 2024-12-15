<%@page import="entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입 - BandConnect</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header class="header">
        <div class="logo">🎵 BandConnect</div>
        <nav>
            <div class="nav-links">
                <a href="/main">홈</a>
                <a href="/login">로그인/회원가입</a>
            </div>
        </nav>
    </header>

    <main>
        <section class="register-section">
            <form class="form" method="post" action="/register" id="registerForm">
                <h1>회원가입</h1>

                <label for="username">아이디</label>
                <input type="text" id="username" name="username" placeholder="아이디를 입력하세요" required>

                <label for="email">이메일</label>
                <input type="email" id="email" name="email" placeholder="이메일을 입력하세요" required>

                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" required>

                <label for="confirm-password">비밀번호 확인</label>
                <input type="password" id="confirm-password" name="confirm-password" placeholder="비밀번호를 다시 입력하세요" required>

                <button type="submit">회원가입</button>

                <p class="sign-up-text">이미 계정이 있으신가요? <a href="/login">로그인하기</a></p>
            </form>
        </section>
    </main>
    
		<script>
		<%
			String message = (String) request.getAttribute("message");
			if (message != null) {
		%>
			alert("<%=message%>");
		<%
			}
		%>
		
		document.getElementById("registerForm").addEventListener("submit", function(event) {
		    // 아이디 유효성 검사: 3자 이상
		    const username = document.getElementById("username").value;
		    if (username.length < 3) {
		        alert("아이디는 최소 3자 이상이어야 합니다.");
		        event.preventDefault(); // 폼 제출 막기
		        return;
		    }

		    // 이메일 유효성 검사
		    const email = document.getElementById("email").value;
		    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
		    if (!emailRegex.test(email)) {
		        alert("유효한 이메일 주소를 입력하세요.");
		        event.preventDefault(); // 폼 제출 막기
		        return;
		    }

		    // 비밀번호 유효성 검사: 최소 8자, 하나 이상의 숫자와 대소문자 포함
		    const password = document.getElementById("password").value;
		    const confirmPassword = document.getElementById("confirm-password").value;
		    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
		    if (!passwordRegex.test(password)) {
		        alert("비밀번호는 최소 8자 이상이고, 숫자와 문자를 포함해야 합니다.");
		        event.preventDefault(); // 폼 제출 막기
		        return;
		    }

		    // 비밀번호와 비밀번호 확인 일치 검사
		    if (password !== confirmPassword) {
		        alert("비밀번호가 일치하지 않습니다.");
		        event.preventDefault(); // 폼 제출 막기
		        return;
		    }
		});
		</script>

		</script>

    <footer class="footer">
        <p>© 2024 BandConnect. All Rights Reserved.</p>
    </footer>
</body>
</html>

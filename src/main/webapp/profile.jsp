<%@page import="entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<% User user = (User) session.getAttribute("user"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - BandConnect</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header class="header">
        <div class="logo">🎵 BandConnect</div>
        <nav>
            <div class="nav-links">
                <a href="/main">홈</a>
                <a href="/logout">로그아웃</a>
                <a href="/profile">내 프로필</a>
            </div>
        </nav>
    </header>
    <main class="profile-section">
        <h1>My Profile</h1>
        <div class="profile-info">
        <%if(user != null){%>
                <p><strong>이름:</strong> <%= user.getUsername() %></p>
                <p><strong>이메일:</strong> <%= user.getEmail() %></p>
        <%}else { response.sendRedirect("/main"); }%>
        </div>

        <div class="profile-actions">
            <a href="/myApl" class="action-link">지원 폼 목록</a>
            <a href="/myRecruit" class="action-link">구인 폼 목록</a>
            <a href="/myComment" class="action-link">내가 쓴 댓글 보기</a>
        </div>
    </main>

    <footer class="footer">
        <p>© 2024 BandConnect. All Rights Reserved.</p>
    </footer>
</body>
</html>

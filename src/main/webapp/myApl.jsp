<%@page import="entity.User"%>
<%@page import="entity.PostDetail"%>
<%@page import="entity.Recruit"%>
<%@page import="entity.Comment"%>
<%@page import="entity.Apl"%>
<%@page import="entity.Post"%>
<%@page import="entity.AplDetail"%>
<%@page import="code.ApplyStatus"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<% User user = (User) session.getAttribute("user"); %>
<% List<AplDetail> aplDetails = (List<AplDetail>) request.getAttribute("aplDetails"); %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>지원 폼 관리</title>
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
<% if(user!=null){ %>
    <main>
        <section class="my-posts-section">
            <h2>지원 폼 관리</h2>
            <ul class="post-list">
            <% for(AplDetail aplDetail : aplDetails){
            ApplyStatus status = aplDetail.getApl().getStatus();%>
            
                <li class="post-card <%= status == ApplyStatus.ACCEPTED ? "approved" : "pending" %>">
                    <h3><%= aplDetail.getPost().getTitle()%></h3>
                    <p><strong>지원 항목:</strong> <%= aplDetail.getRecruit().getPosition() %></p>
                    <p class="date"><strong>날짜:</strong> <%= aplDetail.getApl().getCreatedAt().toString().substring(0, 16) %></p>
                    <p><strong>승낙 상태:</strong> <%= status.getDescription() %></p>
                </li>
            <%}%>
            </ul>
        </section>
    </main>
<%}else { response.sendRedirect("/main"); }%>

    <footer class="footer">
        <p>© 2024 BandConnect. All Rights Reserved.</p>
    </footer>
</body>
</html>

<%@page import="entity.User" %>
<%@page import="entity.PostDetail" %>
<%@page import="entity.Recruit" %>
<%@page import="entity.Comment" %>
<%@page import="entity.Apl" %>
<%@page import="entity.Post" %>
<%@page import="entity.AplDetail" %>
<%@page import="code.ApplyStatus" %>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<% User user = (User) session.getAttribute("user"); %>
<% List<AplDetail> aplDetails = (List<AplDetail>) request.getAttribute("aplDetails"); %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>지원 폼 관리</title>
    <link rel="stylesheet" href="../static/css/styles.css">
    <link rel="stylesheet" href="../static/css/styles-myApl.css">
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
<% if (user != null) { %>
<main>
    <section class="my-posts-section">
        <h2 class="section-title">지원 폼 관리</h2>

        <% if (aplDetails == null || aplDetails.isEmpty()) { %>
        <div class="no-posts">
            <p>아직 지원한 내역이 없습니다.</p>
            <a href="/main" class="action-link-btn">밴드 구인글 보러가기</a>
        </div>
        <% } else { %>
        <ul class="post-list">
            <% for (AplDetail aplDetail : aplDetails) {
                ApplyStatus status = aplDetail.getApl().getStatus();
                // CSS 클래스 결정을 위한 로직
                String statusClass = "pending";
                if(status == ApplyStatus.ACCEPTED) statusClass = "approved";
                else if(status == ApplyStatus.REJECTED) statusClass = "rejected";
            %>
            <li class="post-card <%= statusClass %>">
                <h3><%= aplDetail.getPost().getTitle() %></h3>
                <p><strong>밴드:</strong> <%= aplDetail.getBand().getName() %></p>
                <p><strong>지원 파트:</strong> <%= aplDetail.getRecruit().getPosition() %></p>
                <span class="status-text"><%= status.getDescription() %></span>
                <span class="date">지원 일시: <%= aplDetail.getApl().getCreatedAt().toString().substring(0, 16) %></span>
            </li>
            <% } %>
        </ul>
        <% } %>
    </section>
</main>
<%
    } else {
        response.sendRedirect("/main");
    }
%>

<footer class="footer">
    <p>© 2024 BandConnect. All Rights Reserved.</p>
</footer>
</body>
</html>

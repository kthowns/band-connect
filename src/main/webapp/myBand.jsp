<%@page import="entity.*" %>
<%@page import="code.ApplyStatus" %>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>내 밴드 - BandConnect</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="styles-myBand.css">
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    List<PostDetail> posts = (List<PostDetail>) request.getAttribute("posts");
%>
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

<main>
    <section class="my-bands-section">
        <h2 class="section-title">내 밴드 현황</h2>

        <% if (posts == null || posts.isEmpty()) { %>
        <div class="no-posts">
            <p>소속된 밴드 활동이 없습니다.</p>
            <a href="/main" class="action-link-btn">밴드 찾으러 가기</a>
        </div>
        <% } else { %>
        <ul class="post-list">
            <% for (PostDetail post : posts) { %>
            <li class="post-card band-card">
                <div class="band-header">
                    <span class="band-label">BAND</span>
                    <h3><%= post.getBand().getName() %>
                    </h3>
                </div>

                <p class="post-title-link">연결된 공고: <strong><%= post.getTitle() %>
                </strong></p>

                <div class="member-section">
                    <p class="member-title">👥 활동 멤버</p>
                    <div class="member-grid">
                        <%-- 팀장(작성자) 정보 --%>
                        <div class="member-item leader">
                            <span class="role-badge">Leader</span>
                            <span class="name"><%=post.getBand().getLeader().getUsername() %></span>
                        </div>

                        <%-- 수락된 지원자(멤버)들 정보 --%>
                        <% for (Recruit r : post.getRecruits()) {
                            if (r.getAcceptedId() > 0) { %>
                        <div class="member-item">
                            <span class="role-badge"><%= r.getPosition() %></span>
                            <span class="name"><%= r.get%></span>
                        </div>
                        <% }
                        } %>
                    </div>
                </div>
            </li>
            <% } %>
        </ul>
        <% } %>
    </section>
</main>

<footer class="footer">
    <p>© 2026 BandConnect. All Rights Reserved.</p>
</footer>
</body>
</html>
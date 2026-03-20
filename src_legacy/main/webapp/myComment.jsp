<%@page import="entity.User"%>
<%@page import="entity.PostDetail"%>
<%@page import="entity.Comment"%>
<%@page import="entity.Post"%>
<%@page import="code.ApplyStatus"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>내가 쓴 댓글</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<% User user = (User) session.getAttribute("user");
	List<Comment> comments = (List<Comment>) request.getAttribute("comments"); %>
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
<% if(user != null){%>
    <main>
        <!-- 내가 쓴 댓글 섹션 -->
        <section class="my-comments-section">
            <h2>내가 쓴 댓글</h2>
            <ul class="comment-list">
            <% for(Comment comment : comments){%>
                <li class="comment-item" onClick="redirectToDetail('<%= comment.getPostId() %>')">
                    <h3><%= comment.getContent() %></h3>
                    <p class="date">작성 날짜: <%= comment.getCreatedAt().toString().substring(0, 16) %></p>
                </li>
            <%}%>
            </ul>
        </section>
    </main>
    <script>
	function redirectToDetail(postId){
        window.location.href = '/postDetail?id=' + encodeURIComponent(postId);
	}
    </script>
<% } else { response.sendRedirect("/main"); }%>

    <footer class="footer">
        <p>© 2024 BandConnect. All Rights Reserved.</p>
    </footer>
</body>
</html>

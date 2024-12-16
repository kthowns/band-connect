<%@page import="entity.User"%>
<%@page import="entity.PostDetail"%>
<%@page import="entity.Recruit"%>
<%@page import="entity.Comment"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<% User user = (User) session.getAttribute("user"); %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>구인 폼 관리 - BandConnect</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
	<% 
	if(user == null){
		response.sendRedirect("/main");
	}
	
	List<PostDetail> posts = (List<PostDetail>) session.getAttribute("posts");
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
        <section class="my-posts-section">
            <h2>구인 폼 관리</h2>
            <ul class="post-list">
                <!-- 게시글 1 -->
                <%
                	if(posts != null){
                		for(PostDetail post : posts){
                			%>
                <li class="post-card complete">
                    <h3><%= post.getTitle() %></h3>
                    <p><strong>작성 날짜:</strong> <%= post.getCreatedAt().toString().substring(0, 16) %></p>
                    <p><strong>세션:</strong></p>
                    <ul class="session-status">
                    <%
                    	for(Recruit recruit : post.getRecruits()){ %>
                        <li><%= recruit.getPosition() %> 
                        <span class="status complete">
                        <% String state = "모집 중";
                        if(recruit.getAcceptedId() > 0) {
                        	System.out.println(recruit.getPosition() + " / "+ recruit.getAcceptedId());
                        	state = "완료";
                        }
                        %>
                        <%= state %>
                        </span></li>
                    	<%}
                    %>
                    </ul>
                </li>
                			<%
                		}
                	}
                %>
            </ul>
        </section>
    </main>

    <footer class="footer">
        <p>© 2024 BandConnect. All Rights Reserved.</p>
    </footer>
</body>
</html>

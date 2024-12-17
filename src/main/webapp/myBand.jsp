<%@page import="entity.User"%>
<%@page import="entity.PostDetail"%>
<%@page import="entity.MemberDetail"%>
<%@page import="entity.Recruit"%>
<%@page import="entity.Comment"%>
<%@page import="entity.Apl"%>
<%@page import="entity.Band"%>
<%@page import="entity.Post"%>
<%@page import="entity.AplDetail"%>
<%@page import="code.ApplyStatus"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.UserService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>내 밴드 - BandConnect</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<% List<Apl> apls = (List<Apl>) request.getAttribute("apls");
 List<Band> bands = (List<Band>) request.getAttribute("bands");
 List<PostDetail> posts = (List<PostDetail>) request.getAttribute("posts");%>
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
            <h2>내 밴드</h2>
            <% if(posts!=null){ 
            	for(PostDetail post : posts){
            		for(Apl apl : apls){
            			if(post.getBand().getId()==apl)
            		}
            	%>
            <ul class="post-list">
                <li class="post-card complete" onclick="showBandDetails('드라코스', '홍길동/24/010-1234-1234', '이수민/22/010-9876-5432', '', '', '홍길동')">
                    <h3><%= post.getTitle() %></h3>
                    <p><strong>밴드 설명:</strong> <%= post.getContent() %></p>
                    <p><%= members.toString() %></p>
                </li>
            </ul>
            <% }
            } %>
        </section>

    </main>
    <footer class="footer">
        <p>© 2024 BandConnect. All Rights Reserved.</p>
    </footer>

    </script>
</body>
</html>

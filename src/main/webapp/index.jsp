<%@page import="entity.User"%>
<%@page import="entity.PostDetail"%>
<%@page import="entity.Recruit"%>
<%@page import="entity.Comment"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BandConnect</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>
	<%
	User user = (User) session.getAttribute("user");
	List<PostDetail> posts = (List<PostDetail>) session.getAttribute("posts");
	%>
	<header class="header">
		<div class="logo">🎵 BandConnect</div>
		<nav>
			<div class="nav-links">
				<a href="/main">홈</a>
				<%
					if(user != null){
						%>
						<a href="/logout">로그아웃</a>
				<a href="/profile">내 프로필</a>
						<%
					} else {
				%>
				<a href="/login">로그인/회원가입</a> 
				<%
					}
				%>
			</div>
		</nav>
	</header>
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
	<main>
		<!-- Search Section -->
		<section id="search" class="section">
			<h2>태그 검색</h2>
			<div class="tag-search">
				<input type="text" id="tag" name="tag" placeholder="태그를 입력하세요 #기타"
					required>
				<button class="tag-search-btn">검색</button>
			</div>
			<div class="card-container">
				<%
					if(user != null){
						%>
						<button onclick="window.location.href='/newRecruit'">글 작성</button>
						<%
					} 
				%>
				<!-- 게시글 1 -->
				<%
				if(posts != null){
					for(PostDetail post : posts){
				%>
					<div class="card">
						<h3><%= post.getTitle() %></h3>
						<p>
							<strong>세션:</strong>
							<%
								for(Recruit recruit : post.getRecruits()){
									%>
									<%= recruit.getPosition() + " " %>
									<%
								}
							int commentSize = 0;
							if(post.getComments()!=null)
								commentSize = post.getComments().size();
							%>
						</p>
						<p class="comment-count">댓글(<%= commentSize %>)</p>
						<p class="date">작성 날짜: <%= post.getCreatedAt().toString().substring(0, 16) %></p>
					</div>	
				<%
				}}
				%>
				<!-- 게시글 3 -->
				<div class="card">
					<h3>Pop Group Guitarist Wanted</h3>
					<p>
						<strong>세션:</strong> 기타2(완료)
					</p>
					<p class="comment-count">댓글(15)</p>
					<p class="date">작성 날짜: 2024-11-28</p>
				</div>
			</div>
		</section>
	</main>

	<footer class="footer">
		<p>© 2024 BandConnect. Connecting Musicians Everywhere!</p>
	</footer>
</body>
</html>

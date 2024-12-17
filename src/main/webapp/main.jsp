<%@page import="entity.User"%>
<%@page import="entity.PostDetail"%>
<%@page import="entity.Recruit"%>
<%@page import="entity.Comment"%>
<%@page import="entity.Hashtag"%>
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
	List<PostDetail> posts = (List<PostDetail>) request.getAttribute("posts");
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
		<% String message = (String) session.getAttribute("message");
			if (message != null) {%>
			alert("<%=message%>");
		<% session.setAttribute("message", null);}%>
		</script>
	<main>
		<!-- Search Section -->
		<section id="search" class="section">
			<h2>태그 검색</h2>
			<div class="tag-search">
			<form method="post" action="/search">
				<input type="text" id="tag" name="tag" placeholder="태그를 입력하세요 #태그"
					required>
				<button type="submit" class="tag-search-btn">검색</button>
			</form>
			</div>
			<div class="card-container">
				<%
					if(user != null){
						%>
						<button class="comment-inline-submit-btn" onclick="window.location.href='/newRecruit'">글 작성</button>
						<%
					} 
				%>
				<!-- 게시글 1 -->
				<%
				if(posts != null){
					for(PostDetail post : posts){
			            String hashtag = "";
						if(post.getHashtags().size()>0){
			            	for(Hashtag tag : post.getHashtags()){
			            		hashtag += tag.getHashtag() + " ";
			            	}
						}
		            %>
					<div class="card clickable" onClick="redirectToDetail('<%= post.getPostId() %>')">
						<h3><%= post.getTitle() %></h3> <div class="hashtag"> <%= hashtag %> </div>
						<p><strong>밴드 : <%= post.getBand().getName() %></strong></p>
						<p>
							<strong>세션:</strong>
							<%
								for(Recruit recruit : post.getRecruits()){
									%>
									<%= recruit.getPosition() + " " %>
									<%
								}
							int commentSize = 0;
							if(post.getCommentDetails()!=null)
								commentSize = post.getCommentDetails().size();
							%>
						</p>
						<p class="comment-count">댓글(<%= commentSize %>) 조회수(<%= post.getViews() %>)</p>
						<p class="date">작성 날짜: <%= post.getCreatedAt().toString().substring(0, 16) %></p>
					</div>	
				<%
				}}
				%>
			</div>
		</section>
	</main>
	<script>
		function redirectToDetail(postId){
	        window.location.href = '/postDetail?id=' + encodeURIComponent(postId);
		}
	</script>
	<footer class="footer">
		<p>© 2024 BandConnect. Connecting Musicians Everywhere!</p>
	</footer>
</body>
</html>

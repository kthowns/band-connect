<%@page import="entity.User"%>
<%@page import="entity.PostDetail"%>
<%@page import="entity.Recruit"%>
<%@page import="entity.Comment"%>
<%@page import="entity.Hashtag"%>
<%@page import="entity.CommentDetail"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 - BandConnect</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
	<%
	User user = (User) session.getAttribute("user");
	PostDetail postDetail = (PostDetail) request.getAttribute("postDetail");
	Integer applicantNumber = (Integer) request.getAttribute("applicantNumber");
	System.out.println(postDetail);
	if(postDetail==null){
		response.sendRedirect("/main");
	}
	%>
    <header class="header">
        <div class="logo">🎵 BandConnect</div>
        <nav>
            <div class="nav-links">
				<a href="/main">홈</a>
				<%if(user != null){%>
					<a href="/logout">로그아웃</a>
					<a href="/profile">내 프로필</a>
				<%} else {%>
				<a href="/login">로그인/회원가입</a> 
				<%}%>
            </div>
        </nav>
    </header>
    <script>
    	<% String message = (String) session.getAttribute("message");
    		if(message != null){%>
    		alert("<%= message %>");
    	<%session.setAttribute("message", null); }%>
    </script>
    
        <% request.setCharacterEncoding("UTF-8"); %>
<% if(postDetail != null){%>
	
    <main class="post-container">
        <!-- 게시글 카드 -->
        <section class="post-card">
            <h1 class="post-title"><%= postDetail.getTitle() %></h1>
            <% if(user != null && postDetail.getAuthorId() == user.getId()){ %>
            <form method="post" action="/remove">
            	<input type="hidden" name="cls" value="Post">
            	<input type="hidden" name="postId" value="<%= postDetail.getPostId() %>">
            	<button type="submit">삭제</button>
            </form>
            <% } %>
            <div class="post-meta">
                <span class="date">작성일: <%= postDetail.getCreatedAt().toString().substring(0, 16) %></span> | 
                <span class="views">조회수: <%= postDetail.getViews() %></span> | 
                <span class="applicants">지원자 수: <%= applicantNumber %></span>
				<p><strong>밴드명 : <%= postDetail.getBand().getName() %></strong></p>
            </div>
            <p class="post-content">
				<%= postDetail.getContent() %>
            </p>
            <% String hashtag = "";
            	for(Hashtag tag : postDetail.getHashtags()){
            		hashtag += tag.getHashtag() + " ";
            	}
            %>
            <div class="hashtag"><%= hashtag %></div>
            <ul class="session-status">
                    <%
                    	for(Recruit recruit : postDetail.getRecruits()){ 
                    		Boolean state = recruit.getAcceptedId() > 0;%>
                        <li><%= recruit.getPosition() %> 
                        <span class="status <%= state ? "complete" : "" %>">
                        <%= state ? "마감" : "모집 중" %>
                        </span></li>
                    	<%}
                    %>
            </ul>
            	<button class="apply-btn" onclick="openModal()">지원하기</button>
        </section>

        <!-- 댓글 섹션 -->
        <section class="comments-section">
            <h2>댓글</h2>
            <% if(user!=null){ %>
	            <form method="post" action="/comment">
	            <input type="text" name="content" id="comment-inline" class="comment-inline-input" placeholder="댓글을 입력하세요" required>
	            <input type="hidden" name="postId" value=<%= postDetail.getPostId() %>>
	            <input type="hidden" name="authorId" value=<%= user.getId() %>>
	            <button type="submit" class="comment-inline-submit-btn">게시</button>
            </form>
            <% } %>
            <div class="comments-list">
            <% for(CommentDetail commentDetail : postDetail.getCommentDetails()){%>
                <div class="comment">
                <strong><%= commentDetail.getAuthor().getUsername() %>:</strong> <%= commentDetail.getContent() %>
            	<p><%= commentDetail.getCreatedAt().toString().substring(0, 16) %></p>
            	<%if(user!=null && commentDetail.getAuthor().getId() == user.getId()){ %>
            	<form method="post" action="/remove">
            	<input type="hidden" name="cls" value="Comment">
            	<input type="hidden" name="postId" value="<%= postDetail.getPostId() %>">
            	<input type="hidden" name="commentId" value="<%= commentDetail.getId() %>">
            	<button type="submit">삭제</button>
            	</form>
            	<% } %>
            	</div>
            <%}%>
            </div>
        </section>
    </main>
		
    <div id="applyModal" class="modal">
        <div class="modal-content">
            <span class="close-btn" onclick="closeModal()">&times;</span>
            <h2>지원 폼</h2>
            <form id="application-form" class="form" method="post" action="/apply">
                <label for="name">이름</label>
                <input type="text" id="name" name="name" placeholder="이름을 입력하세요" required>
                <label for="age">나이</label>
                <input type="number" id="age" name="age" placeholder="나이를 입력하세요" required>
                <label for="location">지역</label>
                <input type="text" id="location" name="location" placeholder="지역을 입력하세요" required>
                <label for="position">파트</label>
                <select id="position" name="position" required>
                <%
                	for(Recruit recruit : postDetail.getRecruits()){
                		if(recruit.getAcceptedId() == 0){%>
                            <option value="<%= recruit.getPosition() %>"> <%= recruit.getPosition() %> </option>
                		<%}
                	}
                %>
                </select>
                <label for="phone">전화번호</label>
                <input type="tel" id="phone" name="phone" placeholder="전화번호를 입력하세요" required>
                <label for="description">소개</label>
                <textarea id="description" name="description" rows="4" placeholder="자기소개를 입력하세요" required></textarea>
    			<input type="hidden" name="postId" value="<%= postDetail.getPostId() %>">
    			<input type="hidden" name="bandId" value="<%= postDetail.getBand().getId() %>">
    			<%
					Integer userId = 0;
    				if(user != null){
    					userId = user.getId();
    				}
    			%>
    			<input type="hidden" name="applicantId" value="<%= userId %>">
                <button type="submit">제출하기</button>
            </form>
        </div>
    </div>

	<%}%>
    <footer class="footer">
        <p>© 2024 BandConnect. All Rights Reserved.</p>
    </footer>
<script>
    function openModal() {
        <% if(user != null) { %>
        document.getElementById('applyModal').style.display = 'block';
    <% } else { %>
        alert("<%= "로그인 후 이용해주세요." %>");
    <% } %>
    }

    function closeModal() {
        document.getElementById('applyModal').style.display = 'none';
    }
</script>


</body>
</html>

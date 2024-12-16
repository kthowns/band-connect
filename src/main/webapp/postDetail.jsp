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
    		if(message != null){%>
    		alert("<%= message %>");
    	<%session.setAttribute("message", null); }%>
    </script>
<% if(postDetail != null){%>
	
    <main class="post-container">
        <!-- 게시글 카드 -->
        <section class="post-card">
            <h1 class="post-title"><%= postDetail.getTitle() %></h1>
            <div class="post-meta">
                <span class="date">작성일: <%= postDetail.getCreatedAt().toString().substring(0, 16) %></span> | 
                <span class="views">조회수: <%= postDetail.getViews() %></span> | 
                <span class="applicants">지원자 수: <%= applicantNumber %></span>
				<p><strong>밴드명 : <%= postDetail.getBand().getName() %></strong></p>
            </div>
            <p class="post-content">
				<%= postDetail.getContent() %>
            </p>
            <ul class="session-status">
                    <%
                    	for(Recruit recruit : postDetail.getRecruits()){ %>
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
            	<button class="apply-btn" onclick="openModal()">지원하기</button>
        </section>

        <!-- 댓글 섹션 -->
        <section class="comments-section">
            <h2>댓글</h2>
            <% if(user!=null){ %>
            <input type="text" id="comment-inline" class="comment-inline-input" placeholder="댓글을 입력하세요">
            <button class="comment-inline-submit-btn">게시</button>
            <% } %>
            <div class="comments-list">
                <div class="comment">
                    <strong>홍길동:</strong> 저 관심 있습니다! 연락주세요.
                </div>
                <div class="comment">
                    <strong>김철수:</strong> 지원하고 싶습니다. 나이는 상관없나요?
                </div>
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

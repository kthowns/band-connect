<%@page import="entity.User"%>
<%@page import="entity.PostDetail"%>
<%@page import="entity.Recruit"%>
<%@page import="entity.Apl"%>
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
	
	List<PostDetail> posts = (List<PostDetail>) request.getAttribute("posts");
	List<Apl> apls = (List<Apl>) request.getAttribute("apls");
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
                <%if(posts != null && user != null){
                		for(PostDetail post : posts){%>
                <li class="post-card complete">
                    <h3 onClick="redirectToDetail('<%= post.getPostId() %>')"><%= post.getTitle() %></h3>
                    <p><strong><%= post.getBand().getName() %></strong></p>
                    <p><strong>작성 날짜:</strong> <%= post.getCreatedAt().toString().substring(0, 16) %></p>
                    <p><strong>파트:</strong></p>
                    <ul class="session-apl">
                    <% for(Recruit recruit : post.getRecruits()){ 
                    	Boolean state = false;
                        state = recruit.getAcceptedId() > 0; %>
                        <li><%= recruit.getPosition() %> 
                        <span class="status <%= state ? "complete" : "" %>">
                        <%= state ? "마감" : "모집 중" %> </span>
                        	<ul class="session-apl-item">
	                        <% if(!state){ 
	                        	for(Apl apl : apls){
	                        		if(apl.getRecruitId() == recruit.getId()){%>
	                                <li><div class="applicant-name" onclick="showApplicantDetails(
	                                		'<%= apl.getRecruitId() %>',
	                                		'<%= apl.getApplicantId() %>',
	                                		'<%= apl.getName() %>', 
	                                		'<%= apl.getAge() %>', 
	                                		'<%= apl.getLocation() %>',
	                                		'<%= apl.getPhone() %>', 
	                                		'<%= apl.getDescription() %>'
	                                		)"><%= apl.getName() %></div></li>
	                        		<% }
	                        		}
	                        	} %>
                       		</ul>
                       </li>
                    	<%}%>
                    </ul>
                </li>
                			<%} } else { response.sendRedirect("/main"); }%>
            </ul>
        </section>
    </main>
    <script>
    	function redirectToDetail(postId){
    		window.location.href = "/postDetail?id="+encodeURIComponent(postId);
    	}
    </script>
    <div id="applicant-modal" class="modal">
        <div class="modal-content">
            <button class="close-btn" onclick="closeModal()">×</button>
            <h3>지원자 상세 내용</h3>
            <p><strong>이름:</strong> <span id="applicant-name"></span></p>
            <p><strong>나이:</strong> <span id="applicant-age"></span></p>
            <p><strong>지역:</strong> <span id="applicant-location"></span></p>
            <p><strong>전화번호:</strong> <span id="applicant-phone"></span></p>
            <p><strong>소개:</strong> <span id="applicant-description"></span></p>
            <form method="post" action="/decideApl">
            <input type="hidden" name="decision" value="accept">
            <input type="hidden" name="recruitId" id="accepted-recruitId">
            <input type="hidden" name="applicantId" id="accepted-applicantId">
            <button type="submit" class="accept-btn">수락</button>
            </form>
            <form method="post" action="/decideApl">
            <input type="hidden" name="decision" value="reject">
            <input type="hidden" name="recruitId" id="rejected-recruitId">
            <input type="hidden" name="applicantId" id="rejected-applicantId">
            <button type="submit" class="reject-btn">거절</button>
            </form>
        </div>
    </div>

    <footer class="footer">
        <p>© 2024 BandConnect. All Rights Reserved.</p>
    </footer>

    <script>
        function showApplicantDetails(recruitId, applicantId, name, age, location, phone, description) {
            document.getElementById('applicant-name').textContent = name;
            document.getElementById('applicant-age').textContent = age;
            document.getElementById('applicant-location').textContent = location;
            document.getElementById('applicant-phone').textContent = phone;
            document.getElementById('applicant-description').textContent = description;
            document.getElementById('applicant-modal').style.display = 'flex';
            document.getElementById('accepted-recruitId').value = recruitId;
            document.getElementById('accepted-applicantId').value = applicantId;
            document.getElementById('rejected-recruitId').value = recruitId;
            document.getElementById('rejected-applicantId').value = applicantId;
        }

        function closeModal() {
            document.getElementById('applicant-modal').style.display = 'none';
        }
    </script>
</body>
</html>
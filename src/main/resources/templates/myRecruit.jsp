<%@page import="entity.User" %>
<%@page import="entity.PostDetail" %>
<%@page import="entity.Recruit" %>
<%@page import="entity.Apl" %>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<% User user = (User) session.getAttribute("user"); %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>구인 폼 관리 - BandConnect</title>
    <link rel="stylesheet" href="../static/css/styles.css">
    <link rel="stylesheet" href="../static/css/styles-myRecruit.css">
</head>
<body>
<%
    if (user == null) {
        response.sendRedirect("/main");
        return;
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
        <h2 class="section-title">구인 폼 관리</h2>

        <%-- [Empty State 1] 작성한 게시글이 아예 없는 경우 --%>
        <% if (posts == null || posts.isEmpty()) { %>
        <div class="no-posts">
            <p>작성한 구인 게시글이 없습니다.</p>
            <a href="/newRecruit" class="action-link-btn">첫 구인글 작성하기</a>
        </div>
        <% } else { %>
        <ul class="post-list">
            <% for (PostDetail post : posts) { %>
            <li class="post-card">
                <h3 class="post-title" onClick="redirectToDetail('<%= post.getPostId() %>')"><%= post.getTitle() %>
                </h3>
                <p class="band-name"><strong><%= post.getBand().getName() %>
                </strong></p>
                <p class="post-date"><strong>작성일:</strong> <%= post.getCreatedAt().toString().substring(0, 16) %>
                </p>

                <div class="recruit-container">
                    <p class="recruit-subtitle"><strong>파트별 모집 현황</strong></p>
                    <ul class="session-application">
                        <% for (Recruit recruit : post.getRecruits()) {
                            boolean isClosed = recruit.getAcceptedId() > 0; %>
                        <li class="session-item">
                            <div class="session-header">
                                <span class="position-name"><%= recruit.getPosition() %></span>
                                <span class="status-tag <%= isClosed ? "complete" : "in-progress" %>">
                                        <%= isClosed ? "마감" : "모집 중" %>
                                    </span>
                            </div>

                            <ul class="applicant-list">
                                <%
                                    boolean hasApplicant = false;
                                    if (apls != null) {
                                        for (Apl application : apls) {
                                            if (application.getRecruitId().equals(recruit.getId())) {
                                                hasApplicant = true;
                                                String statusName = application.getStatus().name();
                                                String itemClass = "applicant-name";

                                                if (statusName.equals("ACCEPTED")) itemClass += " accepted decided";
                                                else if (statusName.equals("REJECTED"))
                                                    itemClass += " rejected decided";
                                                else if (isClosed) itemClass += " disabled decided";
                                %>
                                <li>
                                    <div class="<%= itemClass %>"
                                         onclick="showApplicantDetails(
                                                 '<%= application.getRecruitId() %>',
                                                 '<%= application.getApplicantId() %>',
                                                 '<%= application.getName() %>',
                                                 '<%= application.getAge() %>',
                                                 '<%= application.getLocation() %>',
                                                 '<%= application.getPhone() %>',
                                                 '<%= application.getDescription() %>',
                                                 '<%= application.getStatus().getDescription() %>',
                                                 '<%= application.getStatus().name() %>'
                                                 )">
                                        👤 <%= application.getName() %>
                                        <% if (statusName.equals("ACCEPTED")) { %> <span
                                            class="mini-tag">✔️</span> <% } %>
                                        <% if (statusName.equals("REJECTED")) { %> <span
                                            class="mini-tag reject">❌</span> <% } %>
                                    </div>
                                </li>
                                <% }
                                }
                                }

                                    // [Empty State 2] 해당 파트에 지원자가 없는 경우
                                    if (!hasApplicant) { %>
                                <li class="empty-msg">아직 지원자가 없습니다.</li>
                                <% } %>
                            </ul>
                        </li>
                        <%}%>
                    </ul>
                </div>
            </li>
            <% } %>
        </ul>
        <% } %>
    </section>
</main>

<div id="applicant-modal" class="modal">
    <div class="modal-content">
        <button class="close-btn" onclick="closeModal()">×</button>
        <h3 class="modal-title">지원자 상세 정보</h3>

        <div class="status-badge-container">
            <span id="applicant-status" class="status-badge"></span>
        </div>

        <div class="info-group">
            <p><strong>이름:</strong> <span id="applicant-name"></span></p>
            <p><strong>나이:</strong> <span id="applicant-age"></span>세</p>
            <p><strong>지역:</strong> <span id="applicant-location"></span></p>
            <p><strong>연락처:</strong> <span id="applicant-phone"></span></p>
            <div class="desc-box">
                <strong>자기소개</strong>
                <p id="applicant-description"></p>
            </div>
        </div>

        <div id="modal-actions" class="modal-footer">
            <form method="post" action="/decideApl" class="action-form">
                <input type="hidden" name="decision" value="accept">
                <input type="hidden" name="recruitId" id="accepted-recruitId">
                <input type="hidden" name="applicantId" id="accepted-applicantId">
                <button type="submit" class="accept-btn">수락</button>
            </form>
            <form method="post" action="/decideApl" class="action-form">
                <input type="hidden" name="decision" value="reject">
                <input type="hidden" name="recruitId" id="rejected-recruitId">
                <input type="hidden" name="applicantId" id="rejected-applicantId">
                <button type="submit" class="reject-btn">거절</button>
            </form>
        </div>
    </div>
</div>

<footer class="footer">
    <p>© 2024 BandConnect. All Rights Reserved.</p>
</footer>

<script>
    function redirectToDetail(postId) {
        window.location.href = "/postDetail?id=" + encodeURIComponent(postId);
    }

    function showApplicantDetails(recruitId, applicantId, name, age, location, phone, description, statusDesc, statusName) {
        document.getElementById('applicant-name').textContent = name;
        document.getElementById('applicant-age').textContent = age;
        document.getElementById('applicant-location').textContent = location;
        document.getElementById('applicant-phone').textContent = phone;
        document.getElementById('applicant-description').textContent = description;

        const statusBadge = document.getElementById('applicant-status');
        statusBadge.textContent = statusDesc;
        statusBadge.className = 'status-badge ' + statusName.toLowerCase();

        const actionButtons = document.getElementById('modal-actions');
        // APPLIED(지원중) 상태일 때만 버튼 보이기
        actionButtons.style.display = (statusName === 'APPLIED') ? 'flex' : 'none';

        document.getElementById('applicant-modal').style.display = 'flex';
        document.getElementById('accepted-recruitId').value = recruitId;
        document.getElementById('accepted-applicantId').value = applicantId;
        document.getElementById('rejected-recruitId').value = recruitId;
        document.getElementById('rejected-applicantId').value = applicantId;
    }

    function closeModal() {
        document.getElementById('applicant-modal').style.display = 'none';
    }

    window.onclick = function (event) {
        if (event.target == document.getElementById('applicant-modal')) {
            closeModal();
        }
    }
</script>
</body>
</html>
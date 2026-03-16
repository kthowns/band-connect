<%@page import="entity.User"%>
<%@page import="entity.PostDetail"%>
<%@page import="entity.Recruit"%>
<%@page import="entity.Comment"%>
<%@page import="entity.Hashtag"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BandConnect - 뮤지션 매칭 플랫폼</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="styles/styles-main.css">
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
            <% if(user != null) { %>
            <a href="/logout">로그아웃</a>
            <a href="/profile">내 프로필</a>
            <% } else { %>
            <a href="/login">로그인/회원가입</a>
            <% } %>
        </div>
    </nav>
</header>

<script>
    <% String message = (String) session.getAttribute("message");
       if (message != null) { %>
    alert("<%= message %>");
    <% session.setAttribute("message", null);
    } %>
</script>

<main>
    <section id="search" class="section">
        <h2>🎸 우리 밴드에 꼭 맞는 멤버 찾기</h2>
        <div class="tag-search">
            <form method="post" action="/search">
                <input type="text" id="tag" name="tag" placeholder="장르나 악기를 입력하세요 (예: #밴드 #기타)" required>
                <button type="submit" class="tag-search-btn">검색</button>
            </form>
        </div>

        <div class="card-container">
            <div class="content-header">
                <h3>최신 구인 소식</h3>
                <% if(user != null) { %>
                <button class="comment-inline-submit-btn" onclick="window.location.href='/newRecruit'">글 작성</button>
                <% } %>
            </div>

            <%-- [Empty State] 게시글이 없을 때 --%>
            <% if(posts == null || posts.isEmpty()) { %>
            <div class="no-posts">
                <p>등록된 구인글이 없습니다. 첫 소식을 올려보세요!</p>
            </div>
            <% } else {
                for(PostDetail post : posts) {
                    // 해시태그 문자열 가공
                    StringBuilder hashtagStr = new StringBuilder();
                    if(post.getHashtags() != null && !post.getHashtags().isEmpty()) {
                        for(Hashtag tag : post.getHashtags()) {
                            hashtagStr.append(tag.getHashtag()).append(" ");
                        }
                    }
            %>
            <div class="card clickable" onClick="redirectToDetail('<%= post.getPostId() %>')">
                <div class="card-title-row">
                    <h3><%= post.getTitle() %></h3>
                    <span class="hashtag"><%= hashtagStr.toString() %></span>
                </div>

                <p><strong>밴드명 :</strong> <%= post.getBand().getName() %></p>
                <p>
                    <strong>모집 파트:</strong>
                    <%
                        boolean hasRecruit = false;
                        for(Recruit recruit : post.getRecruits()) {
                            if(recruit.getAcceptedId() <= 0) { // 마감되지 않은 파트만 노출
                                hasRecruit = true;
                                out.print("<span class='recruit-badge'>" + recruit.getPosition() + "</span> ");
                            }
                        }
                        if(!hasRecruit) { out.print("<span class='status-tag complete'>모집 마감</span>"); }
                    %>
                </p>

                <div class="card-footer">
                    <span class="comment-count">댓글 <%= (post.getCommentDetails() != null ? post.getCommentDetails().size() : 0) %> · 조회수 <%= post.getViews() %></span>
                    <span class="date">작성일: <%= post.getCreatedAt().toString().substring(0, 16) %></span>
                </div>
            </div>
            <% } // end for
            } // end if %>
        </div>
    </section>
</main>

<script>
    function redirectToDetail(postId){
        window.location.href = '/postDetail?id=' + encodeURIComponent(postId);
    }
</script>

<footer class="footer">
    <p>© 2026 BandConnect. Connecting Musicians Everywhere!</p>
</footer>
</body>
</html>
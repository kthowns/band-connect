<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>게시글 작성 - BandConnect</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>
<%
	if(session.getAttribute("user") == null){
		response.sendRedirect("/main");
	}
%>
	<!-- 헤더 -->
	<header class="header">
		<div class="logo">🎵 BandConnect</div>
		<nav>
			<div class="nav-links">
				<a href="/main">홈</a> <a href="/login">로그아웃</a> <a
					href="/profile">내 프로필</a>
			</div>
		</nav>
	</header>

	<!-- 게시글 작성 섹션 -->
	<main class="post-write-container">
		<section class="card">
			<h1 class="card-title">게시글 작성</h1>

			<form method="POST" action="/newRecruit">
				<!-- POST 요청을 통해 서버로 전송 -->
				<!-- 제목 입력 -->
				<div class="card-field">
					<label for="title">제목</label>
					<div class="input-box">
						<span class="icon">📝</span> <input type="text" id="title"
							name="title" placeholder="제목을 입력하세요" required>
					</div>
				</div>
				<div class="card-field">
					<label for="bandName">밴드 이름</label>
					<div class="input-box">
						<input type="text" id="bandName"
							name="bandName" placeholder="밴드 이름"><br>
					</div>
				</div>

				<!-- 내용 입력 -->
				<div class="card-field">
					<label for="content">내용</label>
					<div class="textarea-box">
						<textarea id="content" name="content" placeholder="내용을 입력하세요"
							required></textarea>
					</div>
				</div>
				
				<div class="card-field">
					<label for="hashtag">태그</label>
					<div class="input-box">
						<input type="text" id="hashtag"
							name="hashtag" placeholder="#태그"><br>
					</div>
				</div>

				<!-- 구인 파트 (동적 추가/삭제) -->
				<div class="card-field">
					<label>구인 파트</label>
					<div id="part-list" class="part-list">
						<div class="part-box">
							<input type="text" class="part-input" name="parts[]"
								placeholder="구인 파트 항목" required>
						</div>
					</div>
					<div class="button-container">
						<button type="button" class="add-remove-btn" onclick="addPart()">+</button>
						<button type="button" class="add-remove-btn"
							onclick="removePart()">-</button>
					</div>
				</div>

				<!-- 제출 버튼 -->
				<div class="submit-section">
					<button type="submit" class="submit-btn">게시글 등록</button>
				</div>
			</form>
		</section>
	</main>

	<!-- 푸터 -->
	<footer class="footer">
		<p>© 2024 BandConnect. All Rights Reserved.</p>
	</footer>
	
	<script>
		<%
			if(request.getAttribute("message") != null){
				%>
				alert("<%= request.getAttribute("message") %>");
				<%
			}
		%>
	</script>

	<!-- JavaScript -->
	<script>
        function addPart() {
            const partList = document.getElementById('part-list');
            const newPartBox = document.createElement('div');
            newPartBox.className = 'part-box';
            newPartBox.innerHTML = `
                <input type="text" class="part-input" name="parts[]" placeholder="구인 파트 항목" required>
            `;
            partList.appendChild(newPartBox);
        }

        function removePart() {
            const partList = document.getElementById('part-list');
            const partBoxes = partList.getElementsByClassName('part-box');
            if (partBoxes.length > 1) {
                partList.removeChild(partBoxes[partBoxes.length - 1]);
            } else {
                alert('최소 하나의 파트는 남겨야 합니다.');
            }
        }
    </script>
</body>
</html>
<!-- 
<form method="post" action="/newRecruit" accept-charset="UTF-8">
	<label for="title">Title : </label> <input type="text" name="title"
		placeholder="title"><br> <label for="bandName">Band
		: </label> <input type="text" name="bandName" placeholder="밴드 이름"><br>
	<label for="content">Content : </label> <input type="text"
		name="content" placeholder="content"><br> <label
		for="part">Part : </label> <input type="text" name="part"
		placeholder="part"><br> <input type="submit" value="작성">
</form>-->
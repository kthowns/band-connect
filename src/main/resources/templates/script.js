// 회원가입 처리
document.addEventListener("DOMContentLoaded", function () {
    const registerForm = document.getElementById("registerForm");
    if (registerForm) {
        registerForm.addEventListener("submit", function (e) {
            e.preventDefault();

            const username = document.getElementById("username").value.trim();
            const email = document.getElementById("email").value.trim();
            const password = document.getElementById("password").value.trim();
            const confirmPassword = document.getElementById("confirm-password").value.trim();

            if (password !== confirmPassword) {
                alert("Passwords do not match. Please try again.");
                return;
            }

            let userData = JSON.parse(localStorage.getItem("userData")) || {};

            if (userData[username]) {
                alert("Username already exists. Please choose another.");
                return;
            }

            // 사용자 데이터 저장
            userData[username] = { email, password };
            localStorage.setItem("userData", JSON.stringify(userData));

            alert("Registration successful! Please log in to continue.");
            window.location.href = "login.html"; // 로그인 페이지로 리다이렉트
        });
    }
});


// 로그인 처리
document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.querySelector(".form");
    if (loginForm) {
        loginForm.addEventListener("submit", function (e) {
            e.preventDefault();

            const username = document.getElementById("username").value.trim();
            const password = document.getElementById("password").value.trim();

            const userData = JSON.parse(localStorage.getItem("userData")) || {};

            if (!userData[username] || userData[username].password !== password) {
                alert("Invalid username or password.");
                return;
            }

            // 로그인 상태 저장
            localStorage.setItem("isLoggedIn", "true");
            localStorage.setItem("currentUser", username);

            alert(`Welcome back, ${username}!`);
            window.location.href = "index.html"; // 메인 화면으로 이동
        });
    }
});


// 페이지 이동 함수
function navigateTo(page) {
    window.location.href = page;
}

// 이벤트 리스너를 통해 네비게이션 연결
document.addEventListener("DOMContentLoaded", function () {
    const navLinks = document.querySelectorAll(".nav-links a");
    navLinks.forEach(link => {
        link.addEventListener("click", function (e) {
            e.preventDefault(); // 기본 링크 동작 방지
            const targetPage = link.getAttribute("href"); // 링크의 href 속성 가져오기
            navigateTo(targetPage); // 해당 페이지로 이동
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const currentPage = window.location.pathname.split("/").pop(); // 현재 페이지 이름
    const navLinks = document.querySelectorAll(".nav-links a");

    navLinks.forEach(link => {
        if (link.getAttribute("href") === currentPage) {
            link.classList.add("active"); // 활성화 클래스 추가
        }
    });
});

// DOMContentLoaded 이벤트를 사용하여 페이지 로드 시 동작 정의
document.addEventListener("DOMContentLoaded", function () {
    const isLoggedIn = localStorage.getItem("isLoggedIn") === "true"; // 로그인 상태 확인
    const currentUser = localStorage.getItem("currentUser"); // 현재 사용자 확인
    const currentPage = window.location.pathname.split("/").pop(); // 현재 페이지 이름

    // 1. 특정 페이지에서만 로그인 상태 확인
    if (currentPage === "profile.html" && !isLoggedIn) {
        alert("Please log in to view your profile.");
        window.location.href = "login.html"; // 로그인 페이지로 리다이렉트
    }

    // 2. 네비게이션 링크 동적 업데이트
    const navLinks = document.querySelector(".nav-links");
    if (navLinks) {
        navLinks.innerHTML = `
            <li><a href="index.html">Home</a></li>
        `;

        if (isLoggedIn && currentUser) {
            navLinks.innerHTML += `
                <li><a href="profile.html">My Profile</a></li>
                <li><a href="#" id="logoutBtn">Logout</a></li>
            `;
        } else {
            navLinks.innerHTML += `
                <li><a href="login.html">Login/Register</a></li>
            `;
        }
    }

    // 3. 로그아웃 버튼 처리
    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function (e) {
            e.preventDefault();
            localStorage.removeItem("isLoggedIn");
            localStorage.removeItem("currentUser");
            alert("You have logged out.");
            window.location.href = "index.html";
        });
    }
});


// 프로필 페이지에서 사용자 정보 표시
document.addEventListener("DOMContentLoaded", function () {
    const currentPage = window.location.pathname.split("/").pop();
    if (currentPage === "profile.html") {
        const currentUser = localStorage.getItem("currentUser");
        const userData = JSON.parse(localStorage.getItem("userData"));

        if (currentUser && userData && userData[currentUser]) {
            const profileSection = document.querySelector(".profile-info");
            profileSection.innerHTML = `
                <p><strong>Username:</strong> ${currentUser}</p>
                <p><strong>Email:</strong> ${userData[currentUser].email}</p>
            `;
        }
    }
});

// My Profile 링크 클릭 시 로그인 상태 확인
document.addEventListener("DOMContentLoaded", function () {
    const profileLink = document.querySelector('a[href="profile.html"]');

    if (profileLink) {
        profileLink.addEventListener("click", function (e) {
            const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";

            if (!isLoggedIn) {
                e.preventDefault(); // 기본 링크 동작 방지
                alert("Please log in to view your profile.");
                window.location.href = "login.html"; // 로그인 페이지로 리다이렉트
            }
        });
    }
});

// 샘플 게시글 데이터
const posts = {
    1: {
        title: "Rock Band Audition",
        content: "We are looking for a talented guitarist to join our rock band. Apply now!"
    },
    2: {
        title: "Jazz Trio Needs Drummer",
        content: "Join our jazz band as a drummer. Auditions open!"
    }
};

// 게시글 상세 페이지로 이동
function navigateToPost(postId) {
    // 게시글 ID를 URL에 전달
    window.location.href = `post.html?postId=${postId}`;
}

// 상세 페이지 로딩
function loadPostDetails() {
    const params = new URLSearchParams(window.location.search);
    const postId = params.get("postId");

    if (posts[postId]) {
        document.getElementById("post-title").textContent = posts[postId].title;
        document.getElementById("post-content").textContent = posts[postId].content;
    } else {
        document.getElementById("post-details").innerHTML = "<p>Post not found.</p>";
        document.getElementById("application-form").style.display = "none";
    }
}

// 신청 폼 처리
document.addEventListener("DOMContentLoaded", function () {
    // 상세 페이지 게시글 로딩
    if (window.location.pathname.endsWith("post.html")) {
        loadPostDetails();
    }

    // 신청 폼 처리
    const form = document.getElementById("application-form");
    if (form) {
        form.addEventListener("submit", function (e) {
            e.preventDefault();
            const name = document.getElementById("name").value.trim();
            const email = document.getElementById("email").value.trim();
            const message = document.getElementById("message").value.trim();

            // 간단한 알림
            alert(`Application submitted!\nName: ${name}\nEmail: ${email}\nMessage: ${message}`);
            
            // 이후 서버 전송 코드 추가 가능
            form.reset(); // 폼 초기화
        });
    }
});

// 샘플 데이터
const userPosts = [
    { id: 1, title: "Rock Band Audition", date: "2024-11-20" },
    { id: 2, title: "Jazz Trio Needs Drummer", date: "2024-11-21" }
];

const userComments = [
    { id: 1, postId: 1, content: "Is this still open?", date: "2024-11-22" },
    { id: 2, postId: 2, content: "I sent my application!", date: "2024-11-23" }
];

// 페이지 로드 시 데이터 추가
document.addEventListener("DOMContentLoaded", function () {
    const postList = document.getElementById("post-list");
    const commentList = document.getElementById("comment-list");

    // 작성한 글 목록 추가
    if (postList) {
        userPosts.forEach(post => {
            const listItem = document.createElement("li");
            listItem.textContent = `${post.title} - ${post.date}`;
            postList.appendChild(listItem);
        });
    }

    // 작성한 댓글 목록 추가
    if (commentList) {
        userComments.forEach(comment => {
            const listItem = document.createElement("li");
            listItem.textContent = `On post ${comment.postId}: "${comment.content}" - ${comment.date}`;
            commentList.appendChild(listItem);
        });
    }
});

// 페이지 이동 함수
function navigateTo(page) {
    window.location.href = page;
}

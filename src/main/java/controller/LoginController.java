package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.User;
import service.UserService;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private final UserService userService = new UserService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.html");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(username+" / "+password);
		try {
			User user = userService.login(username, password);
			System.out.println(user.toString());
	        HttpSession session = request.getSession();
	        session.setAttribute("user", user);

	        // index.jsp로 이동
	        response.sendRedirect("/main");
		} catch (Exception e) {
            request.setAttribute("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);  // 로그인 페이지로 다시 전달
		}
	}
}
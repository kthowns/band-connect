package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import service.UserService;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
	private final UserService userService = new UserService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		User newUser = new User();
		newUser.setUsername(request.getParameter("username"));
		newUser.setEmail(request.getParameter("email"));
		newUser.setPassword(request.getParameter("password"));
		System.out.println("New User : " + newUser.toString());
		try {
			User createdUser = userService.addUser(newUser);
			request.setAttribute("message", "회원가입이 완료되었습니다. "+createdUser.getUsername()+"님, 로그인 해주세요");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			System.out.println(e);
			if(e.getMessage().equals("Duplicated username")) {
				request.setAttribute("message", "중복된 username 입니다.");
			} else if(e.getMessage().equals("Duplicated email")) {
				request.setAttribute("message", "중복된 email 입니다.");
			} else {
				request.setAttribute("message", e.getMessage());
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
			dispatcher.forward(request, response);
		}
	}
}
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
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/register.jsp");
		dispatcher.forward(req, res);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		User newUser = new User();
		newUser.setUsername(req.getParameter("username"));
		newUser.setEmail(req.getParameter("email"));
		newUser.setPassword(req.getParameter("password"));
		System.out.println("New User : " + newUser.toString());
		try {
			User createdUser = userService.addUser(newUser);
			req.setAttribute("message", "회원가입이 완료되었습니다. "+createdUser.getUsername()+"님, 로그인 해주세요");
			RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
			dispatcher.forward(req, res);
		} catch (Exception e) {
			System.out.println(e);
			if(e.getMessage().equals("Duplicated username")) {
				req.setAttribute("message", "중복된 username 입니다.");
			} else if(e.getMessage().equals("Duplicated email")) {
				req.setAttribute("message", "중복된 email 입니다.");
			} else {
				req.setAttribute("message", e.getMessage());
			}
			RequestDispatcher dispatcher = req.getRequestDispatcher("/register.jsp");
			dispatcher.forward(req, res);
		}
	}
}
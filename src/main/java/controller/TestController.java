package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import service.UserService;

@WebServlet("/test")
public class TestController extends HttpServlet {
	private UserService userService = new UserService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer userId = 1;
		try {
			User user = userService.getUserDetail(userId);
			List<User> users = userService.getUserAll();
			request.setAttribute("user", user);
            request.setAttribute("users", users);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/test.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

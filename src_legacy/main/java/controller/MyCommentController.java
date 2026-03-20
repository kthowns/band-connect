package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Comment;
import entity.User;
import service.CommentService;

@WebServlet("/myComment")
public class MyCommentController extends HttpServlet {
	private final CommentService commentService = new CommentService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		try{
			List<Comment> comments = commentService.getCommentByAuthorId(user.getId());
			request.setAttribute("comments", comments);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/myComment.jsp");
		dispatcher.forward(request, response);
	}
}
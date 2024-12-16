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

import entity.PostDetail;
import entity.User;
import service.PostService;

@WebServlet("/myRecruit")
public class MyRecruitController extends HttpServlet {
	private final PostService postService = new PostService();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		try{
			List<PostDetail> posts = postService.getPostDetailsByAuthorId(user.getId());
			req.setAttribute("posts", posts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = req.getRequestDispatcher("/myRecruit.jsp");
		dispatcher.forward(req, res);
	}
}

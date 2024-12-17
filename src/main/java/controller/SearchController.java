package controller;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.PostDetail;
import service.PostService;

@WebServlet("/search")
public class SearchController extends HttpServlet {
	private final PostService postService = new PostService();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			String hashtag = (String) request.getParameter("tag");
			List<PostDetail> posts = postService.searchByHashtag(hashtag);
			System.out.println(posts);
			request.setAttribute("posts", posts);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/main.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
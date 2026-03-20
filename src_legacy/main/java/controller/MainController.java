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

import entity.Post;
import entity.PostDetail;
import repository.PostRepository;
import service.PostService;
import service.UserService;

@WebServlet("/main")
public class MainController extends HttpServlet {
	private final UserService userService = new UserService();
	private final PostService postService = new PostService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		try {
			List<PostDetail> posts = postService.getPostDetails();
			for(PostDetail post : posts) {
				post.setHashtags(postService.getHashtagsByPostId(post.getPostId()));
			}
			request.setAttribute("posts", posts);
			System.out.println(posts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/main.jsp");
		dispatcher.forward(request, response);
	}
}
package controller;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.CommentService;
import service.PostService;

@WebServlet("/remove")
public class RemoveController extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		String cls = (String) request.getParameter("cls");
		try {
			if(cls.equals("Comment")) {
				CommentService commentService = new CommentService();
				Integer commentId = Integer.parseInt((String) request.getParameter("commentId"));
				Integer postId = Integer.parseInt((String) request.getParameter("postId"));
				commentService.deleteCommentById(commentId);
				response.sendRedirect("/postDetail?id="+postId);
			}else if(cls.equals("Post")) {
				PostService postService = new PostService();
				Integer postId = Integer.parseInt((String) request.getParameter("postId"));
				postService.deleteById(postId);
				response.sendRedirect("/main");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
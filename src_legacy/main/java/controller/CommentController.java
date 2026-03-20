package controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Comment;
import service.CommentService;

@WebServlet("/comment")
public class CommentController extends HttpServlet {
	private final CommentService commentService = new CommentService();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		try {
			Integer postId = Integer.parseInt((String) request.getParameter("postId"));
			Comment comment = new Comment();
			comment.setAuthorId(Integer.parseInt((String) request.getParameter("authorId")));
			comment.setPostId(postId);
			comment.setContent((String) request.getParameter("content"));
			commentService.createComment(comment);
			response.sendRedirect("/postDetail?id="+postId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

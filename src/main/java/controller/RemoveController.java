package controller;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Comment;
import service.CommentService;

@WebServlet("/remove")
public class RemoveController extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String cls = (String) request.getParameter("cls");
		try {
			if(cls.equals("Comment")) {
				CommentService commentService = new CommentService();
				Integer commentId = Integer.parseInt((String) request.getParameter("commentId"));
				Integer postId = Integer.parseInt((String) request.getParameter("postId"));
				commentService.deleteCommentById(commentId);
				response.sendRedirect("/postDetail?id="+postId);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
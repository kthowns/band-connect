package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.PostDetail;
import service.AplService;
import service.PostService;

@WebServlet("/postDetail")
public class PostDetailController extends HttpServlet {		
	private final PostService postService = new PostService();
	private final AplService aplService = new AplService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		Integer postId = 0;
		
		try {
			postId = Integer.parseInt((String) request.getParameter("id"));
			if(postId > 0) {
				PostDetail postDetail = postService.getPostDetailByPostId(postId);
				Integer applicantNumber = aplService.getApplicantNumber(postId);
				request.setAttribute("postDetail", postDetail);
				request.setAttribute("applicantNumber", applicantNumber);
				System.out.println("Applicant Number : "+applicantNumber);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/postDetail.jsp");
				postService.increaseViews(postId);
				dispatcher.forward(request, response);
			} else {
				response.sendRedirect("/main");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("/main");
		}
	}
}
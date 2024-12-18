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

import entity.Apl;
import entity.PostDetail;
import entity.User;
import service.AplService;
import service.PostService;

@WebServlet("/myRecruit")
public class MyRecruitController extends HttpServlet {
	private final PostService postService = new PostService();
	private final AplService aplService = new AplService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		try{
			List<PostDetail> posts = postService.getPostDetailsByAuthorId(user.getId());
			List<Apl> apls = aplService.getAplByPostAuthorId(user.getId());
			for(Apl apl : apls) {
				System.out.println(apl.getName() + " / " + apl.getApplicantId() + " / " + apl.getRecruitId());
			}
			request.setAttribute("posts", posts);
			request.setAttribute("apls", apls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/myRecruit.jsp");
		dispatcher.forward(request, response);
	}
}

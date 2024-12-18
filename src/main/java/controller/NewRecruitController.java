package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.User;
import service.BandService;
import service.PostService;
import service.RecruitService;

@WebServlet("/newRecruit")
public class NewRecruitController extends HttpServlet {
	private final RecruitService recruitService = new RecruitService();
	private final BandService bandService = new BandService();
	private final PostService postService = new PostService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		response.sendRedirect("/newRecruit.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		String title = (String) request.getParameter("title");
		String bandName = (String) request.getParameter("bandName");
		String content = (String) request.getParameter("content");
		String hashtag = (String) request.getParameter("hashtag");
		String[] parts = request.getParameterValues("parts[]");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		System.out.println(title + "/"+bandName+"/"+content+"/"+parts);
		try {
			postService.createPost(user.getId(), title, bandName, content, parts, hashtag);
		} catch (Exception e) {
			session.setAttribute("message", e.getMessage());
		}
		response.sendRedirect("/main");
	}
}
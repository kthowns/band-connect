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
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.sendRedirect("/newRecruit.jsp");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html; charset=UTF-8");
		String title = (String) req.getParameter("title");
		String bandName = (String) req.getParameter("bandName");
		String content = (String) req.getParameter("content");
		String[] parts = req.getParameterValues("parts[]");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		System.out.println(title + "/"+bandName+"/"+content+"/"+parts);
		try {
			postService.createPost(user.getId(), title, bandName, content, parts);
			res.sendRedirect("/main");
		} catch (Exception e) {
			req.setAttribute("message", e.getMessage());
		} finally {
			RequestDispatcher dispatcher = req.getRequestDispatcher("/newRecruit.jsp");
			dispatcher.forward(req, res);
		}
	}
}
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
		String part = (String) req.getParameter("part");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		System.out.println(title + "/"+bandName+"/"+content+"/"+part);
		try {
			postService.createPost(user.getId(), title, bandName, content, part);
			req.setAttribute("message", "구인글 등록에 성공했습니다.");
		} catch (RuntimeException e) {
			System.out.println(e);
			req.setAttribute("message", "구인글 등록에 실패했습니다.");
			e.printStackTrace();;
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			req.setAttribute("message", "구인글 등록에 실패했습니다.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println(e);
			req.setAttribute("message", "구인글 등록에 실패했습니다.");
			e.printStackTrace();
		} finally {
			try {
				req.setAttribute("posts", postService.getPostDetails());
				RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
				dispatcher.forward(req, res);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
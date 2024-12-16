package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Application;
import service.ApplicationService;
import service.PostService;
import service.RecruitService;

@WebServlet("/apply")
public class ApplicationController extends HttpServlet {
	RecruitService recruitService = new RecruitService();
	PostService postService = new PostService();
	ApplicationService applicationService = new ApplicationService();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		try {
			Integer bandId = Integer.parseInt((String) request.getParameter("bandId"));
			String position = (String) request.getParameter("position");
			Application application = new Application();
			application.setApplicantId(Integer.parseInt((String) request.getParameter("applicantId")));
			application.setName((String) request.getParameter("name"));
			application.setAge(Integer.parseInt((String) request.getParameter("age")));
			application.setDescription((String) request.getParameter("description"));
			application.setPosition((String) request.getParameter("position"));
			application.setLocation((String) request.getParameter("location"));
			application.setPhone((String) request.getParameter("phone"));
			application.setRecruitId(recruitService.getRecruitByBandIdAndPosition(bandId, position).getId());
			applicationService.createApplication(application);
			HttpSession session = request.getSession();
			session.setAttribute("message", "지원서 등록 완료");
		} catch (Exception e) {
			e.printStackTrace();
			HttpSession session = request.getSession();
			session.setAttribute("message", e.getMessage());
		} finally {
			response.sendRedirect("/postDetail?id="+request.getParameter("postId"));
		}
	}
}
package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Apl;
import service.AplService;
import service.PostService;
import service.RecruitService;

@WebServlet("/apply")
public class AplController extends HttpServlet {
	RecruitService recruitService = new RecruitService();
	PostService postService = new PostService();
	AplService aplService = new AplService();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		try {
			Integer bandId = Integer.parseInt((String) request.getParameter("bandId"));
			String position = (String) request.getParameter("position");
			Apl apl = new Apl();
			apl.setApplicantId(Integer.parseInt((String) request.getParameter("applicantId")));
			apl.setName((String) request.getParameter("name"));
			apl.setAge(Integer.parseInt((String) request.getParameter("age")));
			apl.setDescription((String) request.getParameter("description"));
			apl.setPosition((String) request.getParameter("position"));
			apl.setLocation((String) request.getParameter("location"));
			apl.setPhone((String) request.getParameter("phone"));
			apl.setRecruitId(recruitService.getRecruitByBandIdAndPosition(bandId, position).getId());
			aplService.createApl(apl);
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
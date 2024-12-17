package controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.AplService;
import service.RecruitService;

@WebServlet("/decideApl")
public class AplDecideController extends HttpServlet {
	private final RecruitService recruitService = new RecruitService();
	private final AplService aplService = new AplService();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			Integer recruitId = Integer.parseInt((String) request.getParameter("recruitId"));
			Integer applicantId = Integer.parseInt((String) request.getParameter("applicantId"));
			String decision = (String) request.getParameter("decision");
			if(decision.equals("accept")) {
				recruitService.accept(recruitId, applicantId);
				aplService.accept(recruitId, applicantId);
			}else if(decision.equals("reject")) {
				aplService.reject(recruitId, applicantId);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

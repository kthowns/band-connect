package controller;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/newRecruit")
public class NewRecruitController extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.sendRedirect("/newRecruit.jsp");
	}
	

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String title = (String) req.getAttribute("title");
		String content = (String) req.getAttribute("content");
		String part = (String) req.getAttribute("part");
	}
}
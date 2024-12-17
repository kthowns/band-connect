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
import repository.AplRepository;
import service.AplService;
import service.BandService;
import service.PostService;

@WebServlet("/myBand")
public class MyBandController extends HttpServlet {
	private final BandService bandService = new BandService();
	private final AplService aplService = new AplService();
	private final AplRepository aplRepository = new AplRepository();
	private final PostService postService = new PostService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			List<PostDetail> posts = postService.getPostDetailsByAuthorId(user.getId());
			List<Apl> apls = aplRepository.findAll();
			request.setAttribute("posts", posts);
			request.setAttribute("aplts", apls);
		} catch(Exception e) {
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/myBand.jsp");
		dispatcher.forward(request, response);
	}
}
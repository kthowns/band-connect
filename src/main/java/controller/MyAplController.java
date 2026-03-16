package controller;

import entity.AplDetail;
import entity.User;
import service.AplService;
import service.BandService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/myApl")
public class MyAplController extends HttpServlet {
    private final AplService aplService = new AplService();
    private final BandService bandService = new BandService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        try {
            List<AplDetail> aplDetails = aplService.getAplDetails(user.getId());
            for (AplDetail aplDetail : aplDetails) {
                aplDetail.setBand(bandService.getBandById(aplDetail.getPost().getBandId()));
            }
            request.setAttribute("aplDetails", aplDetails);
            System.out.println(aplDetails);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/myApl.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

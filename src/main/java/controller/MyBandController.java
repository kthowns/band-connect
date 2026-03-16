package controller;

import entity.Apl;
import entity.PostDetail;
import entity.User;
import service.AplService;
import service.PostService;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/myBand")
public class MyBandController extends HttpServlet {
    private final PostService postService = new PostService();
    private final AplService aplService = new AplService();
    private final UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                response.sendRedirect("/login");
                return;
            }

            // 1. 내가 작성한 게시글(밴드장인 경우)의 상세 정보 가져오기
            List<PostDetail> posts = postService.getPostDetailsByAuthorId(user.getId());
            for(PostDetail post : posts) {
                post.getBand().setLeader(userService.getUserById(post.getBand().getLeaderId()));
            }

            // 2. 전체 지원 내역이 아니라, 수락된(ACCEPTED) 내역 혹은 관련 내역만 가져오기
            // JSP에서 'apls'로 받고 있으므로 이름을 맞춰줍니다.
            List<Apl> apls = aplService.getAplByPostAuthorId(user.getId());

            request.setAttribute("posts", posts);
            request.setAttribute("apls", apls); // 오타 수정: aplts -> apls

        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/myBand.jsp");
        dispatcher.forward(request, response);
    }
}
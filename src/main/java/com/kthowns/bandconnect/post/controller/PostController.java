package com.kthowns.bandconnect.post.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    @GetMapping("/write")
    public String write() {
        return "post/write";
    }

    @PostMapping("/write")
    public String writePost(
            HttpSession session
    ) {
        String title = (String) request.getParameter("title");
        String bandName = (String) request.getParameter("bandName");
        String content = (String) request.getParameter("content");
        String hashtag = (String) request.getParameter("hashtag");
        String[] parts = request.getParameterValues("parts[]");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        try {
            postService.createPost(user.getId(), title, bandName, content, parts, hashtag);
        } catch (Exception e) {
            session.setAttribute("message", e.getMessage());
        }
        response.sendRedirect("/main");
    }
}

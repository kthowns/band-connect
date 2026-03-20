package com.kthowns.bandconnect.common.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        /*
        List<PostDetail> posts = postService.getPostDetails();
        for (PostDetail post : posts) {
            post.setHashtags(postService.getHashtagsByPostId(post.getPostId()));
        }
        model.addAttribute("posts", posts);
        */

        return "index";
    }
}

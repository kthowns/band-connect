package com.kthowns.bandconnect.user.controller;

import com.kthowns.bandconnect.post.dto.CommentDto;
import com.kthowns.bandconnect.post.service.CommentService;
import com.kthowns.bandconnect.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/my")
public class MyController {
    private final CommentService commentService;

    @GetMapping("/profile")
    public String profile(
            Model model,
            @AuthenticationPrincipal User user
    ) {
        model.addAttribute("user", user);
        return "my/profile";
    }

    @GetMapping("/comments")
    public String comments(
            Model model,
            @AuthenticationPrincipal User user
    ) {
        List<CommentDto> comments = commentService.getComments(user.getId());
        model.addAttribute("comments", comments);
        return "my/comments";
    }
}

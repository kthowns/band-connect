package com.kthowns.bandconnect.user.controller;

import com.kthowns.bandconnect.application.dto.ApplicationDto;
import com.kthowns.bandconnect.application.service.ApplicationService;
import com.kthowns.bandconnect.post.dto.CommentDto;
import com.kthowns.bandconnect.post.dto.PostWithRecruits;
import com.kthowns.bandconnect.post.service.CommentService;
import com.kthowns.bandconnect.post.service.PostService;
import com.kthowns.bandconnect.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
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
    private final ApplicationService applicationService;
    private final PostService postService;

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

    @GetMapping("/applications")
    public String applications(
            Model model,
            @AuthenticationPrincipal User user
    ) {
        List<ApplicationDto> applications = applicationService.getApplications(user.getId());
        model.addAttribute("applications", applications);
        return "my/applications";
    }

    @GetMapping("/posts")
    public String posts(
            Model model,
            @AuthenticationPrincipal User user,
            HttpServletRequest servletRequest
    ) {
        List<PostWithRecruits> posts = postService.getMyPostsWithRecruits(user.getId());
        model.addAttribute("posts", posts);
        model.addAttribute("currentUri", servletRequest.getRequestURI());
        return "my/posts";
    }
}

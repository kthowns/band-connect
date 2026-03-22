package com.kthowns.bandconnect.post.controller;

import com.kthowns.bandconnect.common.exception.CustomResponseCode;
import com.kthowns.bandconnect.post.dto.AddCommentRequest;
import com.kthowns.bandconnect.post.service.CommentService;
import com.kthowns.bandconnect.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comment/write")
    public String writeComment(
            @ModelAttribute @Valid AddCommentRequest request,
            @PathVariable Long postId,
            @AuthenticationPrincipal User user,
            RedirectAttributes rttr
    ) {
        commentService.createComment(postId, request, user);
        rttr.addFlashAttribute("message", CustomResponseCode.COMMENT_ADD_SUCCESS.getMessage());

        return "redirect:/posts/" + postId;
    }

    @PostMapping("/comments/{id}/delete")
    public String deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            RedirectAttributes rttr,
            @RequestParam(value = "returnUrl", defaultValue = "/") String returnUrl
    ) {
        try {
            commentService.deleteComment(id, user);
            rttr.addFlashAttribute("message", CustomResponseCode.COMMENT_DELETE_SUCCESS.getMessage());
        } catch (Exception e) {
            rttr.addFlashAttribute("message", CustomResponseCode.INTERNAL_SERVER_ERROR.getMessage());
        }
        return "redirect:" + returnUrl;
    }
}

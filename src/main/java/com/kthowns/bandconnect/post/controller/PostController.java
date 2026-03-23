package com.kthowns.bandconnect.post.controller;

import com.kthowns.bandconnect.band.dto.BandDto;
import com.kthowns.bandconnect.band.service.BandService;
import com.kthowns.bandconnect.common.exception.CustomException;
import com.kthowns.bandconnect.common.exception.CustomResponseCode;
import com.kthowns.bandconnect.post.dto.AddPostRequest;
import com.kthowns.bandconnect.post.dto.RecruitPostDetail;
import com.kthowns.bandconnect.post.facade.PostFacade;
import com.kthowns.bandconnect.post.service.PostService;
import com.kthowns.bandconnect.user.entity.User;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final BandService bandService;
    private final PostFacade postFacade;

    @GetMapping("/{id}")
    public String postDetail(
            Model model,
            @PathVariable @Nullable Long id,
            @AuthenticationPrincipal User user,
            HttpServletRequest request
    ) {
        if (id == null) {
            return "redirect:/";
        }

        try {
            RecruitPostDetail postDetail = postFacade.getRecruitPostDetail(id);
            model.addAttribute("postDetail", postDetail);
            model.addAttribute("user", user);
            model.addAttribute("currentUri", request.getRequestURI());
            return "post/detail";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/write")
    public String writePost(
            Model model,
            @AuthenticationPrincipal User user
    ) {
        List<BandDto> myBands = bandService.getMyBands(user)
                .stream().map(BandDto::fromEntity).toList();
        model.addAttribute("bands", myBands);
        return "post/write";
    }

    @PostMapping("/write")
    public String writePost(
            @ModelAttribute AddPostRequest request,
            @AuthenticationPrincipal User user,
            RedirectAttributes rttr,
            Model model,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "post/write";
        }
        try {
            postFacade.createPost(request, user);
            rttr.addFlashAttribute("message", CustomResponseCode.POST_ADD_SUCCESS.getMessage());
            return "redirect:/";
        } catch (CustomException e) {
            log.error(e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "post/write";
        } catch (Exception e) {
            log.error(e.getMessage());
            model.addAttribute("message", CustomResponseCode.INTERNAL_SERVER_ERROR.getMessage());
            return "post/write";
        }
    }

    @PostMapping("/{id}/delete")
    public String deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            RedirectAttributes rttr
    ) {
        try {
            postService.deletePost(id, user);
            rttr.addFlashAttribute("message", CustomResponseCode.POST_DELETE_SUCCESS.getMessage());
            return "redirect:/";
        } catch (Exception e) {
            rttr.addFlashAttribute("message", CustomResponseCode.INTERNAL_SERVER_ERROR.getMessage());
            return "redirect:/posts/" + id;
        }
    }
}

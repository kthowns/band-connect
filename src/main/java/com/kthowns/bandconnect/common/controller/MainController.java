package com.kthowns.bandconnect.common.controller;

import com.kthowns.bandconnect.post.dto.RecruitPostDetail;
import com.kthowns.bandconnect.post.dto.RecruitPostSearch;
import com.kthowns.bandconnect.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final PostService postService;

    @GetMapping("/")
    public String index(
            @ModelAttribute("searchDto") RecruitPostSearch searchDto,
            Model model
    ) {
        if (searchDto.getKeyword() == null) {
            searchDto.setKeyword("");
        }
        List<RecruitPostDetail> posts = postService.getRecruitPosts(
                searchDto
        );

        model.addAttribute("posts", posts);

        return "index";
    }
}

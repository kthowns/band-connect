package com.kthowns.bandconnect.user.controller;

import com.kthowns.bandconnect.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/my")
public class MyController {
    @GetMapping("/profile")
    public String profile(
            Model model,
            @AuthenticationPrincipal User user
    ) {
        model.addAttribute("user", user);
        return "my/profile";
    }
}

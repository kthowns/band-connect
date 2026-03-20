package com.kthowns.bandconnect.user.controller;

import com.kthowns.bandconnect.common.exception.CustomErrorCode;
import com.kthowns.bandconnect.common.exception.CustomException;
import com.kthowns.bandconnect.user.dto.SignupRequest;
import com.kthowns.bandconnect.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("signupRequest", new SignupRequest());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupProcess(
            @ModelAttribute("signupRequest") @Valid SignupRequest request,
            BindingResult bindingResult,
            RedirectAttributes rttr
    ) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        try {
            userService.signup(request);
            rttr.addFlashAttribute("message", "회원가입이 완료되었습니다. 로그인해주세요.");
            return "redirect:/login";
        } catch (CustomException e) {
            rttr.addFlashAttribute("message", e.getMessage());
            return "redirect:/signup";
        } catch (Exception e) {
            rttr.addFlashAttribute("message", CustomErrorCode.INTERNAL_SERVER_ERROR.getMessage());
            return "redirect:/signup";
        }
    }
}

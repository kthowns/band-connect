package com.kthowns.bandconnect.application.controller;

import com.kthowns.bandconnect.application.dto.ApplyRecruitRequest;
import com.kthowns.bandconnect.application.service.ApplicationService;
import com.kthowns.bandconnect.common.exception.CustomException;
import com.kthowns.bandconnect.common.exception.CustomResponseCode;
import com.kthowns.bandconnect.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/recruits/apply")
    public String apply(
            @ModelAttribute ApplyRecruitRequest request,
            @RequestParam("returnUri") String returnUri,
            @AuthenticationPrincipal User user,
            RedirectAttributes rttr
    ) {
        try {
            applicationService.applyApplication(request, user);
            rttr.addFlashAttribute("message", CustomResponseCode.APPLY_SUCCESS.getMessage());
        } catch (CustomException e) {
            rttr.addFlashAttribute("message", e.getMessage());
        } catch (Exception e) {
            rttr.addFlashAttribute("message", CustomResponseCode.INTERNAL_SERVER_ERROR.getMessage());
        }
        return "redirect:" + returnUri;
    }
}

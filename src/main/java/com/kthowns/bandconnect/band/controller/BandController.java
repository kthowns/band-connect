package com.kthowns.bandconnect.band.controller;

import com.kthowns.bandconnect.band.service.BandService;
import com.kthowns.bandconnect.common.exception.CustomException;
import com.kthowns.bandconnect.common.exception.CustomResponseCode;
import com.kthowns.bandconnect.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/bands")
@RequiredArgsConstructor
public class BandController {
    private final BandService bandService;

    // 밴드 생성 페이지 이동
    @GetMapping("/create")
    public String createForm() {
        return "band/create";
    }

    // 밴드 생성 처리
    @PostMapping("/create")
    public String createBand(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @AuthenticationPrincipal User user,
            RedirectAttributes rttr
    ) {
        try {
            bandService.createBand(user, name, description);
            rttr.addFlashAttribute("message", CustomResponseCode.BAND_CREATED);
        } catch (CustomException e) {
            rttr.addFlashAttribute("message", e.getMessage());
        } catch (Exception e) {
            rttr.addFlashAttribute("message", CustomResponseCode.INTERNAL_SERVER_ERROR);
        }

        return "redirect:/";
    }
}
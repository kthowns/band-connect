package com.kthowns.bandconnect.application.facade;

import com.kthowns.bandconnect.application.entity.Application;
import com.kthowns.bandconnect.application.service.ApplicationService;
import com.kthowns.bandconnect.band.service.BandService;
import com.kthowns.bandconnect.recruit.dto.ConfirmApplicationRequest;
import com.kthowns.bandconnect.recruit.entity.Recruit;
import com.kthowns.bandconnect.recruit.service.RecruitService;
import com.kthowns.bandconnect.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationFacade {
    private final ApplicationService applicationService;
    private final RecruitService recruitService;
    private final BandService bandService;

    @Transactional
    public void confirmApplication(ConfirmApplicationRequest request, User user) {
        if (!request.getIsAccepted().equals("ACCEPTED")) {
            applicationService.declineApplication(request.getApplicationId(), user.getId());
            return;
        }
        // 지원서 상태 변경
        Application application = applicationService.acceptApplication(request.getApplicationId(), user.getId());

        // 구인에 합격자 넣기
        Recruit recruit = recruitService.confirmRecruit(
                application.getRecruit().getId(),
                application.getApplicant().getId(),
                user.getId()
        );

        // 밴드에 멤버 추가
        bandService.addMember(
                recruit.getRecruitPost().getBand().getId(),
                application.getApplicant().getId(),
                recruit.getPosition(),
                user.getId()
        );
    }
}

package service;

import java.sql.SQLException;

import entity.Application;
import repository.ApplicationRepository;

public class ApplicationService {
	private final ApplicationRepository applicationRepository = new ApplicationRepository();

	public Application createApplication(Application application) throws ClassNotFoundException, RuntimeException, SQLException {
		validateDuplicate(application);
		return applicationRepository.add(application)
				.orElseThrow(() -> new RuntimeException("Adding application failed"));
	}
	
	public void validateDuplicate(Application application) throws ClassNotFoundException, SQLException {
		applicationRepository.findById(application.getApplicantId(), application.getRecruitId())
		.ifPresent((a) -> { throw new RuntimeException("해당하는 파트에 중복되는 지원이 존재합니다"); });
	}
}
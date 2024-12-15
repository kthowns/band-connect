package service;

import java.sql.SQLException;

import entity.Recruit;
import repository.BandRepository;
import repository.RecruitRepository;

public class RecruitService {
	private final RecruitRepository recruitRepository = new RecruitRepository();
	
	public Recruit addRecruit(Recruit recruit) throws ClassNotFoundException, RuntimeException, SQLException {
		return recruitRepository.add(recruit)
				.orElseThrow(() -> new RuntimeException("Adding recruit failed"));
	}
	
	public void validateDuplication(Integer bandId, String position) throws ClassNotFoundException, SQLException {
		recruitRepository.findBybandIdAndPosition(bandId, position)
		.ifPresent((r) -> { throw new RuntimeException("Duplicated recruit"); });
	}
}

package service;

import java.sql.SQLException;
import java.util.List;

import entity.Recruit;
import repository.RecruitRepository;

public class RecruitService {
	private final RecruitRepository recruitRepository = new RecruitRepository();
	
	public Recruit addRecruit(Recruit recruit) throws ClassNotFoundException, RuntimeException, SQLException {
		return recruitRepository.add(recruit)
				.orElseThrow(() -> new RuntimeException("Adding recruit failed"));
	}

	public List<Recruit> getRecruitsByBandId(Integer id) throws ClassNotFoundException, SQLException {
		return recruitRepository.findByBandId(id);
		
	}

	public Recruit getRecruitByBandIdAndPosition(Integer bandId, String position) throws ClassNotFoundException, RuntimeException, SQLException {
		return recruitRepository.findBybandIdAndPosition(bandId, position)
				.orElseThrow(() -> new RuntimeException("Recruit not found"));
	}
	
	private void validateDuplication(Integer bandId, String position) throws ClassNotFoundException, SQLException {
		recruitRepository.findBybandIdAndPosition(bandId, position)
		.ifPresent((r) -> { throw new RuntimeException("Duplicated recruit"); });
	}
}

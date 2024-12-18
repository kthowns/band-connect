package service;

import java.sql.SQLException;
import java.util.List;

import entity.Band;
import entity.MemberDetail;
import repository.BandRepository;
import repository.UserRepository;

public class BandService {
	private final BandRepository bandRepository = new BandRepository();
	private final UserRepository userRepository = new UserRepository();
	
	public List<MemberDetail> getMembersByBandId(Integer bandId) throws ClassNotFoundException, SQLException{
		return bandRepository.findMembersByBandId(bandId);
	}
	
	public void join(Integer bandId, Integer memberId, String position) throws ClassNotFoundException, SQLException {
		bandRepository.join(bandId, memberId, position);
	}
	
	public Band getBandById(Integer id) throws ClassNotFoundException, RuntimeException, SQLException {
		return bandRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Band not found"));
	}
	
	public Band getBandByName(String name) throws ClassNotFoundException, RuntimeException, SQLException {
		return bandRepository.findByName(name)
				.orElseThrow(() -> new RuntimeException("Band not found"));
	}

	public Band getBandByLeaderId(Integer id) throws ClassNotFoundException, RuntimeException, SQLException {
		return bandRepository.findByLeaderId(id)
				.orElseThrow(() -> new RuntimeException("Band not found"));
	}

	public List<Band> getBandByMemberId(Integer id) throws ClassNotFoundException, SQLException {
		return bandRepository.findByMemberId(id);
	}
}
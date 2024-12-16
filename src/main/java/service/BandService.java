package service;

import java.sql.SQLException;

import entity.Band;
import repository.BandRepository;

public class BandService {
	private final BandRepository bandRepository = new BandRepository();
	
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
}
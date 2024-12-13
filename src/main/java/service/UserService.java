package service;

import java.sql.SQLException;
import java.util.List;

import entity.User;
import repository.UserRepository;

public class UserService {
	private final UserRepository userRepository = new UserRepository();
	
	public User getUserDetail(Integer id) throws Exception {
		return userRepository.findById(id)
				.orElseThrow(() -> new Exception("Some Exception"));
	}
	
	public List<User> getUserAll() throws ClassNotFoundException, SQLException{
		return userRepository.findAll();
	}
}

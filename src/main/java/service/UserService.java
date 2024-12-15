package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import entity.User;
import repository.UserRepository;

public class UserService {
	private final UserRepository userRepository = new UserRepository();
	
	public User login(String username, String password) throws Exception {
		return userRepository.findByUsernameAndPassword(username, password)
				.orElseThrow(() -> new Exception("Login failed"));
	}
	
	public User getUserDetail(Integer id) throws Exception {
		return userRepository.findById(id)
				.orElseThrow(() -> new Exception("Some Exception"));
	}
	
	public List<User> getUserAll() throws ClassNotFoundException, SQLException{
		return userRepository.findAll();
	}
}
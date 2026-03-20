package service;

import java.sql.SQLException;
import java.util.List;

import entity.User;
import repository.UserRepository;

public class UserService {
	private final UserRepository userRepository = new UserRepository();

	public User login(String username, String password) throws Exception {
		return userRepository.findByUsernameAndPassword(username, password)
				.orElseThrow(() -> new RuntimeException("Login failed"));
	}

	public User getUserById(Integer id) throws Exception {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
	}

	public List<User> getUserAll() throws ClassNotFoundException, SQLException {
		return userRepository.findAll();
	}
	
	public User addUser(User user) throws ClassNotFoundException, SQLException {
		validateUsername(user.getUsername());
		validateEmail(user.getEmail());
		return userRepository.add(user)
				.orElseThrow(() -> new RuntimeException("Adding user failed"));
	}

	private void validateUsername(String username) throws ClassNotFoundException, SQLException {
		userRepository.findByUsername(username)
			.ifPresent((u) -> { throw new RuntimeException("Duplicated username"); });
	}	

	private void validateEmail(String email) throws ClassNotFoundException, SQLException {
		userRepository.findByEmail(email)
			.ifPresent((u) -> { throw new RuntimeException("Duplicated email"); });
	}	
}
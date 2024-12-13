package repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import entity.User;
import util.ConnectionUtil;

public class UserRepository {
	private final ConnectionUtil connUtil = new ConnectionUtil();

	public Optional<User> findById(Integer userId) throws ClassNotFoundException, SQLException {
		connUtil.setQuery("SELECT * FROM users where id = ?").setInt(1, userId);
		return connUtil.request(User.class);
	}
	
	public List<User> findAll() throws ClassNotFoundException, SQLException{
		connUtil.setQuery("SELECT * FROM users");
		return connUtil.requestForList(User.class);
	}
}
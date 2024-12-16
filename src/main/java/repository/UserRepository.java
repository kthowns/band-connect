package repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import entity.User;
import util.ConnectionUtil;

public class UserRepository {
	private final ConnectionUtil connUtil = new ConnectionUtil();

	public Optional<User> findByUsernameAndPassword(String username, String password) throws SQLException, ClassNotFoundException{
		PreparedStatement stmt = connUtil.setQuery("SELECT * FROM users WHERE username = ? AND password = ?");
		stmt.setString(1, username);
		stmt.setString(2, password);
		return connUtil.request(User.class);
	}
	
	public Optional<User> findById(Integer userId) throws ClassNotFoundException, SQLException {
		connUtil.setQuery("SELECT * FROM users WHERE id = ?").setInt(1, userId);
		return connUtil.request(User.class);
	}
	
	public Optional<User> findByUsername(String username) throws ClassNotFoundException, SQLException {
		connUtil.setQuery("SELECT * FROM users WHERE username = ?").setString(1, username);
		return connUtil.request(User.class);
	}
	
	public Optional<User> findByEmail(String email) throws ClassNotFoundException, SQLException {
		connUtil.setQuery("SELECT * FROM users WHERE email = ?").setString(1, email);
		return connUtil.request(User.class);
	}


	public List<User> findAll() throws ClassNotFoundException, SQLException {
		connUtil.setQuery("SELECT * FROM users");
		return connUtil.requestForList(User.class);
	}

	public Optional<User> add(User user) throws ClassNotFoundException, SQLException {
		PreparedStatement stmt = connUtil.setInsertQuery("INSERT INTO users(email, username, password) values(?, ?, ?)");
		stmt.setString(1, user.getEmail());
		stmt.setString(2, user.getUsername());
		stmt.setString(3, user.getPassword());
		Integer id = connUtil.requestInsert(User.class);
		connUtil.setQuery("SELECT * FROM users where username = ?").setInt(1, id);
		return connUtil.request(User.class);
	}

	public Optional<User> update(User user) throws ClassNotFoundException, SQLException {
		PreparedStatement stmt = connUtil.setQuery("UPDATE users SET email = ?, username = ?, password = ?");
		stmt.setString(1, user.getEmail());
		stmt.setString(2, user.getUsername());
		stmt.setString(3, user.getPassword());
		connUtil.setQuery("SELECT * FROM users where username = ?").setString(1, user.getUsername());
		return connUtil.request(User.class);
	}

	public Optional<User> delete(User user) throws ClassNotFoundException, SQLException {
		connUtil.setQuery("DELETE FROM users where id = ?").setInt(1, user.getId());
		if (connUtil.requestUpdate(User.class) > 0) {
			return Optional.of(user);
		}
		return Optional.empty();
	}
}
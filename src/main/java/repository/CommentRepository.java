package repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import entity.Comment;
import util.ConnectionUtil;

public class CommentRepository {
	private final ConnectionUtil connUtil = new ConnectionUtil();

	public Optional<Comment> findById(Integer commentId) throws ClassNotFoundException, SQLException {
		connUtil.setQuery("SELECT * FROM comments WHERE id = ?").setInt(1, commentId);
		return connUtil.request(Comment.class);
	}

	public List<Comment> findAll() throws ClassNotFoundException, SQLException {
		connUtil.setQuery("SELECT * FROM comments");
		return connUtil.requestForList(Comment.class);
	}

	public Optional<Comment> add(Comment comment) throws ClassNotFoundException, SQLException {
		PreparedStatement stmt = connUtil.setInsertQuery("INSERT INTO comments(post_id, author_id, content) values(?, ?, ?)");
		stmt.setInt(1, comment.getPostId());
		stmt.setInt(2, comment.getAuthorId());
		stmt.setString(3, comment.getContent());
		Integer postId = connUtil.requestInsert(Comment.class);
		connUtil.setQuery("SELECT * FROM comments WHERE id = ?").setInt(1, postId);
		return connUtil.request(Comment.class);
	}

	public Optional<Comment> update(Comment comment) throws ClassNotFoundException, SQLException {
		PreparedStatement stmt = connUtil
				.setQuery("UPDATE comments SET post_id = ?, author_id = ?, content = ? WHERE id = ?");
		stmt.setInt(1, comment.getPostId());
		stmt.setInt(2, comment.getAuthorId());
		stmt.setString(3, comment.getContent());
		stmt.setInt(4, comment.getId());
		if (connUtil.requestUpdate(Comment.class) > 0) {
			connUtil.setQuery("SELECT * FROM comments WHERE id = ?").setInt(1, comment.getId());
			return connUtil.request(Comment.class);
		}
		return Optional.empty();
	}

	public Optional<Comment> delete(Comment comment) throws ClassNotFoundException, SQLException {
		connUtil.setQuery("DELETE FROM comments WHERE id = ?").setInt(1, comment.getId());
		if (connUtil.requestUpdate(Comment.class) > 0) {
			return Optional.of(comment);
		}
		return Optional.empty();
	}

	public List<Comment> findByPostId(Integer postId) throws ClassNotFoundException, SQLException {
		connUtil.setQuery("SELECT * FROM comments WHERE post_id = ?").setInt(1, postId);
		return connUtil.requestForList(Comment.class);
	}
}

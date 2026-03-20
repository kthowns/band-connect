package service;

import java.sql.SQLException;
import java.util.List;

import entity.Comment;
import repository.CommentRepository;

public class CommentService {
	CommentRepository commentRepository = new CommentRepository();

	public Comment createComment(Comment comment) throws ClassNotFoundException, RuntimeException, SQLException {
		return commentRepository.add(comment)
				.orElseThrow(() -> new RuntimeException("Adding comment failed"));
	}

	public List<Comment> getCommentByAuthorId(Integer authorId) throws ClassNotFoundException, SQLException {
		return commentRepository.findByAuthorId(authorId);
	}

	public void deleteCommentById(Integer commentId) throws ClassNotFoundException, SQLException {
		commentRepository.deleteById(commentId);
	}
}

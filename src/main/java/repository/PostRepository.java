package repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import entity.Post;
import util.ConnectionUtil;

public class PostRepository {
    private final ConnectionUtil connUtil = new ConnectionUtil();

    // ID로 포스트 찾기
    public Optional<Post> findById(Integer id) throws ClassNotFoundException, SQLException {
        connUtil.setQuery("SELECT * FROM posts WHERE id = ?").setInt(1, id);
        return connUtil.request(Post.class);
    }

    // 모든 포스트 찾기
    public List<Post> findAll() throws ClassNotFoundException, SQLException {
        connUtil.setQuery("SELECT * FROM posts");
        return connUtil.requestForList(Post.class);
    }

	public List<Post> findByAuthorId(Integer authorId) throws ClassNotFoundException, SQLException {
        connUtil.setQuery("SELECT * FROM posts WHERE author_id = ?").setInt(1,  authorId);
        return connUtil.requestForList(Post.class);
	}

    // 포스트 추가
    public Optional<Post> add(Post post) throws ClassNotFoundException, SQLException {
        PreparedStatement stmt = connUtil.setQuery("INSERT INTO posts(author_id, band_id, title, content) VALUES(?, ?, ?, ?)");
        stmt.setInt(1, post.getAuthorId());
        stmt.setInt(2, post.getBandId());
        stmt.setString(3, post.getTitle());
        stmt.setString(4, post.getContent());
        if (connUtil.requestUpdate(Post.class) > 0) {
            // 추가된 포스트 정보 반환
            PreparedStatement stmt_ = connUtil.setQuery("SELECT * FROM posts WHERE author_id = ? AND title = ?");
            stmt_.setInt(1, post.getAuthorId());
            stmt_.setString(2, post.getTitle());
            return connUtil.request(Post.class);
        }
        return Optional.empty();
    }

    // 포스트 추가
    public Optional<Post> add(Post post, ConnectionUtil connUtil_) throws ClassNotFoundException, SQLException {
        PreparedStatement stmt = connUtil_.setQuery("INSERT INTO posts(author_id, band_id, title, content) VALUES(?, ?, ?, ?)");
        stmt.setInt(1, post.getAuthorId());
        stmt.setInt(2, post.getBandId());
        stmt.setString(3, post.getTitle());
        stmt.setString(4, post.getContent());
        if (connUtil_.requestUpdate(Post.class) > 0) {
            // 추가된 포스트 정보 반환
            PreparedStatement stmt_ = connUtil_.setQuery("SELECT * FROM posts WHERE author_id = ? AND title = ?");
            stmt_.setInt(1, post.getAuthorId());
            stmt_.setString(2, post.getTitle());
            return connUtil_.request(Post.class);
        }
        return Optional.empty();
    }

    // 포스트 수정
    public Optional<Post> update(Post post) throws ClassNotFoundException, SQLException {
        PreparedStatement stmt = connUtil.setQuery("UPDATE posts SET author_id = ?, band_id = ?, title = ?, content = ? WHERE id = ?");
        stmt.setInt(1, post.getAuthorId());
        stmt.setInt(2, post.getBandId());
        stmt.setString(3, post.getTitle());
        stmt.setString(4, post.getContent());
        stmt.setInt(5, post.getId());
        if (connUtil.requestUpdate(Post.class) > 0) {
            // 수정된 포스트 정보 반환
            connUtil.setQuery("SELECT * FROM posts WHERE id = ?").setInt(1, post.getId());
            return connUtil.request(Post.class);
        }
        return Optional.empty();
    }
    
    // 포스트 수정
    public Optional<Post> update(Post post, ConnectionUtil connUtil_) throws ClassNotFoundException, SQLException {
        PreparedStatement stmt = connUtil_.setQuery("UPDATE posts SET author_id = ?, band_id = ?, title = ?, content = ? WHERE id = ?");
        stmt.setInt(1, post.getAuthorId());
        stmt.setInt(2, post.getBandId());
        stmt.setString(3, post.getTitle());
        stmt.setString(4, post.getContent());
        stmt.setInt(5, post.getId());
        if (connUtil_.requestUpdate(Post.class) > 0) {
            // 수정된 포스트 정보 반환
            connUtil_.setQuery("SELECT * FROM posts WHERE id = ?").setInt(1, post.getId());
            return connUtil_.request(Post.class);
        }
        return Optional.empty();
    }

    // 포스트 삭제
    public Optional<Post> delete(Post post) throws ClassNotFoundException, SQLException {
        connUtil.setQuery("DELETE FROM posts WHERE id = ?").setInt(1, post.getId());
        if (connUtil.requestUpdate(Post.class) > 0) {
            // 삭제된 포스트 반환
            return Optional.of(post);
        }
        return Optional.empty();
    }
    
    // 포스트 삭제
    public Optional<Post> delete(Post post, ConnectionUtil connUtil_) throws ClassNotFoundException, SQLException {
        connUtil_.setQuery("DELETE FROM posts WHERE id = ?").setInt(1, post.getId());
        if (connUtil_.requestUpdate(Post.class) > 0) {
            // 삭제된 포스트 반환
            return Optional.of(post);
        }
        return Optional.empty();
    }
}

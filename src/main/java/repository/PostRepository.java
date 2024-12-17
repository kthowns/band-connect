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
        PreparedStatement stmt = connUtil.setInsertQuery("INSERT INTO posts(author_id, band_id, title, content) VALUES(?, ?, ?, ?)");
        stmt.setInt(1, post.getAuthorId());
        stmt.setInt(2, post.getBandId());
        stmt.setString(3, post.getTitle());
        stmt.setString(4, post.getContent());
        Integer id = connUtil.requestInsert();
        connUtil.setQuery("SELECT * FROM posts WHERE id = ?").setInt(1, id);
        return connUtil.request(Post.class);
    }

    // 포스트 추가
    public Optional<Post> add(Post post, ConnectionUtil connUtil_) throws ClassNotFoundException, SQLException {
    	PreparedStatement stmt = connUtil_.setInsertQuery("INSERT INTO posts(author_id, band_id, title, content) VALUES(?, ?, ?, ?)");
        stmt.setInt(1, post.getAuthorId());
        stmt.setInt(2, post.getBandId());
        stmt.setString(3, post.getTitle());
        stmt.setString(4, post.getContent());
        Integer id = connUtil_.requestInsert();
        connUtil_.setQuery("SELECT * FROM posts WHERE id = ?").setInt(1, id);
        return connUtil_.request(Post.class);
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

	public Integer increaseViews(Integer postId) throws ClassNotFoundException, SQLException {
		connUtil.setQuery("UPDATE posts SET views = views + 1 WHERE id = ?").setInt(1, postId);
		if(connUtil.requestUpdate(Post.class) > 0) {
			connUtil.setQuery("SELECT * FROM posts WHERE id = ?").setInt(1, postId);
			Post post = connUtil.request(Post.class)
					.orElseThrow(() -> new RuntimeException("Post not found"));
			return post.getViews();
		}
		return 0;
	}

	public void deleteById(Integer postId) throws ClassNotFoundException, SQLException {
		connUtil.setQuery("DELETE FROM posts WHERE id = ?").setInt(1, postId);
		connUtil.requestUpdate(Post.class);
	}

	public List<Post> findByHashtag(String hashtag) throws ClassNotFoundException, SQLException {
		connUtil.setQuery("SELECT * FROM posts p join hashtags h on p.id = h.post_id WHERE h.hashtag = ?")
		.setString(1, hashtag);
		return connUtil.requestForList(Post.class);
	}
}

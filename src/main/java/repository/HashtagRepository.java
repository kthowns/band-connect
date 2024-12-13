package repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import entity.Hashtag;
import util.ConnectionUtil;

public class HashtagRepository {
    private final ConnectionUtil connUtil = new ConnectionUtil();

    // ID로 해시태그 찾기
    public Optional<Hashtag> findById(Integer id) throws ClassNotFoundException, SQLException {
        connUtil.setQuery("SELECT * FROM hashtags WHERE id = ?").setInt(1, id);
        return connUtil.request(Hashtag.class);
    }

    // 모든 해시태그 찾기
    public List<Hashtag> findAll() throws ClassNotFoundException, SQLException {
        connUtil.setQuery("SELECT * FROM hashtags");
        return connUtil.requestForList(Hashtag.class);
    }

    // 해시태그 추가
    public Optional<Hashtag> add(Hashtag hashtag) throws ClassNotFoundException, SQLException {
        PreparedStatement stmt = connUtil.setQuery("INSERT INTO hashtags(post_id, hashtag) VALUES(?, ?)");
        stmt.setInt(1, hashtag.getPostId());
        stmt.setString(2, hashtag.getHashtag());
        if (connUtil.requestUpdate(Hashtag.class) > 0) {
            // 추가한 해시태그 정보 반환
            PreparedStatement stmt_ = connUtil.setQuery("SELECT * FROM hashtags WHERE post_id = ? AND hashtag = ?");
            stmt_.setInt(1, hashtag.getPostId());
            stmt_.setString(2, hashtag.getHashtag());
            return connUtil.request(Hashtag.class);
        }
        return Optional.empty();
    }

    // 해시태그 수정
    public Optional<Hashtag> update(Hashtag hashtag) throws ClassNotFoundException, SQLException {
        PreparedStatement stmt = connUtil.setQuery("UPDATE hashtags SET post_id = ?, hashtag = ? WHERE id = ?");
        stmt.setInt(1, hashtag.getPostId());
        stmt.setString(2, hashtag.getHashtag());
        stmt.setInt(3, hashtag.getId());
        if (connUtil.requestUpdate(Hashtag.class) > 0) {
            // 수정된 해시태그 정보 반환
            connUtil.setQuery("SELECT * FROM hashtags WHERE id = ?").setInt(1, hashtag.getId());
            return connUtil.request(Hashtag.class);
        }
        return Optional.empty();
    }

    // 해시태그 삭제
    public Optional<Hashtag> delete(Hashtag hashtag) throws ClassNotFoundException, SQLException {
        connUtil.setQuery("DELETE FROM hashtags WHERE id = ?").setInt(1, hashtag.getId());
        if (connUtil.requestUpdate(Hashtag.class) > 0) {
            // 삭제된 해시태그 반환
            return Optional.of(hashtag);
        }
        return Optional.empty();
    }
}

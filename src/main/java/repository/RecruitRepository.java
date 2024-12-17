package repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import entity.Recruit;
import util.ConnectionUtil;

public class RecruitRepository {
    private final ConnectionUtil connUtil = new ConnectionUtil();
    
    // ID로 리크루트 찾기
    public Optional<Recruit> findById(Integer id) throws ClassNotFoundException, SQLException {
        connUtil.setQuery("SELECT * FROM recruits WHERE id = ?").setInt(1, id);
        return connUtil.request(Recruit.class);
    }

    // 모든 리크루트 찾기
    public List<Recruit> findAll() throws ClassNotFoundException, SQLException {
        connUtil.setQuery("SELECT * FROM recruits");
        return connUtil.requestForList(Recruit.class);
    }

    public Optional<Recruit> findBybandIdAndPosition(Integer bandId, String position) throws ClassNotFoundException, SQLException{
    	PreparedStatement stmt = connUtil.setQuery("SELECT * FROM recruits WHERE band_id = ? AND position = ?");
    	stmt.setInt(1, bandId);
    	stmt.setString(2, position);
    	return connUtil.request(Recruit.class);
    }
    
    public List<Recruit> findByBandId(Integer bandId) throws ClassNotFoundException, SQLException{
    	connUtil.setQuery("SELECT * FROM recruits WHERE band_id = ?").setInt(1, bandId);
    	return connUtil.requestForList(Recruit.class);
    }
    
    // 리크루트 추가
    public Optional<Recruit> add(Recruit recruit) throws ClassNotFoundException, SQLException {
        PreparedStatement stmt = connUtil.setInsertQuery("INSERT INTO recruits(band_id, position, post_id) VALUES(?, ?, ?)");
        stmt.setInt(1, recruit.getBandId());
        stmt.setString(2, recruit.getPosition());
        stmt.setInt(3, recruit.getPostId());
        Integer id = connUtil.requestInsert(Recruit.class);
        connUtil.setQuery("SELECT * FROM recruits WHERE id = ?").setInt(1, id);
        return connUtil.request(Recruit.class);
    }
    
    // 리크루트 추가, Transactional
    public Optional<Recruit> add(Recruit recruit, ConnectionUtil connUtil_) throws ClassNotFoundException, SQLException {
        PreparedStatement stmt = connUtil_.setInsertQuery("INSERT INTO recruits(band_id, position, post_id) VALUES(?, ?, ?)");
        stmt.setInt(1, recruit.getBandId());
        stmt.setString(2, recruit.getPosition());
        stmt.setInt(3, recruit.getPostId());
        Integer id = connUtil_.requestInsert(Recruit.class);
        connUtil_.setQuery("SELECT * FROM recruits WHERE id = ?").setInt(1, id);
        return connUtil_.request(Recruit.class);
    }

    // 리크루트 수정
    public Optional<Recruit> update(Recruit recruit) throws ClassNotFoundException, SQLException {
        PreparedStatement stmt = connUtil.setQuery("UPDATE recruits SET band_id = ?, position = ?, accepted_id = ?, created_at = ? WHERE id = ?");
        stmt.setInt(1, recruit.getBandId());
        stmt.setString(2, recruit.getPosition());
        stmt.setInt(3, recruit.getAcceptedId());
        stmt.setTimestamp(4, recruit.getCreatedAt());
        stmt.setInt(5, recruit.getId());
        if (connUtil.requestUpdate(Recruit.class) > 0) {
            // 수정된 리크루트 정보 반환
            connUtil.setQuery("SELECT * FROM recruits WHERE id = ?").setInt(1, recruit.getId());
            return connUtil.request(Recruit.class);
        }
        return Optional.empty();
    }

    // 리크루트 수정, Transactional
    public Optional<Recruit> update(Recruit recruit, ConnectionUtil connUtil_) throws ClassNotFoundException, SQLException {
        PreparedStatement stmt = connUtil_.setQuery("UPDATE recruits SET band_id = ?, position = ?, accepted_id = ?, created_at = ? WHERE id = ?");
        stmt.setInt(1, recruit.getBandId());
        stmt.setString(2, recruit.getPosition());
        stmt.setInt(3, recruit.getAcceptedId());
        stmt.setTimestamp(4, recruit.getCreatedAt());
        stmt.setInt(5, recruit.getId());
        if (connUtil_.requestUpdate(Recruit.class) > 0) {
            // 수정된 리크루트 정보 반환
            connUtil_.setQuery("SELECT * FROM recruits WHERE id = ?").setInt(1, recruit.getId());
            return connUtil_.request(Recruit.class);
        }
        return Optional.empty();
    }
    
    // 리크루트 삭제
    public Optional<Recruit> delete(Recruit recruit) throws ClassNotFoundException, SQLException {
        connUtil.setQuery("DELETE FROM recruits WHERE id = ?").setInt(1, recruit.getId());
        if (connUtil.requestUpdate(Recruit.class) > 0) {
            // 삭제된 리크루트 반환
            return Optional.of(recruit);
        }
        return Optional.empty();
    }
    

    // 리크루트 삭제, Transactional
    public Optional<Recruit> delete(Recruit recruit, ConnectionUtil connUtil_) throws ClassNotFoundException, SQLException {
        connUtil_.setQuery("DELETE FROM recruits WHERE id = ?").setInt(1, recruit.getId());
        if (connUtil_.requestUpdate(Recruit.class) > 0) {
            // 삭제된 리크루트 반환
            return Optional.of(recruit);
        }
        return Optional.empty();
    }

	public void accept(Integer recruitId, Integer applicantId) throws ClassNotFoundException, SQLException {
		PreparedStatement pstmt = connUtil.setQuery("UPDATE recruits SET accepted_id = ? WHERE id = ?");
		pstmt.setInt(1, applicantId);
		pstmt.setInt(2, recruitId);
		connUtil.requestUpdate(Recruit.class);
	}
}

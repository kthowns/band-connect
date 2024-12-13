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

    // 리크루트 추가
    public Optional<Recruit> add(Recruit recruit) throws ClassNotFoundException, SQLException {
        PreparedStatement stmt = connUtil.setQuery("INSERT INTO recruits(band_id, position, accepted_id, created_at) VALUES(?, ?, ?, ?)");
        stmt.setInt(1, recruit.getBandId());
        stmt.setString(2, recruit.getPosition());
        stmt.setInt(3, recruit.getAcceptedId());
        stmt.setTimestamp(4, recruit.getCreatedAt());
        if (connUtil.requestUpdate(Recruit.class) > 0) {
            // 추가된 리크루트 정보 반환
            PreparedStatement stmt_ = connUtil.setQuery("SELECT * FROM recruits WHERE band_id = ? AND position = ?");
            stmt_.setInt(1, recruit.getId());
            stmt_.setString(2,  recruit.getPosition());
            return connUtil.request(Recruit.class);
        }
        return Optional.empty();
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

    // 리크루트 삭제
    public Optional<Recruit> delete(Recruit recruit) throws ClassNotFoundException, SQLException {
        connUtil.setQuery("DELETE FROM recruits WHERE id = ?").setInt(1, recruit.getId());
        if (connUtil.requestUpdate(Recruit.class) > 0) {
            // 삭제된 리크루트 반환
            return Optional.of(recruit);
        }
        return Optional.empty();
    }
}

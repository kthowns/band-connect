package repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import entity.Apl;
import util.ConnectionUtil;

public class AplRepository {
    private final ConnectionUtil connUtil = new ConnectionUtil();

    // ID로 Apl 찾기
    public Optional<Apl> findById(Integer applicantId, Integer recruitId) throws ClassNotFoundException, SQLException {
        PreparedStatement stmt = connUtil.setQuery("SELECT * FROM apls WHERE applicant_id = ? AND recruit_id = ?");
        stmt.setInt(1, applicantId);
        stmt.setInt(2, recruitId);
        return connUtil.request(Apl.class);
    }

    // 모든 Apl 찾기
    public List<Apl> findAll() throws ClassNotFoundException, SQLException {
        connUtil.setQuery("SELECT * FROM apls");
        return connUtil.requestForList(Apl.class);
    }

    // Apl 추가
    public Optional<Apl> add(Apl apl) throws ClassNotFoundException, SQLException {
        PreparedStatement stmt = connUtil.setQuery("INSERT INTO apls(applicant_id, recruit_id, position, description, name, age, location, phone) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, apl.getApplicantId());
        stmt.setInt(2, apl.getRecruitId());
        stmt.setString(3, apl.getPosition());
        stmt.setString(4, apl.getDescription());
        stmt.setString(5, apl.getName());
        stmt.setInt(6, apl.getAge());
        stmt.setString(7, apl.getLocation());
        stmt.setString(8, apl.getPhone());
        Integer id = connUtil.requestUpdate(Apl.class);
        connUtil.setQuery("SELECT * FROM apls WHERE id = ?").setInt(1, id);
        return connUtil.request(Apl.class);
    }

    // Apl 수정
    public Optional<Apl> update(Apl apl) throws ClassNotFoundException, SQLException {
        PreparedStatement stmt = connUtil.setQuery("UPDATE apls SET status = ?, position = ?, description = ? WHERE applicant_id = ? AND recruit_id = ?");
        stmt.setString(1, apl.getStatus().name());
        stmt.setString(2, apl.getPosition());
        stmt.setString(3, apl.getDescription());
        stmt.setInt(4, apl.getApplicantId());
        stmt.setInt(5, apl.getRecruitId());
        if (connUtil.requestUpdate(Apl.class) > 0) {
            // 수정된 Apl 정보 반환
            PreparedStatement stmt_ = connUtil.setQuery("SELECT * FROM apls WHERE applicant_id = ? AND recruit_id = ?");
            stmt_.setInt(1, apl.getApplicantId());
            stmt_.setInt(2, apl.getRecruitId());
            return connUtil.request(Apl.class);
        }
        return Optional.empty();
    }

    // Apl 삭제
    public Optional<Apl> delete(Apl apl) throws ClassNotFoundException, SQLException {
        PreparedStatement stmt = connUtil.setQuery("DELETE FROM apls WHERE applicant_id = ? AND recruit_id = ?");
        stmt.setInt(1, apl.getApplicantId());
        stmt.setInt(2, apl.getRecruitId());
        if (connUtil.requestUpdate(Apl.class) > 0) {
            // 삭제된 Apl 반환
            return Optional.of(apl);
        }
        return Optional.empty();
    }

	public Integer getApplicantNumber(Integer postId) throws ClassNotFoundException, SQLException {
		connUtil.setQuery("SELECT count(*) FROM apls a JOIN recruits r ON a.recruit_id = r.id JOIN posts p ON p.band_id=r.band_id WHERE p.id = ?")
			.setInt(1,  postId);
		return connUtil.requestInt();
	}

	public List<Apl> findByApplicantId(Integer applicantId) throws ClassNotFoundException, SQLException {
        connUtil.setQuery("SELECT * FROM apls WHERE applicant_id = ?").setInt(1, applicantId);
        return connUtil.requestForList(Apl.class);
	}
}

package repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import entity.Band;
import entity.MemberDetail;
import entity.User;
import util.ConnectionUtil;

public class BandRepository {
	private final ConnectionUtil connUtil = new ConnectionUtil();
	
	public void join(Integer bandId, Integer memberId, String position) throws ClassNotFoundException, SQLException {
		PreparedStatement pstmt = connUtil.setInsertQuery("INSERT INTO band_member(band_id, member_id, position) values(?, ?, ?)");
		pstmt.setInt(1, bandId);
		pstmt.setInt(2, memberId);
		pstmt.setString(3, position);
		connUtil.requestInsert();
	}

	public Optional<Band> findById(Integer bandId) throws ClassNotFoundException, SQLException {
		connUtil.setQuery("SELECT * FROM bands WHERE id = ?").setInt(1, bandId);
		return connUtil.request(Band.class);
	}

	public Optional<Band> findByName(String name) throws SQLException, ClassNotFoundException {
		connUtil.setQuery("SELECT * FROM bands WHERE name = ?").setString(1, name);
		return connUtil.request(Band.class);
	}

	public Optional<Band> findByLeaderId(Integer id) throws ClassNotFoundException, SQLException {
		connUtil.setQuery("SELECT * FROM bands WHERE leader_id = ?").setInt(1,  id);
		return connUtil.request(Band.class);
	}
	
	public List<Band> findAll() throws ClassNotFoundException, SQLException {
		connUtil.setQuery("SELECT * FROM bands");
		return connUtil.requestForList(Band.class);
	}

	public Optional<Band> add(Band band) throws ClassNotFoundException, SQLException {
		PreparedStatement stmt = connUtil.setInsertQuery("INSERT INTO bands(leader_id, name, description) VALUES(?, ?, ?)");
		stmt.setInt(1, band.getLeaderId());
		stmt.setString(2, band.getName());
		stmt.setString(3, band.getDescription());
		Integer bandId = connUtil.requestInsert();
		connUtil.setQuery("SELECT * FROM bands WHERE id = ?").setInt(1, bandId);
		return connUtil.request(Band.class);
	}
	
	public Optional<Band> add(Band band, ConnectionUtil connUtil_) throws ClassNotFoundException, SQLException {
		PreparedStatement stmt = connUtil_.setInsertQuery("INSERT INTO bands(leader_id, name, description) VALUES(?, ?, ?)");
		stmt.setInt(1, band.getLeaderId());
		stmt.setString(2, band.getName());
		stmt.setString(3, band.getDescription());
		Integer bandId = connUtil_.requestInsert();
		connUtil_.setQuery("SELECT * FROM bands WHERE id = ?").setInt(1, bandId);
		return connUtil_.request(Band.class);
	}

	public Optional<Band> update(Band band) throws ClassNotFoundException, SQLException {
		PreparedStatement stmt = connUtil.setQuery("UPDATE bands SET leader_id = ?, name = ?, description = ? WHERE id = ?");
		stmt.setInt(1, band.getLeaderId());
		stmt.setString(2, band.getName());
		stmt.setString(3, band.getDescription());
		stmt.setInt(4, band.getId());
		connUtil.setQuery("SELECT * FROM bands WHERE id = ?").setInt(1, band.getId());
		return connUtil.request(Band.class);
	}
	
	public Optional<Band> update(Band band, ConnectionUtil connUtil_) throws ClassNotFoundException, SQLException {
		PreparedStatement stmt = connUtil_.setQuery("UPDATE bands SET leader_id = ?, name = ?, description = ? WHERE id = ?");
		stmt.setInt(1, band.getLeaderId());
		stmt.setString(2, band.getName());
		stmt.setString(3, band.getDescription());
		stmt.setInt(4, band.getId());
		connUtil_.setQuery("SELECT * FROM bands WHERE id = ?").setInt(1, band.getId());
		return connUtil_.request(Band.class);
	}

	public Optional<Band> delete(Band band) throws ClassNotFoundException, SQLException {
		connUtil.setQuery("DELETE FROM bands WHERE id = ?").setInt(1, band.getId());
		if (connUtil.requestUpdate(Band.class) > 0) {
			return Optional.of(band);
		}
		return Optional.empty();
	}

	public Optional<Band> delete(Band band, ConnectionUtil connUtil_) throws ClassNotFoundException, SQLException {
		connUtil_.setQuery("DELETE FROM bands WHERE id = ?").setInt(1, band.getId());
		if (connUtil_.requestUpdate(Band.class) > 0) {
			return Optional.of(band);
		}
		return Optional.empty();
	}

	public List<MemberDetail> findMembersByBandId(Integer bandId) throws ClassNotFoundException, SQLException {
		/*connUtil.setQuery("SELECT u.id, b.name, a.name, a.age, a.phone, a.position "
				+ "FROM users u JOIN apls a ON a.applicant_id = u.id "
				+ "JOIN band_member bm ON u.id = bm.member_id "
				+ "JOIN bands b ON bm.band_id = b.id WHERE b.id = ?").setInt(1, bandId);*/
		connUtil.setQuery("select * from users u join apls a ON u.id = a.applicant_id "
				+ "JOIN recruits r ON r.id = a.recruit_id WHERE band_id = ?").setInt(1, bandId);
		return connUtil.requestForList(MemberDetail.class);
	}

	public List<Band> findByMemberId(Integer id) throws ClassNotFoundException, SQLException {
		connUtil.setQuery("SELECT b.id, b.leader_id, b.name, b.description FROM bands b JOIN band_member bm "
				+ "ON b.id = bm.band_id WHERE bm.member_id = ?").setInt(1, id);
		return connUtil.requestForList(Band.class);
	}
}

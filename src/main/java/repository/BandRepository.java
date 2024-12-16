package repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import entity.Band;
import util.ConnectionUtil;

public class BandRepository {
	private final ConnectionUtil connUtil = new ConnectionUtil();

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
		Integer bandId = connUtil.requestInsert(Band.class);
		connUtil.setQuery("SELECT * FROM bands WHERE id = ?").setInt(1, bandId);
		return connUtil.request(Band.class);
	}
	
	public Optional<Band> add(Band band, ConnectionUtil connUtil_) throws ClassNotFoundException, SQLException {
		PreparedStatement stmt = connUtil_.setInsertQuery("INSERT INTO bands(leader_id, name, description) VALUES(?, ?, ?)");
		stmt.setInt(1, band.getLeaderId());
		stmt.setString(2, band.getName());
		stmt.setString(3, band.getDescription());
		Integer bandId = connUtil_.requestInsert(Band.class);
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
}

package entity;

import java.sql.Timestamp;

public class Recruit {
	private Integer id;
	private Integer bandId;
	private String position;
	private Integer acceptedId;
	private Timestamp createdAt;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBandId() {
		return bandId;
	}
	public void setBandId(Integer bandId) {
		this.bandId = bandId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Integer getAcceptedId() {
		return acceptedId;
	}
	public void setAcceptedId(Integer acceptedId) {
		this.acceptedId = acceptedId;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	
}

package entity;

import java.sql.Timestamp;

import code.ApplyStatus;

public class Application {
	private Integer applicantId;
	private Integer recruitId;
	private ApplyStatus status;
	private String position;
	private String description;
	private Timestamp createdAt;
	public Integer getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(Integer applicantId) {
		this.applicantId = applicantId;
	}
	public Integer getRecruitId() {
		return recruitId;
	}
	public void setRecruitId(Integer recruitId) {
		this.recruitId = recruitId;
	}
	public ApplyStatus getStatus() {
		return status;
	}
	public void setStatus(ApplyStatus status) {
		this.status = status;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
}
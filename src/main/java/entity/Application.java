package entity;

import java.sql.Timestamp;

import code.ApplyStatus;

public class Application {
	private Integer applicantId;
	private Integer recruitId;
	private String name;
	private Integer age;
	private String location;
	private String phone;
	private String position;
	private String description;
	private ApplyStatus status;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public ApplyStatus getStatus() {
		return status;
	}
	public void setStatus(ApplyStatus status) {
		this.status = status;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
}
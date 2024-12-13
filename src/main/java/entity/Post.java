package entity;

import java.sql.Timestamp;

public class Post {
	private Integer id;
	private Integer authorId;
	private Integer bandId;
	private String title;
	private String content;
	private Timestamp createdAt;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public Integer getBandId() {
		return bandId;
	}
	public void setBandId(Integer bandId) {
		this.bandId = bandId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
}

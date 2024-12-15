package entity;

import java.sql.Timestamp;
import java.util.List;

public class PostDetail {
	private String title;
	private List<Comment> comments;
	private List<Recruit> recruits;
	private Band band;
	private String content;
	private Timestamp createdAt;
	
	@Override
	public String toString() {
		return "PostDetail [title=" + title + ", comments=" + comments + ", recruits=" + recruits + ", band=" + band
				+ ", content=" + content + ", createdAt=" + createdAt + "]";
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
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public List<Recruit> getRecruits() {
		return recruits;
	}
	public void setRecruits(List<Recruit> recruits) {
		this.recruits = recruits;
	}
	public Band getBand() {
		return band;
	}
	public void setBand(Band band) {
		this.band = band;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
}

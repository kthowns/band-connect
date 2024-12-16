package entity;

import java.sql.Timestamp;
import java.util.List;

public class PostDetail {
	private Integer postId;
	private String title;
	private List<Comment> comments;
	private List<Recruit> recruits;
	private Band band;
	private Integer views;
	private String content;
	private Timestamp createdAt;
	@Override
	public String toString() {
		return "PostDetail [postId=" + postId + ", title=" + title + ", comments=" + comments + ", recruits=" + recruits
				+ ", band=" + band + ", views=" + views + ", content=" + content + ", createdAt=" + createdAt + "]";
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
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

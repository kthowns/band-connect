package entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class PostDetail {
	private Integer postId;
	private Integer authorId;
	private String title;
	private List<CommentDetail> commentDetails;
	private List<Recruit> recruits;
	private List<Hashtag> hashtags;
	private Band band;
	private Integer views;
	private String content;
	private Timestamp createdAt;
	@Override
	public String toString() {
		return "PostDetail [postId=" + postId + ", title=" + title + ", commentDetails=" + commentDetails
				+ ", recruits=" + recruits + ", band=" + band + ", views=" + views + ", content=" + content
				+ ", createdAt=" + createdAt + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(authorId, band, commentDetails, content, createdAt, hashtags, postId, recruits, title,
				views);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostDetail other = (PostDetail) obj;
		return Objects.equals(authorId, other.authorId) && Objects.equals(band, other.band)
				&& Objects.equals(commentDetails, other.commentDetails) && Objects.equals(content, other.content)
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(hashtags, other.hashtags)
				&& Objects.equals(postId, other.postId) && Objects.equals(recruits, other.recruits)
				&& Objects.equals(title, other.title) && Objects.equals(views, other.views);
	}

	public List<Hashtag> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<Hashtag> hashtags) {
		this.hashtags = hashtags;
	}

	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
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
	public List<CommentDetail> getCommentDetails() {
		return commentDetails;
	}
	public void setCommentDetails(List<CommentDetail> commentDetails) {
		this.commentDetails = commentDetails;
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

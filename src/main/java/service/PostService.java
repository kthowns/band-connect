package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entity.Band;
import entity.Comment;
import entity.CommentDetail;
import entity.Hashtag;
import entity.Post;
import entity.PostDetail;
import entity.Recruit;
import entity.User;
import repository.BandRepository;
import repository.CommentRepository;
import repository.HashtagRepository;
import repository.PostRepository;
import repository.RecruitRepository;
import repository.UserRepository;
import util.ConnectionUtil;

public class PostService {
	private final RecruitRepository recruitRepository = new RecruitRepository();
	private final BandRepository bandRepository = new BandRepository();
	private final PostRepository postRepository = new PostRepository();
	private final CommentRepository commentRepository = new CommentRepository();
	private final UserRepository userRepository = new UserRepository();
	private final HashtagRepository hashtagRepository = new HashtagRepository();
	
	public List<PostDetail> searchByHashtag(String hashtag) throws ClassNotFoundException, SQLException{
		List<String> hashtags = extractHashtags(hashtag);
		List<PostDetail> postDetails = new ArrayList();
		for(String tag : hashtags) {
			for(Post post : postRepository.findByHashtag(tag)) {
				PostDetail postDetail = getPostDetailByPostId(post.getId());
				System.out.println("hashtag Found : "+postDetail);
				if(!postDetails.contains(postDetail)) {
					postDetails.add(postDetail);
				}
			};
		}
		return postDetails;
	}
	
	public Post createPost(Integer userId, String title, String bandName, String content, String[] parts, String hashtag)
			throws ClassNotFoundException, SQLException {
		ConnectionUtil connUtil = new ConnectionUtil();
		connUtil.openTransactional();

		bandRepository.findByName(bandName)
		.ifPresent((b) -> {throw new RuntimeException("이미 해당하는 밴드의 구인글이 존재합니다");});
		
		Band band = new Band();
		band.setLeaderId(userId);
		band.setName(bandName);
		band.setDescription("");
		band = bandRepository.add(band, connUtil).orElseThrow(() -> new RuntimeException("Adding band failed"));
		
		Post post_ = new Post();
		post_.setTitle(title);
		post_.setAuthorId(userId);
		post_.setBandId(band.getId());
		post_.setContent(content);
		Post post = postRepository.add(post_, connUtil)
				.orElseThrow(() -> new RuntimeException("Adding post failed"));

		for(int i=0; i<parts.length; i++) {
			recruitRepository.findBybandIdAndPosition(userId, parts[i])
				.ifPresent((r) -> { throw new RuntimeException("이미 구인 중인 파트입니다 : "+r.getPosition()); });
			Recruit recruit = new Recruit();
			recruit.setBandId(band.getId());
			recruit.setPosition(parts[i]);
			recruit.setPostId(post.getId());
			recruitRepository.add(recruit, connUtil);
		}
		
		List<String> hashtags = extractHashtags(hashtag);
		for(String tag : hashtags) {
			Hashtag htag = new Hashtag();
			htag.setHashtag(tag);
			htag.setPostId(post.getId());
			hashtagRepository.add(htag, connUtil);
		}
		
		connUtil.commitTransactional();
		return post;
	}
	
    private static List<String> extractHashtags(String str) {
        // 정규 표현식을 사용하여 #으로 시작하는 단어를 추출
        Pattern pattern = Pattern.compile("#\\w+");
        Matcher matcher = pattern.matcher(str);
        
        List<String> hashtags = new ArrayList<>();
        
        // 매칭된 모든 항목을 리스트에 추가
        while (matcher.find()) {
            hashtags.add(matcher.group());
        }
        
        System.out.println("Extracting hashtag : "+hashtags);
        
        // 리스트를 배열로 변환하여 반환
        return hashtags;
    }
	
	public PostDetail getPostDetailByPostId(Integer postId) throws ClassNotFoundException, RuntimeException, SQLException {
		PostDetail postDetail = new PostDetail();
			List<CommentDetail> commentDetails = new ArrayList();
			List<Comment> comments = commentRepository.findByPostId(postId);
			for(Comment comment : comments) {
				CommentDetail commentDetail = new CommentDetail();
				User author = userRepository.findById(comment.getAuthorId())
						.orElseThrow(() -> new RuntimeException("User not found"));
				commentDetail.setAuthor(author);
				commentDetail.setContent(comment.getContent());
				commentDetail.setCreatedAt(comment.getCreatedAt());
				commentDetail.setId(comment.getId());
				commentDetail.setPostId(postId);
				commentDetails.add(commentDetail);
			}
			Post post = postRepository.findById(postId)
					.orElseThrow(() -> new RuntimeException("Post not found"));
			Band band = bandRepository.findById(post.getBandId())
					.orElseThrow(() -> new RuntimeException("Band not found"));
			List<Hashtag> hashtags = this.getHashtagsByPostId(postId);
			postDetail.setPostId(post.getId());
			postDetail.setBand(band);
			postDetail.setHashtags(hashtags);
			postDetail.setAuthorId(post.getAuthorId());
			postDetail.setRecruits(recruitRepository.findByBandId(post.getBandId()));
			postDetail.setTitle(post.getTitle());
			postDetail.setViews(post.getViews());
			postDetail.setCreatedAt(post.getCreatedAt());
			postDetail.setCommentDetails(commentDetails);
			postDetail.setContent(post.getContent());

		return postDetail;
	}
	
	public List<PostDetail> getPostDetails() throws ClassNotFoundException, SQLException{
		List<PostDetail> postDetails = new ArrayList<PostDetail>();
		List<Post> posts = postRepository.findAll();
		posts.forEach((post) -> {
			try {
				postDetails.add(getPostDetailByPostId(post.getId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return postDetails;
	}
	
	public List<PostDetail> getPostDetailsByAuthorId(Integer authorId) throws ClassNotFoundException, SQLException{
		List<PostDetail> postDetails = new ArrayList<PostDetail>();
		List<Post> posts = postRepository.findByAuthorId(authorId);
		posts.forEach((post) -> {
			try {
				postDetails.add(getPostDetailByPostId(post.getId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return postDetails;
	}
	
	public Integer increaseViews(Integer postId) throws ClassNotFoundException, SQLException {
		return postRepository.increaseViews(postId);
	}

	public void deleteById(Integer postId) throws ClassNotFoundException, SQLException {
		postRepository.deleteById(postId);
	}

	public List<Hashtag> getHashtagsByPostId(Integer postId) throws ClassNotFoundException, SQLException {
		return hashtagRepository.findByPostId(postId);
	}
}

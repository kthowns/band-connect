package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.Band;
import entity.Post;
import entity.PostDetail;
import entity.Recruit;
import repository.BandRepository;
import repository.PostRepository;
import repository.RecruitRepository;
import util.ConnectionUtil;

public class PostService {
	private final RecruitRepository recruitRepository = new RecruitRepository();
	private final BandRepository bandRepository = new BandRepository();
	private final PostRepository postRepository = new PostRepository();

	public Post createPost(Integer userId, String title, String bandName, String content, String[] parts)
			throws ClassNotFoundException, SQLException {
		ConnectionUtil connUtil = new ConnectionUtil();
		connUtil.openTransactional();

		Optional<Band> optBand = bandRepository.findByName(bandName);
		Band band;
		if (optBand.isEmpty()) {
			Band band_ = new Band();
			band_.setLeaderId(userId);
			band_.setName(bandName);
			band_.setDescription("");
			band = bandRepository.add(band_, connUtil).orElseThrow(() -> new RuntimeException("Adding band failed"));
		} else {
			band = optBand.get();
		}
		
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
		
		connUtil.commitTransactional();
		return post;
	}
	
	public PostDetail getPostDetailByPostId(Integer postId) throws ClassNotFoundException, RuntimeException, SQLException {
		PostDetail postDetail = new PostDetail();
			Post post = postRepository.findById(postId)
					.orElseThrow(() -> new RuntimeException("Post not found"));
			Band band = bandRepository.findById(post.getBandId())
					.orElseThrow(() -> new RuntimeException("Band not found"));
			postDetail.setPostId(post.getId());
			postDetail.setBand(band);
			postDetail.setRecruits(recruitRepository.findByBandId(post.getBandId()));
			postDetail.setTitle(post.getTitle());
			postDetail.setViews(post.getViews());
			postDetail.setCreatedAt(post.getCreatedAt());
			postDetail.setContent(post.getContent());

		return postDetail;
	}
	
	public List<PostDetail> getPostDetails() throws ClassNotFoundException, SQLException{
		List<PostDetail> postDetails = new ArrayList<PostDetail>();
		List<Post> posts = postRepository.findAll();
		posts.forEach((post) -> {
			try {
				PostDetail postDetail = new PostDetail();
				Band band = bandRepository.findById(post.getBandId())
						.orElseThrow(() -> new RuntimeException("Band not found"));
				postDetail.setPostId(post.getId());
				postDetail.setBand(band);
				postDetail.setRecruits(recruitRepository.findByBandId(post.getBandId()));
				postDetail.setTitle(post.getTitle());
				postDetail.setViews(post.getViews());
				postDetail.setCreatedAt(post.getCreatedAt());
				postDetail.setContent(post.getContent());
				postDetails.add(postDetail);
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
				PostDetail postDetail = new PostDetail();
				Band band = bandRepository.findById(post.getBandId())
						.orElseThrow(() -> new RuntimeException("Band not found"));
				postDetail.setPostId(post.getId());
				postDetail.setBand(band);
				postDetail.setRecruits(recruitRepository.findByBandId(post.getBandId()));
				postDetail.setTitle(post.getTitle());
				postDetail.setCreatedAt(post.getCreatedAt());
				postDetail.setContent(post.getContent());
				postDetail.setViews(post.getViews());
				postDetails.add(postDetail);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return postDetails;
	}
	
	public Integer increaseViews(Integer postId) throws ClassNotFoundException, SQLException {
		return postRepository.increaseViews(postId);
	}
}

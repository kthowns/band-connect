package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Apl;
import entity.AplDetail;
import entity.Post;
import entity.Recruit;
import repository.AplRepository;
import repository.PostRepository;
import repository.RecruitRepository;

public class AplService {
	private final AplRepository aplRepository = new AplRepository();
	private final PostRepository postRepository = new PostRepository();
	private final RecruitRepository recruitRepository = new RecruitRepository();

	public List<Apl> getAplByPostAuthorId(Integer authorId) throws ClassNotFoundException, SQLException{
		List<Apl> apls = new ArrayList();
		List<Post> posts = postRepository.findByAuthorId(authorId);
		for(Post post : posts) {
			List<Apl> apls_ = this.getAplByPostId(post.getId());
			for(Apl apl : apls_) {
				apls.add(apl);
			}
		}
		return apls;
	}
	
	public List<Apl> getAplByApplicantId(Integer applicantId) throws ClassNotFoundException, SQLException{
		return aplRepository.findByApplicantId(applicantId);
	}
	
	public List<Apl> getAplByPostId(Integer postId) throws ClassNotFoundException, SQLException{
		return aplRepository.findByPostId(postId);
	}
	
	public List<AplDetail> getAplDetails(Integer userId) throws ClassNotFoundException, SQLException{
		List<Apl> apls = aplRepository.findByApplicantId(userId);
		List<AplDetail> aplDetails = new ArrayList();
		for(Apl apl : apls) {
			AplDetail aplDetail = new AplDetail();
			Recruit recruit = recruitRepository.findById(apl.getRecruitId())
			.orElseThrow(() -> new RuntimeException("Recruit not found"));
			Post post = postRepository.findById(recruit.getPostId())
					.orElseThrow(() -> new RuntimeException("Post not found"));
			aplDetail.setApl(apl);
			aplDetail.setRecruit(recruit);
			aplDetail.setPost(post);
			aplDetails.add(aplDetail);
		}
		return aplDetails;
	}
	
	public Apl createApl(Apl apl) throws ClassNotFoundException, RuntimeException, SQLException {
		validateDuplicate(apl);
		return aplRepository.add(apl)
				.orElseThrow(() -> new RuntimeException("Adding apl failed"));
	}
	
	public void validateDuplicate(Apl apl) throws ClassNotFoundException, SQLException {
		aplRepository.findById(apl.getApplicantId(), apl.getRecruitId())
		.ifPresent((a) -> { throw new RuntimeException("해당하는 파트에 중복되는 지원이 존재합니다"); });
	}

	public Integer getApplicantNumber(Integer postId) throws ClassNotFoundException, SQLException {
		return aplRepository.getApplicantNumber(postId);
	}

	public void accept(Integer recruitId, Integer applicantId) throws ClassNotFoundException, SQLException {
		aplRepository.accept(recruitId, applicantId);
	}

	public void reject(Integer recruitId, Integer applicantId) throws ClassNotFoundException, SQLException {
		aplRepository.reject(recruitId, applicantId);
	}
}
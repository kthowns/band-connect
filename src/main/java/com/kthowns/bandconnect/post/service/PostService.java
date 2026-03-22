package com.kthowns.bandconnect.post.service;

import com.kthowns.bandconnect.application.dto.PostApplicantCount;
import com.kthowns.bandconnect.application.repository.ApplicationRepository;
import com.kthowns.bandconnect.band.dto.BandDto;
import com.kthowns.bandconnect.band.entity.Band;
import com.kthowns.bandconnect.band.repository.BandRepository;
import com.kthowns.bandconnect.common.exception.CustomException;
import com.kthowns.bandconnect.common.exception.CustomResponseCode;
import com.kthowns.bandconnect.post.dto.CommentDto;
import com.kthowns.bandconnect.post.dto.HashtagDto;
import com.kthowns.bandconnect.post.dto.RecruitPostDetail;
import com.kthowns.bandconnect.post.dto.RecruitPostSearch;
import com.kthowns.bandconnect.post.entity.Comment;
import com.kthowns.bandconnect.post.entity.Hashtag;
import com.kthowns.bandconnect.post.entity.PostHashtag;
import com.kthowns.bandconnect.post.entity.RecruitPost;
import com.kthowns.bandconnect.post.repository.CommentRepository;
import com.kthowns.bandconnect.post.repository.PostHashtagRepository;
import com.kthowns.bandconnect.post.repository.RecruitPostRepository;
import com.kthowns.bandconnect.recruit.dto.RecruitDto;
import com.kthowns.bandconnect.recruit.entity.Recruit;
import com.kthowns.bandconnect.recruit.repository.RecruitRepository;
import com.kthowns.bandconnect.user.dto.UserDto;
import com.kthowns.bandconnect.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final RecruitPostRepository recruitPostRepository;
    private final BandRepository bandRepository;
    private final PostHashtagRepository postHashtagRepository;
    private final RecruitRepository recruitRepository;
    private final ApplicationRepository applicationRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public RecruitPostDetail getRecruitPostDetail(Long recruitPostId) {
        RecruitPost recruitPost = recruitPostRepository.findByIdWithAuthorAndBand(recruitPostId)
                .orElseThrow(() -> new CustomException(CustomResponseCode.POST_NOT_FOUND));

        List<Hashtag> hashtags = postHashtagRepository.findHashtagsByPostId(recruitPostId);
        Long applicantsCount = applicationRepository.countByRecruitRecruitPost_Id(recruitPostId);
        List<Recruit> recruits = recruitRepository.findByRecruitPost_Id(recruitPostId);
        List<Comment> comments = commentRepository.findByPostId(recruitPostId);

        return RecruitPostDetail.builder()
                .id(recruitPostId)
                .comments(comments.stream().map(CommentDto::fromEntity).toList())
                .content(recruitPost.getContent())
                .applicantsCount(applicantsCount)
                .views(recruitPost.getViews())
                .title(recruitPost.getTitle())
                .band(BandDto.fromEntity(recruitPost.getBand()))
                .author(UserDto.fromEntity(recruitPost.getAuthor()))
                .hashtags(hashtags.stream().map(HashtagDto::fromEntity).toList())
                .recruits(recruits.stream().map(RecruitDto::fromEntity).toList())
                .createdAt(recruitPost.getCreatedAt())
                .build();
    }

    @Transactional
    public void increasePostView(Long postId) {
        recruitPostRepository.incrementViews(postId);
    }

    @Transactional(readOnly = true)
    public List<RecruitPostDetail> getRecruitPosts(RecruitPostSearch search) {
        List<String> hashtags = search.getHashtags();
        String keyword = search.getKeyword();
        boolean hasTags = hashtags != null && hashtags.isEmpty();
        boolean hasKeyword = keyword != null && keyword.isEmpty();

        List<RecruitPost> recruitPosts;
        if (hasTags && hasKeyword) {
            recruitPosts = recruitPostRepository
                    .findByKeywordAndHashtags(search.getKeyword(), search.getHashtags());
        } else if (hasTags) {
            recruitPosts = recruitPostRepository.findByHashtags(search.getHashtags());
        } else if (hasKeyword) {
            recruitPosts = recruitPostRepository.findByKeyword(search.getKeyword());
        } else {
            recruitPosts = recruitPostRepository.findAllWithBandAndAuthor();
        }

        List<Long> recruitPostIds = recruitPosts.stream()
                .map(RecruitPost::getId).toList();

        if (recruitPostIds.isEmpty()) {
            return List.of();
        }

        List<PostHashtag> postHashtags = postHashtagRepository.findByRecruitPostIds(recruitPostIds);
        Map<Long, List<PostHashtag>> hashtagMap = postHashtags.stream()
                .collect(Collectors.groupingBy((ph) -> ph.getRecruitPost().getId()));

        List<Recruit> recruits = recruitRepository.findByRecruitPostIds(recruitPostIds);
        Map<Long, List<Recruit>> recruitMap = recruits.stream()
                .collect(Collectors.groupingBy((ph) -> ph.getRecruitPost().getId()));

        List<Comment> comments = commentRepository.findByRecruitPostIds(recruitPostIds);
        Map<Long, List<Comment>> commentMap = comments.stream()
                .collect(Collectors.groupingBy((ph) -> ph.getPost().getId()));

        List<PostApplicantCount> applicantsCount = applicationRepository.countByRecruitPostIds(recruitPostIds);
        Map<Long, Long> applicantsCountMap = applicantsCount.stream()
                .collect(Collectors.toMap(
                        PostApplicantCount::getPostId,
                        PostApplicantCount::getCount
                ));

        return recruitPosts.stream()
                .map((recruitPost) ->
                        RecruitPostDetail.builder()
                                .id(recruitPost.getId())
                                .author(UserDto.fromEntity(recruitPost.getAuthor()))
                                .band(BandDto.fromEntity(recruitPost.getBand()))
                                .views(recruitPost.getViews())
                                .title(recruitPost.getTitle())
                                .content(recruitPost.getContent())
                                .createdAt(recruitPost.getCreatedAt())
                                .applicantsCount(
                                        applicantsCountMap.getOrDefault(recruitPost.getId(), 0L)
                                )
                                .recruits(recruitMap.getOrDefault(recruitPost.getId(), List.of())
                                        .stream().map(RecruitDto::fromEntity)
                                        .toList())
                                .hashtags(hashtagMap.getOrDefault(recruitPost.getId(), List.of())
                                        .stream().map((ph) -> HashtagDto.fromEntity(ph.getHashtag()))
                                        .toList())
                                .comments(commentMap.getOrDefault(recruitPost.getId(), List.of())
                                        .stream().map(CommentDto::fromEntity)
                                        .toList())
                                .build()
                ).toList();
    }

    @Transactional
    public RecruitPost createPost(String title, String content, Long bandId, User user) {
        Band band = bandRepository.findById(bandId).orElseThrow(
                () -> new CustomException(CustomResponseCode.BAND_NOT_FOUND)
        );

        return recruitPostRepository.save(
                RecruitPost.builder()
                        .title(title)
                        .content(content)
                        .author(user)
                        .band(band)
                        .build()
        );
    }

    @Transactional
    public void deletePost(Long postId, User user) {
        RecruitPost recruitPost = recruitPostRepository.findByIdWithAuthor(postId)
                .orElseThrow(() -> new CustomException(CustomResponseCode.POST_NOT_FOUND));

        if (!recruitPost.getAuthor().getId().equals(user.getId())) {
            throw new CustomException(CustomResponseCode.FORBIDDEN);
        }

        recruitPostRepository.delete(recruitPost);
    }
}

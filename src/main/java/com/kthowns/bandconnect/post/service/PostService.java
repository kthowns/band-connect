package com.kthowns.bandconnect.post.service;

import com.kthowns.bandconnect.application.dto.ApplicationDto;
import com.kthowns.bandconnect.application.entity.Application;
import com.kthowns.bandconnect.application.repository.ApplicationRepository;
import com.kthowns.bandconnect.band.dto.BandDto;
import com.kthowns.bandconnect.band.entity.Band;
import com.kthowns.bandconnect.band.repository.BandRepository;
import com.kthowns.bandconnect.common.exception.CustomException;
import com.kthowns.bandconnect.common.exception.CustomResponseCode;
import com.kthowns.bandconnect.post.dto.*;
import com.kthowns.bandconnect.post.entity.Comment;
import com.kthowns.bandconnect.post.entity.Hashtag;
import com.kthowns.bandconnect.post.entity.PostHashtag;
import com.kthowns.bandconnect.post.entity.RecruitPost;
import com.kthowns.bandconnect.post.repository.CommentRepository;
import com.kthowns.bandconnect.post.repository.PostHashtagRepository;
import com.kthowns.bandconnect.post.repository.RecruitPostRepository;
import com.kthowns.bandconnect.recruit.dto.RecruitDetail;
import com.kthowns.bandconnect.recruit.dto.RecruitDto;
import com.kthowns.bandconnect.recruit.entity.Recruit;
import com.kthowns.bandconnect.recruit.repository.RecruitRepository;
import com.kthowns.bandconnect.user.dto.UserDto;
import com.kthowns.bandconnect.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final RecruitPostRepository recruitPostRepository;
    private final BandRepository bandRepository;
    private final PostHashtagRepository postHashtagRepository;
    private final RecruitRepository recruitRepository;
    private final ApplicationRepository applicationRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public RecruitPostDetail getRecruitPostDetail(Long recruitPostId) {
        RecruitPost recruitPost = recruitPostRepository.findByIdWithAuthorAndBand(recruitPostId)
                .orElseThrow(() -> new CustomException(CustomResponseCode.POST_NOT_FOUND));

        List<Hashtag> hashtags = postHashtagRepository.findHashtagsByPostId(recruitPostId);
        List<Recruit> recruits = recruitRepository.findByRecruitPost_Id(recruitPostId);
        List<Comment> comments = commentRepository.findByPostId(recruitPostId);

        long totalApplicantCount = 0L;

        for (Recruit recruit : recruits) {
            long applicantCount = applicationRepository.countByRecruit_Id(recruit.getId());

            recruit.setApplicantCount(
                    applicantCount
            );

            totalApplicantCount += recruit.getApplicantCount();
        }

        return RecruitPostDetail.builder()
                .id(recruitPostId)
                .comments(comments.stream().map(CommentDto::fromEntity).toList())
                .content(recruitPost.getContent())
                .applicantCount(totalApplicantCount)
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
    public Page<RecruitPostSimpleDto> searchRecruitPosts(String searchKeyword, Pageable pageable) {
        List<String> hashtags = new ArrayList<>();
        String keyword = "";

        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            // 해시태그 추출 (#로 시작하고 공백 전까지의 문자열)
            Pattern tagPattern = Pattern.compile("#[\\w가-힣]+");
            Matcher matcher = tagPattern.matcher(searchKeyword);

            while (matcher.find()) {
                hashtags.add(matcher.group()); // #기타, #베이스 등을 리스트에 담음
            }

            // 키워드 정제 (해시태그 부분을 제거하고 남은 문자열)
            keyword = matcher.replaceAll("").replaceAll("\\s+", " ").trim();
        }

        boolean hasTags = !hashtags.isEmpty();
        boolean hasKeyword = !keyword.isEmpty();

        Page<RecruitPost> recruitPosts;
        if (hasTags && hasKeyword) {
            recruitPosts = recruitPostRepository
                    .findByKeywordAndHashtags(keyword, hashtags, pageable);
        } else if (hasTags) {
            recruitPosts = recruitPostRepository.findByHashtags(hashtags, pageable);
        } else if (hasKeyword) {
            recruitPosts = recruitPostRepository.findByKeyword(keyword, pageable);
        } else {
            recruitPosts = recruitPostRepository.findAllWithBandAndAuthor(pageable);
        }

        List<Long> recruitPostIds = recruitPosts.stream()
                .map(RecruitPost::getId).toList();

        if (recruitPostIds.isEmpty()) {
            return Page.empty(pageable);
        }

        // 해시태그 Batch fetch By Map Grouping
        List<PostHashtag> postHashtags = postHashtagRepository.findByRecruitPostIds(recruitPostIds);
        Map<Long, List<PostHashtag>> hashtagMap = postHashtags.stream()
                .collect(Collectors.groupingBy((ph) -> ph.getRecruitPost().getId()));

        // Recruit Strings Batch fetch By Map Grouping
        List<Recruit> recruits = recruitRepository.findByRecruitPostIds(recruitPostIds);
        Map<Long, List<Recruit>> recruitPartsMap = recruits.stream()
                .collect(Collectors.groupingBy((r) -> r.getRecruitPost().getId()));

        // Comment count batch fetch
        List<RecruitPostDto.PostCommentCount> commentCounts = commentRepository.countByRecruitPostIds(recruitPostIds);
        Map<Long, Long> commentCountsMap = commentCounts.stream()
                .filter((pcc) -> pcc.getCommentCount() != null)
                .collect(Collectors.toMap(
                        RecruitPostDto.PostCommentCount::getPostId,
                        RecruitPostDto.PostCommentCount::getCommentCount
                ));

        return convertToSimple(
                recruitPosts,
                hashtagMap,
                recruitPartsMap,
                commentCountsMap
        );
    }

    @Transactional(readOnly = true)
    public Page<PostWithRecruits> getMyPostsWithRecruits(Long userId, Pageable pageable) {
        Page<RecruitPost> recruitPosts = recruitPostRepository.findByAuthorIdWithBand(userId, pageable);
        List<Long> recruitPostIds = recruitPosts.stream()
                .map(RecruitPost::getId).toList();

        if (recruitPostIds.isEmpty()) {
            return Page.empty(pageable);
        }

        List<Recruit> recruits = recruitRepository.findByRecruitPostIds(recruitPostIds);
        List<Long> recruitIds = recruits.stream()
                .map(Recruit::getId).toList();

        List<Application> applications = applicationRepository.findByRecruit_IdIn(recruitIds);
        Map<Long, List<Application>> applicationsMap = applications.stream()
                .collect(Collectors.groupingBy((app) -> app.getRecruit().getId()));

        List<RecruitDetail> recruitDetails = recruits.stream()
                .map((recruit) -> RecruitDetail.builder()
                        .id(recruit.getId())
                        .member(recruit.getMember() != null ? UserDto.fromEntity(recruit.getMember()) : null)
                        .position(recruit.getPosition())
                        .createdAt(recruit.getCreatedAt())
                        .post(RecruitPostDto.fromEntity(recruit.getRecruitPost()))
                        .applications(
                                applicationsMap.getOrDefault(recruit.getId(), List.of()).stream()
                                        .map(ApplicationDto::fromEntity).toList()
                        )
                        .build()).toList();
        Map<Long, List<RecruitDetail>> recruitDetailsMap = recruitDetails.stream()
                .collect(Collectors.groupingBy((rd) -> rd.getPost().getId()));

        return recruitPosts.map((recruitPost) ->
                PostWithRecruits.builder()
                        .id(recruitPost.getId())
                        .bandName(recruitPost.getBand().getName())
                        .title(recruitPost.getTitle())
                        .createdAt(recruitPost.getCreatedAt())
                        .recruits(recruitDetailsMap.getOrDefault(recruitPost.getId(), List.of()))
                        .build()
        );
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

    @Transactional
    public RecruitPost updatePost(Long postId, String title, String content, User user) {
        RecruitPost recruitPost = recruitPostRepository.findByIdWithAuthor(postId)
                .orElseThrow(() -> new CustomException(CustomResponseCode.POST_NOT_FOUND));

        if (!recruitPost.getAuthor().getId().equals(user.getId())) {
            throw new CustomException(CustomResponseCode.FORBIDDEN);
        }

        recruitPost.setTitle(title);
        recruitPost.setContent(content);

        return recruitPostRepository.save(recruitPost);
    }


    private Page<RecruitPostSimpleDto> convertToSimple(
            Page<RecruitPost> recruitPosts,
            Map<Long, List<PostHashtag>> hashtagMap,
            Map<Long, List<Recruit>> recruitMap,
            Map<Long, Long> commentCountsMap
    ) {
        return recruitPosts.map((recruitPost) ->
                RecruitPostSimpleDto.builder()
                        .id(recruitPost.getId())
                        .authorName(recruitPost.getAuthor().getName())
                        .bandName(recruitPost.getBand().getName())
                        .views(recruitPost.getViews())
                        .title(recruitPost.getTitle())
                        .createdAt(recruitPost.getCreatedAt())
                        .recruitParts(
                                recruitMap.getOrDefault(recruitPost.getId(), List.of())
                                        .stream()
                                        .filter((r) -> r.getMember() == null)
                                        .map(Recruit::getPosition).toList()
                        )
                        .hashtags(
                                hashtagMap.getOrDefault(recruitPost.getId(), List.of())
                                        .stream().map((ph) -> ph.getHashtag().getName()).toList()
                        )
                        .commentCount(
                                commentCountsMap.getOrDefault(recruitPost.getId(), 0L)
                        )
                        .applicantCount(
                                recruitMap.getOrDefault(recruitPost.getId(), List.of())
                                        .stream()
                                        .filter((r) -> r.getApplicantCount() != null)
                                        .mapToLong(Recruit::getApplicantCount).sum()
                        )
                        .build()
        );
    }
}

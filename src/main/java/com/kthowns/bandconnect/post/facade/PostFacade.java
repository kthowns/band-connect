package com.kthowns.bandconnect.post.facade;

import com.kthowns.bandconnect.post.dto.AddPostRequest;
import com.kthowns.bandconnect.post.dto.EditPostRequest;
import com.kthowns.bandconnect.post.dto.RecruitPostDetail;
import com.kthowns.bandconnect.post.entity.RecruitPost;
import com.kthowns.bandconnect.post.service.HashtagService;
import com.kthowns.bandconnect.post.service.PostService;
import com.kthowns.bandconnect.recruit.service.RecruitService;
import com.kthowns.bandconnect.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostFacade {
    private final PostService postService;
    private final HashtagService hashtagService;
    private final RecruitService recruitService;

    @Transactional
    public void createPost(AddPostRequest request, User user) {
        RecruitPost post = postService.createPost(request.getTitle(), request.getContent(), request.getBandId(), user);
        hashtagService.linkHashtags(post, request.getHashtag());
        recruitService.createRecruits(post, request.getParts());
    }

    @Transactional
    public void updatePost(Long postId, EditPostRequest request, User user) {
        RecruitPost post = postService.updatePost(postId, request.getTitle(), request.getContent(), user);
        hashtagService.updateHashtags(post, request.getHashtag());
        recruitService.updateRecruits(post, request.getParts());
    }

    @Transactional
    public RecruitPostDetail getRecruitPostDetail(Long postId) {
        postService.increasePostView(postId);
        return postService.getRecruitPostDetail(postId);
    }
}

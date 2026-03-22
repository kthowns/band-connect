package com.kthowns.bandconnect.post.service;

import com.kthowns.bandconnect.common.exception.CustomException;
import com.kthowns.bandconnect.common.exception.CustomResponseCode;
import com.kthowns.bandconnect.post.dto.AddCommentRequest;
import com.kthowns.bandconnect.post.entity.Comment;
import com.kthowns.bandconnect.post.entity.RecruitPost;
import com.kthowns.bandconnect.post.repository.CommentRepository;
import com.kthowns.bandconnect.post.repository.RecruitPostRepository;
import com.kthowns.bandconnect.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final RecruitPostRepository recruitPostRepository;

    @Transactional
    public void createComment(Long postId, AddCommentRequest request, User user) {
        RecruitPost post = recruitPostRepository.findById(postId)
                .orElseThrow(() -> new CustomException(CustomResponseCode.POST_NOT_FOUND));

        commentRepository.save(
                Comment.builder()
                        .author(user)
                        .content(request.getContent())
                        .post(post)
                        .build()
        );
    }

    @Transactional
    public void deleteComment(Long id, User user) {
        Comment comment = commentRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new CustomException(CustomResponseCode.COMMENT_NOT_FOUND));

        if (!comment.getAuthor().getId().equals(user.getId())) {
            throw new CustomException(CustomResponseCode.FORBIDDEN);
        }

        commentRepository.deleteById(id);
    }
}

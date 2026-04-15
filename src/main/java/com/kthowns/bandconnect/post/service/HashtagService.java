package com.kthowns.bandconnect.post.service;

import com.kthowns.bandconnect.post.entity.Hashtag;
import com.kthowns.bandconnect.post.entity.PostHashtag;
import com.kthowns.bandconnect.post.entity.RecruitPost;
import com.kthowns.bandconnect.post.repository.HashtagRepository;
import com.kthowns.bandconnect.post.repository.PostHashtagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashtagService {
    private final PostHashtagRepository postHashtagRepository;
    private final HashtagRepository hashtagRepository;

    @Transactional
    public void linkHashtags(RecruitPost post, String tagString) {
        List<String> hashtags = Arrays.stream(tagString.split(" "))
                .map((hashtag) -> {
                    if (!hashtag.startsWith("#")) {
                        hashtag = "#" + hashtag;
                    }
                    return hashtag;
                }).toList();

        Set<String> uniqueTagNames = new HashSet<>(hashtags);

        log.info("hashtagStrings : {}", tagString);
        for (String hashtagString : uniqueTagNames) {
            Hashtag hashtag = hashtagRepository.findByName(hashtagString)
                    .orElseGet(() -> hashtagRepository.save(Hashtag.builder()
                            .name(hashtagString)
                            .build()
                    ));

            postHashtagRepository.save(
                    PostHashtag.builder()
                            .hashtag(hashtag)
                            .recruitPost(post)
                            .build()
            );
        }
    }

    @Transactional
    public void updateHashtags(RecruitPost post, String tagString) {
        postHashtagRepository.deleteByRecruitPostId(post.getId());
        linkHashtags(post, tagString);
    }
}

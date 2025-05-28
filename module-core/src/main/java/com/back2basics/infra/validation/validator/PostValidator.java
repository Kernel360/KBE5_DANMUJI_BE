package com.back2basics.infra.validation.validator;

import com.back2basics.infra.exception.post.PostErrorCode;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostValidator {

    private final PostRepositoryPort postRepository;

    public Post findPost(Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));
    }

    public void isAuthor(Post post, Long requesterId) {
        if (!post.getAuthorId().equals(requesterId)) {
            log.info("Invalid post author: {}. Expected: {}", post.getAuthorId(), requesterId);
            throw new PostException(PostErrorCode.INVALID_POST_AUTHOR);
        }
    }
}

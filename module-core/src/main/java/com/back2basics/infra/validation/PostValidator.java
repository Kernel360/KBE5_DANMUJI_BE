package com.back2basics.infra.validation;

import com.back2basics.model.post.Post;
import com.back2basics.port.out.post.PostRepositoryPort;
import com.back2basics.service.post.exception.PostErrorCode;
import com.back2basics.service.post.exception.PostException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostValidator {

    private final PostRepositoryPort postRepository;

    public Post findPost(Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));
    }

    public void isAuthor(Post post, String requesterName) {
        if (!post.getAuthorName().equals(requesterName)) {
            throw new PostException(PostErrorCode.INVALID_POST_AUTHOR);
        }
    }
}

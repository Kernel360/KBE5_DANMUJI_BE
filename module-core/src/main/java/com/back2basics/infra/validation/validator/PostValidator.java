package com.back2basics.infra.validation.validator;

import com.back2basics.infra.exception.post.PostErrorCode;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostRepositoryPort;
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

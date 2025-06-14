package com.back2basics.infra.validation.validator;

import com.back2basics.infra.exception.post.PostErrorCode;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostReadPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostValidator {

    private final PostReadPort postReadPort;

    public Post findPost(Long id) {
        return postReadPort.findById(id)
            .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));
    }

    public void isAuthor(Post post, Long requesterId) {
        if (!post.getAuthor().getId().equals(requesterId)) {
            throw new PostException(PostErrorCode.INVALID_POST_AUTHOR);
        }
    }
}

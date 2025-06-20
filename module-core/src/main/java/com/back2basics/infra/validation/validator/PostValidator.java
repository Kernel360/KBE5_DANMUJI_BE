package com.back2basics.infra.validation.validator;

import com.back2basics.infra.exception.post.PostErrorCode;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.port.out.PostReadPort;
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
        if (!post.getAuthorId().equals(requesterId)) {
            throw new PostException(PostErrorCode.INVALID_POST_AUTHOR);
        }
    }

    public void findParentPost(Long parentId) {
        if (parentId == null) {
            return;
        }

        boolean exists = postReadPort.findById(parentId).isPresent();
        if (!exists) {
            throw new PostException(PostErrorCode.POST_NOT_FOUND);
        }
    }
}

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

    // todo : isExists랑 findPost랑 뭔가 중복 쓰임인거같긴한데 또 막상 serviceImpl에서 구현할 때 보면 다른게 의미상 맞는거같기도한데 이게맞나?

    public Boolean isExists(Long id) {
        findPost(id);
        return true;
    }

    public void isAuthor(Post post, String requesterName) {
        if (!post.getAuthorName().equals(requesterName)) {
            throw new PostException(PostErrorCode.UNAUTHORIZED_AUTHOR);
        }
    }
}

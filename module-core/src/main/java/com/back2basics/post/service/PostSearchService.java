package com.back2basics.post.service;

import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.port.in.PostSearchUseCase;
import com.back2basics.post.port.out.PostSearchPort;
import com.back2basics.post.service.result.PostReadResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSearchService implements PostSearchUseCase {

    private final PostSearchPort postSearchPort;

    @Override
    public Page<PostReadResult> searchPost(Long userId, String title, String clientCompany,
        String developerCompany, String author, Integer priority, PostStatus status,
        PostType type, Pageable pageable) {
        return postSearchPort.search(title, clientCompany, developerCompany, author, priority,
                status, type, pageable)
            .map(PostReadResult::toResult);
    }
}

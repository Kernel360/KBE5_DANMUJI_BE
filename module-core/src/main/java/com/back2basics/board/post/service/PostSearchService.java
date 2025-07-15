package com.back2basics.board.post.service;

import com.back2basics.board.post.port.in.PostSearchUseCase;
import com.back2basics.board.post.port.in.command.PostSearchCommand;
import com.back2basics.board.post.port.out.PostSearchPort;
import com.back2basics.board.post.service.result.PostSummaryReadResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSearchService implements PostSearchUseCase {

    private final PostSearchPort postSearchPort;

    @Override
    public Page<PostSummaryReadResult> searchPost(Long userId, PostSearchCommand command,
        Pageable pageable) {
        return postSearchPort.search(command, pageable)
            .map(PostSummaryReadResult::toResult);
    }

    @Override
    public Page<PostSummaryReadResult> searchPostWithCountQuery(Long userId, PostSearchCommand command,
        Pageable pageable) {
        return postSearchPort.searchWithCountQuery(command, pageable)
            .map(PostSummaryReadResult::toResult);
    }
}

package com.back2basics.board.post.port.in;

import com.back2basics.board.post.port.in.command.PostSearchCommand;
import com.back2basics.board.post.service.result.PostDetailReadResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostSearchUseCase {

    Page<PostDetailReadResult> searchPost(Long userId, PostSearchCommand command,
        Pageable pageable);
}

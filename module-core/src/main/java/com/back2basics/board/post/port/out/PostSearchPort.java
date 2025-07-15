package com.back2basics.board.post.port.out;

import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.port.in.command.PostSearchCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostSearchPort {

    Page<Post> search(PostSearchCommand command, Pageable pageable);

    Page<Post> searchWithCountQuery(PostSearchCommand command, Pageable pageable);
}

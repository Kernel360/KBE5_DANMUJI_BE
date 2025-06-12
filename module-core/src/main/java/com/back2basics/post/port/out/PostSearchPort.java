package com.back2basics.post.port.out;

import com.back2basics.post.model.Post;
import com.back2basics.post.port.in.command.PostSearchCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostSearchPort {

    Page<Post> search(PostSearchCommand command, Pageable pageable);
}

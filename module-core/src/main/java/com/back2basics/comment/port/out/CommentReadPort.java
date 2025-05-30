package com.back2basics.comment.port.out;

import com.back2basics.comment.model.Comment;
import java.util.Optional;

public interface CommentReadPort {

    Optional<Comment> findById(Long id);
}

package com.back2basics.comment.port.out;

import com.back2basics.comment.model.Comment;
import java.util.Optional;

public interface CommentRepositoryPort {

    Long save(Comment comment);

    Optional<Comment> findById(Long id);

    void update(Comment comment);

    void delete(Comment comment);
}

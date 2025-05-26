package com.back2basics.port.out.comment;

import com.back2basics.model.comment.Comment;
import java.util.Optional;

public interface CommentRepositoryPort {

    Long save(Comment comment);

    Optional<Comment> findById(Long id);

    void update(Comment comment);

    void delete(Comment comment);
}

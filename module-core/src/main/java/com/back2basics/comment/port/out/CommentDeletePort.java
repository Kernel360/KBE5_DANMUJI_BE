package com.back2basics.comment.port.out;

import com.back2basics.comment.model.Comment;

public interface CommentDeletePort {

    void delete(Comment comment);
}

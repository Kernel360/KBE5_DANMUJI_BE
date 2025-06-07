package com.back2basics.comment.port.out;

import com.back2basics.comment.model.Comment;

public interface CommentUpdatePort {

    void update(Comment comment, String userIp);
}

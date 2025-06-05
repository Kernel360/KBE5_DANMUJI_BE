package com.back2basics.comment.port.in;

import com.back2basics.comment.service.result.CommentReadResult;
import java.util.List;

public interface CommentReadUseCase {

    List<CommentReadResult> getCommentsByPostId(Long userId, Long postId);
}

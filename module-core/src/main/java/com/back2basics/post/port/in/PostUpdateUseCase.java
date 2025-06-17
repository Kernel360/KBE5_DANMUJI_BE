package com.back2basics.post.port.in;

import com.back2basics.post.port.in.command.PostUpdateCommand;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PostUpdateUseCase {

    void updatePost(Long userId, String userIp, Long postId, PostUpdateCommand command,
        List<MultipartFile> files) throws IOException;
}
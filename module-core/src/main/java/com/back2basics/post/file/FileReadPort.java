package com.back2basics.post.file;

import java.util.List;

public interface FileReadPort {

    List<File> getFilesByPostId(Long postId);

    File getFileById(Long fileId);
}
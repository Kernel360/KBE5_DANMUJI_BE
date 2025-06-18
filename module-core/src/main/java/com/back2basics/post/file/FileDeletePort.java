package com.back2basics.post.file;

import java.util.List;

public interface FileDeletePort {

    void deleteAllByPostId(Long postId);

    void deleteFromStorage(List<File> fileUrls);
    
}
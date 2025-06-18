package com.back2basics.post.file;

import java.util.List;

public interface FileSavePort {

    void saveAll(List<File> files, Long postId);
}
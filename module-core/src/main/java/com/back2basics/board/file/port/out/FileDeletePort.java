package com.back2basics.board.file.port.out;

import com.back2basics.board.file.model.File;
import java.util.List;

public interface FileDeletePort {

    void deleteAllByPostId(Long postId);

    void deleteFiles(List<File> files);

    void deleteFromStorage(List<File> fileUrls);

}
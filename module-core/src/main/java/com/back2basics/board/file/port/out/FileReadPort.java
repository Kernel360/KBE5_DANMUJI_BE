package com.back2basics.board.file.port.out;

import com.back2basics.board.file.model.File;
import java.util.List;

public interface FileReadPort {

    List<File> getFilesByPostId(Long postId);

    File getFileById(Long fileId);
}
package com.back2basics.board.file.port.out;

import com.back2basics.board.file.model.File;
import java.util.List;

public interface FileSavePort {

    void saveAll(List<File> files, Long postId);
}
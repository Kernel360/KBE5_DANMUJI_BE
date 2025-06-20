package com.back2basics.board.file.port.in;

import com.back2basics.board.file.service.FileDownloadResult;
import java.io.IOException;

public interface FileDownloadUseCase {

    FileDownloadResult downloadFile(Long userId, Long postId, Long fileId) throws IOException;
}

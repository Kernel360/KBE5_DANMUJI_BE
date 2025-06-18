package com.back2basics.post.file;

import static com.back2basics.infra.exception.file.FileErrorCode.FILE_DOWNLOAD_DENIED;

import com.back2basics.infra.exception.file.FileException;
import com.back2basics.infra.s3.S3Util;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostReadPort;
import com.back2basics.project.port.out.ProjectMemberQueryPort;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileDownloadService implements FileDownloadUseCase {

    private final FileReadPort fileReadPort;
    private final PostReadPort postReadPort;
    private final ProjectMemberQueryPort projectMemberQueryPort;
    private final S3Util s3Util;

    @Override
    public FileDownloadResult downloadFile(Long userId, Long postId, Long fileId)
        throws IOException {

        downloadPermissionValidator(userId, postId);
        File file = fileReadPort.getFileById(fileId);
        String key = file.getFileUrl()
            .replace("https://pub-2823e9b2fef94861b9d9cc553a84821b.r2.dev/", "");
        byte[] bytes = s3Util.downloadFile(key);

        return FileDownloadResult.toResult(file, bytes);
    }

    private void downloadPermissionValidator(Long userId, Long postId) {
        Post post = postReadPort.findById(postId);

        Long projectId = post.getProjectId();

        List<Long> memberIds = projectMemberQueryPort.getUserIdsByProject(projectId);
        boolean isProjectMember = memberIds.contains(userId);

        if (!isProjectMember) {
            throw new FileException(FILE_DOWNLOAD_DENIED);
        }
    }
}

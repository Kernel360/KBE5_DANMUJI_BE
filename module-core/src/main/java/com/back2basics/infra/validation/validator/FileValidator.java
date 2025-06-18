package com.back2basics.infra.validation.validator;

import static com.back2basics.infra.exception.file.FileErrorCode.FILE_DOWNLOAD_DENIED;
import static com.back2basics.infra.exception.post.PostErrorCode.POST_NOT_FOUND;

import com.back2basics.infra.exception.file.FileException;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostReadPort;
import com.back2basics.project.port.out.ProjectMemberQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileValidator {

    private final PostReadPort postReadPort;
    private final ProjectMemberQueryPort projectMemberQueryPort;

    public void validateDownloadPermission(Long userId, Long postId) {
        Post post = postReadPort.findById(postId)
            .orElseThrow(() -> new PostException(POST_NOT_FOUND));

        Long projectId = post.getProjectId();

        List<Long> memberIds = projectMemberQueryPort.getUserIdsByProject(projectId);
        boolean isProjectMember = memberIds.contains(userId);

        if (!isProjectMember) {
            throw new FileException(FILE_DOWNLOAD_DENIED);
        }
    }

}

package com.back2basics.infra.validator;

import static com.back2basics.infra.exception.file.FileErrorCode.FILE_DOWNLOAD_DENIED;

import com.back2basics.board.post.model.Post;
import com.back2basics.infra.exception.file.FileException;
import com.back2basics.project.port.out.ProjectMemberQueryPort;
import com.back2basics.user.model.Role;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileValidator {

    private final ProjectMemberQueryPort projectMemberQueryPort;
    private final UserQueryPort userQueryPort;
    private final PostValidator postValidator;

    public void validateDownloadPermission(Long userId, Long postId) {
            Post post = postValidator.findPost(postId);
            User user = userQueryPort.findById(userId);

            if(user.getRole() == Role.ADMIN) {
                return;
            }

            validateProjectMember(userId, post.getProjectId());

    }

    private void validateProjectMember(Long userId, Long projectId) {
        List<Long> memberIds = projectMemberQueryPort.getUserIdsByProject(projectId);
        boolean isProjectMember = memberIds.contains(userId);

        if (!isProjectMember) {
            throw new FileException(FILE_DOWNLOAD_DENIED);
        }
    }

}

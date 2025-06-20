package com.back2basics.infra.validation.validator;

import static com.back2basics.infra.exception.file.FileErrorCode.FILE_DOWNLOAD_DENIED;
import static com.back2basics.infra.exception.post.PostErrorCode.POST_NOT_FOUND;

import com.back2basics.infra.exception.file.FileException;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostReadPort;
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

    private final PostReadPort postReadPort;
    private final ProjectMemberQueryPort projectMemberQueryPort;
    private final UserQueryPort userQueryPort;

    public void validateDownloadPermission(Long userId, Long postId) {

        // todo : 이것도 SRP에 따라서 분리해야하는지???
        User user = userQueryPort.findById(userId);
        if (user == null) {
            throw new FileException(FILE_DOWNLOAD_DENIED); // USER_NOT_FOUND가 아닌 이유: 파일 접근 실패 의미에 집중
        }

        if (user.getRole() == Role.ADMIN) {
            log.debug("======================== 관리자면 무조건 허용하도록 리턴해버리기");
            return; // 관리자면 무조건 허용하도록 리턴해버림
        }

        log.debug("========================  관리자인데도 여기까지 내려왔는지 디버깅, 일반 회원은 이 로그가 떠야됨");
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

package com.back2basics.project.port.in;

public interface RestoreProjectUseCase {
    void restoreProject(Long requesterId, Long projectId);
}

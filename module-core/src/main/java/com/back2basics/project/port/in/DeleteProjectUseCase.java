package com.back2basics.project.port.in;

public interface DeleteProjectUseCase {

    void deleteProject(Long projectId, Long loggedInUserId);
}

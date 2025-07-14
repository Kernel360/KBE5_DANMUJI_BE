package com.back2basics.adapter.persistence.project.adapter;

import static com.back2basics.adapter.persistence.project.QProjectEntity.projectEntity;
import static com.back2basics.infra.exception.project.ProjectErrorCode.PROJECT_ALREADY_RESTORED;
import static com.back2basics.infra.exception.project.ProjectErrorCode.PROJECT_NOT_FOUND;

import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.project.ProjectEntityRepository;
import com.back2basics.adapter.persistence.project.ProjectMapper;
import com.back2basics.infra.exception.project.ProjectException;
import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.model.StatusCountProjection;
import com.back2basics.project.port.out.ReadProjectPort;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadProjectAdapter implements ReadProjectPort {

    private final JPAQueryFactory jpaQueryFactory;
    private final ProjectEntityRepository projectEntityRepository;
    private final ProjectMapper projectMapper;

    @Override
    public Project findById(Long id) {
        ProjectEntity entity = projectEntityRepository.findByIdAndIsDeletedFalse(id);
        if (entity == null) {
            throw new ProjectException(PROJECT_NOT_FOUND);
        }
        return projectMapper.toDomain(entity);
    }

    @Override
    public Page<Project> findAll(Pageable pageable) {
        return projectEntityRepository.findAllByIsDeletedFalse(pageable)
            .map(projectMapper::toDomain);
    }

    @Override
    public Page<Project> findAllByUserId(Long userId, Pageable pageable) {
        Page<ProjectEntity> projectEntities = projectEntityRepository.findAllByUserId(userId,
            pageable);
        return projectEntities.map(projectMapper::toDomain);
    }

    @Override
    public Page<Project> findAllDeletedProjects(Pageable pageable) {
        return projectEntityRepository.findAllByIsDeletedTrue(pageable)
            .map(projectMapper::toDomain);
    }

    @Override
    public List<Project> getRecentProjects() {
        return projectEntityRepository.findTop5ByDeletedAtIsNullOrderByCreatedAtDesc()
            .stream().map(projectMapper::toDomain).toList();
    }

    @Override
    public List<Project> getAllProjects() {
        return projectEntityRepository.findAllByIsDeletedFalse().stream()
            .map(projectMapper::toDomain).toList();
    }

    @Override
    public List<Project> findByStatusAndUserId(Long userId, ProjectStatus status) {
        return projectEntityRepository.findProjectsByUserIdAndStatus(userId, status)
            .stream().map(projectMapper::toDomain).toList();
    }

    @Override
    public List<StatusCountProjection> countProjectsByProjectStatus() {
        return projectEntityRepository.countProjectsByStatus();
    }

    @Override
    public List<Project> findByStatus(ProjectStatus status) {
        return projectEntityRepository.findAllByProjectStatusAndDeletedAtIsNullOrderByIdDesc(status)
            .stream().map(projectMapper::toDomain).toList();
    }

    //todo: projectId가 없어서 null인 경우에도 PROJECT_ALREADY_RESTORED가 호출 (맞지 않는 에러)
    @Override
    public Optional<Project> findDeletedProjectById(Long projectId) {
        ProjectEntity entity = jpaQueryFactory
            .selectFrom(projectEntity)
            .where(
                projectEntity.id.eq(projectId),
                projectEntity.isDeleted.eq(true)
            )
            .fetchOne();

        if (entity == null) {
            throw new ProjectException(PROJECT_ALREADY_RESTORED);
        }

        return Optional.of(projectMapper.toDomain(entity));
    }

    @Override
    public List<Project> findUpdatableProjects() {
        return projectEntityRepository.findUpdatableProjects()
            .stream()
            .map(projectMapper::toDomain)
            .collect(Collectors.toList());
    }
}

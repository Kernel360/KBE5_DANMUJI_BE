package com.back2basics.adapter.persistence.project.adapter;

import static com.back2basics.adapter.persistence.assignment.QAssignmentEntity.assignmentEntity;
import static com.back2basics.adapter.persistence.company.QCompanyEntity.companyEntity;
import static com.back2basics.adapter.persistence.project.QProjectEntity.projectEntity;

import com.back2basics.adapter.persistence.company.QCompanyEntity;
import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.project.ProjectMapper;
import com.back2basics.adapter.persistence.project.QProjectEntity;
import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.port.in.command.ProjectSearchCommand;
import com.back2basics.project.port.out.SearchProjectPort;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SearchProjectAdapter implements SearchProjectPort {

    private final JPAQueryFactory jpaQueryFactory;
    private final ProjectMapper projectMapper;

    @Override
    public Page<Project> searchByKeyword(ProjectSearchCommand command, Pageable pageable) {
        List<ProjectEntity> projectEntities = jpaQueryFactory
            .selectDistinct(projectEntity)
            .from(projectEntity)
            .leftJoin(projectEntity.assignments, assignmentEntity)
            .leftJoin(assignmentEntity.company, companyEntity)
            .where(
                projectEntity.isDeleted.eq(false),
                matchesKeyword(command.getKeyword(), command.getCategory()
                ),
                matchesStatus(command.getProjectStatus()),
                matchesPeriod(command.getStartDate(), command.getEndDate())
            )
            .orderBy(sortOrder(command.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        List<Project> projects = projectEntities.stream().map(projectMapper::toDomain).toList();

        Long count = jpaQueryFactory
            .select(projectEntity.countDistinct())
            .from(projectEntity)
            .leftJoin(projectEntity.assignments, assignmentEntity)
            .leftJoin(assignmentEntity.company, companyEntity)
            .where(
                projectEntity.isDeleted.eq(false),
                matchesKeyword(command.getKeyword(), command.getCategory()
                ),
                matchesStatus(command.getProjectStatus()),
                matchesPeriod(command.getStartDate(), command.getEndDate())
            )
            .fetchOne();
        return PageableExecutionUtils.getPage(projects, pageable, () -> count != null ? count : 0L);
    }

    @Override
    public Page<Project> searchByKeywordAndUserId(Long userId, ProjectSearchCommand command,
        Pageable pageable) {
        List<ProjectEntity> projectEntities = jpaQueryFactory
            .selectDistinct(projectEntity)
            .from(projectEntity)
            .leftJoin(projectEntity.assignments, assignmentEntity)
            .leftJoin(assignmentEntity.company, companyEntity)
            .where(
                assignmentEntity.user.id.eq(userId), // 할당
                projectEntity.isDeleted.eq(false),
                matchesKeyword(command.getKeyword(), command.getCategory()
                ),
                matchesStatus(command.getProjectStatus()),
                matchesPeriod(command.getStartDate(), command.getEndDate())
            )
            .orderBy(sortOrder(command.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        List<Project> projects = projectEntities.stream().map(projectMapper::toDomain).toList();

        Long count = jpaQueryFactory
            .select(projectEntity.countDistinct())
            .from(projectEntity)
            .leftJoin(projectEntity.assignments, assignmentEntity)
            .leftJoin(assignmentEntity.company, companyEntity)
            .where(
                assignmentEntity.user.id.eq(userId),
                projectEntity.isDeleted.eq(false),
                matchesKeyword(command.getKeyword(), command.getCategory()
                ),
                matchesStatus(command.getProjectStatus()),
                matchesPeriod(command.getStartDate(), command.getEndDate())
            )
            .fetchOne();
        return PageableExecutionUtils.getPage(projects, pageable, () -> count != null ? count : 0L);
    }

    // 조건 메서드
    private BooleanExpression matchesKeyword(String keyword, String category) {
        if (keyword == null || keyword.isBlank() || category == null) {
            return null;
        }

        return switch (category) {
            case "all" -> QProjectEntity.projectEntity.name.contains(keyword)
                .or(QCompanyEntity.companyEntity.name.contains(keyword));
            case "projectName" -> QProjectEntity.projectEntity.name.contains(keyword);
            case "companyName" -> QCompanyEntity.companyEntity.name.contains(keyword);
            default -> null;
        };
    }

    private BooleanExpression matchesStatus(ProjectStatus projectStatus) {
        return (projectStatus == null) ? null
            : QProjectEntity.projectEntity.projectStatus.eq(projectStatus);
    }

    private BooleanExpression matchesPeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return QProjectEntity.projectEntity.startDate.goe(startDate)
                .and(QProjectEntity.projectEntity.endDate.loe(endDate));
        }
        if (startDate != null) {
            return QProjectEntity.projectEntity.startDate.goe(startDate);
        }
        if (endDate != null) {
            return QProjectEntity.projectEntity.endDate.loe(endDate);
        }
        return null;
    }

    private OrderSpecifier<?> sortOrder(String sort) {
        if ("oldest".equals(sort)) {
            return QProjectEntity.projectEntity.createdAt.asc();
        } else if ("latest".equals(sort)) {
            return QProjectEntity.projectEntity.createdAt.desc();
        }
        return null;
    }
}

package com.back2basics.adapter.persistence.assignment.adapter;

import static com.back2basics.infra.exception.company.CompanyErrorCode.COMPANY_NOT_FOUND;
import static com.back2basics.infra.exception.project.ProjectErrorCode.PROJECT_NOT_FOUND;
import static com.back2basics.infra.exception.user.UserErrorCode.USER_NOT_FOUND;

import com.back2basics.adapter.persistence.assignment.AssignmentEntity;
import com.back2basics.adapter.persistence.company.CompanyEntity;
import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.project.ProjectEntityRepository;
import com.back2basics.adapter.persistence.assignment.AssignmentEntityRepository;
import com.back2basics.adapter.persistence.assignment.AssignmentMapper;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.assignment.model.Assignment;
import com.back2basics.infra.exception.company.CompanyException;
import com.back2basics.infra.exception.project.ProjectException;
import com.back2basics.infra.exception.user.UserException;
import com.back2basics.project.port.out.SaveProjectUserPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveAssignmentAdapter implements SaveProjectUserPort {

    private final AssignmentEntityRepository projectUserEntityRepository;
    private final ProjectEntityRepository projectEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final CompanyEntityRepository companyEntityRepository;
    private final AssignmentMapper mapper;

    @Override
    public void save(Assignment assignment) {
        ProjectEntity project = projectEntityRepository.findById(assignment.getProject().getId())
            .orElseThrow(() -> new ProjectException(PROJECT_NOT_FOUND));

        UserEntity user = userEntityRepository.findById(assignment.getUser().getId())
            .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        CompanyEntity company = companyEntityRepository.findById(assignment.getUser().getCompanyId())
            .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));

        AssignmentEntity entity = mapper.toEntity(assignment, project, user, company);
        projectUserEntityRepository.save(entity);
    }

    @Override
    public void saveAll(List<Assignment> assignments) {
        List<AssignmentEntity> entities = assignments.stream()
            .map(assignment -> {
                ProjectEntity project = projectEntityRepository.findById(assignment.getProject().getId())
                    .orElseThrow(() -> new ProjectException(PROJECT_NOT_FOUND));

                UserEntity user = userEntityRepository.findById(assignment.getUser().getId())
                    .orElseThrow(() -> new UserException(USER_NOT_FOUND));

                CompanyEntity company = companyEntityRepository.findById(assignment.getUser().getCompanyId())
                    .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));

                return mapper.toEntity(assignment, project, user, company);
            })
            .toList();

        projectUserEntityRepository.saveAll(entities);
    }
}

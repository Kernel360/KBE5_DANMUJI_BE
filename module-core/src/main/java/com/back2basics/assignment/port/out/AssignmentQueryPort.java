package com.back2basics.assignment.port.out;

import com.back2basics.assignment.model.Assignment;
import java.util.List;

public interface AssignmentQueryPort {

    List<Assignment> findAllByProjectId(Long projectId);

    // 할당된 회사 삭제를 위한 assignment 리스트 조회
    List<Assignment> findAllByProjectIdAndCompanyId(Long projectId, Long companyId);
}

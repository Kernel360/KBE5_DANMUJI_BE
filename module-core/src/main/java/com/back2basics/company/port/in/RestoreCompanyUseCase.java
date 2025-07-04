package com.back2basics.company.port.in;

public interface RestoreCompanyUseCase {
    void restoreCompany(Long requesterId, Long companyId);
}

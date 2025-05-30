package com.back2basics.company.service;

import com.back2basics.company.port.in.DeleteCompanyUseCase;
import com.back2basics.company.port.out.CompanyRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements DeleteCompanyUseCase {

    private final CompanyRepositoryPort companyRepositoryPort;


    @Override
    public void deleteCompany(Long id) {
        companyRepositoryPort.deleteById(id);
    }
}

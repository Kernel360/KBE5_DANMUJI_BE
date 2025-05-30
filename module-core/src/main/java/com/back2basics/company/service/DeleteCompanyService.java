package com.back2basics.company.service;

import com.back2basics.company.port.in.DeleteCompanyUseCase;
import com.back2basics.company.port.out.DeleteCompanyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCompanyService implements DeleteCompanyUseCase {

    private final DeleteCompanyPort deleteCompanyPort;


    @Override
    public void deleteCompany(Long id) {
        deleteCompanyPort.deleteById(id);
    }

}

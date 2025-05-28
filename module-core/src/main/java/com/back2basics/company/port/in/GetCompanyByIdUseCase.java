package com.back2basics.company.port.in;

import com.back2basics.service.company.dto.CompanyResponseDto;

public interface GetCompanyByIdUseCase {

    CompanyResponseDto getCompany(Long id);

}

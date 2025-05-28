package com.back2basics.company.port.in;

import com.back2basics.service.company.dto.CompanyResponseDto;
import java.util.List;

public interface GetAllCompaniesUseCase {

    List<CompanyResponseDto> getAllCompanies();

}

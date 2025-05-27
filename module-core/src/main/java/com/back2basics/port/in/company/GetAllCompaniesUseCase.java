package com.back2basics.port.in.company;

import com.back2basics.service.company.dto.CompanyResponseDto;
import java.util.List;

public interface GetAllCompaniesUseCase {

    List<CompanyResponseDto> getAllCompanies();

}

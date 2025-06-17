package com.back2basics.infra.validation.validator;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.in.command.CreateCompanyCommand;
import com.back2basics.company.port.out.CreateCompanyPort;
import com.back2basics.company.port.out.ReadCompanyPort;
import com.back2basics.global.response.error.ErrorResponse.FieldError;
import com.back2basics.infra.exception.company.CompanyErrorCode;
import com.back2basics.infra.exception.company.CompanyException;
import com.back2basics.infra.exception.company.DuplicateCompanyException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyValidator {

    private final ReadCompanyPort readCompanyPort;
    private final CreateCompanyPort createCompanyPort;

    public Company findCompany(Long id) {
        return readCompanyPort.findById(id)
            .orElseThrow(() -> new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND));
    }

    public void validateCompanyExists(Long id) {
        if (readCompanyPort.findById(id).isEmpty()) {
            throw new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND);
        }
    }

    public void validateDuplicate(CreateCompanyCommand command) {
        List<FieldError> errors = new ArrayList<>();

        if (createCompanyPort.existsByName(command.getName())) {
            errors.add(new FieldError("name", command.getName(), "[COMP002]이미 존재하는 회사명입니다"));
        }
        if (createCompanyPort.existsByBizNo(command.getBizNo())) {
            errors.add(
                new FieldError("bizNo", command.getBizNo().toString(),
                    "[COMP002]이미 존재하는 사업자등록번호입니다"));
        }
        if (createCompanyPort.existsByAddress(command.getAddress())) {
            errors.add(new FieldError("address", command.getAddress(), "[COMP002]이미 존재하는 주소입니다"));
        }

        if (!errors.isEmpty()) {
            throw new DuplicateCompanyException(errors);
        }
    }

}

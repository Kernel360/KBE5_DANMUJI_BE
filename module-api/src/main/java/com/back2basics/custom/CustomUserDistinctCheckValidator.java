package com.back2basics.custom;

import com.back2basics.domain.project.dto.request.UserDistinctCheckDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class CustomUserDistinctCheckValidator implements ConstraintValidator<CustomUserDistinctCheck, UserDistinctCheckDto> {

    @Override
    public boolean isValid(UserDistinctCheckDto dto, ConstraintValidatorContext context) {
        List<Long> devUserList = dto.devUserId();
        List<Long> clientUserList = dto.clientUserId();
        List<Long> devManagerList = dto.devManagerId();
        List<Long> clientManagerList = dto.clientManagerId();

        boolean userDistinctCheck = devUserList.stream().noneMatch(clientUserList::contains);
        boolean managerDistinctCheck = devManagerList.stream()
            .noneMatch(clientManagerList::contains);

        return userDistinctCheck && managerDistinctCheck;
    }
}

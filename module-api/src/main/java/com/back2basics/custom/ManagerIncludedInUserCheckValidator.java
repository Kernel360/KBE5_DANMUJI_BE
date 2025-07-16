package com.back2basics.custom;

import com.back2basics.domain.project.dto.request.UserDistinctCheckDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;

public class ManagerIncludedInUserCheckValidator implements ConstraintValidator<ManagerIncludedInUserCheck, UserDistinctCheckDto> {

    // false 면 에러
    @Override
    public boolean isValid(UserDistinctCheckDto dto, ConstraintValidatorContext context) {
        List<Long> devUserList = dto.devUserId();
        List<Long> clientUserList = dto.clientUserId();
        List<Long> devManagerList = dto.devManagerId();
        List<Long> clientManagerList = dto.clientManagerId();

        if (devManagerList == null && clientManagerList == null) {
            return true;
        }

        boolean isDevInclude = new HashSet<>(devUserList).containsAll(devManagerList);

        boolean isClientInclude = new HashSet<>(clientUserList).containsAll(clientManagerList);

        return isDevInclude && isClientInclude;
    }
}

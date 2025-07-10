package com.back2basics.user.service.result;

public record UserPositionResult(String position) {

    public static UserPositionResult from(String position) {
        return new UserPositionResult(position);
    }

}

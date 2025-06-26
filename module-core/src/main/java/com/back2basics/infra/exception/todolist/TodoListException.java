package com.back2basics.infra.exception.todolist;

import com.back2basics.global.response.error.CustomException;

public class TodoListException extends CustomException {

    public TodoListException(TodoListErrorCode errorCode) {
        super(errorCode);
    }
}

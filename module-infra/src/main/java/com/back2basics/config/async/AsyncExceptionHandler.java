package com.back2basics.config.async;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component // 비동기 동작 메소드에서 발생하는 예외 잡아줄 핸들러
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("비동기 작업 중 예외 발생: {}.{}()",
            method.getDeclaringClass().getSimpleName(),
            method.getName(), ex);

            for (int i = 0; i < params.length; i++) {
                log.error("    ㄴ 파라미터[{}]: {}", i, params[i]);
            }
        }
}


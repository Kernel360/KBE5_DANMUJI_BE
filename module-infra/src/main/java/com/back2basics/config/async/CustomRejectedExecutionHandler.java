package com.back2basics.config.async;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component // 최대 쓰레드 풀 초과 시 작업 거부 예외 처리
public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.error("비동기 작업 거부 - 쓰레드풀 초과 상태");
        log.error("    ⤷ 현재 poolSize: {}", executor.getPoolSize());
        log.error("    ⤷ 최대 poolSize: {}", executor.getMaximumPoolSize());
        log.error("    ⤷ 대기열 크기: {}", executor.getQueue().size());
    }
}


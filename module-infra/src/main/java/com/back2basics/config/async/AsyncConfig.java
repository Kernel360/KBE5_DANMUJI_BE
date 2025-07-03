package com.back2basics.config.async;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 요청 없을 때도 유지시킬 쓰레드 수
        executor.setMaxPoolSize(10); // 최대 동시 쓰레드 수 (큐 꽉차면 그때 맥스 사이즈만큼 생성해서 처리)
        executor.setQueueCapacity(100); // 큐에 넣을 수 있는 작업 수
        executor.setThreadNamePrefix("async-history"); // 쓰레드 이름
        executor.initialize();
        return executor;
    }
}

package com.back2basics;

import java.time.LocalDateTime;

public interface SoftDeletableCleaner {

    void clean(LocalDateTime threshold);

    String getName(); // 로깅용
}
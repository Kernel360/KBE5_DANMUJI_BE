package com.back2basics.checklist.service.result;

import java.time.LocalDateTime;

public record CheckListResult(Long id, String content, Long postId, boolean isChecked,
                              LocalDateTime checkedAt) {

}

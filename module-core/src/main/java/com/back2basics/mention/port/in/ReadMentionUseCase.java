package com.back2basics.mention.port.in;

import com.back2basics.mention.service.result.MyMentionListResult;
import java.util.List;

public interface ReadMentionUseCase {
    List<MyMentionListResult> getMyMentions(Long userId);
}

package com.back2basics.mention.port.out;

import com.back2basics.mention.model.Mention;
import java.util.List;

public interface ReadMentionPort {
    List<Mention> getMentionsByUserId(Long userId);
}

package com.back2basics.mention.service;

import com.back2basics.mention.model.Mention;
import com.back2basics.mention.port.in.ReadMentionUseCase;
import com.back2basics.mention.port.out.ReadMentionPort;
import com.back2basics.mention.service.result.MyMentionListResult;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentionReadService implements ReadMentionUseCase {

    private final ReadMentionPort readMentionPort;

    @Override
    public List<MyMentionListResult> getMyMentions(Long userId) {
        List<Mention> mentions = readMentionPort.getMentionsByUserId(userId);

        return mentions.stream()
            .map(MyMentionListResult::toResult)
            .collect(Collectors.toList());
    }
}

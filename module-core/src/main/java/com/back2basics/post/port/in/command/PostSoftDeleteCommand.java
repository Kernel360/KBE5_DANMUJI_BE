package com.back2basics.post.port.in.command;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostSoftDeleteCommand {

    @NotNull(message = "요청자는 필수입니다.")
    private Long requesterId;
    
}

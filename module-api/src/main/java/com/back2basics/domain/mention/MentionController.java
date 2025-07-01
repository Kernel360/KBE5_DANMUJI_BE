package com.back2basics.domain.mention;


import static com.back2basics.domain.mention.code.MentionResponseCode.MENTION_READ_ALL_SUCCESS;

import com.back2basics.domain.mention.dto.MyMentionListResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.mention.port.in.ReadMentionUseCase;
import com.back2basics.mention.service.result.MyMentionListResult;
import com.back2basics.security.model.CustomUserDetails;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mentions")
@RequiredArgsConstructor
public class MentionController {

    private final ReadMentionUseCase readMentionUseCase;

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<MyMentionListResponse>>> getMyMentions(
        @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        List<MyMentionListResult> resultList = readMentionUseCase.getMyMentions(customUserDetails.getId());
        List<MyMentionListResponse> responseList = resultList.stream()
            .map(MyMentionListResponse::toResponse)
            .collect(Collectors.toList());

        return ApiResponse.success(MENTION_READ_ALL_SUCCESS, responseList);
    }

}

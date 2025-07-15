package com.back2basics.domain.inquiry.dto.request;

import com.back2basics.inquiry.model.InquiryStatus;
import com.back2basics.inquiry.port.in.command.InquirySearchCommand;
import com.back2basics.user.model.Role;
import jakarta.annotation.Nullable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class SearchInquiryRequest {

    @Nullable
    private String title;
    @Nullable
    private String authorName;
    @Nullable
    private String authorUsername;
    @Nullable
    private Role role;
    @Nullable
    private Long authorId;
    @Nullable
    private InquiryStatus status;
    @Nullable
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @Nullable
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    public InquirySearchCommand toCommand() {
        return InquirySearchCommand.builder()
            .title(title)
            .authorName(authorName)
            .authorUsername(authorUsername)
            .role(role)
            .authorId(authorId)
            .status(status)
            .startDate(startDate)
            .endDate(endDate)
            .build();
    }

}

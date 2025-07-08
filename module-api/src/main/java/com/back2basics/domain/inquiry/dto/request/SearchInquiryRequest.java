package com.back2basics.domain.inquiry.dto.request;

import com.back2basics.inquiry.model.InquiryStatus;
import com.back2basics.inquiry.port.in.command.InquirySearchCommand;
import jakarta.annotation.Nullable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString
public class SearchInquiryRequest {

    @Nullable
    private String title;
    @Nullable
    private String authorName;
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
            .authorId(authorId)
            .status(status)
            .startDate(startDate)
            .endDate(endDate)
            .build();
    }

}

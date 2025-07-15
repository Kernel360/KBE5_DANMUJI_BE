package com.back2basics.inquiry.port.in.command;

import com.back2basics.inquiry.model.InquiryStatus;
import com.back2basics.user.model.Role;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Builder(toBuilder = true)
public class InquirySearchCommand {

    private String title;
    private String authorUsername;
    private Role role;
    private String authorName;
    private Long authorId;
    private InquiryStatus status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

}

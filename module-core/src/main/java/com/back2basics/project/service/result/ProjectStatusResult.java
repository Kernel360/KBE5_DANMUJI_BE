package com.back2basics.project.service.result;

import java.time.LocalDate;

public record ProjectStatusResult(Long id, String name, String description, LocalDate startDate,
                                  LocalDate endDate, int progress) {

}

package com.razal.caback.report.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record ReportDto(
        Long id,
        String reportname,
        String interval,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime creationTime,
        List<CurrencyDto> currencies
) {}

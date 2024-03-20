package com.razal.caback.report.dto;

import java.util.List;

public record CurrencyReportDto(
        String reportName,
        String interval,
        List<String> symbols
) {}

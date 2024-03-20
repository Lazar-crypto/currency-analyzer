package com.razal.caback.report.dto;

import java.math.BigDecimal;

public record CandleDto(
    Long openTime,
    BigDecimal openPrice,
     BigDecimal highPrice,
    BigDecimal lowPrice,
    BigDecimal closePrice
) {}

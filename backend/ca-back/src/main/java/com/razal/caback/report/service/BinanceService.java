package com.razal.caback.report.service;

import com.razal.caback.report.dto.CandleDto;
import com.razal.caback.report.model.Candle;
import com.razal.caback.report.model.enums.Interval;

import java.util.List;

public interface BinanceService {

    List<CandleDto> fetchCandleData(Interval interval, String symbol, String size);

    List<Candle> fetchCandleData(Interval interval, String symbol, int limit);
}

package com.razal.caback.report.controller;

import com.razal.caback.report.dto.CandleDto;
import com.razal.caback.report.model.enums.Interval;
import com.razal.caback.report.service.BinanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class BinanceController {

    private final BinanceService binanceService;

    @GetMapping("/candles")
    public ResponseEntity<List<CandleDto>> fetchCandleData(
            @RequestParam String interval,
            @RequestParam String symbol,
            @RequestParam String size
    ) {
        return ResponseEntity.ok(
                binanceService.fetchCandleData(Interval.fromString(interval), symbol, size)
        );
    }
}

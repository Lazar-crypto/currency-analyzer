package com.razal.caback.report.controller;

import com.razal.caback.report.dto.CurrencyReportDto;
import com.razal.caback.report.dto.ReportDto;
import com.razal.caback.report.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/report")
    public ResponseEntity<?> createReport(@RequestBody CurrencyReportDto dto) {
        Long reportId = reportService.createReport(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(reportId).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/reports")
    public ResponseEntity<List<ReportDto>> fetchReports(@RequestParam int pageNum) {
        return ResponseEntity.ok(reportService.fetchReports(pageNum));
    }

    @DeleteMapping("/report/{reportId}")
    public ResponseEntity<?> deleteReport(@PathVariable Long reportId) {
        reportService.deleteReport(reportId);
        return ResponseEntity.noContent().build();
    }

}

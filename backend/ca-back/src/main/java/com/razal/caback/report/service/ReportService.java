package com.razal.caback.report.service;

import com.razal.caback.report.dto.CurrencyReportDto;
import com.razal.caback.report.dto.ReportDto;

import java.math.BigDecimal;
import java.util.List;

public interface ReportService {

   Long createReport(CurrencyReportDto dto);

   List<ReportDto> fetchReports(int pageNum);

   void deleteReport(Long id);
}

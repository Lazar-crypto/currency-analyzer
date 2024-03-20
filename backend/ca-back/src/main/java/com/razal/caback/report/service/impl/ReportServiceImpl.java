package com.razal.caback.report.service.impl;

import com.razal.caback.exception.custom.NotFoundException;
import com.razal.caback.report.dto.CurrencyDto;
import com.razal.caback.report.dto.CurrencyReportDto;
import com.razal.caback.report.dto.ReportDto;

import com.razal.caback.report.model.Candle;
import com.razal.caback.report.model.Currency;
import com.razal.caback.report.model.CurrencyReport;
import com.razal.caback.report.model.Report;
import com.razal.caback.report.model.enums.Interval;
import com.razal.caback.report.repository.CurrencyReportRepository;
import com.razal.caback.report.repository.CurrencyRepository;
import com.razal.caback.report.repository.ReportRepository;
import com.razal.caback.report.service.BinanceService;
import com.razal.caback.report.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final BinanceService binanceService;
    private final CurrencyRepository currencyRepository;
    private final CurrencyReportRepository currencyReportRepository;
    private final ReportRepository reportRepository;

    @Transactional
    @Override
    public Long createReport(CurrencyReportDto dto) {
        Interval interval = Interval.fromString(dto.interval());
        Report report = createReport(dto.reportName(), interval);
        List<Currency> currencies = currencyRepository.findBySymbols(dto.symbols());
        List<CurrencyReport> currencyReports = new ArrayList<>();

        for (Currency currency : currencies) {
            List<Candle> candles  = binanceService.fetchCandleData(interval, currency.getSymbol(), 10);
            CurrencyReport currencyReport = createCurrencyReport(candles);
            currencyReport.setReport(report);
            currencyReport.setCurrency(currency);
            currencyReports.add(currencyReport);

            currencyReportRepository.save(currencyReport);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        report.setCurrencyReports(currencyReports);
        report = reportRepository.save(report);
        log.info("Report saved: {}", report.getId());

        return report.getId();
    }
    private CurrencyReport createCurrencyReport(List<Candle> candles) {
        BigDecimal mean = calculateMean(candles);
        BigDecimal deviation = calculateStandardDeviation(candles, mean);
        CurrencyReport currencyReport = new CurrencyReport();
        currencyReport.setMeanPriceChange(mean);
        currencyReport.setStandardDeviation(deviation);
        return currencyReport;
    }

    private BigDecimal calculateMean(List<Candle> candles) {
        BigDecimal sumOfClosingPrices = BigDecimal.ZERO;
        for (Candle candle : candles) {
            sumOfClosingPrices = sumOfClosingPrices.add(candle.getClosePrice());
        }
        return sumOfClosingPrices.divide(BigDecimal.valueOf(candles.size()), MathContext.DECIMAL64);
    }

    public BigDecimal calculateStandardDeviation(List<Candle> candles, BigDecimal mean) {
        BigDecimal sumOfSquaredDifferences = BigDecimal.ZERO;
        for (Candle candle : candles) {
            BigDecimal difference = candle.getClosePrice().subtract(mean);
            sumOfSquaredDifferences = sumOfSquaredDifferences.add(difference.pow(2));
        }
        BigDecimal variance = sumOfSquaredDifferences.divide(BigDecimal.valueOf(candles.size()), MathContext.DECIMAL64);
        BigDecimal standardDeviation = new BigDecimal(Math.sqrt(variance.doubleValue()), MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);

        return standardDeviation;
    }

    private Report createReport(String name, Interval interval) {
        Report report = new Report();
        report.setCreationTime(LocalDateTime.now());
        report.setInterval(interval);
        report.setName(name);
        return  report;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReportDto> fetchReports(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 10);
        Page<ReportDto> page = reportRepository.findAll(pageable).map(
                report -> {
                    List<CurrencyDto> currencyDtos = fetchCurrencyReports(report);
                    return new ReportDto(report.getId() ,report.getName(),
                            report.getInterval().getCode(), report.getCreationTime(), currencyDtos);
                });
        return page.hasContent() ? page.getContent() : new ArrayList<>();
    }

    private List<CurrencyDto> fetchCurrencyReports(Report report) {
        return report.getCurrencyReports().stream().map(
                currencyReport -> {
                    Currency currency = currencyReport.getCurrency();
                    return new CurrencyDto(currency.getName(), currency.getSymbol(), currencyReport.getStandardDeviation());
                }).toList();
    }

    @Transactional
    @Override
    public void deleteReport(Long id) {
      reportRepository.findById(id).ifPresentOrElse(
              reportRepository::delete,
              () -> {throw new NotFoundException("Report with ID" + id + "does not exist!");}
      );
    }
}

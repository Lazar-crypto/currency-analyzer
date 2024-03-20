package com.razal.caback.report.repository;

import com.razal.caback.report.model.CurrencyReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyReportRepository extends JpaRepository<CurrencyReport, Long> {
}

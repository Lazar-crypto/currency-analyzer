package com.razal.caback.report.repository;

import com.razal.caback.report.model.Report;
import com.razal.caback.report.model.enums.Interval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Optional<Report> findByName(String name);
}

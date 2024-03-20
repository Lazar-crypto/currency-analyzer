package com.razal.caback.report.model;

import com.razal.caback.report.model.enums.Interval;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Report {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Interval interval;

    private LocalDateTime creationTime;

    @OneToMany(mappedBy = "report", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<CurrencyReport> currencyReports;
}

package com.razal.caback.report.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Currency {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String symbol;

    @OneToMany(mappedBy = "currency")
    private List<CurrencyReport> currencyReports;
}

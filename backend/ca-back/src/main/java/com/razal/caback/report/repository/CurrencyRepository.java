package com.razal.caback.report.repository;

import com.razal.caback.report.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findBySymbol(String symbol);

    @Query("SELECT c FROM Currency c WHERE c.symbol IN :symbols")
    List<Currency> findBySymbols(@Param("symbols") List<String> symbols);

}

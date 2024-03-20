package com.razal.caback.report.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "binance.candle")
@Data
public class BinanceApiConfig {
    private String urlPrefix;
    private String url;
    private String symbol;
    private String interval;
    private String limit;
}

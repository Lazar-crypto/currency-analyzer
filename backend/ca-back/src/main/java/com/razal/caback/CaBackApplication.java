package com.razal.caback;

import com.razal.caback.report.configuration.BinanceApiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({BinanceApiConfig.class})

public class CaBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaBackApplication.class, args);
    }

}

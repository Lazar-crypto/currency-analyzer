package com.razal.caback.report.service.impl;

import com.razal.caback.exception.custom.BinanceApiException;
import com.razal.caback.report.configuration.BinanceApiConfig;
import com.razal.caback.report.dto.CandleDto;
import com.razal.caback.report.model.Candle;
import com.razal.caback.report.model.enums.Interval;
import com.razal.caback.report.service.BinanceService;
import com.razal.caback.util.EntityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BinanceServiceImpl implements BinanceService {

    private final BinanceApiConfig binanceApiConfig;

    @Override
    @Transactional
    public List<CandleDto> fetchCandleData(Interval interval, String symbol, String size) {
        int limit;
        if (size.equals("sm")) limit = 10;
        else if(size.equals("md")) limit = 100;
        else limit = 500;
        List<Candle> candles = fetchCandleData(interval, symbol, limit);
        List<CandleDto> candleDtos = mapToDto(candles);
        return candleDtos;
    }


    private List<CandleDto> mapToDto(List<Candle> candles){
        return candles.stream().map(candle -> new CandleDto(
                candle.getOpenTime(), candle.getOpenPrice(), candle.getHighPrice(), candle.getLowPrice(), candle.getClosePrice())
        ).toList();
    }

    @Override
    public List<Candle> fetchCandleData(Interval interval, String symbol, int limit) {
        log.info("Fetching candles from BinanceAPI for {} within the interval of {}", symbol, interval.getCode());
        RestTemplate restTemplate = new RestTemplate();
        URI url = UriComponentsBuilder.fromHttpUrl(binanceApiConfig.getUrlPrefix() + binanceApiConfig.getUrl())
                .queryParam(binanceApiConfig.getSymbol(), symbol)
                .queryParam(binanceApiConfig.getInterval(), interval.getCode())
                .queryParam(binanceApiConfig.getLimit(), limit)
                .build().toUri();

        log.debug("Url: {}", url);
        ResponseEntity<List<List<Object>>> exchange = restTemplate.exchange(new RequestEntity<>(HttpMethod.GET, url),
                new ParameterizedTypeReference<>() {});
        List<List<Object>> response = exchange.getBody();

        if (response == null) throw new BinanceApiException("Error while fetching data from Binance API");

        return response.stream().map(
                EntityMapper::mapToCandle
        ).toList();
    }

}

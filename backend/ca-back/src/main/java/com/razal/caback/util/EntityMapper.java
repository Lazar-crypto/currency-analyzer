package com.razal.caback.util;


import com.razal.caback.report.model.Candle;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;


public class EntityMapper {

    public static Candle mapToCandle(List<Object> fields) {
        int i = 0;
        Candle candle = new Candle();
        candle.setOpenTime((Long)fields.get(i++));
        candle.setOpenPrice(new BigDecimal(fields.get(i++).toString()));
        candle.setHighPrice(new BigDecimal(fields.get(i++).toString()));
        candle.setLowPrice(new BigDecimal(fields.get(i++).toString()));
        candle.setClosePrice(new BigDecimal(fields.get(i++).toString()));
        candle.setVolume(new BigDecimal(fields.get(i++).toString()));
        candle.setCloseTime((Long)fields.get(i++));
        candle.setQuoteAssetVolume(new BigDecimal(fields.get(i++).toString()));
        candle.setNumberOfTrades(BigInteger.valueOf(Long.parseLong(fields.get(i++).toString())));
        candle.setTakerBuyBaseAssetVolume(new BigDecimal(fields.get(i++).toString()));
        candle.setTakerBuyQuoteAssetVolume(new BigDecimal(fields.get(i++).toString()));
        candle.setIgnore(new BigDecimal(fields.get(i).toString()));
        return candle;
    }

    public static <T> T mapToEntity(List<Object> fields, Class<T> clazz) {
        T instance;
        try {
            instance = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e); // .
        }
        Field[] classFields = clazz.getDeclaredFields();
        int fieldIndex = 0;
        for (Field field : classFields) {
            field.setAccessible(true);

            try {
                field.set(instance, fields.get(fieldIndex));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e); //Mapping ex ..
            }
            fieldIndex++;
        }

        return instance;
    }
}

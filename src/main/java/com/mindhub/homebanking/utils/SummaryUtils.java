package com.mindhub.homebanking.utils;

import java.time.LocalDateTime;

import static com.mindhub.homebanking.utils.RandomValuesUtils.generateUniqueCode;

public class SummaryUtils {

    public static String summaryCode(LocalDateTime date) {
        return date.getMonthValue() +"-" + generateUniqueCode() + "-" + date.getYear();
    }
}

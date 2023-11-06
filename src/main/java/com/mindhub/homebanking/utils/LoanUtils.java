package com.mindhub.homebanking.utils;

public class LoanUtils {

    public static String generateLoanCode(){
        return RandomValuesUtils.randomNumber(10, 99) + "-" +
                RandomValuesUtils.randomString(4, 4) + "-" +
                RandomValuesUtils.randomNumber(1, 999) + "-"
                + RandomValuesUtils.randomString(4, 4);
    }

}

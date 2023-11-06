package com.mindhub.homebanking.utils;

import static com.mindhub.homebanking.utils.RandomValuesUtils.randomNumber;

public class CardsUtils {

    public static String getRandomCardNumber(){
        return            randomNumber(1000, 9999)
                + " - " + randomNumber(1000, 9999)
                + " - " + randomNumber(1000, 9999)
                + " - " + randomNumber(1000, 9999);
    }

    public static String getRandomCVV(){
        return randomNumber(100, 9999).toString();
    }

}

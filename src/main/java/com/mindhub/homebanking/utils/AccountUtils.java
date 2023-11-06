package com.mindhub.homebanking.utils;

import static com.mindhub.homebanking.utils.RandomValuesUtils.randomString;

public class AccountUtils {

    public static String getRandomCBU(){
        return  "CBU - " + RandomValuesUtils.randomNumber(100000000, 999999999);
    }

    public static String getRandomAccountNumber(String accountType){
        return  accountType + " - " + RandomValuesUtils.randomNumber(100000000, 999999999);
    }

    public static String getRandomAlias(){
        return randomString(5, 6)+"."+randomString(6, 7);
    }

}

package com.mindhub.homebanking.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomValuesUtils {

    public static Integer randomNumber(Integer min, Integer max){
         return ThreadLocalRandom.current().nextInt(min, max);
    }
    public static String randomString(Integer minLength, Integer maxLength){
        return RandomStringUtils.randomAlphabetic(minLength, maxLength);
    }
}

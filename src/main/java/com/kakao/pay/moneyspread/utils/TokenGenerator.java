package com.kakao.pay.moneyspread.utils;

import java.util.Random;

public class TokenGenerator {
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String create(final int length) {
        String token = "";
        for (int i = 0; i < length; i++) {
            token += ALPHABET.charAt(new Random().nextInt(ALPHABET.length()));
        }
        return token;
    }
}

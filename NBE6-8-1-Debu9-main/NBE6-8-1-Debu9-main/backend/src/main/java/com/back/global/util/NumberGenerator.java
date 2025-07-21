package com.back.global.util;

import java.security.SecureRandom;

public class NumberGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();

    // 주어진 자릿수에 맞는 랜덤 숫자 생성 메서드
    public static int generateRandomNumber(int digits) {
        if (digits <= 0 || digits > 9) { // int 범위 내에서 9자리까지만 유효
            throw new IllegalArgumentException("자릿수는 1에서 9 사이여야 합니다.");
        }
        int min = (int) Math.pow(10, digits - 1);
        int max = (int) Math.pow(10, digits) - 1;
        return secureRandom.nextInt(max - min + 1) + min;
    }
}
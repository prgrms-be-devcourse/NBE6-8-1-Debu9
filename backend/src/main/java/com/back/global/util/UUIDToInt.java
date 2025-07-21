package com.back.global.util;

import java.util.UUID;

public class UUIDToInt {
    public static int generateIntFromUUID() {
        UUID uuid = UUID.randomUUID();
        // UUID의 가장 앞부분을 가져와 int로 변환
        return uuid.hashCode(); // 32비트 정수
    }
}
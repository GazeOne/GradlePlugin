package com.example.myapplication.livedataandviewmodel;

import java.util.Random;

class RandomUtil {
    static String getRandomNumber() {
        return generateRandomStr(10);
    }

    static String getChineseName() {
        return generateRandomStr(10);
    }

    static String getRandomPhone() {
        return generateRandomStr(10);
    }

    private static String generateRandomStr(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}

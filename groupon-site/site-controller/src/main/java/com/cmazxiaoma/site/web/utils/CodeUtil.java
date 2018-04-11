package com.cmazxiaoma.site.web.utils;

import java.util.Random;


/**
 * 编码工具类
 */
public class CodeUtil {

    private static final long salt = 1392008843575129l;

    private static final String[] codes = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    public static String generateShortUUCode() {
        Random rand = new Random();

        Long rest = (System.currentTimeMillis() * 1000 + rand.nextInt(1000)) ^ salt;
        StringBuilder result = new StringBuilder(0);
        while (rest != 0) {
            result.append(codes[new Long((rest - (rest / 62) * 62)).intValue()]);
            rest = rest / 62;
        }

        return result.toString();
    }

}
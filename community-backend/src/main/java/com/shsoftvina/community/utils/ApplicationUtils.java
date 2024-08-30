package com.shsoftvina.community.utils;

import java.util.Random;

public class ApplicationUtils {

    public static String generateCodeInt(){
        int codeLength = 6;
        StringBuilder code = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < codeLength; i++) {
            int digit = random.nextInt(10);
            code.append(digit);
        }

        return code.toString();
    }

}

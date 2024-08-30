package com.shsoftvina.community.utils;

import java.util.regex.Pattern;

public class PatternUtils {

    public static boolean isEMail(String email){
        return email != null && Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$").matcher(email).matches();
    }
}

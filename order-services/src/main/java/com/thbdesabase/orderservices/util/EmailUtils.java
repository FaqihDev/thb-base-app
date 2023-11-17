package com.thbdesabase.orderservices.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtils {

    public static String getEmailMessage(String name, String host,String token){
        return "Hello " + name + "\n\nYour new account has been created. Please click the link below to verify your account"+
                getUrlVerification(host,token) + "\n\nThe Support Team";
    }

    private static String getUrlVerification(String host, String token) {
        return host + "/api/users?token=" + token;
    }

    public static boolean isValidFormatEmail(String email) {
        String regex = "^[a-zA-Z0-9_.]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}

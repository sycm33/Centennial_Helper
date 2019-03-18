package com.team2.centennial_helper.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static boolean isValidEmail(String email){

        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }

    public static boolean isValidPw(String password){

        if(password.length() > 6){
            return true;
        }
        else {
            return false;
        }
    }

}

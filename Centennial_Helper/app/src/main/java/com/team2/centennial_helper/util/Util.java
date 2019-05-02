package com.team2.centennial_helper.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {


    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final static String MyPREFERENCES = "CENTENNIAL_HELPER";

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

    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(Util.MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public static void setSharedPref(Context context,int value) {
        SharedPreferences.Editor editor = Util.getSharedPreferences(context).edit();
        editor.putInt("user_type", value);
        editor.apply();
    }



}

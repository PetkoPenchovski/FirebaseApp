package com.example.laptop_acer.firebaseapp.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.laptop_acer.firebaseapp.constants.Patterns;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtils {

    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    public ValidatorUtils(){

    }

    private static boolean isEmptyField(String txt) {
        return txt.isEmpty();
    }

    public static boolean isValidateName(String name) {
        if(!isEmptyField(name)){
            Pattern pattern = Pattern.compile(Patterns.NAME_PATTERN, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(name);
            return matcher.find();
        }
        return false;
    }

    public static boolean isValidateEmail(String email) {
        return !isEmptyField(email) && Patterns.EMAIL_ADDRESS_PATTERNS.matcher(email).matches();
    }

    public static boolean isValidateAddress(String address) {
        return !isEmptyField(address);
    }

    public static boolean isValidatePhone(String phone) {
        return !isEmptyField(phone);
    }

    public static boolean isValidateText(String text) {
        return !isEmptyField(text);
    }

    public static boolean isValidateUrl(String url) {
        if(!isEmptyField(url)) {
            Pattern pattern = Pattern.compile(Patterns.URL_PATTERN);
            Matcher matcher = pattern.matcher(url);
            return matcher.find();
        }
        return false;
    }

    public static boolean isValidatePassword(String password) {
        if (!isEmptyField(password)) {
            return password.length() >= MINIMUM_PASSWORD_LENGTH;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean validTime(String format, String value, Locale locale) {
        LocalDateTime ldt = null;
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, locale);

        try {
            ldt = LocalDateTime.parse(value, fomatter);
            String result = ldt.format(fomatter);
            return result.equals(value);
        } catch (DateTimeParseException e) {
            try {
                LocalDate ld = LocalDate.parse(value, fomatter);
                String result = ld.format(fomatter);
                return result.equals(value);
            } catch (DateTimeParseException exp) {
                try {
                    LocalTime lt = LocalTime.parse(value, fomatter);
                    String result = lt.format(fomatter);
                    return result.equals(value);
                } catch (DateTimeParseException e2) {
                    // Debugging purposes
                    //e2.printStackTrace();
                }
            }
        }

        return false;
    }
}

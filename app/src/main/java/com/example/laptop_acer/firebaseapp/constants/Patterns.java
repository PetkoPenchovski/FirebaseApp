package com.example.laptop_acer.firebaseapp.constants;

import java.util.regex.Pattern;

public class Patterns {
    public static final String EMAIL_BODY_PATTERN = "I want to request new beacon, " +
            "here is my details: %n%nName: %s %nEmail: %s %nPhone: %s %nAddress: %s";
    public static final Pattern EMAIL_ADDRESS_PATTERNS = Pattern.compile(
            "[a-zA-Z0-9\\+\\._%\\-]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    public static final String URL_PATTERN = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public static final String NAME_PATTERN = "^[\\p{L} .'-]+$";
}

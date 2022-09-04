package com.tutorial.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ValidatorUtils {
    
    // ^ # start-of-string
    // (?=.*[0-9]) # a digit must occur at least once
    // (?=.*[a-z]) # a lower case letter must occur at least once
    // (?=.*[A-Z]) # an upper case letter must occur at least once
    // (?=.*[_@#$%^&+=]) # a special character must occur at least once
    // (?=\S+$) # no whitespace allowed in the entire string
    // .{8,20} # anything, at least 8 characters and a maximum of 20 characters.
    // $ # end-of-string
    public static final String PASSWORD_REGEXP = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_@#$%^&+=])(?=\\S+$).{8,20}$";
    public static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEXP);
    
    // 6-20 characters long
    // no __ or _. or ._ or .. inside
    // no _ or . at the end
    // no _ or . at the beginning
    public static final String LOGIN_REGEXP = "^(?=[a-zA-Z0-9._]{6,20}$)(?!.*[_.]{2})[^_.].*[^_.]$";
    public static final Pattern LOGIN_PATTERN = Pattern.compile(LOGIN_REGEXP);
    
    // 1) A-Z characters allowed
    // 2) a-z characters allowed
    // 3) 0-9 numbers allowed
    // 4) Additionally email may contain only dot(.), dash(-) and underscore(_)
    // 5) Rest all characters are not allowed
    public static final String EMAIL_REGEXP = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEXP);
    
    public static final String PHONE_REGEXP = "^[0]{1}[0-9]{9,10}$";
    public static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEXP);
    
    public static boolean isValid(final String str, final Pattern pattern) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    
    public static boolean isValidLogin(final String login) {
        return isValid(login, LOGIN_PATTERN);
    }
    
    public static boolean isValidEmail(final String email) {
        return isValid(email, EMAIL_PATTERN);
    }
    
    public static boolean isValidPassword(final String password) {
        return isValid(password, PASSWORD_PATTERN);
    }
    
    public static boolean isValidPhoneNumber(final String phoneNumber) {
        return isValid(phoneNumber, PHONE_PATTERN);
    }
}

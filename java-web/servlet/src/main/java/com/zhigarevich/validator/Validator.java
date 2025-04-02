package com.zhigarevich.validator;

public class Validator {
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9]{3,20}$";
    private static final String PHONE_PATTERN = "^\\+?[0-9\\s\\-\\(\\)]{7,15}$";
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

    public static boolean validateUsername(String username) {
        return username != null && username.matches(USERNAME_PATTERN);
    }

    public static boolean validatePassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean validateEmail(String email) {
        return email != null && email.matches(EMAIL_PATTERN);
    }

    public static boolean validateContactName(String contactName) {
        return contactName != null && contactName.length() >= 2 && contactName.length() <= 50;
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches(PHONE_PATTERN);
    }
}
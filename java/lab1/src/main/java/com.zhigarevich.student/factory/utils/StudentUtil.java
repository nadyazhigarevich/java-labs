package com.zhigarevich.student.factory.utils;

import java.time.LocalDate;
import java.util.Random;

public final class StudentUtil {
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Brown", "Jones"};
    private static final String[] FIRST_NAMES = {"James", "Robert", "John", "Michael", "William"};
    private static final String[] PATRONYMICS = {"Ivanovich", "Petrovich", "Sidorovich", "Nikolayevich", "Dmitrievich"};
    private static final Random RANDOM = new Random();


    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 30;
    private static final int MIN_COURSE = 1;
    private static final int MAX_COURSE = 5;
    private static final int MIN_GROUP = 1;
    private static final int MAX_GROUP = 10;
    private static final int PHONE_NUMBER_LENGTH = 9;

    private StudentUtil() {

    }

    public static String generateRandomLastName() {
        return LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
    }

    public static String generateRandomFirstName() {
        return FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)];
    }

    public static String generateRandomPatronymic() {
        return PATRONYMICS[RANDOM.nextInt(PATRONYMICS.length)];
    }

    public static LocalDate generateRandomDateOfBirth() {
        int year = LocalDate.now().getYear() - (MIN_AGE + RANDOM.nextInt(MAX_AGE - MIN_AGE + 1));
        int month = RANDOM.nextInt(12) + 1;
        int day = RANDOM.nextInt(28) + 1;
        return LocalDate.of(year, month, day);
    }

    public static String generateRandomPhoneNumber() {
        return "+1" + (100_000_000 + RANDOM.nextInt(900_000_000));
    }

    public static int generateRandomCourse() {
        return MIN_COURSE + RANDOM.nextInt(MAX_COURSE - MIN_COURSE + 1);
    }

    public static int generateRandomGroup() {
        return MIN_GROUP + RANDOM.nextInt(MAX_GROUP - MIN_GROUP + 1);
    }
}
package com.zhigarevich.student.utility;

import java.time.LocalDate;
import java.util.Random;

public class StudentUtils {
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Brown", "Jones"};
    private static final String[] FIRST_NAMES = {"James", "Robert", "John", "Michael", "William"};
    private static final String[] PATRONYMICS = {"Ivanovich", "Petrovich", "Sidorovich", "Nikolayevich", "Dmitrievich"};
    private static final Random random = new Random();

    public static String getRandomLastName() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }

    public static String getRandomFirstName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }

    public static String getRandomPatronymic() {
        return PATRONYMICS[random.nextInt(PATRONYMICS.length)];
    }

    public static LocalDate generateRandomDateOfBirth() {
        int year = LocalDate.now().getYear() - (random.nextInt(13) + 18);
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1;

        return LocalDate.of(year, month, day);
    }


    public static String generateRandomPhoneNumber() {
        return "+1" + (random.nextInt(900000000) + 100000000);
    }

    public static int generateRandomCourse() {
        return random.nextInt(5) + 1;
    }

    public static int generateRandomGroup() {
        return random.nextInt(10) + 1;
    }
}
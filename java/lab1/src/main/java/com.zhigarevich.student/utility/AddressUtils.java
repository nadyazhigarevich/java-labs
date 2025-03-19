package com.zhigarevich.student.utility;

import java.util.Random;

public class AddressUtils {
    private static final String[] COUNTRIES = {"USA", "Canada", "UK", "Germany", "France"};
    private static final String[] CITIES = {"New York", "Toronto", "London", "Berlin", "Paris"};
    private static final String[] STREETS = {"1st Ave", "2nd St", "3rd Blvd", "4th Rd", "5th Lane"};
    private static final Random random = new Random();

    public static String getRandomCountry() {
        return COUNTRIES[random.nextInt(COUNTRIES.length)];
    }

    public static String getRandomCity() {
        return CITIES[random.nextInt(CITIES.length)];
    }

    public static String getRandomStreet() {
        return STREETS[random.nextInt(STREETS.length)];
    }
}
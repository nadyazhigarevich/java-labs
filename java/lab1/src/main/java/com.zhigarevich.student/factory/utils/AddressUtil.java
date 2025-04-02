package com.zhigarevich.student.factory.utils;

import java.util.Random;

public final class AddressUtil {
    private static final String[] COUNTRIES = {"USA", "Canada", "UK", "Germany", "France"};
    private static final String[] CITIES = {"New York", "Toronto", "London", "Berlin", "Paris"};
    private static final String[] STREETS = {"1st Ave", "2nd St", "3rd Blvd", "4th Rd", "5th Lane"};
    private static final Random RANDOM = new Random();

    private AddressUtil() {

    }

    public static String generateRandomCountry() {
        return COUNTRIES[RANDOM.nextInt(COUNTRIES.length)];
    }

    public static String generateRandomCity() {
        return CITIES[RANDOM.nextInt(CITIES.length)];
    }

    public static String generateRandomStreet() {
        return STREETS[RANDOM.nextInt(STREETS.length)];
    }
}
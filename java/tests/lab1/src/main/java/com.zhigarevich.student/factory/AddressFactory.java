package com.zhigarevich.student.factory;

import com.zhigarevich.student.entity.Address;

import java.util.Random;

public class AddressFactory {
    private static final String[] COUNTRIES = {"USA", "Canada", "UK", "Germany", "France"};
    private static final String[] CITIES = {"New York", "Toronto", "London", "Berlin", "Paris"};
    private static final String[] STREETS = {"1st Ave", "2nd St", "3rd Blvd", "4th Rd", "5th Lane"};
    private static final Random random = new Random();

    public static Address createRandomAddress() {
        return new Address(
                COUNTRIES[random.nextInt(COUNTRIES.length)],
                CITIES[random.nextInt(CITIES.length)],
                STREETS[random.nextInt(STREETS.length)]
        );
    }
}
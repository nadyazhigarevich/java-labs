package com.zhigarevich.model;

public class PhoneBookEntry {
    private String lastName;
    private String phoneNumber;

    public PhoneBookEntry(String lastName, String phoneNumber) {
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
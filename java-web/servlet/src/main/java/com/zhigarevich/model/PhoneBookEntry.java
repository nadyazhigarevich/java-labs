package com.zhigarevich.model;

import java.util.Objects;

public class PhoneBookEntry {
    private int userId; // Идентификатор пользователя
    private String contactName; // Имя контакта
    private String phoneNumber; // Номер телефона

    public PhoneBookEntry(int userId, String contactName, String phoneNumber) {
        this.userId = userId;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
    }

    public int getUserId() {
        return userId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneBookEntry)) return false;
        PhoneBookEntry that = (PhoneBookEntry) o;
        return userId == that.userId &&
                Objects.equals(contactName, that.contactName) &&
                Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, contactName, phoneNumber);
    }

    @Override
    public String toString() {
        return "PhoneBookEntry{" +
                "userId=" + userId +
                ", contactName='" + contactName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
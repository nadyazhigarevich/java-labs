package com.zhigarevich.phonebook.model;

import java.util.Objects;

public class PhoneBookEntry extends AbstractEntity {
    private int id;
    private int userId; // Идентификатор пользователя
    private String contactName; // Имя контакта
    private String phoneNumber; // Номер телефона

    public  PhoneBookEntry(){}

    public PhoneBookEntry(int id, int userId, String contactName, String phoneNumber) {
        this.id = id;
        this.userId = userId;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
    }

    public PhoneBookEntry(int userId, String contactName, String phoneNumber) {
        this.userId = userId;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PhoneBookEntry that = (PhoneBookEntry) o;
        return id == that.id && userId == that.userId && Objects.equals(contactName, that.contactName) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, contactName, phoneNumber);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PhoneBookEntry{");
        sb.append("id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", contactName='").append(contactName).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
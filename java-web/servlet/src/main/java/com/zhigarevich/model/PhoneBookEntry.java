package com.zhigarevich.model;

import java.util.Objects;

public class PhoneBookEntry {
    private int userId; // Идентификатор пользователя
    private String contactName; // Имя контакта
    private String phoneNumber; // Номер телефона

    // Конструктор
    public PhoneBookEntry(int userId, String contactName, String phoneNumber) {
        this.userId = userId;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
    }

    // Геттеры
    public int getUserId() {
        return userId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Переопределение метода equals для корректного сравнения объектов
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Сравниваем ссылки
        if (!(o instanceof PhoneBookEntry)) return false; // Проверяем тип
        PhoneBookEntry that = (PhoneBookEntry) o; // Приводим к нужному типу
        return userId == that.userId &&
                Objects.equals(contactName, that.contactName) &&
                Objects.equals(phoneNumber, that.phoneNumber);
    }

    // Переопределение метода hashCode для корректного использования в коллекциях
    @Override
    public int hashCode() {
        return Objects.hash(userId, contactName, phoneNumber);
    }

    // Переопределение метода toString для удобного вывода информации об объекте
    @Override
    public String toString() {
        return "PhoneBookEntry{" +
                "userId=" + userId +
                ", contactName='" + contactName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
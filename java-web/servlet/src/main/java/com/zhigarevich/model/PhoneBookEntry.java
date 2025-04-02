package com.zhigarevich.model;

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
                (contactName == null ? that.contactName == null : contactName.equals(that.contactName)) &&
                (phoneNumber == null ? that.phoneNumber == null : phoneNumber.equals(that.phoneNumber));
    }

    // Переопределение метода hashCode для корректного использования в коллекциях
    @Override
    public int hashCode() {
        int result = 17; // Начальное значение для генерации хеш-кода
        result = 31 * result + userId; // Умножаем на 31 и добавляем userId
        result = 31 * result + (contactName != null ? contactName.hashCode() : 0); // Обрабатываем contactName
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0); // Обрабатываем phoneNumber
        return result;
    }

    // Переопределение метода toString для удобного вывода информации об объекте
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PhoneBookEntry{");
        sb.append("userId=").append(userId);
        sb.append(", contactName='").append(contactName).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
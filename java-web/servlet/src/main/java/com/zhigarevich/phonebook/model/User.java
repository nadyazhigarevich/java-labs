package com.zhigarevich.phonebook.model;

public class User extends AbstractEntity {
    private int id; // Идентификатор пользователя
    private String username; // Имя пользователя
    private String password; // Пароль
    private String email; // Электронная почта
    private boolean verified; // Статус верификации

    // Конструктор с параметрами
    public User(int id, String username, String password, String email, boolean verified) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.verified = verified;
    }

    // Конструктор без идентификатора и с верификацией по умолчанию
    public User(String username, String password, String email) {
        this(0, username, password, email, false);
    }

    // Геттеры
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public boolean isVerified() { return verified; }

    // Сеттер для верификации
    public void setVerified(boolean verified) { this.verified = verified; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Сравниваем ссылки
        if (!(o instanceof User)) return false; // Проверяем тип
        User user = (User) o; // Приводим к нужному типу
        return id == user.id &&
                (username == null ? user.username == null : username.equals(user.username)) &&
                (password == null ? user.password == null : password.equals(user.password)) &&
                (email == null ? user.email == null : email.equals(user.email)) &&
                verified == user.verified; // Сравниваем статус верификации
    }

    @Override
    public int hashCode() {
        int result = 17; // Начальное значение для генерации хеш-кода
        result = 31 * result + id; // Умножаем на 31 и добавляем id
        result = 31 * result + (username != null ? username.hashCode() : 0); // Обрабатываем username
        result = 31 * result + (password != null ? password.hashCode() : 0); // Обрабатываем password
        result = 31 * result + (email != null ? email.hashCode() : 0); // Обрабатываем email
        result = 31 * result + (verified ? 1 : 0); // Обрабатываем статус верификации
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", verified=").append(verified);
        sb.append('}');
        return sb.toString();
    }
}
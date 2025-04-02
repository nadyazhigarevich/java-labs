package com.zhigarevich.dao;

import com.zhigarevich.model.PhoneBookEntry;
import com.zhigarevich.db.ConnectionPool;
import com.zhigarevich.validator.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhoneBookDAO {

    // Метод для добавления записи
    public void addEntry(PhoneBookEntry entry, int userId) throws SQLException, IllegalArgumentException {
        if (!Validator.validateContactName(entry.getContactName())) {
            throw new IllegalArgumentException("Contact name must be between 2 and 50 characters");
        }

        if (!Validator.validatePhoneNumber(entry.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        String sql = "INSERT INTO phone_book (user_id, contact_name, phone_number) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, entry.getContactName());
            preparedStatement.setString(3, entry.getPhoneNumber());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Inserting entry failed, no rows affected.");
            }
        }
    }

    // Метод для получения всех записей для конкретного пользователя
    public List<PhoneBookEntry> findAllEntries(int userId) throws SQLException {
        List<PhoneBookEntry> entries = new ArrayList<>();
        String sql = "SELECT contact_name, phone_number FROM phone_book WHERE user_id = ?";

        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String contactName = resultSet.getString("contact_name");
                    String phoneNumber = resultSet.getString("phone_number");
                    entries.add(new PhoneBookEntry(userId, contactName, phoneNumber));
                }
            }
        }
        return entries;
    }
}
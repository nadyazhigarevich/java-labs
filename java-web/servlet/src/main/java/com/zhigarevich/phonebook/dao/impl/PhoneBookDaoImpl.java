package com.zhigarevich.phonebook.dao.impl;

import com.zhigarevich.phonebook.dao.PhoneBookDao;
import com.zhigarevich.phonebook.db.ConnectionPool;
import com.zhigarevich.phonebook.exception.BusinessException;
import com.zhigarevich.phonebook.model.PhoneBookEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PhoneBookDaoImpl implements PhoneBookDao {

    public static final String INSERT_QUERY = "INSERT INTO phone_book (user_id, contact_name, phone_number) VALUES (?, ?, ?)";
    public static final String FIND_ALL_BY_USER_QUERY = "SELECT id, contact_name, phone_number FROM phone_book WHERE user_id = ?";
    public static final String FIND_ALL_QUERY = "SELECT id, contact_name, phone_number, user_id FROM phone_book";
    public static final String FIND_BY_ID = "SELECT id, contact_name, phone_number, user_id FROM phone_book WHERE id = ?";
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM phone_book WHERE id = ?";

    // Метод для добавления записи
    @Override
    public void save(PhoneBookEntry entry) throws BusinessException {
        try (Connection connection = ConnectionPool.getInstance().defineConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setInt(1, entry.getUserId());
            preparedStatement.setString(2, entry.getContactName());
            preparedStatement.setString(3, entry.getPhoneNumber());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new BusinessException("Inserting entry failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new BusinessException(e);
        }
    }

    // Метод для получения всех записей для конкретного пользователя
    @Override
    public List<PhoneBookEntry> findAllByUser(int userId) throws BusinessException {
        List<PhoneBookEntry> entries = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().defineConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_USER_QUERY)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String contactName = resultSet.getString("contact_name");
                    String phoneNumber = resultSet.getString("phone_number");
                    entries.add(new PhoneBookEntry(id, userId, contactName, phoneNumber));
                }
            }
        } catch (SQLException e) {
            throw new BusinessException(e);
        }
        return entries;
    }

    @Override
    public List<PhoneBookEntry> findAll() throws BusinessException {
        List<PhoneBookEntry> entries = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().defineConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String contactName = resultSet.getString("contact_name");
                    String phoneNumber = resultSet.getString("phone_number");
                    int userId = resultSet.getInt("user_id");
                    entries.add(new PhoneBookEntry(id, userId, contactName, phoneNumber));
                }
            }
        } catch (SQLException e) {
            throw new BusinessException(e);
        }
        return entries;
    }

    @Override
    public Optional<PhoneBookEntry> findById(Integer id) throws BusinessException {
        Optional<PhoneBookEntry> phoneBookEntry = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().defineConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String contactName = resultSet.getString("contact_name");
                    String phoneNumber = resultSet.getString("phone_number");
                    int userId = resultSet.getInt("user_id");
                    phoneBookEntry = Optional.of(new PhoneBookEntry(id, userId, contactName, phoneNumber));
                }
            }
        } catch (SQLException e) {
            throw new BusinessException(e);
        }
        return phoneBookEntry;
    }

    @Override
    public void delete(Integer id) throws BusinessException {
        try (Connection connection = ConnectionPool.getInstance().defineConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new BusinessException(e);
        }
    }
}
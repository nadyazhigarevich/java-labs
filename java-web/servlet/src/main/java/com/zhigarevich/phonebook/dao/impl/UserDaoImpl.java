package com.zhigarevich.phonebook.dao.impl;

import com.zhigarevich.phonebook.dao.UserDao;
import com.zhigarevich.phonebook.db.ConnectionPool;
import com.zhigarevich.phonebook.exception.BusinessException;
import com.zhigarevich.phonebook.model.User;
import com.zhigarevich.phonebook.validator.Validator;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserDaoImpl implements UserDao {

    private static final String INSERT_USER_SQL = "INSERT INTO users (username, password, email, verification_token, verified) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_USERNAME_SQL = "SELECT * FROM users WHERE username = ?";
    private static final String FIND_BY_EMAIL_SQL = "SELECT * FROM users WHERE email = ?";
    private static final String FIND_BY_TOKEN_SQL = "SELECT * FROM users WHERE verification_token = ?";
    private static final String UPDATE_VERIFIED_SQL = "UPDATE users SET verified = true, verification_token = NULL WHERE verification_token = ?";

    public int addUser(User user) throws BusinessException {
        if (!Validator.validateUsername(user.getUsername())) {
            throw new IllegalArgumentException("Invalid username format");
        }
        if (!Validator.validatePassword(user.getPassword())) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
        if (!Validator.validateEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        String verificationToken = UUID.randomUUID().toString();

        try (Connection connection = ConnectionPool.getInstance().defineConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, hashedPassword);
            ps.setString(3, user.getEmail());
            ps.setString(4, verificationToken);
            ps.setBoolean(5, false);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    // Здесь должна быть отправка email с verificationToken
                    return userId;
                }
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }

    public boolean verifyUser(String token) throws SQLException {
        try (Connection connection = ConnectionPool.getInstance().defineConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_VERIFIED_SQL)) {
            ps.setString(1, token);
            return ps.executeUpdate() > 0;
        }
    }

    // Метод для получения пользователя по ID
    public User findUserById(int userId) throws SQLException {
        String sql = "SELECT id, username, password, email, verified FROM users WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().defineConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getBoolean("verified")
                    );
                }
                return null; // Пользователь не найден
            }
        } catch (SQLException e) {
            // Логирование ошибки может быть добавлено здесь
            throw new SQLException("Ошибка при поиске пользователя по ID: " + userId, e);
        }
    }

    public User findUsername(String username) throws SQLException {
        final String sql = "SELECT id, username, password, email, verified FROM users WHERE username = ?";

        try (Connection connection = ConnectionPool.getInstance().defineConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getBoolean("verified")
                    );
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to find user by username: " + username, ex);
        }
    }

    @Override
    public List<User> findAll() throws BusinessException {
        return List.of();
    }

    @Override
    public Optional<User> findById(Integer id) throws BusinessException {
        return Optional.empty();
    }

    @Override
    public void save(User entity) throws BusinessException {

    }

    @Override
    public void delete(Integer id) throws BusinessException {

    }
}
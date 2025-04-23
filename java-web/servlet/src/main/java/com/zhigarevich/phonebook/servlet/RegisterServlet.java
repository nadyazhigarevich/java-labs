package com.zhigarevich.phonebook.servlet;

import com.zhigarevich.phonebook.dao.impl.UserDaoImpl;
import com.zhigarevich.phonebook.model.User;
import com.zhigarevich.phonebook.validator.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDaoImpl userDAO = new UserDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String email = request.getParameter("email").trim();

        try {
            if (!Validator.validateUsername(username)) {
                throw new IllegalArgumentException("Имя пользователя должно быть от 3 до 20 символов");
            }
            if (!Validator.validatePassword(password)) {
                throw new IllegalArgumentException("Пароль должен содержать не менее 6 символов");
            }
            if (!Validator.validateEmail(email)) {
                throw new IllegalArgumentException("Введите корректный email");
            }

            User user = new User(username, password, email);
            int userId = userDAO.addUser(user);

            // Здесь должен быть код отправки email с подтверждением
            // String verificationLink = request.getRequestURL() + "?token=" + token;
            // EmailService.sendVerificationEmail(email, verificationLink);

            response.sendRedirect(request.getContextPath() +
                    "/login?message=Регистрация почти завершена. Проверьте email для подтверждения.");
        } catch (IllegalArgumentException | SQLException e) {
            response.sendRedirect(request.getContextPath() + "/register?error=" +
                    URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
        }
    }
}
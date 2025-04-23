package com.zhigarevich.phonebook.servlet;

import com.zhigarevich.phonebook.dao.impl.UserDaoImpl;
import com.zhigarevich.phonebook.model.User;
import com.zhigarevich.phonebook.validator.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet(name = "loginServlet", value = {"/login", "/"})
public class LoginServlet extends HttpServlet {
    private final UserDaoImpl userDaoImpl = new UserDaoImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/pages/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        try {
            if (!Validator.validateUsername(username)) {
                throw new IllegalArgumentException("Некорректный формат имени пользователя");
            }

            if (!Validator.validatePassword(password)) {
                throw new IllegalArgumentException("Пароль должен содержать не менее 6 символов");
            }

            User user = userDaoImpl.findUsername(username);
            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getId());
                response.sendRedirect(request.getContextPath() + "/addEntry");
            } else {
                response.sendRedirect(request.getContextPath() + "/login?error=" +
                        URLEncoder.encode("Неверное имя пользователя или пароль", StandardCharsets.UTF_8));
            }
        } catch (IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/login?error=" +
                    URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
        } catch (SQLException e) {
            response.sendRedirect(request.getContextPath() + "/login?error=" +
                    URLEncoder.encode("Ошибка базы данных. Попробуйте позже.", StandardCharsets.UTF_8));
        }
    }
}
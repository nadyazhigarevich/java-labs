package com.zhigarevich.servlet;

import com.zhigarevich.dao.UserDAO;
import com.zhigarevich.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("pages/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        try {
            User user = userDAO.findUsername(username);
            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getId()); // Сохраняем userId в сессии
                response.sendRedirect("addEntry?userId=" + user.getId()); // Перенаправляем на страницу добавления записи с userId
            } else {
                response.sendRedirect("login?error=Invalid credentials");
            }
        } catch (SQLException e) {
            response.sendRedirect("login?error=Database error");
        }
    }
}
package com.zhigarevich.servlet;

import com.zhigarevich.dao.UserDAO;
import com.zhigarevich.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "registerServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User(username, password);

        try {
            userDAO.addUser(user);
            response.sendRedirect("pages/auth/login.jsp");
        } catch (SQLException e) {
            e.printStackTrace(); // Для отладки
            response.sendRedirect("pages/auth/register.jsp?error=Registration failed: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("pages/auth/register.jsp").forward(request, response);
    }
}
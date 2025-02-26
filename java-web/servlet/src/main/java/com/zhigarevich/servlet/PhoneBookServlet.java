package com.zhigarevich.servlet;

import java.io.*;
import java.sql.*;

import com.mysql.cj.jdbc.Driver;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "phoneBookServlet", value = "/addEntry")
public class PhoneBookServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(PhoneBookServlet.class);

    // Замените эти значения на ваши настройки базы данных
    private static final String DB_URL = "jdbc:mysql://localhost:3306/example";
    private static final String DB_USER = "root"; // Ваше имя пользователя
    private static final String DB_PASSWORD = "12101906Nn#06"; // Ваш пароль

    public void init() {
        logger.info("PhoneBookServlet initialized");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO phone_book (last_name, phone_number) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, lastName);
                preparedStatement.setString(2, phoneNumber);
                preparedStatement.executeUpdate();

                logger.info("Added entry: {} - {}", lastName, phoneNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Database error: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            return;
        }

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Entry added successfully!</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
        logger.info("PhoneBookServlet destroyed");
    }
}
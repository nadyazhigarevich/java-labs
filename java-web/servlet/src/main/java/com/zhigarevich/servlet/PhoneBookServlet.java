package com.zhigarevich.servlet;

import com.zhigarevich.dao.PhoneBookDAO;
import com.zhigarevich.db.ConnectionPool;
import com.zhigarevich.model.PhoneBookEntry;
import com.zhigarevich.validator.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "phoneBookServlet", value = "/addEntry")
public class PhoneBookServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(PhoneBookServlet.class);
    private final PhoneBookDAO phoneBookDAO = new PhoneBookDAO();
    private static final String DATABASE_ERROR_TEMPLATE = "Something went wrong: %s";

    public void init() {
        logger.info("PhoneBookServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");

        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/login?error=" +
                    URLEncoder.encode("Требуется авторизация", StandardCharsets.UTF_8));
            return;
        }

        try {
            List<PhoneBookEntry> phoneBooks = phoneBookDAO.findAllEntries(userId);
            req.setAttribute("entries", phoneBooks);
            req.getRequestDispatcher("/pages/entries/entries.jsp").forward(req, resp);
        } catch (SQLException e) {
            logger.error("Error fetching entries: {}", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/error?code=500");
        }
    }

    private void sendJsonError(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"error\": \"%s\"}", message));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contactName = request.getParameter("contactName").trim();
        String phoneNumber = request.getParameter("phoneNumber").trim();
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        if (userId == null) {
            sendJsonError(response, "Требуется авторизация", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            // Валидация
            if (!Validator.validateContactName(contactName)) {
                throw new IllegalArgumentException("Имя контакта должно быть от 2 до 50 символов");
            }
            if (!Validator.validatePhoneNumber(phoneNumber)) {
                throw new IllegalArgumentException("Некорректный формат номера телефона");
            }

            // Создаем и сохраняем запись
            PhoneBookEntry entry = new PhoneBookEntry(userId, contactName, phoneNumber);
            phoneBookDAO.addEntry(entry, userId);
            logger.info("Added entry: {} - {}", contactName, phoneNumber);

            // Возвращаем успешный JSON с данными контакта
            response.setContentType("application/json");
            response.getWriter().write(String.format(
                    "{\"success\": true, \"contactName\": \"%s\", \"phoneNumber\": \"%s\"}",
                    contactName, phoneNumber
            ));

        } catch (IllegalArgumentException e) {
            sendJsonError(response, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            logger.error("Database error: {}", e.getMessage());
            sendJsonError(response, "Ошибка базы данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void destroy() {
        ConnectionPool.close();
        logger.info("PhoneBookServlet destroyed");
    }
}
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contactName = request.getParameter("contactName").trim();
        String phoneNumber = request.getParameter("phoneNumber").trim();
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        if (userId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Требуется авторизация\"}");
            return;
        }

        try {
            // Валидация данных
            if (!Validator.validateContactName(contactName)) {
                throw new IllegalArgumentException("Имя контакта должно быть от 2 до 50 символов");
            }

            if (!Validator.validatePhoneNumber(phoneNumber)) {
                throw new IllegalArgumentException("Некорректный формат номера телефона");
            }

            PhoneBookEntry entry = new PhoneBookEntry(userId, contactName, phoneNumber);
            phoneBookDAO.addEntry(entry, userId);
            logger.info("Added entry: {} - {}", contactName, phoneNumber);

            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true}");
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (SQLException e) {
            String message = DATABASE_ERROR_TEMPLATE.formatted(e.getMessage());
            logger.error(message);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Ошибка базы данных. Попробуйте позже.\"}");
        }
    }

    public void destroy() {
        ConnectionPool.close();
        logger.info("PhoneBookServlet destroyed");
    }
}
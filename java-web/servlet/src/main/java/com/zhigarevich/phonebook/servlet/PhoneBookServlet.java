package com.zhigarevich.phonebook.servlet;

import com.zhigarevich.phonebook.dao.impl.PhoneBookDaoImpl;
import com.zhigarevich.phonebook.exception.BusinessException;
import com.zhigarevich.phonebook.model.PhoneBookEntry;
import com.zhigarevich.phonebook.service.PhoneBookService;
import com.zhigarevich.phonebook.service.impl.PhoneBookServiceImpl;
import com.zhigarevich.phonebook.validator.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet(name = "phoneBookServlet", value = "/addEntry")
public class PhoneBookServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(PhoneBookServlet.class);
    private PhoneBookService phoneBookService;

    public void init() {
        this.phoneBookService = new PhoneBookServiceImpl();
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
            List<PhoneBookEntry> phoneBooks = phoneBookService.findAllByUser(userId);
            req.setAttribute("entries", phoneBooks);
            req.getRequestDispatcher("/pages/entries/entries.jsp").forward(req, resp);
        } catch (BusinessException e) {
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
            phoneBookService.save(entry);
            logger.info("Added entry: {} - {}", contactName, phoneNumber);

            // Возвращаем успешный JSON с данными контакта
            response.setContentType("application/json");
            response.getWriter().write(String.format(
                    "{\"success\": true, \"contactName\": \"%s\", \"phoneNumber\": \"%s\"}",
                    contactName, phoneNumber
            ));

        } catch (IllegalArgumentException e) {
            sendJsonError(response, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
    }
}
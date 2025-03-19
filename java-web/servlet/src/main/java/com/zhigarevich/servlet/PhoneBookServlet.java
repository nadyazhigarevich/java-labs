package com.zhigarevich.servlet;

import com.zhigarevich.dao.PhoneBookDAO;
import com.zhigarevich.db.ConnectionPool;
import com.zhigarevich.model.PhoneBookEntry;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
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
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            logger.warn("User not logged in, redirecting to login page.");
            resp.sendRedirect("login");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required");
            return;
        }

        List<PhoneBookEntry> phoneBooks;
        try {
            phoneBooks = this.phoneBookDAO.findAllEntries(userId);
        } catch (SQLException e) {
            logger.error("Error fetching entries: {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to fetch entries");
            return;
        }
        req.setAttribute("entries", phoneBooks);
        req.getRequestDispatcher("pages/entries/entries.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contactName = request.getParameter("contactName");
        String phoneNumber = request.getParameter("phoneNumber");
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        if (userId == null) {
            logger.warn("User ID is missing in session.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required");
            return;
        }

        PhoneBookEntry entry = new PhoneBookEntry(userId, contactName, phoneNumber);
        try {
            phoneBookDAO.addEntry(entry, userId);
            logger.info("Added entry: {} - {}", contactName, phoneNumber);

            // Ответ в формате JSON для автоматического обновления списка
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true}");
        } catch (SQLException e) {
            String message = DATABASE_ERROR_TEMPLATE.formatted(e.getMessage());
            logger.error(message);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + message + "\"}");
        }
    }

    public void destroy() {
        ConnectionPool.close();
        logger.info("PhoneBookServlet destroyed");
    }
}
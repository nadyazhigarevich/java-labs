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
        List<PhoneBookEntry> phoneBooks = List.of();
        try {
            phoneBooks = this.phoneBookDAO.findAllEntries();
        } catch (SQLException e) {
            String message = DATABASE_ERROR_TEMPLATE.formatted(e.getMessage());
            logger.error(message);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
        }
        req.setAttribute("entries", phoneBooks);
        req.getRequestDispatcher("pages/entries/entries.jsp").forward(req, resp);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        PhoneBookEntry entry = new PhoneBookEntry(contactName, phoneNumber, userID);
        try {
            phoneBookDAO.addEntry(entry);
            logger.info("Added entry: {} - {}", lastName, phoneNumber);
        } catch (SQLException e) {
            String message = DATABASE_ERROR_TEMPLATE.formatted(e.getMessage());
            logger.error(message);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
        }
    }

    public void destroy() {
        ConnectionPool.close();
        logger.info("PhoneBookServlet destroyed");
    }
}
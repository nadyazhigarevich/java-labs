package com.zhigarevich.command.impl;

import com.zhigarevich.command.Command;
import com.zhigarevich.dao.PhoneBookDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;

public class FindAllPhonesCommand implements Command {

    private final PhoneBookDAO phoneBookDAO = new PhoneBookDAO();

    @Override
    public String execute(HttpServletRequest request) throws ServletException, SQLException {
        // Получаем userId из параметров запроса
        int userId = Integer.parseInt(request.getParameter("userId")); // Убедитесь, что userId передается в запросе

        // Передаем userId в метод findAllEntries
        var phones = this.phoneBookDAO.findAllEntries(userId);
        request.setAttribute("entries", phones);
        return "page/entries/entries.jsp";
    }
}
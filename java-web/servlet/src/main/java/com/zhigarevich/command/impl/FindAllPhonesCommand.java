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
        var phones = this.phoneBookDAO.findAllEntries();
        request.setAttribute("entries", phones);
        return "page/entries/entries.jsp";
    }
}

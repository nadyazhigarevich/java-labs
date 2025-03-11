package com.zhigarevich.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;

public interface Command {

    String execute(final HttpServletRequest request) throws ServletException, SQLException;
}

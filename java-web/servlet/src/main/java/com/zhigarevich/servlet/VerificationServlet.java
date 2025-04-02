package com.zhigarevich.servlet;

import com.zhigarevich.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/verify")
public class VerificationServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");

        if (token == null || token.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login?error=Неверная ссылка подтверждения");
            return;
        }

        try {
            if (userDAO.verifyUser(token)) {
                response.sendRedirect(request.getContextPath() + "/login?message=Email успешно подтвержден. Теперь вы можете войти.");
            } else {
                response.sendRedirect(request.getContextPath() + "/login?error=Неверный или устаревший токен подтверждения");
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/login?error=Ошибка подтверждения email");
        }
    }
}
package com.zhigarevich.phonebook.filter;

import com.zhigarevich.phonebook.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {

    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/",
            "/login",
            "/register",
            "/verify",
            "/pages/auth/login.jsp",
            "/pages/auth/register.jsp",
            "/css/",
            "/js/",
            "/images/",
            "/resources/"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Инициализация фильтра (при необходимости)
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // Проверяем, является ли путь публичным
        boolean isPublicPath = PUBLIC_PATHS.stream()
                .anyMatch(publicPath -> path.startsWith(publicPath));

        if (isPublicPath) {
            chain.doFilter(request, response);
            return;
        }

        // Проверяем аутентификацию и подтверждение email
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?error=Требуется авторизация");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!user.isVerified()) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?error=Требуется подтверждение email");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Очистка ресурсов (при необходимости)
    }
}
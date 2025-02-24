package com.zhigarevich.servlet;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
gi
@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(HelloServlet.class);

    public void init() {
        logger.info("Servlet initialized");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        String numberParam = request.getParameter("number");
        float number = 0.0f;

        try {
            number = Float.parseFloat(numberParam);
            float result = number * 2;

            logger.info("Received number: {}", number);
            logger.info("Returning doubled number: {}", result);

            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>Doubled Number: " + result + "</h1>");
            out.println("</body></html>");
        } catch (NumberFormatException e) {
            logger.error("Invalid number format: {}", numberParam);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
        }
    }

    public void destroy() {
        logger.info("Servlet destroyed");
    }
}
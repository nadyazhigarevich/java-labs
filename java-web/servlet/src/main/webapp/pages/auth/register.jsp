<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <title>Регистрация</title>
    <script src="${pageContext.request.contextPath}/resources/js/validation.js"></script>
</head>
<body>
<h1>Регистрация</h1>
<% if (request.getParameter("error") != null) { %>
<p style="color:red;"><%= request.getParameter("error") %></p>
<% } %>
<form action="${pageContext.request.contextPath}/register" method="post" onsubmit="return validateRegistrationForm()">
    <label for="username">Имя пользователя:</label>
    <input type="text" id="username" name="username" required>
    <small>От 3 до 20 символов (только буквы и цифры)</small>
    <br>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>
    <br>

    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>
    <small>Не менее 6 символов</small>
    <br>

    <input type="submit" value="Зарегистрироваться">
</form>
<p>Уже есть аккаунт? <a href="${pageContext.request.contextPath}/login">Войти</a></p>
</body>
</html>
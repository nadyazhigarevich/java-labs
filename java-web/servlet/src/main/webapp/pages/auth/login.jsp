<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <title>Вход</title>
</head>
<body>
<h1>Вход</h1>
<% if (request.getParameter("error") != null) { %>
<p style="color:red;"><%= request.getParameter("error") %></p>
<% } %>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="username">Имя пользователя:</label>
    <input type="text" id="username" name="username" required>
    <br>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>
    <br>
    <input type="submit" value="Войти">
</form>
<a href="${pageContext.request.contextPath}/pages/auth/register.jsp">Зарегистрироваться</a>
</body>
</html>
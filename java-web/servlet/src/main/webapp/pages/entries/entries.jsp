<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Phone Book</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <script src="${pageContext.request.contextPath}/resources/js/validation.js"></script>
</head>
<body>
<h1>All Entries:</h1>

<% if (request.getParameter("error") != null) { %>
<p style="color:red;"><%= request.getParameter("error") %></p>
<% } %>

<form action="${pageContext.request.contextPath}/addEntry" method="post" onsubmit="return validateContactForm()">
    <input type="hidden" name="userId" value="${sessionScope.userId}">
    <label for="contactName">Contact Name:</label>
    <input type="text" id="contactName" name="contactName" required>
    <small>От 2 до 50 символов</small>

    <label for="phoneNumber">Phone Number:</label>
    <input type="text" id="phoneNumber" name="phoneNumber" required>
    <small>Формат: +375291234567 или 80441234567</small>

    <input type="submit" value="Add Contact">
</form>

<ul id="entriesList">
    <c:if test="${not empty requestScope.entries}">
        <c:forEach var="entry" items="${requestScope.entries}">
            <li>${entry.contactName}: ${entry.phoneNumber}</li>
        </c:forEach>
    </c:if>
    <c:if test="${empty requestScope.entries}">
        <li>No contacts available.</li>
    </c:if>
</ul>

<a href="${pageContext.request.contextPath}/logout">Logout</a>
</body>
</html>
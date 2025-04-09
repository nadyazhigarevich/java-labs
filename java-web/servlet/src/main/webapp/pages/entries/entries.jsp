<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Phone Book</title>
    <script>
        document.getElementById('contactForm').addEventListener('submit', async (e) => {
            e.preventDefault();
            const formData = new FormData(e.target);

            try {
                const response = await fetch(e.target.action, {
                    method: 'POST',
                    body: formData,
                    credentials: 'include'
                });

                if (!response.ok) {
                    const error = await response.json();
                    throw new Error(error.error || "Ошибка сервера");
                }

                const data = await response.json();
                const list = document.getElementById('entriesList');

                // Удаляем заглушку "No contacts", если есть
                if (list.firstElementChild?.textContent.includes("No contacts")) {
                    list.removeChild(list.firstElementChild);
                }

                // Добавляем новый контакт
                const newItem = document.createElement('li');
                newItem.textContent = `${data.contactName}: ${data.phoneNumber}`;
                list.prepend(newItem);
                e.target.reset();

            } catch (error) {
                alert(error.message || "Произошла неизвестная ошибка");
                console.error("Ошибка:", error);
            }
        });
    </script>
</head>
<body>
<h1>All Entries:</h1>

<form id="contactForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <label>
        Contact Name:
        <input type="text" name="contactName" required>
    </label>
    <small>От 2 до 50 символов</small>

    <label>
        Phone Number:
        <input type="text" name="phoneNumber" required>
    </label>
    <small>Формат: +375291234567 или 80441234567</small>

    <button type="submit">Add Contact</button>
</form>

<ul id="entriesList">
    <c:choose>
        <c:when test="${not empty requestScope.entries}">
            <c:forEach var="entry" items="${requestScope.entries}">
                <li>${entry.contactName}: ${entry.phoneNumber}</li>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <li>No contacts available.</li>
        </c:otherwise>
    </c:choose>
</ul>
</body>
</html>
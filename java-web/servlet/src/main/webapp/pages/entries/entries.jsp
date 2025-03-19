<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <title>Phone Book</title>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css">
    <script src="resources/js/scripts.js"></script>
    <script>
        function refreshEntries() {
            const userId = ${sessionScope.userId};
            fetch(`./getEntries?userId=${userId}`)
                .then(response => response.json())
                .then(data => {
                    const entriesList = document.getElementById('entriesList');
                    entriesList.innerHTML = ''; // Очищаем текущий список
                    if (data.length === 0) {
                        entriesList.innerHTML = '<li>No contacts available.</li>';
                    } else {
                        data.forEach(entry => {
                            const li = document.createElement('li');
                            li.textContent = `${entry.contactName}: ${entry.phoneNumber}`;
                            entriesList.appendChild(li);
                        });
                    }
                })
                .catch(error => console.error('Error fetching entries:', error));
        }

        // Вызываем refreshEntries при загрузке страницы
        window.onload = refreshEntries;

        // Вызываем refreshEntries после успешного добавления контакта
        function onContactAdded() {
            refreshEntries();
        }
    </script>
</head>
<body>
<h1>All Entries:</h1>

<!-- Форма для добавления контакта -->
<form action="${pageContext.request.contextPath}/addEntry" method="post" onsubmit="onContactAdded()">
    <input type="hidden" name="userId" value="${sessionScope.userId}"> <!-- Скрытое поле для userId -->
    <label for="contactName">Contact Name:</label>
    <input type="text" id="contactName" name="contactName" required>

    <label for="phoneNumber">Phone Number:</label>
    <input type="text" id="phoneNumber" name="phoneNumber" required>

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

<a href="logout">Logout</a>
</body>
</html>
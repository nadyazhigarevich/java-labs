<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Phone Book</title>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css">
    <script src="resources/js/scripts.js"></script>
</head>
<body>
<h1>Entry added successfully!</h1>
<h2>All Entries:</h2>
<ul>
    <c:forEach var="entry" items="${requestScope.entries}">
        <li>${entry.lastName}: ${entry.phoneNumber}</li>
    </c:forEach>
</ul>
<a href="logout">Logout</a>
</body>
</html>
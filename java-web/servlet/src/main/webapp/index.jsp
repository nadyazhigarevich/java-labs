<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "First servlet" %></h1>
<form action="hello-servlet" method="get">
    <input type="text" name="number" placeholder="Enter a number" required />
    <input type="submit" value="Submit" />
</form>
<br/>
</body>
</html>
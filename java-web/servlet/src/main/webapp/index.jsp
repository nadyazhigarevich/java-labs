<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Phone Book Entry</title>
</head>
<body>
<h1>Add Phone Book Entry</h1>
<form action="addEntry" method="post">
    <input type="text" name="lastName" placeholder="Last Name" required />
    <input type="text" name="phoneNumber" placeholder="Phone Number" required />
    <input type="submit" value="Add Entry" />
</form>
<br/>
</body>
</html>
<%@ page import="clientSide.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/profile" method="get">
    Welcome ${parameter} <input type="submit" value="My Profile" ${hiddenButton}/>
</form>
</body>
</html>

<%@ page import="clientSide.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Welcome
<%=((User) request.getSession().getAttribute("user")).getUserName()%>
<form action="/profile" method="get">
    <input type="submit" value="My Profile"/>
</form>
</body>
</html>

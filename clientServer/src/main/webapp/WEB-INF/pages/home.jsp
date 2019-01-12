<%@ page import="clientSide.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Welcome
<%=((Users) request.getSession().getAttribute("user")).getUserName()%>
</body>
</html>

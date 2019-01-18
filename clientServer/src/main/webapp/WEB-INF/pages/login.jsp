<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2019-01-17
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/login" method="post">
    <label>
        Username: <input type="text" name="username" required/>
    </label>
    <label>
        Password: <input type="password" name="password" required/>
    </label>
    <input type="submit" value="Login"/>
</form>
${loginConfirmedLogin}
</body>
</html>

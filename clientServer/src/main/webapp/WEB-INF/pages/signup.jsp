<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2019-01-12
  Time: 17:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/signup" method="post">
    <label>
        Username: <input type="text" name="username" required/>
    </label>
    <label>
        FirstName: <input type="text" name="firstName"required/>
    </label>
    <label>
        LastName: <input type="text" name="lastName" required/>
    </label>
    <label>
        Email: <input type="email" name="email" required/>
    </label>
    <label>
        Password: <input type="password" name="password" required/>
    </label>
    <label>
        Confirm Password: <input type="password" name="confirmPassword" required/>
    </label>
    <input type="submit" value="Submit"/>
</form>
${passwordConfirmed}
</body>
</html>

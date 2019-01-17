<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2019-01-16
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/addPost" method="post">
    <label>
        Title: <input type="text" name="title" required/>
    </label>
    <label>
        Description: <input type="text" name="description" required/>
    </label>
    <select name="type">
        Type:
        <option value="IT">IT</option>
        <option value="Marketing">Marketing</option>
    </select>
    <label>
        Email: <input type="email" name="email" required/>
    </label>
    <label>
        Salary: <input type="number" name="salary"/>
    </label>
    <select name="workTime">
        Work Time:
        <option value="Half Time">Half Time</option>
        <option value="Full Time">Full Time</option>
    </select>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>

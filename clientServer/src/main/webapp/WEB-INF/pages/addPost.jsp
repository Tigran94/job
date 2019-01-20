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
    <style>
        .titles{
            margin-top:10px;
            display: block;

        }
        .type {
            margin: 13px;
            width: 100px;
            height: 20px;
            margin-top: 10px;
        }
        .workTime {
            margin: 0 2px;
            width: 100px;
            height: 20px;
            margin-top: 10px;
        }
        .textarea{
            margin-top: 5px;
            margin: 0 53px;
        }
        .title{
            margin: 0 15px;
        }

        .email{
            margin-top: -10px;
            display: block;
        }
        .email_textarea{
            margin: 6px;
        }

        .salary{
            margin-top: 0px;
            display: block;
        }
        .salary_textarea{
            margin: 3px;
        }
    </style>
</head>
<body>
<form action="/addPost" method="post">
    <label class="titles">
        Title: <input class="title" type="text" name="title" required/>
    </label>

    <label class="titles">
        <%--Description: <input type="text" name="description" required/>--%>
        Description: <br/><textarea class="textarea" name="description" cols="40" rows="4" required></textarea>
    </label>
    Type: <select name="type" class="type">
        <option value="IT">IT</option>
        <option value="Marketing">Marketing</option>
    </select>
    <label class="email">
        Email: <input class="email_textarea" type="email" name="email" required/>
    </label>
    <label class="salary">
        Salary: <input class="salary_textarea" type="number" name="salary"/>
    </label>

    Work Time:
    <select name="workTime" class="workTime" >
        <option value="Half Time">Half Time</option>
        <option value="Full Time">Full Time</option>
        <option value="Remote">Remote</option>
    </select>
    <br/>
    <input type="submit" value="Submit" class="titles"/>
</form>

<form action="/home" method="get">
    <input type="submit" value="Home" class="titles"/>
</form>
<form action="/profile" method="get">
    <input type="submit" value="Back" class="titles"/>
</form>
</body>
</html>

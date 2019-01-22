<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
    <style>
        *{
            padding: 0;
            margin: 0;
            box-sizing: border-box;
        }
        .titles {
            background-color: #FFFAA9;
            width: 30%;
            height: 100%;
            float: left;
        }
        .filter{
            background-color: brown;
            width: 20%;
            height: 100%;
            float: left;
        }
        .content {
            background-color: bisque;
            width: 50%;
            height: 100%;
            float: left;
        }

        .header{
            background-color: darkcyan;
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
        .salary{
            margin-top: 0px;
            display: block;
        }
        .salary_textarea{
            margin: 3px;
        }
        .clear {
            clear: both;
        }
    </style>
</head>
<body>
<div class="header">
<form action="/profile" method="get">
    Welcome ${parameter} <input type="submit" value="My Profile" ${hiddenButton}/>
</form>
</div>

<div class="filter">
    <form action="/home" method="post">
        Type:
        <select name="type" class="type">
            <option></option>
            <option value="IT">IT</option>
            <option value="Marketing">Marketing</option>
        </select>

        <label class="salary">
            Salary: <input class="salary_textarea" type="number" name="salary"/>
        </label>

        Work Time:
        <select name="workTime" class="workTime">
            <option></option>
            <option value="Half Time">Half Time</option>
            <option value="Full Time">Full Time</option>
            <option value="Remote">Remote</option>
        </select>
        <br/>
        <input type="submit" value="Filter"/>
    </form>
</div>

<div class="titles">
    <c:forEach var="jobTitle" items="${jobTitles}">
        <a href="/home/${jobTitle.id}">
            <c:if test="${jobTitle.id == post.id}">
                >>>
            </c:if>
                ${jobTitle.title}
        </a>
        <br/>
    </c:forEach>
</div>

<div class="content">
    Title: ${post.title}
    <br/>
    Description: ${post.description}
    <br/>
    Type: ${post.type}
    <br/>
    Salary: ${post.salary}
    <br/>
    Work Time: ${post.workTime}
    <br/>
    Email: ${post.email}
    <br/>
    Company: ${post.company}
    <br/>
</div>
<div class="content">

</div>
<div class="clear">
</div>
</body>
</html>

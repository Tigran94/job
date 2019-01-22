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
        .header{
            background-color: darkcyan;
            padding: 20px 15px;
            color: #fff;
            text-transform: uppercase;
            max-width: 100%;
        }
        .header a{
            padding: 5px 10px;
            color: #fff;
            background-color: lightgreen;
            text-transform: uppercase;
            border: none;
            text-decoration: none;
        }
        .header a:hover{
            background-color: green;
            cursor: pointer;
        }
        .main{
            max-width: 100%;
            display: flex;
        }
        .titles {
            background-color: lightgreen;
            width: 20%;
            height: 100vh;
        }
        .titles ul li{
            list-style-type: none;
            border-bottom: 2px solid #fff;
        }
        .titles ul li a{
            text-transform: capitalize;
            text-decoration: none;
            color: green;
            padding: 5px 10px;
            line-height: 28px;
            display: block;
            width: 100%;
        }
        .titles li:hover{
            background-color: green;
            cursor: pointer;
        }
        .titles li:hover a{
            color: #fff;
            font-weight: bold;
        }
        .content {
            background-color: #d0d0d0;
            width: 60%;
            height: 100vh;
        }
        .filter{
            background-color: brown;
            width: 20%;
            height: 100vh;
        }
        .formdiv{
            display: flex;
            justify-content: space-between;
            width: 100%;
            padding: 5px;
            max-height: 40px;
        }
        .formdiv input, select{
            width: 100px;
        }
        .labels{
            color: #fff;
            width: 50%;
        }
        #salarytype{
            width: 100px;
        }
        .vals{
            width: 50%;
        }
        #filter{
            padding: 5px 10px;
            color: #fff;
            font-weight: bold;
            text-transform: uppercase;
            border: none;
            background-color: #cd5b5b;
        }
        #filter:hover{
            background-color: #6e2a2a;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="header">
    <span>Welcome ${parameter} </span>
    <a href="/profile">My Profile</a>
</div>
<div class="main">
    <div class="filter">
        <form action="/home" method="post">
            <div class="formdiv">
                <div class="labels">
                    <label for="typename">Type: </label>
                </div>
                <div class="vals">
                    <select name="type" id="typename">
                        <option></option>
                        <option value="IT">IT</option>
                        <option value="Marketing">Marketing</option>
                    </select>
                </div>
            </div>
            <div class="formdiv">
                <div class="labels">
                    <label for="salarytype">Salary: </label>
                </div>
                <div class="vals">
                    <input id="salarytype" type="number" name="salary"/>
                </div>
            </div>
            <div class="formdiv">
                <div class="labels">
                    <label for="worktime">Work Time: </label>
                </div>
                <div class="vals">
                    <select id="worktime" name="workTime">
                        <option></option>
                        <option value="Half Time">Half Time</option>
                        <option value="Full Time">Full Time</option>
                        <option value="Remote">Remote</option>
                    </select>
                </div>
            </div>
            <div class="formdiv">
                <input id="filter" type="submit" value="Filter"/>
            </div>
        </form>
    </div>
    <div class="titles">
        <c:forEach var="jobTitle" items="${jobTitles}">
            <ul>
                <li>
                    <a href="/home/${jobTitle.id}">${jobTitle.title}</a>
                </li>
            </ul>
        </c:forEach>
    </div>
    <div class="content">
    </div>
</div>
</body>
</html>
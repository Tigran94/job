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
        body{
            background-color: #19504b;
        }
        .header{
            background-color: #1f4545;
            padding: 20px 15px;
            color: #fff;
            text-transform: uppercase;
            width: 100%;
            position: fixed;
            top: 0;
            box-shadow: 0px 5px 2px #000;
        }
        .header a{
            padding: 5px 10px;
            color: #fff;
            background-color: #3e8484;
            text-transform: uppercase;
            border: none;
            border-radius: 6px;
            text-decoration: none;
            font-size: 14px;
        }
        .header a:hover{
            background-color: #265353;
            cursor: pointer;
            border: 1px solid #fff;
            padding: 4px 10px;
        }
        .main{
            max-width: 100%;
            display: flex;
            margin-top: 62px;
        }
        .titles {
            background-color: #19504b;
            width: 20%;
            height: 100vh;
            position: fixed;
            overflow-y: auto;

        }
        .titles ul li{
            list-style-type: none;
            border-bottom: 2px solid #fff;
        }
        .titles ul li a{
            text-transform: capitalize;
            text-decoration: none;
            color: #a9c1bf;
            padding: 5px 10px;
            line-height: 28px;
            display: block;
            width: 100%;
            font-weight: bold;
            word-wrap: break-word;
        }
        .titles li:hover{
            background-color: #1a3131;
            cursor: pointer;
        }
        .titles li:hover a{
            color: #fff;
        }
        .content {
            background-color: #ced2d4;
            width: 80%;
            height: 100vh;
            padding: 5px;
            margin-left: 20%;
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
            background-color: #3e8484;
            text-transform: uppercase;
            border: none;
            border-radius: 6px;
            text-decoration: none;
            font-size: 14px;
        }
        #filter:hover{
            background-color: #265353;
            cursor: pointer;
            border: 1px solid #fff;
            padding: 4px 10px;
        }
        #form{
            position: fixed;
            top: 62px;
            background-color: #19504b;
            width: 20%;
            box-shadow: 0px 2px 2px #000;
        }
        #list{
            overflow-y: auto;
            overflow-x: hidden;
        }
        ::-webkit-scrollbar {
            width: 10px;
            border-radius: 10px;
        }
        ::-webkit-scrollbar-thumb {
            background: #1f4545;
            border-radius: 10px;
            border: 2px solid #fff;
        }
        ::-webkit-scrollbar-track {
            box-shadow: inset 0 0 5px #fff;
            border-radius: 10px;
        }
        .mrg{
            padding-top: 125px;
            overflow-y: auto;
        }
    </style>
</head>
<body>
<div class="header">
    <span>Welcome ${parameter} </span>
    <a href="/profile" ${hiddenButton}>My Profile</a>
</div>
<div class="main">
    <div class="titles">
        <form id="form" action="/home" method="post">
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
        <div class="mrg">
            <c:forEach var="jobTitle" items="${jobTitles}">
                <ul id="list">
                    <li>
                        <a href="/home/${jobTitle.id}">${jobTitle.title}</a>
                    </li>
                </ul>
            </c:forEach>
        </div>
    </div>
    <div class="content">

    </div>
</div>
</body>
</html>
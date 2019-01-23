<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Title</title>
    <style>
        *{
            padding: 0;
            margin: 0;
            box-sizing: border-box;
        }
        body{
            background-color: #ced2d4;
        }
        .header{
            background-color: #1f4545;
            padding: 20px 15px;
            color: #ced2d4;
            text-transform: uppercase;
            width: 100%;
            position: fixed;
            top: 0;
            box-shadow: 0px 5px 2px #000;
        }
        .header a{
            padding: 5px 10px;
            color: #ced2d4;
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
            border: 1px solid #ced2d4;
            padding: 4px 9px;
        }
        .tablerow{
            display: flex;
            padding: 5px;
            width: 40%;
            justify-content: space-between;
        }
        .main{
            max-width: 100%;
            margin-top: 62px;
        }
        .tablerow{
            display: flex;
            padding: 5px;
            width: 50%;
            justify-content: end;
        }
        .left{
            font-weight: bold;
            color: #1f4545;
            width: 30%;
        }
        #subm{
            padding: 5px 10px;
            color: #ced2d4;
            background-color: #3e8484;
            text-transform: uppercase;
            border: none;
            border-radius: 6px;
            text-decoration: none;
            font-size: 14px;
        }
        #subm:hover{
            background-color: #265353;
            cursor: pointer;
            border: 1px solid #ced2d4;
            padding: 4px 10px;
            box-shadow: 5px 5px 2px #000;
        }
        input[placeholder], select, textarea, input[type="date"]{
            padding: 5px;
            outline: none;
            border: 1px solid #1f4545;
        }
    </style>
</head>
<body>
<div class="header">
    <a href="/profile">Back</a>
    <a href="/home">Home</a>
</div>
<div class="main">
    <form action="/addPost" method="post">
        <div class="tablerow">
            <div class="left">
                <label for="title">Title: </label>
            </div>
            <div class="right">
                <input id="title" type="text" name="title" placeholder="Title" required/>
            </div>
        </div>
        <div class="tablerow">
            <div class="left">
                <label for="textarea">Description: </label>
            </div>
            <div class="right">
                <textarea id="textarea" name="description" cols="40" rows="4" placeholder="Description" required></textarea>
            </div>
        </div>
        <div class="tablerow">
            <div class="left">
                <label for="select">Type: </label>
            </div>
            <div class="right">
                <select id="select" name="type" class="type">
                    <option value="" disabled selected>Select your option</option>
                    <option value="IT">IT</option>
                    <option value="Marketing">Marketing</option>
                </select>
            </div>
        </div>
        <div class="tablerow">
            <div class="left">
                <label for="salary">Salary: </label>
            </div>
            <div class="right">
                <input id="salary" type="text" name="salary" placeholder="Salary" required/>
            </div>
        </div>
        <div class="tablerow">
            <div class="left">
                <label for="time">Work Time: </label>
            </div>
            <div class="right">
                <select id="time" name="workTime" class="workTime">
                    <option value="" disabled selected>Select your option</option>
                    <option value="Half Time">Half Time</option>
                    <option value="Full Time">Full Time</option>
                    <option value="Remote">Remote</option>
                </select>
            </div>
        </div>
        <div class="tablerow">
            <div class="left">
                <label for="comp">Company: </label>
            </div>
            <div class="right">
                <input id="comp" type="text" name="company" placeholder="Company" required/>
            </div>
        </div>
        <div class="tablerow">
            <div class="left">
                <label for="currentDate">Date: </label>
            </div>
            <div class="right">
                <input type="date" name="endDate" id="currentDate" required min="">
            </div>
        </div>
        <div class="tablerow">
            <div class="left">
                <input type="submit" value="Submit" id="subm"/>
            </div>
        </div>
    </form>
</div>
</body>
<script type="text/javascript" src='<c:url value="/resources/scripts/todaysDate.js"/>'></script>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style type="text/css">
        *{
            padding: 0;
            margin: 0;
            box-sizing: border-box;
        }
        body{
            background-color: #d0d0d0;
        }
        .main{
            max-width: 350px;
            padding: 20px;
            border-radius: 5px;
            margin: 18% auto;
            background-color: darkcyan;
            box-shadow: 5px 5px 7px;
        }
        .rows{
            display: flex;
            justify-content: space-between;
            padding: 5px;
            color: #fff;
        }
        #login{
            border: none;
            background-color: #60caa6;
            padding: 5px 10px;
            text-transform: uppercase;
            color: #fff;
            cursor: pointer;
        }
        #login:hover{
            background-color: #579a9c;
        }
        #err{
            color: #620202;
            text-shadow: 5px 5px 7px #333;
            text-transform: uppercase;
            font-size: 15px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="main">
    <form action="/login" method="post">
        <div class="rows">
            <div class="lab">
                <label for="usname">Username: </label>
            </div>
            <div class="inp">
                <input id="usname" type="text" name="username" placeholder="Username" required/>
            </div>
        </div>
        <div class="rows">
            <div class="lab">
                <label for="pass">Password: </label>
            </div>
            <div class="inp">
                <input id="pass" type="password" name="password" placeholder="Password" required/>
            </div>
        </div>
        <div class="rows">
            <div class="lab">
                <input id="login" type="submit" value="Login"/>
            </div>
            <div class="inp">
                <span id="err">${loginConfirmedLogin}</span>
            </div>
        </div>
    </form>
</div>
</body>
</html>
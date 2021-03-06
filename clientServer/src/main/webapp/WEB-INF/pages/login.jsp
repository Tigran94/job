<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <title>Title</title>
    <style type="text/css">
        *{
            padding: 0;
            margin: 0;
            box-sizing: border-box;
        }
        body{
            background-color: #ced2d4;
        }
        .main{
            max-width: 350px;
            padding: 20px;
            border-radius: 5px;
            margin: 18% auto;
            background-color: #1f4545;
            box-shadow: 5px 5px 7px #000;
        }
        .rows{
            display: flex;
            justify-content: space-between;
            padding: 5px;
            color: #ced2d4;
        }
        .login{
            border: none;
            background-color: #3e8484;
            padding: 5px 10px;
            text-transform: uppercase;
            color: #ced2d4;
            cursor: pointer;
            border-radius: 6px;
            font-family: sans-serif;
            font-size: 15px;
        }
        .login:hover{
            background-color: #579a9c;
        }
        #err{
            color: #620202;
            text-shadow: 5px 5px 7px #333;
            text-transform: uppercase;
            font-size: 15px;
            font-weight: bold;
        }
        #usname, #pass{
            background-color: #ced2d4;
            padding: 5px;
            outline: none;
        }
        .cancel{
            text-decoration: none;
            font-family: sans-serif;
            font-size: 15px;
            line-height: 27px;
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
                <span id="err">${loginConfirmedLogin}</span>
            </div>
        </div>
        <div class="rows">
            <div class="lab">
                <input class="login" type="submit" value="Login"/>
            </div>
            <div class="inp">
                <a class="login cancel" href="/">Back</a>
            </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
</body>
</html>
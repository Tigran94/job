<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <head>
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
                margin: 15% auto;
                background-color: #1f4545;
                box-shadow: 5px 5px 7px #000;
            }
            .row{
                display: flex;
                justify-content: space-between;
                padding: 5px;
                color: #ced2d4;
            }
            .log{
                border: none;
                background-color: #3e8484;
                padding: 5px 10px;
                text-transform: uppercase;
                color: #ced2d4;
                cursor: pointer;
                font-family: sans-serif;
                font-size: 15px;
                border-radius: 6px;
            }
            .log:hover{
                background-color: #579a9c;
            }
            .welcome{
                text-align: center;
                width: 100%;
                color: #ced2d4;
                text-shadow: 5px 5px 7px #000;
                margin-bottom: 15px;
            }
            .sign{
                text-decoration: none;
                font-family: sans-serif;
                font-size: 15px;
                line-height: 27px;
            }
            .guest{
                text-decoration: none;
                border: none;
                background-color: #3e8484;
                padding: 5px 13px;
                text-transform: uppercase;
                color: #ced2d4;
                cursor: pointer;
                font-family: sans-serif;
                border-radius: 6px;
            }
            .guest:hover{
                background-color: #579a9c;
            }
            #err{
                color: #620202;
                text-shadow: 5px 5px 7px #000;
                text-transform: uppercase;
                font-size: 15px;
                font-weight: bold;
            }
            #username, #password{
                background-color: #ced2d4;
                padding: 5px;
                outline: none;
            }
        </style>
    </head>
<body>
<div class="main">
    <div class="welcome">
        <h1>Welcome To JOB.AM</h1>
    </div>
    <form action="/" method="post">
        <div class="row">
            <div class="labels">
                <label for="username">Username: </label>
            </div>
            <div class="inputs">
                <input id="username" type="text" name="username" placeholder="Username" required/>
            </div>
        </div>
        <div class="row">
            <div class="labels">
                <label for="password">Password: </label>
            </div>
            <div class="inputs">
                <input id="password" type="password" name="password" placeholder="Password" required/>
            </div>
        </div>
        <div class="row">
            <div class="labels">
                <a class="log sign" href="/signup">Sign Up</a>
            </div>
            <div class="inputs">
                <input class="log" type="submit" value="Login"/>
            </div>
        </div>
        <div class="row">
            <div class="labels">
                <a class="guest" href="/home">Guest</a>
            </div>
            <div class="inputs">
                <span id="err">${loginConfirmedMain}</span>
            </div>
        </div>
    </form>
</div>
</body>
</html>
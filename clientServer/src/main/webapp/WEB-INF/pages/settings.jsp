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
        .main{
            max-width: 100%;
            margin-top: 62px;
            display: flex;
            justify-content: center;
        }
        .wind{
            width: 370px;
            padding: 20px;
            border-radius: 5px;
            margin: 15% auto;
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
        }
        .login:hover{
            background-color: #579a9c;
        }
        .err{
            color: #620202;
            text-shadow: 5px 5px 7px #333;
            text-transform: uppercase;
            font-size: 15px;
            font-weight: bold;
        }
        input[placeholder]{
            background-color: #ced2d4;
            padding: 5px;
            outline: none;
        }
        .cnt{
            width: 100%;
            text-align: center;
            text-transform: uppercase;
            font-size: 23px;
            box-shadow: 0px 2px 2px #000;
        }
    </style>
</head>
<body>
<div class="header">
    <a href="#">Back</a>
</div>
<div class="main">
    <div class="wind">
        <form action="/settings/fn" method="post">
            <div class="rows">
                <div class="lab cnt">
                    <p>Change First Name</p>
                </div>
            </div>
            <div class="rows">
                <div class="lab">
                    <label for="nfname">New First Name: </label>
                </div>
                <div class="inp">
                    <input id="nfname" placeholder="New First Name" type="text" name="firstName"/>
                </div>
            </div>
            <div class="rows">
                <div class="lab">
                    <input class="login" type="submit" value="Submit"/>
                </div>
                <div class="inp">
                    <span class="err">${firstNameChanged}</span>
                </div>
            </div>
        </form>
    </div>
    <div class="wind">
        <form action="/settings/ln" method="post">
            <div class="rows">
                <div class="lab cnt">
                    <p>Change Last Name</p>
                </div>
            </div>
            <div class="rows">
                <div class="lab">
                    <label for="nlname">New Last Name: </label>
                </div>
                <div class="inp">
                    <input id="nlname" placeholder="New Last Name" type="text" name="lastName"/>
                </div>
            </div>
            <div class="rows">
                <div class="lab">
                    <input class="login" type="submit" value="Submit"/>
                </div>
                <div class="inp">
                    <span class="err">${lastNameChanged}</span>
                </div>
            </div>
        </form>
    </div>
    <div class="wind">
        <form action="/settings/pass" method="post">
            <div class="rows">
                <div class="lab cnt">
                    <p>Change Password</p>
                </div>
            </div>
            <div class="rows">
                <div class="lab">
                    <label for="cpass">Current Password: </label>
                </div>
                <div class="inp">
                    <input id="cpass" placeholder="Current Password" type="password" name="currentPassword"/>
                </div>
            </div>
            <div class="rows">
                <div class="lab">
                    <label for="npass">New Password: </label>
                </div>
                <div class="inp">
                    <input id="npass" placeholder="New Password" type="password" name="newPassword"/>
                </div>
            </div>
            <div class="rows">
                <div class="lab">
                    <input class="login" type="submit" value="Submit"/>
                </div>
                <div class="inp">
                    <span class="err">${passwordChanged}</span>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
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
            max-width: 400px;
            padding: 20px;
            border-radius: 5px;
            margin: 10% auto;
            background-color: #1f4545;
            box-shadow: 5px 5px 7px #000;
        }
        .rows{
            display: flex;
            justify-content: space-between;
            padding: 5px;
            color: #ced2d4;
        }
        .sub{
            border: none;
            background-color: #3e8484;
            padding: 5px 10px;
            text-transform: uppercase;
            color: #ced2d4;
            cursor: pointer;
            border-radius: 6px;
            font-family: sans-serif;
            font-size: 15px;
            border-radius: 6px;
        }
        .sub:hover{
            background-color: #579a9c;
        }

        .aa{
            text-decoration: none;
            font-family: sans-serif;
            font-size: 15px;
            line-height: 27px;
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
        .fileInpt{
            width: 0.1px;
            height: 0.1px;
            opacity: 0;
            overflow: hidden;
            position: absolute;
            z-index: -1;
        }
        .fileInpt + label{
            font-size: 1.25em;
            font-weight: 700;
            color: white;
            background-color: red;
            display: inline-block;
        }



    </style>
</head>
<body>
<div class="main">
    <form action="/signup" method="post" enctype="multipart/form-data">
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
                <label for="frname">FirstName: </label>
            </div>
            <div class="inp">
                <input id="frname" type="text" name="firstname" placeholder="FirstName" required/>
            </div>
        </div>
        <div class="rows">
            <div class="lab">
                <label for="lname">LastName: </label>
            </div>
            <div class="inp">
                <input id="lname" type="text" name="lastname" placeholder="LastName" required/>
            </div>
        </div>
        <div class="rows">
            <div class="lab">
                <label for="mail">Email: </label>
            </div>
            <div class="inp">
                <input id="mail" type="email" name="email" placeholder="Email" required/>
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
                <label for="confpass">Confirm Password: </label>
            </div>
            <div class="inp">
                <input id="confpass" type="password" name="confirmPassword" placeholder="Confirm Password" required/>
            </div>
        </div>
        <div class="rows">
            <div class="lab">
                <p class="err">${passwordConfirmed}</p>
                <p class="err">${registrationFailed}</p>
            </div>
        </div>
        <div class="rows">
            <div class="lab">
                <label>Upload your cv:</label>
            </div>
        </div>
        <div class="rows">
            <input type="file"  id="file" name="pdfFile" class="fileInpt" accept="application/pdf">
            <label for="file">Choose a file</label>
        </div>
        <div class="rows">
            <div class="lab">
                <p class="err">${uploadError}</p>
            </div>
        </div>
        <div class="rows">
            <div>
                <input class="sub" type="submit" value="Submit"/>
            </div>
            <div>
                <a href="/" class="sub aa">Cancel</a>
            </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
</body>
</html>
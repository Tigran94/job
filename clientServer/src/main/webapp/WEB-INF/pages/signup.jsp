<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Title</title>
</head>
<body>
<form action="/signup" method="post">
    <label>
        Username: <input type="text" name="username" required/>
    </label>
    <label>
        FirstName: <input type="text" name="firstName"required/>
    </label>
    <label>
        LastName: <input type="text" name="lastName" required/>
    </label>
    <label>
        Email: <input type="email" name="email" required/>
    </label>
    <label>
        Password: <input type="password" name="password" required/>
    </label>
    <label>
        Confirm Password: <input type="password" name="confirmPassword" required/>
    </label>
    <input type="submit" value="Submit"/>
</form>
<form action="/" method="get">
    <input type="submit" value="Back"/>
</form>
${passwordConfirmed}
${registrationFailed}
</body>
</html>

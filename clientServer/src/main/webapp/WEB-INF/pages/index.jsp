<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Hello To JOB.AM
<form action="/" method="post">
    <label>
        Username: <input type="text" name="username" required/>
    </label>
    <label>
        Password: <input type="password" name="password" required/>
    </label>
    <input type="submit" value="Login"/>
</form>
<br/>
<form action="/signup" method="get">
    <input type="submit" value="Sign Up">
</form>
<br/>

<form action="/guest" method="get">
    <input type="submit" value="Guest">
</form>
<br/>
${loginConfirmedMain}

</body>
</html>

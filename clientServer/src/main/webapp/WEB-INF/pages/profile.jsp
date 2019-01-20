<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
My Profile

<form action="/addPost" method="get">
    <input type="submit" value="Add Post"/>
</form>
<form action="/profile" method="post">
    <label>
        Change Password
    </label>
    <label>
        Current Password:  <input type="password" name="currentPassword"/>
    </label>
    <label>
        New Password:  <input type="password" name="newPassword"/>
    </label>
    <input type="submit" value="Submit"/>
</form>
${passwordChanged}

<form action="/home" method="get">
    <input type="submit" value="Back"/>
</form>
<form action="/profile/" method="get">
    <input type="submit" value="Log Out"/>
</form>

</body>
</html>

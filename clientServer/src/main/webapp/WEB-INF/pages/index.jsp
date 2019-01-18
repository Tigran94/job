<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Hello To JOB.AM


<%--<form action="/signUp" method="post">--%>
    <%--<input type="submit" value="Sign Up">--%>
    <%--&lt;%&ndash;<label>&ndash;%&gt;--%>
        <%--&lt;%&ndash;Username: <input type="text" name="username"/>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</label>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<label>&ndash;%&gt;--%>
        <%--&lt;%&ndash;Password: <input type="password" name="password"/>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</label>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<input type="submit" value="Login"/>&ndash;%&gt;--%>
<%--</form>--%>
<form action="/login" method="post">
    <label>
        Username: <input type="text" name="username" required/>
    </label>
    <label>
        Password: <input type="password" name="password" required/>
    </label>
    <input type="submit" value="Login"/>
</form>
<form action="/signup" method="get">
    <input type="submit" value="Sign Up">
</form>
<form action="/guest" method="get">
    <input type="submit" value="Guest">
</form>
${loginConfirmedMain}

</body>
</html>

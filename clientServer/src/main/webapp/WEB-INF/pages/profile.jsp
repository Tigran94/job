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
            background-color: #19504b;
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
            display: flex;
            justify-content: space-between;
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
            display: flex;
            margin-top: 62px;
        }
        .titles {
            background-color: #19504b;
            width: 20%;
            height: 100vh;
            position: fixed;
        }
        .titles ul li{
            list-style-type: none;
            border-bottom: 2px solid #ced2d4;
        }
        .titles ul li a{
            text-transform: capitalize;
            text-decoration: none;
            color: #a9c1bf;
            padding: 5px 10px;
            line-height: 28px;
            display: block;
            width: 100%;
            font-weight: bold;
            word-wrap: break-word;
            /* text-align: left; */
            font-size: 18px;
        }
        .titles li:hover{
            background-color: #1a3131;
            cursor: pointer;
        }
        .titles li:hover a{
            color: #ced2d4;
        }
        .content {
            background-color: #ced2d4;
            width: 80%;
            height: 100vh;
            padding: 5px;
            margin-left: 20%;
        }
        .formdiv{
            display: flex;
            justify-content: space-between;
            width: 100%;
            padding: 5px;
            max-height: 40px;
        }
        .vals input, select{
            background-color: #ced2d4;
            padding: 3px;
            outline: none;
        }
        .formdiv input, select{
            width: 100px;
        }
        .labels{
            color: #ced2d4;
            width: 50%;
        }
        #salarytype{
            width: 100px;
        }
        .vals{
            width: 50%;
        }
        #filter{
            padding: 5px 10px;
            color: #ced2d4;
            background-color: #3e8484;
            text-transform: uppercase;
            border: none;
            border-radius: 6px;
            text-decoration: none;
            font-size: 14px;
        }
        #filter:hover{
            background-color: #265353;
            cursor: pointer;
            border: 1px solid #ced2d4;
            padding: 4px 10px;
        }
        .tablerow{
            display: flex;
            padding: 5px;
            width: 40%;
            justify-content: space-between;
        }
        #form{
            background-color: #19504b;
            box-shadow: 0px 2px 2px #000;
        }
        #list{
            overflow-y: auto;
            margin-top: 5px;
            height: 100%;
        }
        #list li{
            display: flex;
        }
        .left{
            font-weight: bold;
        }
        ::-webkit-scrollbar {
            width: 10px;
            border-radius: 10px;
        }
        ::-webkit-scrollbar-thumb {
            background: #1f4545;
            border-radius: 10px;
            border: 2px solid #ced2d4;
        }
        ::-webkit-scrollbar-track {
            box-shadow: inset 0 0 5px #ced2d4;
            border-radius: 10px;
            margin-left: 5px;
        }
        .pst{
            color: #ced2d4;
            text-transform: uppercase;
            font-size: 25px;
            text-align: center;
            padding-top: 5px;
            box-shadow: 0px 2px 2px #000;
        }
        .derow{
            width: 100%;
            padding-left: 10px;
            display: flex;
        }
        .deleft{
            width: 20px;
            padding-top: 9px;
        }
        .deleft input[type="image"]{
            width: 100%;
        }
    </style>
</head>
<body>
<div class="header">
    <div>
        <span>My Profile</span>
        <a href="/">Back</a>
        <a href="/addPost">Add Post</a>
    </div>
    <div>
        <a href ="/settings">Settings</a>
        <a href="/profile/">Log Out</a>
    </div>
</div>
<div class="main">
    <div class="titles">
        <form id="form" action="/profile" method="post">
            <div class="formdiv">
                <div class="labels">
                    <label for="typename">Type: </label>
                </div>
                <div class="vals">
                    <select name="type" id="typename">
                        <option></option>
                        <option value="IT">IT</option>
                        <option value="Marketing">Marketing</option>
                    </select>
                </div>
            </div>
            <div class="formdiv">
                <div class="labels">
                    <label for="salarytype">Salary: </label>
                </div>
                <div class="vals">
                    <input id="salarytype" type="number" name="salary"/>
                </div>
            </div>
            <div class="formdiv">
                <div class="labels">
                    <label for="worktime">Work Time: </label>
                </div>
                <div class="vals">
                    <select id="worktime" name="workTime">
                        <option></option>
                        <option value="Half Time">Half Time</option>
                        <option value="Full Time">Full Time</option>
                        <option value="Remote">Remote</option>
                    </select>
                </div>
            </div>
            <div class="formdiv">
                <input id="filter" type="submit" value="Filter"/>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        </form>
        <%--<div class="mrg">--%>
            <%--<ul id="list">--%>
                <%--<c:forEach var="jobTitle" items="${jobTitles}">--%>
                    <%--<li>--%>
                        <%--<a href="/profile/${jobTitle.id}">${jobTitle.title}</a>--%>
                    <%--</li>--%>
                <%--</c:forEach>--%>
            <%--</ul>--%>
        <%--</div>--%>
        <div>
            <div class="pst">
                <p>My Posts</p>
            </div>
                <ul id="list">
                <c:forEach var="jobTitle" items="${jobTitles}">
                    <form action="/profile/del" method="post">
                    <li>
                        <div class="derow">
                            <div class="deleft">
                                <input type="hidden" name="jobId" value="${jobTitle.id}">
                                <input type="image" src="/resources/del.png">
                            </div>
                            <div class="deright">
                               <a href="/profile/${jobTitle.id}">${jobTitle.title}</a>
                            </div>
                        </div>
                  </li>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    </form>
                </c:forEach>
            </ul>

        </div>
    </div>
    <div class="content">
        <div class="tablerow"  >
            <div class="left" >
                <p>Title: </p>
            </div>
            <div class="right">
                <p>${post.title}</p>
            </div>
        </div>
        <div class="tablerow">
            <div class="left" >
                <p>Description: </p>
            </div>
            <div class="right">
                <p>${post.description}</p>
            </div>
        </div>
        <div class="tablerow">
            <div class="left" >
                <p>Type: </p>
            </div>
            <div class="right">
                <p>${post.type}</p>
            </div>
        </div>
        <div class="tablerow">
            <div class="left" >
                <p>Salary: </p>
            </div>
            <div class="right">
                <p>${post.salary}</p>
            </div>
        </div>
        <div class="tablerow">
            <div class="left" >
                <p>Work Time: </p>
            </div>
            <div class="right">
                <p>${post.workTime}</p>
            </div>
        </div>
        <div class="tablerow">
            <div class="left" >
                <p>Email: </p>
            </div>
            <div class="right">
                <p>${post.email}</p>
            </div>
        </div>
        <div class="tablerow">
            <div class="left" >
                <p>Company: </p>
            </div>
            <div class="right">
                <p>${post.company}</p>
            </div>
        </div>
        <div class="tablerow">
            <div class="left" >
                <p>Expiration Date: </p>
            </div>
            <div class="right">
                <p>${post.expirationDate}</p>
            </div>
        </div>
        <div class="tablerow">
            <div class="left" >
                <p>Post Date: </p>
            </div>
            <div class="right">
                <p>${post.postDate}</p>
            </div>
        </div>
    </div>
</div>



</body>
</html>

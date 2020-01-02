<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>技能树 | IT修真院</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/css/Untitled-3.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/css/Untitled-1base.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/css/login.css" rel="stylesheet" type="text/css">

</head>

<body>
<div style="text-align: center">
    <h1>登录</h1>
    <form action="${pageContext.request.contextPath}/login/0" name="user" method="POST">
        <div>用户名/手机号/邮箱:<input type="text" name="name"></div>
        <div>密码:<input type="password" name="password"></div>
        <input type="submit" value="登录">
    </form>
</div>
</body>
</html>
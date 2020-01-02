<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>技能树 | IT修真院</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/css/Untitled-3.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/css/Untitled-1base.css" rel="stylesheet" type="text/css">


</head>
<body>
<div>
    <h3>
        <a href="${pageContext.request.contextPath}/register" style="color: blue">切换到邮箱验证码注册</a>
    </h3>

</div>
<div style="text-align: center">

    <h1>注册</h1>
    <form action="${pageContext.request.contextPath}/register/1" name="user" method="post"
          enctype="multipart/form-data">
        <ul class="reg_form" id="reg-ul">
            <div id="userCue" class="cue">欢迎！</div>

            <li>
                <label for="name" class="input-tips2">用户名：</label>
                <div class="inputOuter2">
                    <input type="text" id="name" name="name" maxlength="16" class="inputstyle2"/>
                </div>
            </li>

            <li>
                <label for="password" class="input-tips2">密码：</label>
                <div class="inputOuter2">
                    <input type="password" id="password" name="password" maxlength="16" class="inputstyle2"/>
                </div>
            </li>

            <li>
                <label for="phone" class="input-tips2">手机号：</label>
                <div class="inputOuter2">
                    <input type="text" id="phone" name="phone" maxlength="11" class="inputstyle2"/>
                </div>
            </li>

            <li>
                <label for="code" class="input-tips2">验证码：</label>
                <div class="inputOuter2">
                    <input type="text" id="code" name="code" maxlength="10" class="inputstyle2"/>
                    <input class="message get-verify btn-green" id="getCode" type="button" value="短信获取"
                           onclick="sendCode()">
                </div>

            </li>
            <li>
                <label for="multipartFile" class="input-tips2">图片：</label>
                <div class="inputOuter2" style="text-align: center">
                    <input type="file" id="multipartFile" name="multipartFile" maxlength="10"
                           class="inputstyle2"/>
                </div>
            </li>

            <li>
                <input type="submit" style="margin-top:5px;margin-left:85px;" class="button_blue"
                       value="同意协议并注册"/> <a href="#" class="zcxy" target="_blank">注册协议</a>
            </li>
        </ul>
    </form>

</div>
</body>
</html>
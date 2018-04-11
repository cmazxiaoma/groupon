<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>注册</title>
    <link rel="icon" href="images/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon"/>
    <link rel="bookmark" href="images/favicon.ico" type="image/x-icon"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/style/reset.css">
    <link type="text/css" rel="stylesheet" href="${ctx}/style/main.css">
    <!--[if IE 6]>
    <script type="text/javascript" src="${ctx}/js/DD_belatedPNG_0.0.8a-min.js"></script>
    <script type="text/javascript" src="${ctx}/js/ie6Fixpng.js"></script>
    <![endif]-->
</head>

<body>
<div class="headerBar">
    <div class="logoBar red_logo">
        <div class="comWidth">
            <div class="logo fl">
                <a href="#"><img src="${ctx}/images/logo.jpg" alt="慕课网"></a>
            </div>
            <h3 class="welcome_title">欢迎注册</h3>
        </div>
    </div>
</div>

<div class="regBox">
    <div class="login_cont">
        <ul class="login">
            <form action="/register" method="post">
                <li><span class="reg_item"><i>*</i>用户名：</span>
                    <div class="input_item"><input type="text" name="username" class="login_input user_icon"></div>
                </li>
                <li><span class="reg_item"><i>*</i>密码：</span>
                    <div class="input_item"><input type="password" name="password1" class="login_input user_icon"></div>
                </li>
                <li><span class="reg_item"><i>*</i>确认密码：</span>
                    <div class="input_item"><input type="text" name="password2" class="login_input user_icon"></div>
                </li>
                <li class="autoLogin"><span class="reg_item">&nbsp;</span><input type="checkbox" id="t1"
                                                                                 class="checked"><label for="t1">我同意什么什么条款</label>
                </li>
                <li><span class="reg_item">&nbsp;</span><input type="submit" value="" class="login_btn"></li>
            </form>
        </ul>
    </div>
</div>
<#include "layout/footer.ftl">
</body>
</html>

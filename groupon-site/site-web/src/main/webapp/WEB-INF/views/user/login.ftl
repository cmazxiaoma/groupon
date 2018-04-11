<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>登陆</title>
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
    <div class="logoBar login_logo">
        <div class="comWidth">
            <div class="logo fl">
                <a href="#"><img src="${ctx}/images/logo.jpg" alt="慕课网"></a>
            </div>
            <h3 class="welcome_title">欢迎登陆</h3>
        </div>
    </div>
</div>

<div class="loginBox">
    <div class="login_cont">
        <ul class="login">
            <form action="/dologin" method="post">
                <li class="l_tit">邮箱/用户名/手机号</li>
                <li class="mb_10"><input type="text" name="name" class="login_input user_icon"></li>
                <li class="l_tit">密码</li>
                <li class="mb_10"><input type="password" name="password" class="login_input user_icon"></li>
                <li class="autoLogin"><input type="checkbox" id="a1" class="checked"><label for="a1">自动登陆</label></li>
                <li><input type="submit" value="" class="login_btn"></li>
            </form>
        </ul>
        <div class="login_partners">
            <p class="l_tit">使用合作方账号登陆网站</p>
            <ul class="login_list clearfix">
                <li><a href="#">QQ</a></li>
                <li><span>|</span></li>
                <li><a href="#">网易</a></li>
                <li><span>|</span></li>
                <li><a href="#">新浪微博</a></li>
                <li><span>|</span></li>
                <li><a href="#">腾讯微薄</a></li>
                <li><span>|</span></li>
                <li><a href="#">新浪微博</a></li>
                <li><span>|</span></li>
                <li><a href="#">腾讯微薄</a></li>
            </ul>
        </div>
    </div>
    <a class="reg_link" href="${ctx}/reg"></a>
</div>
<#include "layout/footer.ftl">
</body>
</html>

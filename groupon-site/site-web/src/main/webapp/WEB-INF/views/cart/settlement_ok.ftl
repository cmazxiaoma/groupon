<#import "macro/common.ftl" as common>
<html>
<head>
    <meta charset="utf-8">
    <title>下单成功</title>
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
<#include "layout/topbar.ftl">
    <div class="logoBar">
        <div class="comWidth">
            <div class="logo fl">
                <a href="${ctx}"><img src="${ctx}/images/logo.jpg" alt="慕课网"></a>
            </div>
            <div class="stepBox fr">
                <div class="step"></div>
                <ul class="step_text">
                    <li class="s01 active">提交订单</li>
                    <li class="s02 active">确认订单</li>
                    <li class="s03 active">购买成功</li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="shoppingCart comWidth">
    <div class="hr_45"></div>
    <div style="text-align: center;">
    <#if result == 1>
        <span style="font-size: 30px;">付款成功！</span>
    <#else>
        <span style="font-size: 30px;">付款失败!</span>
    </#if>
    </div>
    <div class="hr_45"></div>
    <div class="hr_15"></div>
    <div style="text-align: center;">
        <a href="${ctx}/cart" class="settlement_back_btn">返回购物车</a>
        <a href="${ctx}" class="settlement_back_btn">继续购物</a>
        <a href="${ctx}/home/order" class="settlement_back_btn">我的订单</a>
    </div>
    <div class="hr_45"></div>
</div>
<#include "layout/footer.ftl">
</body>
</html>

<#import "macro/common.ftl" as common>
<html>
<head>
    <meta charset="utf-8">
    <title>商品结算</title>
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
                <a href="${ctx}"><img src="${ctx}/images/logo.jpg" alt="DCAMPUS"></a>
            </div>
            <div class="stepBox fr">
                <div class="step"></div>
                <ul class="step_text">
                    <li class="s01 active">提交订单</li>
                    <li class="s02 active">确认订单</li>
                    <li class="s03">购买成功</li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="shoppingCart comWidth">
<#--<form action="${ctx}/settlement/confirm" method="post">-->
<#--<form action="${ctx}/alipay" method="post">-->
    <form action="${ctx}/pay" method="post">
        <div class="shopping_item">
            <h3 class="shopping_tit">送货清单<a href="${ctx}/cart" class="backCar">返回购物车修改</a></h3>
            <div class="shopping_cont pb_10">
                <div class="cart_inner">
                    <div class="cart_head clearfix">
                        <div class="cart_item t_name">商品名称</div>
                        <div class="cart_item t_price">单价</div>
                        <div class="cart_item t_num">数量</div>
                        <div class="cart_item t_return">折扣</div>
                        <div class="cart_item t_subtotal">小计</div>
                        <div class="cart_item t_num">&nbsp;</div>
                    </div>
                <#if carts??>
                    <#list carts as cart>
                        <input type="hidden" name="cartIds" value="${cart.cart.id}">
                        <input type="hidden" name="skuId" value="${cart.cart.dealSkuId}">
                        <div class="cart_cont clearfix">
                            <div class="cart_item t_name">
                                <div class="cart_shopInfo clearfix">
                                    <img src="${helper.getDealImageUrlForDealList(cart.deal)}" alt="">
                                    <div class="cart_shopInfo_cont">
                                        <p class="cart_link"><a href="${ctx}/item/${cart.deal.skuId}">${cart.deal.dealTitle}</a></p>
                                        <p class="cart_info">7天退换</p>
                                    </div>
                                </div>
                            </div>
                            <div class="cart_item t_price">
                                <@common.formatPrice cart.deal.dealPrice/>
                            </div>
                            <div class="cart_item t_num">${cart.cart.count}</div>
                            <div class="cart_item t_return">无</div>
                            <div class="cart_item t_subtotal t_red"><@common.formatPrice cart.subtotal/></div>
                        </div>
                    </#list>
                </#if>
                <#--<div class="cart_message">-->
                <#--若有问题请留言，若有问题请留言-->
                <#--</div>-->
                <#--<div class="cart_prompt"><i class="cart_prompt_icon"></i>抱歉，一下商品无法购买。</div>-->
                <#--<div class="cart_cont cart_no_bor clearfix">-->
                <#--<div class="cart_item t_name">-->
                <#--<div class="cart_shopInfo clearfix">-->
                <#--<img src="${ctx}/images/des_sm.jpg" alt="">-->
                <#--<div class="cart_shopInfo_cont">-->
                <#--<p class="cart_link"><a href="#">微信支付微信支付微信支付微</a></p>-->
                <#--<p class="cart_info">微信支付微信支付微信支付微</p>-->
                <#--</div>-->
                <#--</div>-->
                <#--</div>-->
                <#--</div>-->
                </div>
            </div>
        </div>
        <div class="hr_25"></div>
        <div class="shopping_item">
            <h3 class="shopping_tit">选择收货地址</h3>
        <#--<div class="shopping_cont padding_shop">-->
        <#--<ul class="shopping_list">-->
        <#--<li><span class="shopping_list_text"><em>*</em>选择地区：</span>-->
        <#--<div class="select">-->
        <#--<h3>海淀区五环内</h3><span></span>-->
        <#--<ul class="show_select">-->
        <#--<li>上帝</li>-->
        <#--<li>五道口</li>-->
        <#--<li>上帝</li>-->
        <#--</ul>-->
        <#--</div>-->
        <#--</li>-->
        <#--<li><span class="shopping_list_text"><em>*</em>详细地址：</span><input type="text" value="" placeholder="最多输入20个汉字" class="input input_b"></li>-->
        <#--<li><span class="shopping_list_text"><em>*</em>收 货 人：</span><input type="text" value="" placeholder="最多10个" class="input"></li>-->
        <#--<li><span class="shopping_list_text"><em>*</em>联系电话：</span><input type="text" value="" class="input"></li>-->
        <#--<li><input type="button" class="affirm"></li>-->
        <#--</ul>-->
        <#--</div>-->

            <!--表格-->
            <table class="table" cellspacing="0" cellpadding="0">
                <thead>
                <tr>
                    <th width="3%"></th>
                    <th width="10%">收货人</th>
                    <th width="25%">地区</th>
                    <th width="45%">详细地址</th>
                    <th width="12%">手机</th>
                </tr>
                </thead>
                <tbody>
                <#if addresses??>
                    <#list addresses as address>
                    <tr>
                        <td><input type="radio" name="addressId" value="${address.id}"></td>
                        <td>${address.receiver}</td>
                        <td>${address.area}</td>
                        <td>${address.detail}</td>
                        <td>${address.phone}</td>
                    </tr>
                    </#list>
                </#if>
                </tbody>
            </table>
        </div>
        <div class="hr_25"></div>
        <div class="shopping_item">
            <h3 class="shopping_tit">支付方式</h3>
            <div class="shopping_cont padding_shop">
                <ul class="shopping_list">
                    <li><input type="radio" name="payType" value="1" class="radio" id="r1"><label for="r1">微信支付</label>
                    </li>
                    <li><input type="radio" name="payType" value="2" class="radio" id="r2"><label for="r2">支付宝支付</label>
                    </li>
                    <li><input type="radio" name="payType" value="3" class="radio" id="r3"><label for="r3">货到付款</label>
                    </li>
                    <li><input type="radio" name="payType" value="4" class="radio" id="r3"><label for="r3">易宝支付</label>
                    </li>
                </ul>
            </div>
        </div>
        <div class="hr_25"></div>
    <#--<div class="shopping_item">-->
    <#--<h3 class="shopping_tit">订单结算</h3>-->
        <div class="shopping_cont padding_shop clearfix">
            <div class="cart_count fr">
                <div class="cart_rmb">
                    <i>总计：</i><span><@common.formatPrice totalPrice/></span>
                </div>
                <input type="hidden" name="totalPrice" value="${totalPrice}">
                <div class="settlement_btnBox">
                    <input type="submit" class="settlement_btn" value="确认购买">
                </div>
            </div>
        </div>
    <#--</div>-->
    <#--</div>-->
    </form>
<#include "layout/footer.ftl">
</body>
</html>

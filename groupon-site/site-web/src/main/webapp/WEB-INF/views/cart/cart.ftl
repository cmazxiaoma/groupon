<#import "macro/common.ftl" as common>
<html>
<head>
    <meta charset="utf-8">
    <title>购物车</title>
    <link rel="icon" href="images/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon"/>
    <link rel="bookmark" href="images/favicon.ico" type="image/x-icon"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/style/reset.css">
    <link type="text/css" rel="stylesheet" href="${ctx}/style/main.css">
    <!--[if IE 6]>
    <script type="text/javascript" src="${ctx}/js/DD_belatedPNG_0.0.8a-min.js"></script>
    <script type="text/javascript" src="${ctx}/js/ie6Fixpng.js"></script>
    <![endif]-->

    <script type="text/javascript" src="${ctx}/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/site.js"></script>

    <script type="text/javascript">
        var ctx = '${ctx}';

        //勾选框事件
        function check(object, cartId) {
            if (object.checked) {
                console.log("勾选");
            } else {
                console.log("取消勾选");
            }
            var val = Number(0);//声明一个数组保存结果
            var chk = document.getElementsByTagName("input");
            var cartIds = '';

            for (var i = 0; i < chk.length; i++) {
                if (chk[i].type == 'checkbox' && chk[i].checked) {
                    val += Number(document.getElementById('subtotal_' + chk[i].id).value);
                    cartIds += chk[i].id + ',';
                }
            }
            var totalSpan = document.getElementsByTagName("span");
            totalSpan[0].innerHTML = '￥' + (val / 100).toFixed(2);

            document.getElementById("totalPrice").value = val;
            document.getElementById("cartIds").value = cartIds.substring(0, cartIds.length - 1);
        }

        function dealCount(type, cartId, maxPurchaseCount) {
            var id = 'count_' + cartId;
            if (type == 1) {
                var count = Number(document.getElementById(id).value);
                if (count >= maxPurchaseCount) {
                    document.getElementById(id).value = maxPurchaseCount;
                } else {
                    document.getElementById(id).value = count + 1;
                    Submit.AjaxSubmit1(ctx + '/cart/' + cartId + '/1', "", "post",
                    function(result) {
                        var newResult = result / 100;
                        if (newResult > 0) {
                            newResult = newResult + ".00";
                        }
                       console.log(newResult);
                       console.log($("#subtotal_" + cartId));
                       //更新当前cart的总价
                       $("#subtotal_" + cartId).val(newResult);
                       $("#cart_item_t_subtotal_" + cartId).text(newResult);
                       //更新所有cart的总价
                        check(this, cartId);
                    },
                    function(result) {
                        console.log(result);
                    });
                }
            } else {
                var count = Number(document.getElementById(id).value);
                if (count <= 1) {
                    document.getElementById(id).value = 1;
                } else {
                    document.getElementById(id).value = count - 1;
                    Submit.AjaxSubmit1(ctx + '/cart/' + cartId + '/0', "", "post",
                            function(result) {
                                var newResult = result / 100;
                                if (newResult > 0) {
                                    newResult = newResult + ".00";
                                }
                                console.log(newResult);
                                $("#subtotal_" + cartId).val(newResult);
                                $("#cart_item_t_subtotal_" + cartId).text(newResult);
                                //更新所有cart的总价
                                check(this, cartId);
                            },
                            function(result) {
                                console.log(result);
                            });
                }
            }
        }

    </script>
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
                    <li class="s02">确认订单</li>
                    <li class="s03">购买成功</li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="shoppingCart comWidth">
    <div class="shopping_item">
        <h3 class="shopping_tit">购物车</h3>
        <div class="shopping_cont pb_10">
            <div class="cart_inner">
                <div class="cart_head clearfix">
                    <div class="cart_item t_name">商品名称</div>
                    <div class="cart_item t_price">单价</div>
                    <div class="cart_item t_num">数量</div>
                    <div class="cart_item t_return">折扣</div>
                    <div class="cart_item t_subtotal">小计</div>
                    <div class="cart_item t_status">状态</div>
                    <div class="cart_item t_operate">操作</div>
                </div>
            <#if carts??>
                <#list carts as cart>
                    <div class="cart_cont clearfix">
                        <div class="cart_item t_name">
                            <div class="cart_shopInfo clearfix">
                                <div>
                                    <input type="checkbox" id="${cart.cart.id}" onclick="check(this, ${cart.cart.id})"
                                           style="float:left; margin-top:40px; margin-right: 10px">
                                    <img src="${helper.getDealImageUrlForDealList(cart.deal)}" alt="">
                                    <div class="cart_shopInfo_cont">
                                        <p class="cart_link"><a
                                                href="${ctx}/item/${cart.deal.skuId}">${cart.deal.dealTitle}</a></p>
                                        <p class="cart_info">7天退换</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cart_item t_price">
                            <@common.formatPrice cart.deal.dealPrice/>
                        </div>
                        <div class="cart_item t_num">
                            <div class="des_number">
                                <div class="reduction"
                                     onclick="dealCount(0, '${cart.cart.id}', '${cart.deal.maxPurchaseCount}')">-
                                </div>
                                <div class="des_input">
                                    <input type="text" id="count_${cart.cart.id}" name="count"
                                           value="${cart.cart.count}" readonly>
                                </div>
                                <div class="plus"
                                     onclick="dealCount(1, '${cart.cart.id}', '${cart.deal.maxPurchaseCount}')">+
                                </div>
                            </div>
                        </div>
                        <div class="cart_item t_return"><@common.formatDiscount cart.deal.discount/></div>
                        <div class="cart_item t_subtotal t_red" id="cart_item_t_subtotal_${cart.cart.id}">
                            <input id="subtotal_${cart.cart.id}" type="hidden" value="${cart.subtotal}" />
                            <@common.formatPrice cart.subtotal/>
                        </div>
                        <div class="cart_item t_status">
                            <#if cart.deal.start>
                                进行中
                            <#elseif cart.deal.end>
                                已结束
                            <#else>
                                未开始
                            </#if>
                        </div>
                        <div class="cart_item t_operate"><a href="javascript:void(0)" class="btn_link">删除</a></div>
                    </div>
                </#list>
            </#if>
                <div class="cart_message">
                    若有问题请留言
                </div>
            </div>
        </div>
    </div>

    <div class="hr_25"></div>
    <div class="shopping_item">
    <h3 class="shopping_tit">订单结算</h3>
        <div class="shopping_cont padding_shop clearfix">
            <div class="cart_count fr">
                <div class="cart_rmb">
                    <i>总计：</i><span>￥0.00</span>
                </div>
                <div class="settlement_btnBox">
                    <form action="${ctx}/settlement" method="post" id="settlement_form">
                        <input type="hidden" id="totalPrice" name="totalPrice" value="0.00">
                        <input type="hidden" id="cartIds" name="cartIds">
                        <input type="submit" class="settlement_btn" value="提交订单">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "layout/footer.ftl">
</body>
</html>

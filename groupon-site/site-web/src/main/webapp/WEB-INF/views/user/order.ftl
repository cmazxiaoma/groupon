<#import "macro/order.ftl" as order_macro>
<#import "macro/common.ftl" as common>
<html>
<head>
    <title>我的订单</title>
    <script type="text/javascript">
        var ctx = '${ctx}';
        function pay(orderId) {
        }

        function del(orderId) {
            Submit.AjaxSubmit1(ctx + "/order/del/" + orderId, "", "post", function(successResult) {
                console.log(successResult);
                window.location.href = ctx + "/home/order";
            }, function(failResult) {
                console.log(failResult);
            });
        }

        function cancel(orderId) {
            Submit.AjaxSubmit1(ctx + "/order/cancel/" + orderId, "", "post", function(successResult) {
                console.log(successResult);
                window.location.href = ctx + "/home/order";
            }, function(failResult) {
                console.log(failResult);
            });
        }
    </script>
</head>
<body>
<div class="hr_15"></div>
<div class="comWidth clearfix products">
    <div class="leftArea">
    <#include "user/left_menu.ftl">
    </div>
    <div class="rightArea">
        <h3 class="shopping_tit">我的订单</h3>
    <#if orders??>
        <#list orders as order>
            <h3 class="shopping_item">
                <@common.formatDateTime order.createTime/>&nbsp;&nbsp;&nbsp;
                订单号：${order.id}&nbsp;&nbsp;&nbsp;
                收货人：${order.receiver}&nbsp;&nbsp;&nbsp;
                订单金额：<span style="color: #FF7300;"><@common.formatPrice order.totalPrice/>&nbsp;&nbsp;&nbsp;</span>
                支付方式：<@order_macro.getOrderPayTypeName order.payType/>&nbsp;&nbsp;&nbsp;
                订单状态：<span style="<@order_macro.getOrderStatusClass order.orderStatus/>">
                    <@order_macro.getOrderStatusName order.orderStatus/>
                    </span>&nbsp;&nbsp;&nbsp;
                <#if order.orderStatus??>
                    <#switch order.orderStatus>
                        <#case -1>
                            <#assign orderStatusName="已删除">
                            <#break>
                        <#case 1>
                            <#assign orderStatusClass="color:green;">
                            <#assign orderStatusName="待付款">
                            <#if order.payType == 1 || order.payType == 2>
                                <a href="javascript:pay(${order.id})" class="order_btn">去支付</a>
                            </#if>

                            <#if order.payType == 3>
                            </#if>
                            <a href="javascript:cancel(${order.id})" class="order_btn">取消</a>
                            <#break>
                        <#case 2>
                            <#assign orderStatusClass="color:green;">
                            <#assign orderStatusName="已付款">
                            <#break>
                        <#case 3>
                            <#assign orderStatusClass="font_green">
                            <#assign orderStatusName="待发货">
                            <#break>
                        <#case 4>
                            <#assign orderStatusClass="color:gray;">
                            <#assign orderStatusName="配送中">
                            <#break>
                        <#case 5>
                            <#assign orderStatusClass="color:gray;">
                            <#assign orderStatusName="完成">
                            <a href="javascript:del(${order.id})" class="order_btn">删除</a>
                            <#break>
                        <#case 6>
                            <#assign orderStatusClass="color:red;">
                            <#assign orderStatusName="已取消">
                            <#break>
                        <#default>
                            <#assign orderStatusClass="color:red;">
                            <#assign orderStatusName="未知订单状态">
                    </#switch>
                </#if>
            </h3>

            <#list order.orderDetails as detail>
                <div class="cart_cont clearfix">
                    <div class="cart_item t_name">
                        <div class="cart_shopInfo clearfix">
                            <div>
                            <#--<input type="checkbox" id="${order.id}" onclick="check(this, ${order.id})" style="float:left; margin-top:40px; margin-right: 10px">-->
                                <img src="${helper.getDealImageUrlForGrid(detail.dealImgId)}" alt="">
                                <div class="cart_shopInfo_cont">
                                    <p class="cart_link"><a href="${ctx}/item/${detail.dealSkuId}">${detail.dealTitle}</a></p>
                                    <p class="cart_info">7天退换</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cart_item t_num">
                        x${detail.dealCount}
                    </div>
                    <div class="cart_item t_return"></div>
                    <div class="cart_item t_status">
                    </div>
                    <div class="cart_item t_operate" style="margin-left: 65px">
                        <a href="${ctx}/settlement/${detail.dealSkuId}" class="btn_link" onclick="">再次购买&nbsp;&nbsp;</a>
                        <a href="${ctx}/item/${detail.dealSkuId}" class="btn_link" onclick="" style="display: inline-block">查看&nbsp;&nbsp;</a>
                    </div>
                </div>
            </#list>
            <div class="hr_25"></div>
        </#list>
    </#if>
    </div>
</div>
</body>
</html>


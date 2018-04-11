<#import "macro/paging.ftl" as paging_macro>
<#import "macro/common.ftl" as common>
<html>
<head>
    <title>收货地址</title>
</head>
<body>
<div class="hr_15"></div>
<div class="comWidth clearfix products">
    <div class="leftArea">
    <#include "user/left_menu.ftl">
    </div>
    <div class="rightArea">
        <h3 class="shopping_tit">收货地址</h3>
        <div class="btn_link">
            <a class="btn" href="${ctx}/home/address/new">新增收货地址</a>
        </div>
        <!--表格-->
        <table class="table" cellspacing="0" cellpadding="0">
            <thead>
            <tr>
                <th width="10%">收货人</th>
                <th width="30%">地区</th>
                <th width="40%">详细地址</th>
                <th width="15%">手机</th>
            </tr>
            </thead>
            <tbody>
            <#if addresses??>
                <#list addresses as address>
                <tr>
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
</div>
</body>
</html>


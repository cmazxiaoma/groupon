<#--订单类型-->
<#macro getOrderTypeName type>
    <#switch type>
        <#case 0>
            <#assign orderTypeName="订单">
            <#break>
        <#case 1>
            <#assign orderTypeName="订单">
            <#break>
        <#default>
            <#assign orderTypeName="其他订单">
    </#switch>
${orderTypeName}
</#macro>

<#--支付类型-->
<#macro getOrderPayTypeName payType>
    <#switch payType>
        <#case 1>
            <#assign orderTypeName="微信">
            <#break>
        <#case 2>
            <#assign orderTypeName="支付宝">
            <#break>
        <#case 3>
            <#assign orderTypeName="货到付款">
            <#break>
        <#default>
            <#assign orderTypeName="未知的支付类型">
    </#switch>
${orderTypeName}
</#macro>

<#--订单状态-->
<#macro getOrderStatusName status>
    <#switch status>
        <#case 1>
            <#assign orderStatusClass="font_green">
            <#assign orderStatusName="待付款">
            <#break>
        <#case 2>
            <#assign orderStatusClass="font_green">
            <#assign orderStatusName="已付款">
            <#break>
        <#case 3>
            <#assign orderStatusClass="font_green">
            <#assign orderStatusName="待发货">
            <#break>
        <#case 4>
            <#assign orderStatusClass="font_gray">
            <#assign orderStatusName="配送中">
            <#break>
        <#case 5>
            <#assign orderStatusClass="font_gray">
            <#assign orderStatusName="完成">
            <#break>
        <#case 6>
            <#assign orderStatusClass="font_red">
            <#assign orderStatusName="已取消">
            <#break>
        <#default>
            <#assign orderStatusClass="font_red">
            <#assign orderStatusName="未知订单状态">
    </#switch>
${orderStatusName}
</#macro>
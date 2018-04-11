<#--格式化价格-->
<#macro formatPrice price>
    <#if price??>
    ¥${(price / 100)?string("0.00")}
    </#if>
</#macro>

<#--格式化折扣-->
<#macro formatDiscount discount>
    <#if discount??>
        <#if (discount = 100)>
        无
        <#else>
        ${discount / 100}折

        </#if>
    </#if>
</#macro>

<#--格式化消息状态-->
<#macro formatMessageStatus readed>
    <#if readed??>
        <#if (readed = 0)>
        未读
        <#elseif (readed = 1)>
        已读
        <#else>
        未知
        </#if>
    </#if>
</#macro>

<#--格式化在商品列表页面显示的折扣-->
<#macro formatDiscountForDealList deal>
    <#if deal?? && deal.dealPrice && deal.marketPrice && deal.marketPrice != 0>
    (${(deal.dealPrice / deal.marketPrice * 10)?string("0.0")}折)
    </#if>
</#macro>

<#--格式化年、月、日、时、分、秒-->
<#macro formatDateTime dateTime><#if dateTime?? && dateTime?string("yyyy-MM-dd HH:mm:ss") != "1970-01-01 00:00:00">${dateTime?string("yyyy-MM-dd HH:mm:ss")}</#if></#macro>

<#--格式化年、月、日-->
<#macro formatDate date><#if date?? && date?string("yyyy-MM-dd") != "1970-01-01">${date?string("yyyy-MM-dd")}</#if></#macro>

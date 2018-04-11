<#macro generateOrderStatusSelect name defaultValue>
<#--1:待付款;2:已付款;3:待发货;4:配送中;5:完成;6:已取消;7:已关闭-->
<select class='easyui-combobox' name=${name}>
    <option value=''>全部</option>
    <option value='1' <#if (defaultValue == 1)>selected='selected'</#if>>待付款</option>
    <option value='2' <#if (defaultValue == 2)>selected='selected'</#if>>已付款</option>
    <option value='3' <#if (defaultValue == 3)>selected='selected'</#if>>待发货</option>
    <option value='4' <#if (defaultValue == 4)>selected='selected'</#if>>配送中</option>
    <option value='5' <#if (defaultValue == 5)>selected='selected'</#if>>完成</option>
    <option value='6' <#if (defaultValue == 6)>selected='selected'</#if>>已取消</option>
    <option value='7' <#if (defaultValue == 7)>selected='selected'</#if>>已关闭</option>
</select>
</#macro>

<#macro generateOrderTypeSelect name defaultValue>
<select class='easyui-combobox' name=${name}>
    <option value=''>全部</option>
    <option value='0' <#if (defaultValue == 0)>selected='selected'</#if>>实物订单</option>
    <option value='1' <#if (defaultValue == 1)>selected='selected'</#if>>虚拟订单</option>
</select>
</#macro>
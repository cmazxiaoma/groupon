<#--商品类型-->
<#macro getProductType type>
    <#switch type>
        <#case 0>
            <#assign productType="商品">
            <#break>
        <#case 1>
            <#assign productType="商品">
            <#break>
        <#default>
            <#assign productType="其他商品">
    </#switch>
${productType}
</#macro>

<#--商家类型-->
<#macro getMerchantType type>
    <#switch type>
        <#case 1>
            <#assign merchantType="易迅">
            <#break>
        <#case 2>
            <#assign merchantType="易金商">
            <#break>
        <#case 3>
            <#assign merchantType="当当">
            <#break>
        <#case 4>
            <#assign merchantType="我买网">
            <#break>
        <#case 5>
            <#assign merchantType="京东">
            <#break>
        <#default>
            <#assign merchantType="其他">
    </#switch>
${merchantType}
</#macro>

<#--商品状态-->
<#macro getProductStatus status>
    <#switch status>
        <#case 0>
            <#assign publishStatus="新建">
            <#break>
        <#case 1>
            <#assign publishStatus="已审核">
            <#break>
        <#case 2>
            <#assign publishStatus="已发布不显示">
            <#break>
        <#case 3>
            <#assign publishStatus="已发布">
            <#break>
        <#case 4>
            <#assign publishStatus="已发布只搜索">
            <#break>
        <#case 5>
            <#assign publishStatus="已删除">
            <#break>
        <#default>
            <#assign publishStatus="处理中">
    </#switch>
${publishStatus}
</#macro>

<#-- 生成商品发布状态 -->
<#macro generateProductStatusSelect name defaultValue hasHeader>
<select class='easyui-combobox' name="${name}">
    <#if hasHeader == true>
        <option value='' <#if (defaultValue == '')>selected='selected'</#if>>所有(未删除)</option>
    </#if>
    <option value='0' <#if (defaultValue == 0)>selected='selected'</#if>>新建</option>
    <option value='1' <#if (defaultValue == 1)>selected='selected'</#if>>已审核</option>
    <option value='2' <#if (defaultValue == 2)>selected='selected'</#if>>已发布不显示</option>
    <option value='3' <#if (defaultValue == 3)>selected='selected'</#if>>已发布</option>
    <option value='4' <#if (defaultValue == 4)>selected='selected'</#if>>已发布只搜索</option>
    <option value='5' <#if (defaultValue == 5)>selected='selected'</#if>>已删除</option>
</select>
</#macro>

<#--商品级别-->
<#macro generateProductLevelSelect name defaultValue hasHeader>
<select class='easyui-combobox' name="${name}" data-options="editable:false">
    <#if hasHeader == true>
        <option value=''>全部</option>
    </#if>
    <option value='1' <#if defaultValue == '1' || defaultValue == ''>selected</#if>>1</option>
    <option value='2' <#if defaultValue == '2'>selected</#if>>2</option>
    <option value='3' <#if defaultValue == '3'>selected</#if>>3</option>
    <option value='4' <#if defaultValue == '4'>selected</#if>>4</option>
    <option value='5' <#if defaultValue == '5'>selected</#if>>5</option>
    <option value='6' <#if defaultValue == '6'>selected</#if>>6</option>
    <option value='7' <#if defaultValue == '7'>selected</#if>>7</option>
    <option value='8' <#if defaultValue == '8'>selected</#if>>8</option>
    <option value='9' <#if defaultValue == '9'>selected</#if>>9</option>
    <option value='10' <#if defaultValue == '10'>selected</#if>>10</option>
</select>
</#macro>


<#-- 生成商品类型 -->
<#macro generateProductClassRadio name defaultValue>
<input id="dealClassVirtual" name="${name}" type="radio" value="1" <#if defaultValue == 1> checked </#if>/><label
        for="dealClassVirtual">虚拟</label>
<input id="dealClassCommon" name="${name}" type="radio" value="0" <#if defaultValue == 0 || defaultValue == ''>
       checked </#if>/><label for="dealClassCommon">普通</label>
</#macro>

<#-- 生成商品类型 -->
<#macro generateProductTypeSelect name defaultValue hasHeader>
<select id="dealType" class='easyui-combobox' name="${name}" data-options="editable:false">
    <#if hasHeader==true>
        <option value=''>全部</option>
    </#if>
    <option value='0' <#if (defaultValue == 0)>selected='selected'</#if>>团购商品</option>
    <option value='1' <#if (defaultValue == 1)>selected='selected'</#if>>其它商品</option>
</select>
</#macro>

<#-- 生成发布到微信 -->
<#macro generatePublishToWechat name defaultValue>
<input id="${name}" type="checkbox" name="${name}" <#if defaultValue == 1>checked</#if>/>&nbsp;<label for="${name}">发布到微信</label>
</#macro>

<#-- 生成发布到App -->
<#macro generatePublishToApp name defaultValue>
<input id="${name}" type="checkbox" name="${name}" <#if defaultValue == 1>checked</#if>/>&nbsp;<label for="${name}">发布到App</label>
</#macro>

<#-- 生成发布到主站 -->
<#macro generatePublishToSite name defaultValue>
<input id="${name}" type="checkbox" name="${name}" <#if defaultValue == 1>checked</#if>/>&nbsp;<label for="${name}">发布到主站</label>
</#macro>

<#-- 页面类型 -->
<#macro generatePageType name defaultValue>
<input id="${name}" type="checkbox" name="${name}" <#if defaultValue == 1>checked</#if>/>&nbsp;<label for="${name}">是否虚拟页面</label>
</#macro>

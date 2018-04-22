<#macro generateAreaTypeSelect name defaultValue hasHeader>
<select class='easyui-combobox' name="${name}" data-options="editable:false">
    <#if hasHeader == true>
        <option value=''>全部</option>
    </#if>
    <option value='CITY' <#if defaultValue == 'CITY' || defaultValue == ''>selected</#if>>地市行政单位</option>
    <option value='PROVINCE' <#if defaultValue == 'PROVINCE'>selected</#if>>省级行政单位</option>
</select>
</#macro>
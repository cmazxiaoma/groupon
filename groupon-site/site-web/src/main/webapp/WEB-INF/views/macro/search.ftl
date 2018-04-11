<#macro search action button=true text="查询" onsubmit="" class="form-inline" method="post">
<form name="searchForm" <#if onsubmit!="">onsubmit="${onsubmit!}" </#if><#t>
      action="${action!}" method="${method!}" class="${class!}">
    <input type="hidden" name="userSubmit" value="true"/>
    <#nested/>
    <#if button >
        <button type="submit" class="btn btn-primary pull-right"><span class="icon-search">&nbsp;</span> ${text!}
        </button>
    </#if>
</form>
</#macro>
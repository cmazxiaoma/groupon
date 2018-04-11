<#macro search2 gridId>
<div id="searchpanel" class="easyui-panel" border="false">
    <form id="searchForm" style="margin-bottom:5px;text-align: center;">
        <#nested/>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData('${gridId}')">查询</a>
    </form>
</div>
</#macro>
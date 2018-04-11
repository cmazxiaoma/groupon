<#macro paging pagingList url>
<#--计算最大页码-->
    <#if pagingList.total % pagingList.pageSize == 0>
        <#assign maxPageIndex = pagingList.total / pagingList.pageSize>
    <#else>
        <#assign maxPageIndex = (pagingList.total / pagingList.pageSize)?floor + 1>
    </#if>

<div class="page">
<#--第一页，禁用“上一页”按钮-->
    <#if pagingList.total == 0 || pagingList.page == 1>
        <span class="prev-disabled">上一页</span>
    <#else>
        <#if pagingList.page == 2>
            <a href="${url}">上一页</a>
        <#else>
            <a href="${url}/${pagingList.page-1}">上一页</a>
        </#if>
    </#if>

<#--第一页-->
    <#if (pagingList.total > 0)>
        <a href="${url}" <#if pagingList.page == 1>class="current_page"</#if>>1</a>
    </#if>

<#--如果不只有一页-->
    <#if (maxPageIndex > 1)>
    <#--如果当前页往前查3页不是第2页-->
        <#if ((pagingList.page - 3) > 2)>
            <span class="text">…</span>
        </#if>

    <#--当前页的前3页和后3页-->
        <#list (pagingList.page - 3)..(pagingList.page + 3) as index>
        <#--如果位于第一页和最后一页之间-->
            <#if (index > 1) && (index < maxPageIndex)>
                <a href="${url}/${index}" <#if pagingList.page == index>class="current_page"</#if>>${index}</a>
            </#if>
        </#list>

    <#--如果当前页往后查3页不是倒数第2页-->
        <#if (pagingList.page + 3) < (maxPageIndex - 1)>
            <span class="text">…</span>
        </#if>

    <#--最后页-->
        <a href="${url}/${maxPageIndex}"
           <#if pagingList.page == maxPageIndex>class="current_page"</#if>>${maxPageIndex}</a>
    </#if>

<#--最后页，禁用“下一页”按钮-->
    <#if pagingList.total == 0 || pagingList.page == maxPageIndex>
        <span class="prev-disabled">下一页</span>
    <#else>
        <a href="${url}/${pagingList.page+1}">下一页</a>
    </#if>
</div>
</#macro>


<#macro searchPaging pagingList>
<#--计算最大页码-->
    <#if pagingList.total % pagingList.pageSize == 0>
        <#assign maxPageIndex = pagingList.total / pagingList.pageSize>
    <#else>
        <#assign maxPageIndex = (pagingList.total / pagingList.pageSize)?floor + 1>
    </#if>

<div class="page">
<#--第一页，禁用“上一页”按钮-->
    <#if pagingList.total == 0 || pagingList.page == 1>
        <span class="prev-disabled">上一页</span>
    <#else>
        <#if pagingList.page == 2>
            <a href="javascript:void(0)" onclick="submitSearch(1)">上一页</a>
        <#else>
            <a href="javascript:void(0)" onclick="submitSearch(${pagingList.page-1})">上一页</a>
        </#if>
    </#if>

<#--第一页-->
    <#if (pagingList.total > 0)>
        <a href="javascript:void(0)" onclick="submitSearch(1)"
           <#if pagingList.page == 1>class="current_page"</#if>>1</a>
    </#if>

<#--如果不只有一页-->
    <#if (maxPageIndex > 1)>
    <#--如果当前页往前查3页不是第2页-->
        <#if ((pagingList.page - 3) > 2)>
            <span class="text">…</span>
        </#if>

    <#--当前页的前3页和后3页-->
        <#list (pagingList.page - 3)..(pagingList.page + 3) as index>
        <#--如果位于第一页和最后一页之间-->
            <#if (index > 1) && (index < maxPageIndex)>
                <a href="javascript:void(0)" onclick="submitSearch(${index})"
                   <#if pagingList.page == index>class="current_page"</#if>>${index}</a>
            </#if>
        </#list>

    <#--如果当前页往后查3页不是倒数第2页-->
        <#if (pagingList.page + 3) < (maxPageIndex - 1)>
            <span class="text">…</span>
        </#if>

    <#--最后页-->
        <a href="javascript:void(0)" onclick="submitSearch(${maxPageIndex})"
           <#if pagingList.page == maxPageIndex>class="current_page"</#if>>${maxPageIndex}</a>
    </#if>

<#--最后页，禁用“下一页”按钮-->
    <#if pagingList.total == 0 || pagingList.page == maxPageIndex>
        <span class="prev-disabled">下一页</span>
    <#else>
        <a href="javascript:void(0)" onclick="submitSearch(${pagingList.page+1})">下一页</a>
    </#if>
    <script type="text/javascript">
        function submitSearch(page) {
            $('#search_page').val(page);
            $('#search_form').submit();
        }

    </script>
</div>
</#macro>
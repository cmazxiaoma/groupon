<#import "macro/paging.ftl" as paging_macro>
<#import "macro/common.ftl" as common>
<html>
<head>
    <title>消息提醒</title>
</head>
<body>
<div class="hr_15"></div>
<div class="comWidth clearfix products">
    <div class="leftArea">
    <#include "user/left_menu.ftl">
    </div>
    <div class="rightArea">
        <h3 class="shopping_tit">消息提醒</h3>
        <!--表格-->
        <table class="table" cellspacing="0" cellpadding="0">
            <thead>
            <tr>
                <th width="20%">标题</th>
                <th width="60%">内容</th>
                <th width="10%">状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#if messages??>
                <#list messages as message>
                <tr>
                    <td>${message.title}</td>
                    <td>${message.content}</td>
                    <td><@common.formatMessageStatus message.readed/></td>
                    <td>
                        <a href="javascript:void(0)" class="btn_link" onclick="">已读&nbsp;&nbsp;</a>
                        <a href="javascript:void(0)" class="btn_link" onclick="">删除</a>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>


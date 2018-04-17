<#import "macro/paging.ftl" as paging_macro>
<#import "macro/common.ftl" as common>
<html>
<head>
    <title>消息提醒</title>
    <script type="text/javascript">
        var ctx = '${ctx}';

        function delMessage(messageId, page) {
            Submit.AjaxSubmit1(ctx + "/home/delMessage/" + messageId, "", "post",
            function(successResult) {
                console.log(successResult);
                window.location.href = ctx + "/home/message/" + page;
            }, function (failResult) {
                console.log(failResult);});
        }

        function updateMessageStatus(messageId, page) {
            Submit.AjaxSubmit1(ctx + "/home/readMessage/" + messageId, "", "post",
            function(successResult){
                console.log(successResult);
                window.location.href = ctx + "/home/message/" + page;
            }, function(failResult) {
                console.log(failResult);});
        }

        function readMessage(messageId, dealSkuId) {
            window.location.href = ctx + "/item/" + dealSkuId;
            Submit.AjaxSubmit2(ctx + "/home/readMessage/" + messageId, "", "post",
                    function(successResult) {
                        console.log(successResult);
                    },
                    function(failResult) {
                       console.log(failResult);
                    });
        }
    </script>
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
                    <td>
                        <a href="javascript:readMessage(${message.id}, ${message.dealSkuId})">${message.content}</a>
                    </td>
                    <td><@common.formatMessageStatus message.readed/></td>
                    <td>
                        <a href="javascript:updateMessageStatus(${message.id}, ${pagingResult.page})" class="btn_link">已读&nbsp;&nbsp;</a>
                        <a href="javascript:delMessage(${message.id}, ${pagingResult.page})" class="btn_link">删除</a>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
        <div class="hr_25"></div>
            <#if pagingResult??>
                <@paging_macro.paging pagingList=pagingResult url="${ctx}/home/message"/>
            </#if>
     </div>
</div>
</body>
</html>


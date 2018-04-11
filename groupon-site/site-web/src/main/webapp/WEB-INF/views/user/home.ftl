<#import "macro/paging.ftl" as paging_macro>
<#import "macro/common.ftl" as common>
<html>
<head>
    <title>个人中心</title>
</head>
<body>
<div class="hr_15"></div>
<div class="comWidth clearfix products">
    <div class="leftArea">
    <#include "user/left_menu.ftl">
    </div>
    <div class="rightArea">
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
            <tr>
                <!--这里的id和for里面的c1 需要循环出来-->
                <td>后台设计</td>
                <td>测试内容</td>
                <td>已读</td>
                <td align="center"><input type="button" value="删除" class="btn"></td>
            </tr>
            <tr>
                <td>后台设计</td>
                <td>测试内容</td>
                <td>已读</td>
                <td align="center"><input type="button" value="删除" class="btn"></td>
            </tr>
            <tr>
                <td>后台设计</td>
                <td>测试内容</td>
                <td>已读</td>
                <td align="center"><input type="button" value="删除" class="btn"></td>
            </tr>
            <tr>
                <td>后台设计</td>
                <td>测试内容</td>
                <td>已读</td>
                <td align="center"><input type="button" value="删除" class="btn"></td>
            </tr>
            <tr>
                <td>后台设计</td>
                <td>测试内容</td>
                <td>已读</td>
                <td align="center"><input type="button" value="删除" class="btn"></td>
            </tr>
            <tr>
                <td>后台设计</td>
                <td>测试内容</td>
                <td>已读</td>
                <td align="center"><input type="button" value="删除" class="btn"></td>
            </tr>
            <tr>
                <td>后台设计</td>
                <td>测试内容</td>
                <td>已读</td>
                <td align="center"><input type="button" value="删除" class="btn"></td>
            </tr>
            <tr>
                <td>后台设计</td>
                <td>测试内容</td>
                <td>已读</td>
                <td align="center"><input type="button" value="删除" class="btn"></td>
            </tr>
            <tr>
                <td>后台设计</td>
                <td>测试内容</td>
                <td>已读</td>
                <td align="center"><input type="button" value="删除" class="btn"></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>


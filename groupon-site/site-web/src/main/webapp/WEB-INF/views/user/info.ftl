<#import "macro/paging.ftl" as paging_macro>
<#import "macro/common.ftl" as common>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>个人信息</title>
    <script type="text/javascript">
        function modify() {
            if ($("#nickname").attr("disabled") == undefined) {
                $("#nickname").attr("disabled", true);
                $("#realName").attr("disabled", true);
                $("#mail").attr("disabled", true);
                $("#phone").attr("disabled", true);

                $("#nickname").css("background-color", "#BFBFBF");
                $("#realName").css("background-color", "#BFBFBF");
                $("#mail").css("background-color", "#BFBFBF");
                $("#phone").css("background-color", "#BFBFBF");
            } else if ($("#nickname").attr("disabled") == 'disabled') {
                $("#nickname").attr("disabled", false);
                $("#realName").attr("disabled", false);
                $("#mail").attr("disabled", false);
                $("#phone").attr("disabled", false);

                $("#nickname").css("background-color", "#FFFFFF");
                $("#realName").css("background-color", "#FFFFFF");
                $("#mail").css("background-color", "#FFFFFF");
                $("#phone").css("background-color", "#FFFFFF");
            }
        }

        function updateInfo() {
            var data = $("#info_form").serializeArray();
            Submit.AjaxSubmit(ctx + '/home/info/update', data);
            modify();
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
        <h3 class="shopping_tit">基本信息</h3>
        <table class="table">
            <form method="post" id="info_form">
                <input type="hidden" name="id" value="${info.id}">
                <tr>
                    <td style="width: 20%">昵称：</td>
                    <td><input type="text" id="nickname" name="nickname" value="${info.nickname}" class="big_input"
                               style="background-color: #BFBFBF" disabled></td>
                </tr>
                <tr>
                    <td>邮箱：</td>
                    <td><input type="text" id="mail" name="mail" value="${info.mail}" class="big_input"
                               style="background-color: #BFBFBF" disabled></td>
                </tr>
                <tr>
                    <td>联系电话：</td>
                    <td><input type="text" id="phone" name="phone" value="${info.phone}" class="big_input"
                               style="background-color: #BFBFBF" disabled></td>
                </tr>
                <tr>
                    <td>真实姓名：</td>
                    <td><input type="text" id="realName" name="realName" value="${info.realName}" class="big_input"
                               style="background-color: #BFBFBF" disabled></td>
                </tr>
                <tr>
                    <td align="center" colspan="2"><input type="button" onclick="modify()" value="修改"
                                                          class="btn"/><input type="button" onclick="updateInfo()"
                                                                              value="提交" class="btn"/></td>
                </tr>
            </form>
        </table>
    </div>
</div>
</body>
</html>


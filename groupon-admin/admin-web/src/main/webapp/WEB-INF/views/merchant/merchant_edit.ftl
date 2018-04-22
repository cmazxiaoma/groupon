<style type="text/css">
    .td1 {
        width: 100px;
        text-align: right;
        white-space: nowrap;
    }

    .td2 {
        width: 350px;
        text-align: left;
    }
</style>

<form id="edit_merchant_form" method="post">
    <input id="id" name="id" value="<#if merchant??>${merchant.id}<#else></#if>" type="hidden"/>
    <table width="100%">
        <tr>
            <td class="td1">
                <label>名称：</label>
            </td>
            <td class="td2">
                <input id="name" name="name" class="easyui-validatebox" type="text" value="${merchant.name!}"
                       data-options="required:true">
            </td>
        </tr>
        <tr>
            <td class="td1">
                <label>级别：</label>
            </td>
            <td class="td2">
                <input id="level" name="level" class="easyui-validatebox" type="text" value="${merchant.level!}"
                       data-options="required:true">
            </td>
        </tr>
        <tr>
            <td class="td1">
                <label>热度级别：</label>
            </td>
            <td class="td2">
                <input id="hotLevel" name="hotLevel" class="easyui-validatebox" type="text"
                       value="${merchant.hotLevel!}" data-options="required:true">
            </td>
        </tr>
        <tr>
            <td class="td1">
                <label>URL：</label>
            </td>
            <td class="td2">
                <input id="url" name="url" class="easyui-validatebox" type="text" value="${merchant.url!}"
                       data-options="required:true">
            </td>
        </tr>
        <tr>
            <td class="td1">
                <label>描述：</label>
            </td>
            <td class="td2">
                <textarea id="description" name="description" class="easyui-validatebox" type="text"
                          value="${merchant.description!}" data-options="required:true">${merchant.description!}</textarea>
            </td>
        </tr>
        <tr>
            <td align="center" colspan="2">
                <div id="dlg-buttons">
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveMerchant();">保存</a>
                </div>
            </td>
        </tr>
    </table>
</form>
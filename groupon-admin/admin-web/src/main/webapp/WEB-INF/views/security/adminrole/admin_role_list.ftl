<#import "/macro/search.ftl" as search_macro/>
<body>
<script type="text/javascript">
    function setTreeUnselected() {
        var nodes = $('#menuTree').tree('getChildren');
        for (var i = 0; i < nodes.length; i++) {
            $('#menuTree').tree('uncheck', nodes[i].target);
        }
    }

    function setTreeSelected() {
        Submit.AjaxSubmit(ctx + "/security/adminRole/getAdminRoleFunctionIdsByRoleId",
                eval('(' + '{"roleId":' + $("#curRoleId").val() + '}' + ')'), "",
                function (result) {
                    $('#functionIds').val(result);
                });
        if ($('#functionIds').val()) {
            var functionIds = $('#functionIds').val().split(',');
            var nodes = $('#menuTree').tree('getChildren');
            for (var i = 0; i < nodes.length; i++) {
                if (nodes[i] && nodes[i].id) {
                    for (var j = 0; j < functionIds.length; j++) {
                        if (nodes[i].id == functionIds[j]) {
                            $('#menuTree').tree('check', nodes[i].target);
                        }
                    }
                }
            }
        } else {
            var nodes = $('#menuTree').tree('getChildren');
            for (var i = 0; i < nodes.length; i++) {
                $('#menuTree').tree('uncheck', nodes[i].target);
            }
        }
    }

    function setFunctionIds() {
        var nodes = $('#menuTree').tree('getChecked');
        var s = '';
        for (var i = 0; i < nodes.length; i++) {
            if (nodes[i] && nodes[i].id) {
                if (s != '') s += ',';
                s += nodes[i].id;
                $('#menuTree').tree('uncheck', nodes[i].target);
            }
        }
        $('#functionIds').val(s);
    }
</script>
<@search_macro.search2 gridId="userGrid">
<label>最后登录日期</label><input class="easyui-datebox" style="width:100px">
到<input class="easyui-datebox" style="width:100px">
<label>用户名</label><input name="search_userName">
</@search_macro.search2>

<div id="tb" style="padding:1px;height:auto">
    <div style="margin-bottom:1px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add"
           onclick="Dialog.OpenNew('adminRoleDialog', '新建权限', 'adminRoleForm');setTreeUnselected()" plain="true">新建</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit"
           onclick="Dialog.OpenEdit('adminRoleGrid', 'adminRoleDialog', '修改用户权限信息', 'adminRoleForm');setTreeSelected()"
           plain="true">修改</a>
    </div>
</div>
<table id="adminRoleGrid" class="easyui-datagrid" title="角色列表" pagination="true" rownumbers="true" idField="id"
       data-options="singleSelect:true,url:'${ctx}/security/adminRole/listAdminRole',method:'post',toolbar:'#tb'">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th field="name">角色名称</th>
        <th field="createTime" align="right" formatter="Formatter.DateTimeFormatter">创建时间</th>
        <th field="updateTime" align="right" formatter="Formatter.DateTimeFormatter">修改时间</th>
    </tr>
    </thead>
</table>


<div id="adminRoleDialog" class="easyui-dialog" style="width:490px;height:480px;padding:10px 20px" closed="true"
     buttons="#dlg-buttons">
    <form id="adminRoleForm" name="adminRoleForm" method="post" novalidate>
        <input id="curRoleId" name="id" type="hidden"/>
        <input id="functionIds" name="functionIds" type="hidden"/>
        <table>
            <tr>
                <td valign="top"><label>角色名称：</label><input name="name" class="easyui-validatebox" required="true">
                    <br><br>
                    <label>功能菜单：</label>
                    <div style="width:450px;height:320px;overflow:auto" id="menuTree" class="easyui-tree"
                         data-options="url:'${ctx}/security/adminFunction/buildFunctionTreeForEdit',animate:true,checkbox:true"></div>
                </td>
            <tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"
       onclick="javascript:setFunctionIds();Submit.FormSubmit('adminRoleForm', 'adminRoleDialog', 'adminRoleGrid', '${ctx}/security/adminRole/addEditAdminRole')">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:Dialog.Close('adminRoleDialog')">取消</a>
</div>
</body>
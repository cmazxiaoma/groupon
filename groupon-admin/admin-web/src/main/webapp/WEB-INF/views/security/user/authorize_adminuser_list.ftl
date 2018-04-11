<#import "/macro/search.ftl" as search_macro/>
<body>
<script type="text/javascript">
    function formatUserName(value, row) {
        if (row.userStatus == 0) {
            return '<span style="color:red;">' + value + '</span>';
        } else {
            return value;
        }
    }

    function setDefaultRoleValue() {
        $('#roleIds').combogrid('clear');
        var userRow = $('#userAuthorizeGrid').datagrid('getSelected');
        if (userRow.roleId.indexOf(',') < 0) {
            var idArray = new Array();
            idArray[0] = userRow.roleId;
            $('#roleIds').combogrid('setValues', idArray);
        } else {
            var ids = userRow.roleId.split(',');
            $('#roleIds').combogrid('setValues', ids);
        }
    }

    function formatAdminRole(value, row, index) {
        var roleIds = '';
        $.each(value, function (i, field) {
            if (roleIds != '') roleIds += ',';
            roleIds += field.adminRoleId;
        });
        //return roleIds;
        var roleNames = '';
        $.ajax({
            type: "post",
            url: "${ctx}/security/adminuser/getAdminRole",
            data: {idList: roleIds},
            dataType: "json",
            async: false,
            success: function (result, textStatus) {
                if (null == result) return '';
                $.each(result, function (i, field) {
                    if (roleNames != '') roleNames += ',';
                    roleNames += field.name;
                });
            },
            error: function () {
                $.messager.show({
                    title: '失败',
                    msg: '系统错误',
                    style: {
                        right: '',
                        top: document.body.scrollTop + document.documentElement.scrollTop,
                        bottom: ''
                    }
                });
            }
        });
        $('#userAuthorizeGrid').datagrid('getRows')[index]['roleId'] = roleIds;
        return roleNames;
    }
</script>

<@search_macro.search2 gridId="userAuthorizeGrid">
<label>最后登录日期</label><input class="easyui-datebox" style="width:100px">
到<input class="easyui-datebox" style="width:100px">
<label>用户名</label><input name="search_userName">
</@search_macro.search2>

<div id="tb" style="padding:1px;height:auto">
    <div style="margin-bottom:1px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit"
           onclick="setDefaultRoleValue();Dialog.OpenEdit('userAuthorizeGrid', 'userAuthorizeDialog', '设置用户权限', 'userAuthorizeForm')"
           plain="true">设置权限</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove"
           onclick="Submit.FormDeleteSubmit('userAuthorizeForm', 'userAuthorizeGrid', '${ctx}/security/adminUserRole/deleteAdminUserAuthorize')"
           plain="true">删除权限</a>
    </div>
</div>
<table id="userAuthorizeGrid" class="easyui-datagrid" title="用户列表" pagination="true" rownumbers="true" idField="id"
       data-options="singleSelect:true,url:'${ctx}/security/adminuser/listAdminUser',method:'post',toolbar:'#tb'">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th field="name" formatter="formatUserName">用户名</th>
        <th field="adminUserRoles" formatter="formatAdminRole">角色</th>
        <th field="lastLoginTime" align="right" formatter="Formatter.DateTimeFormatter">最后登录时间</th>
        <th field="roleId" data-options="hidden:true">角色ID</th>
    </tr>
    </thead>
</table>


<div id="userAuthorizeDialog" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true"
     buttons="#dlg-buttons">
    <div class="ftitle">用户信息</div>
    <form id="userAuthorizeForm" name="userAuthorizeForm" method="post" novalidate>
        <input name="id" type="hidden">
        <table>
            <tr>
                <td><label>用户名</label></td>
                <td><input name="name" class="easyui-validatebox" required="true"></td>
            <tr>
            </tr>
            <td><label>角色</label></td>
            <td>
                <div class="easyui-combogrid" id="roleIds" name="roleIds" style="width:250px"
                     data-options="panelWidth: 600,
								            idField: 'id',
								            textField: 'name',
								            multiple: true,
								            url: '${ctx}/security/adminRole/listAdminRole',
								            method: 'post',
								            pagination: true,
                    						rownumbers: true,
								            pageSize: 10000,
                    						pageList: [10000],
								            columns: [[
								            	{field:'ck',checkbox:true},
								            	{field:'name',title:'角色名称',width:40},
								                {field:'permissionCode',title:'角色编码',width:120},
								                {field:'createTime',title:'创建时间',formatter:Formatter.DateFormatter,width:25,align:'right'},
								                {field:'updateTime',title:'修改时间',formatter:Formatter.DateFormatter,width:25,align:'right'}
								            ]],
								            fitColumns: true
								        "/>
            </td>
            <tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"
       onclick="javascript:Submit.FormSubmit('userAuthorizeForm', 'userAuthorizeDialog', 'userAuthorizeGrid', '${ctx}/security/adminUserRole/setAdminUserAuthorize')">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:Dialog.Close('userAuthorizeDialog')">取消</a>
</div>
</body>
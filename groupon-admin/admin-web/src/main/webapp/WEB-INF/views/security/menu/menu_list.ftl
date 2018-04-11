<body>
<script type="text/javascript">
    var parentId = 0;
    var nodeTarget;

    function functionOnClick(node) {
        var params = $('#functionGrid').datagrid('options').queryParams;
        params['search_parentId'] = node.id;
        $('#functionGrid').datagrid('reload');
        parentId = node.id;
        nodeTarget = node.target;
    }

    function setParentId() {
        $('#parentId').val(parentId);
    }

    function setEditDefaultValue() {
        var row = $('#functionGrid').datagrid('getSelected');
    }

    function reloadNode() {
        $('#functionTree').tree('reload');
        $('#functionTree').tree('select', nodeTarget);
    }
</script>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'west',split:true" style="width:200px;">
        <ul id="functionTree" class="easyui-tree" data-options="animate:true,lines:true,
						onClick:functionOnClick,url:'${ctx}/security/adminFunction/buildFunctionTreeForEdit'">
    </div>
    <div id="functionDataArea" data-options="region:'center'" style="padding:5px;background:#eee;">
        <div id="tb" style="padding:1px;height:auto">
            <div style="margin-bottom:1px">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add"
                   onclick="Dialog.OpenNew('functionDialog', '新建菜单', 'functionForm');setParentId()" plain="true">新建</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit"
                   onclick="setEditDefaultValue();Dialog.OpenEdit('functionGrid', 'functionDialog', '修改菜单信息', 'functionForm')"
                   plain="true">修改</a>
            </div>
        </div>
        <table id="functionGrid" class="easyui-datagrid" title="子节点列表" pagination="true" rownumbers="true" idField="id"
               data-options="singleSelect:true,url:'${ctx}/security/adminFunction/listFunction',method:'post',toolbar:'#tb'">
            <thead>
            <tr>
                <th data-options="field:'ck',checkbox:true"></th>
                <th field="name">名称</th>
                <th field="state">状态</th>
                <th field="url">URL</th>
                <th field="createTime" align="right" formatter="Formatter.DateTimeFormatter">创建时间</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div id="functionDialog" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true"
     buttons="#dlg-buttons">
    <div class="ftitle">功能信息</div>
    <form id="functionForm" name="functionForm" method="post" novalidate>
        <input id="parentId" name="parentId" type="hidden">
        <input id="id" name="id" type="hidden">
        <table>
            <tr>
                <td><label>名称</label></td>
                <td><input name="name" class="easyui-validatebox" required="true"></td>
            <tr>
            </tr>
            <td><label>URL</label></td>
            <td><input name="url" class="easyui-validatebox" value=""></td>
            <tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"
       onclick="javascript:Submit.FormSubmit('functionForm', 'functionDialog', 'functionGrid', '${ctx}/security/adminFunction/addEditFunction', reloadNode);">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:Dialog.Close('functionDialog')">取消</a>
</div>
</body>
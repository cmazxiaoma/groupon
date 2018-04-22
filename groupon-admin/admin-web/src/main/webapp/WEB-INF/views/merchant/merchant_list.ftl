<body>
<script type="text/javascript">

    function addMerchant() {
        $('#merchant_edit_dialog').dialog({
            title: '添加商家',
            closed: false,
            modal: true,
            cache: false,
            href: '${ctx}/merchant/edit/0',
            onLoad: function () {
            }
        });
    }

    function updateMerchant() {
        var selectedMerchant = $("#merchantGrid").datagrid("getSelected");
        if (selectedMerchant) {
            $('#merchant_edit_dialog').dialog({
                title: '修改商家',
                closed: false,
                modal: true,
                cache: false,
                href: '${ctx}/merchant/edit/' + selectedMerchant.id,
                onLoad: function () {
                }
            });
        } else {
            Messager.Prompt("温馨提示", "请先选择商家");
        }
    }

    function saveMerchant() {
        Submit.FormSubmit("edit_merchant_form", "merchant_edit_dialog", "merchantGrid", ctx + "/merchant/save");
    }

</script>

<div id="tb" style="padding:1px;height:auto">
    <div style="margin-bottom:1px">
        <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="addMerchant();" plain="true"
           title="添加商家">添加商家</a>
    <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="updateMerchant();" plain="true" title="修改商家">修改商家</a>
    </div>
</div>

<table id="merchantGrid" class="easyui-datagrid" title="商家列表" pagination="true" rownumbers="true" idField="id"
       data-options="singleSelect:true,url:'${ctx}/merchant/listMerchant',method:'post',toolbar:'#tb'">
    <thead>
    <tr>
        <th field="name">商家名称</th>
        <th field="url">URL</th>
        <th field="createTime" formatter="Formatter.DateTimeFormatter">创建时间</th>
        <th field="updateTime" formatter="Formatter.DateTimeFormatter">更新时间</th>
        <th field="description">商家描述</th>
    </tr>
    </thead>
</table>

<#--商家编辑对话框-->
<div id="merchant_edit_dialog" class="easyui-dialog" style="width:500px;height:250px;"
     data-options="closed:true,maximizable:true"></div>
</body>
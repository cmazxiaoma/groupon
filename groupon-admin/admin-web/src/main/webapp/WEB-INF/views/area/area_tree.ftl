<#import "/macro/common.ftl" as common_macro/>
<body>
<script type="text/javascript">
    function onAreaClick(node) {
        var params = $('#areaGrid').datagrid('options').queryParams;
        params['search_parentId'] = node.id;
        $('#areaGrid').datagrid('reload');
        $('#areaGrid').datagrid('clearSelections');
    }

    function addArea() {
        var parentId = 0;
        var title = "新建商品类别";
        var selectedAreaTreeNode = $('#area_tree').tree("getSelected");
        if (selectedAreaTreeNode) {
            parentId = selectedAreaTreeNode.id;
            title += "（父类别：" + selectedAreaTreeNode.text + "）";
        } else {
            title += "（父类别：无）";
        }

        $('#area_edit_dialog').dialog({
            title: title,
            iconCls: 'icon-add',
            closed: false,
            cache: false,
            href: '${ctx}/area/edit?id=0&parentId=' + parentId,
            modal: true
        });
    }

    function updateArea() {
        var selectedAreaGridItem = $("#areaGrid").datagrid("getSelected");
        if (!selectedAreaGridItem) {
            Messager.Prompt("温馨提示", "请选择要修改的商品类别");
            return;
        }

        var parentId = 0;
        var title = "修改商品类别";
        var selectedAreaTreeNode = $('#area_tree').tree("getSelected");
        if (selectedAreaTreeNode) {
            parentId = selectedAreaTreeNode.id;
            title += "（父类别：" + selectedAreaTreeNode.text + "）";
        } else {
            title += "（父类别：无）";
        }

        $('#area_edit_dialog').dialog({
            title: title,
            iconCls: 'icon-edit',
            closed: false,
            cache: false,
            href: '${ctx}/area/edit?id=' + selectedAreaGridItem.id + '&parentId=' + parentId,
            modal: true
        });
    }

    function areaTypeFormatter(value, row) {
        switch (value) {
            case 'PROVINCE':
                return '省级行政单位';
            case 'CITY':
                return '地市行政单位';
            default:
                return '未知';
        }
    }
</script>
</body>
<body>
<div class="easyui-layout" data-options="fit:true">
<#--商品类别树-->
    <div data-options="region:'west',split:true" style="width:200px;">
        <ul id="area_tree" class="easyui-tree"
            data-options="animate:true,lines:true,method:'get',onClick:onAreaClick,url:'${ctx}/area/buildAreaTree',
					onContextMenu: function(e, node){
						e.preventDefault();
						$(this).tree('select', node.target);
						$('#area_tree_menu').menu('show', {
							left: e.pageX,
							top: e.pageY
						});
					}"/>
    </div>

<#--商品类别编辑表单-->
    <div data-options="region:'center'" style="padding:5px;">
        <div id="tb" style="padding:1px;height:auto">
            <div style="margin-bottom:1px">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addArea();" plain="true">新建</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="updateArea();" plain="true">修改</a>
            </div>
        </div>
        <table id="areaGrid" class="easyui-datagrid" title="地区列表" pagination="true" rownumbers="true" idField="id"
               data-options="singleSelect:true,url:'${ctx}/area/listArea',method:'post',queryParams:{search_parentId:0},toolbar:'#tb'">
            <thead>
            <tr>
                <th data-options="field:'ck',checkbox:true"></th>
                <th field="name">名称</th>
                <th field="type" formatter="areaTypeFormatter">类型</th>
                <th field="createTime" align="right" formatter="Formatter.DateTimeFormatter">创建时间</th>
            </tr>
            </thead>
        </table>
    </div>

<#--商品类别编辑对话框-->
    <div id="area_edit_dialog" class="easyui-dialog" style="width:500px;height:220px;" data-options="closed:true"></div>

<#--商品类别树右键菜单-->
    <div id="area_tree_menu" class="easyui-menu" style="width:120px;">
        <div onclick="deleteArea();" data-options="iconCls:'icon-remove'">删除类别</div>
    </div>
</div>
</body>
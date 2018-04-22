<#import "/macro/common.ftl" as common_macro/>
<body>
<script type="text/javascript" src="${ctx}/js/product.js"></script>
<script type="text/javascript">

    function onProductCategoryClick(node) {
        var params = $('#dealCategoryGrid').datagrid('options').queryParams;
        params['search_parentId'] = node.id;
        $('#dealCategoryGrid').datagrid('reload');
        $('#dealCategoryGrid').datagrid('clearSelections');
    }

    function addProductCategory() {
        var parentId = 0;
        var title = "新建商品类别";

        //通过选择菜单，获取parentId
        var selectedProductCategoryTreeNode = $('#product_category_tree').tree("getSelected");

        console.log(selectedProductCategoryTreeNode);

        if (selectedProductCategoryTreeNode) {
            parentId = selectedProductCategoryTreeNode.id;
            title += "（父类别：" + selectedProductCategoryTreeNode.text + "）";
        } else {
            title += "（父类别：无）";
        }

        console.log(parentId);
        console.log(title);

        $('#deal_category_edit_dialog').dialog({
            title: title,
            iconCls: 'icon-add',
            closed: false,
            cache: false,
            href: '${ctx}/product/category/edit?id=0&parentId=' + parentId,
            modal: true
        });
    }

    function updateProductCategory() {
        var selectedProductCategoryGridItem = $("#dealCategoryGrid").datagrid("getSelected");
        if (!selectedProductCategoryGridItem) {
            Messager.Prompt("温馨提示", "请选择要修改的商品类别");
            return;
        }

        var parentId = 0;
        var title = "修改商品类别";
        var selectedProductCategoryTreeNode = $('#product_category_tree').tree("getSelected");

        if (selectedProductCategoryTreeNode) {
            parentId = selectedProductCategoryTreeNode.id;
            title += "（父类别：" + selectedProductCategoryTreeNode.text + "）";
        } else {
            title += "（父类别：无）";
        }

        $('#deal_category_edit_dialog').dialog({
            title: title,
            iconCls: 'icon-edit',
            closed: false,
            cache: false,
            href: '${ctx}/product/category/edit?id=' + selectedProductCategoryGridItem.id + '&parentId=' + parentId,
            modal: true
        });
    }
</script>
</body>
<body>
<div class="easyui-layout" data-options="fit:true">
<#--商品类别树-->
    <div data-options="region:'west',split:true" style="width:200px;">
        <ul id="product_category_tree" class="easyui-tree"
            data-options="animate:true,lines:true,method:'get',onClick:onProductCategoryClick,url:'${ctx}/product/category/buildProductCategoryTree',
					onContextMenu: function(e, node){
						e.preventDefault();
						$(this).tree('select', node.target);
						$('#product_category_tree_menu').menu('show', {
							left: e.pageX,
							top: e.pageY
						});
					}"/>
    </div>

<#--商品类别编辑表单-->
    <div data-options="region:'center'" style="padding:5px;">
        <div id="tb" style="padding:1px;height:auto">
            <div style="margin-bottom:1px">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addProductCategory();"
                   plain="true">新建</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="updateProductCategory();"
                   plain="true">修改</a>
            </div>
        </div>
        <table id="dealCategoryGrid" class="easyui-datagrid" title="子类别列表" pagination="true" rownumbers="true"
               idField="id"
               data-options="singleSelect:true,url:'${ctx}/product/category/listProductCategory',method:'post',queryParams:{search_parentId:0},toolbar:'#tb'">
            <thead>
            <tr>
                <th data-options="field:'ck',checkbox:true"></th>
                <th field="name">名称</th>
                <th field="urlName">URL标识</th>
                <th field="createTime" align="right" formatter="Formatter.DateTimeFormatter">创建时间</th>
                <th field="publishStatus" formatter="Formatter.ProductPublishStatusFormatter">发布状态</th>
                <th field="orderNum" formatter="Formatter.ProductCategoryOrderNumFormatter">顺序</th>
            </tr>
            </thead>
        </table>
    </div>

<#--商品类别编辑对话框-->
    <div id="deal_category_edit_dialog" class="easyui-dialog" style="width:500px;height:220px;"
         data-options="closed:true"></div>

<#--商品类别树右键菜单-->
    <div id="product_category_tree_menu" class="easyui-menu" style="width:120px;">
        <div onclick="deleteProductCategory();" data-options="iconCls:'icon-remove'">删除类别</div>
    </div>
</div>
</body>
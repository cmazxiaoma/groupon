<#import "/macro/search.ftl" as search_macro/>
<#import "/macro/product.ftl" as product_macro/>
<body>
<script type="text/javascript" src="${ctx}/js/product.js"></script>
<script type="text/javascript">

    function viewDetail(value, row) {
        var html = '<table><tr><td rowspan="2">';
        html = html + '<div style=\"float:left\"><img alt=\"商品图片为空\" src=\"' + row.imageGenPath + '\" /></div></td>';
        html = html + '<td><p>市场价格：' + Formatter.PriceFormatter(row.marketPrice) + '</p><p>结算价格：' + Formatter.PriceFormatter(row.settlementPrice) + '</p><p>折扣：' + row.discount + '</p></td>';
        html = html + '<td><p>商品类型：' + row.dealClass + '</p><p>最大购买数量：' + row.maxPurchaseCount + '</p><p>是否售罄：' + (row.oosStatus == 0 ? '否' : '是') + '</p></td></tr>';
        html = html + '</table>';
        return html;
    }

    function addProduct() {
        $('#product_edit_dialog').dialog({
            title: '添加商品',
            closed: false,
            modal: true,
            cache: false,
            href: '${ctx}/product/product/edit/0',
            onLoad: function () {
                initDealDetailCKEditor();
                bindEventToDealClassRadio();
            }
        });
    }

    function updateProduct() {
        var selectedProduct = $("#productListGrid").datagrid("getSelected");
        if (selectedProduct) {
            $('#product_edit_dialog').dialog({
                title: '修改商品',
                closed: false,
                modal: true,
                cache: false,
                href: '${ctx}/product/product/edit/' + selectedProduct.id,
                onLoad: function () {
                    initDealDetailCKEditor();
                    bindEventToDealClassRadio();
                }
            });
        } else {
            Messager.Prompt("温馨提示", "请先选择商品");
        }
    }

    function copyProduct(id) {
        $('#product_edit_dialog').dialog({
            title: '添加商品',
            closed: false,
            modal: true,
            cache: false,
            href: '${ctx}/product/product/edit/' + id,
            onLoad: function () {
                $("#id").val(0);
                $("#product_edit_cancel_btn").click(function () {
                    $('#tt').tabs('close', '添加商品');
                });

                initDealDetailCKEditor();
                bindEventToDealClassRadio();
            }
        });
    }

    function bindEventToDealClassRadio() {
        // 虚拟商品
        $("#dealClassVirtual").click(function () {
            $("input[name='merchantCode']").attr("disabled", true);
            $("#marketPrice").numberbox({"disabled": true});
            $("#merchantPrice").numberbox({"disabled": true});
            $("#dealPrice").numberbox({"disabled": true});
            $("#settlementPrice").numberbox({"disabled": true});
            $("#inventoryAmount").numberbox({"disabled": true});
            $("#vendibilityAmount").numberbox({"disabled": true});
            $("#maxPurchaseCount").numberbox({"disabled": true});
            $("#bonusPoints").numberbox({"disabled": true});
            $('#dealDetail').ckeditor().editor.setReadOnly(true);
        });

        // 普通商品
        $("#dealClassCommon").click(function () {
            $("input").attr("disabled", false);
            $("select").attr("disabled", false);
            $("textarea[name='seoDesc']").attr("disabled", false);
            $('#dealDetail').ckeditor().editor.setReadOnly(false);
        });
    }

    function initDealDetailCKEditor() {
        var editor = $('#dealDetail').ckeditor({filebrowserUploadUrl: '${ctx}/image/ckeuploadfile?module=dealDetail'}).editor;
        editor.on("instanceReady", function () {
            // CKEditor加载完后，如果是虚拟商品
            if ($("#dealClassVirtual").attr("checked") == "checked") {
                $("#dealClassVirtual").click();
            }
        });
    }

</script>
<#--查询条件-->
<@search_macro.search2 gridId="productListGrid">
<div style="margin-bottom:5px;">

    <label>产品编码:</label>
    <input name="search_merchantCode" type="text" value='${search_merchantCode}'/>

    <label>商品类型:</label>
    <@product_macro.generateProductTypeSelect name="search_dealType" defaultValue=search_dealType hasHeader=true/>

    <label>商品状态:</label>
    <@product_macro.generateProductStatusSelect name="search_publishStatus" defaultValue=search_publishStatus hasHeader=true/>

    <label>商品名称:</label>
    <input name="search_dealTitle" type="text" value='${search_dealTitle}'/>
</div>

<div style="margin-bottom:5px; display:inline;">

    <label>销售开始时间:</label>
    <input name="search_startTime" type="text" value='${search_startTime}' class="easyui-datetimebox"/>

    <label>销售结束时间:</label>
    <input name="search_endTime" type="text" value='${search_endTime}' class="easyui-datetimebox"/>

    <label>发布时间:</label>
    <input name="search_publishTime" type="text" value='${search_publishTime}' class="easyui-datetimebox"/>

    <label>商品类别:</label>
    <input name="search_categoryId" class="easyui-combotree"
           data-options="url:'${ctx}/product/category/buildProductCategoryTree',method:'get',lines:true"
           value="${search_categoryId}">
</div>
</@search_macro.search2>

<#--商品列表-->
<div id="tb" style="padding:1px;height:auto">
    <div style="margin-bottom:1px">
        <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="addProduct();" plain="true"
           title="添加商品">添加商品</a>
        <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="updateProduct();" plain="true"
           title="修改商品">修改商品</a>
    </div>
</div>
<table id="productListGrid" class="easyui-datagrid" title="商品列表" pagination="true" rownumbers="true" idField="id"
       data-options="singleSelect:true,url:'${ctx}/product/product/listProduct',method:'post',toolbar:'#tb',
			view:detailView,detailFormatter:viewDetail,striped:true,
			queryParams:{routerName:'product',
						 methods:'oosStatusInvalid,oosStatusValid,auditProduct,publishProduct,cancelPublish,deleteProduct',
						 confirmMethods:'oosStatusInvalid,oosStatusValid,auditProduct,publishProduct,cancelPublish,deleteProduct',
						 gridId:'productListGrid'}">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th field="areaName">地区</th>
        <th field="skuId">Sku ID</th>
        <th field="merchantId" formatter="Formatter.MerchantFormatter">商家</th>
        <th field="merchantSku">商家Sku</th>
        <th field="merchantCode">产品编码</th>
        <th field="dealTitle">名称</th>
        <th field="dealClass" formatter="Formatter.ProductClassFormatter">类型</th>
        <th field="dealType" formatter="Formatter.ProductTypeFormatter">商品类型</th>
        <th field="dealPrice" formatter="Formatter.PriceFormatter">销售价格</th>
        <th field="publishStatus" formatter="Formatter.ProductPublishStatusFormatter">发布状态</th>
        <th field="publishTime" formatter="Formatter.ProductPublishTimeFormatter">发布时间</th>
        <th field="vendibilityAmount">销售库存</th>
        <th field="inventoryAmount">总库存</th>
        <th field="startTime" formatter="Formatter.ProductStartTimeFormatter">销售开始时间</th>
        <th field="endTime" formatter="Formatter.ProductEndTimeFormatter">销售结束时间</th>
        <th field="id" formatter="Formatter.ProductListOperateButtonsFormatter">操作</th>
        <th field="routerCall" hidden="true"></th>
    </tr>
    </thead>
</table>

<#--商品编辑对话框-->
<div id="product_edit_dialog" class="easyui-dialog" style="width:1024px;height:668px;"
     data-options="closed:true,maximizable:true"></div>

</body>
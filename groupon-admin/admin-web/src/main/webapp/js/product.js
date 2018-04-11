//获取商品发布状态名称
function getProductPublishStatusName(status) {
    switch (status) {
        case 0:
            return "新建";
        case 1:
            return "已审核";
        case 2:
            return "已发布不显示";
        case 3:
            return "已发布";
        case 4:
            return "已发布只搜索";
        case 5:
            return "已删除";
        default:
            return "处理中";
    }
}

function saveOrUpdateProduct() {
    Submit.FormSubmit("edit_product_form", "product_edit_dialog", "productListGrid", ctx + "/product/product/saveOrUpdateProduct", null, function () {
        // 将涉及金钱的字段（以元为单位）转换为以分为单位
        $("input[name='marketPrice']").val(parseInt($('#marketPrice').numberbox('getValue') * 100));
        $("input[name='merchantPrice']").val(parseInt($('#merchantPrice').numberbox('getValue') * 100));
        $("input[name='dealPrice']").val(parseInt($('#dealPrice').numberbox('getValue') * 100));
        $("input[name='settlementPrice']").val(parseInt($('#settlementPrice').numberbox('getValue') * 100));
        // 商品描述
        $("input[name='dealDetailInfo']").val($('#dealDetail').ckeditor().editor.getData());
    });
}

function saveOrUpdateProductCategory() {
    Submit.FormSubmit("edit_product_category_form", "deal_category_edit_dialog", "dealCategoryGrid", ctx + "/product/category/saveOrUpdateProductCategory", function () {
        $("#product_category_tree").tree("reload");
    });
}

function deleteProductCategory() {
    var categoryNode = $("#product_category_tree").tree("getSelected");
    Submit.AjaxDeleteSubmit(ctx + "/product/category/deleteProductCategory", {"categoryId": categoryNode.id}, "dealCategoryGrid", "deal_category_edit_dialog", function () {
        $("#product_category_tree").tree("reload");
    });
}

// 向下移动商品类别顺序
function moveDownProductCategoryOrderNum(currentId, downId) {
    swapProductCategoryOrderNum(currentId, downId);
}

// 向上移动商品类别顺序
function moveUpProductCategoryOrderNum(currentId, upId) {
    swapProductCategoryOrderNum(currentId, upId);
}

function swapProductCategoryOrderNum(id1, id2) {
    Submit.AjaxSubmit(ctx + "/product/category/swapProductCategoryOrderNum", {
        "id1": id1,
        "id2": id2
    }, "dealCategoryGrid", function () {
        $("#product_category_tree").tree("reload");
    });
}
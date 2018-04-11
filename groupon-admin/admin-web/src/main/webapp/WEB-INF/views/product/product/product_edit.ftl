<#import "/macro/product.ftl" as product_macro/>
<#import "/macro/common.ftl" as common_macro/>

<style type="text/css">
    .td1 {
        width: 100px;
        text-align: right;
        white-space: nowrap;
    }

    .td2 {
        text-align: left;
    }
</style>

<script type="text/javascript">
    function onSelectDealCategory(node) {
        if (node.attributes.deep != 3) {
            Messager.Prompt("温馨提示", "请选择合法的商品类别");
            $('#dealCategory').combotree('clear');
        }
    }

    function onSelectArea(node) {
        $('#areaName').val(node.text);
    }
</script>

<table>
    <tr>
        <td>
        <#--商品编辑表单-->
            <form id="edit_product_form" method="post" enctype="multipart/form-data" style="border:1px solid black;">
                <input id="id" name="id" value="<#if deal??>${deal.id}<#else>0</#if>" type="hidden"/>
                <input id="skuId" name="skuId" value="${deal.skuId!}" type="hidden"/>
                <input id="imageId" name="imageId" value="${deal.imageId!}" type="hidden"/>

                <table>
                    <tr>
                        <td class="td1">
                            <label>类型：</label>
                        </td>
                        <td class="td2" colspan="3">
                        <@product_macro.generateProductClassRadio name="dealClass" defaultValue=deal.dealClass />

                            <label>商品类型：</label>
                        <@product_macro.generateProductTypeSelect name="dealType" defaultValue=deal.dealType />
                        </td>
                    </tr>

                    <tr>
                        <td class="td1">
                            <label>销售地区：</label>
                        </td>
                        <td class="td2">
                            <input id="areaName" name="areaName" value="${deal.areaName!}" hidden>
                            <input id="areaId" name="areaId" class="easyui-combotree"
                                   data-options="url:'${ctx}/area/buildAreaTree',method:'get',required:true,lines:true,onClick:onSelectArea"
                                   style="width:200px;" value="${deal.areaId!}">
                        </td>
                    </tr>

                    <tr>
                        <td class="td1">
                            <label>商家：</label>
                        </td>
                        <td class="td2" style="width:180px;">
                            <input id="merchantId" name="merchantId" class="easyui-combobox"
                                   data-options="valueField:'id',textField:'name',url:'${ctx}/merchant/loadAll',editable:false"
                                   value="${deal.merchantId!}">
                        </td>

                        <td class="td1">
                            <label>商家Sku：</label>
                        </td>
                        <td class="td2">
                            <input id="merchantSku" name="merchantSku" class="easyui-validatebox" type="text"
                                   value="${deal.merchantSku!}" data-options="required:true"/>
                        </td>
                    </tr>

                    <tr>
                        <td class="td1">
                            <label>商品名称：</label>
                        </td>
                        <td class="td2" colspan="3">
                            <input id="dealTitle" name="dealTitle" class="easyui-validatebox"
                                   data-options="required:true" style="width:98%" value="${deal.dealTitle!}"/>
                        </td>
                    </tr>

                    <tr>
                        <td class="td1">
                            <label>商品类别：</label>
                        </td>
                        <td class="td2">
                            <input id="dealCategory" name="categoryId" class="easyui-combotree"
                                   data-options="url:'${ctx}/product/category/buildProductCategoryTree',method:'get',required:true,lines:true,onClick:onSelectDealCategory"
                                   style="width:200px;" value="${deal.categoryId}">
                        </td>

                        <td class="td1">
                            <label>商品等级：</label>
                        </td>
                        <td class="td2">
                        <@product_macro.generateProductLevelSelect name="dealLevel" defaultValue=deal.dealLevel hasHeader=false/>
                        </td>
                    </tr>

                    <tr>
                        <td class="td1"><label>商品图片：</label></td>
                        <td class="td2">
                            <input type="file" name="dealImgFile" id="dealImgFile" style="display:inline"/>
                        </td>
                        <td class="td1">
                            <label>产品编码：</label>
                        </td>
                        <td class="td2">
                            <input id="merchantCode" name="merchantCode" class="easyui-validatebox"
                                   data-options="required:true" value="${deal.merchantCode}"/>
                        </td>

                    </tr>

                    <tr>
                        <td class="td1">
                            <label>销售开始时间：</label>
                        </td>
                        <td class="td2">
                            <input id="startTime" name="startTime" type="text"
                                   value="${(deal.startTime?string("yyyy-MM-dd HH:mm:ss"))!}"
                                   class="easyui-datetimebox"/>
                        </td>

                        <td class="td1">
                            <label>销售结束时间：</label>
                        </td>
                        <td class="td2">
                            <input id="endTime" name="endTime" type="text"
                                   value="${(deal.endTime?string("yyyy-MM-dd HH:mm:ss"))!}" class="easyui-datetimebox"/>
                        </td>
                    </tr>

                    <tr>
                        <td class="td1">
                            <label>市场价格(单位为元)：</label>
                        </td>
                        <td class="td2">
                            <input name="marketPrice" type="hidden"/>
                            <input id="marketPrice" class="easyui-numberbox" data-options="precision:2"
                                   value="<#if deal.marketPrice??>${deal.marketPrice / 100}</#if>"/>
                        </td>

                        <td class="td1">
                            <label>商家价格(单位为元)：</label>
                        </td>
                        <td class="td2">
                            <input name="merchantPrice" type="hidden"/>
                            <input id="merchantPrice" class="easyui-numberbox" data-options="precision:2,required:true"
                                   value="<#if deal.merchantPrice??>${deal.merchantPrice / 100}</#if>"/>
                        </td>
                    </tr>

                    <tr>
                        <td class="td1">
                            <label>价格(单位为元)：</label>
                        </td>
                        <td class="td2">
                            <input name="dealPrice" type="hidden"/>
                            <input id="dealPrice" class="easyui-numberbox" data-options="precision:2,required:true"
                                   value="<#if deal.dealPrice??>${deal.dealPrice / 100}</#if>"/>
                        </td>

                        <td class="td1">
                            <label>结算价格(单位为元)：</label>
                        </td>
                        <td class="td2">
                            <input name="settlementPrice" type="hidden"/>
                            <input id="settlementPrice" class="easyui-numberbox" data-options="precision:2"
                                   value="<#if deal.settlementPrice??>${deal.settlementPrice / 100}</#if>"/>
                        </td>
                    </tr>

                    <tr>
                        <td class="td1">
                            <label>总库存：</label>
                        </td>
                        <td class="td2">
                            <input id="inventoryAmount" name="inventoryAmount" class="easyui-numberbox"
                                   data-options="precision:0,required:true" value="${deal.inventoryAmount!}"/>
                        </td>

                        <td class="td1">
                            <label>可售数量：</label>
                        </td>
                        <td class="td2">
                            <input id="vendibilityAmount" name="vendibilityAmount" class="easyui-numberbox"
                                   data-options="precision:0" value="${deal.vendibilityAmount!}"/>
                        </td>
                    </tr>

                    <tr>
                        <td class="td1">
                            <label>最大可购买数量：</label>
                        </td>
                        <td class="td2">
                            <input id="maxPurchaseCount" name="maxPurchaseCount" class="easyui-numberbox"
                                   data-options="precision:0" value="${deal.maxPurchaseCount!}"/>
                        </td>

                        <td class="td1">
                            <label>积分：</label>
                        </td>
                        <td class="td2">
                            <input id="bonusPoints" name="bonusPoints" class="easyui-numberbox"
                                   data-options="precision:0" value="${deal.bonusPoints!}"/>
                        </td>
                    </tr>

                    <tr>
                        <td class="td1"><label>商品描述：</label></td>
                        <td class="td2" colspan="3">
                            <input name="dealDetailInfo" type="hidden"/>
                            <textarea id="dealDetail" rows="5"
                                      style="width:100%">${deal.dealDetail.dealDetail!}</textarea>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="4" align="center">
                            <div id="dlg-buttons">
                                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"
                                   onclick="saveOrUpdateProduct();">保存</a>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
        </td>
        <td valign="top">
        <#if deal.imageGenPath??>
            <img src="${deal.imageGenPath}" style="border:1px solid black;"/>
        </#if>
        </td>
    </tr>
</table>
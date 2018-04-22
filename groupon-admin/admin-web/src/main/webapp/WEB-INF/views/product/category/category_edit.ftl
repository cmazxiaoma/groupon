<#import "/macro/common.ftl" as common_macro/>
<#import "/macro/product.ftl" as product_macro/>
<#--商品类别编辑表单-->
<style type="text/css">
	.td1 {
		width: 60px;
		text-align: right;
		white-space: nowrap;
	}
	.td2 {
		text-align: left;
	}
</style>
<div style="padding-right:10px; padding-bottom:10px; width:500px;">
    <form id="edit_product_category_form" method="post">
		<input name="id" value="<#if category.id??>${category.id}<#else>0</#if>" type="hidden"/>
    	<input name="parentId" value="<#if category.id??>${category.parentId}<#else>0</#if>" type="hidden"/>
    	<input name="publishStatus" value="<#if category.publishStatus??>${category.publishStatus}<#else>0</#if>" type="hidden"/>
    	<input name="deep" value="<#if category.deep??>${category.deep}<#else>0</#if>" type="hidden"/>

        <table width="100%">
            <tr>
                <td class="td1">名称：</td>
                <td class="td2">
                	<input class="easyui-validatebox" type="text" name="name" data-options="required:true" style="width:90%" value="${category.name}"/>
                </td>
            </tr>

            <tr>
                <td class="td1">URL标识：</td>
                <td class="td2">
                	<input class="easyui-validatebox" type="text" name="urlName" data-options="required:true" style="width:90%" value="${category.urlName}"/>
                </td>
            </tr>

            <tr>
                <td class="td1">排序：</td>
                <td class="td2"><@product_macro.generateProductLevelSelect name="orderNum" defaultValue=category.orderNum hasHeader=false/></td>
            </tr>

        </table>
    </form>

    <center>
   		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveOrUpdateProductCategory();">保存</a>
   	</center>
</div>
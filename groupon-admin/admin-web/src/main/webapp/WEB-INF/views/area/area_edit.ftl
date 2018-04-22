<#import "/macro/common.ftl" as common_macro/>
<#import "/macro/area.ftl" as area_marco/>
<style type="text/css">
    .td1 {
        width: 100px;
        text-align: right;
        white-space: nowrap;
    }

    .td2 {
        width: 350px;
        text-align: left;
    }
</style>

<form id="edit_area_form" method="post">
    <input id="id" name="id" value="<#if area??>${area.id}<#else></#if>" type="hidden"/>
    <input name="parentId" value="<#if area??>${area.parentId}<#else>0</#if>" type="hidden"/>
    <table width="100%">
        <tr>
            <td class="td1">
                <label>名称：</label>
            </td>
            <td class="td2">
                <input id="name" name="name" class="easyui-validatebox" type="text" value="${area.name!}"
                       data-options="required:true">
            </td>
        </tr>

        <tr>
            <td class="td1">
                <label>级别：</label>
            </td>
            <td class="td2">

            <@area_marco.generateAreaTypeSelect name="type"
                defaultValue=area.type hasHeader=false/>
            </td>
        </tr>

        <tr>
            <td align="center" colspan="2">
                <div id="dlg-buttons">
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveOrUpdateArea();">保存</a>
                </div>
            </td>
        </tr>

    </table>
</form>
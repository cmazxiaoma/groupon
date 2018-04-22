<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#assign shiro = JspTaglibs["/WEB-INF/tlds/shiro.tld"]>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<#--<title>${title}</title>-->
    <title>Admin</title>
    <link rel="icon" href="images/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon"/>
    <link rel="bookmark" href="images/favicon.ico" type="image/x-icon"/>

    <link rel="stylesheet" type="text/css" href="${ctx}/css/main.css">

    <link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.3.2/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.3.2/themes/icon.css">
    <script type="text/javascript" src="${ctx}/js/jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin.js"></script>
    <script type="text/javascript" src="${ctx}/js/datagrid-detailview.js"></script>
    <script type="text/javascript" src="${ctx}/js/datagrid-defaultview.js"></script>
    <script type="text/javascript" src="${ctx}/js/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="${ctx}/js/ckeditor/adapters/jquery.js"></script>
    <script type="text/javascript">
        var ctx = "${ctx}";

        function treeOnClick(node) {
            if (node.attributes != undefined && node.attributes.url != undefined
                    && node.attributes.url != '') {
                //$.each( node, function(i, field) {
                //	alert(i + '--' + field);
                //});
//					window.location = ctx + node.attributes.url;
                console.log(node);
                console.log(node.attributes);
                $("#dataArea").panel({"href": ctx + node.attributes.url});
                $("#dataArea").panel("setTitle", node.text);
            }
        }
    </script>
</head>
<body>
<#--<#include "header.ftl">-->
<body class="easyui-layout">
<div data-options="region:'north',split:true" style="height:79px;" id="header">
    <div id="logo">
    </div>
    <div id="logout">
        <a href="${ctx}/logout">退出</a>
    </div>
</div>

<div data-options="region:'west',title:'导航',split:true" style="width:200px;">
    <ul id="navigationTree" class="easyui-tree" data-options="animate:true,lines:true,
		    	 		onClick:treeOnClick,url:'${ctx}/buildFunctionTreeForNavigation'">
</div>
<div id="dataArea" data-options="region:'center'" title="数据" style="padding:5px;background:#eee;">
</div>

</body>
<#--<#include "footer.ftl">-->
</body>
</html>
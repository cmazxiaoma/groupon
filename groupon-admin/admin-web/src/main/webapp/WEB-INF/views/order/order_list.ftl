<#import "/macro/order.ftl" as order_macro/>
<#import "/macro/search.ftl" as search_macro/>
<body>
<script type="text/javascript">

    function viewDetail(value, row) {
        if (row.orderDetails == null) {
            return '';
        }
        var productNum = row.orderDetails.length;
        var exception = row.exceptionCause == null ? "" : row.exceptionCause;
        if (row.orderDetails == null) {
            return '<table><tr><td colspan="4">异常原因:' + exception + '</td></tr><tr><td rowspan=2 >订单详情</td></tr></table>';
        }
        var html = '<table>';
        if (exception != '') {
            html += '<tr><td colspan="4">异常原因:' + exception + '</td></tr>'
        }
        html += '<tr><td rowspan=' + (productNum * 2) + '>订单详情</td>';
        for (var i = 0; i < productNum; i++) {
            var orderDetail = row.orderDetails[i];
            html += '</tr><td><p>商品编号:' + (orderDetail.merchantCode == null ? '' : orderDetail.merchantCode) + '</p><p>商品名称:' + orderDetail.dealTitle + '</p></td>' +
                    '<td><p>订单金额:' + '￥' + ((orderDetail.dealPrice / 100).toFixed(2)) + '</p><p>结算金额:' + '￥' + ((orderDetail.settlementPrice / 100).toFixed(2)) + '</p></td>' +
                    '<td><p>商品数量:' + orderDetail.dealCount + '</p><p>商品ID:' + orderDetail.dealId + '</p></td></tr>';
        }
        html += '</table>';
        return html;
    }

    function payTypeFormatter(value, row) {
        switch (value) {
//                1.微信;2.支付宝;3.货到付款
            case 1:
                return '微信';
            case 2:
                return '支付宝';
            case 3:
                return '货到付款';
            default:
                return '未知类型';
        }
    }

    function orderStatusFormatter(value, row) {
        var statusClass;
        var statusName;
        switch (value) {
//               -1:已删除 1:待付款;2:已付款;3:待发货;4:配送中;5:完成;6:已取消;7:已关闭
            case -1:
                statusClass = 'color:green';
                statusName = '已删除';
                break;
            case 1:
                statusClass = 'color:red';
                statusName = '待付款';
                break;
            case 2:
                statusClass = 'color:green';
                statusName = '已付款';
                break;
            case 3:
                statusClass = 'color:green';
                statusName = '待发货';
                break;
            case 4:
                statusClass = 'color:green';
                statusName = '配送中';
                break;
            case 5:
                statusClass = 'color:green';
                statusName = '完成';
                break;
            case 6:
                statusClass = 'color:gray';
                statusName = '已取消';
                break;
            case 7:
                statusClass = 'color:red';
                statusName = '已关闭';
                break;
            default:
                statusClass = 'color:red';
                statusName = '错误的状态';
                break;
        }
        return '<span style="' + statusClass + '">' + statusName + '</span>';
    }
</script>
<@search_macro.search2 gridId="orderGrid">
<div class="">
    <label>订单类型:</label>
    <@order_macro.generateOrderTypeSelect name="search_orderType" defaultValue=search_orderType/>
    <label>订单状态:</label>
    <@order_macro.generateOrderStatusSelect name="search_orderStatus" defaultValue=search_orderStatus/>
    <label>商家订单号:</label>
    <input name="search_merchantOrderId" type="text" value='${search_merchantOrderId}'/>
</div>
<div class="">
    <label>收件人:</label>
    <input name="search_userRealname" type="text" value='${search_userRealname}'/>
    <label>开始时间:</label>
    <input name="search_startTime" type="text" class="easyui-datetimebox" value='${search_startTime}'
           class="form_datetime"/>
    <label>结束时间:</label>
    <input name="search_endTime" type="text" class="easyui-datetimebox" value='${search_endTime}'
           class="form_datetime"/>
</div>
</@search_macro.search2>

<table id="orderGrid" class="easyui-datagrid" title="订单列表" pagination="true" rownumbers="true" idField="id"
       data-options="singleSelect:true,url:'${ctx}/order/management/listOrder',method:'post',toolbar:'#tb',
			pagePosition:'both',view:detailView,detailFormatter:viewDetail,striped:true,
			queryParams:{routerName:'order',
						 methods:'deliver,complete,reorder,cancel,delete',
						 confirmMethods:'deliver,complete,reorder,cancel,delete',
						 gridId:'orderGrid'}">
    <thead>
    <tr>
        <th field="receiver">收件人</th>
        <th field="phone">电话</th>
        <th field="address">地址</th>
        <th field="payType" formatter="payTypeFormatter">支付类型</th>
        <th field="orderStatus" formatter="orderStatusFormatter">状态</th>
        <th field="totalPrice" formatter="Formatter.PriceFormatter">订单金额</th>
        <th field="totalSettlementPrice" formatter="Formatter.PriceFormatter">结算金额</th>
        <th field="merchantOrderPrice" formatter="Formatter.PriceFormatter">商家实收金额</th>
        <th field="id">订单号</th>
        <th field="createTime" formatter="Formatter.DateTimeFormatter">创建时间</th>
        <th field="routerCall" hidden="true"></th>
    </tr>
    </thead>
</table>
</body>
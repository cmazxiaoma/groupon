<div class="topBar">
    <div class="comWidth">
        <div class="leftArea">
            <a href="${ctx}" class="collection">收藏慕课</a>
        </div>
        <div class="rightArea">
        <#if username??>
            <a href="${ctx}/home/index">${username!}</a> 欢迎来到慕课网！&nbsp;&nbsp;
            <a href="${ctx}/home/order">我的订单</a>&nbsp;&nbsp;
            <a href="${ctx}/home/message">消息</a>&nbsp;&nbsp;
            <a href="${ctx}/logout">[退出]</a>
        <#else>
            欢迎来到慕课网！<a href="${ctx}/login">[登录]</a><a href="${ctx}/reg">[免费注册]</a>
        </#if>
        </div>
    </div>
</div>
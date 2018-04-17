<#import "macro/paging.ftl" as paging_macro>
<#import "macro/common.ftl" as common>
<html>
<head>
    <title>收藏商品</title>
    <script type="text/javascript">
        var ctx = '${ctx}';

        function delFavorite(favoriteId) {
            Submit.AjaxSubmit1(ctx + '/home/delFavorite/' + favoriteId, "", "post",
                    function(result) {
                        console.log(result);
                        window.location.href = ctx + "/home/favorite";
                    },
                    function(result) {
                    console.log(result);});
        }
    </script>
</head>
<body>
<div class="hr_15"></div>
<div class="comWidth clearfix products">
    <div class="leftArea">
    <#include "user/left_menu.ftl">
    </div>
    <div class="rightArea">
        <div class="shopping_item">
            <h3 class="shopping_tit">我的收藏</h3>
            <div class="shopping_cont pb_10">
                <div class="cart_inner">
                    <div class="cart_head clearfix">
                        <div class="cart_item t_name">商品名称</div>
                        <div class="cart_item t_price">单价</div>
                        <div class="cart_item t_return">折扣</div>
                        <div class="cart_item t_status">状态</div>
                        <div class="cart_item t_operate">操作</div>
                    </div>
                <#if favorites??>
                    <#list favorites as favorite>
                        <div class="cart_cont clearfix">
                            <div class="cart_item t_name">
                                <div class="cart_shopInfo clearfix">
                                    <div>
                                    <#--<input type="checkbox" id="${favorite.favorite.id}" onclick="check(this, ${favorite.favorite.id})" style="float:left; margin-top:40px; margin-right: 10px">-->
                                        <img src="${helper.getDealImageUrlForDealList(favorite.deal)}" alt="">
                                        <div class="cart_shopInfo_cont">
                                            <p class="cart_link"><a
                                                    href="${ctx}/item/${favorite.deal.skuId}">${favorite.deal.dealTitle}</a>
                                            </p>
                                            <p class="cart_info">7天退换</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="cart_item t_price" style="color: #FF7300;">
                                <@common.formatPrice favorite.deal.dealPrice/>
                            </div>
                            <div class="cart_item t_return"><@common.formatDiscount favorite.deal.discount/></div>
                            <div class="cart_item t_status">
                                <#if favorite.deal.start>
                                    进行中
                                <#elseif favorite.deal.end>
                                    已结束
                                <#else>
                                    未开始
                                </#if>
                            </div>
                            <div class="cart_item t_operate">
                                <#if favorite.deal.start>
                                    <a href="${ctx}/settlement/${favorite.deal.skuId}" class="btn_link" onclick="">购买&nbsp;&nbsp;</a>
                                <#else>
                                    <a href="${ctx}/item/${favorite.deal.skuId}" class="btn_link" onclick="">查看&nbsp;&nbsp;</a>
                                </#if>
                                <a href="javascript:delFavorite(${favorite.favorite.id})" class="btn_link">删除</a>
                            </div>
                        </div>
                    </#list>
                </#if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>


<#import "macro/paging.ftl" as paging_macro>
<#import "macro/common.ftl" as common>
<html>
<head>
    <title>分类</title>

    <script type="text/javascript">

        function addToCart(skuId) {
        <#if username??>
//            Submit.AjaxSubmit(ctx + '/cart/default/' + skuId);
            Submit.AjaxSubmit1(ctx + '/cart/default/' + skuId, "", "post",
                    function(result) {
                        console.log(result);
                        //ajax刷新购物车数量
                        console.log($(".logoBar .shopNum"));
                        $(".logoBar .shopNum").text(result);
                    }, function(result) {
                        console.log(result);
                    });
        <#else >
            window.location = ctx + '/login';
        </#if>
        }
    </script>
</head>
<body>
<div class="hr_15"></div>
<div class="comWidth clearfix products">
    <div class="leftArea">
        <div class="leftNav vertical">
        <#if dealCategory??>
            <h3 class="nav_title">${dealCategory.name}</h3>
            <#list dealCategory.children as child>
                <div class="nav_cont">
                    <h3>${child.name}</h3>
                    <ul class="navCont_list clearfix">
                        <#list child.children as c>
                            <li><a href="${ctx}/category/${c.urlName}">${c.name}</a></li>
                        </#list>
                    </ul>
                </div>
            </#list>
        </#if>
        </div>
    </div>

    <div class="rightArea">
        <div class="addInfo">
            <div class="fr screen_text">
                        <span class="shop_number">
                            共<em>${pagingDealList.total}</em>件
                        </span>
            </div>
        </div>
        <div class="products_list screening_list_more clearfix">
        <#list pagingDealList.rows as deal>
            <div class="item">
                <div class="item_cont">
                    <div class="img_item">
                        <a href="${ctx}/item/${deal.skuId}"><img src="${helper.getDealImageUrlForDealList(deal)}"
                                                                 alt=""></a>
                    </div>
                    <p><a href="${ctx}/item/${deal.skuId}">${deal.dealTitle}</a></p>
                    <p class="money"><@common.formatPrice deal.dealPrice/></p>
                    <p><a href="#" class="addCar" onclick="addToCart(${deal.skuId})">加入购物车</a></p>
                </div>
            </div>
        </#list>
        </div>
        <div class="hr_25"></div>
    <#if pagingDealList??>
        <@paging_macro.paging pagingList=pagingDealList url="${ctx}/category/${dealCategory.urlName}"/>
    </#if>
    </div>
</div>
</body>
</html>

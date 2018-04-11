<#import "macro/paging.ftl" as paging_macro>
<#import "macro/common.ftl" as common>
<html>
<head>
    <title>搜索</title>
</head>
<body>
<div class="hr_15"></div>
<div class="comWidth clearfix products">
    <div class="leftArea">
        <div class="leftNav vertical">
        <#if categoryList??>
            <#list categoryList as category>
                <h3 class="nav_title"><a href="${ctx}/category/${category.urlName}">${category.name}</a></h3>
                <#list category.children as child>
                    <div class="nav_cont">
                        <h3>${child.name}</h3>
                        <ul class="navCont_list clearfix">
                            <#list child.children as c>
                                <li><a href="${ctx}/category/${c.urlName}">${c.name}</a></li>
                            </#list>
                        </ul>
                    </div>
                </#list>
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
                    <p><a href="${ctx}/cart/default/${deal.skuId}" class="addCar">加入购物车</a></p>
                </div>
            </div>
        </#list>
        </div>
        <div class="hr_25"></div>
    <#if pagingDealList??>
        <@paging_macro.searchPaging pagingList=pagingDealList/>
    </#if>
    </div>
</div>
</body>
</html>

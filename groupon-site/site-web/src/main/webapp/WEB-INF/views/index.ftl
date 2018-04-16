<#import "macro/common.ftl" as common>
<html>
<head>
    <title>首页</title>
</head>
<body>
<div class="banner comWidth clearfix">
    <div class="banner_bar banner_big">
        <ul class="imgBox">
            <li><a href="#"><img src="${ctx}/images/banner/banner_01.jpg" alt="banner"></a></li>
            <li><a href="#"><img src="${ctx}/images/banner/banner_01.jpg" alt="banner"></a></li>
        </ul>
        <div class="imgNum">
            <a href="#" class="active"></a>
            <a href="#"></a>
            <a href="#"></a>
            <a href="#"></a>
        </div>
    </div>
</div>

<#if indexCategoryDealDTOs??>
    <#list indexCategoryDealDTOs as dto>
    <div class="shopTit comWidth">
        <span class="icon"></span>
        <h3>${dto.category.name}</h3>
        <a href="${ctx}/category/${dto.category.urlName}" class="more">更多&gt;&gt;</a>
    </div>

    <div class="shopList comWidth clearfix">
        <div class="leftArea">
            <div class="banner_bar banner_sm">
                <ul class="imgBox">
                    <li><a href="#"><img src="images/banner/banner_sm_01.jpg" alt="banner"></a></li>
                    <li><a href="#"><img src="images/banner/banner_sm_02.jpg" alt="banner"></a></li>
                </ul>
                <div class="imgNum">
                    <a href="#" class="active"></a><a href="#"></a><a href="#"></a><a href="#"></a>
                </div>
            </div>
        </div>

        <div class="rightArea">
            <#if dto.first?exists>
                <div class="shopList_top clearfix">
                    <#list dto.first as deal>
                        <div class="shop_item">
                            <div class="shop_img">
                                <a href="${ctx}/item/${deal.skuId}"><img
                                        src="${helper.getDealImageUrlForIndexDeal1List(deal)}" alt=""></a>
                            </div>
                            <div class="shop_item_title">${deal.dealTitle}</div>
                            <p><@common.formatPrice deal.dealPrice/></p>
                        <#--<p>${deal.dealPrice}</p>-->
                        </div>
                    </#list>
                </div>
            </#if>

            <#if dto.second?exists>
                <div class="shopList_sm clearfix">
                    <#list dto.second as deal>
                        <div class="shopItem_sm">
                            <div class="shopItem_smImg">
                                <a href="${ctx}/item/${deal.skuId}"><img
                                        src="${helper.getDealImageUrlForIndexDeal2List(deal)}" alt=""></a>
                            </div>
                            <div class="shopItem_text">
                                <div>${deal.dealTitle}</div>
                                <p><@common.formatPrice deal.dealPrice/></p>
                            </div>
                        </div>
                    </#list>
                </div>
            </#if>

        </div>

    </div>
    </#list>
</#if>

</body>
</html>
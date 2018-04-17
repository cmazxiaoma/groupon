<#import "macro/common.ftl" as common>
<html>
<head>
    <title>首页</title>
    <script language="JavaScript">
        var caruselInit = {
            swiper : function(id) {
                var swiper = new Swiper(id, {
                    autoplay: 5000,
                    speed: 500,
                    paginationType : 'bullets',
                    autoHeight: true,
                    pagination : '.swiper-pagination',
                    scrollbar:'.swiper-scrollbar',
                    freeMode : false,
                    autoplayDisableOnInteraction : false,
                    grabCursor : true,
                    parallax : true,
                    setWrapperSize :true,
                    roundLengths : true,
                });
            }
        };

        $(function() {
           caruselInit.swiper();
        });
    </script>
</head>
<body>
<div class="banner comWidth clearfix">
    <div class="banner_bar banner_big">
        <!-- swiper -->
        <div class="swiper-container" id="carousel">

            <div class="swiper-wrapper">
                <#if carouselList??>
                    <#list carouselList as carousel>
                        <div class="swiper-slide">
                            <img src="${helper.getDealImageUrlForIndexDeal1List(carousel)}" alt="DCAMPUS" onclick="javascript:">
                        </div>
                    </#list>
                </#if>
            </div>

            <div class="swiper-pagination" id="carousel_swiper_pagination"></div>
            <div class="swiper-scrollbar" id="carousel_swiper_scrollbar"></div>
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
                <!-- swiper -->
                <div class="swiper-container" id="sm_carusel_${dto.category.id}">
                    <#if smCarouseList??>
                        <#list smCarouseList as smCarouse>
                            <div class="swiper-slide">
                                <img src="${helper.getDealImageUrlForIndexDeal2List(smCarouse)}" alt="DCAMPUS" onclick="javascript:">
                            </div>
                        </#list>
                    </#if>

                    <div class="swiper-pagination" id="sm_carousel_${dto.category.id}_swiper_pagination"></div>
                    <div class="swiper-scrollbar" id="sm_carousel_${dto.category.id}_swiper_scrollbar"></div>
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
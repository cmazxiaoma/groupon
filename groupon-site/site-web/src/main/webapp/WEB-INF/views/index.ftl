<#import "macro/common.ftl" as common>
<html>
<head>
    <title>首页</title>
    <script language="JavaScript">
        var initBigCarousel = {
             method0: function() {
                var swiper = new Swiper("#carousel", {
                    autoplay: 5000,
                    speed: 500,
                    paginationType : 'bullets',
                    autoHeight: true,
                    pagination : "#carousel_swiper_pagination",
                    scrollbar: "#carousel_swiper_scrollbar",
                    freeMode : false,
                    autoplayDisableOnInteraction : false,
                    grabCursor : true,
                    parallax : true,
                    setWrapperSize :true,
                    roundLengths : true,
                    prevButton:'#carousel_swiper_prev',
                    nextButton:'#carousel_swiper_next',
                });
            }
        };

        var initSmallCarousel = {
            method0: function(id) {
                var swiper = new Swiper(id, {
                    autoplay: 5000,
                    speed: 500,
                    paginationType : 'bullets',
                    autoHeight: true,
                    pagination : id + "_swiper_pagination",
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
            //初始化大carousel
           initBigCarousel.method0();
           //初始化小carousel
            var size = '${size}';
            console.log("test");
            console.log(size);

            for (var i = 0; i < size; i++) {
                console.log("#sm_carousel_" + i);
                initSmallCarousel.method0("#sm_carousel_" + i);
            }
        });

        function forwardToDealDetail(skuId) {
            window.location.href = ctx + "/item/" + skuId;
        }

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
                            <img src="${helper.getDealImageUrlForIndexDeal1List(carousel)}"
                                 alt="DCAMPUS" onclick="forwardToDealDetail(${carousel.skuId})">
                        </div>
                    </#list>
                </#if>
            </div>

            <div class="swiper-pagination" id="carousel_swiper_pagination"></div>
            <div class="swiper-button-prev" id="carousel_swiper_prev"></div>
            <div class="swiper-button-next" id="carousel_swiper_next"></div>
            <div class="swiper-scrollbar" id="carousel_swiper_scrollbar"></div>
        </div>
    </div>
</div>

<#assign index = 0>
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
                    <div class="swiper-container" id="sm_carousel_${index}">
                        <div class="swiper-wrapper">
                            <#if smCarouselListList?? && smCarouselListList?size gt 0>
                                <#if smCarouselListList[index]?? && smCarouselListList[index]?size gt 0>
                                    <#list smCarouselListList[index] as smCarousel>
                                        <div class="swiper-slide">
                                            <img src="${helper.getDealImageUrlForDealList(smCarousel)}"
                                                 alt="DCAMPUS" onclick="forwardToDealDetail(${smCarousel.skuId})">
                                        </div>
                                    </#list>
                                </#if>
                            </#if>
                        </div>
                        <div class="swiper-pagination" id="sm_carousel_${index}_swiper_pagination"></div>
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
        <#assign index = index + 1>
    </#list>
</#if>

</body>
</html>
<div class="logoBar">
    <div class="comWidth">
        <div class="logo fl">
            <a href="${ctx}"><img src="${ctx}/images/logo.jpg" alt="DCAMPUS"></a>
            <span style="font-size: 17px; color: #000000">${areaName}</span> <a href="${ctx}/area/index"
                                                                                style="color: #ffffff">切换城市</a>
        </div>
        <div class="search_box fl">
            <form action="/search" id="search_form" method="get">
                <input type="hidden" id="search_page" name="page" value="1">
                <input type="text" id="search_s" name="s" <#if s??>value="${s}"</#if> class="search_text fl">
                <input type="submit" value="搜 索" class="search_btn fr">
            </form>
        </div>
        <div class="shopCar fr">
            <span class="shopText fl"><a href="${ctx}/cart">购物车</a></span>
            <span class="shopNum fl">${cartSize!0}</span>
        </div>
    </div>
</div>
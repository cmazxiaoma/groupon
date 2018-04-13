<#import "macro/common.ftl" as common>
<html>
<head>
    <title>商品介绍</title>
    <script type="text/javascript">
        function dealCount(type) {
            var maxPurchaseCount = '${deal.maxPurchaseCount}';
            if (type == 1) {
                var count = Number(document.getElementById('count').value);
                if (count >= maxPurchaseCount) {
                    document.getElementById('count').value = maxPurchaseCount;
                } else {
                    document.getElementById('count').value = count + 1;
                }
            } else {
                var count = Number(document.getElementById('count').value);
                if (count <= 1) {
                    document.getElementById('count').value = 1;
                } else {
                    document.getElementById('count').value = count - 1;
                }
            }
        }

        function addToCart(skuId) {
        <#if username??>
            Submit.AjaxSubmit(ctx + '/cart/default/' + skuId);
        <#else >
            window.location = ctx + '/login';
        </#if>
        }

        function addToFavorite(skuId) {
        <#if username??>
            Submit.AjaxSubmit(ctx + '/home/favorite/' + skuId);
        <#else >
            window.location = ctx + '/login';
        </#if>
        }

        function addToStartRemind(skuId) {
        <#if username??>
            Submit.AjaxSubmit(ctx + '/home/remind/' + skuId);
        <#else >
            window.location = ctx + '/login';
        </#if>
        }

    </script>
</head>
<body>
<div class="userPosition comWidth">
    <strong><a href="${ctx}">首页</a></strong>
    <span>&nbsp;&gt;&nbsp;</span>
    <a href="#">数码相机</a>
    <span>&nbsp;&gt;&nbsp;</span>
    <em>${deal.dealTitle}</em>
</div>
<div class="description_info comWidth">
    <div class="description clearfix">
        <div class="leftArea">
            <div class="description_imgs">
                <div class="big">
                    <img src="${helper.getDealImageUrlForDealView(deal)}" alt="">
                </div>
            <#--<ul class="des_smimg clearfix">-->
            <#--<li><a href="#"><img src="${ctx}/images/des_sm.jpg" class="active" alt=""></a></li>-->
            <#--<li><a href="#"><img src="${ctx}/images/des_sm2.jpg" alt=""></a></li>-->
            <#--<li><a href="#"><img src="${ctx}/images/des_sm.jpg" alt=""></a></li>-->
            <#--<li><a href="#"><img src="${ctx}/images/des_sm2.jpg" alt=""></a></li>-->
            <#--<li><a href="#"><img src="${ctx}/images/des_sm.jpg" alt=""></a></li>-->
            <#--</ul>-->
            </div>
        </div>
        <div class="rightArea">
            <div class="des_content">
                <h3 class="des_content_tit">${deal.dealTitle}</h3>
                <div class="dl clearfix">
                    <div class="dt">慕课价</div>
                    <div class="dd clearfix"><span
                            class="des_money"><em>￥</em><@common.formatPrice deal.dealPrice/></span></div>
                </div>
                <div class="dl clearfix">
                    <div class="dt">优惠</div>
                    <div class="dd clearfix"><span class="hg"><i
                            class="hg_icon">满换购</i><em>购ipad加价优惠够配件或USB充电插座</em></span></div>
                </div>
                <div class="des_position">
                    <div class="dl">
                        <div class="dt des_num">数量</div>
                        <div class="dd clearfix">
                            <div class="des_number">
                                <div class="reduction" onclick="dealCount(0)">-</div>
                                <div class="des_input">
                                    <input type="text" id="count" name="count" value="1">
                                </div>
                                <div class="plus" onclick="dealCount(1)">+</div>
                            </div>
                            <span class="xg">限购<em>${deal.maxPurchaseCount}</em>件</span>
                        </div>
                    </div>
                </div>
                <div class="remind_time">
                <@common.formatDateTime deal.startTime/>开抢
                </div>
                <div class="shop_buy">
                <#if deal.start>
                    <a href="${ctx}/settlement/${deal.skuId}" class="buy_btn"></a>
                <#else>
                <#--<a href="${ctx}/settlement/${deal.skuId}" class="remind_btn"></a>-->
                    <a href="#" onclick="addToStartRemind(${deal.skuId})" class="remind_btn"></a>
                </#if>
                    <span class="line"></span>
                    <a href="#" onclick="addToCart(${deal.skuId})" class="cart_btn"></a>
                    <span class="line"></span>
                    <a href="#" onclick="addToFavorite(${deal.skuId})" class="collection_btn"></a>
                </div>
                <div class="notes">
                    注意：此商品可提供普通发票，不提供增值税发票。
                </div>
            </div>
        </div>
    </div>
</div>
<div class="hr_15"></div>
<div class="des_info comWidth clearfix">
    <div class="leftArea">
        <div class="recommend">
            <h3 class="tit">同价位</h3>
            <div class="item">
                <div class="item_cont">
                    <div class="img_item">
                        <a href="#"><img src="${ctx}/images/shopImg.jpg" alt=""></a>
                    </div>
                    <p><a href="#">文字介绍文字介绍文字介绍文字介绍文字介绍文字介绍</a></p>
                    <p class="money">￥888</p>
                </div>
            </div>
            <div class="item">
                <div class="item_cont">
                    <div class="img_item">
                        <a href="#"><img src="${ctx}/images/shopImg.jpg" alt=""></a>
                    </div>
                    <p><a href="#">文字介绍文字介绍文字介绍文字介绍文字介绍文字介绍</a></p>
                    <p class="money">￥888</p>
                </div>
            </div>
            <div class="item">
                <div class="item_cont">
                    <div class="img_item">
                        <a href="#"><img src="${ctx}/images/shopImg.jpg" alt=""></a>
                    </div>
                    <p><a href="#">文字介绍文字介绍文字介绍文字介绍文字介绍文字介绍</a></p>
                    <p class="money">￥888</p>
                </div>
            </div>
        </div>
    </div>
    <div class="rightArea">
        <div class="des_infoContent">
            <ul class="des_tit">
                <li class="active"><span>产品介绍</span></li>
                <li><span>产品评价(12312)</span></li>
            </ul>
        <#--<div class="ad">-->
        <#--<a href="#"><img src="${ctx}/images/ad.jpg"></a>-->
        <#--</div>-->
            <div class="info_text">
                <div class="info_tit">
                    <h3>${deal.dealTitle!}</h3>
                </div>
                <p>${deal.dealDetail.dealDetail!}</p>
            </div>
        </div>
        <div class="hr_15"></div>
        <div class="des_infoContent">
            <h3 class="shopDes_tit">商品评价</h3>
            <div class="score_box clearfix">
                <div class="score">
                    <span>4.7</span><em>分</em>
                </div>
                <div class="score_speed">
                    <ul class="score_speed_text">
                        <li class="speed_01">非常不满意</li>
                        <li class="speed_02">不满意</li>
                        <li class="speed_03">一般</li>
                        <li class="speed_04">满意</li>
                        <li>非常满意</li>
                    </ul>
                    <div class="score_num">
                        4.7<i></i>
                    </div>
                    <p>共18939位慕课网友参与评分</p>
                </div>
            </div>
            <div class="review_tab">
                <ul class="review fl">
                    <li><a href="#" class="active">全部</a></li>
                    <li><a href="#">满意（3121）</a></li>
                    <li><a href="#">一般（321）</a></li>
                    <li><a href="#">不满意（1121）</a></li>
                </ul>
                <div class="review_sort fr">
                    <a href="#" class="review_time">时间排序</a><a href="#" class="review_reco">推荐排序</a>
                </div>
            </div>
            <div class="review_listBox">
                <div class="review_list clearfix">
                    <div class="review_userHead fl">
                        <div class="review_user">
                            <img src="${ctx}/images/userhead.jpg" alt="">
                            <p>61***42</p>
                            <p>金色会员</p>
                        </div>
                    </div>
                    <div class="review_cont">
                        <div class="review_t clearfix">
                            <div class="starsBox fl"><span class="stars"></span><span class="stars"></span><span
                                    class="stars"></span><span class="stars"></span><span class="stars"></span></div>
                            <span class="stars_text fl">5分 满意</span>
                        </div>
                        <p>慕课挺不错的信赖慕课挺不错的，信赖慕课</p>
                        <p><a href="#" class="ding">顶(0)</a><a href="#" class="cai">踩(0)</a></p>
                    </div>
                </div>
                <div class="review_list clearfix">
                    <div class="review_userHead fl">
                        <div class="review_user">
                            <img src="${ctx}/images/userhead.jpg" alt="">
                            <p>61***42</p>
                            <p>金色会员</p>
                        </div>
                    </div>
                    <div class="review_cont">
                        <div class="review_t clearfix">
                            <div class="starsBox fl"><span class="stars"></span><span class="stars"></span><span
                                    class="stars"></span><span class="stars"></span><span class="stars"></span></div>
                            <span class="stars_text fl">5分 满意</span>
                        </div>
                        <p>赖慕课挺不错的信赖慕课挺不错的，信赖慕课</p>
                        <p><a href="#" class="ding">顶(0)</a><a href="#" class="cai">踩(0)</a></p>
                    </div>
                </div>
                <div class="hr_25"></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<div class="headerBar">
<#include "topbar.ftl">
<#include "logobar.ftl">
<#if categories??>
    <div class="navBox">
        <div class="comWidth clearfix">
            <div class="shopClass fl">
                <h3>全部商品分类<i class="shopClass_icon"></i></h3>
                <div class="shopClass_show">
                    <#list categories as category>
                        <dl class="shopClass_item">
                            <dt><a href="${ctx}/category/${category.urlName}" class="b">${category.name}</a></dt>
                            <dd>
                                <#if category.children??>
                                    <#list category.children as child>
                                        <a href="${ctx}/category/${child.urlName}">${child.name}</a>
                                    </#list>
                                </#if>
                            </dd>
                        </dl>
                    </#list>
                </div>
            <#--<div class="shopClass_list hide">-->
            <#--<div class="shopClass_cont">-->
            <#--<dl class="shopList_item">-->
            <#--<dt>电脑装机</dt>-->
            <#--<dd>-->
            <#--<a href="#">文字啊</a><a href="#">文字字啊</a><a href="#">文字字字啊</a><a href="#">文字啊</a><a href="#">文字</a><a href="#">文字啊</a>-->
            <#--</dd>-->
            <#--</dl>-->
            <#--<dl class="shopList_item">-->
            <#--<dt>电脑装机</dt>-->
            <#--<dd>-->
            <#--<a href="#">文字啊</a><a href="#">文字字啊</a><a href="#">文字字字啊</a><a href="#">文字啊</a><a href="#">文字</a><a href="#">文字啊</a><a href="#">文字啊</a><a href="#">文字字啊</a><a href="#">文字字字啊</a><a href="#">文字啊</a><a href="#">文字</a><a href="#">文字啊</a><a href="#">文字啊</a>-->
            <#--</dd>-->
            <#--</dl>-->
            <#--<dl class="shopList_item">-->
            <#--<dt>电脑装机</dt>-->
            <#--<dd>-->
            <#--<a href="#">文字啊</a><a href="#">文字字啊</a><a href="#">文字字字啊</a><a href="#">文字啊</a><a href="#">文字</a><a href="#">文字啊</a><a href="#">文字啊</a><a href="#">文字字啊</a><a href="#">文字字字啊</a><a href="#">文字啊</a><a href="#">文字</a><a href="#">文字啊</a><a href="#">文字啊</a>-->
            <#--</dd>-->
            <#--</dl>-->
            <#--<dl class="shopList_item">-->
            <#--<dt>电脑装机</dt>-->
            <#--<dd>-->
            <#--<a href="#">文字啊</a><a href="#">文字字啊</a><a href="#">文字字字啊</a><a href="#">文字啊</a><a href="#">文字</a><a href="#">文字啊</a><a href="#">文字啊</a><a href="#">文字字啊</a><a href="#">文字字字啊</a><a href="#">文字啊</a><a href="#">文字</a><a href="#">文字啊</a><a href="#">文字啊</a>-->
            <#--</dd>-->
            <#--</dl>-->
            <#--<dl class="shopList_item">-->
            <#--<dt>电脑装机</dt>-->
            <#--<dd>-->
            <#--<a href="#">文字啊</a><a href="#">文字字啊</a><a href="#">文字字字啊</a><a href="#">文字啊</a><a href="#">文字</a><a href="#">文字啊</a><a href="#">文字啊</a><a href="#">文字字啊</a><a href="#">文字字字啊</a><a href="#">文字啊</a><a href="#">文字</a><a href="#">文字啊</a><a href="#">文字啊</a>-->
            <#--</dd>-->
            <#--</dl>-->
            <#--<div class="shopList_links">-->
            <#--<a href="#">文字测试内容等等<span></span></a><a href="#">文字容等等<span></span></a>-->
            <#--</div>-->
            <#--</div>-->
            <#--</div>-->
            </div>
            <ul class="nav fl">
                <li><a href="${ctx}" <#if controller?? && controller=="index">class="active"</#if>>团购</a></li>
                <li><a href="${ctx}" <#if controller?? && controller=="takeout">class="active"</#if>>二手特卖</a></li>
            </ul>
        </div>
    </div>
</#if>
</div>
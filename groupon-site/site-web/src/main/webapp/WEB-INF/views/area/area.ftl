<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>切换城市</title>
    <style type="text/css">
        <!--
        /*BODY {*/
        /*PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; PADDING-TOP: 0px*/
        /*}*/
        /*DIV {*/
        /*PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; PADDING-TOP: 0px*/
        /*}*/
        /*DL {*/
        /*PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; PADDING-TOP: 0px*/
        /*}*/
        /*DT {*/
        /*PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; PADDING-TOP: 0px*/
        /*}*/
        /*DD {*/
        /*PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; PADDING-TOP: 0px*/
        /*}*/
        /*UL {*/
        /*PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; PADDING-TOP: 0px*/
        /*}*/
        /*OL {*/
        /*PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; PADDING-TOP: 0px*/
        /*}*/
        /*LI {*/
        /*PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; PADDING-TOP: 0px*/
        /*}*/
        /*H1 {*/
        /*PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; PADDING-TOP: 0px*/
        /*}*/
        /*H2 {*/
        /*PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; PADDING-TOP: 0px*/
        /*}*/

        /*P {*/
        /*PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; PADDING-TOP: 0px*/
        /*}*/

        /*LI {*/
        /*LIST-STYLE-TYPE: none; LIST-STYLE-IMAGE: none*/
        /*}*/

        /*H2 {*/
        /*FONT-SIZE: 100%*/
        /*}*/

        #change-city {
            width: 1000px;
            margin: 50px auto;
        }

        #change-city .choosecities {
            BORDER-BOTTOM: #a3d7df 1px solid;
            BORDER-LEFT: #a3d7df 1px solid;
            PADDING-BOTTOM: 60px;
            BACKGROUND: #fff;
            BORDER-TOP: #a3d7df 1px solid;
            BORDER-RIGHT: #a3d7df 1px solid;
            box-shadow: 0 0 1px #d4edf4
        }

        #change-city .choosecities A:hover {
            TEXT-DECORATION: none
        }

        #change-city .citieslist H2 {
            POSITION: relative;
            PADDING-BOTTOM: 0px;
            LINE-HEIGHT: 30px;
            MARGIN: 20px 20px 12px;
            PADDING-LEFT: 14px;
            PADDING-RIGHT: 14px;
            ZOOM: 1;
            DISPLAY: inline-block;
            BACKGROUND: #00c7c7;
            HEIGHT: 32px;
            COLOR: #fff;
            PADDING-TOP: 0px;
            border-radius: 0
        }

        #change-city .citieslist H2 SPAN {
            BORDER-BOTTOM: #fff 6px solid;
            POSITION: absolute;
            BORDER-LEFT: #fff 6px solid;
            LINE-HEIGHT: 0;
            WIDTH: 0px;
            DISPLAY: block;
            FLOAT: none;
            HEIGHT: 0px;
            FONT-SIZE: 0px;
            BORDER-TOP: #00c7c7 6px solid;
            TOP: 32px;
            BORDER-RIGHT: #fff 6px solid;
            LEFT: 20px
        }

        #change-city .citieslist LI {
            BORDER-BOTTOM: #fff 1px solid;
            BORDER-TOP: #fff 1px solid
        }

        #change-city .citieslist P {
            BORDER-BOTTOM: #fff 1px solid;
            PADDING-BOTTOM: 6px;
            PADDING-LEFT: 30px;
            PADDING-RIGHT: 10px;
            BORDER-TOP: #fff 1px solid;
            PADDING-TOP: 6px
        }

        #change-city .citieslist SPAN.label {
            BORDER-BOTTOM: #ddd 1px solid;
            BORDER-LEFT: #ddd 1px solid;
            LINE-HEIGHT: 24px;
            WIDTH: 30px;
            ZOOM: 1;
            DISPLAY: inline-block;
            HEIGHT: 30px;
            COLOR: #666;
            VERTICAL-ALIGN: top;
            BORDER-TOP: #ddd 1px solid;
            MARGIN-RIGHT: 5px;
            BORDER-RIGHT: #ddd 1px solid;
            border-radius: 0
        }

        #change-city .citieslist SPAN.label STRONG {
            BORDER-BOTTOM: #f7f7f7 1px solid;
            TEXT-ALIGN: center;
            BORDER-LEFT: #f7f7f7 1px solid;
            WIDTH: 28px;
            DISPLAY: block;
            FONT-FAMILY: Tahoma;
            BACKGROUND: #f1f1f1;
            HEIGHT: 28px;
            FONT-SIZE: 16px;
            BORDER-TOP: #f7f7f7 1px solid;
            BORDER-RIGHT: #f7f7f7 1px solid;
            border-radius: 0
        }

        #change-city .citieslist SPAN {
            WIDTH: 880px;
            ZOOM: 1;
            DISPLAY: inline-block;
            VERTICAL-ALIGN: top
        }

        #change-city .citieslist SPAN I {
            FONT-STYLE: normal;
            MARGIN: 0px 3px;
            COLOR: #ccc
        }

        #change-city .citieslist .hover {
            BORDER-BOTTOM-COLOR: #e5e5e5;
            BORDER-TOP-COLOR: #e5e5e5;
            BORDER-RIGHT-COLOR: #e5e5e5;
            BORDER-LEFT-COLOR: #e5e5e5
        }

        #change-city .citieslist .hover P {
            BACKGROUND: #f7f7f7
        }

        #change-city .citieslist .hover SPAN.label {
            BORDER-BOTTOM-COLOR: #1fb7bb;
            BORDER-TOP-COLOR: #1fb7bb;
            BORDER-RIGHT-COLOR: #1fb7bb;
            BORDER-LEFT-COLOR: #1fb7bb
        }

        #change-city .citieslist .hover SPAN.label STRONG {
            BORDER-BOTTOM-COLOR: #00c7c7;
            BORDER-TOP-COLOR: #00c7c7;
            BACKGROUND: #00c7c7;
            COLOR: #fff;
            BORDER-RIGHT-COLOR: #00c7c7;
            BORDER-LEFT-COLOR: #00c7c7
        }

        #change-city .citieslist A {
            PADDING-BOTTOM: 1px;
            LINE-HEIGHT: 20px;
            MARGIN: 5px 10px;
            PADDING: 5px 10px;
            ZOOM: 1;
            DISPLAY: inline-block;
            HEIGHT: 20px;
            border-radius: 0
        }

        #change-city .citieslist A:hover {
            BACKGROUND: #00c7c7;
            COLOR: #fff
        }

        #change-city .citieslist .isonline {
            FONT-WEIGHT: bold
        }

        #change-city .citieslist .isoffline {
            FONT-WEIGHT: bold
        }

        /*#bdw {*/
        /*MIN-HEIGHT: 500px*/
        /*}*/
        /*.cf {*/
        /*ZOOM: 1*/
        /*}*/
        /*.cf:after {*/
        /*DISPLAY: block; HEIGHT: 0px; VISIBILITY: hidden; CLEAR: both; OVERFLOW: hidden; CONTENT: ''*/
        /*}*/
        /*H2 {*/
        /*PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; PADDING-TOP: 0px*/
        /*}*/

        /*BODY {*/
        /*FONT: 14px/1.5 Tahoma, Helvetica, arial, sans-serif; BACKGROUND: #d6e9ec; COLOR: #000*/
        /*}*/
        /*A {*/
        /*COLOR: #399; TEXT-DECORATION: none*/
        /*}*/
        /*A:hover {*/
        /*TEXT-DECORATION: underline*/
        /*}*/
        /*SPAN.money {*/
        /*FONT-FAMILY: arial*/
        /*}*/
        /*SPAN.required {*/
        /*COLOR: red*/
        /*}*/
        -->
    </style>
</head>
<body>
<div id="change-city">
    <div id="bdw" class="bdw">
        <div id="bd" class="cf">
            <div class="choosecities">
                <div class="citieslist">
                    <h2>按拼音首字母选择<span class="arrow">&raquo;</span></h2>
                    <ol class="hasallcity">
                    <#if areas?exists>
                        <#list areas?keys as key>
                            <li id="city-${key}">
                                <p class="cf"><span class="label"><strong>${key}</strong></span><span>
                                    <#list areas[key] as area>
                                        <a class="isonline" href="${ctx}?areaId=${area.id}">${area.name}</a>
                                    </#list>
                            </li>
                        </#list>
                    </#if>
                    </ol>
                </div>
            </div>
        </div>
        <!-- bd end -->
    </div>
    <!-- bdw end -->
</div>
<#--<script src="jquery.min.js" type="text/javascript"></script>-->
<script language="javascript" type="text/javascript">
    $(function () {
        $(".hasallcity li").hover(function () {
            $(this).addClass('hover');
        }, function () {
            $(this).removeClass('hover');
        });
    });
</script>
</body>
</html>

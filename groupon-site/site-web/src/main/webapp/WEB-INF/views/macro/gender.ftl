<#--生成性别单选按钮-->
<#macro generateGenderRadioButton sex>
<input id="male" name="sex" type="radio" <#if sex == 2>checked="checked"</#if> value="2"/>
<label for="male">男</label>&nbsp;&nbsp;
<input id="female" name="sex" type="radio" <#if sex == 1>checked="checked"</#if> value="1"/>
<label for="female">女</label>

    <#if sex == 0>
    <input id="unknown" name="sex" type="radio" <#if sex == 0>checked="checked"</#if> value="0"/>
    <label for="unknown">未知</label>
    </#if>
</#macro>
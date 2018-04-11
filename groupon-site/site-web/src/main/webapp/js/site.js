var Submit = {
    AjaxSubmit: function (url, params) {
        $.ajax({
            type: "post",
            url: url,
            data: params,
            dataType: "json",
            async: false,
            success: function (result, textStatus) {
                if (1 == result) {
                    alert("操作成功");
                } else {
                    alert("操作失败");
                }
                //return result;
            },
            error: function () {
                alert("操作失败");
            }
        });
    }
}
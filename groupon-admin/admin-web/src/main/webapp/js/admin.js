function processAjaxRouterCallResult(objectId, data, gridId) {
    var msg = '';
    if (data['statusCode'] == '0' || data['statusCode'] == 0) {
        msg = '操作成功';
        Messager.Prompt('提示', msg);
        $('#' + gridId).datagrid('reload');
        return;
    } else if (data['statusCode'] == '2' || data['statusCode'] == 2) {
        msg = '操作失败';
    } else if (data['statusCode'] == '10' || data['statusCode'] == 10) {
        msg = '由于系统原因未能执行该操作';
    } else if (data['statusCode'] == '20' || data['statusCode'] == 20) {
        msg = data['errorMessages'];
        if (null == msg || '' == msg) {
            msg = '操作中出现异常状况!';
        }
    } else {
        msg = '操作中出现异常状况.';
    }
    Messager.Prompt('提示', msg);
}

function ajaxRouterCall(ctx, routerName, method, methodName, objectId, confirmOperation, gridId) {
    //TODO: confirm 名称转换成中文，可以通过method对应名称
    //TODO: reload data/button block
    if (confirmOperation) {
        $.messager.confirm('确认', '是否确认要执行' + methodName + '操作?', function (result) {
            if (result) {
                $.ajax({
                    type: "post",
                    url: ctx + "/router/call/operation?" + 'routerName=' + routerName + '&method=' + method + '&entityId=' + objectId,
                    //data: postData,
                    beforeSend: function (XMLHttpRequest) {
                        //ShowLoading(); or disableLink
                    },
                    success: function (data, textStatus) {
                        processAjaxRouterCallResult(objectId, data, gridId);
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        //HideLoading(); or enableLink
                    },
                    error: function () {
                        //请求出错处理
                    }
                });
            }
        });
    }
}

/*查询panel查询*/
function searchData(gridId) {
    var id = '#' + gridId;
    var params = $(id).datagrid('options').queryParams;

    var fields = $('#searchForm').serializeArray();
    $.each(fields, function (i, field) {
        params[field.name] = field.value;
    });
    $(id).datagrid('reload');
}

/*ajax请求后处理*/
function processAjaxCallResult(result, gridId, dialogId) {
    var data = null;

    if (typeof result == "string") {
        data = eval('(' + result + ')');
    } else {
        data = result;
    }

    if (data['statusCode'] == '0' || data['statusCode'] == 0) {
        if (dialogId) {
            $('#' + dialogId).dialog('close');
        }
        if (gridId) {
            $('#' + gridId).datagrid('reload');
            $.messager.show({
                title: '提示',
                msg: '操作成功',
                style: {
                    right: '',
                    top: document.body.scrollTop + document.documentElement.scrollTop,
                    bottom: ''
                }
            });
        }
        return data['message'];
    } else if (data['statusCode'] == '1' || data['statusCode'] == 1) {
        msg = data['message'];
    } else if (data['statusCode'] == '2' || data['statusCode'] == 2) {
        msg = data['message'];
    } else {
        msg = '操作中出现异常状况';
    }

    $.messager.show({
        title: '失败',
        msg: msg,
        style: {
            right: '',
            top: document.body.scrollTop + document.documentElement.scrollTop,
            bottom: ''
        }
    });

    return;
}

/*提示*/
var Messager = {
    Prompt: function (title, msg) {
        $.messager.show({
            title: title,
            msg: msg,
            style: {
                right: '',
                top: document.body.scrollTop + document.documentElement.scrollTop,
                bottom: ''
            }
        });
    },

    Confirm: function (title, msg, confirmCallback, cancelCallback) {
        $.messager.confirm(title, msg, function (result) {
            if (result) {
                if (jQuery.isFunction(confirmCallback)) {
                    confirmCallback.call();
                }
            } else {
                if (jQuery.isFunction(cancelCallback)) {
                    cancelCallback.call();
                }
            }
        });
    }
};

var Dialog = {
    OpenNew: function (dialogId, dialogTitle, formId) {
        $('#' + dialogId).dialog('open').dialog('setTitle', dialogTitle);
        $('#' + formId).form('clear');
    },

    Close: function (dialogId) {
        $('#' + dialogId).dialog('close');
    },

    OpenEdit: function (gridId, dialogId, dialogTitle, formId) {
        var row = $('#' + gridId).datagrid('getSelected');
        if (!row) {
            Messager.Prompt('提示', '请选择要编辑的记录。');
            return;
        }
        var row = $('#' + gridId).datagrid('getSelected');
        if (row) {
            $('#' + dialogId).dialog('open').dialog('setTitle', dialogTitle);
            $('#' + formId).form('load', row);
        }
    }
};

/*Form提交*/
var Submit = {
    FormSubmit: function (formId, dialogId, gridId, url, callBackFunction, beforeSubmitFunction) {
        $('#' + formId).form('submit', {
            url: url,
            onSubmit: function () {
                if (beforeSubmitFunction) {
                    beforeSubmitFunction();
                }
                return $(this).form('validate');
            },
            success: function (result) {
                processAjaxCallResult(result, gridId, dialogId);
                if (callBackFunction) {
                    callBackFunction();
                }
            }
        });
    },

    FormDeleteSubmit: function (formId, gridId, url, callBackFunction) {
        var row = $('#' + gridId).datagrid('getSelected');
        if (!row) {
            Messager.Prompt('提示', '请选择要删除的记录。');
            return;
        }
        $('#' + formId).form('load', row);
        $.messager.confirm('确认', '确定要删除该记录?', function (data) {
            if (data) {
                $('#' + formId).form('submit', {
                    url: url,
                    onSubmit: function () {
                        return $(this).form('validate');
                    },
                    success: function (result) {
                        processAjaxCallResult(result, gridId, null);
                        if (callBackFunction) {
                            callBackFunction();
                        }
                    }
                });
            }
        });
    },

    AjaxSubmit: function (url, params, gridId, callBackFunction) {
        var message = null;
        $.ajax({
            type: "post",
            url: url,
            data: params,
            dataType: "json",
            async: false,
            success: function (result, textStatus) {
                message = processAjaxCallResult(result, gridId, null);
                if (callBackFunction) {
                    callBackFunction(message);
                }
            },
            error: function () {
                $.messager.show({
                    title: '失败',
                    msg: '系统错误',
                    style: {
                        right: '',
                        top: document.body.scrollTop + document.documentElement.scrollTop,
                        bottom: ''
                    }
                });
            }
        });
        return message;
    },

    AjaxDeleteSubmit: function (url, params, gridId, dialogId, callBackFunction) {
        $.messager.confirm('确认', '确定要删除该记录？', function (confirm) {
            if (confirm) {
                $.ajax({
                    type: "post",
                    url: url,
                    data: params,
                    dataType: "json",
                    async: false,
                    success: function (result, textStatus) {
                        if (callBackFunction) {
                            callBackFunction();
                        }
                        processAjaxCallResult(result, gridId, dialogId);
                    },
                    error: function () {
                        $.messager.show({
                            title: '失败',
                            msg: '系统错误',
                            style: {
                                right: '',
                                top: document.body.scrollTop + document.documentElement.scrollTop,
                                bottom: ''
                            }
                        });
                    }
                });
            }
        });
    }
};

var merchants = null;

/*格式化*/
var Formatter = {
    DateTimeFormatter: function (value, row) {
        if (undefined != value && '' != value) {
            var datetime = new Date(value);
            var year = datetime.getFullYear();
            var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
            var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
            var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
            var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
            var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
            return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
        }
    },

    DateFormatter: function (value, row) {
        if (value == undefined) {
            return "";
        }
        var datetime = new Date(value);
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
        return year + "-" + month + "-" + date;
    },

    PriceFormatter: function (value, row) {
        return '￥' + (value / 100).toFixed(2);
    },

    ProductTypeFormatter: function (value, row) {
        switch (value) {
            case 0:
                return "团购商品";
            case 1:
                return "其它商品";
            default:
                return "未知"
        }
    },

    ProductClassFormatter: function (value, row) {
        switch (value) {
            case 0:
                return "实体商品";
            case 1:
                return "虚拟商品";
            default:
                return "未知";
        }
    },

    ProductPublishStatusFormatter: function (value, row) {
        if (row.downShelfFlag == true && row.publishStatus == 3) {
            return "<span style='color:#DC143C'>(已过期)</span>";
        } else if (row.publishStatus == 3) {
            return "<span style='color:#00FF66'>" + getProductPublishStatusName(row.publishStatus) + "</span>";
        } else if (row.publishStatus == 5) {
            return "<span style='color:#808080'>" + getProductPublishStatusName(row.publishStatus) + "</span>";
        } else {
            return getProductPublishStatusName(row.publishStatus);
        }
    },

    ProductPublishTimeFormatter: function (value, row) {
        if (row.publishStatus != 3) {
            return "<span style='color:red'>未发布</span>";
        } else {
            return Formatter.DateTimeFormatter(value, row);
        }
    },

    ProductStartTimeFormatter: function (value, row) {
        if (row.publishStatus != 3) {
            return "<span style='color:#FF0000'>未开始销售</span>";
        } else {
            return Formatter.DateTimeFormatter(value, row);
        }
    },

    ProductEndTimeFormatter: function (value, row) {
        if (row.publishStatus != 3) {
            return "<span style='color:#FF0000'>未开始销售</span>";
        } else {
            return Formatter.DateTimeFormatter(value, row);
        }
    },

    ProductListOperateButtonsFormatter: function (value, row) {
        return "<a href='javascript:;' style='text-decoration:none;' onclick='copyProduct(" + row.id + ");'>复制商品</a>";
    },

    MerchantFormatter: function (value, row) {
        if (merchants == null) {
            $.ajax({
                type: "post",
                url: ctx + "/merchant/loadAll",
                async: false,
                success: function (data, textStatus) {
                    merchants = data;
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    Messager.Prompt("系统错误", "商家信息加载失败");
                }
            });
        }

        for (i = 0; i < merchants.length; i++) {
            if (merchants[i].id == value) {
                return merchants[i].name;
            }
        }
    },

    ProductCategoryOrderNumFormatter: function (value, row) {
        var rows = $("#dealCategoryGrid").datagrid("getRows");
        var rowIndex = $("#dealCategoryGrid").datagrid("getRowIndex", row);
        var result = "";

        // 最后一行，没有向下箭头
        if (rowIndex < rows.length - 1) {
            var downRow = null;
            for (i = 0; i < rows.length; i++) {
                if (i == rowIndex + 1) {
                    downRow = rows[i];
                }
            }
            result += "<a href='javascript:;' onclick='moveDownProductCategoryOrderNum(" + row.id + "," + downRow.id + ");'>"
            result += "<img src='" + ctx + "/images/move_down.gif'/>"
            result += "</a>";
        }

        // 第一行，没有向上箭头
        if (rowIndex > 0) {
            var upRow = null;
            for (i = 0; i < rows.length; i++) {
                if (i == rowIndex - 1) {
                    upRow = rows[i];
                }
            }
            result += "<a href='javascript:;' onclick='moveUpProductCategoryOrderNum(" + row.id + "," + upRow.id + ");'>"
            result += "<img src='" + ctx + "/images/move_up.gif'/>"
            result += "</a>";
        }

        return result;
    }
};

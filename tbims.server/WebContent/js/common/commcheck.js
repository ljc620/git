var varify;//用于查询验证,验证开始时间是否小于结束时间 
$.extend($.fn.validatebox.defaults.rules, {//验证开始时间小于结束时间
checkDt: {
validator: function(value, param){
startTime2 = $(param[0]).datetimebox('getValue');
var d1 = $.fn.datebox.defaults.parser(startTime2);
var d2 = $.fn.datebox.defaults.parser(value);
varify=(d2>=d1);
return varify;

},
message: '结束日期不能小于开始日期！'
},
ip : {// 验证IP地址(简单验证) 
    validator : function(value) { 
        return /\d+\.\d+\.\d+\.\d+/.test(value);
    }, 
    message : 'IP地址格式不正确' 
},
idcard : {
	validator : function(value) {
        return /(^\d{17}([0-9]|X)$)/i.test($.trim(value)); 
	},
	message : '身份证号格式不正确'
}, 
mobile : {// 验证手机号码 
    validator : function(value) { 
		return /^((1\d{10})|(0\d{2,3}-\d[0-9]{7,8}))$/.test(value);
    }, 
    message : '手机号或固话格式不正确' 
},
chineseCheck : {
	 validator : function(value) { 
			return /^\w+$/.test($.trim(value));
	    }, 
	    message : '请不要输入非法字符'
},
mail : {
	 validator : function(value) { 
			return /^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,9}|[0-9]{1,9})$/.test($.trim(value));
	    }, 
	    message : '邮箱格式不正确'
},
num : {
	 validator : function(value) { 
			return /^[1-9]\d*(\.\d+)?$/.test($.trim(value));
	    }, 
	    message : '必须是大于0的数'
}
});

/**
 * 身份证号检验
 * @param value
 * @returns
 */
function checkIdCard(value) {
    return /(^\d{17}([0-9]|X)$)/i.test($.trim(value)); 
}

$.extend($.fn.combobox.methods, {
    yearandmonth: function (jq) {
        return jq.each(function () {
            var obj = $(this).combobox();
            var date = new Date();
            var year = date.getFullYear();
            var table = $('<table>');
            var tr1 = $('<tr>');
            var tr1td1 = $('<td>', {
                text: '-',
                click: function () {
                    var y = $(this).next().html();
                    y = parseInt(y) - 1;
                    $(this).next().html(y);
                }
            });
            tr1td1.appendTo(tr1);
            var tr1td2 = $('<td>', {
                text: year,
                click: function () {
                    var yyyy = $(table).find("tr:first>td:eq(1)").html();
                    var cell = $(this).html();
                    var v = yyyy;
                    obj.combobox('setValue', v).combobox('hidePanel');

                }
            }).appendTo(tr1);

            var tr1td3 = $('<td>', {
                text: '+',
                click: function () {
                    var y = $(this).prev().html();
                    y = parseInt(y) + 1;
                    $(this).prev().html(y);
                }
            }).appendTo(tr1);
            tr1.appendTo(table);
            table.addClass('mytable cursor');
            table.find('td').hover(function () {
                $(this).addClass('tdbackground');
            }, function () {
                $(this).removeClass('tdbackground');
            });
            table.appendTo(obj.combobox("panel"));

        });
    }
});
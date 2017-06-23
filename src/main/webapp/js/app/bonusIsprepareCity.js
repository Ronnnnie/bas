
$(function(){

	initIsprepareCityGrid();
	initInfo();
	initAddForm("saveIsprepareperiod");
	
});


function initInfo(){
	$('.easyui-filebox').filebox({
	       buttonText:"选择文件",
	});
}

function initAddForm(validIndId) {
	$("#" + validIndId).combobox({
		data : [ {
			text : '是',
			value : '是'
		}, {
			text : '否',
			value : '否'
		}, ],
		panelHeight : 80,
		editable : false,
		required: true,
		valueField : 'value',
		textField : 'text',
		value : '是'
	});
	
	
	
	$("#saveCreatetime").datebox({  
        required: "true",  
        missingMessage: "必填项",  
        editable:false,
        formatter:function(date){
        	   var y = date.getFullYear();
        	   var m = date.getMonth()+1;
        	   var d = date.getDate();
        	   function formatNumber(value){
        		   return (value < 10 ? '0' : '') + value;
        	   }
        	   return y+'/'+formatNumber(m)+'/'+formatNumber(d);
        	   },
         parser:function(s){
        	   var t = Date.parse(s);
        	   if (!isNaN(t)){
        	    return new Date(t);
        	   } else {
        	    return new Date();
        	   }
         }
    });
	
	$("#searchIsprepareperiod").combobox({
		data : [ {text : '全部', value : ''},
	             {text : '是', value : '是'}, 
                 {text : '否', value : '否'},
				 ],
	width:"90px",
	panelHeight:80,
	editable:false,
	valueField:'value',    
	textField:'text' 
	});
	
	$("#searchCreatetime").datebox({  
        editable:false,
        formatter:function(date){
        	   var y = date.getFullYear();
        	   var m = date.getMonth()+1;
        	   var d = date.getDate();
        	   function formatNumber(value){
        		   return (value < 10 ? '0' : '') + value;
        	   }
        	   return y+'/'+formatNumber(m)+'/'+formatNumber(d);
        	   },
         parser:function(s){
        	   var t = Date.parse(s);
        	   if (!isNaN(t)){
        	    return new Date(t);
        	   } else {
        	    return new Date();
        	   }
         }
    });
}

Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function initIsprepareCityGrid(){
	var toolbar='';
	//不是超级管理员，没有操作按钮
//	if(isSuperAdmin){
		toolbar = [{ iconCls: 'icon-redo',
			text:'导出',
			handler: function(){
			        
    			var form=$("<form>");//定义一个form表单
    			form.attr("style","display:none");
    			form.attr("target","");
    			form.attr("method","post");
    			form.attr("action",contextPath + '/bonusIsprepareCity/downBonusIsprepareCity.do');
    			var input1=$("<input>");
    			input1.attr("type","hidden");
    			input1.attr("name","exportData");
    			input1.attr("value",(new Date()).getMilliseconds());
    			$("body").append(form);//将表单放置在web中
    			form.append(input1);

    			form.submit();//表单提交 
			}
		},{
			iconCls: 'icon-add',
			text:'新增',
			handler: function(){
				addIsprepareCity();
			}
		},{
			iconCls: 'icon-remove',
			text:'删除',
			handler: function(){
				deleteIsprepareCity();
			}
		}];
//	}
	
	$('#bonusIsprepareCityGrid').datagrid({
	    title:'城市筹备期列表',
	    toolbar:toolbar,
	    pagination: true,
	    singleSelect:false,
	    checkOnSelect:true,
	    pageSize:10,
	    fit:true,
	    rownumbers:true,
	    width:'auto',
	    url:contextPath + "/bonusIsprepareCity/queryBonusIsprepareCityListPage.do",
	    columns:[[
	        {field:'bonusmonth',title:'月份'},
	        {field:'city',title:'城市名'},
	        {field:'isprepareperiod',title:'是否筹备期'},
	        {field:'createby',title:'创建人'},
	        {field:'createtime',title:'创建时间'}
	    ]],
	    onLoadSuccess:function(data){
        }
	});
	
	//设置分页控件 
    var p = $('#bonusIsprepareCityGrid').datagrid('getPager'); 
    $(p).pagination({ 
        pageSize: 10,//每页显示的记录条数，默认为10 
        pageList: [5,10,15],//可以设置每页记录条数的列表 
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
    }); 
}





function closeWin(win){
	$("#"+win).window("close"); 
}
//查询员工薪资信息
function query(){
	var city = $("#searchCity").val();
	var bonusmonth = $("#searchBonusmonth").val();
	var isprepareperiod = $("#searchIsprepareperiod").combobox('getValue');
	var createby = $("#searchCreateby").val();
	var createtime = $("#searchCreatetime").datebox('getValue');
	$('#bonusIsprepareCityGrid').datagrid('load',{
		city:city,
		isprepareperiod:isprepareperiod,
		bonusmonth:bonusmonth,
		createby:createby,
		createtime:createtime
	});
}



function deleteIsprepareCity() {
	var rows = $("#bonusIsprepareCityGrid").datagrid("getSelections");
	var city = [];
	for (var int = 0; int < rows.length; int++) {
		city[int] = rows[int].city;
	}

	if (0 === city.length) {
		$.messager.alert("操作提示", "请选择需要操作的城市筹备期信息", "info");
		return;
	}
	$.messager.confirm('确认', '您确认删除所选城市筹备期信息吗？', function(r) {
		if (r) {

			$.ajax({
				url : contextPath + '/bonusIsprepareCity/deleteBonusIsprepareCity.do',
				data : {
					"rows" : city
				},
				dataType : "json",
				type : "POST",
				async : true,
				traditional : true,// 这个设置为true，data:{"steps":["qwe","asd","zxc"]}会转换成steps=qwe&steps=asd&...
				success : function(data) {
					if (data.success) {
						$.messager.alert("操作提示", data.msg, "info");
						// 重新加载表格数据
						initIsprepareCityGrid();
					} else {
						$.messager.alert("操作提示", data.msg, "error");
					}
					rows.length = 0;
				},
				error : function() {
					$.messager.alert("操作提示", data.msg, "error");
					rows.length = 0;
				}
			});
		}
	});
}


function addIsprepareCity() {
	$("#bonusIsprepareCityWin").show();
	$win = $("#bonusIsprepareCityWin").window({
		title : "新增员工",
		width : '400px',
		height : '300px',
		minimizable : false,
		collapsible : false,
		maximizable : false
	});
	$win.window("open");
	$("#bonusIsprepareCityWin").form('clear');
}

function clearSearch(){
	$("#searchBonusmonth").val("");
	$("#searchCity").val("");
	$("#searchIsprepareperiod").combobox('setValue','');
	$("#searchCreateby").val("");
	$("#searchCreatetime").datebox('setValue','');
}

function saveIsprepareCity() {
	var bonusmonth = $("#saveBonusmonth").val();
	var city = $("#saveCity").val();
	var isprepareperiod = $("#saveIsprepareperiod").combobox('getValue');
	var createby = $("#saveCreateby").val();
	var createtime = $("#saveCreatetime").datebox('getValue');
	
	if(bonusmonth==""||bonusmonth==null){
		$.messager.alert("操作提示", "请输入月份", "error");
		return;
	}
	if(city==""||city==null){
		$.messager.alert("操作提示", "请输入城市", "error");
		return;
	}
	if(isprepareperiod==""||isprepareperiod==null){
		$.messager.alert("操作提示", "请输入是否筹备期", "error");
		return;
	}
	if(createby==""||createby==null){
		$.messager.alert("操作提示", "请输入创建人", "error");
		return;
	}
	if(createtime==""||createtime==null){
		$.messager.alert("操作提示", "请输入创建时间", "error");
		return;
	}
	
	$.ajax({
		url : contextPath + '/bonusIsprepareCity/addBonusIsprepareCity.do',
		data : {
			"bonusmonth" : bonusmonth,
			"city" : city,
			"isprepareperiod" : isprepareperiod,
			"createby" : createby,
			"createtime" : createtime
		},
		dataType : "json",
		type : "POST",
		async : true,
		traditional : true,// 这个设置为true，data:{"steps":["qwe","asd","zxc"]}会转换成steps=qwe&steps=asd&...
		success : function(data) {
			if (data.success) {
				$.messager.alert("操作提示", data.msg, "info");
				// 重新加载表格数据
				closeWin('bonusIsprepareCityWin')
				initIsprepareCityGrid();
				
			} else {
				$.messager.alert("操作提示", data.msg, "error");
			}
		},
		error : function() {
			$.messager.alert("操作提示", data.msg, "error");
		}
	});
}


function uploadProExcel() {
	
	
	
	// 得到上传文件的全路径
	var fileName = $('#uploadExcel').filebox('getValue');
	// 获取题型
	// var id= $('#questionType').combobox('getValue');
	// var questionTypes=encodeURI(id);
	if (fileName != "") {
		// 进行基本校验
		if (fileName == "") {
			$.messager.alert('提示', '请选择上传文件！', 'info');
		} else {
			// 对文件格式进行校验
			var d1 = /\.[^\.]+$/.exec(fileName);
			if (d1 == ".xls" || d1 == ".xlsx") {
				$.messager.progress({ title: 'excel数据导入中', msg: '数据导入中...'});
				
				$('#uploadForm').form({ 
					url : contextPath + '/bonusIsprepareCity/upload.do',
					onSubmit: function(){ 
					// 做某些检查 
					// 返回 false 来阻止提交 
					}, 
					success:function(data){ 
						$.messager.progress('close');
						var obj = jQuery.parseJSON(data);
						
						if(obj.success){
							$.messager.alert("提示信息", obj.msg, 'info');
							initIsprepareCityGrid();
	    				}else{
	    					$.messager.alert("提示信息", obj.msg, 'error');
	    				}
					} 
					}); 
					$('#uploadForm').submit(); 
				
			} else {
				
				$.messager.alert('提示', '请选择xls格式文件！', 'info');
				$('#uploadExcel').filebox('setValue', '');
			}
		}
	} else {
		$.messager.alert('提示', '请选择需要上传的文件！', 'info');
	}

}


$(function(){

//	initSystem();
//	initValidInd();
	initSalaryCountGrid();
	initInfo();
});

function initInfo(){
	$('.easyui-filebox').filebox({    
	    buttonText:"选择文件",
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

function initSalaryCountGrid(){
	var toolbar='';
	//不是超级管理员，没有操作按钮
//	if(isSuperAdmin){
		toolbar = [{
			text: '计算月份(YYYYMM)<input id="countDate" type="text" name="countDate" " maxlength="6">'
		},{
			iconCls: 'icon-add',
			text:'全员计算',
			handler: function(){
				
				var countDate = $("#countDate").val();
				var r = /^\d{6,}$/;
				
				if (countDate && typeof(countDate)!="undefined" && countDate!=0){
				    if(!r.test(countDate)){
				    	$.messager.alert("操作提示", "计算时间输入有误，请输入数字且长度必须为6位","error");
				    	return;
				    }
				    var month = countDate.substring(4,6);
				    if(month>12){
				    	$.messager.alert("操作提示", "计算时间输入有误，月份不可大于12","error");
				    	return;
				    }
				    
				    var nowTime = new Date().Format("yyyyMM");
				    if(countDate>nowTime){
				    	$.messager.alert("操作提示", "计算时间输入有误，不可超过当前月份","error");
				    	return;
				    }
				    if(nowTime-countDate>1){
				    	$.messager.alert("操作提示", "计算时间输入有误，只能输入当月或者上一个月","error");
				    	return;
				    }
				    
				    
				}else{
					$.messager.alert("操作提示", "请输入计算时间！","error");
			    	return;
				}
				
				$.messager.confirm('确认','您确认进行全员计算吗？',function(r){    
				    if (r){    
				    	$.messager.progress({ title: '薪资计算', msg: '薪资计算中,请稍等......'});
			    		$.ajax({
			    			url: contextPath + '/salary/count/countAllPersonSalary.do',
			    			type: "POST", 
			    			data:{
			    				userCode:'',
			    				countDate:countDate
			    				},
			    	        success: function (data) { 
			    	        	$.messager.progress('close');
			    	        	data = eval("("+data+")");
			    	        	if(data.success){
			    					 $("#downData").show();
			    						$win = $("#downData").window({
			    							title:"下载计算成功后的数据",
			    							width:'400px',
			    							height:'130px',
			    							minimizable:false,
			    							collapsible:false,
			    							maximizable:false
			    						});
			    						$win.window("open");
			    						document.getElementById("downCountData").href = contextPath
			    						+ "/salary/count/downSalaryDataByF.do";
			    				}else{
			    					$.messager.alert("操作提示", data.message,"error");
			    				}
			    	        },
			    			error: function (e) { 
			    				$.messager.progress('close');
			    				 $.messager.alert("操作提示", "薪资计算失败，请联系管理员","error");
			    	        }
			    		});
				    }    
				}); 
			}
		},{
			iconCls: 'icon-add',
			text:'个人计算',
			handler: function(){
				
				var countDate = $("#countDate").val();
				var r = /^\d{6,}$/;
				
				if (countDate && typeof(countDate)!="undefined" && countDate!=0){
				    if(!r.test(countDate)){
				    	$.messager.alert("操作提示", "计算时间输入有误，请输入数字且长度必须为6位","error");
				    	return;
				    }
				    var month = countDate.substring(4,6);
				    if(month>12){
				    	$.messager.alert("操作提示", "计算时间输入有误，月份不可大于12","error");
				    	return;
				    }
				    
				    var nowTime = new Date().Format("yyyyMM");
				    if(countDate>nowTime){
				    	$.messager.alert("操作提示", "计算时间输入有误，不可超过当前月份","error");
				    	return;
				    }
				    if(nowTime-countDate>1){
				    	$.messager.alert("操作提示", "计算时间输入有误，只能输入当月或者上一个月","error");
				    	return;
				    }
				    
				    
				}else{
					$.messager.alert("操作提示", "请输入计算时间！","error");
			    	return;
				}
				
				$("#personCountWin").show();
				$win = $("#personCountWin").window({
					title:"根据员工号计算",
					width:'400px',
					height:'130px',
					minimizable:false,
					collapsible:false,
					maximizable:false
				});
				$win.window("open");
				$("#countFrm").form('clear');
				initAddForm("sysCodeCombobox","validInd");
			}
		},{
			iconCls: 'icon-add',
			text:'结转锁定',
			handler: function(){
				$.messager.confirm("确认","确认结转锁定吗?", function (r) {  
			        if (r) { 
			        		$.messager.progress({ title: '结转锁定', msg: '保存数据...'});
		        			$.ajax({
		    	    			url:contextPath + '/salary/count/lockSalaryData.do',
		    	    			dataType:"json",
		    	    			type: "POST", 
		    	    			async : true,
		    	    	        success: function (data) { 
		    	    	        	if(data.success){
		    	    	        		$.messager.progress('close');
		    	    					$.messager.alert("操作提示", data.msg,"info");
		    	    					//重新加载表格数据
		    	    					initSalaryCountGrid();
		    	    				}else{
		    	    					$.messager.alert("操作提示", data.msg,"error");
		    	    				}
		    	    	        },
		    	    			error: function () { 
		    	    				 $.messager.alert("操作提示", data.msg,"error");
		    	    	        }
		    	    		});
			        }else{	        	
			        	return false;  
			        }  
			    });  
			}
		},{ iconCls: 'icon-redo',
			text:'导出',
			handler: function(){
    			var form=$("<form>");//定义一个form表单
    			form.attr("style","display:none");
    			form.attr("target","");
    			form.attr("method","get");
    			form.attr("action",contextPath + '/salary/count/downSalaryData.do');
    			var input1=$("<input>");
    			input1.attr("type","hidden");
    			input1.attr("name","exportData");
    			input1.attr("value",(new Date()).getMilliseconds());
    			$("body").append(form);//将表单放置在web中
    			form.append(input1);

    			form.submit();//表单提交 
			}
		},{ iconCls: 'icon-redo',
			text:'导出(带公式)',
			handler: function(){
				var countDate = $("#countDate").val();
    			var form=$("<form>");//定义一个form表单
    			form.attr("style","display:none");
    			form.attr("target","");
    			form.attr("method","post");
    			form.attr("action",contextPath + '/salary/count/downSalaryDataByF.do');
    			var input1=$("<input>");
    			input1.attr("type","hidden");
    			input1.attr("name","exportData");
    			input1.attr("value",countDate);
    			$("body").append(form);//将表单放置在web中
    			form.append(input1);

    			form.submit();//表单提交 
			}
		}];
//	}
	
	$('#salaryCountGrid').datagrid({
	    title:'薪资计算列表',
	    toolbar:toolbar,
	    pagination: true,
	    singleSelect:true,
	    checkOnSelect:true,
	    pageSize:10,
	    fit:true,
	    rownumbers:true,
	    width:'auto',
	    idField:'userCode',
	    url:contextPath + "/salary/count/querySalaryDataPage.do",
	    columns:[[
	        {field:'userCode',title:'员工工号'},
	        {field:'userName',title:'姓名'},
	        {field:'taxtype',title:'计税类别'},
	        {field:'sattend',title:'应出勤天数'},
	        {field:'fattend',title:'实际出勤天数'},
	        {field:'bsalary',title:'基本工资'},
	        {field:'tsubsi',title:'综合补贴'},
	        {field:'mprizeb',title:'月度奖金基数'},
	        {field:'qprizeb',title:'季度奖金基数'},
	        {field:'yprizeb',title:'年度奖金基数'},
//	        {field:'qprize',title:'季度奖金'},
//	        {field:'msalaryt',title:'月工资总额'},
//	        {field:'yprizem',title:'年奖月提'},
//	        {field:'callsubs',title:'话费补贴标准'},
//	        {field:'tsubs',title:'交通补贴标准'},
//	        {field:'esubs',title:'派驻津贴标准'},
//	        {field:'htsubs',title:'高温补贴标准'},
//	        {field:'ltsubs',title:'低温补贴标准'},
//	        {field:'owoverh',title:'平时加班时数'},
//	        {field:'wwoverh',title:'周末加班时数'},
//	        {field:'hwoverh',title:'节假日加班时数'},
//	        {field:'sickleave',title:'病假（天）'},
//	        {field:'noleave',title:'事假（天）'},
//	        {field:'mateleave',title:'产假（天）'},
//	        {field:'attendt',title:'考勤类别'},
//	        {field:'bsalaryr',title:'实发基本工资'},
//	        {field:'tsubsir',title:'实发综合补贴'},
//	        {field:'callsubr',title:'实发话费补贴'},
//	        {field:'tsubsr',title:'实发交通补贴'},
//	        {field:'esubsr',title:'实发派驻津贴'},
//	        {field:'htsubsr',title:'实发高温补贴'},
//	        {field:'ltsubsr',title:'实发低温补贴'},
//	        {field:'percoef',title:'绩效系数'},
//	        {field:'perprize',title:'绩效奖金'},
//	        {field:'saleprize',title:'销售奖金'},
//	        {field:'oprize',title:'其他奖金'},
//	        {field:'nightsub',title:'夜班补贴'},
//	        {field:'osubsi',title:'其他补贴'},
//	        {field:'ssalary',title:'补发工资'},
//	        {field:'holsubsi',title:'节日津贴'},
//	        {field:'exeitem',title:'免税项目'},
//	        {field:'qprizer',title:'实发季度奖金'},
//	        {field:'overtime',title:'加班费'},
//	        {field:'sickleavec',title:'病假扣款'},
//	        {field:'noleavec',title:'事假扣款'},
//	        {field:'mateleavec',title:'产假扣款'},
//	        {field:'shsalary',title:'应发金额'},
//	        {field:'sosecpt',title:'社保个人合计'},
//	        {field:'sosecct',title:'社保单位合计'},
//	        {field:'pasturec',title:'养老单位'},
//	        {field:'pasturep',title:'养老个人'},
//	        {field:'healthc',title:'医疗单位'},
//	        {field:'healthp',title:'医疗个人'},
//	        {field:'outworkc',title:'失业单位'},
//	        {field:'outworkp',title:'失业个人'},
//	        {field:'industc',title:'工伤单位'},
//	        {field:'reprodc',title:'生育单位'},
//	        {field:'fundc',title:'住房公积金单位'},
//	        {field:'fundp',title:'住房公积金个人'},
//	        {field:'tamount',title:'应税金额'},
//	        {field:'payrollt',title:'工资税'},
//	        {field:'payrolltr',title:'工资税率'},
//	        {field:'tdeduct',title:'税后扣款'},
//	        {field:'holsubsid',title:'已发放节日津贴'},
//	        {field:'atadjust',title:'税后调整'},
//	        {field:'bpayr',title:'银行实发'},
//	        {field:'cardid',title:'身份证号码'},
//	        {field:'paidstatus',title:'计薪状态'},
//	        {field:'bandno',title:'银行账号'},
//	        {field:'accopenbn',title:'银行账号开户行名称'},
//	        {field:'accopenba',title:'银行账号开户地'},
//	        {field:'fillbec',title:'报税公司'},
//	        {field:'fillbecity',title:'报税城市'},
//	        {field:'countDate',title:'核算日期'},
	        {field:'opr',title:'操作',width:'220',
                formatter:function(value, row, index){
                	//不是超级管理员，没有操作按钮
//                	if(!isSuperAdmin){
//                		return;
//                	}
                	var view = '<a  class="viewcls"  href="javascript:;" onclick="openPersonSalary('+index+')" ></a>';

                	return view;
//                	}
                }
            }
	    ]],
	    onLoadSuccess:function(data){
	    	$('.viewcls').linkbutton({text:'详情',plain:true,iconCls:'icon-edit'});
        }
	});
	
	//设置分页控件 
    var p = $('#salaryCountGrid').datagrid('getPager'); 
    $(p).pagination({ 
        pageSize: 10,//每页显示的记录条数，默认为10 
        pageList: [5,10,15],//可以设置每页记录条数的列表 
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
    }); 
}

function initAddForm(sysCodeId,validIndId){
	/*$("#"+sysCodeId).combobox({    
	    url: contextPath + "/sysServer/initSystem.do",
		panelHeight:200,
		editable:false,
	    valueField:'sysCode',    
	    textField:'sysCname'
	});*/
	 $("#"+validIndId).combobox({
			data : [ 
		               {text : '有效', value : '1'}, 
                       {text : '失效', value : '0'},
					 ],
			panelHeight:80,
			editable:false,
			valueField:'value',    
			textField:'text' ,
			value:'1'
		});
}

function countPersonSalary(){
	$('#personalCountDate').val("");
	$('#personalCountDate').val($('#countDate').val())
	$('#countFrm').form('submit', {
		url: contextPath + '/salary/count/countPersonSalary.do',
		onSubmit:function(param){
			if($(this).form('validate')){
				$.messager.progress({ title: '薪资计算', msg: '薪资计算中,请稍等......'});
			}
			return $(this).form('validate');
		},
		success: function(data){
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				
				$('#salaryCountGrid').datagrid('reload'); 
				 $.messager.alert("操作提示", data.message,"info");
//				 重新加载表格数据
//				关闭窗口
				closeWin('personCountWin');
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}
		},
		onLoadError:function(data){
			$.messager.alert("操作提示", data.message,"error");
		}
	});
}

function closeWin(win){
	$("#"+win).window("close"); 
}
//查询员工薪资信息
function query(){
	var userCode = $("#searchUserCode").val();
	$('#salaryCountGrid').datagrid('load',{
		userCode:userCode
	});
}

function openPersonSalary(index){
	$("#salaryCountGrid").datagrid("selectRow",index);
	var row = $("#salaryCountGrid").datagrid("getSelected");
	$("#viewPersonSalaryWin").show();
	$win = $("#viewPersonSalaryWin").window({
		title:"查看薪资计算详情",
		width:'400px',
		height:'500px',
		minimizable:false,
		collapsible:false,
		maximizable:false
	});
	$win.window("open");
	$("#viewPersonSalaryFrm").form("load",row);
}
function uploadCountDataExcel() {
	
	// 得到上传文件的全路径
	var fileName = $('#uploadExcel').filebox('getValue');
	// 获取题型
	var countDate = $("#countDate").val();
	
	if (fileName != "") {
		// 进行基本校验
		if (fileName == "") {
			$.messager.alert('提示', '请选择上传文件！', 'info');
		} else {
			// 对文件格式进行校验
			var d1 = /\.[^\.]+$/.exec(fileName);
			if (d1 == ".xls" || d1 == ".xlsx") {
				$.messager.progress({ title: 'excel数据导入中', msg: '数据导入中...'});
				$('#uploadExcelFrm').form("submit", { 
					url : contextPath + '/salary/count/uploadCountDataExcel.do?countDate='+countDate,
					onSubmit: function(){ 
					}, 
					success:function(data){ 
						$.messager.progress('close');
						var obj = jQuery.parseJSON(data);
						if(obj.success){
							$.messager.alert("提示信息", obj.msg);
							closeWin('downData');
							$('#salaryCountGrid').datagrid('reload'); 
	    				}else{
	    					$.messager.alert("提示信息", obj.msg);
	    				}
					} 
					}); 
				//-------------------------------------
				
			} else {
				
				$.messager.alert('提示', '请选择xls格式文件！', 'info');
				$('#uploadExcel').filebox('setValue', '');
			}
		}
	} else {
		$.messager.alert('提示', '请选择需要上传的文件！', 'info');
	}
}






$(function() {
	initDownloadGrid();
});

function initDownloadGrid(){
	$('#downloadList').datagrid({
		title : '报表下载列表',
		pagination : true,
		singleSelect : true,
		checkOnSelect : true,
		fit : true,
		pageSize : 10,
		rownumbers : true,
		width : 'auto',
	    idField:'reportName',
	    url:contextPath + "/reportDownload/queryDownloadPage.do",
	    columns:[[
	        {field:'reportName',title:'报表名称'},
	        {field:'requestUser',title:'请求用户'},
	        {field:'requestTime',title:'请求时间'},
	        {field:'reportStatus',title:'导出状态'},
	        {field:'finishTime',title:'完成时间'},
	        {field:'entryPassword',title:'解压密码'},
	        {field:'recordVolume',title:'数据量'},
	        {field:'downloadTimes',title:'下载次数'},
	        {field:'lastDownloadUser',title:'最后下载用户'},
	        {field:'lastDownloadTime',title:'最后下载时间'},
	        {field:'searchCriteria',title:'查询条件',width:'15%'},
	        {field:'opr',title:'操作',width:'20%',
                formatter:function(value, row, index){
                	var loadcls = '<a  class="loadcls"  href="javascript:;" onclick="downloadReport('+index+')" ></a>';
                	if(row.reportStatus == '导出完成'){
                		return loadcls;
                	}else{
                		return null;
                	}
                }
            }
	    ]],
	    onLoadSuccess:function(data){
            $('.loadcls').linkbutton({text:'下载',plain:true,iconCls:'icon-edit'});
        },
		onClickCell: function(index,field,value){
	 		if(field != "searchCriteria"){return;}
	 		$.messager.alert("报表查询条件",value);
		}
	});
	// 设置分页控件
	var p = $('#downloadList').datagrid('getPager');
	$(p).pagination({
		pageSize : 10,// 每页显示的记录条数，默认为10
		pageList : [ 5, 10, 15 ],// 可以设置每页记录条数的列表
		beforePageText : '第',// 页数文本框前显示的汉字
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

function downloadSearch(){
	//查询前先清空所有选中行
	$("#downloadList").datagrid("clearSelections");
	var reportName = $("#reportName").val();
	$('#downloadList').datagrid('load',{
		reportName:reportName
	});
}
//重置
function salarySearchReset(name){
    $("#"+name).find(":input").val("");
}
function downloadReport(index){
	$("#downloadList").datagrid("selectRow",index);
	var row = $("#downloadList").datagrid("getSelected");
	var url= contextPath + "/reportDownload/get.do?id=" + row.id;
	url = encodeURI(url);
	//追加蒙板效果
	$.messager.progress({
		text : 'Excel下载中，请稍后....',
	    interval : 100
	});
	var a= window.location.href=url;
	setTimeout(function(){ $.messager.progress('close'); },2000);
	initDownloadGrid();
}


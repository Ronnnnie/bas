function query() {
		var checkType = $("#checkType").combobox('getValue');
		if(checkType==""){
			$.messager.alert('提示', '请选择需校验的表单类型!', 'warning');
		}else{
			$("#dataCheckGrid").datagrid('options').url = contextPath + "/dataCheckServer/queryDataCheckDetail.do";
			$("#dataCheckGrid").datagrid('load',{
				checkType : checkType
			});
			var p = $('#dataCheckGrid').datagrid('getPager'); 
			    $(p).pagination({ 
			        pageSize: 10,//每页显示的记录条数，默认为10 
			        pageList: [5,10,15],//可以设置每页记录条数的列表 
			        beforePageText: '第',//页数文本框前显示的汉字 
			        afterPageText: '页    共 {pages} 页', 
			        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
			});
			dataGridFieldChange(checkType);
			$.ajax({
				url : contextPath + "/dataCheckServer/queryDataCheckDetail.do",
				type : "POST",
				data : {
					rows: 10,
					page:1,
					checkType : checkType
				},
				success : function(data) {
					var jsondata = eval("(" + data + ")");
					//是否有数据
					if (jsondata.rows == "") {
						$.messager.alert('提示', '暂无匹配数据!请重新搜索..', 'warning');
						//清空表单数据
						$('#dataCheckGrid').datagrid('loadData', {
							total : 0,
							rows : []
						});
					}else{
					}
				},
				error : function() {
					$.messager.alert("操作提示", data.message, "error");
				}
			});
		}
	}
	
	function isNullOrEmpty(str){
		if(typeof(str) == 'undefined'){
			return '';
		}else{
			return str;
		}
	}
	
	function dataGridFieldChange(checkType){
		debugger;
		if (checkType=="01") {
			$('#dataCheckGrid').datagrid({
				columns:[[
					{field:'classfy',title:'十四级分类',width:80},
					{field:'fkbj',title:'放款本金',width:80,formatter: function(value,row,index){
						return thousandthFormat(value == null ? 0 : value, 2);
					}},
					{field:'shbj',title:'实还本金',width:80,formatter: function(value,row,index){
						return thousandthFormat(value == null ? 0 : value, 2);
					}},
					{field:'yqbj',title:'逾期本金',width:80,formatter: function(value,row,index){
						return thousandthFormat(value == null ? 0 : value, 2);
					}},
					{field:'wdqbj',title:'未到期本金',width:80,formatter: function(value,row,index){
						return thousandthFormat(value == null ? 0 : value, 2);
					}},
					{field:'cy',title:'差异',width:100,formatter: function(value,row,index){
						return thousandthFormat(value == null ? 0 : value, 2);
					}}
				]]
			});
		}else if(checkType=="02"){
			$('#dataCheckGrid').datagrid({
				columns:[[
							{field:'belong',title:'所属方',width:80,formatter: function(value,row,index){
								return companyNameFormatter(value,null);
							}},
							{field:'guaranteeparty',title:'保证方',width:80,formatter: function(value,row,index){
								return companyNameFormatter(value,null);
							}},
							{field:'dqbj',title:'到期本金',width:80,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'shbj',title:'实还本金',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'yqbj',title:'逾期本金',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'dqlx',title:'到期利息',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'shlx',title:'实还利息',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'yqlx',title:'逾期利息',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'bjcy',title:'本金差异',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'lxcy',title:'利息差异',width:80,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}}
				]]
			});
		}else if(checkType=="03"){
			$('#dataCheckGrid').datagrid({
				columns:[[
							{field:'belong',title:'所属方',width:80,formatter: function(value,row,index){
								return companyNameFormatter(value,null);
							}},
							{field:'guaranteeparty',title:'保证方',width:80,formatter: function(value,row,index){
								return companyNameFormatter(value,null);
							}},
							{field:'dqbj',title:'到期本金',width:80,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'shbj',title:'实还本金',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'yqbj',title:'逾期本金',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'dqlx',title:'到期利息',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'shlx',title:'实还利息',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'yqlx',title:'逾期利息',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'bjcy',title:'本金差异',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'lxcy',title:'利息差异',width:80,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}}
				]]
			});
		}else if(checkType=="04"){
			$('#dataCheckGrid').datagrid({
				columns:[[
							{field:'sfdq',title:'是否当期',width:80},
							{field:'guaranteeparty',title:'保证方',width:80,formatter: function(value,row,index){
								return companyNameFormatter(value,null);
							}},
							{field:'dqbj',title:'到期本金',width:80,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'wdqbj',title:'未到期本金',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'dcbj',title:'代偿本金',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'dqlx',title:'到期利息',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'wdqlx',title:'未到期利息',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'dclx',title:'代偿利息',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'bjcy',title:'本金差异',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'lxcy',title:'利息差异',width:80,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}}
				]]
			});
		}else if(checkType=="05"){
			$('#dataCheckGrid').datagrid({
				columns:[[
							{field:'sfdq',title:'是否当期',width:80},
							{field:'guaranteeparty',title:'保证方',width:80,formatter: function(value,row,index){
								return companyNameFormatter(value,null);
							}},
							{field:'dqbj',title:'到期本金',width:80,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'wdqbj',title:'未到期本金',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'ransomsum',title:'赎回本金',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'bjcy',title:'本金差异',width:80,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}}
				]]
			});
		}else if(checkType=="09"){
			$('#dataCheckGrid').datagrid({
				columns:[[
							{field:'sfdq',title:'是否当期',width:80},
							{field:'srf',title:'受让方',width:80,formatter: function(value,row,index){
								return companyNameFormatter(value,1);
							}},
							{field:'dqbj',title:'到期本金',width:80,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'wdqbj',title:'未到期本金',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'zrbj',title:'转让本金',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'bjcy',title:'本金差异',width:80,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
				]]
			});
		}else if(checkType=="12"){
			$('#dataCheckGrid').datagrid({
				columns:[[
							{field:'shbd',title:'所属表单',width:80},
							{field:'belong',title:'所属方',width:80,formatter: function(value,row,index){
								return companyNameFormatter(value,null);
							}},
							{field:'guaranteeparty',title:'保证方',width:100,formatter: function(value,row,index){
								return companyNameFormatter(value,null);
							}},
							{field:'syljbj',title:'上月累计本金',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'dybj',title:'当月本金',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'dyljbj',title:'当月累计本金',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'syljlx',title:'上月累计利息',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'dylx',title:'当月利息',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'dyljlx',title:'当月累计利息',width:100,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'bjcy',title:'本金差异',width:80,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}},
							{field:'lxcy',title:'利息差异',width:80,formatter: function(value,row,index){
								return thousandthFormat(value == null ? 0 : value, 2);
							}}
				]]
			});
		}
	}
	
	function ExportExcel(path){
		var checkType = $("#checkType").combobox('getValue');
		if(checkType==null||checkType==''){
			$.messager.alert('提示', '请选择表单类型!', 'warning');
		}else{
			window.location.href=path+'/dataCheckServer/excelExport.do?checkType='+checkType;
		}
	}
	
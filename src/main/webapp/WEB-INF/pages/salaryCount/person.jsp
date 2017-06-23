<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<title>薪资计算个人</title>

<style type="text/css">
	.formdiv {padding: 5px;}
	.required{color: red;}
</style>
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/salaryCount.js"></script>
</head>
<body>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north'" title=" " style="width:100%;height: 15%;"> 
		<form>
			<div style="width:90%;margin: 5px;">
				<label>员工工号：</label>
				<input type="text" name="userCode" id="searchUserCode" class="easyui-validatebox" data-options="validType:'unnormal'"  maxlength="20"/>
				<a id="button-search" href ="javascript:;" onclick="query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
			</div>
		</form>
	</div>
	 <div data-options="region:'center'">
   	   <table id="salaryCountGrid" style="height: 100%"></table>
    </div>
</div>	
	
    <!-- 个人查询 -->
	<div id="personCountWin" style="width: 350px;text-align:center;display:none;" >
		<form id="countFrm" method="post">
			<table class="formTable">
				<input type="hidden" name="countDate" id="personalCountDate"/>
				<tr>
					<th><samp>*</samp>员工工号:</th>
					<td>
					 <input  type="text"  class="easyui-validatebox"
		        		id="userCode" name="userCode" data-options=" required:true" />   
					</td>
				</tr>
				
			</table>
		    <div style="padding:5px;text-align:center;">  
	            <a href="javascript:;" onClick = "countPersonSalary()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">计算</a>
	            <a href="javascript:;" onClick = "closeWin('personCountWin')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
	     	</div>  
		</form>
	</div>
	
	<!-- 个人查询 -->
	<div id="downData" style="width: 350px;text-align:center;display:none;" ><!--  -->
		<h4>计算成功，请下载计算后的薪资数据:
			<a id="downCountData" href="#">下载</a></h4>
			<h4>核查后请导入数据:
			
			<form id="uploadExcelFrm" method="post"
						enctype="multipart/form-data">
								<input id="uploadExcel" name="file" class="easyui-filebox"
						style="width: 200px" data-options="prompt:'请选择文件...'" /> <a
						href="#" class="easyui-linkbutton"
						style="width: 44px; height: 22px" onclick="uploadCountDataExcel()">导入</a>
					</form>
	
				</h4>
	</div>
	
	
	<!-- 查看详情 -->
	<div id="viewPersonSalaryWin" style="width: 350px;text-align:center;display: none;" >
		<form id="viewPersonSalaryFrm" method="post">
			<table class="formTable">
				<tr> <th>员工工号:</th> <td> <input name="userCode" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr> 
				<tr> <th>员工姓名:</th> <td> <input name="userName" class="easyui-validatebox" type="text" readonly="readonly"  /> </td> </tr>
				<tr> <th>计税类别:</th> <td> <input name="taxtype" class="easyui-validatebox" type="text"  readonly="readonly"/> </td> </tr> 
				<tr> <th>应出勤天数:</th> <td> <input name="sattend" class="easyui-validatebox" type="text"   readonly="readonly"/> </td> </tr>
				<tr> <th>实际出勤天数:</th> <td> <input name="fattend" class="easyui-validatebox" type="text"   readonly="readonly"/> </td> </tr> 
				<tr> <th>基本工资:</th> <td> <input name="bsalary" class="easyui-validatebox" type="text"  readonly="readonly"/> </td> </tr>
				<tr> <th>综合补贴:</th> <td> <input name="tsubsi" class="easyui-validatebox" type="text"   readonly="readonly"/> </td> </tr> 
				<tr> <th>月度奖金基数:</th> <td> <input name="mprizeb" class="easyui-validatebox" type="text"   readonly="readonly"/> </td> </tr>
				<tr> <th>季度奖金基数:</th> <td> <input name="qprizeb" class="easyui-validatebox" type="text"  readonly="readonly"/> </td> </tr> 
				<tr> <th>年度奖金基数:</th> <td> <input name="yprizeb" class="easyui-validatebox" type="text"  readonly="readonly"/> </td> </tr>
				<tr> <th>季度奖金:</th> <td> <input name="qprize" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr>
				<tr> <th>月工资总额:</th> <td> <input name="msalaryt" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr> 
				<tr> <th>年奖月提:</th> <td> <input name="yprizem" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr>
				<tr> <th>话费补贴标准:</th> <td> <input name="callsubs" class="easyui-validatebox" type="text"   readonly="readonly"/> </td> </tr>
				<tr> <th>交通补贴标准:</th> <td> <input name="tsubs" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr> 
				<tr> <th>派驻津贴标准:</th> <td> <input name="esubs" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr>
				<tr> <th>高温补贴标准:</th> <td> <input name="htsubs" class="easyui-validatebox" type="text"  readonly="readonly"/> </td> </tr> 
				<tr> <th>低温补贴标准:</th> <td> <input name="ltsubs" class="easyui-validatebox" type="text"  readonly="readonly"/> </td> </tr>
				<tr> <th>平时加班时数:</th> <td> <input name="owoverh" class="easyui-validatebox" type="text"  readonly="readonly"/> </td> </tr> 
				<tr> <th>周末加班时数:</th> <td> <input name="wwoverh" class="easyui-validatebox" type="text"   readonly="readonly"/> </td> </tr>
				<tr> <th>节假日加班时数:</th> <td> <input name="hwoverh" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr> 
				<tr> <th>病假（天）:</th> <td> <input name="sickleave" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr>
				<tr> <th>事假（天）:</th> <td> <input name="noleave" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr> 
				<tr> <th>产假（天）:</th> <td> <input name="mateleave" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr>
				<tr> <th>考勤类别:</th> <td> <input name="attendt" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr> 
				
				<tr> <th>实发基本工资:</th> <td> <input name="bsalaryr" class="easyui-validatebox" type="text" readonly="readonly"  /> </td> </tr>
				<tr> <th>实发综合补贴:</th> <td> <input name="tsubsir" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr>
				<tr> <th>实发话费补贴:</th> <td> <input name="callsubr" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr> 
				<tr> <th>实发交通补贴:</th> <td> <input name="tsubsr" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr>
				<tr> <th>实发派驻津贴:</th> <td> <input name="esubsr" class="easyui-validatebox" type="text"  readonly="readonly"/> </td> </tr> 
				<tr> <th>实发高温补贴:</th> <td> <input name="htsubsr" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr>
				<tr> <th>实发低温补贴:</th> <td> <input name="ltsubsr" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr> 
				<tr> <th>绩效系数:</th> <td> <input name="percoef" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr>
				<tr> <th>绩效奖金:</th> <td> <input name="perprize" class="easyui-validatebox" type="text"   readonly="readonly"/> </td> </tr> 
				<tr> <th>销售奖金:</th> <td> <input name="saleprize" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr>
				<tr> <th>其他奖金:</th> <td> <input name="oprize" class="easyui-validatebox" type="text" readonly="readonly"  /> </td> </tr> 
				<tr> <th>夜班补贴:</th> <td> <input name="nightsub" class="easyui-validatebox" type="text"  readonly="readonly"/> </td> </tr>
				<tr> <th>其他补贴:</th> <td> <input name="osubsi" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr> 
				
				<tr> <th>补发工资:</th> <td> <input name="ssalary" class="easyui-validatebox" type="text"   readonly="readonly"/> </td> </tr>
				<tr> <th>节日津贴:</th> <td> <input name="holsubsi" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr>
				<tr> <th>免税项目:</th> <td> <input name="exeitem" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr> 
				<tr> <th>实发季度奖金:</th> <td> <input name="qprizer" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr>
				<tr> <th>加班费:</th> <td> <input name="overtime" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr> 
				<tr> <th>病假扣款:</th> <td> <input name="sickleavec" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr>
				<tr> <th>事假扣款:</th> <td> <input name="noleavec" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr> 
				<tr> <th>产假扣款:</th> <td> <input name="mateleavec" class="easyui-validatebox" type="text" readonly="readonly"  /> </td> </tr>
				<tr> <th>应发金额:</th> <td> <input name="shsalary" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr> 
				<tr> <th>社保个人合计:</th> <td> <input name="sosecpt" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr>
				<tr> <th>社保单位合计:</th> <td> <input name="sosecct" class="easyui-validatebox" type="text" readonly="readonly"  /> </td> </tr> 
				<tr> <th>养老单位:</th> <td> <input name="pasturec" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr>
				<tr> <th>养老个人:</th> <td> <input name="pasturep" class="easyui-validatebox" type="text" readonly="readonly"  /> </td> </tr> 
				
				<tr> <th>医疗单位:</th> <td> <input name="healthc" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr>
				<tr> <th>医疗个人:</th> <td> <input name="healthp" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr>
				<tr> <th>失业单位:</th> <td> <input name="outworkc" class="easyui-validatebox" type="text" readonly="readonly"  /> </td> </tr> 
				<tr> <th>失业个人:</th> <td> <input name="outworkp" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr>
				<tr> <th>工伤单位:</th> <td> <input name="industc" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr> 
				<tr> <th>生育单位:</th> <td> <input name="reprodc" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr>
				<tr> <th>住房公积金单位:</th> <td> <input name="fundc" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr> 
				<tr> <th>住房公积金个人:</th> <td> <input name="fundp" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr>
				<tr> <th>应税金额:</th> <td> <input name="tamount" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr> 
				<tr> <th>工资税:</th> <td> <input name="payrollt" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr>
				<tr> <th>工资税率:</th> <td> <input name="payrolltr" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr> 
				<tr> <th>税后扣款:</th> <td> <input name="tdeduct" class="easyui-validatebox" type="text"  readonly="readonly"/> </td> </tr>
				<tr> <th>已发放节日津贴:</th> <td> <input name="holsubsid" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr> 
				
				<tr> <th>税后调整:</th> <td> <input name="atadjust" class="easyui-validatebox" type="text"  readonly="readonly"/> </td> </tr> 
				<tr> <th>银行实发:</th> <td> <input name="bpayr" class="easyui-validatebox" type="text"  readonly="readonly"/> </td> </tr>
				<tr> <th>身份证号码:</th> <td> <input name="cardid" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr> 
				<tr> <th>计薪状态:</th> <td> <input name="paidstatus" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr>
				<tr> <th>银行账号:</th> <td> <input name="bandno" class="easyui-validatebox" type="text" readonly="readonly"  /> </td> </tr> 
				<tr> <th>银行账号开户行名称:</th> <td> <input name="accopenbn" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr>
				<tr> <th>银行账号开户地:</th> <td> <input name="accopenba" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr> 
				<tr> <th>报税公司:</th> <td> <input name="fillbec" class="easyui-validatebox" type="text" readonly="readonly" /> </td> </tr>
				<tr> <th>报税城市:</th> <td> <input name="fillbecity" class="easyui-validatebox" type="text" readonly="readonly"  /> </td> </tr> 
				<tr> <th>核算日期:</th> <td> <input name="countDate" class="easyui-validatebox" type="text"  readonly="readonly" /> </td> </tr> 
			</table>
		    <div style="padding:5px;text-align:center;">  
	            <a href="javascript:;" onClick = "closeWin('viewPersonSalaryWin')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
	     	</div>  
		</form>
	</div> 
	
	</body>   
</html>
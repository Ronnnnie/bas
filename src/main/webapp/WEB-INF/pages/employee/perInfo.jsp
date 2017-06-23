<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<title>个人信息管理</title>

<style type="text/css">
.formdiv {
	padding: 5px;
}

.required {
	color: red;
}
</style>
<script type="text/javascript"
	src="${CONTEXT_PATH}/js/app/employeePerInfo.js"></script>
<link rel="stylesheet"
	href="${CONTEXT_PATH}/css/jquery.multiselect2side.css" type="text/css"
	media="screen" />

<script type="text/javascript"
	src="${CONTEXT_PATH}/js/jquery.multiselect2side.js"></script>
</head>
<body>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north'" title=" "
			style="width: 100%; height: 15%;">
			<form>
				<div style="width: 90%; margin: 5px;">
					<label>员工工号：</label> <input type="text" name="userCode"
						id="searchUserCode" class="easyui-validatebox"
						data-options="validType:'unnormal'" maxlength="20" /> 
					<label>员工姓名：</label> <input type="text" name="userName"
						id="searchUserName" class="easyui-validatebox"
						data-options="validType:'unnormal'" maxlength="40" /> 
					<a id="button-search" href="javascript:;" onclick="query()"
						class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
				</div>
			</form>
		</div>
		<div data-options="region:'center'">
			<table id="employeeGrid" toolbar="#tb"></table>
		</div>
	</div>

	<!-- 新增员工 -->
	<div id="employeeWin"
		style="width: 350px; text-align: center; display: none;">
		<form id="employeeFrm" method="post">
			<table class="formTable">
				<tr>
					<th><samp>*</samp>员工工号:</th>
					<td><input name="jobNo" class="easyui-validatebox"  type="text" data-options="required:true,validType:'code'" maxlength="20" />
					</td>
				</tr>
				<tr>
					<th><samp>*</samp>员工姓名:</th>
					<td><input name="employeeName" class="easyui-validatebox" data-options="required:true" maxlength="40"
						type="text" /></td>
				</tr>
				<tr>
					<th><samp>*</samp>员工状态:</th>
					<td><input name="status" class="easyui-validatebox" maxlength="40" data-options="required:true"
						type="text"  /></td>
				</tr>
				<tr>
					<th>人员类型:</th>
					<td><input name="employeeType" class="easyui-validatebox" maxlength="140"
						type="text" /></td>
				</tr>
				<tr>
					<th>职位:</th>
					<td><input name="positionName" class="easyui-validatebox" maxlength="140"
						type="text" /></td>
				</tr>
				<tr>
					<th>外派津贴标准:</th>
					<td><input name="egressStandard" class="easyui-validatebox" maxlength="28"
						type="text" /></td>
				</tr>
				<tr>
					<th>基本工资:</th>
					<td><input name="baseSalary" class="easyui-validatebox" 
						type="text" maxlength="17" /></td>
				</tr>
				<tr>
					<th>综合补贴:</th>
					<td><input name="synAllowance" class="easyui-validatebox"
						type="text" maxlength="17" /></td>
				</tr>
				<tr>
					<th>月度奖金基数:</th>
					<td><input name="monthBonusBase" class="easyui-validatebox"
						type="text" maxlength="17" /></td>
				</tr>
				<tr>
					<th>季度奖金基数:</th>
					<td><input name="quarterBonusBase" class="easyui-validatebox"
						type="text" maxlength="17" /></td>
				</tr>
				<tr>
					<th>年度奖金基数:</th>
					<td><input name="yearBonusBase" class="easyui-validatebox"
						type="text" maxlength="17" /></td>
				</tr>
				<tr>
					<th>行政组织名称:</th>
					<td><input name="organizeName" class="easyui-validatebox"
						type="text" maxlength="255" /></td>
				</tr>
				<tr>
					<th>是否在职:</th>
					<td><input name="iswork" class="easyui-validatebox"
						type="text" maxlength="10" /></td>
				</tr>
				<tr>
					<th>员工属地:</th>
					<td><input name="empCity" class="easyui-validatebox" maxlength="160"
						type="text" /></td>
				</tr>
				<tr>
					<th>员工属省:</th>
					<td><input name="empProvince" class="easyui-validatebox" maxlength="160"
						type="text" /></td>
				</tr>
				<tr>
					<th>入职日期:</th>
					<td><input name="entryDate" class="easyui-validatebox"
						type="text" data-options="requried:true,validType:'promptDate'"/></td>
				</tr>
				<tr>
					<th>转正日期:</th>
					<td><input name="confirmDate" class="easyui-validatebox"
						type="text" data-options="validType:'promptDate'" /></td>
				</tr>
				<tr>
					<th>离职日期:</th>
					<td><input name="leaveDate" class="easyui-validatebox"
						type="text" data-options="validType:'promptDate'" /></td>
				</tr>
				<tr>
					<th>合同到期日:</th>
					<td><input name="contractEndDate" class="easyui-validatebox"
						type="text" data-options="validType:'promptDate'" /></td>
				</tr>
				<tr>
					<th>身份证号:</th>
					<td><input name="identityId" class="easyui-validatebox" maxlength="18"
						type="text" data-options="validType:'number'" /></td>
				</tr>
				<tr>
					<th>银行卡号:</th>
					<td><input name="bankCardId" class="easyui-validatebox" maxlength="32"
						type="text" data-options="required:true,validType:'number'" /></td>
				</tr>
				<tr>
					<th>银行名称:</th>
					<td><input name="bankName" class="easyui-validatebox" maxlength="200"
						type="text" /></td>
				</tr>
				<tr>
					<th>银行卡所在城市:</th>
					<td><input name="bankCityName" class="easyui-validatebox" maxlength="200"
						type="text" /></td>
				</tr>
				<tr>
					<th>公司邮箱:</th>
					<td><input name="email" class="easyui-validatebox" 
					data-options="required:true,validType:'email'" type="text" />
					</td>
				</tr>
				<tr>
					<th>所任职位:</th>
					<td><input name="positionJob" class="easyui-validatebox"
						type="text" maxlength="400"/></td>
				</tr>
				<tr>
					<th>一级组织名称:</th>
					<td><input name="firstOrganizeName" class="easyui-validatebox"
						type="text" maxlength="400"/></td>
				</tr>
				<tr>
					<th>二级组织名称:</th>
					<td><input name="secondOrgName" class="easyui-validatebox"
						type="text" maxlength="400"/></td>
				</tr>
				<tr>
					<th>三级组织名称:</th>
					<td><input style="width:146px" id="thirdOrgName" name="thirdOrgName" class="easyui-validatebox" 
						type="text"  maxlength="400"/></td>
				</tr>
				<tr>
					<th>四级组织名称:</th>
					<td><input name="fourthOrgName" class="easyui-validatebox"
						type="text" maxlength="400"/></td>
				</tr>
				<tr>
					<th>五级组织名称:</th>
					<td><input name="fifthOrgName" class="easyui-validatebox"
						type="text" maxlength="400"/></td>
				</tr>
				<tr>
					<th>六级组织名称:</th>
					<td><input name="sixthOrgName" class="easyui-validatebox"
						type="text" maxlength="400"/></td>
				</tr>
				<tr>
					<th>七级组织名称:</th>
					<td><input name="seventhOrgName" class="easyui-validatebox"
						type="text" maxlength="400"/></td>
				</tr>
			</table>
			<div style="padding: 5px; text-align: center;">
				<a href="javascript:;" onClick="saveEmployee()"
					class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a> <a
					href="javascript:;" onClick="closeWin('employeeWin')"
					class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
			</div>
		</form>
	</div>

	<!-- 查看详情 -->
	<div id="employeeInfoWin"
		style="width: 350px; text-align: center; display: none;">
		<form id="queryInfoFrm" method="post">

			<table class="formTable">
				<tr>
					<th>员工工号:</th>
					<td><input name="jobNo" class="easyui-validatebox" type="text"
						readonly="true" /></td>
				</tr>
				<tr>
					<th>员工姓名:</th>
					<td><input name="employeeName" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>员工状态:</th>
					<td><input name="status" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>人员类型:</th>
					<td><input name="employeeType" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>职位:</th>
					<td><input name="positionName" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>外派津贴标准:</th>
					<td><input name="egressStandard" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>基本工资:</th>
					<td><input name="baseSalary" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>综合补贴:</th>
					<td><input name="synAllowance" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>月度奖金基数:</th>
					<td><input name="monthBonusBase" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>季度奖金基数:</th>
					<td><input name="quarterBonusBase" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>年度奖金基数:</th>
					<td><input name="yearBonusBase" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>行政组织名称:</th>
					<td><input name="organizeName" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>是否在职:</th>
					<td><input name="iswork" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>员工属地:</th>
					<td><input name="empCity" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>员工属省:</th>
					<td><input name="empProvince" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>入职日期:</th>
					<td><input name="entryDate" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>转正日期:</th>
					<td><input name="confirmDate" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>离职日期:</th>
					<td><input name="leaveDate" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>合同到期日:</th>
					<td><input name="contractEndDate" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>身份证号:</th>
					<td><input name="identityId" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>银行卡号:</th>
					<td><input name="bankCardId" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>银行名称:</th>
					<td><input name="bankName" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>银行卡所在城市:</th>
					<td><input name="bankCityName" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>公司邮箱:</th>
					<td><input name="email" class="easyui-validatebox" type="text"
						readonly="true" /></td>
				</tr>
				<tr>
					<th>所任职位:</th>
					<td><input name="positionJob" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>一级组织名称:</th>
					<td><input name="firstOrganizeName" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>二级组织名称:</th>
					<td><input name="secondOrgName" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>三级组织名称:</th>
					<td><input style="width:146px" id="quethirdOrgName" name="thirdOrgName" class="easyui-validatebox" 
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>四级组织名称:</th>
					<td><input name="fourthOrgName" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>五级组织名称:</th>
					<td><input name="fifthOrgName" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>六级组织名称:</th>
					<td><input name="sixthOrgName" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
				<tr>
					<th>七级组织名称:</th>
					<td><input name="seventhOrgName" class="easyui-validatebox"
						type="text" readonly="true" /></td>
				</tr>
			</table>

		</form>
	</div>


	<!-- 修改角色 -->
	<div id="updateEmployeeWin"
		style="width: 350px; text-align: center; display: none;">
		<form id="updateEmployeeFrm" method="post">
			<table class="formTable">
				<tr>
					<th>员工工号:</th>
					<td><input name="jobNo" class="easyui-validatebox" type="text"
						readonly="true" /></td>
				</tr>
				<tr>
					<th><samp>*</samp>员工姓名:</th>
					<td><input name="employeeName" class="easyui-validatebox" data-options="required:true" maxlength="40"
						type="text" /></td>
				</tr>
				<tr>
					<th><samp>*</samp>员工状态:</th>
					<td><input name="status" class="easyui-validatebox" maxlength="40" data-options="required:true"
						type="text"  /></td>
				</tr>
				<tr>
					<th>人员类型:</th>
					<td><input name="employeeType" class="easyui-validatebox" maxlength="140"
						type="text" /></td>
				</tr>
				<tr>
					<th>职位:</th>
					<td><input name="positionName" class="easyui-validatebox" maxlength="140"
						type="text" /></td>
				</tr>
				<tr>
					<th>外派津贴标准:</th>
					<td><input name="egressStandard" class="easyui-validatebox" maxlength="28"
						type="text" /></td>
				</tr>
				<tr>
					<th>基本工资:</th>
					<td><input name="baseSalary" class="easyui-validatebox" 
						type="text" maxlength="17" /></td>
				</tr>
				<tr>
					<th>综合补贴:</th>
					<td><input name="synAllowance" class="easyui-validatebox"
						type="text" maxlength="17" /></td>
				</tr>
				<tr>
					<th>月度奖金基数:</th>
					<td><input name="monthBonusBase" class="easyui-validatebox"
						type="text" maxlength="17" /></td>
				</tr>
				<tr>
					<th>季度奖金基数:</th>
					<td><input name="quarterBonusBase" class="easyui-validatebox"
						type="text" maxlength="17" /></td>
				</tr>
				<tr>
					<th>年度奖金基数:</th>
					<td><input name="yearBonusBase" class="easyui-validatebox"
						type="text" maxlength="17" /></td>
				</tr>
				<tr>
					<th>行政组织名称:</th>
					<td><input name="organizeName" class="easyui-validatebox"
						type="text" maxlength="255" /></td>
				</tr>
				<tr>
					<th>是否在职:</th>
					<td><input name="iswork" class="easyui-validatebox"
						type="text" maxlength="10" /></td>
				</tr>
				<tr>
					<th>员工属地:</th>
					<td><input name="empCity" class="easyui-validatebox" maxlength="160"
						type="text" /></td>
				</tr>
				<tr>
					<th>员工属省:</th>
					<td><input name="empProvince" class="easyui-validatebox" maxlength="160"
						type="text" /></td>
				</tr>
				<tr>
					<th>入职日期:</th>
					<td><input name="entryDate" class="easyui-validatebox"
						type="text" data-options="requried:true,validType:'promptDate'"/></td>
				</tr>
				<tr>
					<th>转正日期:</th>
					<td><input name="confirmDate" class="easyui-validatebox"
						type="text" data-options="validType:'promptDate'" /></td>
				</tr>
				<tr>
					<th>离职日期:</th>
					<td><input name="leaveDate" class="easyui-validatebox"
						type="text" data-options="validType:'promptDate'" /></td>
				</tr>
				<tr>
					<th>合同到期日:</th>
					<td><input name="contractEndDate" class="easyui-validatebox"
						type="text" data-options="validType:'promptDate'" /></td>
				</tr>
				<tr>
					<th>身份证号:</th>
					<td><input name="identityId" class="easyui-validatebox" maxlength="18"
						type="text" data-options="validType:'number'" /></td>
				</tr>
				<tr>
					<th>银行卡号:</th>
					<td><input name="bankCardId" class="easyui-validatebox" maxlength="32"
						type="text" data-options="required:true,validType:'number'" /></td>
				</tr>
				<tr>
					<th>银行名称:</th>
					<td><input name="bankName" class="easyui-validatebox" maxlength="200"
						type="text" /></td>
				</tr>
				<tr>
					<th>银行卡所在城市:</th>
					<td><input name="bankCityName" class="easyui-validatebox" maxlength="200"
						type="text" /></td>
				</tr>
				<tr>
					<th>公司邮箱:</th>
					<td><input name="email" class="easyui-validatebox" 
					data-options="required:true,validType:'email'" type="text" />
					</td>
				</tr>
				<tr>
					<th>所任职位:</th>
					<td><input name="positionJob" class="easyui-validatebox"
						type="text" maxlength="400"/></td>
				</tr>
				<tr>
					<th>一级组织名称:</th>
					<td><input name="firstOrganizeName" class="easyui-validatebox"
						type="text" maxlength="400"/></td>
				</tr>
				<tr>
					<th>二级组织名称:</th>
					<td><input name="secondOrgName" class="easyui-validatebox"
						type="text" maxlength="400"/></td>
				</tr>
				<tr>
					<th>三级组织名称:</th>
					<td><input style="width:146px" id="upthirdOrgName" name="thirdOrgName" class="easyui-validatebox" 
						type="text" maxlength="400"/></td>
				</tr>
				<tr>
					<th>四级组织名称:</th>
					<td><input name="fourthOrgName" class="easyui-validatebox"
						type="text" maxlength="400"/></td>
				</tr>
				<tr>
					<th>五级组织名称:</th>
					<td><input name="fifthOrgName" class="easyui-validatebox"
						type="text" maxlength="400"/></td>
				</tr>
				<tr>
					<th>六级组织名称:</th>
					<td><input name="sixthOrgName" class="easyui-validatebox"
						type="text" maxlength="400"/></td>
				</tr>
				<tr>
					<th>七级组织名称:</th>
					<td><input name="seventhOrgName" class="easyui-validatebox"
						type="text" maxlength="400"/></td>
				</tr>
			</table>
			<div style="padding: 5px; text-align: center;">
				<a href="javascript:;" onClick="updateEmployee()"
					class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a> <a
					href="javascript:;" onClick="closeWin('updateEmployeeWin')"
					class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
			</div>
		</form>
	</div>


	<div id="addResourcesWin" data-options="closed:true">
		<!-- 分配资源 -->
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north'" style="height: 10%; padding: 5px">
				<label>角色名称:</label> <input type="text" name="roleCname"
					id="showroleCname" /> <label>系统名称:</label> <input type="text"
					name="sysCname" id="showsysCname" />
			</div>
			<div data-options="region:'west'" title="资源" style="height: 60%">
				<ul id="roleResTree" data-options="animate:true,checkbox:true">

				</ul>
			</div>

			<div data-options="region:'south',border:false"
				style="text-align: right; padding: 5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
					href="javascript:void(0)" onclick="addRoleResources()">保存</a> <a
					class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
					href="javascript:void(0)" onclick="closeWin('addResourcesWin')">取消</a>
			</div>
		</div>
	</div>

	<!-- datagrid 按钮 -->
	<div id="tb" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
			<a href="#" onclick="addEmployee()" class="easyui-linkbutton"
				iconCls="icon-add">新增</a> <a href="#" onclick="deleteEmployeeInfo()"
				class="easyui-linkbutton" iconCls="icon-remove">删除</a> <a href="#"
				onclick="allUpdate()" class="easyui-linkbutton"
				iconCls="icon-reload">一键更新</a> <a href="#" onclick="exportData()"
				class="easyui-linkbutton" iconCls="icon-redo">导出</a>
		</div>
	</div>


	<!-- 双向选择框，有插件，怕使用的版本冲突，暂时不适用插件 -->

	<div id="multSelected" data-options="closed:true">
		<select name="searchable[]" id='searchable' multiple='multiple'
			size="15">
		</select>

		<div>
		<div id="tt" style="margin-left: 30%">
		选择模板:<select id="templete" onchange="selectValue()">
		<option value="">请选择模板</option>
				<option value="empTemplete1">模板1</option>
				<option value="empTemplete2">模板2</option>
				<option value="empTemplete3">模板3</option>
				<option value="empTemplete4">模板4</option>
				<option value="empTemplete5">模板5</option>
				<option value="empTemplete6">模板6</option>
				<option value="empTemplete7">模板7</option>
				<option value="empTemplete8">模板8</option>
			</select>
			<input onclick="saveTemplete();"  type="button" value="保存模板" /><br />
		</div>
		</div>
		<div id="tt1">
			 <a id="exportA" style="margin-left: 40%;font-size: 16px;"  href="#">导出</a>
		</div>
		
		<div id="uu">
			 <a id="updateA" onclick="updateAllByPro()" style="margin-left: 40%;font-size: 16px;"  href="#">更新</a>
		</div>

	</div>
	
	
<!-- 	<div id="multSelected1" data-options="closed:true"> -->
<!-- 		<select name="searchable[]" id='searchable1' multiple='multiple' -->
<!-- 			size="15"> -->
<!-- 		</select> -->

<!-- 		<div> -->
<!-- 			 <a id="updateA" onclick="updateAllByPro()" style="margin-left: 40%;font-size: 16px;"  href="#">更新</a> -->
<!-- 		</div> -->

<!-- 	</div> -->
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业招聘需求管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/erp/companyJobneed/">企业招聘需求列表</a></li>
		<li class="active"><a href="${ctx}/erp/companyJobneed/form?id=${companyJobneed.id}">企业招聘需求<shiro:hasPermission name="erp:companyJobneed:edit">${not empty companyJobneed.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="erp:companyJobneed:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="companyJobneed" action="${ctx}/erp/companyJobneed/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">归属公司:</label>
			<div class="controls">
                <sys:treeselect id="company" name="company.id" value="${companyJobneed.company.id}" labelName="company.name" labelValue="${companyJobneed.company.name}"
					title="公司" url="/erp/companyInfo/treeData?type=1"  cssStyle = "width:225px"  cssClass="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属公司22:</label>
			<div class="controls">
                <sys:treeselect id="company22" name="company.id" value="${companyJobneed.company.id}" labelName="company.name22" labelValue="${companyJobneed.company.name}"
					selectParam="companyName"	title="公司222" url="/erp/companyInfo/treeData?type=1"  cssStyle = "width:225px"  cssClass="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人才分类：</label>
			<div class="controls">
				<form:select path="sort" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('talent_sort')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">岗位要求：</label>
			<div class="controls">
				<form:input path="jobskill" htmlEscape="false" maxlength="200"  class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select path="sex" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人才数量：</label>
			<div class="controls">
				<form:input path="jobquantity" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历要求：</label>
			<div class="controls">
				<form:select path="educationneed" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('education_need')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作年限：</label>
			<div class="controls">
				<form:input path="workedyear" htmlEscape="false" maxlength="10" class="input-xlarge  digits required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布时间：</label>
			<div class="controls">
				<input name="publistime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required "
					value="<fmt:formatDate value="${companyJobneed.publistime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最晚到岗时间：</label>
			<div class="controls">
				<input name="jointime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required "
					value="<fmt:formatDate value="${companyJobneed.jointime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业要求：</label>
			<div class="controls">
				<form:input path="majorneed" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="erp:companyJobneed:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
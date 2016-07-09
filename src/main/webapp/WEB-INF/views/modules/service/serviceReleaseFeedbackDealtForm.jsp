<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业服务处理管理</title>
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
		<li><a href="${ctx}/service/serviceReleaseFeedbackDealt/">企业服务处理列表</a></li>
		<li class="active"><a href="${ctx}/service/serviceReleaseFeedbackDealt/form?id=${serviceReleaseFeedbackDealt.id}">企业服务处理<shiro:hasPermission name="service:serviceReleaseFeedbackDealt:edit">${not empty serviceReleaseFeedbackDealt.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="service:serviceReleaseFeedbackDealt:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="serviceReleaseFeedbackDealt" action="${ctx}/service/serviceReleaseFeedbackDealt/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">服务名称：</label>
			<div class="controls">
				<sys:treeselect id="serviceRelease" name="serviceRelease.id" value="${serviceReleaseFeedback.serviceRelease.id}" labelName="serviceRelease.name" labelValue="${serviceReleaseFeedback.serviceRelease.name}" 
				title="服务名称" url="/service/serviceRelease/treeData?type=1" cssClass="input-small" allowClear="true"/>
				<span class="help-inline"><font color="red">*</font> </span>	
				
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">服务企业：</label>
			<div class="controls" id="companydiv">
				<sys:treeselect id="company" name="company.id" value="${serviceReleaseFeedback.company.id}" labelName="company.name" labelValue="${serviceReleaseFeedback.company.name}" 
				title="公司" url="/service/serviceReleaseFeedback/treeDatabyRename" cssClass="input-small" allowClear="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>					
		<div class="control-group">
			<label class="control-label">服务内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="2000" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业意见：</label>
			<div class="controls">
				<form:textarea path="companyView" htmlEscape="false" rows="4" maxlength="2000" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">完成情况：</label>
			<div class="controls">
				<form:input path="shcedule" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" rows="4" maxlength="1500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务时间：</label>
			<div class="controls">
				<input name="serviceDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${serviceReleaseFeedbackDealt.serviceDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="service:serviceReleaseFeedbackDealt:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
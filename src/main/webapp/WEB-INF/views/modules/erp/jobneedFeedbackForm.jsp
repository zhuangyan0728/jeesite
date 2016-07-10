<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>反馈信息管理</title>
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
		<li><a href="${ctx}/erp/jobneedFeedback/">反馈信息列表</a></li>
		<li class="active"><a href="${ctx}/erp/jobneedFeedback/form?id=${jobneedFeedback.id}">反馈信息<shiro:hasPermission name="erp:jobneedFeedback:edit">${not empty jobneedFeedback.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="erp:jobneedFeedback:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="jobneedFeedback" action="${ctx}/erp/jobneedFeedback/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">招聘需求</label>
			<div class="controls">
                <sys:treeselect id="joobneed" name="joobneed.id" value="${jobneedFeedback.joobneed.id}" labelName="joobneed.name" labelValue="${jobneedFeedback.joobneed.remarks}"
					 title="招聘需求" url="/erp/companyJobneed/treeData?type=1"  cssStyle = "width:225px"  cssClass="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回馈类型：</label>
			<div class="controls">
				<form:select path="feedbacktype" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('jobFeedBack_Type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回馈时间：</label>
			<div class="controls">
				<input name="feedbacktime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${jobneedFeedback.feedbacktime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回馈具体内容：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="erp:jobneedFeedback:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户注册</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		 table{border:0;margin:20;border-collapse:collapse;border-spacing:0;}
		/*控制cellspacing*/
		table td{padding:20;} 
	</style>
   
	<script type="text/javascript">
		$(document).ready(function() {
			$("#no").focus();
			$("#inputForm").validate({
				//rules: {
					//loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				//},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
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
	<div class="header">
		<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error">${message}</label>
		</div>
	</div>
	<div> &nbsp; &nbsp; </div>
	<h1 class="form-signin-heading" align="center" >新用户注册</h1>
	<div> &nbsp; &nbsp; </div>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/register/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		
		<table align="center" cellspacing="100"  border="1" >
			<tr >
				<td > 
					<label class="control-label" >企业名称:</label>
	                <form:input path="company.name" htmlEscape="false" maxlength="50" class="required"/>
	                <span class="help-inline"><font color="red">*</font> </span>
				 </td>
			</tr>
			<tr >
				<td > 
					<label class="control-label" >企业信用代码:</label>
	                <form:input path="company.organizecode" htmlEscape="false" maxlength="50" class="required"/>
	                <span class="help-inline"><font color="red">*</font> </span>
				 </td>
			</tr>
 
			<tr>
				<td><label class="control-label">姓名:</label>
					<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
			</tr>
			<tr>
				<td><label class="control-label">登录名:</label>
						<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
						<form:input path="loginName" htmlEscape="false" maxlength="50" class="required userName"/>
						<span class="help-inline"><font color="red">*</font> </span>
				</td>
			</tr>
			<tr>
				<td><label class="control-label">密码:</label>
							<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="${empty user.id?'required':''}"/>
							<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
							<c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>

				</td>
			</tr>
			<tr>
				<td><label class="control-label">确认密码:</label>
					<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
					<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
				</td>
			</tr>
			<tr>
				<td><label class="control-label">邮箱:</label><form:input path="email" htmlEscape="false" maxlength="100" class="email"/></td>
			</tr>
			<tr>
				<td><label class="control-label">电话:</label><form:input path="phone" htmlEscape="false" maxlength="100"/></td>
			</tr>
			<tr>
				<td><label class="control-label">手机:</label><form:input path="mobile" htmlEscape="false" maxlength="100"/></td>
			</tr>
			<tr>
				<td><label class="control-label">备注:</label><form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/></td>
			</tr>
			<tr>
				<td>
					<div class="form-actions">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="注 册"/>&nbsp;
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</td>
			</tr>
		 
		</table>
		
		
		
		
		
		
		
		
 
		
		
	</form:form>
</body>
</html>
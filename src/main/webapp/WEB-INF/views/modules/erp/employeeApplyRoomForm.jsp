<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业人才申请住房管理</title>
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
		
		//解析身份证上的出生日期以及性别
		function getData(){
		   if(!/^\d{6}((?:19|20)((?:\d{2}(?:0[13578]|1[02])(?:0[1-9]|[12]\d|3[01]))|(?:\d{2}(?:0[13456789]|1[012])(?:0[1-9]|[12]\d|30))|(?:\d{2}02(?:0[1-9]|1\d|2[0-8]))|(?:(?:0[48]|[2468][048]|[13579][26])0229)))\d{2}(\d)[xX\d]$/.test($("#identityno").val())){
		      $.jBox.alert("身份证号非法","提示");
		      return;
		   }
		   var bd =(RegExp.$1).substr(0,4)+'-'+(RegExp.$1).substr(4,2)+'-'+(RegExp.$1).substr(6,2);	
		   $("#birthday").val(bd);
		   
		   var idno= $("#identityno").val();
		   var last = idno[idno.length - 2];
		   var sex="";
		   if(last % 2 != 0){
	             sex="男";
	             $("#sex1").attr("checked","checked");
	         }else{
	             sex="女";
	             $("#sex2").attr("checked","checked");
	         }
		}



	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/erp/employee/">人才申请住房信息列表</a></li>
		<li class="active"><a href="${ctx}/erp/employee/applyform?id=${employee.id}">人才申请住房信息<shiro:hasPermission name="erp:employee:edit">${not empty employee.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="erp:employee:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="employee" action="${ctx}/erp/employee/passApply" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
	<table>
		<tr>
			<td>
				<div class="control-group">
					<label class="control-label">归属公司:</label>
					<div class="controls">
						<c:choose>
							<c:when test="${empty fns:getUser().company.id}">
								<sys:treeselect id="company" name="company.id" value="${employee.company.id}" labelName="company.name" labelValue="${employee.company.name}"
									title="公司" url="/erp/companyInfo/treeData?type=1"  cssStyle = "width:225px"/>
							</c:when>
							<c:otherwise>
								<c:if test="${not empty employee.company.id}">
									<div style="display: none;">
									  <sys:treeselect id="company" name="company.id" value="${employee.company.id}" labelName="company.name" labelValue="${employee.company.name}"
									   title="公司" url="/erp/companyInfo/treeData?type=1"  cssStyle = "width:225px"/>
									 </div>
									 <label>${employee.company.name}</label>
								 </c:if>
								 <c:if test="${empty employee.company.id}">
									<label>${fns:getUser().company.name}</label>
								 </c:if>
							</c:otherwise>
						</c:choose>
						<!-- <span class="help-inline"><font color="red">*</font> </span> -->
					</div>
				</div>
			</td>
			<td>
				<div class="control-group">
					<label class="control-label">姓名：</label>
					<div class="controls">
						<form:input path="name" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</td>

		</tr>

		<tr>
			<td>
				<div class="control-group">
					<label class="control-label">身份证号：</label>
					<div class="controls">
						<form:input path="identityno" htmlEscape="false" maxlength="100" onblur="javascript:getData();" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</td>
			<td>
				<div class="control-group">
					<label class="control-label">性别：</label>
					<div class="controls">
						<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</td>
		</tr>

		<tr>
			<td>
				<div class="control-group">
					<label class="control-label">出生日期：</label>
					<div class="controls">
						<form:input path="birthday" htmlEscape="false" maxlength="200" class="input-xlarge"/>
					</div>
				</div>
			</td>
			<td>
				<div class="control-group">
					<label class="control-label">籍贯：</label>
					<div class="controls">
						<form:input path="nativeplace" htmlEscape="false" maxlength="200" class="input-xlarge"/>
						<!-- <span class="help-inline"><font color="red">*</font> </span> -->
					</div>
				</div>
			</td>
		</tr>

		<tr>
			<td>
				<div class="control-group">
					<label class="control-label">居住地：</label>
					<div class="controls">
						<form:select path="residenceplace" class="input-xlarge">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('residence_place')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<!-- <span class="help-inline"><font color="red">*</font> </span> -->
					</div>
				</div>
			</td>
			<td>
				<div class="control-group">
					<label class="control-label">婚姻状况：</label>
					<div class="controls">
						<form:radiobuttons path="martitalstatus" items="${fns:getDictList('marital_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						<!-- <span class="help-inline"><font color="red">*</font> </span> -->
					</div>
				</div>
			</td>
		</tr>

		<tr>
			<td>
				<div class="control-group">
					<label class="control-label">学历：</label>
					<div class="controls">
						<form:select path="education" class="input-xlarge required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('education')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</td>
			<td>
				<div class="control-group">
					<label class="control-label">技术职称：</label>
					<div class="controls">
						<form:input path="worktitle" htmlEscape="false" maxlength="100" class="input-xlarge"/>
						<!-- <span class="help-inline"><font color="red">*</font> </span> -->
					</div>
				</div>
			</td>
		</tr>

		<tr>
			<td>
				<div class="control-group">
					<label class="control-label">职务：</label>
					<div class="controls">
						<form:input path="workpositon" htmlEscape="false" maxlength="100" class="input-xlarge"/>
						<!-- <span class="help-inline"><font color="red">*</font> </span> -->
					</div>
				</div>
			</td>
			<td>
				<div class="control-group">
					<label class="control-label">薪酬状况：</label>
					<div class="controls">
						<form:select path="salary" class="input-xlarge">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('salary_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<!-- <span class="help-inline"><font color="red">*</font> </span> -->
					</div>
				</div>
			</td>
		</tr>

		<tr>
			<td>
			<div class="control-group">
				<label class="control-label">人才分类：</label>
				<div class="controls">
					<form:select path="sort" class="input-xlarge">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('talent_sort')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<!-- <span class="help-inline"><font color="red">*</font> </span> -->
				</div>
			</div>
			</td>
			<td>
			<div class="control-group">
				<label class="control-label">是否关键岗位：</label>
				<div class="controls">
					<form:radiobuttons path="ifkey" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					<!-- <span class="help-inline"><font color="red">*</font> </span> -->
				</div>
			</div>
			</td>
		</tr>

		<tr>
			<td>
				<div class="control-group">
					<label class="control-label">是否申领居住证：</label>
					<div class="controls">
						<form:radiobuttons path="ifresidencecard" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						<!-- <span class="help-inline"><font color="red">*</font> </span> -->
					</div>
				</div>
			</td>
			<td>
				<div class="control-group">
					<label class="control-label">居住证号码：</label>
					<div class="controls">
						<form:input path="residenceno" htmlEscape="false" maxlength="100" class="input-xlarge "/>
					</div>
				</div>
			</td>
		</tr>

		<tr>
			<td>
				<div class="control-group">
					<label class="control-label">居住证有效期：</label>
					<div class="controls">
						<input name="validity" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${employee.validity}" pattern="yyyy-MM-dd"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</div>
				</div>
			</td>
			<td>
				<div class="control-group">
					<label class="control-label">居住积分：</label>
					<div class="controls">
						<form:input path="integral" htmlEscape="false" maxlength="10" class="input-xlarge "/>
					</div>
				</div>
			</td>
		</tr>

		<tr>
			<td>
				<div class="control-group">
					<label class="control-label">虚拟打分：</label>
					<div class="controls">
						<form:input path="virtualintegral" htmlEscape="false" maxlength="10" class="input-xlarge "/>
					</div>
				</div>
			</td>
			<td>
				<div class="control-group">
					<label class="control-label">子女入学情况：</label>
					<div class="controls">
						<form:input path="situation" htmlEscape="false" maxlength="500" class="input-xlarge "/>
					</div>
				</div>
			</td>
		</tr>

		<tr>
			<td>
				<div class="control-group">
					<label class="control-label">是否在职：</label>
					<div class="controls">
						<form:radiobuttons path="ifquite" items="${fns:getDictList('if_quite')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						<!-- <span class="help-inline"><font color="red">*</font> </span> -->
					</div>
				</div>
			</td>
			<td>
				<div class="control-group">
					<label class="control-label">离职日期：</label>
					<div class="controls">
						<input name="qutietime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${employee.qutietime}" pattern="yyyy-MM-dd"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</div>
				</div>
			</td>
		</tr>

		<tr>
			<td>
				<div class="control-group">
					<label class="control-label">是否高端人才：</label>
					<div class="controls">
						<form:radiobuttons path="ifsenioremp" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						<!-- <span class="help-inline"><font color="red">*</font> </span> -->
					</div>
				</div>
			</td>
			<td>
				<div class="control-group">
					<label class="control-label">奖项荣誉：</label>
					<div class="controls">
						<form:textarea path="reward" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
					</div>
				</div>
			</td>
		</tr>




			<tr>
				<td colspan=2 align="left">
					<div class="form-actions">
			<shiro:hasPermission name="erp:employee:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="审核通过"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
					</td>
			</tr>

	</table>
	</form:form>

</body>
</html>
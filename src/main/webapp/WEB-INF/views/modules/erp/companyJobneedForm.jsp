<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业招聘需求管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			showReason();
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
		
		function showReason(){
			 var checkValue=$("#auditType").val();  //获取Select选择的Value
			 if(checkValue == '2'){
				 $("#auditDIV").show(); 
				 $("#tr").show(); 
				 $("#td").show(); 
			 }else{
				 $("#auditDIV").hide();
				 $("#tr").hide(); 
				 $("#td").hide(); 
			 }

		}
		

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
											 <div style="display: none;">
												 <sys:treeselect id="company" name="company.id" value="${fns:getUser().company.id}" labelName="company.name" labelValue="${fns:getUser().company.name}"
																 title="公司" url="/erp/companyInfo/treeData?type=1"  cssStyle = "width:225px"/>
											 </div>
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
							<label class="control-label">人才分类：</label>
							<div class="controls">
								<form:select path="sort" class="input-xlarge required">
									<form:option value="" label=""/>
									<form:options items="${fns:getDictList('talent_sort')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
								</form:select>
								<span class="help-inline"><font color="red">*</font> </span>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="control-group">
							<label class="control-label">岗位名称：</label>
							<shiro:hasPermission name="erp:companyJobneed:edit"> 
								<div class="controls">
									<form:input path="jobskill" htmlEscape="false" maxlength="200"  class="input-xlarge required" />
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</shiro:hasPermission>
							<shiro:lacksPermission name="erp:companyJobneed:edit"> 
								<div class="controls">
									<form:input path="jobskill" htmlEscape="false" maxlength="200"  class="input-xlarge required" readonly="true"/>
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</shiro:lacksPermission>
						</div>
					</td>
					<td>
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
					</td>
				</tr>
				<tr>
					<td>
						<div class="control-group">
							<label class="control-label">招聘人数：</label>
							<shiro:hasPermission name="erp:companyJobneed:edit"> 
								<div class="controls">
									<form:input path="jobquantity" htmlEscape="false" maxlength="50" class="input-xlarge required" />
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</shiro:hasPermission>
							<shiro:lacksPermission name="erp:companyJobneed:edit"> 
								<div class="controls">
									<form:input path="jobquantity" htmlEscape="false" maxlength="50" class="input-xlarge required" readonly="true"/>
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</shiro:lacksPermission>
						</div>
					</td>
					<td>
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
					</td>
				</tr>
				<tr>
					<td>
						<div class="control-group">
							<label class="control-label">工作年限：</label>
							<shiro:hasPermission name="erp:companyJobneed:edit"> 
								<div class="controls">
									<form:input path="workedyear" htmlEscape="false" maxlength="10" class="input-xlarge  digits required"/>
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</shiro:hasPermission>
							<shiro:lacksPermission name="erp:companyJobneed:edit"> 
								<div class="controls">
									<form:input path="workedyear" htmlEscape="false" maxlength="10" class="input-xlarge  digits required" readonly="true"/>
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</shiro:lacksPermission>
						</div>
					</td>
					<td>
						<div class="control-group">
							<label class="control-label">发布日期：</label>
							<div class="controls">
								<input name="publistime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required "
									value="<fmt:formatDate value="${companyJobneed.publistime}" pattern="yyyy-MM-dd"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="control-group">
							<label class="control-label">最晚到岗日期：</label>
							<div class="controls">
								<input name="jointime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required "
									value="<fmt:formatDate value="${companyJobneed.jointime}" pattern="yyyy-MM-dd"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
							</div>
						</div>
					</td>
					<td>
						<div class="control-group">
							<label class="control-label">专业要求：</label>
							<shiro:hasPermission name="erp:companyJobneed:edit"> 
								<div class="controls">
									<form:input path="majorneed" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</shiro:hasPermission>
							<shiro:lacksPermission name="erp:companyJobneed:edit"> 
								<div class="controls">
									<form:input path="majorneed" htmlEscape="false" maxlength="100" class="input-xlarge required" readonly="true"/>
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</shiro:lacksPermission>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="control-group">
							<label class="control-label">外语要求：</label>
							<shiro:hasPermission name="erp:companyJobneed:edit"> 
								<div class="controls">
									<form:input path="majorneed" htmlEscape="false" maxlength="100" class="input-xlarge required"  value="四级"  />
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</shiro:hasPermission>
							<shiro:lacksPermission name="erp:companyJobneed:edit"> 
								<div class="controls">
									<form:input path="majorneed" htmlEscape="false" maxlength="100" class="input-xlarge required" readonly="true" value="四级"/>
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</shiro:lacksPermission>
						</div>
					</td>
					<td>
						<div class="control-group">
							<label class="control-label">月薪范围：</label>
							<shiro:hasPermission name="erp:companyJobneed:edit"> 
								<div class="controls">
									<form:input path="majorneed" htmlEscape="false" maxlength="100" class="input-xlarge required" value="1000-2000"/>
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</shiro:hasPermission>
							<shiro:lacksPermission name="erp:companyJobneed:edit"> 
								<div class="controls">
									<form:input path="majorneed" htmlEscape="false" maxlength="100" class="input-xlarge required" readonly="true"  value="1000-2000"/>
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</shiro:lacksPermission>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="control-group">
							<label class="control-label">计算机要求：</label>
							<shiro:hasPermission name="erp:companyJobneed:edit"> 
								<div class="controls">
									<form:input path="majorneed" htmlEscape="false" maxlength="100" class="input-xlarge required" value="国家三级"/>
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</shiro:hasPermission>
							<shiro:lacksPermission name="erp:companyJobneed:edit"> 
								<div class="controls">
									<form:input path="majorneed" htmlEscape="false" maxlength="100" class="input-xlarge required" readonly="true" value="国家三级"/>
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</shiro:lacksPermission>
						</div>
					</td>
					<td>
						<div class="control-group">
							<label class="control-label">工作地点：</label>
							<shiro:hasPermission name="erp:companyJobneed:edit"> 
								<div class="controls">
									<form:input path="majorneed" htmlEscape="false" maxlength="100" class="input-xlarge required" value="金山"/>
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</shiro:hasPermission>
							<shiro:lacksPermission name="erp:companyJobneed:edit"> 
								<div class="controls">
									<form:input path="majorneed" htmlEscape="false" maxlength="100" class="input-xlarge required" readonly="true" value="金山"/>
									<span class="help-inline"><font color="red">*</font> </span>
								</div>
							</shiro:lacksPermission>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan=2 align="left">
						<div class="control-group">
							<label class="control-label">职位描述/岗位要求：</label>
							<shiro:hasPermission name="erp:companyJobneed:edit"> 
								<div class="controls"   >
									<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="5000" class="input-xxlarge "  style="width:750px" />
								</div>
							</shiro:hasPermission>
							<shiro:lacksPermission name="erp:companyJobneed:edit"> 
								<div class="controls" >
									<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="5000" class="input-xxlarge " readonly="true"    style="width:750px"/>
								</div>
							</shiro:lacksPermission>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan=2 align="left">
						<div class="control-group">
							<label class="control-label">福利待遇：</label>
							<shiro:hasPermission name="erp:companyJobneed:edit"> 
								<div class="controls"   >
									<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="5000" class="input-xxlarge "  style="width:750px" />
								</div>
							</shiro:hasPermission>
							<shiro:lacksPermission name="erp:companyJobneed:edit"> 
								<div class="controls" >
									<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="5000" class="input-xxlarge " readonly="true"    style="width:750px"/>
								</div>
							</shiro:lacksPermission>
						</div>
					</td>
				</tr>
				<shiro:hasPermission name="erp:companyJobneed:audit">
				<tr>
					<td colspan=2 align="left">
						
							<div class="control-group">
								<label class="control-label">审核类型：</label>
								<div class="controls">
									<form:select path="auditType" class="input-xlarge " onchange="showReason();">
										<form:option value="1" label="审核通过"/>
										<form:option value="2" label="已拒绝"/>
										
										<span class="help-inline"><font color="red">*</font> </span>
									</form:select>
								</div>
							</div>
							
							
						
					</td>
				</tr>
				
				<tr id="tr" style="display:none">
					<td id="td" colspan=2 align="left" style="display:none">
						<div id="auditDIV" class="control-group" style="display:none">
							<label class="control-label">拒绝理由：</label>
							<div class="controls">
								<form:textarea path="auditReason" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
								<span class="help-inline"><font color="red">*</font> </span>
							</div>
						</div>
					</td>
				<tr>
				
				</shiro:hasPermission>
			</table>
			
		
	 
		<div class="form-actions">
			<shiro:hasPermission name="erp:companyJobneed:edit">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<shiro:hasPermission name="erp:companyJobneed:audit">
					<c:if test="${auditModle!=null && auditModle=='true'}"> 
	    				<input id="btnSubmit" class="btn btn-primary" type="submit" value="审 阅"/>&nbsp;
	  				</c:if>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
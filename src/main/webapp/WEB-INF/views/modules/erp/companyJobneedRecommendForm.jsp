<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>推荐人才信息管理</title>
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
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/erp/companyJobneedRecommend/">推荐人才信息列表</a></li>
		<li class="active"><a href="${ctx}/erp/companyJobneedRecommend/form?id=${companyJobneed.id}">推荐人才信息<shiro:hasPermission name="erp:companyJobneedRecommend:edit">${not empty companyJobneed.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="erp:companyJobneedRecommend:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="companyJobneed" action="${ctx}/erp/companyJobneedRecommend/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">所属企业：</label>
			<div class="controls">
				<form:input path="company.name" htmlEscape="false" maxlength="64" class="input-xlarge required" readonly= "true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人才分类：</label>
			<div class="controls">
				<form:select path="sort" class="input-xlarge required"   disabled= "true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('talent_sort')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">岗位要求：</label>
			<div class="controls">
				<form:input path="jobskill" htmlEscape="false" maxlength="200" class="input-xlarge required"  readonly= "true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select path="sex" class="input-xlarge required"  disabled= "true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人才数量：</label>
			<div class="controls">
				<form:input path="jobquantity" readonly= "true" htmlEscape="false" maxlength="50" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历要求：</label>
			<div class="controls">
				<form:select path="educationneed" class="input-xlarge required" readonly= "true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('education_need')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作年限：</label>
			<div class="controls">
				<form:input path="workedyear" htmlEscape="false" maxlength="10" class="input-xlarge required digits" readonly= "true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布时间：</label>
			<div class="controls">
				<input name="publistime" type="text"   disabled= "true" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${companyJobneed.publistime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最晚到岗时间：</label>
			<div class="controls">
				<input name="jointime" type="text"  disabled= "true" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${companyJobneed.jointime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业要求：</label>
			<div class="controls">
				<form:input path="majorneed" htmlEscape="false" maxlength="100" class="input-xlarge required" readonly= "true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge required" readonly= "true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">三方企业推荐人才表：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>三方人才服务企业</th>
								<th>姓名</th>
								<th>性别</th>
								<th>学历</th>
								<th>身份证号</th>
								<th>联系电话</th>
								<th>附件路径</th>
								<th>发布时间</th>
								<th>备注</th>
								<shiro:hasPermission name="erp:companyJobneedRecommend:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="companyJobneedRecommendList">
						</tbody>
						<shiro:hasPermission name="erp:companyJobneedRecommend:edit"><tfoot>
							<tr><td colspan="11"><a href="javascript:" onclick="addRow('#companyJobneedRecommendList', companyJobneedRecommendRowIdx, companyJobneedRecommendTpl);companyJobneedRecommendRowIdx = companyJobneedRecommendRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="companyJobneedRecommendTpl">//<!--
						<tr id="companyJobneedRecommendList{{idx}}">
							<td class="hide">
								<input id="companyJobneedRecommendList{{idx}}_id" name="companyJobneedRecommendList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="companyJobneedRecommendList{{idx}}_delFlag" name="companyJobneedRecommendList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="companyJobneedRecommendList{{idx}}_servercompany" name="companyJobneedRecommendList[{{idx}}].servercompany" type="text" value="{{row.servercompany}}51Job" maxlength="64" class="input-small "/>
							</td>
							<td>
								<input id="companyJobneedRecommendList{{idx}}_name" name="companyJobneedRecommendList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="200" class="input-small "/>
							</td>
							<td>
								<select id="companyJobneedRecommendList{{idx}}_sex" name="companyJobneedRecommendList[{{idx}}].sex" data-value="{{row.sex}}" class="input-small required">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('sex')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select id="companyJobneedRecommendList{{idx}}_education" name="companyJobneedRecommendList[{{idx}}].education" data-value="{{row.education}}" class="input-small required">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('education_need')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="companyJobneedRecommendList{{idx}}_idcard" name="companyJobneedRecommendList[{{idx}}].idcard" type="text" value="{{row.idcard}}" maxlength="50" class="input-small required"/>
							</td>
							<td>
								<input id="companyJobneedRecommendList{{idx}}_telephone" name="companyJobneedRecommendList[{{idx}}].telephone" type="text" value="{{row.telephone}}" maxlength="50" class="input-small required"/>
							</td>
							<td>
								<input id="companyJobneedRecommendList{{idx}}_attactpath" name="companyJobneedRecommendList[{{idx}}].attactpath" type="text" value="{{row.attactpath}}" maxlength="200" class="input-small "/>
							</td>
							<td>
								<input id="companyJobneedRecommendList{{idx}}_publistime" name="companyJobneedRecommendList[{{idx}}].publistime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
									value="{{row.publistime}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</td>
							<td>
								<textarea id="companyJobneedRecommendList{{idx}}_remarks" name="companyJobneedRecommendList[{{idx}}].remarks" rows="4" maxlength="500" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="erp:companyJobneed:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#companyJobneedRecommendList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr> 
					</script>
					<script type="text/javascript">
						var companyJobneedRecommendRowIdx = 0, companyJobneedRecommendTpl = $("#companyJobneedRecommendTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(companyJobneed.companyJobneedRecommendList)};
							for (var i=0; i<data.length; i++){
								addRow('#companyJobneedRecommendList', companyJobneedRecommendRowIdx, companyJobneedRecommendTpl, data[i]);
								companyJobneedRecommendRowIdx = companyJobneedRecommendRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="erp:companyJobneedRecommend:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
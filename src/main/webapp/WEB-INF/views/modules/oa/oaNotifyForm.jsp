<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通知管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
			
			/* 查看平台回复 */
			$("#jsQueryRecordBack").click(function RecordBack()
			{
				var msgback="";			
				
				if($("#backMsg").val()=="" || $("#backMsg").val()==null)
				{
					msgback="无平台回复信息";
				}
				else
				{
					msgback=$("#backMsg").val();
				}
				var html4 = "<div class='msg'>" +
	            "<div align='center'><span style='color:gray;padding-right:300px;'>"+$("#remarksMsg").val()+"</span></div><div class='xian' style='width:1000px;margin:0 auto;padding:0 200px; border-top:1px solid #ddd'></div><div align='center'><span id='backMsgSpan' style='padding-right:345px'>"+msgback+"</span></div>" +
	            "</div>";                
				 $.jBox.open(html4, "平台回复", 500, 300, { showType: "show" , buttons: { "确定": true, "取消": false }});
			});
			
			/* 企业hr回复 */
			$("#jsRecordBackEdit").click(function (){
				var index=$("#jsRecordBackEdit").attr("value").toString();
				var recordId=$("#recordId"+index).val();
				var useId=$("#useId"+index).val();
				var html2 = "<div class='msg'>" +
	            "<p><span style='color:gray;align:center'>（选填，没有需求，就不需要回复！）</span></p><div align='center'><textarea id='messageIn' name='messageIn' rows='6' maxlength='2000' class='input-xxlarge required'></textarea></div>" +
	            "</div>";
	            
				 $.jBox.open(html2, "回复", 500, 300, { showType: "show" , buttons: { "保存": true, "取消": false },
					 submit:function (v, h, f) {
				            if (v == true) {
								if($("#messageIn").val()=="" || $("#messageIn").val()==null)
								{
									$.jBox.alert("请输入回复信息","提示");
									return;
								}
				            	$.ajax({
									url: "${ctx}/oa/oaNotifyRecordBack/saveRecordBack?nr_id="+recordId+"&messageIn="+encodeURIComponent(encodeURIComponent($("#messageIn").val()))+"&useId="+useId,
									type: 'GET',
									dataType: 'json',
									cache: false,
									async: true,
									success:function (data) {
										if(data.result=="ok")
										{
											$.jBox.alert(data.msg,"提示");
											$("#jsRecordBackEdit").hide();
											
										}else if(data.result=="false")
										{
											$.jBox.alert(data.msg,"提示");
										}
					                },
					                error: function(jqXHR, textStatus, errorThrown) {
					                	$.jBox.alert("通知回复失败","提示");
					                }
								});
				            }
				  }
				 });
			});
			
			/* 平台回复 */
			$("#jsRecordBackEditFrom").click(function (){
				var index=$("#jsRecordBackEditFrom").attr("value").toString();
				var recordId=$("#recordId"+index).val();
				var useId=$("#useId"+index).val();			
	            var msg="";
	            $.ajax({
					url: "${ctx}/oa/oaNotifyRecordBack/getRecordBack?nr_id="+recordId+"&useId="+useId,
					type: 'GET',
					dataType: 'json',
					cache: false,
					async: false,
					success:function (data) {				
							msg=data.msg;					
	                },
	                error: function(jqXHR, textStatus, errorThrown) {
	                	msg="获取企业回复信息失败，请重新打开页面！";
	                }
				});
	            
	            var html3 = "<div class='msg'>" +
	            "<p><span style='color:gray;align:center'>企业反馈：</span></p><div align='center'><span id='HRMessage' style='padding-right:300px'>"+msg+"</span><textarea id='messageInback' name='messageInback' rows='6' maxlength='2000' class='input-xxlarge required'></textarea></div>" +
	            "</div>";
				 $.jBox.open(html3, "回复", 500, 300, { showType: "show" , buttons: { "保存": true, "取消": false },
					 submit:function (v, h, f) {
				            if (v == true) {
				            	if($("#messageInback").val()=="" || $("#messageInback").val()==null)
								{
									$.jBox.alert("请输入回复信息","提示");
									return;
								}
				            	$.ajax({
									url: "${ctx}/oa/oaNotifyRecordBack/updateRecordBack?nr_id="+recordId+"&messageInback="+encodeURIComponent(encodeURIComponent($("#messageInback").val()))+"&useId="+useId,
									type: 'GET',
									dataType: 'json',
									cache: false,
									async: true,
									success:function (data) {
										if(data.result=="ok")
										{
											$.jBox.alert(data.msg,"提示");
											$("#jsRecordBackEditFrom").hide();
											
										}else if(data.result=="false")
										{
											$.jBox.alert(data.msg,"提示");
										}
					                },
					                error: function(jqXHR, textStatus, errorThrown) {
					                	$.jBox.alert("通知回复失败","提示");
					                }
								});
				            }
				  }
				 }
				 );
			});
		});		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/oaNotify/">通知列表</a></li>
		<li class="active"><a href="${ctx}/oa/oaNotify/form?id=${oaNotify.id}">通知<shiro:hasPermission name="oa:oaNotify:edit">${oaNotify.status eq '1' ? '查看' : not empty oaNotify.id ? '修改' : '添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaNotify:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaNotify" action="${ctx}/oa/oaNotify/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<%-- <div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('oa_notify_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>	 --%>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="6" maxlength="2000" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:if test="${oaNotify.status ne '1'}">
			<div class="control-group">
				<label class="control-label">附件：</label>
				<div class="controls">
					<form:hidden id="files" path="files" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<sys:ckfinder input="files" type="files" uploadPath="/oa/notify" selectMultiple="true"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">状态：</label>
				<div class="controls">
					<form:radiobuttons path="status" items="${fns:getDictList('oa_notify_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					<span class="help-inline"><font color="red">*</font> 发布后不能进行操作。</span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">接受人：</label>
				<div class="controls">
	                <sys:treeselect id="oaNotifyRecord" name="oaNotifyRecordIds" value="${oaNotify.oaNotifyRecordIds}" labelName="oaNotifyRecordNames" labelValue="${oaNotify.oaNotifyRecordNames}"
						title="用户" url="/sys/office/treeDataHr?type=3" cssClass="input-xxlarge required" notAllowSelectParent="true" checked="true"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<c:if test="${oaNotify.status eq '1'}">
			<div class="control-group">
				<label class="control-label">附件：</label>
				<div class="controls">
					<form:hidden id="files" path="files" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<sys:ckfinder input="files" type="files" uploadPath="/oa/notify" selectMultiple="true" readonly="true" />
				</div>
			</div>
			<shiro:hasPermission name="oa:oaNotify:edit">
			<div class="control-group">
				<label class="control-label">接受人：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>接受人</th>
								<th>接受企业(部门)</th>
								<th>阅读状态</th>
								<th>阅读时间</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${oaNotify.oaNotifyRecordList}" var="oaNotifyRecord" varStatus="status">
							<tr>
								<td>
								   <input type="hidden" id="recordId${ status.index + 1}" value="${oaNotifyRecord.id}"/>
								   <input type="hidden" id="useId${ status.index + 1}" value="${oaNotifyRecord.user.id}"/>
									${oaNotifyRecord.user.name}(${oaNotifyRecord.user.phone})
								</td>
								<td>
									${oaNotifyRecord.user.office.name}
								</td>
								<td>
									${fns:getDictLabel(oaNotifyRecord.readFlag, 'oa_notify_read', '')}									
									<c:if test="${oaNotifyRecord.readFlag ne '0'}">
										<!-- 企业回复 -->
										<c:if test="${oaNotifyRecord.user.id eq fns:getUser().id}">	
										   <c:if test="${fns:getNotifyRecordBack(oaNotifyRecord.id).remarks eq null}">							   
												<!-- <a id="jsRecordBackEdit" href="#" value=${oaNotifyRecord.id}>（回复）</a> -->
												<a id="jsRecordBackEdit" href="javascript:void(0)" value=${ status.index + 1}>（回复）</a>
										   </c:if> 
										   <c:if test="${fns:getNotifyRecordBack(oaNotifyRecord.id).back ne null}">							   
												<a id="jsQueryRecordBack" href="javascript:void(0)">（查看平台回复）</a>
										   </c:if> 
										</c:if>
										<!-- 平台回复 -->
										<c:if test="${fns:getNotifyRecordBack(oaNotifyRecord.id).back eq null}">		
										   	<c:if test="${fns:getNotifyRecordFrom(oaNotifyRecord.oaNotify.id).createBy eq fns:getUser().id}">							   
												<a id="jsRecordBackEditFrom" href="javascript:void(0)" value=${ status.index + 1}>（回复1）</a>
										   	</c:if> 
										</c:if>
										<input type="hidden" id="backMsg" value="${fns:getNotifyRecordBack(oaNotifyRecord.id).back}"/>
										<input type="hidden" id="remarksMsg" value="${fns:getNotifyRecordBack(oaNotifyRecord.id).remarks}"/>
									</c:if>								
								</td>
								<td>
									<fmt:formatDate value="${oaNotifyRecord.readDate}" pattern="yyyy-MM-dd"/>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					已查阅：${oaNotify.readNum} &nbsp; 未查阅：${oaNotify.unReadNum} &nbsp; 总共：${oaNotify.readNum + oaNotify.unReadNum}
				</div>
			</div>
			</shiro:hasPermission>
		</c:if>
		<div class="form-actions">
			<c:if test="${oaNotify.status ne '1'}">
				<shiro:hasPermission name="oa:oaNotify:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			<!-- <input id="btnRecordback" class="btn" type="button" value="回复" onclick="history.go(-1)"/> -->
		</div>
	</form:form>
</body>
</html>
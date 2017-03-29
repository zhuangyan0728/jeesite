<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人才申请住房信息列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出人员信息数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/erp/employee/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});



		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function printRoomApply(eid){ 
			//alert("sss");
			var openUrl = "http://222.69.93.84:8082/WebReport/ReportServer?reportlet=talentTenementApply.cpt&eid=" + eid;//弹出窗口的url
			var iWidth=1000; //弹出窗口的宽度;
			var iHeight=600; //弹出窗口的高度;
			var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
			var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
			window.open(openUrl,"_blank","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft); 
			 
		}

		function test(uid){

			var userid=uid;
			var html2 =
					"<div class='msg'>" +
					"<p><span style='color:gray;align:center'>请填写拒绝意见</span></p>" +
					"<div align='center'>" +
					"<form id='upform' method='post'> <textarea id='messageIn' name='messageIn' rows='6' maxlength='2000' class='input-xxlarge required'></textarea></form>"  +
					"</div>" +
					"</div>";

			$.jBox.open(html2, "拒绝", 500, 300, { showType: "show" , buttons: { "拒绝": true, "取消": false },
				submit:function (v, h, f) {
					if (v == true) {
						if($("#messageIn").val()=="" || $("#messageIn").val()==null)
						{
							$.jBox.alert("请输入拒绝意见","提示");
							return;
						}
						var formData = new FormData($( "#upform" )[0]);
						$.ajax({
							url: "${ctx}/erp/employee/ajustRentApply?id="+userid+"&messageIn="+encodeURIComponent(encodeURIComponent($("#messageIn").val())),
							type: 'POST',
							data: formData,
							async: false,
							cache: false,
							contentType: false,
							processData: false,
							async: true,
							success:function (data) {

								if(data.result=="ok")
								{
									$.jBox.alert(data.msg,"提示");
									//$("#jsRecordBackEdit").hide();


								}else if(data.result=="false")
								{
									$.jBox.alert(data.msg,"提示");

								}
								location.reload();
							},
							error: function(jqXHR, textStatus, errorThrown) {
								$.jBox.alert("通知失败","提示");
							}
						});
					}
				}
			});
		};
 			
	</script>
</head>
<body>
    <div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/erp/employee/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/erp/employee/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/erp/employee/">人才申请住房信息列表</a></li>
		<%--<shiro:hasPermission name="erp:employee:edit"><li><a href="${ctx}/erp/employee/form">企业人才信息添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="employee" action="${ctx}/erp/employee/rentlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form" style="overflow:auto;">
			<li><label>公司名称：</label>
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
							 <label >${employee.company.name}</label>  
						 </c:if>
						 <c:if test="${empty employee.company.id}">						   
							 ${fns:getUser().company.name}
						 </c:if>						 
					</c:otherwise>
				</c:choose>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<!--  <li><label>身份证号：</label>
				<form:input path="identityno" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>-->	
			<li><label>学历：</label>
				<form:select path="education" class="input-medium">
					<form:option value="" label="    "/>
					<form:options items="${fns:getDictList('education')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>奖项荣誉：</label>
				<form:input path="reward" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>技术职称：</label>
				<form:input path="worktitle" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			
			<li><label>性别：</label>
				<form:checkboxes path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				<!--  <form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>-->
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			<%--<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>--%>
			<%--<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>--%>
			</li>
			
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<%!  int lineNum=1;%> 
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="2%">序号</th>
				<th>公司名称</th>
				<th>姓名</th>
				<th>性别</th>
				<th>学历</th>
				<th>技术职称</th>
				<th>奖项荣誉</th>
				<!--  
				<th>身份证号</th>
				<th>籍贯</th>
				<th>居住地</th>
				<th>婚姻状况</th>
				
				<th>备注</th>
				-->
				<th>修改时间</th>
				<th>审核状态</th>
				<th>打印</th>
				<th>住房申请</th>

			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="employee">
			<tr>
				<td><%=lineNum%><%lineNum++;%></td>
				<td>
					${employee.company.name}
				</td>
				<td><a href="${ctx}/erp/employee/applyform?id=${employee.id}">
					${employee.name}
				</a></td>
				
				<td>
					${fns:getDictLabel(employee.sex, 'sex', '')}
				</td>
				<td>
					${fns:getDictLabel(employee.education, 'education', '')}
				</td>
				<td>
					${employee.worktitle}
				</td>
				<td>
					${employee.reward}
				</td>
				<!--  
				<td>
					${employee.identityno}
				</td>
				<td>
					${employee.nativeplace}
				</td>
				<td>
					${fns:getDictLabel(employee.residenceplace, 'residence_place', '')}
				</td>
				<td>
					${fns:getDictLabel(employee.martitalstatus, 'marital_status', '')}
				</td>
				
				<td>
					${employee.remarks}
				</td>
				-->
				<td>
					<fmt:formatDate value="${employee.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<c:if test="${employee.ifRentApplyAudit =='0'}">
						未审核
					</c:if>
					<c:if test="${employee.ifRentApplyAudit =='1'}">
						审核通过
					</c:if>
					<c:if test="${employee.ifRentApplyAudit =='2'}">
						拒绝申请<c:if test="${not empty employee.reason2}">
					<label>（原因：${employee.reason2}）</label></c:if>
					</c:if>

				</td>
				<td><a href="javascript:void(0);" onclick ="printRoomApply('${employee.id}')">租房申请打印</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<shiro:hasPermission name="erp:employee:edit">
					<td>
						<a href="${ctx}/erp/employee/cancelRentApply?id=${employee.id}" onclick="return confirmx('确认要发起申请？', this.href)">撤销租房申请</a>
					</td>
				</shiro:hasPermission>
				<shiro:lacksPermission name="erp:employee:edit">
					<td>
						<a href="${ctx}/erp/employee/passRentApply?id=${employee.id}" onclick="return confirmx('确认要通过改申请信息吗？', this.href)">
							通过申请&nbsp;</a>
						<%--<a href="${ctx}/erp/employee/ajustRentApply?id=${employee.id}" onclick="return confirmx('确认要拒绝该申请吗？', this.href)">--%>
							<%--拒绝申请</a>--%>
						<a id="jsRecordBackEdit" href="javascript:void(0)" value="${employee.id}" onclick="test('${employee.id}')">拒绝申请</a>
					</td>

				</shiro:lacksPermission>
				<%--<shiro:hasPermission name="erp:employee:edit"><td>--%>
    				<%--<a href="${ctx}/erp/employee/form?id=${employee.id}">审核通过</a>--%>
					<%--<a href="${ctx}/erp/employee/delete?id=${employee.id}" onclick="return confirmx('确认要删除该企业人才信息吗？', this.href)">不通过</a>--%>
				<%--</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
		<%lineNum=1;%>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
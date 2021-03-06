<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业人才信息管理</title>
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
			var openUrl = "http://222.69.93.84:8082/WebReport/ReportServer?reportlet=talentRoomApply.cpt&eid=" + eid;//弹出窗口的url
			var iWidth=1000; //弹出窗口的宽度;
			var iHeight=600; //弹出窗口的高度;
			var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
			var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
			window.open(openUrl,"_blank","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft); 
			 
		}

        function printRentRoomApply(eid){
            //alert("sss");
            var openUrl = "http://222.69.93.84:8082/WebReport/ReportServer?reportlet=talentTenementApply.cpt&eid=" + eid;//弹出窗口的url
            var iWidth=1000; //弹出窗口的宽度;
            var iHeight=600; //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
            window.open(openUrl,"_blank","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);

        }

        function printBuyRoomApply(eid){
            //alert("sss");
            var openUrl = "http://222.69.93.84:8082/WebReport/ReportServer?reportlet=talentBuyRoomApply.cpt&eid=" + eid;//弹出窗口的url
            var iWidth=1000; //弹出窗口的宽度;
            var iHeight=600; //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
            window.open(openUrl,"_blank","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);

        }

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
		<li class="active"><a href="${ctx}/erp/employee/">企业人才信息列表</a></li>
		<shiro:hasPermission name="erp:employee:edit"><li><a href="${ctx}/erp/employee/form">企业人才信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="employee" action="${ctx}/erp/employee/" method="post" class="breadcrumb form-search">
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
			<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
			<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
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
				<th>查看</th>
				<th align=center>租房申请</th>
                <th align=center>打印</th>
				<shiro:hasPermission name="erp:employee:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="employee">
			<tr>
				<td><%=lineNum%><%lineNum++;%></td>
				<td>
					${employee.company.name}
				</td>
				<td><a href="${ctx}/erp/employee/form?id=${employee.id}">
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
				<td><a href="${ctx}/erp/employee/form?id=${employee.id}">
					查看
				</a></td>
				<td align="center">
					<shiro:hasPermission name="erp:employee:edit">
						<%--<c:if test="${employee.ifApply =='0'}">--%>

						<%--</c:if>--%>
                        <a href="${ctx}/erp/employee/roomApply?id=${employee.id}" onclick="return confirmx('确认要发起申请？', this.href)">人才公寓租住申请</a>
						<%--<c:if test="${employee.ifApply =='1'}">--%>
							<%--<a href="${ctx}/erp/employee/cancelApply?id=${employee.id}" onclick="return confirmx('确认要发起申请？', this.href)">撤销租房申请</a>--%>
						<%--</c:if>--%>
                        <a href="${ctx}/erp/employee/buyApply?id=${employee.id}" onclick="return confirmx('确认要发起申请？', this.href)">购房申请</a>
                        <a href="${ctx}/erp/employee/rentApply?id=${employee.id}" onclick="return confirmx('确认要发起申请？', this.href)">租房申请</a>

					</shiro:hasPermission>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;


					<%--<c:if test="${employee.ifApply =='1'}">--%>
						<%--审核状态：--%>
						<%--<c:if test="${employee.ifApplyAudit =='0'}">--%>
							<%--未审核--%>
						<%--</c:if>--%>
						<%--<c:if test="${employee.ifApplyAudit =='1'}">--%>
							<%--审核通过--%>
						<%--</c:if>--%>
						<%--<c:if test="${employee.ifApplyAudit =='2'}">--%>
							<%--拒绝申请--%>
						<%--</c:if>--%>
					<%--</c:if>--%>
				</td>
                <td align="center">
                    <a href="javascript:void(0);" onclick ="printRoomApply('${employee.id}')">公寓申请打印</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="javascript:void(0);" onclick ="printBuyRoomApply('${employee.id}')">购房申请打印</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="javascript:void(0);" onclick ="printRentRoomApply('${employee.id}')">租房申请打印</a>&nbsp;&nbsp;&nbsp;&nbsp;
                </td>
				<shiro:hasPermission name="erp:employee:edit"><td>
    				<a href="${ctx}/erp/employee/form?id=${employee.id}">修改</a>
					<a href="${ctx}/erp/employee/delete?id=${employee.id}" onclick="return confirmx('确认要删除该企业人才信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
		<%lineNum=1;%>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业招聘需求管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出招聘信息吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/erp/companyJobneed/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function audit(){
			$.jBox.alert("招聘信息审核通过","提示");
		}
		function viewDetial(cid){ 
			//alert("sss");
			var openUrl = "http://222.69.93.84:8082/WebReport/ReportServer?reportlet=employeeDetails.cpt&cid=" + cid;//弹出窗口的url
			var iWidth=1000; //弹出窗口的宽度;
			var iHeight=600; //弹出窗口的高度;
			var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
			var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
			window.open(openUrl,"_blank","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft); 
			 
		} 
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/erp/companyJobneed/">企业招聘需求列表</a></li>
		<shiro:hasPermission name="erp:companyJobneed:edit"><li><a href="${ctx}/erp/companyJobneed/form">企业招聘需求添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="companyJobneed" action="${ctx}/erp/companyJobneed/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>所属公司：</label>
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
							<label>${fns:getUser().company.name}</label>
						 </c:if>						 
					</c:otherwise>
				</c:choose>
			</li> 
			<li><label>人才分类：</label>
				<form:select path="sort" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('talent_sort')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>学历要求：</label>
				<form:select path="educationneed" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('education_need')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>工作年限：</label>
				<form:input path="workedyear" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>发布时间：</label>
				<input name="publistime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${companyJobneed.publistime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
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
				<th>企业名称</th>
				<th>招聘类别</th>
				<th>性别</th>
				<th>年龄要求</th>
				<th>学历要求</th>
				<th>招聘数量</th> 
				
				<th width="20%">有效期起止</th>
				 
			   <th>发布时间</th>
			   <th>审核状态</th> 
			    <th>审核时间</th>
			   <th>查看</th>
			   <shiro:hasPermission name="erp:companyJobneed:audit"><th>操作</th></shiro:hasPermission>
			   <shiro:hasPermission name="erp:companyJobneed:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="companyJobneed">
			<tr>
				<td><%=lineNum%><%lineNum++;%></td>
				<td><a href="${ctx}/erp/companyJobneed/form?id=${companyJobneed.id}">
					${companyJobneed.company.name}
				</a></td>
				<td>
					${fns:getDictLabel(companyJobneed.sort, 'talent_sort', '')}
				</td>
				<td>
					${fns:getDictLabel(companyJobneed.sex, 'sex', '')}
				</td>
				<td>${companyJobneed.jobskill} </td>
				<td>
					${fns:getDictLabel(companyJobneed.educationneed, 'education_need', '')}
				</td>
				
				<td>
					${companyJobneed.jobquantity}
				</td>
				
				
				<td>
					<fmt:formatDate value="${companyJobneed.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>     到<fmt:formatDate value="${companyJobneed.jointime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			 	
				<td>
					<fmt:formatDate value="${companyJobneed.publistime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:if test="${companyJobneed.auditType=='0'}"> 
	    				未审核
	  				</c:if>
	  				<c:if test="${companyJobneed.auditType=='1'}"> 
	    				审核通过
	  				</c:if>
	  				<c:if test="${companyJobneed.auditType=='2'}"> 
	    				已拒绝
	  				</c:if>
				</td>
				<td>
					<c:if test="${companyJobneed.auditTime != null && companyJobneed.auditTime !=''}"> 
	    				<fmt:formatDate value="${companyJobneed.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	  				</c:if>
				</td>
				<td> <a href="javascript:void(0);" onclick ="viewDetial('${companyJobneed.id}')">
					查看
				</a></td>
				<shiro:hasPermission name="erp:companyJobneed:audit">
					<td><a href="${ctx}/erp/companyJobneed/form?&auditModle=true&id=${companyJobneed.id}">审核</a></shiro:hasPermission></td>
				<shiro:hasPermission name="erp:companyJobneed:edit"><td>
    				<a href="${ctx}/erp/companyJobneed/form?id=${companyJobneed.id}">修改</a>
					<a href="${ctx}/erp/companyJobneed/delete?id=${companyJobneed.id}" onclick="return confirmx('确认要删除该企业招聘需求吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
		<%lineNum=1;%>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
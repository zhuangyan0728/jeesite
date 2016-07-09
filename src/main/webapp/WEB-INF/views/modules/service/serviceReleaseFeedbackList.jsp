<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业回馈服务项目管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/service/serviceReleaseFeedback/">企业回馈服务项目列表</a></li>
		<shiro:hasPermission name="service:serviceReleaseFeedback:edit"><li><a href="${ctx}/service/serviceReleaseFeedback/form">企业回馈服务项目添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="serviceReleaseFeedback" action="${ctx}/service/serviceReleaseFeedback/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>服务名称：</label>
				<%-- <form:input path="reName" htmlEscape="false" maxlength="254" class="input-medium"/> --%>
				<sys:treeselect id="serviceRelease" name="serviceRelease.id" value="${serviceReleaseFeedback.serviceRelease.id}" labelName="serviceRelease.name" labelValue="${serviceReleaseFeedback.serviceRelease.name}" 
				title="公司" url="/service/serviceRelease/treeData?type=1" cssClass="input-small" allowClear="true"/>
			</li>
			<%-- <li><label>反馈公司：</label>
				<form:input path="company" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li> --%>			
			<li><label class="control-label">归属公司:</label></li>
			<li>
                <sys:treeselect id="company" name="company.id" value="${serviceReleaseFeedback.company.id}" labelName="company.name" labelValue="${serviceReleaseFeedback.company.name}" 
				title="公司" url="/erp/companyInfo/treeData?type=1" cssClass="input-small" allowClear="true"/>
			</li>
			<li><label>发布时间：</label>
				<input name="beginUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${serviceReleaseFeedback.beginUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${serviceReleaseFeedback.endUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>服务名称</th>
				<th>反馈公司</th>
				<th>备注</th>
				<th>发布时间</th>
				<shiro:hasPermission name="service:serviceReleaseFeedback:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="serviceReleaseFeedback">
			<tr>
				<td><a href="${ctx}/service/serviceReleaseFeedback/form?id=${serviceReleaseFeedback.id}">
					${serviceReleaseFeedback.serviceRelease.name}
				</a></td>
				<td>
					${serviceReleaseFeedback.company.name}
				</td>
				<td>
					${serviceReleaseFeedback.remarks}
				</td>
				<td>
					<fmt:formatDate value="${serviceReleaseFeedback.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="service:serviceReleaseFeedback:edit"><td>
    				<a href="${ctx}/service/serviceReleaseFeedback/form?id=${serviceReleaseFeedback.id}">修改</a>
					<a href="${ctx}/service/serviceReleaseFeedback/delete?id=${serviceReleaseFeedback.id}" onclick="return confirmx('确认要删除该企业回馈服务项目吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
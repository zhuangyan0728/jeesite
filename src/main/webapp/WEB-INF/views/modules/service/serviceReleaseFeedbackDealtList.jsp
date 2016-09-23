<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业服务处理管理</title>
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
		<li class="active"><a href="${ctx}/service/serviceReleaseFeedbackDealt/">企业服务处理列表</a></li>
		<shiro:hasPermission name="service:serviceReleaseFeedbackDealt:edit"><li><a href="${ctx}/service/serviceReleaseFeedbackDealt/form">企业服务处理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="serviceReleaseFeedbackDealt" action="${ctx}/service/serviceReleaseFeedbackDealt/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>服务企业：</label>
				<sys:treeselect id="company" name="company.id" value="${serviceReleaseFeedbackDealt.company.id}" labelName="company.name" labelValue="${serviceReleaseFeedbackDealt.company.name}" 
				title="公司" url="/erp/companyInfo/treeData?type=1" cssClass="input-small" allowClear="true"/>
			</li>
			<li><label>服务内容：</label>
				<form:input path="content" htmlEscape="false" maxlength="2000" class="input-medium"/>
			</li>
			<li><label>完成情况：</label>
				<form:input path="shcedule" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>服务时间：</label>
				<input name="beginServiceDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${serviceReleaseFeedbackDealt.beginServiceDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endServiceDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${serviceReleaseFeedbackDealt.endServiceDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
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
				<th>处理进度</th>
				<th>处理时间</th>
				<shiro:hasPermission name="service:serviceReleaseFeedbackDealt:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="serviceReleaseFeedbackDealt">
			<tr>
				<td><a href="${ctx}/service/serviceReleaseFeedbackDealt/form?id=${serviceReleaseFeedbackDealt.id}">
					${serviceReleaseFeedbackDealt.serviceRelease.name}
				</a></td>
				<td>
					${serviceReleaseFeedbackDealt.company.name}
				</td>
				<td>
					${serviceReleaseFeedbackDealt.shcedule}
				</td>
				<td>
					<fmt:formatDate value="${serviceReleaseFeedbackDealt.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="service:serviceReleaseFeedbackDealt:edit"><td>
    				<a href="${ctx}/service/serviceReleaseFeedbackDealt/form?id=${serviceReleaseFeedbackDealt.id}">修改</a>
					<a href="${ctx}/service/serviceReleaseFeedbackDealt/delete?id=${serviceReleaseFeedbackDealt.id}" onclick="return confirmx('确认要删除该企业服务处理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>发布服务管理</title>
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
		<li class="active"><a href="${ctx}/service/serviceRelease/">发布服务列表</a></li>
		<shiro:hasPermission name="service:serviceRelease:edit"><li><a href="${ctx}/service/serviceRelease/form">发布服务添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="serviceRelease" action="${ctx}/service/serviceRelease/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="name" htmlEscape="false" maxlength="254" class="input-medium"/>
			</li>
			<li><label>备注：</label>
				<form:input path="remarks" htmlEscape="false" maxlength="1500" class="input-medium"/>
			</li>
			<li><label>更新时间：</label>
				<input name="beginUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${serviceRelease.beginUpdateDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${serviceRelease.endUpdateDate}" pattern="yyyy-MM-dd"/>"
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
				<th>标题</th>
				<th>备注</th>
				<th>更新时间</th>
				<shiro:hasPermission name="service:serviceRelease:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="serviceRelease">
			<tr>
				<td><a href="${ctx}/service/serviceRelease/form?id=${serviceRelease.id}">
					${serviceRelease.name}
				</a></td>
				<td>
					${serviceRelease.remarks}
				</td>
				<td>
					<fmt:formatDate value="${serviceRelease.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="service:serviceRelease:edit"><td>
    				<a href="${ctx}/service/serviceRelease/form?id=${serviceRelease.id}">修改</a>
					<a href="${ctx}/service/serviceRelease/delete?id=${serviceRelease.id}" onclick="return confirmx('确认要删除该发布服务吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
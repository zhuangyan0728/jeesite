<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业人才信息管理</title>
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
		<li class="active"><a href="${ctx}/erp/employee/">企业人才信息列表</a></li>
		<shiro:hasPermission name="erp:employee:edit"><li><a href="${ctx}/erp/employee/form">企业人才信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="employee" action="${ctx}/erp/employee/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>所属公司：</label>
				<form:input path="companyid" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>性别：</label>
				<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="identityno" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>所属公司</th>
				<th>姓名</th>
				<th>性别</th>
				<th>身份证号</th>
				<th>修改时间</th>
				<shiro:hasPermission name="erp:employee:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="employee">
			<tr>
				<td><a href="${ctx}/erp/employee/form?id=${employee.id}">
					${employee.companyid}
				</a></td>
				<td>
					${employee.name}
				</td>
				<td>
					${fns:getDictLabel(employee.sex, 'sex', '')}
				</td>
				<td>
					${employee.identityno}
				</td>
				<td>
					<fmt:formatDate value="${employee.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="erp:employee:edit"><td>
    				<a href="${ctx}/erp/employee/form?id=${employee.id}">修改</a>
					<a href="${ctx}/erp/employee/delete?id=${employee.id}" onclick="return confirmx('确认要删除该企业人才信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
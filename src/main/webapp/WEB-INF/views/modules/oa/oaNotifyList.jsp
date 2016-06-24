<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通知管理</title>
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
		<li class="active"><a href="${ctx}/oa/oaNotify/${hr_users.self?'self':''}">人事专员信息列表</a></li>
		<c:if test="${!hr_users.self}"><shiro:hasPermission name="oa:oaNotify:edit"><li><a href="${ctx}/oa/oaNotify/form">人事添加</a></li></shiro:hasPermission></c:if>
	</ul>
	<form:form id="searchForm" modelAttribute="hr_users" action="${ctx}/oa/oaNotify/${hr_users.self?'self':''}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户名：</label>
				<form:input path="nickname" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>公司：</label>
				<form:select path="company" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getCompanyList()}" itemLabel="label" itemValue="label" htmlEscape="false"/>
				</form:select>
			</li>
			<c:if test="${!requestScope.hr_users.self}"><li><label>性别：</label>
				<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li></c:if>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>性别</th>
				<th>公司</th>
				<th>真实姓名</th>
				<th>密码</th>
				<c:if test="${!hr_users.self}"><shiro:hasPermission name="oa:oaNotify:edit"><th>操作</th></shiro:hasPermission></c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hr_users">
			<tr>
				<td><%-- <a href="${ctx}/oa/oaNotify/${requestScope.hr_users.self?'view':'form'}?uid=${hr_users.uid}"> 
					${fns:abbr(hr_users.nickname,30)}
				</a>--%>${fns:abbr(hr_users.nickname,30)}</td>
				<td>
					${fns:getDictLabel(hr_users.sex, 'sex', '')}
				</td>
				<td>
					${hr_users.company}
				</td>
				<td>
					${hr_users.name}
				</td>
				<td>
					${hr_users.password}
				</td>
				<%-- <td>
					<c:if test="${requestScope.hr_users.self}">
						${fns:getDictLabel(hr_users.readFlag, 'oa_notify_read', '')}
					</c:if>
					<c:if test="${!requestScope.hr_users.self}">
						${hr_users.readNum} / ${hr_users.readNum + hr_users.unReadNum}
					</c:if>
				</td>
				<td>
					<fmt:formatDate value="${hr_users.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td> --%>
				<c:if test="${!requestScope.hr_users.self}"><shiro:hasPermission name="oa:oaNotify:edit"><td>
    				<a href="${ctx}/oa/oaNotify/form?id=${hr_users.id}">修改</a>
					<a href="${ctx}/oa/oaNotify/delete?id=${hr_users.id}" onclick="return confirmx('确认要删除该通知吗？', this.href)">删除</a>
				</td></shiro:hasPermission></c:if>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
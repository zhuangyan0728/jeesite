<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通知回复管理</title>
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
		<li class="active"><a href="${ctx}/oa/oaNotifyRecordBack/">通知回复列表</a></li>
		<shiro:hasPermission name="oa:oaNotifyRecordBack:edit"><li><a href="${ctx}/oa/oaNotifyRecordBack/form">通知回复添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="oaNotifyRecordBack" action="${ctx}/oa/oaNotifyRecordBack/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>内容：</label>
				<form:input path="remarks" htmlEscape="false" maxlength="2000" class="input-medium"/>
			</li>
			<li><label>时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaNotifyRecordBack.beginCreateDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaNotifyRecordBack.endCreateDate}" pattern="yyyy-MM-dd"/>"
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
				<th>内容</th>
				<th>更新时间</th>
				<shiro:hasPermission name="oa:oaNotifyRecordBack:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaNotifyRecordBack">
			<tr>
				<td><a href="${ctx}/oa/oaNotifyRecordBack/form?id=${oaNotifyRecordBack.id}">
					${oaNotifyRecordBack.remarks}
				</a></td>
				<td>
					<fmt:formatDate value="${oaNotifyRecordBack.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="oa:oaNotifyRecordBack:edit"><td>
    				<a href="${ctx}/oa/oaNotifyRecordBack/form?id=${oaNotifyRecordBack.id}">修改</a>
					<a href="${ctx}/oa/oaNotifyRecordBack/delete?id=${oaNotifyRecordBack.id}" onclick="return confirmx('确认要删除该通知回复吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
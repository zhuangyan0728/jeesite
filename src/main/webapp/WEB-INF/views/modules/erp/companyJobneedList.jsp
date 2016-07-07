<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业招聘需求管理</title>
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
		<li class="active"><a href="${ctx}/erp/companyJobneed/">企业招聘需求列表</a></li>
		<shiro:hasPermission name="erp:companyJobneed:edit"><li><a href="${ctx}/erp/companyJobneed/form">企业招聘需求添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="companyJobneed" action="${ctx}/erp/companyJobneed/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>所属公司：</label><sys:treeselect id="company" name="company.id" value="${companyJobneed.company.id}" labelName="company.name" labelValue="${companyJobneed.company.name}" 
				title="公司" url="/erp/companyInfo/treeData?type=1" cssClass="input-small" allowClear="true"/>
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
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>所属企业</th>
				<th>人才分类</th>
				<th>性别</th>
				<th>人才数量</th>
				<th>学历要求</th>
				<th>备注</th>
				<th>更新时间</th>
				<shiro:hasPermission name="erp:companyJobneed:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="companyJobneed">
			<tr>
				<td><a href="${ctx}/erp/companyJobneed/form?id=${companyJobneed.id}">
					${companyJobneed.company.name}
				</a></td>
				<td>
					${fns:getDictLabel(companyJobneed.sort, 'talent_sort', '')}
				</td>
				<td>
					${fns:getDictLabel(companyJobneed.sex, 'sex', '')}
				</td>
				<td>
					${companyJobneed.jobquantity}
				</td>
				<td>
					${fns:getDictLabel(companyJobneed.educationneed, 'education_need', '')}
				</td>
				<td>
					${companyJobneed.remarks}
				</td>
				<td>
					<fmt:formatDate value="${companyJobneed.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="erp:companyJobneed:edit"><td>
    				<a href="${ctx}/erp/companyJobneed/form?id=${companyJobneed.id}">修改</a>
					<a href="${ctx}/erp/companyJobneed/delete?id=${companyJobneed.id}" onclick="return confirmx('确认要删除该企业招聘需求吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
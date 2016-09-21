<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>推荐人才信息管理</title>
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
		<!--  <li class="active"><a href="${ctx}/erp/companyJobneedRecommend/">推荐人才信息列表</a></li>
		<shiro:hasPermission name="erp:companyJobneedRecommend:edit"><li><a href="${ctx}/erp/companyJobneedRecommend/form">推荐人才信息添加</a></li></shiro:hasPermission>-->
	</ul>
	<form:form id="searchForm" modelAttribute="companyJobneed" action="${ctx}/erp/companyJobneedRecommend/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>所属公司：</label><sys:treeselect id="company" name="company.id" value="${companyJobneed.company.id}" labelName="company.name" labelValue="${companyJobneed.company.name}" 
				title="公司" url="/erp/companyInfo/treeData?type=1" cssClass="input-small" allowClear="true"/>
			</li> 
			<li><label>人才分类：</label>
				<form:select path="sort" class="input-medium" readonly="readonly">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('talent_sort')}" itemLabel="label" itemValue="value" htmlEscape="false" disabled="true"/>
				</form:select>
			</li>
			<li><label>学历要求：</label>
				<form:select path="educationneed" class="input-medium" readonly="readonly">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('education_need')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>工作年限：</label>
				<form:input path="workedyear" htmlEscape="false" maxlength="10" class="input-medium" readonly="readonly"/>
			</li>
			<li><label>数量：</label>
				<form:input path="workedyear" htmlEscape="false" maxlength="10" class="input-medium" readonly="readonly"/>
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
	<%!  int lineNum=1;%>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="2%">序号</th>
				<th>所属企业</th>
				<th>人才分类</th>
				<th>性别</th>
				<th>人才数量</th>
				<th>学历要求</th>
				<th>到岗时间</th>
				<th>备注</th>
				<th>更新时间</th>
				<shiro:hasPermission name="erp:companyJobneedRecommend:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="companyJobneed">
			<tr>
				<td><%=lineNum%><%lineNum++;%></td>
				<td><a href="${ctx}/erp/companyJobneedRecommend/form?id=${companyJobneed.id}">
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
					<fmt:formatDate value="${companyJobneed.jointime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${companyJobneed.remarks}
				</td>
				<td>
					<fmt:formatDate value="${companyJobneed.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="erp:companyJobneedRecommend:edit"><td>
    				<a href="${ctx}/erp/companyJobneedRecommend/form?id=${companyJobneed.id}">推荐人才      </a>
    				<label>      </label>
					<a href="${ctx}/erp/companyJobneedRecommend/delete?id=${companyJobneed.id}" onclick="return confirmx('确认要删除该推荐人才信息吗？', this.href)">  删除推荐信息</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
		<%lineNum=1;%>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
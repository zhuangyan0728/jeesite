<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业信息管理</title>
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
		var lineNum = 0;
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/erp/companyInfo/">企业信息列表</a></li>
		<shiro:hasPermission name="erp:companyInfo:edit"><li><a href="${ctx}/erp/companyInfo/form">企业信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="companyInfo" action="${ctx}/erp/companyInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>企业名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<!--
			<li><label>组织机构代码：</label>
				<form:input path="organizecode" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>税务登记证号：</label>
				<form:input path="taxcode" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>法人：</label>
				<form:input path="legalperson" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>联系电话：</label>
				<form:input path="phone" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>-->
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="2%">序号</th>
				<th>企业名称</th>
				<th>法人</th>
				<th>手机</th>
				<!--  <th>三证合一代码</th>
				<th>税务登记证号</th>
				<th>备注</th>-->
				<th>企业人数</th>
				<th>是否规上</th>
				<th>是否高新企业</th>
				<th>联络人</th>
				<th>职务</th>
				<th>联络人手机</th>
				<th>更新时间</th>
				<th>查看</th>
				<shiro:hasPermission name="erp:companyInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<%!  int lineNum=1;%> 
		<c:forEach items="${page.list}" var="companyInfo">
			<tr>
				<td><%=lineNum%><%lineNum++;%></td>
				<td><a href="${ctx}/erp/companyInfo/form?id=${companyInfo.id}">
					${companyInfo.name}
				</a></td>
				<td>
					${companyInfo.legalperson}
				</td>
				<td>
					${companyInfo.phone}
				</td>			
				<td>
					 ${companyInfo.empCnt}
				</td>
				<td>
					${fns:getDictLabel(companyInfo.gauge, 'yes_no', '')}
				</td>			
				<td>
					${fns:getDictLabel(companyInfo.highTech, 'yes_no', '')}
				</td>
				<td>
					${companyInfo.legalperson}
				</td>
				<td>
					总经理
				</td>
				<td>
					${companyInfo.phone}
				</td>	
				<td>
					<fmt:formatDate value="${companyInfo.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<!--  
				<td>
					${companyInfo.organizecode}
				</td>
				<td>
					${companyInfo.taxcode}
				</td>
				<td>
					${companyInfo.remarks}
				</td>-->
				
				<td><a href="${ctx}/erp/companyInfo/form?id=${companyInfo.id}">
					查看</td>
				<shiro:hasPermission name="erp:companyInfo:edit"><td>
    				<a href="${ctx}/erp/companyInfo/form?id=${companyInfo.id}">修改</a>
					<a href="${ctx}/erp/companyInfo/delete?id=${companyInfo.id}" onclick="return confirmx('确认要删除该企业信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
		 <%lineNum=1;%>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
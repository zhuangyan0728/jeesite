<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业人员结构管理</title>
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
		<li class="active"><a href="${ctx}/erp/employeecount/">企业人员结构列表</a></li>
		<shiro:hasPermission name="erp:employeecount:edit"><li><a href="${ctx}/erp/employeecount/form">企业人员结构上报</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="employeecount" action="${ctx}/erp/employeecount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>企业名称：</label><sys:treeselect id="company" name="company.id" value="${employeecount.company.id}" labelName="company.name" labelValue="${employeecount.company.name}" 
				title="公司" url="/erp/companyInfo/treeData?type=1" cssClass="input-small" allowClear="true"/>
			</li>
			<li><label>填报时间：</label>
				<input name="beginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${employeecount.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${employeecount.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>总人数：</label>
				<form:input path="totalsum" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>企业名称</th>
				<th>上报时间</th>
				<th>总人数</th>
				<th>博士人数</th>
				<th>研究生人数</th>
				<th>大学本科人数</th>
				<th>大学专科</th>
				<th>高中</th>
				<th>初中以下</th>
				<th>高级技术职称</th>
				<th>中级技术职称</th>
				<th>高级技师</th>
				<th>技师</th>
				<th>高级工程师</th>
				<th>中级工程师</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="erp:employeecount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="employeecount">
			<tr>
				<td> 
					${employeecount.company.name}
				 </td>
				 <td> 
					<fmt:formatDate value="${employeecount.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
				 </td>
				<td> 
					${employeecount.totalsum}
				 </td>
				<td>
					${employeecount.doctor}
				</td>
				<td>
					${employeecount.master}
				</td>
				<td>
					${employeecount.bachelordegree}
				</td>
				<td>
					${employeecount.collegeeducation}
				</td>
				<td>
					${employeecount.seniorhighschool}
				</td>
				<td>
					${employeecount.middleschool}
				</td>
				<td>
					${employeecount.seniortechnicaltitles}
				</td>
				<td>
					${employeecount.middletechnicaltitles}
				</td>
				<td>
					${employeecount.seniortechnician}
				</td>
				<td>
					${employeecount.technician}
				</td>
				<td>
					${employeecount.seniorengineer}
				</td>
				<td>
					${employeecount.middleengineer}
				</td>
				<td>
					<c:if test="${employeecount.status=='0'}"> 
	    				未上报
	  				</c:if>
	  				<c:if test="${employeecount.status=='1'}"> 
	    				已上报
	  				</c:if>
				</td>
		 
				<td>
					<fmt:formatDate value="${employeecount.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="erp:employeecount:edit"><td>
				 <c:if test="${employeecount.status=='0'}"> 
			     	<a href="${ctx}/erp/employeecount/commit?id=${employeecount.id}" onclick="return confirmx('确认要上报企业人员结构吗？(上报后无法修改)', this.href)">上报</a>
    				<a href="${ctx}/erp/employeecount/form?id=${employeecount.id}">修改</a>
					<a href="${ctx}/erp/employeecount/delete?id=${employeecount.id}" onclick="return confirmx('确认要删除该企业人员结构吗？', this.href)">删除</a>
				  </c:if>
				  
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
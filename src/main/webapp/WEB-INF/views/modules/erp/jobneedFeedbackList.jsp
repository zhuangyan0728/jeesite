<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>反馈信息管理</title>
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
		<li class="active"><a href="${ctx}/erp/jobneedFeedback/">反馈信息列表</a></li>
		<shiro:hasPermission name="erp:jobneedFeedback:edit"><li><a href="${ctx}/erp/jobneedFeedback/form">反馈信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="jobneedFeedback" action="${ctx}/erp/jobneedFeedback/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>招聘需求：</label>
                <sys:treeselect id="joobneed" name="joobneed.id" value="${jobneedFeedback.joobneed.id}" labelName="joobneed.name" labelValue="${jobneedFeedback.joobneed.remarks}"
					 title="招聘需求" url="/erp/companyJobneed/treeData?type=1"  cssStyle = "width:225px"  cssClass="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</li>
			<li><label>回馈类型：</label>
				<form:select path="feedbacktype" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('jobFeedBack_Type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>回馈时间：</label>
				<input name="beginFeedbacktime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${jobneedFeedback.beginFeedbacktime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endFeedbacktime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${jobneedFeedback.endFeedbacktime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>创建时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${jobneedFeedback.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>招聘需求</th>
				<th>回馈类型</th>
				<th>回馈时间</th>
				<th>创建时间</th>
				<th>修改时间</th>
				<shiro:hasPermission name="erp:jobneedFeedback:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="jobneedFeedback">
			<tr>
				<td><%=lineNum%><%lineNum++;%></td>
				<td><a href="${ctx}/erp/jobneedFeedback/form?id=${jobneedFeedback.id}">
					${jobneedFeedback.joobneed.remarks}
				</a></td>
				<td>
					${fns:getDictLabel(jobneedFeedback.feedbacktype, 'jobFeedBack_Type', '')}
				</td>
				<td>
					<fmt:formatDate value="${jobneedFeedback.feedbacktime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${jobneedFeedback.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${jobneedFeedback.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="erp:jobneedFeedback:edit"><td>
    				<a href="${ctx}/erp/jobneedFeedback/form?id=${jobneedFeedback.id}">修改</a>
					<a href="${ctx}/erp/jobneedFeedback/delete?id=${jobneedFeedback.id}" onclick="return confirmx('确认要删除该反馈信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
		<%lineNum=1;%>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
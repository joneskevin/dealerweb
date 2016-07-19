<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<body>
<script>
function comfirmDelete() {
	if (confirm("确定要删除此记录吗？")) {
		return true;
	} else {
		return false;
	}
}

var pageFormObj = document.getElementById("myPageForm");
function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/base/user/exportUserExcel.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/base/user/search.vti?startIndex=${transMsg.startIndex}";
}
</script>
<form id="myPageForm" action="<%=path%>/base/user/search.vti" method="get" >
	<div class="bd_rt" id="audit">
		<div class="bd_rt_nav clearfix">
			<span>当前位置：帮助中心
			</span>
		</div>
		<fieldset class="bd_rt_nav clearfix " >
    			<legend>查询条件</legend>
   				问题标题
				<form:input path="question.title" cssClass="ipt_txt" />
				<input type="submit"  class="btn_submit" value="查询" />
  		</fieldset>

		<aside class="bd_rt_main clearfix">
			<div class="table_list table_all">
				<div>
				<ul class="table_hd clearfix">
<!-- 					<li class="li_50"><span class="checkbox text_over" onClick="selectAll(this, 'checkedIds')">checkbox</span></li> -->
            		<li class="li_150">所属单位</li>
					<li class="li_100">姓名</li>
					<li class="li_100">帐号</li>
					<li class="li_120">手机</li>
					<li class="li_120">邮箱</li>
					<li class="li_180">角色</li>
					<li class="li_100">禁用</li>
					<li class="li_220">操作</li>
				</ul>
				</div>
				<div>
				<c:if test="${userList != null}" >
				<c:forEach var="user" items="${userList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'>
<%-- 					<li class="li_50"><span class="checkbox text_over" name="checkedIds" value="${user.id}">checkbox</span></li> --%>
		
            		<li class="li_150"><span title="${user.companyName}">${user.companyName}</span></li>
            		<li class="li_100"><span title="${user.nickName}">${user.nickName}</span></li>
            		<li class="li_100"><span title="${user.loginName}">${user.loginName}</span></li>
            		<li class="li_120"><span title="${user.mobile}">${user.mobile}</span></li>
            		<li class="li_120"><span title="${user.email}">${user.email}</span></li>
            		<li class="li_180"><span title="${user.roles}">${user.roles}</span></li>
            		<li class="li_100"><span title="${user.isDisableNick}">${user.isDisableNick}</span></li>
            		<li class="li_220">
								
						<a href="javascript:openWindow('<%=path%>/base/user/view.vti?userId=<c:out value="${user.id}" />&startIndex=${transMsg.startIndex}', 660, 445, '用户信息')">查看</a>
						<c:if test="${user.userRight.editorAndDeleteCompetence == 1}">
							<a href="javascript:openWindow('<%=path%>/base/user/displayEdit.vti?id=${user.id}&startIndex=${transMsg.startIndex}', 660, 460, '编辑用户')" >编辑</a>
		 			    </c:if>	
						<c:set var="globalTrue" value="<%=String.valueOf(GlobalConstant.TRUE)%>"/> 
           				<c:if test="${user.userRight.editorAndDeleteCompetence == globalTrue}">						
							<a href = "<%=path%>/base/user/delete.vti?userId=<c:out value="${user.id}" />&startIndex=${transMsg.startIndex}" onclick="return comfirmDelete();">删除</a>
						</c:if>	
            		</li>
				</ul>
				</c:forEach>
				</c:if>
				</div>
			</div>
			<footer>
				<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
				<jsp:include page="/pub/navigate/navigation4dealer.jsp" flush="true"/>
			</footer>
		</aside>
	</div>
</form>

</body>
</html>
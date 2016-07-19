<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>

function selectAll(checkeAllObj, checkboxName) {
	if ($(checkeAllObj).is('.active')) {
		$(checkeAllObj).removeClass("active");
	} else {
		$(checkeAllObj).addClass("active");
	}
	$(".checkbox").each(function(index,domEle){
		if ($(this).is('.active')) {
			$(this).removeClass("active");
		} else {
			$(this).addClass("active");
		}
	});
}

var pageFormObj = document.getElementById("myPageForm");
function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/base/user/exportUserExcel.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/base/user/search.vti?startIndex=${transMsg.startIndex}";
}

function comfirmDelete(targetUrl){
	var pageFormObj = document.getElementById("myPageForm");
	if (confirm("确定要删除此记录吗？")) {
	  	 targetUrl += "&random=" + Math.random();
		 $.ajax({
		 url: targetUrl,
   		  type: 'GET',
		      dataType: 'json',
		      timeout: 10000,
		      error: function(){
		         alert('系统异常');
		      },
		      success: function(responseData){
		      if (responseData.code == 1) {
		    	 pageFormObj.action="<%=path%>/base/user/search.vti";
		    	 var startIndex = "${transMsg.startIndex}";
		    	 if (startIndex != null && startIndex !="") {
		    	 	$("#startIndex").val(startIndex);
		    	 }
		    	 pageFormObj.submit();
		         
		     } else {
		        jAlert(responseData.message);
		     }
		    }
		 });
	} 
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/base/user/search.vti" method="get" >
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：运营管理  > 用户管理</span>
		</c:if>
	</div>
	<div class="audit_nav_c">
	    <strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a>
		 <a href="javascript:openWindow('<%=path%>/base/user/displayAdd.vti?startIndex=${transMsg.startIndex}', 660, 400, '添加用户')" >添加用户</a>
		 </span></strong>
			
			所属单位
			<form:select path='user.orgId' cssClass="select_condition_company"  >
				<form:options items="${orgList}" itemLabel="name" itemValue="id"  htmlEscape="false" />
			</form:select>
			名称
			<form:input path="user.nickName"  cssClass="ipt_txt" />
			帐号
			<form:input path="user.loginName" cssClass="ipt_txt" />
			<input type="submit"  class="btn_submit" value="查 询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:1380px;">
					<li class="li_240">所属单位</li>
					<li class="li_100">名称</li>
					<li class="li_100">帐号</li>
					<li class="li_100">登录密码</li>
					<li class="li_120">手机</li>
					<li class="li_200">邮箱</li>
					<li class="li_240">角色</li>
					<li class="li_50">禁用</li>
					<li class="li_220">操作</li>
				</ul>
			<c:if test="${userList != null}" >
				<c:forEach var="user" items="${userList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:1380px;">
		
            		<li class="li_240"><span title="${user.companyName}">${user.companyName}</span></li>
            		<li class="li_100"><span title="${user.nickName}">${user.nickName}</span></li>
            		<li class="li_100"><span title="${user.loginName}">${user.loginName}</span></li>
            		<li class="li_100"><span title="${user.pseudoPassword}">${user.pseudoPassword}</span></li>
            		<li class="li_120"><span title="${user.mobile}">${user.mobile}</span></li>
            		<li class="li_200"><span title="${user.email}">${user.email}</span></li>
            		<li class="li_240"><span title="${user.roles}">${user.roles}</span></li>
            		<li class="li_50"><span title="${user.isDisableNick}">${user.isDisableNick}</span></li>
            		<li class="li_220">
								
						<%-- <a href="javascript:openWindow('<%=path%>/base/user/view.vti?userId=<c:out value="${user.id}" />&startIndex=${transMsg.startIndex}', 660, 400, '用户信息')">查看</a> --%>
						<c:if test="${user.userRight.editorAndDeleteCompetence == 1}">
							<a href="javascript:openWindow('<%=path%>/base/user/displayEdit.vti?id=${user.id}&startIndex=${transMsg.startIndex}', 660, 400, '编辑用户')" >编辑</a>
		 			    </c:if>	
						<c:set var="globalTrue" value="<%=String.valueOf(GlobalConstant.TRUE)%>"/> 
           				<c:if test="${user.userRight.editorAndDeleteCompetence == globalTrue}">						
							 <a  href="#" onClick="javascript:comfirmDelete('<%=path%>/base/user/delete.vti?userId=<c:out value="${user.id}" />&startIndex=${transMsg.startIndex}');">删除</a>
						</c:if>	
            		</li>
				</ul>
				</c:forEach>
				</c:if>
	</div><!--/table_list-->
	</aside>
	<div class="bd_rt_ft">
			<jsp:include page="/pub/navigate/navigation4dealer.jsp" flush="true"/>
	</div>
</div><!--/.bd_rt-->
</form>
</body>
</html>
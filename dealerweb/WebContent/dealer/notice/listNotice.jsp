<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.domain.entity.Notice" %>
<%
	Notice notice = (Notice)request.getAttribute("notice");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>

function comfirmDelete(targetUrl){
	var pageFormObj = document.getElementById("myPageForm");
	if (confirm("删除提示：删除将不能恢复，且会影响用户功能的使用，确认要删除吗？")) {
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
		    	 pageFormObj.action="<%=path%>/base/role/search.vti";
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

function addRole(dialogSrc) {
	var addRole=$("#addPopupContainerId");
	$.ajax({
		url:dialogSrc +"&r=" + Math.random(),
		type:"GET",
		dataType:"html",
		success:function(data) {
			addRole.append(data);
			addRole.show();
		}
	});
}

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
	pageFormObj.action="<%=path%>/dealer/role/outputroleInfoExcel.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/role/search.vti?startIndex=${transMsg.startIndex}";
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/base/notice/search.vti" method="get" >
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：运营管理  > 公告管理 </span>
		</c:if>
	</div>
	<div class="audit_nav_c">
		 <span class="audit_nav_c_span">
		<a href="javascript:openWindow('<%=path%>/base/notice/displayAdd.vti?startIndex=${transMsg.startIndex}', 750, 220, '添加公告')" >添加公告</a>
		</span>
			 标题
			<form:input path="notice.title"  cssClass="ipt_txt" />
			<input type="submit"  class="btn_submit" value="查询" />
		</div>		
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" >
					<li class="li_170">标题</li>
					<li class="li_400">简介</li>
					<li class="li_150">启用时间</li>
					<li class="li_150">失效时间</li>
					<li class="li_220">操作</li>
				</ul>
		<c:if test="${noticeList != null}" >
				<c:forEach var="notice" items="${noticeList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'>
<%-- 					<li class="li_50"><span class="checkbox text_over" name="checkedIds" value="${role.id}">checkbox</span></li> --%>
		
            		<li class="li_170"><span title="${notice.title}">${notice.title}</span></li>
            		<li class="li_400"><span title="${notice.summary}">${notice.summary}</span></li>
            		<li class="li_150"><span title="<fmt:formatDate value="${notice.startTime}"
											type="both" pattern="yyyy-MM-dd" />"><fmt:formatDate value="${notice.startTime}"
											type="both" pattern="yyyy-MM-dd" /></span></li>
					<li class="li_150"><span title="<fmt:formatDate value="${notice.invalidTime}"
											type="both" pattern="yyyy-MM-dd" />"><fmt:formatDate value="${notice.invalidTime}"
											type="both" pattern="yyyy-MM-dd" /></span></li>
            		<li class="li_220">
            		<a href="javascript:openWindow('<%=path%>/base/notice/view.vti?id=${notice.id}&startIndex=${transMsg.startIndex}', 750, 220, '查看公告')" >查看</a>
            		<%-- <c:if test="${notice.type == 3}" > --%>
            			<a href="javascript:openWindow('<%=path%>/base/notice/displayEdit.vti?id=${notice.id}&startIndex=${transMsg.startIndex}', 750, 260, '编辑公告')" >编辑</a>
					<%-- </c:if> --%>
					<c:if test="${notice.type == 3}" > 
						<a href = "<%=path%>/base/notice/displayEditNoticeOfDealer.vti?noticeId=${notice.id}&startIndex=${transMsg.startIndex}">分配经销商</a>
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
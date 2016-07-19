<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>


var pageFormObj = document.getElementById("myPageForm");
function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/dealer/role/outputroleInfoExcel.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/role/search.vti?startIndex=${transMsg.startIndex}";
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/base/notice/listUserNotice.vti" method="get" >
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：系统设置  > 公告列表 </span>
		</c:if>
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" >
					<li class="li_170">标题</li>
					<li class="li_400">简介</li>
					<li class="li_120">生效日期</li>
					<li class="li_120">失效日期</li>
					<li class="li_120">公告类型</li>
					<li class="li_120">查看</li>
				</ul>
		<c:if test="${noticeList != null}" >
				<c:forEach var="notice" items="${noticeList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'>
            		<li class="li_170"><span title="${notice.title}">${notice.title}</span></li>
            		<li class="li_400"><span title="${notice.summary}">${notice.summary}</span></li>
            		<li class="li_120"><fmt:formatDate value="${notice.startTime}" type="both" pattern="yyyy-MM-dd" /></li>
					<li class="li_120"><fmt:formatDate value="${notice.invalidTime}" type="both" pattern="yyyy-MM-dd" /></li>
					<li class="li_120">${notice.noticeType}</li>
					<li class="li_120"><a href="javascript:openWindow('<%=path%>/base/notice/view.vti?id=${notice.id}&startIndex=${transMsg.startIndex}', 600, 220, '查看公告')" >查看</a></li>
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
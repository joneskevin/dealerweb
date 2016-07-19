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
	pageFormObj.action="<%=path%>/base/user/exportUserLogExcel.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/base/user/listUserLog.vti?startIndex=${transMsg.startIndex}";
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/base/user/listUserLog.vti" method="get" >
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：运营管理  > 登录日志</span>
		</c:if>
	</div>
	<div class="audit_nav_c">
	<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
		 网络代码
		<input type="text" name="dealerCode" value="${dealerCode}" class="ipt_txt"/>
		 登录时间
	 	<input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
			-
		<input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" 	value="<fmt:formatDate value="${endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
		<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" >
				<ul class="table_hd clearfix" >
            		<li class="dealerCode_70">网络代码</li>
            		<li class="cnName_240">经销商名称</li>
            		<li class="time_150">登录时间</li>
				</ul>
			<c:if test="${userLogList != null}" >
				<c:forEach var="vo" items="${userLogList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'>
            		<li class="dealerCode_70"><span title="${vo.dealerCode}">${vo.dealerCode}</span></li>
            		<li class="cnName_240"><span title="${vo.companyName}">${vo.companyName}</span></li>
            		<li class="time_150"><span title="<fmt:formatDate value="${vo.actionTime}" type="both" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${vo.actionTime}" type="both" pattern="yyyy-MM-dd"/></span></li>
				</ul>
				</c:forEach>
				</c:if>
	</div>
	</aside>
	<div class="bd_rt_ft">
			<jsp:include page="/pub/navigate/navigation4dealer.jsp" flush="true"/>
	</div>
</div><!--/.bd_rt-->
</form>
</body>
</html>
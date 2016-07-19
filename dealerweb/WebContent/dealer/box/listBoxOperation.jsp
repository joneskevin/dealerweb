<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>

function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/dealer/box/exportBoxOperation.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/box/listBoxOperation.vti?startIndex=${transMsg.startIndex}";
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/box/listBoxOperation.vti" method="get" >
<div class="bd_rt" id="audit">
	
	<div class="bd_rt_nav clearfix">
		<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
			<span>当前位置：设备管理  > 设备操作日志  </span>
		</c:if>
	 </div>
	<div class="audit_nav_c">
		<span class="audit_nav_c_span">
			<a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a>
			<%-- <a href="javascript:openWindow('<%=path%>/dealer/box/displayAdd.vti', 660, 300, '添加设备')">添加设备</a> --%>
	   	</span>
		网络代码
		<form:input path="boxOperationVO.box.dealerCode" cssClass="ipt_txt"/>
		VIN
		<form:input path="boxOperationVO.vin" cssClass="ipt_txt"/>
		设备号
		<form:input path="boxOperationVO.box.uniqueId" cssClass="ipt_txt"/>
		<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="min-width:1540px;">
					<li class="distributionSaleCenterName_80">分销中心</li>
					<li class="dealerCode_70">网络代码</li>
            		<li class="cnName_240">经销商名称</li>
            		<li class="li_120">设备号</li>
            		<li class="carStyleId_Nick_200">车型</li>
            		<li class="vin_140">VIN</li>
            		<li class="plateNumber_75">车牌</li>
            		<li class="li_50">类型</li>
            		<li class="time_150">操作时间</li>
				</ul>
			<c:if test="${boxOperationList != null}" >
				<c:forEach var="vo" items="${boxOperationList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="min-width:1540px;">
					<li class="distributionSaleCenterName_80"><span title="${vo.saleCenterName}">${vo.saleCenterName}</span></li>
            		<li class="dealerCode_70"><span title="${vo.dealerCode}">${vo.dealerCode}</span></li>
            		<li class="cnName_240"><span title="${vo.companyName}">${vo.companyName}</span></li>
            		<li class="li_120"><span title="${vo.uniqueId}">${vo.uniqueId}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${vo.carStyleName}">${vo.carStyleName}</span></li>
            		<li class="vin_140"><span title="${vo.vin}">${vo.vin}</span></li>
            		<li class="plateNumber_75"><span title="${vo.plateNumber}">${vo.plateNumber}</span></li>
            		<li class="li_50"><span title="${vo.typeNick}">${vo.typeNick}</span></li>
            		<li class="time_150"><span title="<fmt:formatDate value="${vo.operationTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${vo.operationTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
				</ul>
			</c:forEach>
		</c:if>
	</div>
	</aside>
	<div class="bd_rt_ft">
			<jsp:include page="/pub/navigate/navigation4dealer.jsp" flush="true"/>
	</div>
</div>
</form>
</body>
</html>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
	 var pageFormObj = document.getElementById("myPageForm");
    function gotoLoadExcel(){
    	pageFormObj.action="<%=path%>/dealer/testDrive/outputIntevelDetail.vti?startIndex=${transMsg.startIndex}&startTime=${startTimeNormal}&endTime=${endTimeNormal}&vehicleId=${vehicleId}&lineId=${lineId}";
	    pageFormObj.submit();
	    pageFormObj.action="<%=path%>/dealer/testDrive/find.vti";
    }    
</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/testDrive/find.vti" method="get" >
<input type="hidden" id="lineId" name="lineId" value="${lineId}" />
<input type="hidden" id="vehicleId" name="vehicleId" value="${vehicleId}" />
<input type="hidden" id="startTime" name="startTime" value="${startTimeNormal}" />
<input type="hidden" id="endTime" name="endTime" value="${endTimeNormal}" />
	<div class="bd_rt" id="audit">
		<div class="bd_rt_nav clearfix">
			<span> 当前位置：试驾明细  > 单周统计 > 试驾明细</span>
		</div>
		<div class="audit_nav_c">
			<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
		</div>
		<aside class="bd_rt_main clearfix">
			<div class="table_list table_all">
				<ul class="table_hd clearfix">
            		<li class="li_100">试驾日期</li>
            		<li class="li_180">开始时间</li>
            		<li class="li_180">结束时间</li>
            		<li class="li_100">用时(分)</li>
            		<li class="li_120">线路</li> 
            		<!-- <li class="li_50">次数</li> -->
            		<li class="li_100">里程(KM)</li>
            		<li style="border:0;padding-left:15px;">操作</li>
				</ul>
				<c:if test="${testDrive4StatVOList != null}" >
				<c:forEach var="vo" items="${testDrive4StatVOList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'>
            		<li class="li_100"><span title="${vo.testDriveDate}">${vo.testDriveDate}</span></li>
            		<li class="li_180"><span title="<fmt:formatDate value="${vo.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${vo.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            		<li class="li_180"><span title="<fmt:formatDate value="${vo.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${vo.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            		<li class="li_100"><span title="${vo.secondsOfInterval / 60}">${vo.secondsOfInterval / 60}</span></li>
            		<li class="li_120"><span title="${vo.lineId_Nick}">${fn:substring(vo.lineId_Nick,0,4)}</span></li>
            		<%-- <li class="li_50"><span title="${vo.loopCount}">${vo.loopCount}</span></li> --%>
            		<li class="li_100"><span title="${vo.mileage/1000}">${vo.mileage/1000}</span></li>
            		<li style="border:0;padding-left:15px;">
			         	<a href="<%=path%>/dealer/track/home.vti?plateNumber=${vo.vehicle.plateNumber}&startTime=<fmt:formatDate value="${vo.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>&endTime=<fmt:formatDate value="${vo.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>">查看轨迹</a>
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

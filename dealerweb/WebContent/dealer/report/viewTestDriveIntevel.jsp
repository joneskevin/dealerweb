<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.base.dao.hibernate.TransMsg" %>
<%
	
	TransMsg transMsg = (TransMsg)request.getAttribute("transMsg");
	//总记录数
	Integer startIndex;
	if (transMsg.getStartIndex() == null) {
		startIndex = new Integer(0);
	} else {
		startIndex = transMsg.getStartIndex();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>


<script>
	 var pageFormObj = document.getElementById("myPageForm");
	 
    function gotoLoadExcel(){  
    	pageFormObj.action="<%=path%>/dealer/report/outputTestDriveDetailIntevel.vti?startIndex=<%=startIndex%>&carStyleId_Nick=${carStyleId_Nick}&vin=${vin}&vehicleId=${vehicleId}&titleVechie=${titleVechie}&driveLineId=${driveLineId}&startTime=${startTime}&endTime=${endTime}&plateNumber=${plateNumber}";
    
	    pageFormObj.submit();
	    pageFormObj.action="<%=path%>/dealer/report/detailTestDriveIntevel.vti";
    }    
</script>


<body>
<div class="mainAll">
<div class="boxAll">
<div class="boxTitle boxAllBg">
<div class="iconBox2"><img src="<%=request.getContextPath()%>/images/titleIcon_orgManage.gif" /></div>
<h1>报表管理</h1>
<div class="iconR"><img src="<%=request.getContextPath()%>/images/titleIconBox02_assignment2.gif" /></div>
	<ul class="tags">
		<li class="selectTag"><span class="left"></span>
		试驾明细统计<span class="right"></span>
		</li>
	</ul>
	<div class="enterBoxAll"><span>  <a href="javascript:history.go(-1);">返回上一级</a></span></div>
</div>

<div class="allContent">                    
<div class="pageRoute">当前位置：报表管理  > 试驾明细统计: ${titleVechie}</div>
<div class='msgResult<form:errors path="MSG_RESULT_LEVEL"/>' id='msg_result' style="display:none;"><form:errors path="MSG_RESULT"/></div>
<form id="myPageForm" action="<%=path%>/dealer/report/detailTestDriveIntevel.vti" method="get" >
<input type="hidden" id="vehicleId" name="vehicleId" value="${vehicleId}" />
<input type="hidden" id="driveLineId" name="driveLineId" value="${driveLineId}" />
<input type="hidden" id="startTime" name="startTime" value="${startTime}" />
<input type="hidden" id="endTime" name="endTime" value="${endTime}" />
<input type="hidden" id="plateNumber" name="plateNumber" value="${plateNumber}" />
<input type="hidden" id="carStyleId_Nick" name="carStyleId_Nick" value="${carStyleId_Nick}" />
<input type="hidden" id="titleVechie" name="titleVechie" value="${titleVechie}" />
<input type="hidden" id="vin" name="vin" value="${vin}" />
<table border="0" cellpadding="0" cellspacing="1" bgcolor="#92b4d0" class="tableBox">
	<tr>
		<th colspan="8">功能区</th>
	</tr>
   <tr>
	
    <td colspan="4" class="tdSpace" style="text-align:left;">
   <!--  <input  type="submit" class="btnOne" value="查 询" 
    		onclick='$("#startIndex").attr("value", "0");'/> &nbsp;&nbsp; -->
  	 &nbsp;&nbsp; <a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a>
    </td>
    
    </tr>
</table>
<table id="tableList" class="tableList" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<th width="4%">试驾日期</th>
		<th width="4%">开始时间</th>
		<th width="4%">结束时间</th>
		<th width="4%">用时(分)</th>
		<th width="3%">线路</th>
		<th width="3%">里程/km</th>
		<th width="3%" >操作</th>
		
	</tr>
	<c:if test="${testDriveList != null}" >
		<c:forEach var="testDrive" items="${testDriveList}">
		<tr>
		<td><fmt:formatDate pattern="yyyy-MM-dd" value="${testDrive.startTime}"/></td>
		<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${testDrive.startTime}"/></td>
		<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${testDrive.endTime}"/></td>
		<td><c:out value="${testDrive.costMinutes}" /></td>
		<td><c:out value="${testDrive.lineId_Nick}" /></td>
		<td><c:out value="${testDrive.mileage}" /></td>
		<td>
         	<a href="<%=path%>/dealer/testDrive/home.vti?plateNumber=${plateNumber}&startTime=${testDrive.startTime}&endTime=${testDrive.endTime}">查看轨迹</a>
		</td>
		</tr>
		</c:forEach>
	</c:if>
</table>
<div class="pages">
	<span class="page">
		<jsp:include page="/pub/navigate/navigation4Base.jsp" flush="true"/>
	</span>
</div>
</form>

</div>
</div>
</div>
</body>
</html>
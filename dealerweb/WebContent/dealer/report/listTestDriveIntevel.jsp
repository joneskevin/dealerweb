<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.base.dao.hibernate.TransMsg" %>
<%
	 User currentUser = SessionManager.getCurrentUser(request);
	
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
	$(document).ready(function() {
		$("#endTime").datepicker();
		$("#startTime").datepicker();
	});
	
	 var pageFormObj = document.getElementById("myPageForm");
	 function query() {
			pageFormObj.action="<%=path%>/dealer/report/listTestDriveIntevel.vti";
		    pageFormObj.submit();
		}
    function gotoLoadExcel(){
    	pageFormObj.action="<%=path%>/dealer/report/outputTestDriveInfoIntevelExcel.vti?startIndex=<%=startIndex %>";
	    pageFormObj.submit();
	    pageFormObj.action="<%=path%>/dealer/report/listTestDriveIntevel.vti";
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
		<a href="<%=path%>/dealer/report/listTestDriveIntevel.vti" target="main" >试驾明细统计</a><span class="right"></span>
		</li>
	</ul>
</div>

<div class="allContent">
<div class="pageRoute">当前位置：报表管理  >试驾明细统计</div>
<div class='msgResult<form:errors path="MSG_RESULT_LEVEL"/>' id='msg_result' style="display:none;"><form:errors path="MSG_RESULT"/></div>
<form id="myPageForm" action="<%=path%>/dealer/report/listTestDriveIntevel.vti" method="get" >
<table border="0" cellpadding="0" cellspacing="1" bgcolor="#92b4d0" class="tableBox">
	<tr>
		<th colspan="8">功能区</th>
	</tr>
   <tr>
	<td class="tdTitle" >车牌 ：</td>
    <td class="tdSpace" >
	<form:input path="testDriveInfoVO.plateNumber" cssClass="size" cssStyle="width:100px"/>
	</td>
	
	<td class="tdTitle" >车型 ：</td>
    <td class="tdSpace">
	<form:select path='testDriveInfoVO.carStyleId' cssClass="size" cssStyle="width:150px">
	    <form:option value=" ">--请选择--</form:option>
		<form:options items="${carStyleList}" itemLabel="name" itemValue="id"  htmlEscape="false" />
	</form:select> 
	</td>
	<td class="tdTitle" >VIN码 ：</td>
    <td class="tdSpace" >
	<form:input path="testDriveInfoVO.vin" cssClass="size" cssStyle="width:180px"/>
	</td>
  </tr>		
	<tr>
		<td class="tdTitle">开始时间：</td>
		<td class="tdSpace">
		<input id="startTime"
			name="startTime" class="size date"
			
			value="<fmt:formatDate value="${testDriveInfoVO.startTime}"
				type="both" pattern="yyyy-MM-dd" />"
			readonly="readonly" type="text" /></td>
		<td class="tdTitle">结束时间：</td>
			<td class="tdSpace">
			<input id="endTime"
				name="endTime" class="size date"
				value="<fmt:formatDate value="${testDriveInfoVO.endTime}"
				type="both" pattern="yyyy-MM-dd" />"
				readonly="readonly" type="text" />
		</td>
    <td colspan="4" class="tdSpace" style="text-align:left;">
    <input  type="button" class="btnOne" value="查 询" onclick='$("#startIndex").attr("value", "0");query()'/>&nbsp;&nbsp;
  	 
  	  <a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a>
    </td>
    
    
    </tr>
</table>
<table id="tableList" class="tableList" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<th width="4%">经销商状态</th>
		<th width="4%">网络形态</th>
		<th width="4%">网络代码</th>
		<th width="4%">分销中心</th>
		<th width="2%" >省份</th>
		<th width="2%" >城市</th>
		<th width="5%" >经销商名称</th>
		<th width="3%" >车牌</th>
		<th width="5%" >车型</th>
		<th width="5%" >VIN</th>
		<th width="4%" >线路</th>
		<th width="2%" >次数</th>
		<th width="2%" >次数</th>
		<th width="3%" >里程/km</th>
		<th width="3%" >查看明细</th>
	</tr>
	<c:if test="${testDriveInfoVOList != null}" >
		<c:forEach var="testDriveInfo" items="${testDriveInfoVOList}">
		<tr>
		<td><c:out value="${testDriveInfo.isKeyCity_Nick}" /></td>
		<td><c:out value="${testDriveInfo.dealerType_Nick}" /></td>
		<td><c:out value="${testDriveInfo.dealerCode}" /></td>
		<td><c:out value="${testDriveInfo.fenxiao_center}" /></td>
		<td><c:out value="${testDriveInfo.regionProvinceId_Nick}" /></td>
		<td><c:out value="${testDriveInfo.regionCityId_Nick}" /></td>
		<td><c:out value="${testDriveInfo.name}" /></td>
		<td><c:out value="${testDriveInfo.plateNumber}" /></td>
							
		<td><c:out value="${testDriveInfo.carStyleId_Nick}" /></td>
		<td><c:out value="${testDriveInfo.vin}" /></td>
		
		<td><c:out value="${testDriveInfo.lineId_Nick}" /></td>
		
		<td><c:out value="${testDriveInfo.loopCount}" /></td>
		<td><c:out value="${testDriveInfo.times}" /></td>
		<td><c:out value="${testDriveInfo.MILEAGE}" /></td>  <!-- testDriveInfoVO -->
		<td>
			<a href="<%=path%>/dealer/report/detailTestDriveIntevel.vti?vehicleId=<c:out value="${testDriveInfo.vehicleId}" />
			&driveLineId=<c:out value="${testDriveInfo.lineId}"/>
			&startTime=<c:out value="${testDriveInfoVO.startTime}" /> 
			&endTime=<c:out value="${testDriveInfoVO.endTime}" /> 
			&vin=<c:out value="${testDriveInfo.vin}" />
			&plateNumber=<c:out value="${testDriveInfo.plateNumber}" />&carStyleId_Nick=<c:out value="${testDriveInfo.carStyleId_Nick}" /> ">查看明细</a>
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
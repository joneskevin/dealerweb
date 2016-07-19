<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.domain.vo.CompanyCarStyleVO"%>
<%
	CompanyCarStyleVO v =	(CompanyCarStyleVO)request.getAttribute("companyCarStyle");
	String nowYear =DateTime.getYear();
	String chooseYear="";
	if(v.getYear()!=null)
	{
		chooseYear = v.getYear().toString();	
	}else{
		chooseYear= nowYear;
	}
	String lastYear= String.valueOf((Integer.parseInt(DateTime.getYear())-1));
		
	 /* User currentUser = SessionManager.getCurrentUser(request); */
	/* List<CompanyCarStyleVO> companyCarStyleVOList = (List<CompanyCarStyleVO> )
			request.getAttribute("companyCarStyleVOList"); */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
var pageFormObj = document.getElementById("myPageForm");
function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/dealer/report/outputTestDrivePercentExcel.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
	pageFormObj.action="<%=path%>/dealer/report/listTestDrivePercent.vti";
} 
</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/report/listTestDrivePercent.vti" method="get" >
<div class="bd_rt" id="audit">
	
	 <div class="bd_rt_nav clearfix">
		<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
			<span>当前位置：配置报表 > 试驾率报表  </span>
		</c:if>
	 </div>
	<div class="audit_nav_c">
	 	<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
	 	网络代码：
		<form:input path="companyCarStyle.dealerCode" cssClass="ipt_txt"/>
		关键字(经销商)：
		<form:input path="companyCarStyle.name" cssClass="ipt_txt"/>
		查询时间：
		 <input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${companyCarStyle.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
			-
		<input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" 	value="<fmt:formatDate value="${companyCarStyle.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
		
		<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:${width}px;">
					<li class="isKeyCity_Nick_80">经销商状态</li>
            		<li class="dealerType_Nick_70">网络形态</li>
            		<li class="dealerCode_70">网络代码</li>
            		<li class="distributionSaleCenterName_80">分销中心</li>
            		<li class="regionProvinceId_Nick_50">省份</li>
            		<li class="regionCityId_Nick_50">城市</li>
            		<li class="cnName_240">经销商名称</li>
             		<li class="li_240">全车型</li>
             		<c:if test="${carStyleList != null}" >
						<c:forEach var="carStyle" items="${carStyleList}">
							<li class="li_240">${carStyle.name}</li>
						</c:forEach>
					</c:if>
				</ul>
				<ul class="table_hd clearfix" style="width:${width}px;">
            		<li class="isKeyCity_Nick_80"></li>
            		<li class="dealerType_Nick_70"></li>
            		<li class="dealerCode_70"></li>
            		<li class="distributionSaleCenterName_80"></li>
            		<li class="regionProvinceId_Nick_50"></li>
            		<li class="regionCityId_Nick_50"></li>
            		<li class="cnName_240"></li>
            		<li class="single_test_drive_percent_79">试驾次数</li>
            		<li class="single_test_drive_percent_79">有望客户数</li>
            		<li class="single_test_drive_percent_79">试驾率</li>
             		<c:if test="${carStyleList != null}" >
						<c:forEach var="carStyle" items="${carStyleList}">
							<li class="single_test_drive_percent_79">试驾次数</li>
		            		<li class="single_test_drive_percent_79">有望客户数</li>
		            		<li class="single_test_drive_percent_79">试驾率</li>
						</c:forEach>
					</c:if>
				</ul>
				<c:if test="${companyCarStyleVOList != null}" >
				<c:forEach var="companyCarStyleVO" items="${companyCarStyleVOList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:${width+10}px;">
				
            		<li class="isKeyCity_Nick_80"><span title="${companyCarStyleVO.isKeyCity_Nick}">${companyCarStyleVO.isKeyCity_Nick}</span></li>
            		<li class="dealerType_Nick_70"><span title="${companyCarStyleVO.dealerType_Nick}">${companyCarStyleVO.dealerType_Nick}</span></li>
            		<li class="dealerCode_70"><span title="${companyCarStyleVO.dealerCode}">${companyCarStyleVO.dealerCode}</span></li>
            		<li class="distributionSaleCenterName_80"><span title="${companyCarStyleVO.fenxiao_center_nick}">${companyCarStyleVO.fenxiao_center_nick}</span></li>
            		<li class="regionProvinceId_Nick_50"><span title="${companyCarStyleVO.regionProvinceId_Nick}">${fn:substring(companyCarStyleVO.regionProvinceId_Nick,0,2)}</span></li>
            		<li class="regionCityId_Nick_50"><span title="${companyCarStyleVO.regionCityId_Nick}">${fn:substring(companyCarStyleVO.regionCityId_Nick,0,2)}</span></li>
            		<li class="cnName_240"><span title="${companyCarStyleVO.name}">${fn:substring(companyCarStyleVO.name,0,18)}</span></li>
            		<li class="single_test_drive_percent_79"><span title="${companyCarStyleVO.testDriveNum}">${companyCarStyleVO.testDriveNum}</span></li>
            		<li class="single_test_drive_percent_79"><span title="${companyCarStyleVO.hopefulCustomer}">${companyCarStyleVO.hopefulCustomer}</span></li>
            		<li class="single_test_drive_percent_79"><span title="${companyCarStyleVO.drivePercent}">${companyCarStyleVO.drivePercent}</span></li>
            		
					<c:if test="${companyCarStyleVO.carStyleList != null}" >
						<c:forEach var="carStyleId" items="${companyCarStyleVO.carStyleList}" >
							<li class="single_test_drive_percent_79"><span title="${carStyleId}">${carStyleId}</span></li>
						</c:forEach>
					</c:if>
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
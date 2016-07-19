<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.domain.vo.TestDrive4StatVO"%>
<%
	TestDrive4StatVO vo = (TestDrive4StatVO)request.getAttribute("testDrive");
	String nowYear = DateTime.getYear();
	String chooseYear = "";
	if (vo.getYear() != null) {
		chooseYear = vo.getYear().toString();	
	} else {
		chooseYear=nowYear;
	}
	String lastYear= String.valueOf((Integer.parseInt(DateTime.getYear())-1));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
	var pageFormObj = document.getElementById("myPageForm");
  	function gotoLoadExcel(){
    	pageFormObj.action="<%=path%>/dealer/testDrive/outputSeason.vti?startIndex=${transMsg.startIndex}";
	    pageFormObj.submit();
	    pageFormObj.action="<%=path%>/dealer/testDrive/listSeason.vti";
    } 
</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/testDrive/listSeason.vti" method="get" >
<div class="bd_rt" id="audit">
	 <div class="bd_rt_nav clearfix">
		<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
			<span>当前位置：试驾明细  > 季度统计  </span>
		</c:if>
	 </div>
	<div class="audit_nav_c">
	 	<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
	 	分销中心
		<form:select path='testDrive.dealer.parentId' cssClass="select_condition" >
			<form:options items="${orgList}" itemLabel="name" itemValue="id"  htmlEscape="false" />
		</form:select>
		网络代码
		<form:input path="testDrive.dealer.dealerCode" maxlength="7" cssClass="ipt_txt"/>
		VIN
		<form:input path="testDrive.vehicle.vin" maxlength="17" cssClass="ipt_txt"/>
		车牌
		<form:input path="testDrive.vehicle.plateNumber" maxlength="7" cssClass="ipt_txt"/>
		试驾季度
		<select id="year" name="year"  class="select_condition">
		   	  <option value=<%=nowYear%>  <%=chooseYear.equals(nowYear) ? "selected='selected' ": "" %> > 
					<%=nowYear%>
			  </option> 
    			 
			 <option value=<%=lastYear%>  <%=chooseYear.equals(lastYear) ? "selected='selected' ": "" %> > 
					<%=lastYear%>
			  </option> 
		</select>
		
		<form:select path='testDrive.season' cssClass="select_condition">
			<form:options items="${testDriveSeasonList}"  itemLabel="optionText" itemValue="optionValue"  />
		</form:select>
		<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
			<ul class="table_hd clearfix" style="width:1360px;">
           		<li class="distributionSaleCenterName_80">分销中心</li>
           		<li class="regionProvinceId_Nick_50">省份</li>
           		<li class="regionCityId_Nick_50">城市</li>
           		<li class="dealerCode_70">网络代码</li>
           		<li class="cnName_240">经销商名称</li>
           		<li class="isKeyCity_Nick_80">经销商状态</li>
           		<li class="dealerType_Nick_70">网络形态</li>
           		<li class="carStyleId_Nick_200">车型</li>
           		<li class="vin_140">VIN</li>
           		<li class="plateNumber_75">车牌</li>
           		<li class="loopTotal_50">总次数</li>
           		<li class="mileageTotal_80">总里程[km]</li>
           		<li class="short_operator_150">操作</li>
			</ul>
			<c:if test="${testDriveVOList != null}" >
				<c:forEach var="vo" items="${testDriveVOList}" varStatus="statusObj">
					<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'style="width:1360px;">
		           		<li class="distributionSaleCenterName_80"><span title="${vo.saleCenterName}">${vo.saleCenterName}</span></li>
	            		<li class="regionProvinceId_Nick_50"><span title="${vo.provinceName}">${vo.provinceName}</span></li>
	            		<li class="regionCityId_Nick_50"><span title="${vo.cityName}">${vo.cityName}</span></li>
	            		<li class="dealerCode_70"><span title="${vo.dealerCode}">${vo.dealerCode}</span></li>
	            		<li class="cnName_240"><span title="${vo.companyName}">${vo.companyName}</span></li>
	            		<li class="isKeyCity_Nick_80"><span title="${vo.isKeyCityNick}">${vo.isKeyCityNick}</span></li>
	            		<li class="dealerType_Nick_70"><span title="${vo.dealerTypeNick}">${vo.dealerTypeNick}</span></li>
	            		<li class="carStyleId_Nick_200"><span title="${vo.carStyleName}">${vo.carStyleName}</span></li>
	            		<li class="vin_140"><span title="${vo.vin}">${vo.vin}</span></li>
	            		<li class="plateNumber_75"><span title="${vo.plateNumber}">${vo.plateNumber}</span></li>
		           		<li class="loopTotal_50"><span title="${vo.loopTotal}">${vo.loopTotal}</span></li>
		           		<li class="mileageTotal_80">
			           		<span title="<fmt:formatNumber type="number" maxFractionDigits="1" groupingUsed="false" value="${vo.mileageTotal/1000}" />">
			           			<fmt:formatNumber type="number" maxFractionDigits="1" groupingUsed="false" value="${vo.mileageTotal/1000}" />
			           		</span>
		           		</li>
		           		<li class="short_operator_150">
							<a href="<%=path%>/dealer/testDrive/listTestDriveDetail.vti?vinValue=${testDrive.vehicle.vin}						
							&plateNumberValue=${testDrive.vehicle.plateNumber}&dealerCode=${testDrive.dealer.dealerCode}
							&vehicleId=${vo.vehicleId}&lineId=${vo.lineId}&year=${testDrive.year}&season=${testDrive.season}&higherPage=${transMsg.startIndex}">查看明细</a>
		           		</li>
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
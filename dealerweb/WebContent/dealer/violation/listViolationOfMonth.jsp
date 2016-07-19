<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%
	 User currentUser = SessionManager.getCurrentUser(request);
	Violation v =	(Violation)request.getAttribute("violation");
	

	String nowYear =DateTime.getYear();
	String chooseYear="";
	if(v.getYear()!=null)
	{
		chooseYear = v.getYear().toString();	
	}else{
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
	pageFormObj.action="<%=path%>/dealer/violation/outputMonth.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/violation/listMonth.vti?startIndex=${transMsg.startIndex}";
    }
</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/violation/listMonth.vti" method="get" >
<div class="bd_rt" id="audit">
	
	<div class="bd_rt_nav clearfix">
		<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
			<span>当前位置：违规明细 > 月度统计 </span>
		</c:if>
	 </div>
	<div class="audit_nav_c">
			 <strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
			VIN：
			<form:input path="violation.vin" cssClass="ipt_txt"/>
			  车牌：
			<form:input path="violation.plateNumber" cssClass="ipt_txt"/>
			网络代码：
			<form:input path="violation.dealerCode" cssClass="ipt_txt"/>
			违规月份：
			<select id="year" name="year"  class="select_condition">
			   	 <option value=<%=nowYear%>  <%=chooseYear.equals(nowYear) ? "selected='selected' ": "" %>  > 
						<%=nowYear%>
				  </option> 
				 <option value=<%=lastYear%>  <%=chooseYear.equals(lastYear) ? "selected='selected' ": "" %>  > 
						<%=lastYear%>
				  </option> 
			</select>
			<form:select path='violation.month' cssClass="select_condition">
				<form:options items="${violationMonthList}"  itemLabel="optionText" itemValue="optionValue"  />
			</form:select>
		<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:1330px;">
					<li class="isKeyCity_Nick_80">经销商状态</li>
            		<li class="dealerType_Nick_70">网络形态</li>
            		<li class="dealerCode_70">网络代码</li>
            		<li class="distributionSaleCenterName_80">分销中心</li>
            		<li class="regionProvinceId_Nick_50">省份</li>
            		<li class="regionCityId_Nick_50">城市</li>
            		<li class="cnName_240">经销商名称</li>
            		<li class="plateNumber_75">车牌</li>
            		<li class="carStyleId_Nick_200">车型</li>
            		<li class="vin_140">VIN</li>
            		<li class="typeId_nick_70">违规类型</li>
            		<li class="testDriveNum_70">违规次数</li>
            		<li class="tiny_operator_100">操作</li>
				</ul>
			<c:if test="${violationList != null}" >
			<c:forEach var="violation1" items="${violationList}" varStatus="statusObj">
			<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:1330px">
            		 <li class="isKeyCity_Nick_80"><span title="${violation1.isKeyCity_Nick}">${violation1.isKeyCity_Nick}</span></li>
            		<li class="dealerType_Nick_70"><span title="${violation1.dealerType_Nick}">${violation1.dealerType_Nick}</span></li>
            		<li class="dealerCode_70"><span title="${violation1.dealerCode}">${violation1.dealerCode}</span></li>
            		<li class="distributionSaleCenterName_80"><span title="${violation1.fenxiao_center}">${violation1.fenxiao_center}</span></li>
            		<li class="regionProvinceId_Nick_50"><span title="${violation1.regionProvinceId_Nick}">${fn:substring(violation1.regionProvinceId_Nick,0,2)}</span></li>
            		<li class="regionCityId_Nick_50"><span title="${violation1.regionCityId_Nick}">${fn:substring(violation1.regionCityId_Nick,0,2)}</span></li>
            		<li class="cnName_240"><span title="${violation1.companyName}">${fn:substring(violation1.companyName,0,18)}</span></li>
            		<li class="plateNumber_75"><span title="${violation1.plateNumber}">${violation1.plateNumber}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${violation1.carStyleId_Nick}">${fn:substring(violation1.carStyleId_Nick,0,30)}</span></li>
            		<li class="vin_140"><span title="${violation1.vin}">${violation1.vin}</span></li>
            		<li class="typeId_nick_70"><span title="${violation1.typeId_nick}">${violation1.typeId_nick}</span></li>
            		<li class="testDriveNum_70"><span title="${violation1.sumNum}">${violation1.sumNum}</span></li>
            		<li class="tiny_operator_100">
            			<a href="<%=path%>/dealer/violation/detailMonth.vti?vehicleId=<c:out value="${violation1.vehicleId}" />
						&driveLineId=<c:out value="${violation1.driveLineId}"/>&year=<c:out value="${violation.year}" /> 
						&month=<c:out value="${violation.month}" />&companyName=<c:out value="${violation1.companyName}" />
						&typeId_nick=<c:out value="${violation1.typeId_nick}"/>&vin=<c:out value="${violation1.vin}" />
						&plateNumber=<c:out value="${violation1.plateNumber}" />
						&vinValue=<c:out value="${violation.vin}" />
						&plateNumberValue=<c:out value="${violation.plateNumber}" />
						&dealerCode=<c:out value="${violation.dealerCode}" />&carStyleId_Nick=<c:out value="${violation1.carStyleId_Nick}" /> ">查看明细</a>
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
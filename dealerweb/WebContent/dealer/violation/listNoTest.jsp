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
	pageFormObj.action="<%=path%>/dealer/violation/exportNoTest.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/violation/listNoTest.vti?startIndex=${transMsg.startIndex}";
    }
function queryViolation(){
	var violationYear=$('#violationYear').val();
	if((!(null==violationYear || violationYear.length<=0)) && violationYear.length!=4 && !validateNumber(violationYear)){
		alert("请输入有效年份（例如1970）");
    	return;
	}
	var violationWeek=$('#violationWeek').val();
	
	var dateType=0;
	if(!(null==violationWeek || violationWeek.length<=0)){
		dateType=1;
	}
	if(dateType==1 && (null==violationYear || violationYear.length<=0 ) ){
		alert("请输入有效年份（例如1970）");
    	return;
	}else{
		if(dateType==1){
			if(!validateNumber(violationWeek) || ((violationWeek>=55 || violationWeek<=0))){
				alert("请输入有效周（1-54）");
		    	return;
			}
		}
	}
	pageFormObj.action="<%=path%>/dealer/violation/listNoTest.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
}

function validateNumber(value)  {
	if(null==value || ""==value)
		return false;
	var re = /^[1-9]+[0-9]*]*$/;       
	if (!re.test(value)) {
		return false;       
	} 
	return true;
}

</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/violation/listNoTest.vti" method="get" >
<div class="bd_rt" id="audit">
	
	<div class="bd_rt_nav clearfix">
		<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
			<span>当前位置：违规明细 > 非活跃试驾</span>
		</c:if>
	 </div>
	<div class="audit_nav_c">
			 <strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
			 分销中心
			<select id="selectSaleCenterId" name="selectSaleCenterId" style="border:1px solid #666;">
				<c:forEach items="${orgList}" var="org" varStatus="num">
						<c:choose>
							<c:when test="${org.id==selectedSaleCenterId}" >
							    <option value="${org.id}" selected>${org.name}</option>
							</c:when>
							<c:otherwise>
								<option value="${org.id}">${org.name}</option>
							</c:otherwise>
	  					</c:choose>
				</c:forEach>
			</select>
			VIN<input id="vin" name="vin" value="${vin}" class="ipt_txt" />
			  车牌<input id="plateNumber" name="plateNumber" value="${plateNumber}" class="ipt_txt" />
			网络代码<input id="dealerCode" name="dealerCode" value="${dealerCode}" class="ipt_txt" />
			年<input id="violationYear" name="violationYear" value="${violationYear}" class="ipt_txt_short" />
			周<input id="violationWeek" name="violationWeek" value="${violationWeek}" class="ipt_txt_short" />
			<%-- 月：<input id="violationMonth" name="violationMonth" value="${violationMonth}" class="ipt_txt_short" />
			季：<input id="violationQuarter" name="violationQuarter" value="${violationQuarter}" class="ipt_txt_short" /> --%>
		<input type="button"  class="btn_submit" value="查询" onclick="queryViolation()"/>
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:1200px;">
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
            		<li class="testDriveNum_70">连续周数</li>
				</ul>
			<c:if test="${testNoDriveWeekList != null}" >
			<c:forEach var="testNoDriveWeek" items="${testNoDriveWeekList}" varStatus="statusObj">
			<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:1200px">
            		<li class="distributionSaleCenterName_80"><span title="${testNoDriveWeek.saleCenterName}">${testNoDriveWeek.saleCenterName}</span></li>
            		<li class="regionProvinceId_Nick_50"><span title="${testNoDriveWeek.provinceName}">${testNoDriveWeek.provinceName}</span></li>
            		<li class="regionCityId_Nick_50"><span title="${testNoDriveWeek.cityName}">${testNoDriveWeek.cityName}</span></li>
            		<li class="dealerCode_70"><span title="${testNoDriveWeek.dealerCode}">${testNoDriveWeek.dealerCode}</span></li>
            		<li class="cnName_240"><span title="${testNoDriveWeek.companyName}">${testNoDriveWeek.companyName}</span></li>
            		<li class="isKeyCity_Nick_80"><span title="${testNoDriveWeek.isKeyCityNick}">${testNoDriveWeek.isKeyCityNick}</span></li>
            		<li class="dealerType_Nick_70"><span title="${testNoDriveWeek.dealerTypeNick}">${testNoDriveWeek.dealerTypeNick}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${testNoDriveWeek.vehicleStyle}">${testNoDriveWeek.vehicleStyle}</span></li>
            		<li class="vin_140"><span title="${testNoDriveWeek.vin}">${testNoDriveWeek.vin}</span></li>
            		<li class="plateNumber_75"><span title="${testNoDriveWeek.plateNumber}">${testNoDriveWeek.plateNumber}</span></li>
            		<li class="testDriveNum_70"><span title="${testNoDriveWeek.sumNum}">${testNoDriveWeek.sumNum}</span></li>
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
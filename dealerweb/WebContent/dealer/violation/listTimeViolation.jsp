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
	pageFormObj.action="<%=path%>/dealer/violation/exportViolation.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/violation/listLineTimeViolation.vti?startIndex=${transMsg.startIndex}";
    }
function queryViolation(){
	if(validateDate()){
		pageFormObj.action="<%=path%>/dealer/violation/listLineTimeViolation.vti?startIndex=${transMsg.startIndex}";
	    pageFormObj.submit();
	}
}

function queryViolationDetail(vehicleId){
	if(validateDate()){
		var detailUrl="<%=path%>/dealer/violation/viewViolationDetail.vti";
		$("#vehicleId").val(vehicleId);
		$('#myPageIndex').val("${transMsg.startIndex}");
		pageFormObj.action=detailUrl;
	    pageFormObj.submit();
	}
}

function validateDate(){
	var violationYear=$('#violationYear').val();
	if((!(null==violationYear || violationYear.length<=0)) && violationYear.length!=4 && !validateNumber(violationYear)){
		alert("请输入有效年份（例如1970）");
    	return false;
	}
	var violationWeek=$('#violationWeek').val();
	var violationMonth=$('#violationMonth').val();
	var violationQuarter=$('#violationQuarter').val();
	var dateType=0;
	if(!(null==violationWeek || violationWeek.length<=0)){
		dateType=1;
	}else if(!(null==violationMonth || violationMonth.length<=0)){
		dateType=2;
	}else if(!(null==violationQuarter || violationQuarter.length<=0)){
		dateType=3;
	}
	if((dateType==1 || dateType==2 || dateType==3) && (null==violationYear || violationYear.length<=0 ) ){
		alert("请输入有效年份（例如1970）");
    	return false;
	}else{
		if(dateType==1){
			if(!validateNumber(violationWeek) || ((violationWeek>=55 || violationWeek<=0))){
				alert("请输入有效周（1-54）");
		    	return false;
			}
		}else if(dateType==2){
			if(!validateNumber(violationMonth) || ((violationMonth>=13 || violationMonth<=0))){
				alert("请输入有效月（1-12）");
		    	return false;
			}
		}else if(dateType==3){
			if(!validateNumber(violationQuarter) || ((violationQuarter>=5 || violationQuarter<=0))){
				alert("请输入有效季（1-4）");
		    	return false;
			}
		}
	}
	return true;
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
<form id="myPageForm" action="<%=path%>/dealer/violation/listLineTimeViolation.vti" method="get" >
<input type="hidden" id="violationType" name="violationType" value="2" />
<input type="hidden" id="myPageIndex" name="myPageIndex" value="${myPageIndex}" />
<input type="hidden" id="vehicleId" name="vehicleId" value="" />
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
		<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
			<span>当前位置：违规明细 > 时间违规</span>
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
			月<input id="violationMonth" name="violationMonth" value="${violationMonth}" class="ipt_txt_short" />
			季<input id="violationQuarter" name="violationQuarter" value="${violationQuarter}" class="ipt_txt_short" />
		<input type="button"  class="btn_submit" value="查询" onclick="queryViolation()"/>
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:1270px;">
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
            		<li class="testDriveNum_70">违规次数</li>
            		<li class="tiny_operator_100">操作</li>
				</ul>
			<c:if test="${violationList != null}" >
			<c:forEach var="violation" items="${violationList}" varStatus="statusObj">
			<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:1270px">
            		<li class="distributionSaleCenterName_80"><span title="${violation.saleCenterName}">${violation.saleCenterName}</span></li>
            		<li class="regionProvinceId_Nick_50"><span title="${violation.provinceName}">${violation.provinceName}</span></li>
            		<li class="regionCityId_Nick_50"><span title="${violation.cityName}">${violation.cityName}</span></li>
            		<li class="dealerCode_70"><span title="${violation.dealerCode}">${violation.dealerCode}</span></li>
            		<li class="cnName_240"><span title="${violation.companyName}">${violation.companyName}</span></li>
            		<li class="isKeyCity_Nick_80"><span title="${violation.isKeyCityNick}">${violation.isKeyCityNick}</span></li>
            		<li class="dealerType_Nick_70"><span title="${violation.dealerTypeNick}">${violation.dealerTypeNick}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${violation.vehicleStyle}">${violation.vehicleStyle}</span></li>
            		<li class="vin_140"><span title="${violation.vin}">${violation.vin}</span></li>
            		<li class="plateNumber_75"><span title="${violation.plateNumber}">${violation.plateNumber}</span></li>
            		<li class="testDriveNum_70"><span title="${violation.sumNum}">${violation.sumNum}</span></li>
            		<li class="tiny_operator_100">
            			<a  href="#" onClick="queryViolationDetail('<c:out value="${violation.vehicleId}" />');">查看明细</a>
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
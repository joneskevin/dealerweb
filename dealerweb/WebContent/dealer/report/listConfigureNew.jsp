<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.domain.vo.CompanyCarStyleVO"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
var pageFormObj = document.getElementById("myPageForm");
function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/dealer/report/outputConfigureInfoExcelNew.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/report/listConfigureNew.vti";
}

</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/report/listConfigureNew.vti" method="get" >
<div class="bd_rt" id="audit">
	
	<div class="bd_rt_nav clearfix">
		<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
			<span>当前位置：报表管理 > 配置报表 </span>
		</c:if>
	</div>
	<div class="audit_nav_c">
	 	<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
	 	 分销中心
		<form:select path='companyCarStyleVO.dealer.parentId' cssClass="select_condition" >
			<form:options items="${orgList}" itemLabel="name" itemValue="id"  htmlEscape="false" />
		</form:select>
	 	网络代码：
		<form:input path="companyCarStyleVO.dealer.dealerCode" cssClass="ipt_txt"/>
		网络形态
		<form:select path='companyCarStyleVO.dealer.dealerType' cssClass="select_condition" >
			<form:option value=" ">--请选择--</form:option>
			<form:options items="${dealerTypeList}" itemLabel="optionText" itemValue="optionValue" htmlEscape="false" />
		</form:select>
		配置周期(本年)：第
		<form:input path="companyCarStyleVO.week" maxlength="3" 
		 onkeyup="this.value=this.value.replace(/\D/g,'')" 
		 onafterpaste="this.value=this.value.replace(/\D/g,'')"
		 cssClass="ipt_txt"/>周
		<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
			<ul class="table_hd clearfix"style="min-width:1280px;">
				<li class="distributionSaleCenterName_80">分销中心</li>
           		<li class="regionProvinceId_Nick_50">省份</li>
           		<li class="regionCityId_Nick_50">城市</li>
           		<li class="dealerCode_70">网络代码</li>
           		<li class="cnName_240">经销商名称</li>
				<li class="isKeyCity_Nick_80">经销商状态</li>
           		<li class="dealerType_Nick_70">网络形态</li>
           		<li class="carStyleId_Nick_200">车型</li>
            	<li class="configureNum_70">配置数量</li>
            	<li class="parentCode_100">一级网点代码</li>
            	<li class="parentCodeName_240">一级网点名称</li>
			</ul>
			<c:if test="${configureInfoList != null}" >
				<c:forEach var="vo" items="${configureInfoList}" varStatus="statusObj">
					<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="min-width:1280px;">
						<li class="distributionSaleCenterName_80"><span title="${vo.saleCenterName}">${vo.saleCenterName}</span></li>
		           		<li class="regionProvinceId_Nick_50"><span title="${vo.provinceName}">${vo.provinceName}</span></li>
		           		<li class="regionCityId_Nick_50"><span title="${vo.cityName}">${vo.cityName}</span></li>
		           		<li class="dealerCode_70"><span title="${vo.dealerCode}">${vo.dealerCode}</span></li>
		           		<li class="cnName_240"><span title="${vo.companyName}">${vo.companyName}</span></li>
		           	    <li class="isKeyCity_Nick_80"><span title="${vo.isKeyCityNick}">${vo.isKeyCityNick}</span></li>
		           		<li class="dealerType_Nick_70"><span title="${vo.dealerTypeNick}">${vo.dealerTypeNick}</span></li>
		           		<li class="carStyleId_Nick_200"><span title="${vo.carStyleName}">${vo.carStyleName}</span></li>
		           		<li class="configureNum_70"><span title="${vo.realityConfigCount}">${vo.realityConfigCount}</span></li>
		           		<li class="parentCode_100"><span title="${vo.parentDealerCode}">${vo.parentDealerCode}</span></li>
		            	<li class="parentCodeName_240"><span title="${vo.parentCompanyName}">${vo.parentCompanyName}</span></li>
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
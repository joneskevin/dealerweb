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
	pageFormObj.action="<%=path%>/dealer/report/outputFillingProposalExcel.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/report/listFillingProposal.vti?startIndex=${transMsg.startIndex}";
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/report/listFillingProposal.vti" method="get" >
<div class="bd_rt" id="audit">
	
	<div class="bd_rt_nav clearfix">
		<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
			<span>当前位置：报备报表 >报备查询  </span>
		</c:if>
	 </div>
	<div class="audit_nav_c">
	 	<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
	 	  报备类型:
		<form:select path='companyFilingProposalVO.type'  cssClass="select_condition">
			<form:options items="${filingProposalList}"  itemLabel="optionText" itemValue="optionValue"  />
		</form:select>
		车牌：
		<form:input path="companyFilingProposalVO.plateNumber" cssClass="ipt_txt"/>
		网络代码:
		<form:input path="companyFilingProposalVO.id" cssClass="ipt_txt"/>
		VIN：
		<form:input path="companyFilingProposalVO.vin" cssClass="ipt_txt"/>
		 报备时间
	 	<input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${companyFilingProposalVO.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
			-
		<input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" 	value="<fmt:formatDate value="${companyFilingProposalVO.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
		<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:1350px;">
					<li class="li_80">经销商状态</li>
            		<li class="li_60">网络形态</li>
            		<li class="li_70">网络代码</li>
            		<li class="li_120">分销中心</li>
            		<li class="li_50">省份</li>
            		<li class="li_50">城市</li>
            		<li class="li_240">经销商名称</li>
             		<li class="li_80">车牌</li>
            		<li class="li_120">车型</li>
            		<li class="li_140">VIN</li> 
            		<li class="li_130">开始时间</li>
            		<li class="li_130">结束时间</li>
				</ul>
				<c:if test="${companyFilingProposalVOList != null}" >
				<c:forEach var="companyFilingProposalVO" items="${companyFilingProposalVOList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'style="width:1350px;">
            		<li class="li_80"><span title="${companyFilingProposalVO.isKeyCity_Nick}">${companyFilingProposalVO.isKeyCity_Nick}</span></li>
            		<li class="li_60"><span title="${companyFilingProposalVO.dealerType_Nick}">${companyFilingProposalVO.dealerType_Nick}</span></li>
            		<li class="li_70"><span title="${companyFilingProposalVO.dealerCode}">${companyFilingProposalVO.dealerCode}</span></li>
            		<li class="li_120"><span title="${companyFilingProposalVO.fenxiao_center_nick}">${companyFilingProposalVO.fenxiao_center_nick}</span></li>
            		<li class="li_50"><span title="${companyFilingProposalVO.regionProvinceId_Nick}">${companyFilingProposalVO.regionProvinceId_Nick}</span></li>
            		<li class="li_50"><span title="${companyFilingProposalVO.regionCityId_Nick}">${companyFilingProposalVO.regionCityId_Nick}</span></li>
            		<li class="li_240"><span title="${companyFilingProposalVO.name}">${companyFilingProposalVO.name}</span></li>
            		<li class="li_80"><span title="${companyFilingProposalVO.plateNumber}">${companyFilingProposalVO.plateNumber}</span></li>
            		<li class="li_120"><span title="${companyFilingProposalVO.carStyleId_Nick}">${companyFilingProposalVO.carStyleId_Nick}</span></li>
            		<li class="li_140"><span title="${companyFilingProposalVO.vin}">${companyFilingProposalVO.vin}</span></li>
            		<li class="li_130"><span title="<fmt:formatDate value="${companyFilingProposalVO.start_Time}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${companyFilingProposalVO.start_Time}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            		<li class="li_130"><span title="<fmt:formatDate value="${companyFilingProposalVO.end_Time}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${companyFilingProposalVO.end_Time}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
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
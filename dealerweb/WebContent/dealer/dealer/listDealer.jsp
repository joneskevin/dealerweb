<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
$().ready(function(){

});

function selectAll(checkeAllObj, checkboxName) {
	if ($(checkeAllObj).is('.active')) {
		$(checkeAllObj).removeClass("active");
	} else {
		$(checkeAllObj).addClass("active");
	}
	$(".checkbox").each(function(index,domEle){
		if ($(this).is('.active')) {
			$(this).removeClass("active");
		} else {
			$(this).addClass("active");
		}
	});
}

var pageFormObj = document.getElementById("myPageForm");
function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/base/dealer/exportDealerExcel.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/base/dealer/search.vti?startIndex=${transMsg.startIndex}";
}

function comfirmSubmit(targetUrl,message){
	var pageFormObj = document.getElementById("myPageForm");
	if (confirm(message)) {
	  	 targetUrl += "&random=" + Math.random();
		 $.ajax({
		 url: targetUrl,
   		  type: 'GET',
		      dataType: 'json',
		      timeout: 10000,
		      error: function(){
		         alert('系统异常');
		      },
		      success: function(responseData){
		      if (responseData.code == 1) {
		    	 pageFormObj.action="<%=path%>/base/dealer/search.vti";
		    	 var startIndex = "${transMsg.startIndex}";
		    	 if (startIndex != null && startIndex !="") {
		    	 	$("#startIndex").val(startIndex);
		    	 }
		    	 pageFormObj.submit();
		         
		     } else {
		        jAlert(responseData.message);
		     }
		    }
		 });
	} 
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/base/dealer/search.vti" method="get" >
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：运营管理  > 经销商管理 </span>
		</c:if>
	</div>
	<div class="audit_nav_c">
		<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a>
		<a href="javascript:openWindow('<%=path%>/base/dealer/displayAdd.vti?startIndex=${transMsg.startIndex}', 660, 450, '添加经销商')" >添加经销商</a>
		 </span></strong>
		所属单位
		<form:select path='company.parentId' cssClass="select_condition" >
			<form:options items="${orgList}" itemLabel="name" itemValue="id"  htmlEscape="false" />
		</form:select>
		网络代码
		<form:input path="company.dealerCode" maxlength="12" cssClass="ipt_txt"/>
		经销商名称
		<form:input path="company.cnName" cssClass="ipt_txt"/>
		经销商状态
		<form:select path='company.isKeyCity' cssClass="select_condition" >
			<form:option value=" ">--请选择--</form:option>
			<form:options items="${isKeyCityList}" itemLabel="optionText" itemValue="optionValue"  htmlEscape="false" />
		</form:select>
		网络形态
		<form:select path='company.dealerType' cssClass="select_condition" >
			<form:option value=" ">--请选择--</form:option>
			<form:options items="${dealerTypeList}" itemLabel="optionText" itemValue="optionValue" htmlEscape="false" />
		</form:select>
		状态
		<form:select path='company.deletionFlag' cssClass="select_condition" >
			<form:option value=" ">--请选择--</form:option>
			<form:options items="${dealerStatusList}" itemLabel="optionText" itemValue="optionValue" htmlEscape="false" />
		</form:select>
		
		<input type="submit"  class="btn_submit" value="查 询" />
		</div>	
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:1630px;">
					<li class="li_150">分销中心</li>
					<li class="li_70">省份</li>
					<li class="li_70">城市</li>
					<li class="li_100">网络代码</li>
					<li class="cnName_240">经销商名称</li>
					<li class="li_150">经销商状态</li>
					<li class="li_100">网络形态</li>
					<li class="time_150">入网时间</li>
					<li class="li_90">状态</li>
					<li class="li_90">退网时间</li>
					<li class="li_400">操作</li>
				</ul>
			<c:if test="${companyList != null}" >
				<c:forEach var="company" items="${companyList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:1630px;">
<%-- 					<li class="li_50"><span class="checkbox text_over" name="checkedIds" value="${company.id}">checkbox</span></li> --%>
		
            		<li class="li_150"><span title="${company.parentName}">${company.parentName}</span></li>
            		<li class="li_70"><span title="${company.regionProvinceId_Nick}">${company.regionProvinceId_Nick}</span></li>
            		<li class="li_70"><span title="${company.regionCityId_Nick}">${company.regionCityId_Nick}</span></li>
            		<li class="li_100"><span title="${company.dealerCode}">${company.dealerCode}</span></li>
            		<li class="cnName_240"><span title="${company.cnName}">${company.cnName}</span></li>
            		<li class="li_150"><span title="${company.isKeyCity_Nick}">${company.isKeyCity_Nick}</span></li>
            		<li class="li_100"><span title="${company.dealerType_Nick}">${company.dealerType_Nick}</span></li>
            		<li class="time_150"><span title="<fmt:formatDate value="${company.creationTime}" type="both" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${company.creationTime}" type="both" pattern="yyyy-MM-dd"/></span></li>
            		<li class="li_90"><span title="${company.deletionFlag}">
	            		<c:if test="${company.deletionFlag == 0}" >
	            			正常
	            		</c:if>
	            		<c:if test="${company.deletionFlag == 1}" >
	            			退网
	            		</c:if>
	            		</span>
            		</li>
            		<li class="li_90"><span title="<fmt:formatDate value="${company.deletionTime}" type="both" pattern="yyyy-MM-dd"/>">
            			<fmt:formatDate value="${company.deletionTime}" type="both" pattern="yyyy-MM-dd"/>
            		</li>
            		<li class="li_400">
						 <a href="javascript:openWindow('<%=path%>/base/dealer/view.vti?companyId=<c:out value="${company.id}" />&startIndex=${transMsg.startIndex}', 660, 450, '经销商信息')">查看</a>
				         <a href="javascript:openWindow('<%=path%>/base/dealer/displayEdit.vti?companyId=<c:out value="${company.id}" />&startIndex=${transMsg.startIndex}', 660, 450, '编辑经销商')">编辑</a>
				         <a href="javascript:openWindow('<%=path%>/base/dealer/displayConfigCarStyle.vti?companyId=<c:out value="${company.id}" />&startIndex=${transMsg.startIndex}', 530, 450, '配置车型')">配置车型</a>
				         <a href="javascript:openWindow('<%=path%>/base/dealer/setDealerCurrentPositionView.vti?companyId=<c:out value="${company.id}" />', 1000, 460, '设置经销商位置')">设置经销商位置</a>
				         <a  href="#" onClick="javascript:comfirmDelete('<%=path%>/base/dealer/delete.vti?companyId=<c:out value="${company.id}" />&startIndex=${transMsg.startIndex}','确定要删除此记录吗？');">删除</a>
				         <c:if test="${company.deletionFlag == 0}" >
	            			<a  href="#" onClick="javascript:comfirmSubmit('<%=path%>/base/dealer/retreat.vti?companyId=<c:out value="${company.id}" />','确定此经销商退网吗？');">退网</a>
	            		 </c:if>
	            		 <c:if test="${company.deletionFlag == 1}" >
	            			<a  href="#" onClick="javascript:comfirmSubmit('<%=path%>/base/dealer/rollbackRetreat.vti?companyId=<c:out value="${company.id}" />','确定恢复此经销商状态吗？');">恢复</a>
	            		 </c:if>
            			<a  href="#" onClick="javascript:comfirmSubmit('<%=path%>/dealer/survey/deleteSurveyResult.vti?companyId=${company.id}','确定删除配置调研吗？');">删除调研</a>
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
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%
	User leftMenuCurrentUser = SessionManager.getCurrentUser(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>

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

function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/dealer/installationPlan/exportDesignateOrder.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/installationPlan/listDesignateOrder.vti?startIndex=${transMsg.startIndex}";
}

function comfirmDelete(targetUrl){
	var pageFormObj = document.getElementById("myPageForm");
	if (confirm("确定删除吗？")) {
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
		    	 pageFormObj.action="<%=path%>/dealer/installationPlan/listDesignateOrder.vti";
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
<form id="myPageForm" action="<%=path%>/dealer/installationPlan/listDesignateOrder.vti" method="get" >
<input type="hidden" name="proposalType" value="${proposalCompanyVehcileInfo.proposal.type}" />
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：系统设置  > 指派订单</span>
		</c:if>
	</div>
	<div class="audit_nav_c">
	   <strong><span>
	   <a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a>
	   <a href="javascript:openWindow('<%=path%>/dealer/installationPlan/displayImportDesignateOrder.vti?startIndex=${transMsg.startIndex}', 660, 220, '导入指派订单')" >导入指派订单</a>
	   </span></strong>
	 	网络代码
		<form:input path="designateOrder.dealerCode" cssClass="ipt_txt"/>
		<input type="submit"  class="btn_submit" value="查询" /> 
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:1300px;">
					<li class="dealerCode_70">网络代码</li>
            		<li class="cnName_240">经销商名称</li>
					<li class="vin_140">换装前VIN</li>
					<li class="carStyleId_Nick_200">换装前车型</li>
					<li class="carStyleId_Nick_200">换装后车型</li>
					<li class="time_150">创建时间</li>
					<li class="tiny_operator_100">操作</li>
				</ul>
			<c:if test="${designateOrderList != null}" >
			<c:forEach var="vo" items="${designateOrderList}" varStatus="statusObj">
			<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:1300px">
            		<li class="dealerCode_70"><span title="${vo.dealerCode}">${vo.dealerCode}</span></li>
            		<li class="cnName_240"><span title="${vo.companyName}">${vo.companyName}</span></li>
            		<li class="vin_140"><span title="${vo.vin}">${vo.vin}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${vo.testDriveCarStyleName}">${vo.testDriveCarStyleName}</span></li>
            		<li class=carStyleId_Nick_200><span title="${vo.dessCarStyleName}">${vo.dessCarStyleName}</span></li>
            		 <li class="time_150"><span title="<fmt:formatDate value="${vo.creationTime}"
											type="both" pattern="yyyy-MM-dd HH:mm:ss" />"><fmt:formatDate value="${vo.creationTime}"
											type="both" pattern="yyyy-MM-dd HH:mm:ss" /></span>
                     </li>
            		<li class="tiny_operator_100">
            		<a href="#" onClick="javascript:comfirmDelete('<%=path%>/dealer/installationPlan/deleteDesignateOrder.vti?designateOrderId=${vo.id}&startIndex=${transMsg.startIndex}');">删除</a>
            		</li>
			</ul>
		</c:forEach>
		</c:if>
	</div><!--/table_list-->
	</aside>
	<div class="bd_rt_ft">
			<jsp:include page="/pub/navigate/navigation4dealer.jsp" flush="true"/>
	</div>
</div>
</form>
</body>
</html>
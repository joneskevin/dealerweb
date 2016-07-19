<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
function comfirmDelete(targetUrl){
	var pageFormObj = document.getElementById("myPageForm");
	if (confirm("确定要删除此记录吗？")) {
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
		    	 pageFormObj.action="<%=path%>/dealer/box/list.vti";
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

function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/dealer/box/exportBox.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/box/list.vti?startIndex=${transMsg.startIndex}";
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/box/list.vti" method="get" >
<div class="bd_rt" id="audit">
	
	<div class="bd_rt_nav clearfix">
		<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
			<span>当前位置：设备管理  > 设备维护  </span>
		</c:if>
	 </div>
	<div class="audit_nav_c">
		<span class="audit_nav_c_span">
			<a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a>
			<%-- <a href="javascript:openWindow('<%=path%>/dealer/box/displayAdd.vti', 660, 300, '添加设备')">添加设备</a> --%>
	   	</span>
		网络代码
		<form:input path="box.dealerCode" cssClass="ipt_txt"/>
		设备号
		<form:input path="box.uniqueId" cssClass="ipt_txt"/>
	 	SIM卡号
		<form:input path="box.simId" cssClass="ipt_txt"/>
		<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="min-width:1540px;">
					<li class="distributionSaleCenterName_80">分销中心</li>
					<li class="dealerCode_70">网络代码</li>
            		<li class="cnName_240">经销商名称</li>
            		<li class="li_120">设备号</li>
            		<li class="li_200">SIM卡号</li>
            		<li class="carStyleId_Nick_200">车型</li>
            		<li class="vin_140">VIN</li>
            		<li class="plateNumber_75">车牌</li>
            		<li class="li_100">状态</li>
            		<li class="li_200">操作</li>
				</ul>
			<c:if test="${boxList != null}" >
				<c:forEach var="box" items="${boxList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="min-width:1540px;">
					<li class="distributionSaleCenterName_80"><span title="${box.saleCenterName}">${box.saleCenterName}</span></li>
            		<li class="dealerCode_70"><span title="${box.dealerCode}">${box.dealerCode}</span></li>
            		<li class="cnName_240"><span title="${box.companyName}">${box.companyName}</span></li>
            		<li class="li_120"><span title="${box.uniqueId}">${box.uniqueId}</span></li>
            		<li class="li_200"><span title="${box.simId}">${box.simId}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${box.carStyleName}">${box.carStyleName}</span></li>
            		<li class="vin_140"><span title="${box.vin}">${box.vin}</span></li>
            		<li class="plateNumber_75"><span title="${box.plateNumber}">${box.plateNumber}</span></li>
            		<li class="li_100"><span title="${box.statusNick}">${box.statusNick}</span></li>
            		<li class="li_200">
	            		<a href="javascript:openWindow('<%=path%>/dealer/box/view.vti?boxId=<c:out value="${box.id}" />&startIndex=${transMsg.startIndex}', 660, 300, '设备信息')">查看</a>
						<%-- <a href="javascript:openWindow('<%=path%>/dealer/box/displayEdit.vti?id=${box.id}&startIndex=${transMsg.startIndex}', 660, 300, '编辑信息')">编辑</a> --%>
						<c:set var="unbindFlag" value="<%=String.valueOf(GlobalConstant.TRUE)%>"/>
						<%-- <c:if test="${box.unbindFlag == unbindFlag}">
							<a href="javascript:openWindow('<%=path%>/dealer/box/displayUnbind.vti?id=${box.id}&startIndex=${transMsg.startIndex}', 660, 300, '解绑信息')">解绑</a>
						</c:if> --%>
						
					 <%-- <a  href="#" onClick="javascript:comfirmDelete('<%=path%>/dealer/box/delete.vti?boxId=<c:out value="${box.id}" />&startIndex=${transMsg.startIndex}');">删除</a> --%>
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
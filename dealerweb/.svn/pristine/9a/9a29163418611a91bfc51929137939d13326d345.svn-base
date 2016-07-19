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
	pageFormObj.action="<%=path%>/dealer/installationPlan/exportCarStyle.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/installationPlan/listCarStyle.vti?startIndex=${transMsg.startIndex}";
}

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
		    	 pageFormObj.action="<%=path%>/dealer/installationPlan/listCarStyle.vti";
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
<form id="myPageForm" action="<%=path%>/dealer/installationPlan/listCarStyle.vti" method="get" >
<input type="hidden" name="proposalType" value="${proposalCompanyVehcileInfo.proposal.type}" />
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：车辆更新  > 车型列表</span>
		</c:if>
	</div>
	<div class="audit_nav_c">
	   <strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a>
	   <a href="javascript:openWindow('<%=path%>/dealer/installationPlan/displayAddCarStyle.vti?startIndex=${transMsg.startIndex}', 660, 220, '新增车型')" >新增车型</a>
	   </span></strong>
		 车系
		<form:select path='carStyle.seriesId' cssClass="select_condition" >
				<form:option value="">--请选择--</form:option>
				<form:options items="${carSeriesList}" itemLabel="name" itemValue="id" htmlEscape="false" />
		</form:select>
		<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:1200px;">
					<li class="carStyleId_Nick_200">车型ID</li>
					<li class="carStyleId_Nick_200">车型名称</li>
					<li class="carStyleId_Nick_200">车系ID</li>
					<li class="carStyleId_Nick_200">车系名称</li>
					<li class="short_operator_150">操作</li>
				</ul>
			<c:if test="${carStyleList != null}" >
			<c:forEach var="vo" items="${carStyleList}" varStatus="statusObj">
			<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:1200px">
            		<li class="carStyleId_Nick_200"><span title="${vo.id}">${vo.id}</span></li>
            		<li class=carStyleId_Nick_200><span title="${vo.name}${' '}${vo.yearType}">${vo.name}${' '}${vo.yearType}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${vo.seriesId}">${vo.seriesId}</span></li>
            		<li class=carStyleId_Nick_200><span title="${vo.seriesName}">${vo.seriesName}</span></li>
            		<li class="short_operator_150">
					 	<a href="javascript:openWindow('<%=path%>/dealer/installationPlan/displayEditCarStyle.vti?carStyleId=${vo.id}&startIndex=${transMsg.startIndex}', 660, 200, '编辑车型')">编辑</a>
						<a href="#" onClick="javascript:comfirmDelete('<%=path%>/dealer/installationPlan/deleteCarStyle.vti?carStyleId=${vo.id}&startIndex=${transMsg.startIndex}');">删除</a>
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
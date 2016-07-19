<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
	
<script type="text/javascript">
  function editLog(id){
		if(!window.confirm('您确定已处理完成吗？')){
          return;
        }
		var pageFormObj = document.getElementById("myPageForm");
		var targetUrl = "<%=request.getContextPath()%>/admin/testDrive/editLog.vti";
		var idTxt = document.getElementById("id");
		idTxt.value=id;
		pageFormObj.action=targetUrl;
	    pageFormObj.submit();
}
  
  var pageFormObj = document.getElementById("myPageForm");
  function gotoLoadExcel(){
		pageFormObj.action="<%=path%>/admin/testDrive/exportLog.vti";
	    pageFormObj.submit();
	    pageFormObj.action="<%=path%>/admin/testDrive/listLog.vti";
  }
</script>
</head>

<body>
<form id="myPageForm" action="<%=path%>/admin/testDrive/listLog.vti" method="get" >
<input type="hidden" name="proposalType" value="${proposalCompanyVehcileInfo.proposal.type}" />
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：运营管理  > 异常日志查询</span>
		</c:if>
	</div>
	<div class="audit_nav_c">
	<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
	<form:input path="taskMessageLogVo.id" maxlength="17" cssClass="ipt_txt" cssStyle="display:none;"  />
	VIN
	<form:input path="taskMessageLogVo.vin" maxlength="17" cssClass="ipt_txt" />
	起始时间
	<form:input path="taskMessageLogVo.startDateNick"  cssClass="ipt_txt" name="startTime"  id="txtStartTime" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" />
	结束时间
	<form:input path="taskMessageLogVo.endDateNick"  cssClass="ipt_txt" name="endTime"  id="txtEndTime" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" />
	异常类型
	<form:select path="taskMessageLogVo.logCode" id="testDriveStauts">
		<form:option value="">全部</form:option>
		<form:option value="10003">用户身份验证错误</form:option>
		<form:option value="20005">GPS缺失 </form:option>
	</form:select>
	<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
			<ul class="table_hd clearfix" style="width:2210px;">
			    <li class="regionProvinceId_Nick_50">ID</li>
           		<li class="time_150">VIN</li>
           		<li class="time_150">设备号</li>
           		<li class="time_150">创建时间</li>
           		<li class="time_150">状态</li>
           		<li class="time_150">起始时间</li>
				<li class="time_150">结束时间</li>	
				<li class="time_150">累计</li>	
				<li style="width:50px;">操作</li>
			</ul>
			<c:if test="${logList != null}" >
				<c:forEach var="vo" items="${logList}" varStatus="statusObj">
					<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:2210px;">
	            		<li class="regionProvinceId_Nick_50"><span title="${vo.id}">${vo.id}</span></li>	            		
	            		<li class="time_150"><span title="${vo.vin}">${vo.vin}</span></li>
	            		<li class="time_150"><span title="${vo.serialNumber}">${vo.serialNumber}</span></li>
	            		<li class="time_150"><span title="${vo.curtime}">${vo.curtime}</span></li>
	            		<li class="time_150"><span title="${vo.logCode}">${vo.logCode}</span></li>	            		
	            		<li class="time_150"><span title="${vo.startDate}">${vo.startDate}</span></li>
	            		<li class="time_150"><span title="${vo.endDate}">${vo.endDate}</span></li>
	            		<li class="time_150"><span title="${vo.counter}">${vo.counter}</span></li>
	            		<li style="width:50px;">
	            			<a href="javascript:editLog(${vo.id})">处理</a>
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
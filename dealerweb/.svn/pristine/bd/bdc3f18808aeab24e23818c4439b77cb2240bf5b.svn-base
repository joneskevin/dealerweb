<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>


<body>
<form id="myPageForm" action="<%=path%>/admin/testDrive/listTask.vti" method="get" >
<input type="hidden" name="proposalType" value="${proposalCompanyVehcileInfo.proposal.type}" />
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：运营管理  > 重跑任务查询</span>
		</c:if>
	</div>
	<div class="audit_nav_c">
	任务ID
	<form:input path="taskMessageVo.id" maxlength="17" cssClass="ipt_txt" />
	创建起始时间
	<form:input path="taskMessageVo.createStartTimeNick"  cssClass="ipt_txt" name="startTime"  id="txtStartTime" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" />
	创建结束时间
	<form:input path="taskMessageVo.createEndTimeNick"  cssClass="ipt_txt" name="startTime"  id="txtStartTime" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" />
	VIN
	<form:input path="taskMessageVo.vin" maxlength="17" cssClass="ipt_txt" />
	起始时间
	<form:input path="taskMessageVo.startTimeNick"  cssClass="ipt_txt" name="startTime"  id="txtStartTime" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" />
	结束时间
	<form:input path="taskMessageVo.endTimeNick"  cssClass="ipt_txt" name="endTime"  id="txtEndTime" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" />
	状态
	<form:select path="taskMessageVo.status" id="testDriveStauts">
		<form:option value="">全部</form:option>
		<form:option value="10">准备运行</form:option>
		<form:option value="20">正在运行</form:option>
		<form:option value="30">运行失败 </form:option>
		<form:option value="40">运行完成</form:option>
		<form:option value="50">运行错误 </form:option>
	</form:select>
	<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
			<ul class="table_hd clearfix" style="width:2210px;">
           		<li class="regionProvinceId_Nick_50">任务ID</li>
           		<li class="time_150">创建时间</li>
           		<li class="dealerType_Nick_70">完成进度</li>
           		<li class="dealerType_Nick_70">持续时间(s)</li>
           		<li class="time_150">预计完成所需时间(s)</li>
           		<li class="dealerType_Nick_70">状态</li>
           		<li class="time_150">完成时间</li>
           		<li class="time_150">起始时间</li>
				<li class="time_150">结束时间</li>
				<li class="vin_140">VIN</li>
				
			</ul>
			<c:if test="${taskList != null}" >
				<c:forEach var="vo" items="${taskList}" varStatus="statusObj">
					<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:2210px;">
	            		<li class="regionProvinceId_Nick_50"><span title="${vo.id}">${vo.id}</span></li>
	            		<li class="time_150"><span title="${vo.createTime}">${vo.createTime}</span></li>
	            		<li class="dealerType_Nick_70"><span title="${vo.completeRate}">${vo.completeRate}</span></li>	            		
	            		<li class="dealerType_Nick_70"><span title="${vo.durationTime}">${vo.durationTime}</span></li>
	            		<li class="time_150"><span title="${vo.completeEstimate}">${vo.completeEstimate}</span></li>
	            		<li class="dealerType_Nick_70"><span title="${vo.status}">${vo.status}</span></li>
	            		<li class="time_150"><span title="${vo.completeTime}">${vo.completeTime}</span></li>
	            		<li class="vin_140"><span title="${vo.startTime}">${vo.startTime}</span></li>
	            		<li class="time_150"><span title="${vo.endTime}">${vo.endTime}</span></li>
	            		<li class="time_150"><span title="${vo.vin}">${vo.vin}</span></li>
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
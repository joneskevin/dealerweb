<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp" %>
<%@ include file="/dealer/include/meta.jsp" %>
<%
	User leftMenuCurrentUser = SessionManager.getCurrentUser(request);
%>
<body>
<div class="bd_lf" id="bd_id">
	<ul>
		<% if (null != leftMenuCurrentUser && null != leftMenuCurrentUser.getUserRight() && leftMenuCurrentUser.getUserRight().isRightVehicleManagement()) { %>
		<li><strong>车辆信息</strong>
			<ol>
				<% if (leftMenuCurrentUser.getUserRight().isRightListVehicleAll()) { %>
					<li><a href="<%=path%>/dealer/vehicle/list.vti?random=" + Math.random()  target="iframe_right">车辆列表</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightHistoryVehicleAll()) { %>
					<li><a href="<%=path%>/dealer/vehicle/listHistory.vti?random=" + Math.random()  target="iframe_right">历史车辆</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightVehicleRebateAll()) { %>
					<li><a href="<%=path%>/dealer/vehicle/listVehicleRebate.vti?random=" + Math.random()  target="iframe_right">车辆返利</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightVehicleMaintainAll()) { %>
					<li><a href="<%=path%>/dealer/vehicle/listMaintainVehicle.vti?random=" + Math.random()  target="iframe_right">车辆维护</a></li>
				<% } %>
				
				 <% if (leftMenuCurrentUser.getUserRight().isRightMonitorVehicleObdAll()) { %>
       				<li><a href="<%=path%>/dealer/vehicle/listMonitorVehicleObd.vti?random=" + Math.random() target="iframe_right">车辆OBD监控</a></li>
      			 <% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightRealtimeMonitorAll()) { %>
					<li><a href="<%=path%>/dealer/monitor/home.vti?random=" + Math.random()  target="iframe_right">实时监控</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightTrackPlayBackAll()) {%>
					<li><a href="<%=path%>/dealer/track/home.vti?userMenu=1"  target="iframe_right">轨迹回放</a></li>
				<% } %>
			</ol>
		</li>
		<% } %>
		
		<% if (leftMenuCurrentUser.getUserRight().isRightVehicleUpdateManagement()) { %>
		<li><strong>车辆更新</strong>
			<ol>
				<% if (leftMenuCurrentUser.getUserRight().isRightReplaceProposalView()) { %>
					<li><a href="<%=path%>/dealer/proposal/listReplace.vti?random=" + Math.random() target="iframe_right">车辆换装</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightInstallationProposalView()) { %>
					<li><a href="<%=path%>/dealer/proposal/search.vti?seriesId=9&random=" + Math.random() target="iframe_right">凌渡新装</a></li>
<%-- 					<li><a href="<%=path%>/dealer/proposal/search.vti?seriesId=14&random=" + Math.random() target="iframe_right">夏朗新装</a></li> --%>
					<li><a href="<%=path%>/dealer/proposal/search.vti?seriesId=15&random=" + Math.random() target="iframe_right">Cross Polo新装</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightDemolitionVehicle()) { %>
					<li><a href="<%=path%>/dealer/proposal/displayDemolitionVehicle.vti?random=" + Math.random() target="iframe_right">车辆拆除</a></li>
				<% } %>
				<% if (leftMenuCurrentUser.getUserRight().isRightInstallationPlan()) { %>
					<li><a href="<%=path%>/dealer/installationPlan/listCarSeries.vti?random=" + Math.random()  target="iframe_right">车系列表</a></li>
					<li><a href="<%=path%>/dealer/installationPlan/listCarStyle.vti?random=" + Math.random()  target="iframe_right">车型列表</a></li>
					<li><a href="<%=path%>/dealer/installationPlan/listDesignateOrder.vti?random=" + Math.random()  target="iframe_right">指派订单</a></li>
					<li><a href="<%=path%>/dealer/installationPlan/listInstallationPlan.vti?random=" + Math.random()  target="iframe_right">换装拆除计划</a></li>
				<% } %>
			</ol>
		</li>
		<% } %>
		
		<% if (leftMenuCurrentUser.getUserRight().isRightFilingManagement()) { %>
		<li class="active"><strong>报备管理</strong>
			<ol>
				<% if (leftMenuCurrentUser.getUserRight().isRightFilingOutAll()) { %>
					<li><a href="<%=path%>/dealer/filingProposalManager/filingProposalMarketList.vti?random=" + Math.random() target="iframe_right">市场活动</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightFilingMaintainAll()) { %>
					<li><a href="<%=path%>/dealer/filingProposalManager/filingProposalRepairList.vti?random=" + Math.random() target="iframe_right">事故维修</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightFilingApprovalAll()) { %>
					<li><a href="<%=path%>/dealer/filingProposalManager/approveFilingProposalList.vti?random=" + Math.random() target="iframe_right">报备审批</a></li>
				<% } %>

				<% if (leftMenuCurrentUser.getUserRight().isRightFilingHistoryAll()) { %>
					<li><a href="<%=path%>/dealer/filingProposalManager/listFilingProposalHis.vti?random=" + Math.random() target="iframe_right">历史报备</a></li>
				<% } %>
			</ol>
		</li>
		<% } %>
		
		<% if (leftMenuCurrentUser.getUserRight().isRightTestDriveManagement()) { %>
		<li><strong>试驾明细</strong>
			<ol>
				<% if (leftMenuCurrentUser.getUserRight().isRightValidTestDriveAll()) { %>
					<li><a href="<%=path%>/dealer/testDrive/listValidTestDrive.vti?random=" + Math.random()  target="iframe_right">有效试驾</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightTestDriveSingleWeeksStatisticsAll()) { %>
					<li><a href="<%=path%>/dealer/testDrive/listWeek.vti?random=" + Math.random() target="iframe_right">---单周统计</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightTestDriveSingleMonthStatisticsAll()) { %>
					<li><a href="<%=path%>/dealer/testDrive/listMonth.vti?random=" + Math.random()  target="iframe_right">---月度统计</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightTestDriveQuarterStatisticsAll()) { %>
					<li><a href="<%=path%>/dealer/testDrive/listSeason.vti?random=" + Math.random()  target="iframe_right">---季度统计</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightTestDriveStageStatisticsAll()) { %>
					<li><a href="<%=path%>/dealer/testDrive/listInterval.vti?random=" + Math.random()  target="iframe_right">---时间段统计</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightInValidTestDriveAll()) { %>
					<li><a href="<%=path%>/dealer/testDrive/listInValidTestDrive.vti?random=" + Math.random()  target="iframe_right">无效试驾</a></li>
				<% } %>
			</ol>
		</li>
		<% } %>
		
		<% if (leftMenuCurrentUser.getUserRight().isRightViolationManagement()) { %>
		<li><strong>违规明细</strong>
			<ol>
				<% if (leftMenuCurrentUser.getUserRight().isRightTimeViolation()) {%>
				<li><a href="<%=path%>/dealer/violation/listLineTimeViolation.vti?violationType=2&random=" + Math.random() target="iframe_right">时间违规</a></li>
				<% } %> 
				<% if (leftMenuCurrentUser.getUserRight().isRightLineViolation()) {%>
				<li><a href="<%=path%>/dealer/violation/listLineTimeViolation.vti?violationType=1&random=" + Math.random() target="iframe_right">路线违规</a></li>
				<% } %> 
				<% if (leftMenuCurrentUser.getUserRight().isRightNoValidTestDriveAll()) {%>
					<li><a href="<%=path%>/dealer/violation/listNoTest.vti?random=" + Math.random() target="iframe_right">非活跃试驾</a></li>
					<li><a href="<%=path%>/dealer/violation/listNoTestDetail.vti?random=" + Math.random() target="iframe_right">非活跃试驾明细</a></li>
				<% } %> 
				<%-- 				
				<% if (leftMenuCurrentUser.getUserRight().isRightViolationDetailsAll()) { %>
					<li><a href="<%=path%>/dealer/violation/list.vti"  target="iframe_right">违规明细</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightViolationSingleWeeksStatisticsAll()) { %>
					<li><a href="<%=path%>/dealer/violation/listWeek.vti"  target="iframe_right">---单周统计</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightViolationSingleMonthStatisticsAll()) { %>
					<li><a href="<%=path%>/dealer/violation/listMonth.vti"  target="iframe_right">---月度统计</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightViolationQuarterStatisticsAll()) { %>
					<li><a href="<%=path%>/dealer/violation/listSeason.vti"  target="iframe_right">---季度统计</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightViolationStageStatisticsAll()) { %>
					<li><a href="<%=path%>/dealer/violation/listIntevel.vti"  target="iframe_right">---时间段统计</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightTestDriveSingleWeeksStatisticsAll()) {%>
					<li><a href="<%=path%>/dealer/testDrive/listNoTestDrive.vti"  target="iframe_right">非活跃试驾明细</a></li>
				<% } %> --%>
			</ol>
		</li>
		<% } %>
		
		<% if (leftMenuCurrentUser.getUserRight().isRightReportManagement()) { %>
		<li><strong>报表管理</strong>
			<ol>
				<% if (leftMenuCurrentUser.getUserRight().isRightConfigureReportAll()) { %>
					<li><a href="<%=path%>/dealer/report/listConfigureNew.vti?random=" + Math.random()  target="iframe_right">配置报表</a></li>
					<%-- <li><a href="<%=path%>/dealer/report/listBigAreaInfo.vti"  target="iframe_right">综合报表</a></li>--%>
				<% } %>
			</ol>
		</li>
		<%}%>
		
		<% if (leftMenuCurrentUser.getUserRight().isRightChartManagement()) { %>
		<li><strong>图表管理</strong>
			<ol>
				<% if (leftMenuCurrentUser.getUserRight().isRightVehicleConfigStatisticsForCarStyleAll()) { %>
					<li><a href="<%=path%>/dealer/reportManager/byVehicleTypeInit.vti?random=" + Math.random()  target="iframe_right">车辆配置统计（分车型）</a></li>
				<% }%>
				<% if (leftMenuCurrentUser.getUserRight().isRightVehicleConfigStatisticsForRegionAll()) { %>
					<li><a href="<%=path%>/dealer/reportManager/byVehicleRegionInit.vti?random=" + Math.random()  target="iframe_right">车辆配置统计（分区）</a></li>
				<% } %>
				
			</ol>
		</li>
		<% } %>
		
		<% if (leftMenuCurrentUser.getUserRight().isRightBoxManagement()) { %>
		<li><strong>设备管理</strong>
			<ol>
				<% if (leftMenuCurrentUser.getUserRight().isRightBoxAll()) { %>
					<li><a href="<%=path%>/dealer/box/list.vti?random=" + Math.random() target="iframe_right">设备维护</a></li>
					<li><a href="<%=path%>/dealer/box/listBoxOperation.vti?random=" + Math.random() target="iframe_right">设备操作日志</a></li>
				<% } %>
			</ol>
		</li>
		<% } %>

		<% if (leftMenuCurrentUser.getUserRight().isRightSystemManagement()) { %>
		<li><strong>系统设置</strong>
			<ol>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightLineView()) { %>
					<li><a href="<%=path%>/dealer/driveLine/list.vti?random=" + Math.random()  target="iframe_right">线路管理</a></li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightNoticeListAll()) { %>
					<li><a href="<%=path%>/base/notice/listUserNotice.vti?random=" + Math.random()  target="iframe_right">公告列表</a> </li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightCarStyleConfig()) { %>
					<li><a href="<%=path%>/dealer/survey/displayAddSurveyLevel.vti?random=" + Math.random()  target="iframe_right">车型配置调研</a> </li>
				<% } %>
				
				<% if (leftMenuCurrentUser.getUserRight().isRightQuestionnaireResult()) { %>
					<li><a href="<%=path%>/dealer/survey/listSurvey.vti?random=" + Math.random() target="iframe_right">问卷结果列表</a> </li>
				<% } %>
				<li><a href="<%=path%>/dealer/helpFile/list.vti?random=" + Math.random() target="iframe_right">文档列表</a> </li>
				<li><a href='<%=path%>/base/user/displayEditPersonalPassword.vti?userId=<%=leftMenuCurrentUser.getId()%>' target="iframe_right" >用户密码修改</a> </li>
			</ol>
		</li>
		<% } %>
		
		<% if (leftMenuCurrentUser.getUserRight().isRightAdminManage()) { %>
		<li><strong>运营管理</strong>
			<ol>
				<% if (leftMenuCurrentUser.getUserRight().isRightUserMangementAll()) { %>
					<li><a href="<%=path%>/base/user/search.vti?random=" + Math.random()  target="iframe_right">用户管理</a></li>
					<li><a href="<%=path%>/base/user/listUserLog.vti?random=" + Math.random()  target="iframe_right">登录日志</a></li>
				<% } %>
				<% if (leftMenuCurrentUser.getUserRight().isRightRoleMangementAll()) { %>
					<li><a href="<%=path%>/base/role/search.vti?random=" + Math.random()  target="iframe_right">角色管理</a></li>
				<% } %>
				<% if (leftMenuCurrentUser.getUserRight().isRightDealerMangementAll()) { %>
					<li><a href="<%=path%>/base/dealer/search.vti?menuType=0"  target="iframe_right">经销商管理</a></li>
				<% } %>
				<% if (leftMenuCurrentUser.getUserRight().isRightNoticeMangementAll()) {%>
					<li><a href="<%=path%>/base/notice/search.vti?random=" + Math.random()  target="iframe_right">公告管理</a> </li>
				<% } %>
				<% if (leftMenuCurrentUser.getUserRight().isRightAdminListTestDrive()) { %>
					<li><a href="<%=path%>/admin/testDrive/listTestDrive.vti?random=" + Math.random()  target="iframe_right">试驾查询</a></li>
				<% } %>
				<% if (leftMenuCurrentUser.getUserRight().isRightAdminListGpsPoint()) { %>
					<li><a href="<%=path%>/admin/testDrive/listGpsPoint.vti?random=" + Math.random()  target="iframe_right">GPS报文查询</a></li>
				<% } %>
				<% if (leftMenuCurrentUser.getUserRight().isRightAdminTaskList()) { %>
					<li><a href="<%=path%>/admin/testDrive/listTask.vti?random=" + Math.random()  target="iframe_right">重跑任务查询</a></li>
				<% } %>
				<% if (leftMenuCurrentUser.getUserRight().isRightAdminLogList()) { %>
					<li><a href="<%=path%>/admin/testDrive/listLog.vti?random=" + Math.random()  target="iframe_right">异常日志查询</a></li>
				<% } %>
			</ol>
		</li>
		<% } %>
	</ul>
</div>
<script type="text/javascript">
//二级导航高度自适应
var left = $('.bd_lf');
var olH = 530 - left.find('ul > li').length*25 -8;
left.find('ol').each(function(){
	$(this).height(olH+'px');
})
</script>
</body>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp"%>
<%@ page import="com.ava.domain.entity.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/main.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/style.css" />
</head>
</head>
<script>
	function verify() {
		if (confirm("确定审核？")) {
			$("#myPageForm").submit();
		}
	}
</script>
<body>
	<form id="myPageForm"
		action="<%=request.getContextPath()%>/dealer/proposal/proposalApproval.vti" method="post">
		<form:hidden path="proposalCompanyVehcileInfo.proposal.id" />
		<table border="0" cellpadding="0" cellspacing="0" 
			class="tableBox">
			<tr>
				<td class="tdTitle">分销中心：</td>
				<td class="tdSpace">${proposalCompanyVehcileInfo.company.parentName}</td>
			</tr>
			<tr>
				<td class="tdTitle">省份：</td>
				<td class="tdSpace">${proposalCompanyVehcileInfo.company.regionProvinceId_Nick}</td>
			</tr>
			<tr>
				<td class="tdTitle">城市：</td>
				<td class="tdSpace">${proposalCompanyVehcileInfo.company.regionCityId_Nick}</td>
			</tr>
			<tr>
				<td class="tdTitle">经销商名称：</td>
				<td class="tdSpace">${proposalCompanyVehcileInfo.company.cnName}</td>
			</tr>
			<tr>
				<td class="tdTitle">网络代码：</td>
				<td class="tdSpace">${proposalCompanyVehcileInfo.company.dealerCode}</td>
			</tr>
			<tr>
				<td class="tdTitle">经销商状态：</td>
				<td class="tdSpace">${proposalCompanyVehcileInfo.company.isKeyCity_Nick}</td>
			</tr>
			<tr>
				<td class="tdTitle">网络形态：</td>
				<td class="tdSpace">${proposalCompanyVehcileInfo.company.dealerType_Nick}</td>
			</tr>
			<tr>
				<td class="tdTitle">车牌：</td>
				<td class="tdSpace">${proposalCompanyVehcileInfo.vehicle.plateNumber}</td>
			</tr>
			<tr>
				<td class="tdTitle">车型：</td>
				<td class="tdSpace">${proposalCompanyVehcileInfo.vehicle.carStyleId_Nick}</td>
			</tr>
			<tr>
				<td class="tdTitle">VIN码：</td>
				<td class="tdSpace">${proposalCompanyVehcileInfo.vehicle.vin}</td>
			</tr>
			<tr>
				<td class="tdTitle">上牌日期：</td>
				<td class="tdSpace"><fmt:formatDate value="${proposalCompanyVehcileInfo.vehicle.licensingTime}" type="both" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<td class="tdTitle">上牌人：</td>
				<td class="tdSpace">${proposalCompanyVehcileInfo.vehicle.licensingExecutorName}</td>
			</tr>
			<c:set var="proposalTypeInstallation" value="<%=String.valueOf(GlobalConstant.PROPOSAL_TYPE_INSTALLATION)%>"/> 
			<c:if test="${proposalTypeInstallation == proposalCompanyVehcileInfo.proposal.type}">
			<tr>
				<td class="tdTitle">联系人：</td>
				<td class="tdSpace">${proposalCompanyVehcileInfo.proposal.contactName}</td>
			</tr>
			<tr>
				<td class="tdTitle">联系电话：</td>
				<td class="tdSpace">${proposalCompanyVehcileInfo.proposal.contactPhone}</td>
			</tr>
			<tr>
				<td class="tdTitle">提交时间：</td>
				<td class="tdSpace"><fmt:formatDate value="${proposalCompanyVehcileInfo.proposal.proposalTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			</c:if>
			<tr>
				<td class="tdTitle">  状态：</td>
	 			<c:if test="${proposalTypeInstallation == proposalCompanyVehcileInfo.proposal.type}"> 
				<td class="tdSpace"><em>${proposalCompanyVehcileInfo.proposal.status_nick}</em></td>
				</c:if>
				<c:set var="proposalTypeDemolition" value="<%=String.valueOf(GlobalConstant.PROPOSAL_TYPE_DEMOLITION)%>"/> 
	 			<c:if test="${proposalTypeDemolition == proposalCompanyVehcileInfo.proposal.type}"> 
				<td class="tdSpace"><em>${proposalCompanyVehcileInfo.proposal.demolitionStatus_nick}</em></td>
				</c:if>
			</tr>
			<tr>
				<td class="tdTitle">审核人：</td>
				<td class="tdSpace">${proposalCompanyVehcileInfo.approval.userNick}</td>
			</tr>
			<tr>
				<td class="tdTitle">审批意见：</td>
				<td class="tdSpace"><form:select
						path="proposalCompanyVehcileInfo.approval.approvalStatus">
						<form:option value="20">同意</form:option>
						<form:option value="30">拒绝</form:option>
					</form:select></td>
			</tr>
			<tr>
				<td class="tdTitle">审核原因：</td>
				<td class="tdSpace"><form:textarea
						path="proposalCompanyVehcileInfo.approval.remark" cols="50"
						rows="3" /></td>
			</tr>
			<tr>
				<td colspan="4" class="tdSpace" align="center"><input name=""
					type="button" class="btn_submit" onclick="verify()" value="提  交" /></td>
			</tr>
		</table>
	</form>

</body>
</html>
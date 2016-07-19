<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp"%>
<%@ page import="com.ava.resource.GlobalConstant"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
</head>

<body style="overflow-x: hidden;">
		<table border="0" cellpadding="0" cellspacing="0" 
			class="tableBox">
			<tr>
				<td class="tdTitle">经销商状态：</td>
				<td class="tdSpace">${vo.company.isKeyCity_Nick}</td>
			</tr>
			<tr>
				<td class="tdTitle">网络形态：</td>
				<td class="tdSpace">${vo.company.dealerType_Nick}</td>
			</tr>
			<tr>
				<td class="tdTitle">网络代码：</td>
				<td class="tdSpace">${vo.company.dealerCode}</td>
			</tr>
			<tr>
				<td class="tdTitle">分销中心：</td>
				<td class="tdSpace">${vo.company.parentName}</td>
			</tr>
			<tr>
				<td class="tdTitle">省份：</td>
				<td class="tdSpace">${vo.company.regionProvinceId_Nick}</td>
			</tr>
			<tr>
				<td class="tdTitle">城市：</td>
				<td class="tdSpace">${vo.company.regionCityId_Nick}</td>
			</tr>
			<tr>
				<td class="tdTitle">经销商名称：</td>
				<td class="tdSpace">${vo.company.cnName}</td>
			</tr>
			<tr>
				<td class="tdTitle"> 车牌：</td>
				<td class="tdSpace">${vo.vehicle.plateNumber}</td>
			</tr>
			<tr>
				<td class="tdTitle"> 车型：</td>
				<td class="tdSpace">${vo.vehicle.carStyleId_Nick}</td>
			</tr>
			<tr>
				<td class="tdTitle"> VIN码：</td>
				<td class="tdSpace">${vo.vehicle.vin}</td>
			</tr>
			<tr>
				<td class="tdTitle">上牌日期：</td>
				<td class="tdSpace"><fmt:formatDate value="${vo.vehicle.licensingTime}" type="both" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<td class="tdTitle">上牌人：</td>
				<td class="tdSpace">${vo.vehicle.licensingExecutorName}</td>
			</tr>
			<tr>
				<td class="tdTitle">提交时间：</td>
				<td class="tdSpace"><fmt:formatDate value="${vo.proposal.proposalTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr>
				<td class="tdTitle">  状态：</td>
				<td class="tdSpace">
				<c:set var="proposalStatusUnpassed" value="<%=String.valueOf(GlobalConstant.PROPOSAL_STATUS_UNPASSED)%>"/>
           			<c:choose>
         			    <c:when test="${proposalStatusUnpassed == vo.proposal.status}">
         			    <em>拒绝</em>
        				</c:when>
           			<c:otherwise>
           			<em>${vo.vehicle.configureStatus_Nick}</em>
		           </c:otherwise>
				    </c:choose>		
				
				</td>
			</tr>
			<tr>
				<td class="tdTitle"  valign="top">  审批意见：</td>
				<td class="tdSpace">
				<form:textarea cssStyle="border:none; resize:none; background:#FFFFFF;" path="vo.approval.remark" rows="3" cols="40"  />
				</td>
			</tr>
		</table>
</body>
</html>

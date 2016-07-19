<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp"%>
<%@ page import="com.ava.resource.GlobalConstant"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
</head>

<body style="overflow-x: hidden;">
    <table border="0" cellpadding="0" cellspacing="0" class="tableBox">
        <tr>
            <td class="tdTitle">分销中心：</td>
            <td class="tdSpace" colspan="3">${vo.company.parentName}</td>
        </tr>
        <tr>
            <td class="tdTitle">省份：</td>
            <td class="tdSpace">${vo.company.regionProvinceId_Nick}</td>
            <td class="tdTitle">城市：</td>
            <td class="tdSpace">${vo.company.regionCityId_Nick}</td>
        </tr>
        <tr>
            <td class="tdTitle">网络代码：</td>
            <td class="tdSpace" colspan="3">${vo.company.dealerCode}</td>
        </tr>
        <tr>
            <td class="tdTitle">经销商名称：</td>
            <td class="tdSpace" colspan="3">${vo.company.cnName}</td>
        </tr>
        <tr>
            <td class="tdTitle">经销商状态：</td>
            <td class="tdSpace">${vo.company.isKeyCity_Nick}</td>
            <td class="tdTitle">网络形态：</td>
            <td class="tdSpace">${vo.company.dealerType_Nick}</td>
        </tr>
        <tr>
            <td class="tdTitle"> 车型：</td>
            <td class="tdSpace" colspan="3">${vo.vehicle.carStyleId_Nick}</td>
        </tr>
        <tr>
            <td class="tdTitle"> VIN码：</td>
            <td class="tdSpace" colspan="3">${vo.vehicle.vin}</td>
        </tr>
        <tr>
            <td class="tdTitle"> 车牌：</td>
            <td class="tdSpace" colspan="3">${vo.vehicle.plateNumber}</td>
        </tr>
        <tr>
            <td class="tdTitle">上牌日期：</td>
            <td class="tdSpace">
                <fmt:formatDate value="${vo.vehicle.licensingTime}" type="both" pattern="yyyy-MM-dd" />
            </td>
            <td class="tdTitle">上牌人：</td>
            <td class="tdSpace">${vo.vehicle.licensingExecutorName}</td>
        </tr>
        <c:set var="proposalTypeInstallation" value="<%=String.valueOf(GlobalConstant.PROPOSAL_TYPE_INSTALLATION)%>" />
        <c:if test="${proposalTypeInstallation == vo.proposal.type}">
            <tr>
                <td class="tdTitle"> 联系人：</td>
                <td class="tdSpace">${vo.proposal.contactName}</td>
                <td class="tdTitle"> 联系电话：</td>
                <td class="tdSpace">${vo.proposal.contactPhone}</td>
            </tr>
        </c:if>
        <tr>
            <td class="tdTitle">提交时间：</td>
            <td class="tdSpace" colspan="3">
                <fmt:formatDate value="${vo.proposal.proposalTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
            </td>
        </tr>
        <tr>
            <td class="tdTitle"> 状态：</td>
            <c:if test="${proposalTypeInstallation == vo.proposal.type}">
                <td class="tdSpace" colspan="3"><em>${vo.proposal.status_nick}</em>
                </td>
            </c:if>
            <c:set var="proposalTypeDemolition" value="<%=String.valueOf(GlobalConstant.PROPOSAL_TYPE_DEMOLITION)%>" />
            <c:if test="${proposalTypeDemolition == vo.proposal.type}">
                <td class="tdSpace" colspan="3"><em>${vo.proposal.demolitionStatus_nick}</em>
                </td>
            </c:if>
        </tr>
        <tr>
    	<td class="tdTitle" valign="top">附件信息：</td>
        <td class="tdSpace" colspan="3">
               <c:if test="${proposalAttachmentList != null}" >
	           <table style="width:100%;">
				<c:forEach var="paFile" items="${proposalAttachmentList}" varStatus="statusObj">
						<c:choose>
						    <c:when test="${(statusObj.index +1) %2==0}" >
						    	<td style="width:30%" title="${paFile.originalName}">${fn:substring(paFile.originalName,0,40)}</td>
								<td style="width:8%"><a href="<%=path%>/dealer/proposal/getAttachFileDown.vti?proposalId=<c:out value="${paFile.proposalId}" />&fileId=<c:out value="${paFile.id}" />">查看</a></td>
								</tr>
							</c:when>
							<c:when test="${statusObj.last && (statusObj.index +1) %2!=0}" >
						    	<td style="width:30%" title="${paFile.originalName}">${fn:substring(paFile.originalName,0,40)}</td>
								<td style="width:8%"><a href="<%=path%>/dealer/proposal/getAttachFileDown.vti?proposalId=<c:out value="${paFile.proposalId}" />&fileId=<c:out value="${paFile.id}" />">查看</a></td>
								<td colspan="2"></td>
								</tr>
							</c:when>
							<c:otherwise>
							    <tr><td style="width:30%" title="${paFile.originalName}">${fn:substring(paFile.originalName,0,40)}</td>
									<td style="width:8%"><a href="<%=path%>/dealer/proposal/getAttachFileDown.vti?proposalId=<c:out value="${paFile.proposalId}" />&fileId=<c:out value="${paFile.id}" />">查看</a></td>
	   						</c:otherwise>
  						</c:choose>
				</c:forEach>
				</table>
			</c:if>
        </td>
    </tr>
    </table>
</body>

</html>

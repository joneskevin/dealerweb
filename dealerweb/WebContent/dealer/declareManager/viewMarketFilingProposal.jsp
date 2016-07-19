<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.domain.entity.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
</head>
<body class="body_pop">

<div class="pop-tableBox">
<form id="myPageForm">
<table>
	<tr>
    	<td width="100px" align="right">经销商状态：</td>
        <td width="150px">${dealer.isKeyCity_Nick}</td>
        <td width="100px" align="right">网络形态：</td>
        <td width="150px">${dealer.dealerType_Nick}</td>
        <td width="100px" align="right">网络代码：</td>
        <td width="150px">${dealer.dealerCode}</td>
    </tr>
    <tr>
        <td width="100px" align="right">分销中心：</td>
        <td width="150px">${dealer.distributionSaleCenterName}</td>
        <td width="100px" align="right">省份：</td>
        <td width="150px">${dealer.regionProvinceId_Nick}</td>
        <td width="100px" align="right">城市：</td>
        <td width="150px">${dealer.regionCityId_Nick}</td>
    </tr>
    <tr>
    	<td width="100px" align="right">经销商名称：</td>
        <td width="650px" colspan="5">${dealer.cnName}</td>
    </tr>
    <tr>
    	<td width="100px" align="right" valign="top">报备车辆：</td>
        <td width="650px" colspan="5">
        	<c:if test="${vehicleStyles != null}" >
	           <table style="width:95%; margin:0px 0;font-family:"微软雅黑";">
				<c:forEach var="vehicleStyle" items="${vehicleStyles}" varStatus="statusObj">
						<c:choose>
						    <c:when test="${(statusObj.index +1) %2==0}" >
						    	<td>${vehicleStyle.vehicleInfo}</td>
								</tr>
							</c:when>
							<c:when test="${statusObj.last && (statusObj.index +1) %2!=0}" >
						    	<td>${vehicleStyle.vehicleInfo}</td>
								<td></td>
								</tr>
							</c:when>
							<c:otherwise>
							    <tr><td>${vehicleStyle.vehicleInfo}</td>
	   						</c:otherwise>
  						</c:choose>
				</c:forEach>
					</table>
			</c:if>
        </td>
    </tr>
    <tr>
    	<td width="100px" align="right">开始日期：</td>
        <td width="275px" colspan="2">
        	<fmt:formatDate value="${filingProposal.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
		</td>
        <td width="100px" align="right">结束日期：</td>
        <td width="275px" colspan="2">
        	<fmt:formatDate value="${filingProposal.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
        </td>
    </tr>
    <tr>
    	<td width="100px" align="right">目的地：</td>
        <td width="650px" colspan="5">${filingProposal.description}
        </td>
    </tr>
    <tr>
    	<td width="100px" align="right" valign="top">报备原因：</td>
        <td width="650px" colspan="5"><textarea id="filingProposal_reason" readonly="true" name="reason" rows = "3" >${filingProposal.reason}</textarea></td>
    </tr>
</table>
</form>
</div>
</body>
</html>
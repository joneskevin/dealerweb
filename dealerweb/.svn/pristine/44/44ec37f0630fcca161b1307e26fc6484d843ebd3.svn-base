<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<%@ page import="com.ava.domain.entity.User"%>
<%@ page import="com.ava.domain.vo.FilingProposalCompanyVehcileInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
</head>

<script>
	function save(advice){
		var adviceDescription=$('#approvalMessage').val();
		if(null!=adviceDescription && adviceDescription.length>100){
			alert("意见长度不能超过100");
			return;
		}
		 $("#advice").val(advice);
		$("#myPageForm").submit();
	}
</script>

<body class="body_pop">
<form id="myPageForm" action="<%=path%>/dealer/filingProposalManager/approveFilingProposal.vti" method="post">
<input type="hidden" id="filingProposalId" name="filingProposalId" value="${filingProposal.id}" />
<input type="hidden" id="advice" name="advice" value="" />
<div class="pop-tableBox">
<table style="border-collapse:separate; border-spacing:2px">
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
	           <table style="width:100%; margin:0px 0;font-family:"微软雅黑";">
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
        <td width="650px" colspan="5"><textarea id="filingProposal_reason" readonly="true" name="reason" rows = "3" style="width:90%;">${filingProposal.reason}</textarea></td>
    </tr>
    <tr>
    	<td width="100px" align="right" valign="top">意见描述：</td>
        <td width="650px" colspan="5">
        	<textarea id="approvalMessage" name="approvalMessage" cols ="100"  class="textarea_noborder" style="width:90%;border:1px solid #666;padding:1px 0;"></textarea>
        </td>
    </tr>
    <tr>
    	<td width="100px" align="right" valign="top">附件信息：</td>
        <td width="650px" colspan="5">
               <c:if test="${fpaVOList != null}" >
	           <table style="width:100%; margin:0px 0;font-family:"微软雅黑";">
				<c:forEach var="fpaFile" items="${fpaVOList}" varStatus="statusObj">
						<c:choose>
						    <c:when test="${(statusObj.index +1) %2==0}" >
						    	<td style="width:44%" title="${fpaFile.originalName}">${fn:substring(fpaFile.originalName,0,40)}</td>
								<td style="width:5%"><a href="<%=path%>/dealer/filingProposalManager/getAttachFileDown.vti?filingProposalId=<c:out value="${fpaFile.proposalId}" />&fileId=<c:out value="${fpaFile.id}" />">查看</a></td>
								</tr>
							</c:when>
							<c:when test="${statusObj.last && (statusObj.index +1) %2!=0}" >
						    	<td style="width:44%" title="${fpaFile.originalName}">${fn:substring(fpaFile.originalName,0,40)}</td>
								<td style="width:5%"><a href="<%=path%>/dealer/filingProposalManager/getAttachFileDown.vti?filingProposalId=<c:out value="${fpaFile.proposalId}" />&fileId=<c:out value="${fpaFile.id}" />">查看</a></td>
								<td colspan="2"></td>
								</tr>
							</c:when>
							<c:otherwise>
							    <tr><td style="width:44%" title="${fpaFile.originalName}">${fn:substring(fpaFile.originalName,0,40)}</td>
									<td style="width:5%"><a href="<%=path%>/dealer/filingProposalManager/getAttachFileDown.vti?filingProposalId=<c:out value="${fpaFile.proposalId}" />&fileId=<c:out value="${fpaFile.id}" />">查看</a></td>
	   						</c:otherwise>
  						</c:choose>
				</c:forEach>
				</table>
			</c:if>
        </td>
    </tr>
</table>
<div class="input_btn_div">
	<input type="button" onclick="save(1)" value="同意"/>
	<input type="button" onclick="save(2)" value="拒绝" />
</div>

</div>
<%-- <b class="pop_more_btn"><span>报备附件明细</span></b>
	<aside class="pop_more">
	    <c:if test="${fpaVOList != null}" >
		<c:forEach var="fpaFile" items="${fpaVOList}">
			<p style="word-break:break-all">
				文件名：<span title="${fpaFile.originalName}">${fn:substring(fpaFile.originalName,0,100)}</span>
				<span style="float:right"><a href="<%=path%>/dealer/filingProposalManager/getAttachFileDown.vti?filingProposalId=<c:out value="${fpaFile.proposalId}" />&fileId=<c:out value="${fpaFile.id}" />">查看附件</a></span> 
				<br />
			</p>
		</c:forEach>
		</c:if>	
	</aside> --%>
</form>
</body>
</html>
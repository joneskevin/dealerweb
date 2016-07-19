<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp" %>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.domain.entity.User"%>
<%@ page import="com.ava.domain.vo.FilingProposalCompanyVehcileInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
</head>
<body class="body_pop">
<form id="myPageForm" action="<%=path%>/dealer/declareManager/uploadFile.vti" method="post"  enctype="multipart/form-data">
	<input type="hidden" id="filingProposalId" name="filingProposalId" value="${filingProposalId}" />
	<div class="table_list" style="min-width:630px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="min-width:630px;">
            		<li class="li_280">文件名</li>
            		<li class="li_150">上传时间</li>
            		<li class="li_100">文件大小(M)</li>
            		<li class="li_90">操作</li>
				</ul>
			<c:if test="${filingProposalAttachmentList != null}" >
					<c:forEach var="filingProposalAttachment" items="${filingProposalAttachmentList}" varStatus="statusObj">
			<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="min-width:615px;">
            	<li class="li_280" ><span title="${filingProposalAttachment.originalName}">${fn:substring(filingProposalAttachment.originalName,0,30)}</span></li>
            	<li class="li_150"><span ><fmt:formatDate value="${filingProposalAttachment.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            	<li class="li_100"><span>${filingProposalAttachment.size}</span></li>
            	<li class="li_90"><span>
            	<%-- <a href="<%=path%>/dealer/declareManager/previewFile.vti?filingProposalId=<c:out value="${filingProposalAttachment.filingProposalId}" />&filingProposalAttachmentId=<c:out value="${filingProposalAttachment.id}" />">查看</a> --%>
            	<a href="<%=path%>/dealer/declareManager/declareAttachFileDown.vti?filingProposalId=<c:out value="${filingProposalAttachment.filingProposalId}" />&filingProposalAttachmentId=<c:out value="${filingProposalAttachment.id}" />">查看</a>
            	</span></li>
			</ul>
		</c:forEach>
		</c:if>
	</div><!--/table_list-->
	
</form>
</body>
</html>
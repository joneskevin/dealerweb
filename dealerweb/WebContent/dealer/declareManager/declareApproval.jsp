<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<%@ page import="com.ava.domain.entity.User"%>
<%@ page import="com.ava.domain.vo.FilingProposalCompanyVehcileInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="height:95%;width:98%">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
</head>

<body style="min-height:95%;min-width:98%;width:98%">
<div class="pop-tableBox" style="width:95%;height:100%">
<form id="myPageForm" action="<%=path%>/dealer/declareManager/declareApproval.vti" method="post">
<input type="hidden" id="filingProposalId" name="filingProposalId" value="${filingApproval.filingProposalId}" />
<table style="width:100%;height:100%">

    <tr style="height:45%">
    	<td style="width:20%; height:100%" align="right" ><span>*</span>审批意见：</td>
        <td colspan="3" style="height:95%">
        	<select id="advice" name="advice">
					<option value="3">同意</option>
					<option value="4">拒绝</option>
			</select>
        </td>
    </tr>
    <tr style="height:45%">
    	<td style="width:20%; height:95%;padding:15px 0;" align="right" >意见描述：</td>
        <td colspan="3" style="height:95% ;padding:15px 0;">
        	<textarea id="adviceDescription" name="adviceDescription" style="width:82%; height:100%;border:1px solid #666;padding:3px 0;" rows = "5" ></textarea>
        </td>
    </tr>
</table>
<button>提交</button>
</form>
</div>
</body>
</html>
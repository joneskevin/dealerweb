<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp"%>
<% 
	Integer currentYear = Integer.valueOf(DateTime.getYear());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/main.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/style.css" />
<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>

function save() {
	$("#myPageForm").submit();
}

</script>

<body>
    <form id="myPageForm" method="post" action="<%=request.getContextPath()%>/dealer/installationPlan/addInstallationPlan.vti">
   		<input type="hidden" name="type" value="2"/>
        <table border="0" cellpadding="0" cellspacing="0" class="tableBox">
            <tr>
                <td class="tdTitle">拆除车型：</td>
                <td class="tdSpace" colspan="3">
                    <form:select path="vehicleInstallationPlan.testDriveCarStyleId" >
                    	<form:options items="${carStyleList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                    </form:select>
                </td>
            </tr>
            <tr>
                <td colspan="4" class="tdSpace" align="center">
                    <input name="" type="button" onclick="save()" class="btn_submit" value="提 交" />
                    <input type="button" class="btn_submit" onclick="javascript:new parent.dialog().reset();" value="取 消" />
                </td>
            </tr>
        </table>
    </form>
</body>
</html>

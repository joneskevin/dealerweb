<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
</head>
<body>

<div class="allContent">
	<table border="0" cellpadding="0" cellspacing="0" 
		class="tableBox">
		<tr>
			<td class="tdTitle">所属单位：</td>
			<td class="tdSpace">${box.companyName}
			</td>
		</tr>
		<tr>
			<td class="tdTitle">设备号：</td>
			<td class="tdSpace">${box.uniqueId}
			</td>
		</tr>
		<tr>
			<td class="tdTitle">SIM卡号：</td>
			<td class="tdSpace">${box.simId}
			</td>
		</tr>
		<tr>
			<td class="tdTitle">通讯号：</td>
			<td class="tdSpace">${box.simMobile}
			</td>
		</tr>
		<tr>
			<td class="tdTitle">当前版本：</td>
			<td class="tdSpace">${box.currentVersion}
			</td>
		</tr>
		<tr>
			<td class="tdTitle">状态：</td>
			<td class="tdSpace">${box.status_nick}
			</td>
		</tr>
	</table>
</div>

</body>
</html>
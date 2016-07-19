<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/main.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/style.css" />
</head>

<body>
	<table border="0" cellpadding="0" cellspacing="0" class="tableBox">
		<tr>
			<td class="tdTitle"><span>所属单位：</span></td>
			<td class="tdSpace">${vehicle.companyName}</td>
		</tr>
		<tr>
			<td class="tdTitle"><span>VIN码：</span></td>
			<td class="tdSpace">${vehicle.vin}</td>
		</tr>
		<tr>
			<td class="tdTitle"><span>车牌：</span></td>
			<td class="tdSpace">${vehicle.plateNumber}</td>
		</tr>
		<tr>
			<td class="tdTitle"><span>车型：</span></td>
			<td class="tdSpace">${vehicle.carStyleId_Nick}</td>
		</tr>
		<tr>
			<td class="tdTitle"><span>配置类型：</span></td>
			<td class="tdSpace">${vehicle.configureType_Nick}</td>
		</tr>
		<tr>
			<td class="tdTitle"><span>配置状态：</span></td>
			<td class="tdSpace">${vehicle.configureStatus_Nick}</td>
		</tr>
		<tr>
			<td class="tdTitle"><span>上牌日期：</span></td>
			<td class="tdSpace"><fmt:formatDate
					value="${vehicle.licensingTime}" type="both" pattern="yyyy-MM-dd" /></td>
		</tr>
		<tr>
			<td class="tdTitle"><span>上牌人姓名：</span></td>
			<td class="tdSpace">${vehicle.licensingExecutorName}</td>
		</tr>
		<tr>
			<td colspan="2" class="tdSpace" align="center">
				<input type="button" class="btn_submit" onclick="javascript:new parent.dialog().reset();" value="取 消" />
			</td>
		</tr>
	</table>
</body>
</html>

</body>
</html>
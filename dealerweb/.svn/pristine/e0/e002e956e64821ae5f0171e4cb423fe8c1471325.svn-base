<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/main.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/style.css" />
</head>
<%@ include file="/dealer/include/meta.jsp" %>
<%@ include file="/dealer/include/typeAndSubtypeCascader.jsp"%>
<script>
	function unbind() {
		var remark = $("#remark").val();
		if (remark == "") {
			jAlert("解绑原因不能为空");
			$("#remark").focus(); 
			return;
		}
		   
		if (confirm("确定解绑？")) {
			$("#myPageForm").submit();
		}
	}
	
</script>
<body>
<form id="myPageForm" action="<%=request.getContextPath()%>/dealer/box/unbind.vti" method="post">
		<input type="hidden" name="startIndex" value="${startIndex}" />
		<input type="hidden" name="id" value="${box.id}" />
		<table border="0" cellpadding="0" cellspacing="0" 
			class="tableBox">
		
			<tr>
				<td class="tdTitle">所属单位：</td>
				<td class="tdSpace">${box.companyName}</td>
			</tr>
			<tr>
				<td class="tdTitle">设备号：</td>
				<td class="tdSpace">${box.uniqueId}</td>
			</tr>
			<tr>
				<td class="tdTitle">车牌：</td>
				<td class="tdSpace">${box.vehicle.plateNumber}</td>
			</tr>
			<tr>
				<td class="tdTitle">VIN：</td>
				<td class="tdSpace">${box.vehicle.vin}</td>
			</tr>
			<tr>
				<td class="tdTitle">状态：</td>
				<td class="tdSpace">${box.status_nick}</td>
			</tr>
			<tr>
				<td class="tdTitle"><em>*</em>解绑原因：</td>
				<td class="tdSpace">
				<form:input id="remark" path="box.boxOperation.remark" maxlength="80" cssClass="size" cssStyle="width:450px" />
				</td>
			</tr>
			<tr>
				<td colspan="4" class="tdSpace" align="center">
				<input name="" type="button" class="btn_submit" onclick="unbind()" value="提  交" />
				<input type="button" class="btn_submit" onclick="javascript:new parent.dialog().reset();" value="取 消" />
				</td>
				
			</tr>
		</table>
	</form>
</body>
</html>
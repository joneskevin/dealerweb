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

<body>
				<form id="myPageForm" action="<%=request.getContextPath()%>/dealer/box/edit.vti" method="post">
					<input type="hidden" name="startIndex" value="${startIndex}" />
					<input type="hidden" name="id" value="${box.id}" />
					<table border="0" cellpadding="0" cellspacing="0" 
						class="tableBox">
					
						<tr>
							<td class="tdTitle"><em>*</em> 所属单位：</td>
							<td class="tdSpace"><form:select
									path='box.companyId'>
									<form:options items="${orgList}" itemLabel="name"
										itemValue="id" htmlEscape="false" />
								</form:select></td>
						</tr>
						<tr>
							<td class="tdTitle"><em>*</em> 设备号：</td>
							<td class="tdSpace"><form:input
									path="box.uniqueId" cssClass="size"
									cssStyle="width:200px" /></td>
						</tr>
						<tr>
							<td class="tdTitle"><em>*</em> SIM卡号：</td>
							<td class="tdSpace"><form:input
									path="box.simId" cssClass="size"
									cssStyle="width:200px" /></td>
						</tr>
						<tr>
							<td class="tdTitle"><em>*</em> 通讯号：</td>
							<td class="tdSpace"><form:input
									path="box.simMobile" cssClass="size"
									cssStyle="width:200px" maxlength="15" /></td>
						</tr>
						<tr>
							<td class="tdTitle"><em>*</em> 当前版本：</td>
							<td class="tdSpace"><form:input
									path="box.currentVersion" cssClass="size"
									cssStyle="width:200px" /></td>
						</tr>
						<tr>
							<td colspan="4" class="tdSpace" align="center"><input
								name="" type="submit" class="btn_submit" value="提  交" />
								<input type="button" class="btn_submit" onclick="javascript:new parent.dialog().reset();" value="取 消" />
							</td>
							
						</tr>
					</table>
				</form>
</body>
</html>
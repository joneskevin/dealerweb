<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
	<link rel="stylesheet"
		href="<%=request.getContextPath()%>/dealer/css/main.css" />
	<link rel="stylesheet"
		href="<%=request.getContextPath()%>/dealer/css/style.css" />
</head>

<body style="overflow-x: hidden;">
    <form id="myPageForm" action="<%=path%>/dealer/driveLine/edit.vti" method="post">
        <input type="hidden" name="startIndex" value="${startIndex}" />
        <input type="hidden" name="id" value="${driveLine.id}" />
        <table border="0" cellpadding="0" cellspacing="0" class="tableBox">
            <tr>
                <td class="tdTitle"><em>*</em> 所属单位：</td>
                <td class="tdSpace">
                    ${driveLine.companyName}
                </td>
            </tr>
            <tr>
                <td class="tdTitle"><em>*</em> 名称：</td>
                <td class="tdSpace">
                    <form:select path='driveLine.name'>
                        <form:option value="A线路">A线路</form:option>
                        <form:option value="B线路">B线路</form:option>
                        <form:option value="C线路">C线路</form:option>
                        <form:option value="D线路">D线路</form:option>
                        <form:option value="E线路">E线路</form:option>
                        <form:option value="F线路">F线路</form:option>
                    </form:select>
                    <em style="margin-left: 10px;">【C线路】为加油线路,其他线路为试驾线路</em>
                </td>
            </tr>
            <tr>
                <td class="tdTitle"><em>*</em> 围栏类型：</td>
                <td class="tdSpace">
                    <form:select path='driveLine.fenceType'>
                        <form:options items="${driveLineFenceTypeOptions}" itemLabel="optionText" itemValue="optionValue" htmlEscape="false" />
                    </form:select>
                    <em style="margin-left: 10px;">【单围栏】为单线,【双围栏】为环线</em>
                </td>
            </tr>
            <tr>
                <td colspan="4" class="tdSpace" align="center">
                    <input name="" type="submit" class="btn_submit" value="提  交" />
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
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

$().ready(function() {
	carSeriesChange();
});

function save() {
	var name = $("#name").val();
	if (name == "") {
		jAlert("请输入车型！");
		return;
	}
	$("#myPageForm").submit();
}



function carSeriesChange() {
	  var seriesLable = $("#seriesId option:selected").text();
	  $("#name").val(seriesLable);
	  $("#seriesName").val(seriesLable);
} 

</script>

<body>
    <form id="myPageForm" method="post" action="<%=request.getContextPath()%>/dealer/installationPlan/addCarStyle.vti">
    	<form:hidden path="carStyle.seriesName" />
        <table border="0" cellpadding="0" cellspacing="0" class="tableBox">
            <tr>
                <td class="tdTitle">车系：</td>
                <td class="tdSpace" colspan="3">
                    <form:select path="carStyle.seriesId"  onchange="carSeriesChange()">
                    	<form:options items="${carSeriesList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                    </form:select>
                </td>
            </tr>
            <tr>
                <td class="tdTitle">年款：</td>
                <td class="tdSpace" colspan="3">
                    <form:select  path="carStyle.yearType" >
					 	<form:option value="<%=currentYear - 2%>"><%=currentYear - 2%>年</form:option>
					 	<form:option value="<%=currentYear - 1%>"><%=currentYear - 1%>年</form:option>
					 	<form:option value="<%=currentYear%>"><%=currentYear%>年</form:option>
					 	<form:option value="<%=currentYear + 1%>"><%=currentYear + 1%>年</form:option>
	   				</form:select>
                </td>
            </tr>
            <tr>
                <td class="tdTitle">车型：</td>
                <td class="tdSpace" colspan="3">
                    <form:input path="carStyle.name" cssClass="size" maxlength="30" cssStyle="width:150px" /><em>此处输入车系 + 排量，车系与排量用空格分开</em>
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

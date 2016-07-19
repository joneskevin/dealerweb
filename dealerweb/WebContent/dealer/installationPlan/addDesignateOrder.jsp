<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp"%>
<%@ page import="com.ava.resource.GlobalConstant"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/main.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/style.css" />
<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>

    function save() {
    	var importExcelPacket=$("#importExcelPacket").val();
		if(importExcelPacket == undefined || importExcelPacket == null || importExcelPacket == ""){
			alert("请选择附件");
			return ;
		}
    	$("#myPageForm").submit();
    }
</script>

<body>
    <form id="myPageForm" method="post" action="<%=request.getContextPath()%>/dealer/installationPlan/importDesignateOrder.vti" enctype="multipart/form-data">
        <table border="0" cellpadding="0" cellspacing="0" class="tableBox">
            <tr>
		    	<td class="tdTitle"><em>*</em>上传附件：</td>
		        <td colspan="3">
					<input type="file" name="importExcelPacket" id="importExcelPacket" />
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
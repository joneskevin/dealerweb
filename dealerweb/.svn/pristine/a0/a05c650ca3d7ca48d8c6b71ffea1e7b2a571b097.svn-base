<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp"%>
<%@ page import="com.ava.resource.GlobalConstant"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
</head>

<script>
	$(document).ready(function() {
		//加载多<strong>文件</strong>上传的JS
    	$('#multiFileId').MultiFile({
			list : '#addMultiFileIdList',
			accept:'png|jpg|bmp|jpeg|doc|docx|xls|xlsx|ppt|pptx|pdf',
			max:10, 
			STRING: {
		           remove:'删除',
		           selected:'文件重复: $file',  
		           denied:'不支持上传该文件类型 $ext!',
		           duplicate:'文件重复:\n$file'
			}
    	});
		
	});

	function save() {
		var multiFileTitle = $('.MultiFile-title');
		if ((undefined == multiFileTitle || null == multiFileTitle || multiFileTitle.length <= 0)) {
			alert("请选择附件");
			return;
		}

		$("#myPageForm").submit();

	}

</script>

<body>
	<form id="myPageForm" method="post" action="<%=request.getContextPath()%>/dealer/helpFile/upload.vti" enctype="multipart/form-data">
		<table border="0" cellpadding="0" cellspacing="0" class="tableBox">
			<tr>
		    	<td class="tdTitle"><em>*</em>上传附件：</td>
		        <td colspan="3">
					<fieldset style="width:96%;">
						<legend><em>请上传文档</em></legend>
						<!-- 注意这里的attach名字为固定，如果要控制上传的格式，则追加accept="doc|txt|jsp"  最大上传量maxlength="3" -->
						<input type="file" name="attach" id="multiFileId" class="text-input"/>
						<div id="addMultiFileIdList"></div>
					</fieldset>
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

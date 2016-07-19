<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<html>
<head>
<jsp:include page="/back/backMeta.jsp" flush="true">
	<jsp:param name="titleInner" value="" />
</jsp:include>
</head>

<body>
<table width="100%" border="0" cellpadding="2" cellspacing="1"
	class="border">
	<tr align="center">
		<td height=25 colspan=2 bgcolor="#39867B">
		<div align="left" class="style4"><strong>当前位置：</strong>内容管理
		&gt; 论坛管理 &gt; 帖子维护 &gt; 维护帖子</div>
	</tr>
	<tr>
		<td width="81%" height=23 nowrap class="bgcolor"><strong>管理菜单：</strong>
		<a
			href="<%=request.getContextPath()%>/back/backForum/orgForumPostMan/orgForumPostManAction.do?action=searchPost"
			target="_self">帖子列表</a>
		</td>
		<td width="23%" height=23 valign="bottom" nowrap class="bgcolor">
		<div align="right"><a href="javascript:history.back();"><img
			src="<%=request.getContextPath()%>/back/images/backto.gif"
			border="0"></a><a href="javascript:window.location.reload();"><img
			src="<%=request.getContextPath()%>/back/images/refresh.gif"
			border="0"></a></div>
		</td>
	</tr>
</table>
<table width="100%" border="0" cellpadding="2" cellspacing="1"
	class="tablebg">
	<tr bgcolor="#FFFFFF" onMouseOver="this.bgColor='#DFEDEA'" onMouseOut="this.bgColor='#FFFFFF'">
		<td nowrap>
		<div align="center"><font
				color="red"><html:errors property="MSG_RESULT" /></font>
		</div>
		</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="2"
	cellspacing="1" class="tablebg">
	<html:form action="/back/backForum/orgForumPostMan/orgForumPostManAction" method="post">
    <input type="hidden" name="threadForEdit.id" value="<bean:write name="PROJ_FORUM_POST_MAN_FORM" property="threadForEdit.id"/>" />	
		<tr>
		  <td class="bgcolor"><font color="red">*</font>主题图片：</td>
		  <td width="11%" class="bgcolor"><font color="red">*</font>标题</td>
		  <td colspan="2" bgcolor="#FFFFFF">
		  <html:text size="10" name="PROJ_FORUM_POST_MAN_FORM" property="threadForEdit.title" />	</td>
	  </tr>
		<tr>		
			<td width="31%" rowspan="2" bgcolor="#FFFFFF">	
			      <iframe style="top:2px" ID="UploadFiles1"
						src="/pub/uploadpages/upload.jsp?act=view&picpath=
						<bean:write name='PROJ_FORUM_POST_MAN_FORM' property='threadForEdit.attachmentPath'/>"
						frameborder=0 scrolling=no width="100%" height="180">				
			      </iframe>
			</td>
			<td colspan="3" bgcolor="#FFFFFF"><font color="red">*</font>正文</td>		
		</tr>
		<tr>
			<td colspan="5" bgcolor="#FFFFFF">
				<html:textarea name='PROJ_FORUM_POST_MAN_FORM' property="threadForEdit.body" cols="100" rows="10"></html:textarea>			</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td colspan="10" align="center" class="40td">
    			<input type="hidden" name="threadForEdit.attachmentPath"/>	
				<script type="text/javascript">
					function onAddRecode(){
						if ( UploadFiles1.document.form1.picpath.value==null ){
							document.getElementById("threadForEdit.attachmentPath").value = "";
						}else{
							document.getElementById("threadForEdit.attachmentPath").value = UploadFiles1.document.form1.picpath.value;
						}
					  	return true; 
					}
				</script>
				<html:submit
				property="action.editPost" value="保  存"
				styleClass="bgcolor" onclick="onAddRecode()"/> <html:reset property="clear" value="清　空"
				styleClass="bgcolor" /></td>
		</tr>
	</html:form>
</table>
<br>
<table width="100%" border="0" cellpadding="2" cellspacing="1"
	class="tablebg">
	<tr bgcolor="#FFFFFF" onMouseOver="this.bgColor='#DFEDEA'"
		; onMouseOut="this.bgColor='#FFFFFF'">
		<td nowrap>
		<div align="left">系统说明：<br>
		1.继续添加：添加产品成功后有提示成功的会话框；还在这个页面;<br>
		2.添加完成：则跳转到编辑入库单页面;<br>
		</div>
		</td>
	</tr>
</table>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.resource.GlobalConstant"%>
<%@ page import="com.ava.util.TypeConverter"%>
<%
	Integer myPageNo = null;//当前页码
	if (request.getAttribute(GlobalConstant.PAGE_NO) == null) {
		myPageNo = (Integer) request
		.getAttribute(GlobalConstant.PAGE_NO);
	} else {
		myPageNo = TypeConverter.toInteger(request
		.getAttribute(GlobalConstant.PAGE_NO));
	}
%>
<html>
	<head>

<jsp:include page="/back/backMeta.jsp" flush="true">
	<jsp:param name="titleInner" value="" />
</jsp:include>
		<script language='javascript'>
	function comfirmDelete(){
          if(confirm("确定要删除该记录吗?")){
            return true;
          }else{
         return false;
       }
    }
</script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="2" cellspacing="1"
			class="border">
			<tr align="center">
				<td height=25 colspan=2 bgcolor="#39867B">
					<div align="left" class="style4">
						<strong>当前位置：</strong>内容管理 &gt; 论坛管理 &gt;帖子维护
					</div>
			</tr>
			<tr>
				<td width="81%" height=23 nowrap class="bgcolor">
					<strong>管理菜单：</strong>
					<a
						href="<%=request.getContextPath()%>/back/backForum/orgForumPostMan/orgForumPostManAction.do?action=searchPost"
						target="_self">帖子列表</a>					
				</td>
				<td width="23%" height=23 valign="bottom" nowrap class="bgcolor">
					<div align="right">
						<a href="javascript:history.back();"><img
								src="<%=request.getContextPath()%>/back/images/backto.gif"
								border="0"> </a><a href="javascript:window.location.reload();"><img
								src="<%=request.getContextPath()%>/back/images/refresh.gif"
								border="0"> </a>
					</div>
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="2"
			cellspacing="1" class="tablebg">
			<html:form
				action="/back/backForum/orgForumPostMan/orgForumPostManAction"
				method="post">
				<tr>
					<td class="bgcolor" width="5%">
						<div align="right">
							组织
						</div>
					</td>
					<td width="10%" bgcolor="#FFFFFF">
						<html:text size="10" name="PROJ_FORUM_POST_MAN_FORM"
							property="postRemarkForSearch.orgId" />
					</td>
					<td class="bgcolor" width="5%">
						<div align="right">
							标题
						</div>
					</td>
					<td width="10%" bgcolor="#FFFFFF">
						<html:text size="10" name="PROJ_FORUM_POST_MAN_FORM"
							property="postRemarkForSearch.title" />
					</td>
					<td class="bgcolor" width="5%">
						<div align="right">
							正文
						</div>
					</td>
					<td width="10%" bgcolor="#FFFFFF">
						<html:text size="10" name="PROJ_FORUM_POST_MAN_FORM"
							property="postRemarkForSearch.body" />
					</td>
				</tr>
				<tr>
					<td class="bgcolor" width="5%">
						<div align="right">
							创建者
						</div>
					</td>
					<td width="10%" bgcolor="#FFFFFF">
						<html:text size="10" name="PROJ_FORUM_POST_MAN_FORM"
							property="postRemarkForSearch.creatorName" />
					</td>
					<td class="bgcolor" width="5%">
						<div align="right">
							创建时间
						</div>
					</td>
					<td bgcolor="#FFFFFF" colspan="3">
						起始日期:
						<html:text name="PROJ_FORUM_POST_MAN_FORM" property="startTime"
							styleId="startTime" size="10"  readonly="true"/>
						截止日期:
						<html:text name="PROJ_FORUM_POST_MAN_FORM" property="endTime"
							styleId="endTime" size="10"  readonly="true"/>
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td colspan="10" align="center" class="40td">
						<html:submit property="action.searchRemark" value="搜  索"
							styleClass="bgcolor" />
						<html:reset property="clear" value="清　空" styleClass="bgcolor" />
					</td>
				</tr>
			</html:form>
		</table>
		<table width="100%" border="0" cellpadding="2" cellspacing="1"
			class="tablebg">
			<tr class="bgcolor">
				<td width="6%" nowrap>
					组织
				</td>
				<td width="6%" nowrap>
					标题
				</td>
				<td width="6%" nowrap>
					正文
				</td>
				<td width="6%" nowrap>
					创建者
				</td>
				<td width="9%" nowrap>
					创建时间
				</td>
				<td width="16%" nowrap>
					操作
				</td>
			</tr>
			<html:form
				action="/back/backForum/orgForumPostMan/orgForumPostManAction"
				method="post">
				<logic:notEmpty name="PROJ_FORUM_POST_MAN_FORM" property="remarksForList">
					<logic:iterate id="remark" name="PROJ_FORUM_POST_MAN_FORM"
						property="remarksForList">
						<tr bgcolor="#FFFFFF" onMouseOver="this.bgColor='#DFEDEA'"
							; onMouseOut="this.bgColor='#FFFFFF'">
							<td nowrap>
								<bean:write name="remark" property="orgId" />
							</td>
							<td nowrap>
								<bean:write name="remark" property="title" />
							</td>
							<td nowrap>
								<bean:write name="remark" property="body" />
							</td>
							<td nowrap>
								<bean:write name="remark" property="creatorName" />
							</td>
							<td nowrap>
								<bean:write name="remark" property="creationTime" format="yyyy-MM-dd"/>
							</td>
							<td nowrap>
								<a
									href="<%=request.getContextPath()%>/back/backForum/orgForumPostMan/orgForumPostManAction.do?action=deleteRemark&threadId=<bean:write
						name="PROJ_FORUM_POST_MAN_FORM" property="postThreadId"/>&remarkId=<bean:write
						name="remark" property="id"/>&<%=GlobalConstant.PAGE_NO%>=<%=myPageNo%>"
									onclick="return comfirmDelete();">删除 </a>
							</td>
						</tr>
					</logic:iterate>
					<tr bgcolor="#FFFFFF">
						<td colspan=10 align=right>
							<div align="right">
								<jsp:include page="/pub/navigate/navigation.jsp" flush="true">
									<jsp:param name="pageFormUrl"
										value="/back/backForum/orgForumPostMan/orgForumPostManAction.do" />
									<jsp:param name="pageFormAct" value="searchRemark" />
								</jsp:include>
							</div>
						</td>
					</tr>
				</logic:notEmpty>
			</html:form>
		</table>
		<br>
		<table width="100%" border="0" cellpadding="2" cellspacing="1"
			class="tablebg">
			<tr bgcolor="#FFFFFF" onMouseOver="this.bgColor='#DFEDEA'"
				; onMouseOut="this.bgColor='#FFFFFF'">
				<td nowrap>
					<div align="left">
						系统说明：
						<br>
						1.修改帖子信息：；
						<br>
					</div>
				</td>
			</tr>
		</table>
<script language="javascript">
	new CalendarCtrl('startTime',"<%=request.getContextPath()%>/js");       
	new CalendarCtrl('endTime',"<%=request.getContextPath()%>/js");	
</script>
	</body>
</html>

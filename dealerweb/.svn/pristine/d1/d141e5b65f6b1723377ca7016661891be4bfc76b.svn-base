<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.resource.GlobalConstant"%>
<%@ page import="com.ava.util.TypeConverter"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.ava.domain.entity.DataDictionary"%>
<%@ page
	import="com.ava.backForum.postMan.presentation.form.ProjForumPostManForm"%>
<%
	int numPerRow = 10;
ProjForumPostManForm postForm = (ProjForumPostManForm) request.getAttribute("PROJ_FORUM_POST_MAN_FORM");
	
	Iterator itor0 = null;
	int rowCount0 = 0;
	if (postForm.getForumSortList() != null) {
		List nodeList = postForm.getForumSortList();	
		itor0 = nodeList.iterator();		
		rowCount0 = nodeList.size() / numPerRow;
		int surplusCount = nodeList.size() % numPerRow;
		if (surplusCount > 0 && surplusCount < numPerRow) {
	rowCount0++;
		}
	}

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
							讨论区
						</div>
					</td>
					<td colspan="5" bgcolor="#FFFFFF">
					<%
					for (int i = 0; i < rowCount0; i++) {
					%>
					<%
						for (int k = 0; k < numPerRow; k++) {
							if (itor0 != null && itor0.hasNext()) {
								DataDictionary node = (DataDictionary) itor0.next();
					%>
						<html:radio name="PROJ_FORUM_POST_MAN_FORM"
							property="forumSortId"
							value="<%=TypeConverter.toString(node.getId())%>" />
						<%=node.getNodeName()%>
					<%
							}
						}
					}
					%>
					</td>
				</tr>
				<tr>
					<td class="bgcolor" width="5%">
						<div align="right">
							标题
						</div>
					</td>
					<td width="10%" bgcolor="#FFFFFF">
						<html:text size="10" name="PROJ_FORUM_POST_MAN_FORM"
							property="threadForSearch.title" />
					</td>
					<td class="bgcolor" width="5%">
						<div align="right">
							正文
						</div>
					</td>
					<td width="10%" bgcolor="#FFFFFF" colspan="3">
						<html:text size="10" name="PROJ_FORUM_POST_MAN_FORM"
							property="threadForSearch.body" />
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
							property="threadForSearch.creatorName" />
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
					<td colspan="6" align="center" class="40td">
						<html:submit property="action.searchThread" value="搜  索"
							styleClass="bgcolor" />
						<html:reset property="clear" value="清　空" styleClass="bgcolor" />
					</td>
				</tr>
			</html:form>
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
		<table width="100%" border="0" cellpadding="2" cellspacing="1"
			class="tablebg">
			<tr class="bgcolor">
				<td width="3%" nowrap>
				<div align="center">选择</div>
				</td>
				<td width="3%" nowrap align="center">
					组织
				</td>
				<td width="44%" nowrap align="center">
					标题
				</td>
				<td width="5%" nowrap align="center">
					回复数量
				</td>
				<td width="5%" nowrap align="center">
					浏览数量
				</td>
				<td width="10%" nowrap align="center">
					创建者
				</td>
				<td width="10%" nowrap align="center">
					创建时间
				</td>
				<td width="20%" nowrap align="center">
					操作
				</td>
			</tr>
			<html:form
				action="/back/backForum/orgForumPostMan/orgForumPostManAction"
				method="post">
				<logic:notEmpty name="PROJ_FORUM_POST_MAN_FORM" property="orgForumThreadsForList">
					<logic:iterate id="thread" name="PROJ_FORUM_POST_MAN_FORM"
						property="orgForumThreadsForList">
						<tr bgcolor="#FFFFFF" onMouseOver="this.bgColor='#DFEDEA'" onMouseOut="this.bgColor='#FFFFFF'">
							<td nowrap align="center"><input type="radio" name="postThreadId" value="<bean:write name='thread'
										property='id' format='0' />">
							</td>
							<td nowrap>
								<bean:write name="thread" property="orgId" />
							</td>
							<td nowrap>
								<string:write name="thread" property="title" cut="60"/>
							</td>
							<td nowrap>
								<bean:write name="thread" property="replyCount" />
							</td>
							<td nowrap>
								<bean:write name="thread" property="viewCount" />
							</td>
							<td nowrap>
								<bean:write name="thread" property="creatorName" />
							</td>
							<td nowrap>
								<bean:write name="thread" property="creationTime" format="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td nowrap align="center">
								<a
									href="<%=request.getContextPath()%>/back/backForum/orgForumPostMan/orgForumPostManAction.do?action=searchRemark&threadId=<bean:write name="thread"
									property="id" />&<%=GlobalConstant.PAGE_NO%>=<%=myPageNo%>">所有回帖</a>
								<a
									href="<%=request.getContextPath()%>/back/backForum/orgForumPostMan/orgForumPostManAction.do?action=deleteThread&threadId=<bean:write
									name="thread" property="id"/>&<%=GlobalConstant.PAGE_NO%>=<%=myPageNo%>"
									onclick="return comfirmDelete();">删除 </a>
								<a
									href="<%=request.getContextPath()%>/frontForum/forumLeaf/forumLeafAction.do?action=displayForumLeaf&threadId=<bean:write name="thread"
									property="id" />&<%=GlobalConstant.PAGE_NO%>=<%=myPageNo%>">查看</a>
							</td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
				<tr bgcolor="#FFFFFF">
					<td colspan=10 align=right>
						<div align="right">
							<jsp:include page="/pub/navigate/navigation.jsp" flush="true">
								<jsp:param name="pageFormUrl"
									value="/back/backForum/orgForumPostMan/orgForumPostManAction.do" />
								<jsp:param name="pageFormAct" value="searchThread" />
							</jsp:include>
						</div>
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td colspan="10" align="center" class="40td">
						<html:submit property="action.topPost" value="置  顶"
							styleClass="bgcolor" />
						<html:submit property="action.bottomPost" value="沉  底"
							styleClass="bgcolor" />
						<html:submit property="action.finePost" value="加为精华"
							styleClass="bgcolor" />
					</td>
				</tr>
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
						1.置顶：把帖子置顶；
						<br>
						2.沉底：把帖子沉底；
						<br>
						3.加为精华：把帖子加为精华；
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

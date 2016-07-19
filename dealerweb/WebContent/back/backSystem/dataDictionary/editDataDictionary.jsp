<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<%@ include file="/pub/jstl.jsp" %>
<html>
<head>
	<jsp:include page="/back/backMeta.jsp" flush="true">
		<jsp:param name="titleInner" value="" />
	</jsp:include>
</head>
<body topmargin="0" bottommargin="0">
	<table width="100%" border="0" cellpadding="2" cellspacing="1" class="border">
		<tr align="center">
			<td height=25 colspan=2 bgcolor="#39867B">
				<div align="left" class="style4">
					<strong>当前位置：</strong>系统管理&gt; 数据字典&gt;字典设置
				</div>
		</tr>
		<tr>
			<td width="81%" height=23 nowrap class="bgcolor">
				<strong>管理菜单：<a href="/back/dataDictionary/displayTree.vti?sortId=<c:out value="${editedNode.sortId}"/>" target="_self">
				<c:out value="${editedNode.nodeSortName}"/>设置</a></strong>
			</td>
			<td width="23%" height=23 valign="bottom" nowrap class="bgcolor">
				<div align="right">
					<a href="javascript:history.back();"><img
							src="<%=request.getContextPath()%>/back/images/backto.gif" border="0"> </a><a
						href="javascript:window.location.reload();"><img
							src="<%=request.getContextPath()%>/back/images/refresh.gif" border="0"> </a>
				</div>
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="2"
		class="tablebg">
		<form action="/back/dataDictionary/edit.vti" method="post">
			<tr>
				<td width="32%" class="bgcolor">
					<div align="right">
						编号：
					</div>
				</td>
				<td width="68%" bgcolor="#FFFFFF">
					<c:out value="${editedNode.id}"/>
					<input type="hidden" name="editedNodeId" value="<c:out value="${editedNode.id}"/>"/>
				</td>
			</tr>
			<tr>
				<td width="32%" class="bgcolor">
					<div align="right">
						当前修改人：
					</div>
				</td>
				<td bgcolor="#FFFFFF">
					<c:out value="${editedNode.currentModificatorName}"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="bgcolor">
					修改日期：
				</td>
				<td bgcolor="#FFFFFF">
					<fmt:formatDate value="${editedNode.currentModificationTimestamp}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<td width="32%" class="bgcolor">
					<div align="right">
						名称：
					</div>
				</td>
				<td bgcolor="#FFFFFF">
					<input type="text" name="name" value="<c:out value="${editedNode.name}"/>"/>
				</td>
			</tr>
			<!-- 
			<tr>
				<td width="32%" class="bgcolor">
					<div align="right">
						关联值：
					</div>
				</td>
				<td bgcolor="#FFFFFF">
					<input type="text" name="linkedValue" value="<c:out value="${editedNode.linkedValue}"/>"/>
				</td>
			</tr>-->
			<tr>
				<td width="32%" class="bgcolor">
					<div align="right">
						排序位置：
					</div>
				</td>
				<td bgcolor="#FFFFFF">
					<input type="text" name="rank" value="<c:out value="${editedNode.rank}"/>"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="bgcolor">
					备注：
				</td>
				<td bgcolor="#FFFFFF">
					<input type="text" name="nodeComment" value="<c:out value="${editedNode.comment}"/>"/>
				</td>
			</tr>
			<tr bgcolor="#FFFFFF">
				<td align="center" colspan="3" class="40td">
					<input type="submit" value="修改" />
					<input type="reset" value="返回"
						onclick="javascript:history.back();" />
				</td>
			</tr>
		</form>
	</table>
	<br>
	<table width="100%" border="0" cellpadding="2" cellspacing="1"
		class="tablebg">
		<tr bgcolor="#FFFFFF" onMouseOver="this.bgColor='#DFEDEA'" onMouseOut="this.bgColor='#FFFFFF'">
			<td nowrap>
			<div align="left">系统说明：<br>
			1.排序位置：数字越小越靠前<br>
			</div>
			</td>
		</tr>
	</table>
</body>
</html>

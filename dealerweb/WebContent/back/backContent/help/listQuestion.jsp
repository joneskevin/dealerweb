<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.resource.GlobalConstant"%>
<%@ page import="com.ava.util.TypeConverter"%>
<%@ include file="/pub/jstl.jsp" %>
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
</head>
<body>
<table width="100%" border="0" cellpadding="2" cellspacing="1"
	class="border">
	<tr align="center">
		<td height=25 colspan=2 bgcolor="#39867B">
		<div align="left"><span class="style4"><strong>当前位置：</strong>帮助中心 &gt; 帮助列表</span></div>
	</tr>
	<tr>
		<td width="82%" height=23 nowrap class="bgcolor"><strong>管理菜单：</strong>
			<a href='/back/question/displayAdd.vti' target="_self">新增问题</a>
		</td>
		<td width="23%" height=23 valign="bottom" nowrap class="bgcolor">
		<div align="right"><a href="javascript:history.back();"><img
			src="/back/images/backto.gif" border="0"></a><a
			href="javascript:window.location.reload();"><img
			src="/back/images/refresh.gif" border="0"></a></div>
		</td>
	</tr>
</table>
<form id="myPageForm" action="/back/question/search.vti" method="get">
<table width="100%" border="0" cellpadding="0" cellspacing="2"
	class="tablebg">	
	<tr valign="bottom" bgcolor="#FFFFFF">
		<td height="40" class="40td">
		<table width="100%" border="0" cellpadding="3" cellspacing="1"
			class="tablebg">
			<tr valign="bottom" bgcolor="#FFFFFF">
				<td width="14%" class="bgcolor"><div align="right">标题</div></td>
				<td width="14%" align="left">
					<form:input path="question.title"/>
				</td>
				<td width="14%" height="26" align="right" class="bgcolor">问题类别</td>
				<td align="left">
			        <form:select path="question.sortLevelId" items="${questionSortsLevel3IdList}" itemLabel="optionText" itemValue="optionValue">
			        </form:select>
                </td>
			</tr>
			<tr align="center" bgcolor="#FFFFFF">
				<td colspan="4" height="40" class="40td">
					<input name="" type="submit" value="搜  索" class="bgcolor">
					<input name="" type="reset" value="重 置" class="bgcolor">
				</td>
			</tr>
		</table>
		</td>
	</tr>	
</table>
<table width="100%" border="0" cellpadding="2" cellspacing="1" class="tablebg">
		<tr class="bgcolor">
			<td width="20%" height="25" nowrap><div align="center">标题</div></td>
			<td><div align="center">内容</div></td>
			<td width="15%" nowrap><div align="center">操作</div></td>
		</tr>
		<c:if test="${questionList != null}">
		<c:forEach var="question" items="${questionList}">
		<tr bgcolor="#FFFFFF" onMouseOver="this.bgColor='#DFEDEA'" onMouseOut="this.bgColor='#FFFFFF'">
            <td align="center">
	            <p title='${question.title}'>
	            <c:choose>
					<c:when test="${fn:length(question.title) > 15}">
						${fn:substring(question.title, 0, 15)}...
					</c:when>
					<c:otherwise>
						${question.title}
					</c:otherwise>
				</c:choose>
	            </p>
            </td>
            <td align="left" title='${question.content}'>
           		<c:out value="${fn:length(question.content) > 60 ? fn:substring(question.content, 0, 60) : question.content}" escapeXml="false"/>
           		<c:out value="${fn:length(question.content) > 60 ? '...' : ''}" escapeXml="false"/>
            </td>
			<td align="center">
				<a href='/back/question/displayEdit.vti?questionId=${question.id}'>查看/修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href='/back/question/delete.vti?questionId=${question.id}&<%=GlobalConstant.PAGE_NO%>=<%=myPageNo%>' onclick="return comfirmDelete();">删除</a>
			</td>
		</tr>
		</c:forEach>
		</c:if>
		<tr bgcolor="#FFFFFF">
			<td colspan=40 align=right>
			<div align="right">
			<jsp:include page="/pub/navigate/navigation.jsp" flush="true"/></div>
			</td>
		</tr>
</table>
</form>
<script language = "JavaScript">
function comfirmDelete(){
          if(confirm("确定要删除该帮助信息吗？")){
            return true;
          }else{
         return false;
       }
}
</script>
</body>
</html>

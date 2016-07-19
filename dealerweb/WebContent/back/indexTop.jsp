<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.resource.SessionManager" %>
<html>
<head>
<title>后台管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
a:link { color:#000000;text-decoration:none}
a:hover {color:#666666;}
a:visited {color:#000000;text-decoration:none}

td {FONT-SIZE: 9pt; FILTER: dropshadow(color=#FFFFFF,offx=1,offy=1); COLOR: #000000; FONT-FAMILY: "宋体"}
img {filter:Alpha(opacity:100); chroma(color=#FFFFFF)}
</style>
<base target="main">
<script>
function preloadImg(src)
{
	var img=new Image();
	img.src=src
}
preloadImg("<%=request.getContextPath()%>/back/images/admin_top_open.gif");

var displayBar=true;
function switchBar(obj)
{
	if (displayBar)
	{
		parent.frame.cols="0,*";
		displayBar=false;
		obj.src="<%=request.getContextPath()%><%=request.getContextPath()%>/back/images/admin_top_open.gif";
		obj.title="打开左边管理菜单";
	}
	else{
		parent.frame.cols="161,*";
		displayBar=true;
		obj.src="<%=request.getContextPath()%><%=request.getContextPath()%>/back/images/admin_top_close.gif";
		obj.title="关闭左边管理菜单";
	}
}

function confirm_logout(){
	if(confirm("确定要注销吗?")){
		return true;
	}else{
		return false;
    }
}
</script>
</head>

<body background="<%=request.getContextPath()%>/back/images/admin_top_bg.gif" leftmargin="0" topmargin="0">
<table width="100%" height="100%" border=0 cellpadding=0 cellspacing=0>
  <tr valign=middle> 
    <td width=6% nowrap><img onClick="switchBar(this)" src="<%=request.getContextPath()%>/back/images/admin_top_close.gif" title="关闭左边管理菜单" style="cursor:hand"> </td>
    <td width=15%>窗口缩放</td>
    <td width=2%>&nbsp;</td>
    <td width="32%"><div align="right"><img src="<%=request.getContextPath()%>/back/images/admin.gif" width="29" height="28"></div></td>
    <td width="32%" align="right" nowrap>
	<div align="left"><strong> &nbsp;用户登录名：</strong>
	    <%=SessionManager.getCurrentOperatorLoginName(request)%>
&nbsp;&nbsp;<strong>用户姓名：</strong>
	    <%=SessionManager.getCurrentOperatorName(request)%>
    </div></td>
    <td width="4%" align="right"><div align="right"><img src="<%=request.getContextPath()%>/back/images/zhuxiao.gif" width="24" height="24"></div></td>
    <td width="9%" align="right"><div align="right"><a href="/back/operator/logout.vti" target="_top" onclick="return confirm_logout();"><strong>&nbsp;退出登录</strong></a>&nbsp;</div></td>
  </tr>
</table>
</body>
</html>

<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.resource.GlobalConstant" %>
<%@ page import="com.ava.resource.SessionManager" %>
<html>
<head>
<title>后台管理系统</title>
<style type=text/css>
body  { background:url(<%=request.getContextPath()%>/back/images/bg_left.gif); margin:0px; font:9pt 宋体; font-size: 9pt;text-decoration: none;
scrollbar-face-color: #c6ebde;
scrollbar-highlight-color: #ffffff; scrollbar-shadow-color: #39867b; scrollbar-3dlight-color: #39867b; scrollbar-arrow-color: #330000; scrollbar-track-color: #e2f3f1; scrollbar-darkshadow-color: #ffffff;}
table  { border:0px; }
td  {
	font-family: "宋体";
	font-size: 12px;
	font-style: normal;
	font-weight: normal;
	font-variant: normal;
	color: #333333;
}
img  { vertical-align:bottom; border:0px; }
a  { font:normal 12px "宋体"; color:#25564F; text-decoration:none; }
a:hover  { color:#cc0000;text-decoration:underline; }
.sec_menu  { border-left:1px solid white; border-right:1px solid white; border-bottom:1px solid white; overflow:hidden; background:#ECF9F4; }
.third_menu  { border-left:1px solid white; border-right:1px solid white; border-bottom:1px solid white; overflow:hidden; background:#ECF9F4; }
.menu_title  { }
.menu_title span  { position:relative; top:2px; left:8px; color:#39867b; font-weight:bold; }
.menu_title2  { }
.menu_title2 span  { position:relative; top:2px; left:8px; color:#cc0000; font-weight:bold; }
.2menu {
	font-family: "宋体";
	font-size: 9pt;
	font-weight: bold;
	color: #666666;
}
</style>

<SCRIPT language=javascript1.2>
function showsubmenu(sid){
	whichEl = eval("submenu" + sid);
	if (whichEl.style.display == "none")
	{
		eval("submenu" + sid + ".style.display=\"\";");
	}
	else
	{
		eval("submenu" + sid + ".style.display=\"none\";");
	}
}

function confirm_logout(){
	if(confirm("确定要注销吗·")){
		return true;
	}else{
		return false;
    }
}
</SCRIPT>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">
<table width=100% cellpadding=0 cellspacing=0 border=0 align=left>
	<tr>
		<td valign=top>
		<table width=158 border="0" align=center cellpadding=0 cellspacing=0>
			<tr>
				<td height=42 valign=bottom><img src="<%=request.getContextPath()%>/back/images/admin_left_title.gif" width=158
					height=38></td>
			</tr>
		</table>
		
		<!-- 系统管理 -->
		<table width=158 border="0" align=center cellpadding=0 cellspacing=0>
			<tr>
				<td height=25 class=menu_title
					background="<%=request.getContextPath()%>/back/images/menu_system.gif" id=menuTitle1
					onClick="showsubmenu(1)" style="cursor:hand;">&nbsp;</td>
			</tr>
			<tr>
				<td style="display:none" id='submenu1'>
				<div class=sec_menu style="width:158">
				<table width=100% border="0" align=center cellpadding=0
					cellspacing=0>	
					<%
					if (SessionManager.getCurrentOperator(request).hasRightOfManageOperator()){		
					%>
					<tr onMouseOver="this.bgColor='#FFFFFF'"
						onMouseOut="this.bgColor='#ECF9F4'">
						<td width=15>&nbsp;</td>
						<td width="141" height=20>
						<div align="left"><a href="javascript:void(0);"
							onClick="showsubmenu(11)" class="2menu">[操作员]</a></div>
						</td>
					</tr>
					<tr>
						<td colspan=2 style="display:none" id='submenu11'>
						<div class=third_menu>
						<table width=100% border="0" align=center cellpadding=0
							cellspacing=0>
							<%
							if (SessionManager.getCurrentOperator(request).hasRightOfMaintOperator()){			
							%>
							<tr onMouseOver="this.bgColor='#FFFFFF'"
								onMouseOut="this.bgColor='#ECF9F4'">
								<td width="25" height=18>&nbsp;</td>
								<td width="129">
								<div align="left"><a
									href="<%=request.getContextPath()%>/back/operator/search.vti"
									target="main">·操作员维护</a>
								</div>
								</td>
							</tr>
							<%
							}
							%>
							<%
							if (SessionManager.getCurrentOperator(request).hasRightOfEditPassword()){			
							%>
							<tr onMouseOver="this.bgColor='#FFFFFF'"
								onMouseOut="this.bgColor='#ECF9F4'">
								<td width="25" height=18>&nbsp;</td>
								<td width="129">
								<div align="left"><a
									href="/back/operator/displayEditPassword.vti"
									target="main">·修改密码</a></div>
								</td>
							</tr>
							<%
							}
							%>
						</table>
						</div>
						</td>
					</tr>
					<%
					}
					%>			
					<%
					if (SessionManager.getCurrentOperator(request).hasRightOfManageDictionary()){		
					%>
					<tr onMouseOver="this.bgColor='#FFFFFF'"
						onMouseOut="this.bgColor='#ECF9F4'">
						<td width=15>&nbsp;</td>
						<td width="141" height=20>
						<div align="left"><a href="javascript:void(0);" 
							onClick="showsubmenu(12)" class="2menu">[数据字典]</a></div>
						</td>
					</tr>
					
					<tr>
						<td colspan=2 style="display:none" id='submenu12'>
						<div class=third_menu>
						<table width=100% border="0" align=center cellpadding=0
							cellspacing=0>	
							<%
							if (SessionManager.getCurrentOperator(request).hasRightOfSetDictionary()){		
							%>
							<tr onMouseOver="this.bgColor='#FFFFFF'"
								onMouseOut="this.bgColor='#ECF9F4'">
								<td width="25" height=18>&nbsp;</td>
								<td>
								<div align="left"><a
									href='/back/dataDictionary/displayTree.vti?sortId=<%=GlobalConstant.DICTIONARY_AREA%>'
									target="main">·设置字典</a></div>
								</td>
							</tr>
							<%
							}
							%>
						</table>
						</div>
						</td>
					</tr>
					
					<tr onMouseOver="this.bgColor='#FFFFFF'"
						onMouseOut="this.bgColor='#ECF9F4'">
													
						<td width=15>&nbsp;</td>
						<td width="141" height=20>
							<div align="left"><a href="javascript:void(0);" 
							onClick="showsubmenu(13)" class="2menu">[日志管理]</a></div>
						</td>
					</tr>
					
						<tr>
						<td colspan=2 style="display:none" id='submenu13'>
						<div class=third_menu>
						<table width=100% border="0" align=center cellpadding=0
							cellspacing=0>	
							<%
							if (SessionManager.getCurrentOperator(request).hasRightOfSetDictionary()){		
							%>
							<tr onMouseOver="this.bgColor='#FFFFFF'"
								onMouseOut="this.bgColor='#ECF9F4'">
								<td width="25" height=18>&nbsp;</td>
								<td>
								<div align="left"><a
									href="/back/userLog/search.vti"
									target="main">·用户日志</a></div>
						
								</td>
								</td>
							</tr>
							<%
							}
							%>
							
							<%
							if (SessionManager.getCurrentOperator(request).hasRightOfSetDictionary()){		
							%>
							<tr onMouseOver="this.bgColor='#FFFFFF'"
								onMouseOut="this.bgColor='#ECF9F4'">
								<td width="25" height=18>&nbsp;</td>
								<td>
								<div align="left"><a
									href="/back/operatorLog/search.vti"
									target="main">·管理员日志</a></div>
						
								</td>
								</td>
							</tr>
							<%
							}
							%>
						</table>
						</div>
						</td>
					</tr>
				     
				     
				 
				      
					<%
					}
					%>
				</table>
				</div>
				</td>
			</tr>
		</table>		
		
		<!-- 论坛管理 -->
		<!-- 
		<table cellpadding=0 cellspacing=0 width=158 align=center>
			<tr>
				<td height=25 class=menu_title
					background="<%=request.getContextPath()%>/back/images/menu_forum.gif" id=menuTitle2
					onClick="showsubmenu(2)" style="cursor:hand;">&nbsp;</td>
			</tr>
			<tr>
				<td style="display:none" id='submenu2'>
				<div class=sec_menu style="width:158">
				<table width=100% border="0" align=center cellpadding=0
					cellspacing=0>	
					<tr onMouseOver="this.bgColor='#FFFFFF'"
						onMouseOut="this.bgColor='#ECF9F4'">
						<td width="15">&nbsp;</td>
						<td width="139" height=20>
						<div align="left"><a href="javascript:void(0);"
							onClick="showsubmenu(21)" class="2menu">[帖子]</a></div>
						</td>
					</tr>
					<tr>
						<td colspan=2 style="display:none" id='submenu21'>
						<div class=third_menu>
						<table width=100% border="0" align=center cellpadding=0
							cellspacing=0>
							<tr onMouseOver="this.bgColor='#FFFFFF'"
								onMouseOut="this.bgColor='#ECF9F4'">
								<td width="25" height=18>&nbsp;</td>
								<td width="127">
								<div align="left"><a href="/back/backForum/postMan/postManAction.do?action=searchThread" target="main">·帖子维护</a></div>
								</td>
							</tr>
							<tr onMouseOver="this.bgColor='#FFFFFF'"
								onMouseOut="this.bgColor='#ECF9F4'">
								<td width="25" height=18>&nbsp;</td>
								<td width="127">
								<div align="left"><a href="/back/backForum/postMan/postManAction.do?action=searchPost" target="main">·帖子迁移</a></div>
								</td>
							</tr>
						</table>
						</div>
						</td>
					</tr>	
				</table>
				</div>
				</td>
			</tr>
		</table> -->

		<!-- 内容管理 -->
		<%
		if (SessionManager.getCurrentOperator(request).hasRightOfManageContent()){	
		%>
		<table cellpadding=0 cellspacing=0 width=158 align=center>
			<tr>
				<td height=25 class=menu_title
					background="<%=request.getContextPath()%>/back/images/menu_content.gif" id=menuTitle3
					onClick="showsubmenu(3)" style="cursor:hand;">&nbsp;</td>
			</tr>
			<tr>
				<td style="display:none" id='submenu3'>
				<div class=sec_menu style="width:158">
				<table cellpadding=0 cellspacing=0 align=center width=100%>
					<tr onMouseOver="this.bgColor='#FFFFFF'" onMouseOut="this.bgColor='#ECF9F4'">
						<td width="15">&nbsp;</td>
						<td width="139" height=20>
						<div align="left"><a href="javascript:void(0);"
							onClick="showsubmenu(31)" class="2menu">[帮助信息]</a></div>
						</td>
					</tr>
					<tr>
						<td colspan=2 style="display:none" id='submenu31'>
						<div class=third_menu>
						<table width=100% border="0" align=center cellpadding=0 cellspacing=0>
						<!-- 
							<tr onMouseOver="this.bgColor='#FFFFFF'" onMouseOut="this.bgColor='#ECF9F4'">
								<td width="25" height=18>&nbsp;</td>
								<td>
								<div align="left"><a href='/back/backTeam/helpCenter/helpCenterAction.do?action=searchHelpCenter' target="main">·帮助中心</a></div>
								</td>								
							</tr>
							<tr onMouseOver="this.bgColor='#FFFFFF'" onMouseOut="this.bgColor='#ECF9F4'">
								<td width="25" height=18>&nbsp;</td>								
								<td>
								<div align="left"><a href='/back/backTeam/helpCenter/helpCenterAction.do?action=displayHelpCenterAdd' target="main">·新增帮助</a></div>
								</td>
							</tr>
						 -->	
							<tr onMouseOver="this.bgColor='#FFFFFF'" onMouseOut="this.bgColor='#ECF9F4'">
								<td width="25" height=18>&nbsp;</td>								
								<td>
								<div align="left"><a href='/back/question/search.vti' target="main">·BASE帮助中心</a></div>
								</td>
							</tr>
							<tr onMouseOver="this.bgColor='#FFFFFF'" onMouseOut="this.bgColor='#ECF9F4'">
								<td width="25" height=18>&nbsp;</td>								
								<td>
								<div align="left"><a href='/back/question/displayAdd.vti' target="main">·BASE新增帮助</a></div>
								</td>
							</tr>
							
						</table>
						</div>
						</td>
					</tr>					
				</table>
				</div>
				</td>
			</tr>
		</table>	
		<%
		}
		%>
		
		</td>
		<!--主框架结束-->
	</tr>
</table>
</body>
</html>

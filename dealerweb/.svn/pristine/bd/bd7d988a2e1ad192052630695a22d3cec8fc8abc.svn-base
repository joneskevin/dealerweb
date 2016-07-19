<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.ava.resource.GlobalConstant"%>
<%
 String sortId = (String)request.getParameter("sortId");
%>
<html>
<head>
<jsp:include page="/back/backMeta.jsp" flush="true">
	<jsp:param name="titleInner" value="" />
</jsp:include>
</head>

<script language=JavaScript>

//右击菜单生成
function createPopupMenu(id, e){
    dTree.selectItem(id);

	var bro = $.browser;

	var _event = getEvent(); 
	var _target;
	if(bro.msie) {
		_target = $(_event.srcElement);
	}else if(bro.mozilla) {
    	_target = $(_event.target);
    }else if(bro.webkit) {
    	_target = $(_event.target);
    } else {
    	_target = $(_event.target);
    } 
    var _menuPanel = $("#menuPanel" );
    _menuPanel.empty();
    _menuPanel.append(""
    	+"<div class='dtree_nodeRow'><a href='/back/dataDictionary/displayAdd.vti?currentNodeId=" + id + "'>新建下级节点</a></div>" 
		+"<div class='dtree_nodeRow'><a href='/back/dataDictionary/displayEdit.vti?currentNodeId=" + id + "'>编辑节点资料</a></div>" 
		+"<div class='dtree_nodeRow'><a href='javascript:void(0);' onclick='deleteNode("+ id +")'>删除</a></div>" 
	);

    var targetHtml =  _target.html();
    if(targetHtml == "" || targetHtml.indexOf("span") >= 0 || targetHtml.indexOf("SPAN") >= 0 ){
    	_menuPanel.css("display", "none"); 
    	return;
    }
	var positionX = _target.offset().top;
	var positionY = _target.offset().left + _target.width() + 15;
	_menuPanel.css("top", positionX); 
	_menuPanel.css("left", positionY); 
	
	_menuPanel.css("display", ""); 
	_menuPanel.focus();
};

function deleteNode(){
	var childsCount = dTree.hasChildren(dTree.getSelectedItemId());		
	if(childsCount == null){
		 return alert("请选择要删除的组织!");
	}				
	if(childsCount > 0){
		 return alert("不能删除有下级组织的组织!");
	}
	if(confirm('确定要删除该组织吗？')){	 	
		var targetUrl = "/back/dataDictionary/delete.vti?";
		targetUrl += "&currentNodeId=" + dTree.getSelectedItemId();
		targetUrl += "&r=" + Math.random();
		$.ajax({
			url: targetUrl,
		    type: 'GET',
		    dataType: 'json',
		    timeout: 10000,
		    error: function(){
		        alert('系统异常');
		    },
		    success: function(responseData){
			    if(responseData.code == 1){
					dTree.deleteItem(dTree.getSelectedItemId(),true);
		    		jAlert(responseData.message);
			    }else{
		    		jAlert(responseData.message);
			    }
		    }
		});
	}
}

window.onload = function() {
	document.body.onclick=checkClick;
}
function checkClick(evt){
	var _target = evt ? evt.target : event.srcElement ;
	var _id = _target.id;
	if( _id == "" ){
		_id = _target.parentNode.id;
	}
	if(  _id.indexOf( "menuPanel" ) > -1 ){
		;
	}else{
		Show( "menuPanel" , false );
	}
}
</script>

<body topmargin="0" bottommargin="0">
<div id="menuPanel" style="width:180px;position:absolute;top:230px;left:650px; background:#EFF7FF;z-index:999;border:1px solid #96C2F1;display:none;" class="infobar">管理员</div>	
<table width="100%" border="0" cellpadding="2" cellspacing="1"
	class="border">
	<tr align="center">
		<td height=25 colspan=2 bgcolor="#39867B">
		<div align="left" class="style4"><strong>当前位置：</strong>系统管理&gt;数据字典&gt;字典设置</div>
	</tr>
	<tr>
		<td width="81%" height=23 nowrap class="bgcolor"><strong>管理菜单：
			<a href='/back/dataDictionary/displayTree.vti?sortId=<%=GlobalConstant.DICTIONARY_AREA%>' target='_self'>地区设置</a>
			<a href='/back/dataDictionary/displayTree.vti?sortId=<%=GlobalConstant.DICTIONARY_INDUSTRY_SORT%>' target='_self'>行业分类设置</a>
			<a href='/back/dataDictionary/displayTree.vti?sortId=<%=GlobalConstant.DICTIONARY_NEWS_SORT%>' target='_self'>新闻类别设置</a>
			<a href='/back/dataDictionary/displayTree.vti?sortId=<%=GlobalConstant.DICTIONARY_BASE_HELP_CENTER%>' target='_self'>Base帮助中心设置</a></strong>
		</td>
		<td width="23%" height=23 valign="bottom" nowrap class="bgcolor">
			<div align="right">
				<a href="javascript:history.back();"><img
						src="/back/images/backto.gif" border="0"></a>
			</div>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="2"
	class="tablebg">
		<tr valign="bottom" bgcolor="#FFFFFF">
			<td>
				&nbsp;&nbsp;&nbsp;<a  href='/back/dataDictionary/displayAdd.vti?levelId=1&sortId=${sortId}'>新增一级节点</a>
		   </td>
		</tr>
</table>

<div class="tree">
	<div id="tree_dataDictionary" >
	<script>
		$().ready(function(){
	        $(document).bind("contextmenu",function(e){  
	            return false;//disabled right click 
	        }); 
			$("body").bind("click", function(event){
				var _target = event.target;
				var _id = _target.id;		
				if( _id == "" ){
					_id = _target.parentNode.id;
				}
				if(_id.indexOf( "menuPanel" ) > -1 ){
				}else{
					$( "#menuPanel" ).css("display", "none"); 
				}		
			});
		});	
				
		var rightlist = null;
		dTree=new dhtmlXTreeObject("tree_dataDictionary","100%","100%",0);
		dTree.setImagePath("/js/dxTree/images/");
		dTree.setOnClickHandler();	//设置鼠标左键点击事件
		dTree.setOnRightClickHandler(createPopupMenu);	//设置鼠标右键点击事件
		dTree.loadXMLString("${dhtmlXtreeXML}");//要用双引号，单引号会出错
		dTree.closeAllItems();//itemId不指定值，则合拢根节点
	</script>
	</div>
</div>

</body>
</html>

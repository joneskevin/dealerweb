<!-- **************************** JSP代码区域  **************************** -->
<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.ava.resource.GlobalConstant"%>
<%
	String defaultTitle = "ava_title"; //网页默认title
	//搜索区域是否显示的状态
	String path4meta = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- **************************** CSS代码区域 **************************** -->
<style type="text/css" title="currentStyle">
	@import "/js/jquery/jquery-ui-1.10.2.css";
	@import "/back/css/back.css";
</style>
<!--[if lt IE 9]>
<script type="text/javascript" src = "<%=path4meta%>/js/pngfix.js"></script>
<![endif]-->

<!-- **************************** Javascript代码区域 **************************** -->
<!-- jquery 1.12 will be the last one with official support for ie 6/7 -->
<script type="text/javascript" src = "<%=path4meta%>/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src = "<%=path4meta%>/js/jquery/jquery-ui-1.10.2.min.js"></script>
<script type="text/javascript" src = "<%=path4meta%>/js/jquery/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src = "<%=path4meta%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src = "<%=path4meta%>/js/basic.js"></script>
<script type="text/javascript" src = "<%=path4meta%>/js/PopupMenuDiv.js"></script>
<script type="text/javascript" src = "<%=path4meta%>/js/SelectCtrlUtil.js"></script>
<script src = "<%=path4meta%>/js/dxTree/dhtmlXUtil.js"></script>

<script type="text/javascript">
var s_dataTablesObj;//dataTables控件实例的全局变量
var msgItems = new Array();
<%
//*********** 以下是系统处理结果消息提示流程 ************************
String msgLevel = "";
String msgContentWithCommonKey = "";
String msgSpecificKey = "";
String msgContentWithSpecificKey = "";
//系统处理结果的消息MAP
Map<String, String> actionMessageMap = (Map)request.getAttribute(GlobalConstant.MSG_RESULT_MAP_ID);
if(actionMessageMap != null){
	msgLevel = (String)request.getAttribute(GlobalConstant.MSG_RESULT_LEVEL);
	msgContentWithCommonKey = actionMessageMap.get(GlobalConstant.MSG_RESULT_CONTENT);
       for (Iterator iterator = actionMessageMap.keySet().iterator(); iterator.hasNext();) {
    	   msgSpecificKey = String.valueOf(iterator.next());
		if(! msgSpecificKey.equalsIgnoreCase(GlobalConstant.MSG_RESULT_CONTENT)){
			msgContentWithSpecificKey = String.valueOf(actionMessageMap.get(msgSpecificKey));
%>
			msgItems["<%=msgSpecificKey%>"] = "<%=msgContentWithSpecificKey%>";
<%
		}
	}
}
%>
</script>

<script type="text/javascript">
//全局的ajax访问，处理ajax清求时sesion超时  
$.ajaxSetup({   
    contentType:"application/x-www-form-urlencoded;charset=utf-8",   
    complete:function(XMLHttpRequest,textStatus){
    	//通过XMLHttpRequest取得响应头
        var navigate2login = XMLHttpRequest.getResponseHeader("navigate2login"); 
        if(navigate2login == "login"){   
			//如果需要导航到登录页 
			window.location.replace("/fleetOS/general/logout.kyx");   
		}   
    }   
});
$().ready(function(){
	displayMsgOnLoad();	
});

function displayMsgOnLoad(){
	if($('#randomKey').val()=="randomKey"){
		var msgContentWithCommonKey = "<%=msgContentWithCommonKey%>";
		if(msgContentWithCommonKey == null || msgContentWithCommonKey == 'null'){
			msgContentWithCommonKey = "";
		}
		var msgLevel = "<%=msgLevel%>";
		if(msgLevel == null || msgLevel == 'null'){
			msgLevel = "<%=GlobalConstant.MSG_RESULT_LEVEL_SUCCESS%>";
		}
		
		var obj = document.getElementById("msg_result");
		if (obj){//提示条风格显示消息
			//对于带有通用key的消息的显示
			if(msgContentWithCommonKey != null && msgContentWithCommonKey != ''){
				obj.innerHTML = msgContentWithCommonKey;
				obj.className = "msgResult" + msgLevel;
				obj.style.display = "block";
				setTimeout('eval("h(\'' + obj.id + '\')")', 6000);
			}
		}else{//弹出框风格显示消息
			//对于带有通用key的消息的显示
			if(msgContentWithCommonKey != null && msgContentWithCommonKey != ''){
				alert(msgContentWithCommonKey, "来自系统的消息");
				//alert(msgContentWithCommonKey);//在closeDialog.jsp中jAlert弹不出来
			}

			//对于带有特定key的消息的显示
			for (var msgKey in msgItems) {
				var msgValue = msgItems[msgKey];
				if(msgValue == null || msgValue == 'null'){
					msgValue = "";
				}
				if(msgValue != null && msgValue != ''){
					var msgObj = $('#commonAlertError');
					msgObj.css("color", "#bd4247");
					msgObj.css("border-color", "#bd4247");
					msgObj.css("margin", "0");
					
					var tipModalHtml = "";
					tipModalHtml += "<div class='alertError'>";
					tipModalHtml += "<strong>" + msgValue + "</strong>";
					tipModalHtml += "</div>";
					msgObj.after(tipModalHtml);
				}
			}
		}
	}
	//重新初始化相关变量，表示提示消息已经显示过一次
	$('#randomKey').val("reload");
	msgItems = [];
}
</script>

<!-- **************************** 页面公用标签区域 **************************** -->
<input type="hidden" name="randomKey" id="randomKey" value="randomKey"/>

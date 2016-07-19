<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.ava.resource.GlobalConstant"%>
<%@ page import="java.util.List"%>
<%@ page import="com.ava.resource.*"%>
<%@ page import="com.ava.util.*"%>
<%@ page import="com.ava.domain.entity.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
	String path = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css" title="currentStyle">
	@import "<%=path%>/js/jquery/jquery-ui-1.10.2.css";
	@import "<%=path%>/js/dxTree/css/dhtmlXTree.css";
	@import "<%=path%>/dealer/css/base.css";
	@import "<%=path%>/dealer/css/main.css";
	@import "<%=path%>/dealer/css/table_style.css";
	@import "<%=path%>/dealer/css/main_nav_gather.css";
	@import "<%=path%>/dealer/css/popup.css";
	@import "<%=path%>/dealer/css/style.css";
</style>

<!-- **************************** Javascript代码区域 **************************** -->
<!-- jquery 1.12 will be the last one with official support for ie 6/7 -->
<script type="text/javascript" src="<%=path%>/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery/jquery-ui-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery/datetime/jquery-ui-timepicker-addon.js"></script>
<!--
<script type="text/javascript" src="<%=path%>/js/jquery/datetime/jquery-ui-timepicker-zh-CN.js"></script>
-->
<script type="text/javascript" src="<%=path%>/js/basic.js"></script>
<script type="text/javascript" src="<%=path%>/js/PopupMenuDiv.js"></script>
<script type="text/javascript" src="<%=path%>/js/SelectCtrlUtil.js"></script>
<script src="<%=path%>/js/dxTree/dhtmlXCommon.js"></script>
<script src="<%=path%>/js/dxTree/dhtmlXTree.js"></script>
<script src="<%=path%>/js/dxTree/dhtmlXUtil.js"></script>
<script type="text/javascript" src="<%=path%>/js/multiFile/jquery.MetaData.js"></script>
<script type="text/javascript" src="<%=path%>/js/multiFile/jquery.MultiFile.js"></script>
<!-- biz related script start -->
<script type="text/javascript" src="<%=path%>/dealer/js/main.js"></script>
<script type="text/javascript" src="<%=path%>/dealer/js/datePicker/WdatePicker.js"></script>
<!-- <script type="text/javascript" src="<%=path%>/dealer/js/DD_belatedPNG_0.0.8a-min.js"></script> -->

<!--[if lte IE 9]>
<script type="text/javascript" src="<%=path%>/dealer/js/html5.js"></script>
<script type="text/javascript">
    var html5Tags=['article', 'aside', 'details', 'figcaption', 'figure', 'footer', 'header', 'hgroup', 'nav', 'menu', 'section', 'summary'];
    for(var i=0;i<html5Tags.length;i++){ document.createElement(html5Tags[i]); }
</script>
<![endif]-->

<!-- biz related script end -->
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

function confirmTipWindow(e, warningText, fnName, parameters) {
	e.preventDefault();
	$('#global_warningText').text(warningText);
	$('#global_submitBtn').attr("name", fnName);
	$('#global_submitBtn_parameters').val(parameters);
	
	$('#global_warningModal').modal('show');
	return false;
}
$().ready(function(){
	displayMsgOnLoad();
	
	/** 设置列表的样式 */
	setTableListStyle();
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
// 				setTimeout('eval("h(\'' + obj.id + '\')")', 6000);
// 				setTimeout("alert('5seconds!')",5000)
			}
		}else{//弹出框风格显示消息
			//对于带有通用key的消息的显示
			if(msgContentWithCommonKey != null && msgContentWithCommonKey != ''){
				jAlert(msgContentWithCommonKey, "来自系统的消息");
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

function setTableListStyle(tableListId){
	var containerId = "tableList";
	if(tableListId){
		containerId = tableListId;
	}
	var tableObj = document.getElementById(containerId);
	if(tableObj){
		var trArray = tableObj.getElementsByTagName("tr");
		var num = trArray.length;
		for(var i=0; i<num; i++){
			if(i%2==0){
				trArray[i].className="one";
			}else{
				trArray[i].className="two";
			}
		}
	}
}

</script>
<script type="text/javascript" src="<%=path%>/js/echarts/esl.js"></script>
<script type="text/javascript" src="<%=path%>/js/echarts/echarts.js"></script>
<!-- **************************** 页面公用标签区域 **************************** -->
<input type="hidden" name="randomKey" id="randomKey" value="randomKey"/>
<div class="modal hide fade" id="global_warningModal" style="display: none;" >
	<div class="modal-header" >
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3>提示</h3>
	</div>
	<div class="modal-body">
		<p id="global_warningText"></p>
	</div>
	<div class="modal-footer">
		<a id="global_closeBtn" href="#" class="btn" data-dismiss="modal">关 闭</a>
		<button id="global_submitBtn" name="" class="btn btn-primary">确 定</button>
		<input type="hidden" id="global_submitBtn_parameters" value=""/>
	</div>
</div>

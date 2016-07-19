<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.resource.cache.CacheShortMessageManager" %>
<%@ page import="com.ava.resource.SessionManager" %>

<%
	com.ava.domain.entity.User currentUser4header =  SessionManager.getCurrentUser(request);
	com.ava.domain.entity.User currentUserTopDown =  SessionManager.getCurrentUser(request);
%>
<!--  
<link rel=stylesheet type="text/css" href="<%=request.getContextPath()%>/dealer/css/base.css">
<link rel=stylesheet type="text/css" href="<%=request.getContextPath()%>/dealer/css/main.css">
-->
<script type="text/javascript">
var intervalID = 0;
var blinkTitleSwitch = 0;
var blinkTitle = parent.document.title;
/** 闪动title框 */
function blinkNewMsg() {
	parent.document.title = blinkTitleSwitch % 2 == 0 ? "【　　　　】 - "
			+ blinkTitle : "【新站内消息】- " + blinkTitle;
	blinkTitleSwitch++;
}

/** 检查新站内消息的方法 */
function checkNewShortMessage() {
	var targetUrl = "<%=request.getContextPath()%>/base/shortMessage/checkNew.vti?r=" + Math.random();
	$.ajax({
		url: targetUrl,
	    type: 'GET',
	    dataType: 'json',
	    timeout: 10000,
	    error: function(){
	    },
	    success: function(responseData){
	    	 if(null!=responseData && null!=responseData.code && responseData.code == 1){
				var newShortMessageCount = responseData.firstItem;
				$("#newShortMessageCount").text(newShortMessageCount);
				
				/* if (blinkTitleSwitch == 0) {
					//when(blinkTitleSwitch=0) {start to enable alert("active blinkNewMsg()...");}
					intervalID = setInterval(blinkNewMsg, 3000);
				} else {
					clearInterval(intervalID);
					parent.document.title = blinkTitle;
				} */
		    }
	    }
	});
}

$(document).ready(function(){
	 autoNavigation();
	 setNoticeWidth();
	/** 轮询新站内消息的数量(10分钟) */
	//setInterval(checkNewShortMessage, 600000);
});
     
function cssSite(obj) {
 if (obj != null){
		var oUl=$("#mainMenu"); 
		var olis=oUl.children() ;  
		//获得oul-li的id集合
		var hrefName;
		var liName;
			for (var i = 0; i < olis.length; i++) { 
			liName = olis[i].id;
			hrefName = liName.substr(0,liName.indexOf("_li"));
			if (hrefName == obj.id) {
				$("#"+hrefName).attr("class","active");
			} else {
				$("#"+hrefName).removeClass("active");
			}
		}
	}
}
function setNoticeWidth(){
	/**notince begin*/
	var speed = 50; //每秒50px的速度滚动
	var c1 = $('#noticeLink'), c2 = $('#moveContent');
	var w1 = c1.width(),    w2 = c2.width();
	var ww = w1 + w2;
	var tt = ww / speed * 3000;
	c1.appendTo(c2).css("marginLeft", w2+"px").show().animate({"marginLeft": "-="+ww+"px"}, tt, "linear");
	window.setInterval(function(){
	        c1.css("marginLeft", w2+"px").animate({"marginLeft": "-="+ww+"px"}, tt, "linear");
		}, tt + 500);
	 /**notince end*/
}
</script>

<div id="hd">
	<h1><a href="<%=request.getContextPath()%>/dealer/index.jsp" class="text_over" target="_parent">logo</a></h1>

	<div class="clearfix hd_nav"><ul id="mainMenu" class="clearfix">
		<li id="main_li"><a id="main" href="<%=request.getContextPath()%>/base/index/displayMain.vti" class="active" onclick="cssSite(this)" target="iframe_right">主界面</a></li>
		<li id="site_li"><a id="site" href="<%=request.getContextPath()%>/dealer/userMenu/listUserMenu.vti" onclick="cssSite(this)" target="iframe_right">设置</a></li>
		<li id="help_li"><a id="help" href="<%=request.getContextPath()%>/base/question/search.vti" onclick="cssSite(this)" target="iframe_right">帮助</a></li>
		<li id="link_li"><a id="linkOur" href="<%=request.getContextPath()%>/base/question/linkOur.vti" onclick="cssSite(this)" target="iframe_right">联系我们</a></li>
		<li ><a href="<%=request.getContextPath()%>/base/registerLogin/logout.vti" onclick="cssSite(this)" target="_parent">退出</a></li>
	</ul></div>
	<p>
		<strong id="user"><%=currentUser4header.getLoginName()%></strong>
		欢迎进入系统！
		<a href="<%=request.getContextPath()%>/base/shortMessage/inboxList.vti" target="iframe_right">站内消息
		<%-- (<em id="newShortMessageCount"><%=CacheShortMessageManager.getNewShortMessageCount(currentUserTopDown.getId())%></em>)</a> --%>
		(<em id="newShortMessageCount"><%=CacheShortMessageManager.getNewShortMessageCount(currentUserTopDown.getId())%></em>)</a>
	</p>
	<div class="notice">
		<div id="moveContent"></div>
		<div id="noticeLink">
		<c:if test="${noticeList != null}" >
				<c:forEach var="notice" items="${noticeList}" varStatus="statusObj">
				    <a href="javascript:openWindow('<%=path%>/base/notice/view.vti?id=${notice.id}', 750, 300, '查看公告') " style="margin-right:30px" >${notice.title}</a>
					<!-- <a href=# onclick=javascript:openWindow('"+serviceBaseUrl+"/base/notice/view.vti?id=" + id +"',600,200,'查看公告') style='margin-right:30px' >" + title +"</a> -->
				</c:forEach>
		</c:if>
		</div>
	</div>

</div><!--/#hd-->

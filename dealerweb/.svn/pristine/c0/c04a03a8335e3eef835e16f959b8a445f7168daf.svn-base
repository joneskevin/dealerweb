<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>设置</title>
	<%@ include file="/dealer/include/meta.jsp"%>
  <script>
  var left_array = []; //左栏布局 
  $(function(document) {
    $( "#sortable1, #sortable2" ).sortable({
      connectWith: ".connectedSortable",
      
    }).disableSelection();
    
//     $( "#sortable1" ).sortable({
//     	  over: function( event, ui ) {
//     		  var oUl=$("#sortable1"); 
//     		  var olis=oUl.children();
//     		  if (olis.length > 6 ) {
//     			  alert("只能添加6个快捷菜单");
//     			  return;
//     		  }
//     	  }
//     });
    
  });
	
function save () {
	var oUl=$("#sortable1"); 
	var userMenus = []; //左栏布局 
	var olis = oUl.children() ;  
	if (olis.length > 6 ) {
		  alert("只能添加6个快捷菜单");
		  return;
	}
	//获得oul-li的id集合
	for (var i = 0; i < olis.length; i++) {  
		var li = olis[i].id.substr(8);
		userMenus.push(li);
	}
	if(olis.length == 0){
		userMenus.push(0);
	}
	var targetUrl = "<%=path%>/dealer/userMenu/editUserMenu.vti?userMenus=" + userMenus;
	 $.ajax({
	 url: targetUrl,
		  type: 'GET',
	      dataType: 'json',
	      timeout: 10000,
	      error: function(){
	         alert('系统异常');
	      },
	      success: function(responseData){
	     if (responseData) {
	    	 if (responseData.code == 1) {
	 	    	jAlert(responseData.message);
	 	     } else {
	 	        jAlert(responseData.message);
	 	     }
	     }
	    }
	 });
}
  </script>
</head>

<body>

<section class="bd" id="site">
<form id="myPageForm" action = "<%=path%>/dealer/userMenu/editUserMenu.vti" method="post" >
	<div class="bd_rt">
		<div class="bd_rt_nav">
			<div class="site_title clearfix">从右侧列表拖拽主界面所需显示的图表标签至左侧列表（支持排序）,只能设置6个快捷菜单</div>
		</div>
		<div class="Sortable clearfix">
		    <ul id="sortable1" class="connectedSortable">
		    <c:if test="${userMenuRelationVOList != null}" >
				<c:forEach var="userMenuRelationVO" items="${userMenuRelationVOList}" varStatus="statusObj">
			      <li id="menuName${userMenuRelationVO.menuId}" title="拖动">${userMenuRelationVO.menu.name}</li>
		  		</c:forEach>
		    </c:if>
		    </ul>
		     
		    <ul id="sortable2" class="connectedSortable">
		    <c:if test="${roleMenuList != null}" >
				<c:forEach var="roleMenu" items="${roleMenuList}" varStatus="statusObj">
			      <li id="menuName${roleMenu.id}" title="拖动">${roleMenu.name}</li>
		  		</c:forEach>
		    </c:if>
		      
		    </ul>
			<input id="confim" onclick="save()" name="" type="button" class="sortBtn" value="确  定" />
		</div>
	</div>
	</form>
</section>
<div class="bd_rt_ft" /><!-- 加最下面的灰色条 -->

</body>
</html>

<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<%
/**
用户快捷导航条
*/
      String menuId = (String)request.getAttribute("menuId");
%>
<script type="text/javascript">

</script>
<input type="hidden" name="menuId" value="${menuId}"/>
<input type="hidden" name="menuType" value="${menuType}"/>

<div class="nav_gather_main clearfix">
 	<ul class="clearfix" id="mainMenu">
		<c:if test="${userMenuRelationVOList != null}" >
		 	<c:set var="currentMenuId" value="<%=Integer.parseInt(menuId.trim())%>"/> 
			<c:forEach var="vo" items="${userMenuRelationVOList}" varStatus="status">
			
				   <c:if test="${fn:indexOf(vo.menu.links, '?') >= 1}">
				       <c:if test="${vo.menu.id == currentMenuId}">
              		 		 <li id="menu${vo.menu.id}_li"><a id="menu${vo.menu.id}" class="active" href="<%=request.getContextPath()%>${vo.menu.links}&menuId=${vo.menu.id}&menuType=1"  target="iframe_right">${vo.menu.name}</a></li>
              		  </c:if>
				       <c:if test="${vo.menu.id != currentMenuId}">
              		 		 <li id="menu${vo.menu.id}_li"><a id="menu${vo.menu.id}"  href="<%=request.getContextPath()%>${vo.menu.links}&menuId=${vo.menu.id}&menuType=1"  target="iframe_right">${vo.menu.name}</a></li>
              		  </c:if>
              	  </c:if>
              	  
              	  <c:if test="${fn:indexOf(vo.menu.links, '?') <= 0}">
					   <c:if test="${vo.menu.id == currentMenuId}">
	              		 	<li id="menu${vo.menu.id}_li"><a id="menu${vo.menu.id}" class="active" href="<%=request.getContextPath()%>${vo.menu.links}?menuId=${vo.menu.id}&menuType=1"  target="iframe_right">${vo.menu.name}</a></li>
	              	   </c:if>
					   <c:if test="${vo.menu.id != currentMenuId}">
	              		 	<li id="menu${vo.menu.id}_li"><a id="menu${vo.menu.id}" href="<%=request.getContextPath()%>${vo.menu.links}?menuId=${vo.menu.id}&menuType=1"  target="iframe_right">${vo.menu.name}</a></li>
	              	   </c:if>
              	 </c:if>
			  </li>
	  		</c:forEach>
   		</c:if>
     </ul>
</div>
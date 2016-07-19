<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<%@ include file="/dealer/include/meta.jsp"%>
<%@ page import="com.ava.domain.vo.UserMenuRelationVO"%>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="content-language" content="zh-cn" />
<title>试乘试驾</title>
<style tyle="text/css">
	html { overflow-y:hidden; }
</style>
<script type="text/javascript">
$(document).ready(function(){
	autoNavigation();
	});
</script>

</head>

<body style="min-height:500px">
<div class="bd_rt" id="statistics" style="min-height:500px">
		<div class="bd_rt_nav clearfix">
                  <div class="nav_gather clearfix">
                        <ul class="clearfix">
                              <c:if test="${userMenuRelationVOList != null && userMenuRelationVOList.size() > 0 }" >
								<c:forEach var="userMenuRelationVO" items="${userMenuRelationVOList}" varStatus="status">
								  <li> 
								  	<c:choose>
              						  <c:when test="${status.index == 0}">
              						  <c:if test="${userMenuRelationVO.menu.isParameter ==1 }">
              						   <a class="active" href="<%=path%>${userMenuRelationVO.menu.links}&menuType=1"  target="iframe_right">${userMenuRelationVO.menu.name}</a>
              						  </c:if>
              						  <c:if test="${userMenuRelationVO.menu.isParameter ==0 }">
              						   <a class="active" href="<%=path%>${userMenuRelationVO.menu.links}?menuType=1"  target="iframe_right">${userMenuRelationVO.menu.name}</a>
              						  </c:if>
             						   </c:when>
                						<c:otherwise>
              						    <c:if test="${userMenuRelationVO.menu.isParameter ==1 }">
              						    <a  href="<%=path%>${userMenuRelationVO.menu.links}&menuType=1"  target="iframe_right">${userMenuRelationVO.menu.name}</a>
              						    </c:if>
              						    <c:if test="${userMenuRelationVO.menu.isParameter ==0 }">
              						    <a  href="<%=path%>${userMenuRelationVO.menu.links}?menuType=1"  target="iframe_right">${userMenuRelationVO.menu.name}</a>
              						    </c:if>
							           </c:otherwise>
							        </c:choose>
								  </li>
						  		</c:forEach>
		   					 </c:if>
                        </ul>
                  </div>
<%-- 			<c:if test="${userMenuRelationVOList != null && userMenuRelationVOList.size() > 0}" > --%>
<!-- 			<p> -->
<!-- 				<span class="text_over" id="btn_prev">left</span> -->
<!-- 				<span class="text_over" id="btn_back">back</span> -->
<!-- 				<span class="text_over" id="btn_ahead">ahead</span> -->
<!-- 				<span class="text_over" id="btn_next">next</span> -->
<!-- 			</p> -->
<%-- 			</c:if> --%>

<!-- 			<select name="typeList" id="typeList"> -->
<!-- 				<option value="0">全国RBO</option> -->
<!-- 				<option value="1">S:全国RBO</option> -->
<!-- 				<option value="2">T:全国RBO</option> -->
<!-- 				<option value="3">C:全国RBO</option> -->
<!-- 			</select> -->
		</div>

		<aside class="bd_rt_main">
<!-- 			<ul class="type_explain clearfix"> -->
<!-- 				<li class="type_a clearfix"><i></i><span>要求配置</span></li> -->
<!-- 				<li class="type_b clearfix"><i></i><span>可选配置</span></li> -->
<!-- 				<li class="type_c clearfix"><i></i><span>实际安装</span></li> -->
<!-- 				<li class="type_d clearfix"><i></i><span>待安装</span></li> -->
<!-- 			</ul> -->

<%-- 			<div id="histogram"><img src="<%=path%>/dealer/images/histogram.jpg" width="100%"></div><!--/#histogram--> --%>
				<c:if test="${userMenuRelationVOList.size() == 0 }" >
		   					 	<div style="text-align:center;font-size:12px;" >请到设置功能添加您的快捷菜单</div>
		   		</c:if>

<!-- 			<ul class="type_btn clearfix"> -->
<!-- 				<li class="type_btn_a"><span>外出报备审核</span></li> -->
<!-- 				<li class="type_btn_b"><span>维修报备审核</span></li> -->
<!-- 				<li class="type_btn_c"><span>试驾率环比</span></li> -->
<!-- 				<li class="type_btn_d"><span>单周试驾率</span></li> -->
<!-- 				<li class="type_btn_e"><span>补贴发放</span></li> -->
<!-- 				<li class="type_btn_f"><span>图片导出</span></li> -->
<!-- 			</ul> -->
		</aside>
</div>
<c:if test="${userMenuRelationVOList.size() == 0 }" >
<div class="bd_rt_ft"/>
</c:if>
</body>
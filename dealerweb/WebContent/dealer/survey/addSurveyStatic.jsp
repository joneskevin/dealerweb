<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<head>
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">   
    	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">   
    	<META HTTP-EQUIV="Expires" CONTENT="0">   
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
$(function(){ 
	$(':radio:checked').each(function(i) {
		var type = $("#type").val();
		var size = $("#size").val();
		debugger;
		var selectTenCarStyles = $(":radio[name='selectTenCarStyles']:checked").val();
		if ((type == 2 && size > 2) 
				|| (type == 3 && selectTenCarStyles != null && size > 1) 
				|| (type == 3 && (typeof(selectTenCarStyles) == "undefined" || selectTenCarStyles == null) && size > 3) ) {
		    $(':radio').attr("checked",false); 
		 }
	});
}); 

function clickTenCarStyles(){
	var carStyles = $("#selectTenCarStyles:checked").val();
	if (carStyles == 13) {
		$(':radio').attr("disabled",true);
		$(':radio').attr("checked",false); 
	} else {
		$(':radio').attr("disabled",false); 
	}
}
</script>
<body>
<form id="myPageForm" action="<%=path%>/dealer/survey/addSurveyStatic.vti"" method="post" >
 	<form:hidden id="type"  path="surveyResultAdd.type"/>
 	<form:hidden id="size"  path="surveyResultAdd.size"/>
	<div class="bd_rt" id="audit">
		<div class="bd_rt_nav clearfix">
			<c:if test="${menuType == 1}" >
			<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
			</c:if>
			<c:if test="${menuType == 0}" >
			<span >当前位置：系统设置  > 车型配置调研</span>
			</c:if>
		</div>
		<div class='msgResult<html:errors property="MSG_RESULT_LEVEL"/>' id='msg_result' style="DISPLAY:none;"><html:errors property="MSG_RESULT"/></div>
		<div style="margin: 10px 20px;font-weight: bold;color: red;">2016年的试乘试驾配置车的调研：<br>为更好的推进经销商落实和贯彻上汽大众试乘试驾管理标准，倾听经销商声音，完善试乘试驾政策，使潜在用户得以充分享受到试乘试驾服务与体验，<br>从而提升客户满意度和成交转化率。现对以下可选车型进行调研，现请各位经销商选择各自期望配置的对应车型;
		</div>
	    <div style="padding: 10px 20px; height:345px; overflow: auto;">
	        <c:if test="${surveyResultAdd.type != null && surveyResultAdd.type == 1}">
	        <div style="margin: 0 0 10px 0px;font-weight: bold;color: red; font-size:16px;">注：New Passat、All New Touran L 已完成订单下达，在配置中，不在本次调研范围之内；Tiguan 1.8T、Lamando 1.4T、Lamando GTS以及C Model 为必配车型，将在配置时下发通知和订单；</div>
	        <div style="margin: 0 0 10px 0px;font-weight: bold;color: red; font-size:16px;">以下四项为单选，每项必须选择一个车型</div>
	            <div class="surveyAnswer clearfix ">
	          	 1、二选一(必选) 
	                <form:radiobutton path="surveyResultAdd.selectFirstCarStyles" value="1" onclick="a(this)" style=" margin:0 5px 0 10px" />New Lavida 1.6L
	                <form:radiobutton path="surveyResultAdd.selectFirstCarStyles" value="2" style=" margin:0 5px 0 10px" />New Lavida 1.2T Bluemotion
	            </div>
	            <div class="surveyAnswer clearfix ">
			2、二选一(必选)
	                <form:radiobutton path="surveyResultAdd.selectSecondCarStyles" value="3" style=" margin:0 5px 0 10px" />Cross Lavida
	                <form:radiobutton path="surveyResultAdd.selectSecondCarStyles" value="4" style=" margin:0 5px 0 10px" />Cross Santana
	            </div>
	            <div class="surveyAnswer clearfix ">
			3、二选一(必选)
	                <form:radiobutton path="surveyResultAdd.selectThreeCarStyles" value="5" style=" margin:0 5px 0 10px" />Polo GTI
	                <form:radiobutton path="surveyResultAdd.selectThreeCarStyles" value="6" style=" margin:0 5px 0 10px" />Cross Polo
	            </div>
	            <div class="surveyAnswer clearfix ">
			4、二选一(必选)
	                <form:radiobutton path="surveyResultAdd.selectFourCarStyles" value="7" style=" margin:0 5px 0 10px" />New Santana 1.6L
	                <form:radiobutton path="surveyResultAdd.selectFourCarStyles" value="8" style=" margin:0 5px 0 10px" />不配置New Santana 1.6L
	            </div>
	        </c:if>
	        
	        <c:if test="${surveyResultAdd.type != null && surveyResultAdd.type == 2}">
	        <div style="margin: 0 0 10px 0;font-weight: bold;color: red;  font-size:16px;">注：New Passat、All New Touran L已完成订单下达，在配置中，不在本次调研范围之内；C Model为必配车型，将在配置时下发通知和订单</div>
	        <div style="margin: 0 0 10px 0;font-weight: bold;color: red;  font-size:16px;">请在以下大类车型中必须选择两辆，每个大类车型仅可选择一辆车作为试乘试驾车</div>
	            <div class="surveyAnswer clearfix ">
	             	第一类
	             	<form:radiobutton path="surveyResultAdd.selectFiveCarStyles" value="9" style=" margin:0 5px 0 10px" />New Tiguan 1.8T
	            </div>
	            <div class="surveyAnswer clearfix ">
	            	第二类
	            	<form:radiobutton path="surveyResultAdd.selectFirstCarStyles" value="1" style=" margin:0 5px 0 10px" />New Lavida 1.6L
	                <form:radiobutton path="surveyResultAdd.selectFirstCarStyles" value="2" style=" margin:0 5px 0 10px" />New Lavida 1.2T Bluemotion
	            </div>
	            <div class="surveyAnswer clearfix ">
	            	第三类
	            	<form:radiobutton path="surveyResultAdd.selectSecondCarStyles" value="3" style=" margin:0 5px 0 10px" />Cross Lavida
	                <form:radiobutton path="surveyResultAdd.selectSecondCarStyles" value="4" style=" margin:0 5px 0 10px" />Cross Santana
	                
	            </div>
	            <div class="surveyAnswer clearfix ">
	            	第四类
	            	<form:radiobutton path="surveyResultAdd.selectThreeCarStyles" value="5" style=" margin:0 5px 0 10px" />Polo GTI
	                <form:radiobutton path="surveyResultAdd.selectThreeCarStyles" value="6" style=" margin:0 5px 0 10px" />Cross Polo
	                
	            </div>
	            <div class="surveyAnswer clearfix ">
	            	第五类
	            	<form:radiobutton path="surveyResultAdd.selectSixCarStyles" value="11" style=" margin:0 5px 0 10px" />Lamando 1.4T
	                <form:radiobutton path="surveyResultAdd.selectSixCarStyles" value="12" style=" margin:0 5px 0 10px" />Lamando GTS
	            </div>
	            <div class="surveyAnswer clearfix ">
	            	第六类
	                <form:radiobutton path="surveyResultAdd.selectFourCarStyles" value="7" style=" margin:0 5px 0 10px" />New Santana 1.6L
	            </div>
	        </c:if>
	        
	        <c:if test="${surveyResultAdd.type != null && surveyResultAdd.type == 3}">
	        <div style="margin: 0 0 10px 0px;font-weight: bold;color: red;  font-size:16px;">经销商可以选择0~3台试乘试驾车，如果选择配置试驾车辆，每个大类车型仅可选择一辆车作为试乘试驾车，最多不超过3辆</div>
	            <div class="surveyAnswer clearfix ">
	            	第一类
	            	<input type="checkbox" id="selectTenCarStyles" name="selectTenCarStyles" value="13" onclick="clickTenCarStyles();" style=" margin:0 5px 0 10px" />不配置试乘试驾车
	            </div>
	            <div class="surveyAnswer clearfix ">
	            	第二类
	            	<form:radiobutton path="surveyResultAdd.selectSevenCarStyles" value="14" style=" margin:0 5px 0 10px" />New Passat 1.8T
	                <form:radiobutton path="surveyResultAdd.selectSevenCarStyles" value="15" style=" margin:0 5px 0 10px" />New Passat 2.0T
	            </div>
	            <div class="surveyAnswer clearfix ">
	          		  第三类
	            	<form:radiobutton path="surveyResultAdd.selectEightCarStyles" value="16" style=" margin:0 5px 0 10px" />C Model
	            </div>
	            <div class="surveyAnswer clearfix ">
	            	第四类
	            	<form:radiobutton path="surveyResultAdd.selectFirstCarStyles" value="1" style=" margin:0 5px 0 10px" />New Lavida 1.6L
	                <form:radiobutton path="surveyResultAdd.selectFirstCarStyles" value="2" style=" margin:0 5px 0 10px" />New Lavida 1.2T Bluemotion
	            </div>
	            <div class="surveyAnswer clearfix ">
	            	第五类
	                <form:radiobutton path="surveyResultAdd.selectSecondCarStyles" value="3" style=" margin:0 5px 0 10px" />Cross Lavida
	                <form:radiobutton path="surveyResultAdd.selectSecondCarStyles" value="4" style=" margin:0 5px 0 10px" />Cross Santana
	            </div>
	            <div class="surveyAnswer clearfix ">
	            	第六类
	           		<form:radiobutton path="surveyResultAdd.selectThreeCarStyles" value="5" style=" margin:0 5px 0 10px" />Polo GTI
	                <form:radiobutton path="surveyResultAdd.selectThreeCarStyles" value="6" style=" margin:0 5px 0 10px" />Cross Polo
	            </div>
	            <div class="surveyAnswer clearfix ">
	            	第七类
	                <form:radiobutton path="surveyResultAdd.selectFourCarStyles" value="7" style=" margin:0 5px 0 10px" />New Santana 1.6L
	            </div>
	            <div class="surveyAnswer clearfix ">
	            	第八类
	                 <form:radiobutton path="surveyResultAdd.selectFiveCarStyles" value="9" style=" margin:0 5px 0 10px" />New Tiguan 1.8T
	            </div>
	            <div class="surveyAnswer clearfix ">
	            	第九类
	                <form:radiobutton path="surveyResultAdd.selectSixCarStyles" value="11" style=" margin:0 5px 0 10px" />Lamando 1.4T
	                <form:radiobutton path="surveyResultAdd.selectSixCarStyles" value="12" style=" margin:0 5px 0 10px" />Lamando GTS
	            </div>
	            <div class="surveyAnswer clearfix ">
	            	第十类
		            <form:radiobutton path="surveyResultAdd.selectNineCarStyles" value="10" style=" margin:0 5px 0 10px" />All New Touran L 1.4T
	            </div>
	        </c:if>
	        <div style="padding-bottom: 30px;">
	            <input name="" type="submit"  class="btn_submit" value="提 交" />
	        </div>
	    </div>
	
		<div class="bd_rt_ft" />
	</div>
</form>

</body>
</html>

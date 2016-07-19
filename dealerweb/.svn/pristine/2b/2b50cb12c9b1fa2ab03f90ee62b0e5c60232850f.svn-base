<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<head>
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">   
    	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">   
    	<META HTTP-EQUIV="Expires" CONTENT="0">   
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
</script>
<body>
<form id="myPageForm" action="<%=path%>/dealer/survey/add.vti"" method="post" >
    <form:hidden path="surveyResultAdd.firstSelectType"/>
    <form:hidden path="surveyResultAdd.secondSelectType"/>
    <form:hidden path="surveyResultAdd.threeSelectType"/>
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
		<div style="margin: 10px 20px;font-weight: bold;">温馨提示： 
		<span style="margin: 10px 20px;font-weight: bold;color: red;">2016年如果销商选择配置Passat GP 2.0T Flagship，为了降低经销商成本，总部将通过提高返点，使经销商以1.8T Highline的成本配置2.0TFlagship车型。</span>
		</div>
		<div style="margin: 10px 100px;font-weight: bold;">现对Passat车型进行调研，请经销商选择自己期望配置的车型。</div>
		<!-- <div style="margin: 10px 20px;font-weight: bold;color: red;">至少选其一：必须选择其中至少一款车型作为试乘试驾车<br>可选：至少选择一个车型或者只选择不配置Passat车型
		</div> //-->									
	    <div style="padding: 10px 20px; height:345px; overflow: auto;">
	        <c:if test="${surveyResultAdd.firstAllCarStyles != null && surveyResultAdd.firstAllCarStyles.size() > 0}">
	
	            <div class="surveyQuestion" style="margin-bottom: 0px;">
			<!--<span style="font-weight: bold;">第一项</span>//-->
	                <c:choose>
	                    <c:when test="${surveyResultAdd.firstSelectType == 1}">
	                        <!--(至少选其一)//-->
	                    </c:when>
	                    <c:otherwise>
	                        <!--(可选)//-->
	                    </c:otherwise>
	                </c:choose>
	
	            </div>
	            <div class="surveyAnswer clearfix ">
	                <form:radiobuttons items="${surveyResultAdd.firstAllCarStyles}" path="surveyResultAdd.selectFirstCarStyles" element="br" style=" margin:0 5px 0 10px" />
	            </div>
	
	        </c:if>
	        <c:if test="${surveyResultAdd.secondAllCarStyles != null && surveyResultAdd.secondAllCarStyles.size() > 0}">
	            <div class="surveyQuestion"><span style="font-weight: bold;">第二项</span>
	                <c:choose>
	                    <c:when test="${surveyResultAdd.secondSelectType == 1}">
	                        (至少选其一)
	                    </c:when>
	                    <c:otherwise>
	                        (可选)
	                    </c:otherwise>
	                </c:choose>
	            </div>
	            <div class="surveyAnswer clearfix">
	                <form:checkboxes items="${surveyResultAdd.secondAllCarStyles}" path="surveyResultAdd.selectSecondCarStyles" element="br" style="margin:0 5px 0 10px" />
	            </div>
	
	        </c:if>
	
	        <c:if test="${surveyResultAdd.threeAllCarStyles != null && surveyResultAdd.threeAllCarStyles.size() > 0}">
	            <div class="surveyQuestion"><span style="font-weight: bold;">第三项</span>
	                <c:choose>
	                    <c:when test="${surveyResultAdd.threeSelectType == 1}">
	                        (至少选其一)
	                    </c:when>
	                    <c:otherwise>
	                        (可选)
	                    </c:otherwise>
	                </c:choose>
	            </div>
	            <div class="surveyAnswer clearfix">
	                <form:checkboxes items="${surveyResultAdd.threeAllCarStyles}" path="surveyResultAdd.selectThreeCarStyles" element="br" style="margin:0 5px 0 10px" />
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

<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<head>
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">   
    	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">   
    	<META HTTP-EQUIV="Expires" CONTENT="0">   
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
$(function() {
    var targetUrl = "<%=path%>/dealer/survey/querySurveyLevel.vti";
    targetUrl += "?random=" + Math.random();
    $.ajax({
        url: targetUrl,
        type: 'GET',
        dataType: 'json',
        timeout: 10000,
        error: function() {
            alert('系统异常');
        },
        success: function(responseData) {
            if (responseData.code != 1) {
                $("#message").show();
                $("#message").html(responseData.message);
                $("#proposal").attr("disabled", "true");
                $("#proposal").css("background", "#D1D1D1");
            }
        }
    });
});

function comfirmSubmit() {
    if (confirm("确定要申请吗？")) {
        var targetUrl = "<%=path%>/dealer/survey/addSurveyLevel.vti";
        targetUrl += "?random=" + Math.random();
        $.ajax({
            url: targetUrl,
            type: 'POST',
            dataType: 'json',
            timeout: 10000,
            error: function() {
                alert('系统异常');
                $('#proposal').removeAttr("disabled")
                $("#proposal").css("background", "#10a0d9");
            },
            success: function(responseData) {
                $("#message").show();
                $("#message").html(responseData.message);
            }
        });
        $("#proposal").attr("disabled", "true");
        $("#proposal").css("background", "#D1D1D1");
    }
}
</script>
<body>
<form id="myPageForm" action="<%=path%>/dealer/survey/addSurveyLevel.vti" method="post" >
	<div class="bd_rt" id="audit">
		<div class="bd_rt_nav clearfix">
			<c:if test="${menuType == 1}" >
			<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
			</c:if>
			<c:if test="${menuType == 0}" >
			<span >当前位置：系统设置  > 车型配置调研</span>
			</c:if>
		</div>
		<div style="margin: 10px 20px;color: green;font-weight: bold; background-color: #D1EEEE;padding: 10 0 10 0;display: none;" id="message"></div>
		<div style="margin: 10px 20px;font-weight: bold;">目的： <br></div>
		<div style="margin: 10px 20px;font-weight: bold;color: red;">推动大众品牌C级车的市场示范效应； <br></div>
		<div style="margin: 10px 20px;font-weight: bold;color: red;">通过对C级车的优先体验，增强信心的同时，推动对服务升级的前瞻性差异化部署； <br></div>
		<div style="margin: 10px 20px;font-weight: bold;color: red;">提升大众品牌经销商总经理职业经理人形象；<br></div>
		<div style="margin: 10px 20px;font-weight: bold;color: red;">加强大众品牌经销商总经理投资人归属感。<br></div>
		<div style="margin: 10px 20px;font-weight: bold;">方案： <br></div>
		<div style="margin: 10px 20px;font-weight: bold;color: red;">配置车型：2.0T DL Comfort adv. <br></div>
		<div style="margin: 10px 20px;font-weight: bold;color: red;">数量：240台 。<br></div>
		<div style="margin: 10px 20px;font-weight: bold;color: red;">对象：大众品牌特许经销商投资方、总经理。 <br></div>
		<div style="margin: 10px 20px;font-weight: bold;color: red;">方式：2015年度星级经销商优先，自愿申请原则，先报先得，每家限额一台。 <br></div>
		<div style="margin: 10px 20px;font-weight: bold;color: red;">提报及截止时间：2016年XX月XX日至2016年XX月XX日。 <br></div>
	    <div style="padding: 10px 20px; height:345px; overflow: auto;">
	        <div style="padding-bottom: 30px;">
	            <input id = "proposal" name="" type="button"   class="btn_submit" onclick="comfirmSubmit()" value="申 请" />
	        </div>
	    </div>
	
		<div class="bd_rt_ft" />
	</div>
</form>

</body>
</html>

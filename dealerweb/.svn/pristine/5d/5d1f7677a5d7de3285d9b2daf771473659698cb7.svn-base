<%@ page contentType="text/html; charset=utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script type="text/javascript">
$(document).ready(function() {
	 $("#submitBut").click(function(){
			if(!window.confirm('你确定要重跑吗？')){
	             return;
	         }
		    $("#submitBut").attr("disabled",true);
		 	$("#message").html("");
		    $("#waitImg").show();
			var targetUrl = "<%=request.getContextPath()%>/admin/testDrive/rehandle.vti";
			//targetUrl += "&random=" + Math.random();
			$.ajax({
				url: targetUrl,
				type: 'POST',
				data:{'vin':$("#vin").val(),'startDate':$("#startDate").val(),'endDate':$("#endDate").val()},
				error: function() {
					 $("#message").html(系统异常);
					 $("#waitImg").hide();
					 $("#submitBut").attr("disabled",false);
				},
				success: function(responseData) {
					if (responseData) {
						$("#message").html(responseData);
					}else{
						$("#message").html("处理完成..");
					}
					$("#waitImg").hide();
				    $("#submitBut").attr("disabled",false);
				}
			});
	 });

});
</script>
<style>
 .rehandleMain{
    text-align: center;
    margin-top: 10px;
 }
 .re_ipt_txt{
  	border: 1px solid black;
  	height: 20px;
 }
</style>
<body>
<form id="myPageForm" action="<%=request.getContextPath()%>/admin/testDrive/rehandle.vti" method="post" >
<input type="hidden" name="proposalType" value="${proposalCompanyVehcileInfo.proposal.type}" />
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：运营管理  > 重跑试驾</span>
		</c:if>
	</div>
	<div class="rehandleMain">
	VIN
	<input type="text" id="vin"  class="re_ipt_txt" maxlength="17" size="17" />
	起始时间
	<input type="text" id="startDate" class="re_ipt_txt" maxlength="20" name="startTime"  id="txtStartTime" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" />
	结束时间
	<input type="text" id="endDate" class="re_ipt_txt" maxlength="20" name="endTime"  id="txtEndTime" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" />
	<input type="button" id="submitBut"  value="开始重跑" />
	</br>
	<font color="red" id="message"></font></br>
	<img src="<%=request.getContextPath()%>/images/wait.gif" alt="正在等待..." id="waitImg" style="display:none" />
	</div>

</div>
</form>
</body>
</html>
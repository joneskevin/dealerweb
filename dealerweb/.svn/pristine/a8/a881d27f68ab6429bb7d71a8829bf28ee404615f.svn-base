<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
$(document).ready(function(){
	//密码框不能复制粘贴
	$('input[type=password]').bind('copy paste',function(e){
		e.preventDefault();
	});
});
</script>
<body>
<form id="myPageForm" action="<%=path%>/base/user/editPersonalPassword.vti"" method="post" >
<form:hidden path="userEdit.id"/>
	<div class="bd_rt" id="audit">
		<div class="bd_rt_nav clearfix">
			<span >当前位置：系统设置  > 用户修改密码</span>
		</div>
		<aside class="bd_rt_main clearfix" >
		<div class='msgResult<html:errors property="MSG_RESULT_LEVEL"/>' id='msg_result' style="DISPLAY:none;"><html:errors property="MSG_RESULT"/></div>
		<div style="height:250px;">
			<table border="0" cellpadding="0" cellspacing="0" class="tableBox">
			<tr>
				<td class="tdTitle">用户名：</td>
				<td class="tdSpace"><c:out value="${user.loginName}" /></td>
			</tr>
			<tr>
				<td class="tdTitle"><span class="red">*</span>现在的密码：</td>
				<td class="tdSpace">
				<input type="password" name="pageOldPassword" maxlength="15" class="size" value="${pageOldPassword}" />请输入现在的密码
				</td>
			</tr>
			<tr>
				<td class="tdTitle"><span class="red">*</span>设置新的密码：</td>
				<td class="tdSpace">
				<input type="password" name="pagePassword" maxlength="16" class="size" value="${pagePassword}"/>6-16位，区分大小写，只能使用字母，数字
				</td>
			</tr>
			<tr>
				<td class="tdTitle"><span class="red">*</span>重复新的密码：</td>
				<td class="tdSpace"><label>
				<input type="password" name="pageConfirmPassword" maxlength="16" class="size" value="${pageConfirmPassword}"/>
			</label></td>
			</tr>
			<tr>
			<td colspan="4" class="tdSpace" style="padding-left:200px;">
		    <input name="" type="submit"  class="btn_submit" value="提 交" />
		    </td>
			</tr>
			</table>
		</div>	
		</aside>
		<div class="bd_rt_ft" /><!-- 加最下面的灰色条 -->
	</div>
</form>

</body>
</html>
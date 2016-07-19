<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <title>登录</title>
    <%@ include file="/dealer/include/meta.jsp" %>
        <link href="<%=path%>/dealer/css/login.css" rel="stylesheet" type="text/css" />
</head>
<script language=JavaScript>
$().ready(function() {
    if (window.top.frames.length > 0) {
        window.top.location.href = '<%=path%>/base/registerLogin/logout.vti';
    }
});

function checkOnLogin() {
    var loginName = document.getElementById("loginName").value;
    if (loginName == null || loginName.length == 0) {
        alert("用户名不能为空！");
        return false;
    }

    var loginPassword = document.getElementById("pseudoPassword").value;
    if (loginPassword == null || loginPassword.length == 0) {
        alert("登录密码不能为空！");
        return false;
    }

    // 	var validationCode = document.getElementById("verify").value;
    // 	if(validationCode == null || validationCode.length == 0){
    // 		alert("验证码不能为空！");
    // 		return false;
    // 	} 

    return true;
}

function refreshAuthenCode() {
    var authenCodeImg = document.getElementById("authenCodeImg");
    authenCodeImg.src = '<%=path%>/servlet/authenCodeImage?random=' + Math.random();
}
</script>

<body>
    <div id="login">
        <h1 class="text_over">logo</h1>
        <strong class="text_over">试乘试驾</strong>
        <form id="myPageForm" action="<%=path%>/base/registerLogin/login.vti" onsubmit="checkOnLogin()" method="post">
            <p class="clearfix">
                <em>用户名</em>
                <input type="text" id="loginName" value="" name="loginName"><span>用户名不能为空</span>
            </p>
            <p class="clearfix">
                <em>密码</em>
                <input type="password" id="pseudoPassword" value="" name="pseudoPassword"><span>密码错误</span>
            </p>
            <p class="clearfix">
                <em>验证码</em>
                <input type="text" id="verify" name="validationCode" value="${validationCode}" maxlength="4">
                <label onclick="refreshAuthenCode()" style="pointer:hand; width:106px;"><img id="authenCodeImg" src="<%=request.getContextPath()%>/servlet/authenCodeImage" width="53" height="25">
                </label>
                <span>验证码错误</span>
            </p>
            <p class="clearfix">
                <input type="submit" value="登 录" id="btn_login" onclick="return checkOnLogin()">
                <%-- <a href="javascript:openWindow('<%=path%>/base/company/displayRegister.vti', 600, 480, '新经销商注册')">新经销商注册</a> --%>
            </p>
        </form>
    </div>
</body>

</html>
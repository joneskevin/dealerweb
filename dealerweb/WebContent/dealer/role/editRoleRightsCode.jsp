<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<%
	User currentUser = SessionManager.getCurrentUser(request);
%>
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/main.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/style.css" />
</head>
<script language=JavaScript>
function setPermission(){
	var form1 = document.getElementById("myPageForm");
	form1.rightsCode.value=tree2.getAllCheckedBranches();
}


var tree2;
$(function(){
	tree2 = createTree('tree_rightsCode', "${dhtmlXtreeXML}", '${role.rightsCode}', true);
	
    $(document).bind("contextmenu",function(e){  
        return false;//disabled right click 
    }); 
});
</script>
<body>
<script>
</script>
<form id="myPageForm" action="<%=request.getContextPath()%>/base/role/editRoleRightsCode.vti?startIndex=${startIndex}"  method="post" >
	<div class="bd_rt" id="audit"  >
		<div class="bd_rt_nav clearfix" >
			<span >当前位置：运营管理  > 角色管理  > 分配权限 </span>
		</div>
		<aside class="bd_rt_main clearfix">
		<form:hidden path="role.id"/>
		<form:hidden path="role.rightsCode"/>
		<div id="tree_rightsCode"  style="overflow:auto;height: 450px; width:100%;" >
		</div>
		<div class=btnBox>
			<input name="" type="submit" class="btn_submit" onclick="setPermission();" value="保存设置" />
			  <a href="<%=path%>/base/role/search.vti" >返回上级</a>
		</div>
	</aside>
	<div class="bd_rt_ft" style="width:2000px;">
	</div>
	</div>
</form>

</body>
</html>
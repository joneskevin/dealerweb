<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<% User leftMenuCurrentUser = SessionManager.getCurrentUser(request); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
function comfirmDelete(targetUrl) {
    var pageFormObj = document.getElementById("myPageForm");
    if (confirm("确定要删除此记录吗？")) {
        targetUrl += "&random=" + Math.random();
        $.ajax({
            url: targetUrl,
            type: 'GET',
            dataType: 'json',
            timeout: 10000,
            error: function() {
                alert('系统异常');
            },
            success: function(responseData) {
                if (responseData.code == 1) {
                    pageFormObj.action = "<%=path%>/dealer/helpFile/list.vti";
                    var startIndex = "${transMsg.startIndex}";
                    if (startIndex != null && startIndex != "") {
                        $("#startIndex").val(startIndex);
                    }
                    pageFormObj.submit();

                } else {
                    jAlert(responseData.message);
                }
            }
        });
    }
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/helpFile/list.vti" method="get" >
<div class="bd_rt" id="audit">
	
	<div class="bd_rt_nav clearfix">
		<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
			<span>当前位置：系统设置  > 文档列表  </span>
		</c:if>
	 </div>
	  <div class="audit_nav_c">
		<strong><span>
		 <% if (leftMenuCurrentUser.getCompanyId() == 1) { %>
			<a href="javascript:openWindow('<%=path%>/dealer/helpFile/displayUpload.vti?startIndex=${transMsg.startIndex}', 660, 300, '上传文档')" >上传文档</a>
		 <% } %>
		</span></strong>
	</div>
	<aside class="bd_rt_main clearfix">
	<div class="table_list table_all" style="width:1200px;overflow-y:hidden;overflow:auto">
		<ul class="table_hd clearfix" >
			<li class="li_300">标题</li>
         	<li class="li_300">文件名</li>
         	<li class="li_300">描述</li>
         	<li class="li_200">操作</li>
		</ul>
		<c:if test="${helpFileList != null}" >
			<c:forEach var="helpFile" items="${helpFileList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'>
           		<li class="li_300"><span title="${helpFile.displayName}">${helpFile.displayName}</span></li>
           		<li class="li_300"><span title="${helpFile.originalName}">${helpFile.originalName}</span></li>
           		<li class="li_300"><span title="${helpFile.description}">${helpFile.description}</span></li>
           		<li class="li_200">
				<a href="<%=path%>/dealer/helpFile/getAttachFileDown.vti?helpFileId=${helpFile.id}&startIndex=${transMsg.startIndex}" />查看</a></td>
				 <% if (leftMenuCurrentUser.getCompanyId() == 1) { %>
					 <a href="#" onClick="javascript:comfirmDelete('<%=path%>/dealer/helpFile/delete.vti?helpFileId=${helpFile.id}&startIndex=${transMsg.startIndex}');">删除</a>
	    		 <% } %>
           		</li>
				</ul>
			</c:forEach>
		</c:if>
	</div>
	</aside>
	<div class="bd_rt_ft">
			<jsp:include page="/pub/navigate/navigation4dealer.jsp" flush="true"/>
	</div>
</div>
</form>
</body>
</html>
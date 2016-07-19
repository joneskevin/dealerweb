<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<body>

<form id="myPageForm" action="<%=path%>/dealer/box/list.vti" method="get" >
	<div class="bd_rt" id="audit">
		<div class="bd_rt_nav clearfix">
			<span>当前位置 > 设备管理  >  新增设备
				 <a href="<%=path%>/dealer/box/list.vti?startIndex=${startIndex}"
						target="main">返回列表</a>
			</span>
		</div>
		<div class="bd_rt_nav clearfix">
			<p class="audit_nav_c">
				   &nbsp;&nbsp; &nbsp;&nbsp;
				   所属单位：
				<form:select path='boxAdd.companyId'>
									<form:options items="${orgList}" itemLabel="name"
										itemValue="id" htmlEscape="false" />
				</form:select>
				 设备号：
				<form:input path="boxAdd.uniqueId" cssClass="ipt_txt"/>
			    SIM卡号：
				<form:input path="boxAdd.simId" cssClass="ipt_txt"/>
				通讯号：
				<form:input path="boxAdd.simMobile" cssClass="ipt_txt"/> 
				当前版本：
				<form:input path="boxAdd.currentVersion" cssClass="ipt_txt"/>
				
				<button  onclick='$("#startIndex").attr("value", "0");'/>提交</button>
			</p>
		</div>

	</div><!--/.bd_rt-->
</form>

</body>
</html>
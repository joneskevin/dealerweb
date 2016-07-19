<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<%@ page import="com.ava.domain.vo.CompanyCarStyleVO" %> 
<%
	List<CompanyCarStyleVO> companyCarStyleVOList = (List<CompanyCarStyleVO> )
			request.getAttribute("companyCarStyleVOList");
%>

<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<body>



<script>

var pageFormObj = document.getElementById("myPageForm");

function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/dealer/report/outputTestDrivePercentExcel.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
	pageFormObj.action="<%=path%>/dealer/report/listTestDrivePercent.vti";
} 
</script>
<form id="myPageForm" action="<%=path%>/dealer/report/listBigAreaInfo.vti" method="get" >
	<div class="bd_rt" id="audit">
		<div class="bd_rt_nav clearfix">
			<div>大区报表 > 大区</div>
			<div class="audit_nav_c">
				<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
				查询时间：
				 <input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${companyCarStyle.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
					-
				<input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" 	value="<fmt:formatDate value="${companyCarStyle.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
				
				<input type="submit"  class="btn_submit" value="查询" />
			</div>
		</div>

		<aside class="bd_rt_main clearfix">
			<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:${width+20}px;">
				
            		<li class="li_150"></li>
            		<li class="li_150"></li>
             		<c:if test="${carStyleList != null}" >
						<c:forEach var="carStyle" items="${carStyleList}">
							<li class="li_150">${carStyle.name}</li>
						</c:forEach>
					</c:if>
					
				</ul>
			
				<div>
				<c:if test="${companyCarStyleVOList != null}" >
				
				<!-- --------------------------------------------------------------->
				
				<c:forEach var="companyCarStyleVO" items="${companyCarStyleVOList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'style="width:${width+20}px;">
            		<c:if test="${companyCarStyleVO.configureRequireList != null}" >
						<c:forEach var="value" items="${companyCarStyleVO.configureRequireList}">
						<li class="li_150"><span title="${value}">${value}</span></li>
						</c:forEach>
					</c:if>
				</ul>
				
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'style="width:${width+20}px;">
            		<c:if test="${companyCarStyleVO.hasConfigureList != null}" >
						<c:forEach var="value" items="${companyCarStyleVO.hasConfigureList}">
						<li class="li_150"><span title="${value}">${value}</span></li>
						</c:forEach>
					</c:if>
				</ul>
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'style="width:${width+20}px;">
            		<c:if test="${companyCarStyleVO.hasNotConfigureList != null}" >
						<c:forEach var="value" items="${companyCarStyleVO.hasNotConfigureList}">
						<li class="li_150"><span title="${value}">${value}</span></li>
						</c:forEach>
					</c:if>
				</ul>
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'style="width:${width+20}px;">
            		<c:if test="${companyCarStyleVO.configurePercentList != null}" >
						<c:forEach var="value" items="${companyCarStyleVO.configurePercentList}">
						<li class="li_150"><span title="${value}">${value}</span></li>
						</c:forEach>
					</c:if>
				</ul>
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'style="width:${width+20}px;">
            		<c:if test="${companyCarStyleVO.chinaConfigurePercentList != null}" >
						<c:forEach var="value" items="${companyCarStyleVO.chinaConfigurePercentList}">
						<li class="li_150"><span title="${value}">${value}</span></li>
						</c:forEach>
					</c:if>
				</ul>
				
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'style="width:${width+20}px;">
            		<c:if test="${companyCarStyleVO.hopeFulCustomersList != null}" >
            			<c:forEach  var="value"  items="${companyCarStyleVO.hopeFulCustomersList}"  begin="0" end="5" step="1" >
						<li class="li_150"><span title="${value}">${value}</span></li>
						</c:forEach>
						<c:forEach  var="value"  items="${companyCarStyleVO.hopeFulCustomersList}"  begin="6" end="6" step="1" >
						<li class="li_300"><span title="${value}">${value}</span></li>
						</c:forEach>
						<c:forEach  var="value"  items="${companyCarStyleVO.hopeFulCustomersList}"  begin="7" end="${carStyleList.size()}" step="1" >
						<li class="li_150"><span title="${value}">${value}</span></li>
						</c:forEach>
					</c:if>
				</ul>
				
				<%-- <ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'style="width:${width+20}px;">
            		<c:if test="${companyCarStyleVO.hopeFulCustomersList != null}" >
						<c:forEach var="value" items="${companyCarStyleVO.hopeFulCustomersList}">
						<li class="li_100"><span title="${value}">${value}</span></li>
						</c:forEach>
					</c:if>
				</ul> --%>
				
				
				<%-- <ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'style="width:${width+20}px;">
            		<c:if test="${companyCarStyleVO.testDriveTimesList != null}" >
						<c:forEach var="value" items="${companyCarStyleVO.testDriveTimesList}">
						<li class="li_100"><span title="${value}">${value}</span></li>
						</c:forEach>
					</c:if>
				</ul> --%>
				
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'style="width:${width+20}px;">
            		<c:if test="${companyCarStyleVO.testDriveTimesList != null}" >
            			<c:forEach  var="value"  items="${companyCarStyleVO.testDriveTimesList}"  begin="0" end="5" step="1" >
						<li class="li_150"><span title="${value}">${value}</span></li>
						</c:forEach>
						<c:forEach  var="value"  items="${companyCarStyleVO.testDriveTimesList}"  begin="6" end="6" step="1" >
						<li class="li_300"><span title="${value}">${value}</span></li>
						</c:forEach>
						<c:forEach  var="value"  items="${companyCarStyleVO.testDriveTimesList}"  begin="7" end="${carStyleList.size()}" step="1" >
						<li class="li_150"><span title="${value}">${value}</span></li>
						</c:forEach>
					</c:if>
				</ul>
				
				<%-- <ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'style="width:${width+20}px;">
					
            		<c:if test="${companyCarStyleVO.testDrivePercentList != null}" >
						<c:forEach var="value" items="${companyCarStyleVO.testDrivePercentList}">
						<li class="li_100"><span title="${value}">${value}</span></li>
						</c:forEach>
					</c:if>
				</ul> --%>
				
				
				
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'style="width:${width+20}px;">
            		<c:if test="${companyCarStyleVO.testDrivePercentList != null}" >
            			<c:forEach  var="value"  items="${companyCarStyleVO.testDrivePercentList}"  begin="0" end="5" step="1" >
						<%-- <c:forEach  var="value" items="${companyCarStyleVO.testDrivePercentList}"> --%>
						<li class="li_150"><span title="${value}">${value}</span></li>
						</c:forEach>
						<c:forEach  var="value"  items="${companyCarStyleVO.testDrivePercentList}"  begin="6" end="6" step="1" >
						<li class="li_300"><span title="${value}">${value}</span></li>
						</c:forEach>
						<c:forEach  var="value"  items="${companyCarStyleVO.testDrivePercentList}"  begin="7" end="${carStyleList.size()}" step="1" >
						<li class="li_150"><span title="${value}">${value}</span></li>
						</c:forEach>
					</c:if>
				</ul>
			
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'style="width:${width+20}px;">
            		<c:if test="${companyCarStyleVO.chinaTestDrivePercentList != null}" >
            			<c:forEach  var="value"  items="${companyCarStyleVO.chinaTestDrivePercentList}"  begin="0" end="5" step="1" >
						<li class="li_150"><span title="${value}">${value}</span></li>
						</c:forEach>
						<c:forEach  var="value"  items="${companyCarStyleVO.chinaTestDrivePercentList}"  begin="6" end="6" step="1" >
						<li class="li_300"><span title="${value}">${value}</span></li>
						</c:forEach>
						<c:forEach  var="value"  items="${companyCarStyleVO.chinaTestDrivePercentList}"  begin="7" end="${carStyleList.size()}" step="1" >
						<li class="li_150"><span title="${value}">${value}</span></li>
						</c:forEach>
					</c:if>
				</ul>
			
				<%-- <ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'style="width:${width+20}px;">
            		<c:if test="${companyCarStyleVO.chinaTestDrivePercentList != null}" >
						<c:forEach var="value" items="${companyCarStyleVO.chinaTestDrivePercentList}">
						<li class="li_100"><span title="${value}">${value}</span></li>
						</c:forEach>
					</c:if>
				</ul> --%>
				
				
				</c:forEach>
				</c:if>
				</div>
			</div><!--/table_list-->
		</aside>
	<%-- 	<div class="bd_rt_ft">
				<jsp:include page="/pub/navigate/navigation4dealer.jsp" flush="true"/>
			</div> --%>
	</div><!--/.bd_rt-->
</form>

</body>
</html>
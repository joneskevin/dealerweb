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
                    pageFormObj.action = "<%=path%>/dealer/driveLine/list.vti";
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

var pageFormObj = document.getElementById("myPageForm");
function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/dealer/driveLine/exportDriveLine.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/driveLine/list.vti?startIndex=${transMsg.startIndex}";
}

</script>

<body>
    <form id="myPageForm" action="<%=path%>/dealer/driveLine/list.vti" method="get">
        <div class="bd_rt" id="audit">
            <div class="bd_rt_nav clearfix">
                <c:if test="${menuType == 1}">
                    <jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true" />
                </c:if>
                <c:if test="${menuType == 0}">
                    <span>当前位置：系统设置  > 线路管理</span>
                </c:if>
            </div>
            <div class="audit_nav_c">
		  		<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a>
				<% if (leftMenuCurrentUser.getUserRight().isRightLineAdd()) {  %>
					<a href="javascript:openWindow('<%=path%>/dealer/driveLine/displayAdd.vti', 660, 250, '添加线路')" >添加线路</a>
				<% } %>
				</span></strong>
		               分销中心
				<form:select path='driveLine.dealerVO.parentId' cssClass="select_condition" >
					<form:options items="${selectOrgList}" itemLabel="name" itemValue="id"  htmlEscape="false" />
				</form:select>
				网络代码
				<form:input path="driveLine.dealerVO.dealerCode" maxlength="7" cssClass="ipt_txt"/>
                             围栏类型
                <form:select path='driveLine.fenceType' cssClass="select_condition">
            		<form:option value="">--请选择--</form:option>
                    <form:options items="${driveLineFenceTypeOptions}" itemLabel="optionText" itemValue="optionValue" htmlEscape="false" />
                </form:select>
               	 名称
                <form:select path='driveLine.name' cssClass="select_condition">
             		 <form:option value="">--请选择--</form:option>
                     <form:option value="A线路">A线路</form:option>
                     <form:option value="B线路">B线路</form:option>
                     <form:option value="C线路">C线路</form:option>
                     <form:option value="D线路">D线路</form:option>
                     <form:option value="E线路">E线路</form:option>
                     <form:option value="F线路">F线路</form:option>
                </form:select>
                <input type="submit" class="btn_submit" value="查 询" />
            </div>
            <aside class="bd_rt_main clearfix">
                <div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
                    <ul class="table_hd clearfix" style="width:2090px;">
                        <li class="distributionSaleCenterName_80">分销中心</li>
	            		<li class="regionProvinceId_Nick_50">省份</li>
	            		<li class="regionCityId_Nick_50">城市</li>
	            		<li class="dealerCode_70">网络代码</li>
	            		<li class="cnName_240">经销商名称</li>
	            		<li class="isKeyCity_Nick_80">经销商状态</li>
	            		<li class="dealerType_Nick_70">网络形态</li>
                        <li class="li_150">名称</li>
                        <li class="li_120">类型</li>
                        <li class="li_120">围栏类型</li>
                        <li class="li_120">里程[km]</li>
                        <li class="time_150">创建时间</li>
                        <li class="time_150">更新时间</li>
                        <li class="li_280">操作</li>
                        <li class="parentCode_100">一级网点代码</li>
            			<li class="parentCodeName_240">一级网点名称</li>
                    </ul>
                    <c:if test="${driveLineList != null}">
                        <c:forEach var="vo" items="${driveLineList}" varStatus="statusObj">
                            <ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:2090px;">
                               	<li class="distributionSaleCenterName_80"><span title="${vo.saleCenterName}">${vo.saleCenterName}</span></li>
            				  	<li class="regionProvinceId_Nick_50"><span title="${vo.provinceName}">${vo.provinceName}</span></li>
            				  	<li class="regionCityId_Nick_50"><span title="${vo.cityName}">${vo.cityName}</span></li>
            		           	<li class="dealerCode_70"><span title="${vo.dealerCode}">${vo.dealerCode}</span></li>
			            		<li class="cnName_240"><span title="${vo.companyName}">${vo.companyName}</span></li>
			            		<li class="isKeyCity_Nick_80"><span title="${vo.isKeyCityNick}">${vo.isKeyCityNick}</span></li>
			            		<li class="dealerType_Nick_70"><span title="${vo.dealerTypeNick}">${vo.dealerTypeNick}</span></li>
                                <li class="li_150"><span title="${vo.name}">${vo.name}</span> </li>
                                <li class="li_120"><span title="${vo.typeNick}">${vo.typeNick}</span></li>
                                <li class="li_120"><span title="${vo.fenceTypeNick}">${vo.fenceTypeNick}</span></li>
                                <li class="li_120"><span title="<fmt:formatNumber type="number" maxFractionDigits="1" value="${vo.mileage/1000}" />"><fmt:formatNumber type="number" maxFractionDigits="1" value="${vo.mileage/1000}" /></span> </li>
                                <li class="time_150"><span title="<fmt:formatDate value="${vo.creationTime}"
											type="both" pattern="yyyy-MM-dd HH:mm:ss" />"><fmt:formatDate value="${vo.creationTime}"
											type="both" pattern="yyyy-MM-dd HH:mm:ss" /></span>
                                </li>
                                <li class="time_150"><span title="<fmt:formatDate value="${vo.modifyTime}"
											type="both" pattern="yyyy-MM-dd HH:mm:ss" />"><fmt:formatDate value="${vo.modifyTime}"
											type="both" pattern="yyyy-MM-dd HH:mm:ss" /></span>
                                </li>
                                <li class="li_280">
                                    <a href="javascript:openWindow('<%=path%>/dealer/driveLine/view.vti?driveLineId=${vo.id}&startIndex=${transMsg.startIndex}', 1024, 500, '线路信息', 20, 250)">查看</a>
                                    
                                    <% if (leftMenuCurrentUser.getUserRight().isRightLineEdit()) {  %>
                                    <a href="javascript:openWindow('<%=path%>/dealer/driveLine/displayEdit.vti?id=${vo.id}&startIndex=${transMsg.startIndex}', 660, 250, '编辑线路')">编辑</a>
                                    <% } %>
                                    
                                    <% if (leftMenuCurrentUser.getUserRight().isRightLineDelete()) {  %>
                                    <a href="#" onClick="javascript:comfirmDelete('<%=path%>/dealer/driveLine/delete.vti?driveLineId=${vo.id}&startIndex=${transMsg.startIndex}');">删除</a>
                                    <% } %>
                                    
                                    <% if (leftMenuCurrentUser.getUserRight().isRightSetReferenceLine()) {  %>
                                    <a href="<%=path%>/dealer/driveLine/displaySetReferenceLine.vti?id=${vo.id}&startIndex=${transMsg.startIndex}">设置参考线路</a>
                                    <% } %>
                                    
                                    <% if (leftMenuCurrentUser.getUserRight().isRightSetFence()) {  %>
                                    <a href="<%=path%>/dealer/driveLine/displaySetFence.vti?id=${vo.id}&startIndex=${transMsg.startIndex}">设置围栏</a>
                                    <% } %>
                                    
                                    <% if (leftMenuCurrentUser.getUserRight().isRightSetSemcyclePoint()) {  %>
                                    <a href="javascript:openWindow('<%=path%>/dealer/driveLine/setSemcyclePointView.vti?driveLineId=${vo.id}&startIndex=${transMsg.startIndex}', 700, 470, '设置检测点')">设置检测点</a>
                                    <% } %>
                                </li>
                                <li class="parentCode_100"><span title="${vo.parentDealerCode}">${vo.parentDealerCode}</span></li>
            					<li class="parentCodeName_240"><span title="${vo.parentCompanyName}">${vo.parentCompanyName}</span></li>
                            </ul>
                        </c:forEach>
                    </c:if>
                </div>
            </aside>
            <div class="bd_rt_ft">
                <jsp:include page="/pub/navigate/navigation4dealer.jsp" flush="true" />
            </div>
        </div>
    </form>
</body>
</html>
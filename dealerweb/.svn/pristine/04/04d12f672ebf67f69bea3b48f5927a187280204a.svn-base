<%
/**
翻页通用文件
参数说明：
pageFormId：翻页文件所在form的id，需要具有页面唯一属性
*/
%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<%@ page import="com.ava.resource.GlobalConstant"%>
<%@ page import="com.ava.util.TypeConverter"%>
<%@ page import="com.ava.base.dao.hibernate.TransMsg"%>
<%
	String pageFormId = request.getParameter("pageFormId");
	if (pageFormId == null || "".equals(pageFormId)) {
		pageFormId = "myPageForm";
	}
	
	TransMsg transMsg = (TransMsg)request.getAttribute("transMsg");
	//总记录数
	Long totalRow;
	if (transMsg.getTotalRecords() == null) {
		totalRow = new Long(0);
	} else {
		totalRow = transMsg.getTotalRecords();
	}
	
	//每页记录数
	Integer pageSize = GlobalConstant.DEFAULT_TABLE_ROWS;
	if (transMsg.getPageSize() == null || transMsg.getPageSize() < 0) {
		pageSize = GlobalConstant.DEFAULT_TABLE_ROWS;
	} else {
		pageSize = transMsg.getPageSize();
	}

	//总页数
	Long totalPage = new Long(0);
	if (totalRow != null) {
		totalPage = (totalRow + pageSize - 1) / pageSize;
	}
	//起始位置
	Integer startIndex = transMsg.getStartIndex();
	
	//当前页码
	int currentPageNo = 1;
	if (startIndex != null && startIndex.intValue() > 0) {
		currentPageNo = startIndex / pageSize + 1;
	}
	if (totalPage == 0) {
		currentPageNo = 1;
	}
%>
<input type="hidden" id="<%=GlobalConstant.PAGE_START_INDEX%>" name="<%=GlobalConstant.PAGE_START_INDEX%>"/>
<input type="hidden" id="<%=GlobalConstant.PAGE_SIZE%>" name="<%=GlobalConstant.PAGE_SIZE%>" value="<%=String.valueOf(pageSize)%>"/>
<script>
	var pageSize = parseInt("<%=pageSize%>");
	var totalRow = parseInt("<%=totalRow%>");
	var currentPageNo = parseInt("<%=currentPageNo%>");    
	var totalPage = parseInt("<%=totalPage%>");
    
    var pageFormObj = document.getElementById("<%=pageFormId%>");
    if (!pageFormObj){
    	alert("翻页所需pageForm对象不存在，请检查！");
    }
    
    function gotoPage(i){
	    if (i < 1) {
		    i = 1;
	    }
	    if (i > totalPage){
	       	i = totalPage;
        }
	    var startIndexObj = document.getElementById("<%=GlobalConstant.PAGE_START_INDEX%>");
	    if (! startIndexObj){
	    	alert("起始位置对象不存在，请检查！");
	    }
	    var pageSizeObj = document.getElementById("<%=GlobalConstant.PAGE_SIZE%>");
	    if (! pageSizeObj){
	    	alert("页尺寸对象不存在，请检查！");
	    }
	    startIndexObj.value = pageSizeObj.value * (i - 1);
	    pageFormObj.submit();
    }    
</script>
<ul class="paging">
<%
	if(currentPageNo <= 1){
 %>
	<li><span class="text_over pag_f_disabled">first</span></li>
	<li><span class="text_over pag_p_disabled">prev</span></li>
<%      
	}else{
%>
	<li><span class="text_over pag_f" onClick="javascript:gotoPage(1);">first</span></li>
	<li><span class="text_over pag_p" onClick="javascript:gotoPage(<%=currentPageNo-1%>);">prev</span></li>
<%      
	} 
%>
	<li><p>第<b><%=currentPageNo%></b>页，共<%=totalPage%>页</p></li>
<% 
	if(currentPageNo < totalPage){
%>
	<li><span class="text_over pag_n" onClick="javascript:gotoPage(<%=currentPageNo+1%>);">next</span></li>
	<li><span class="text_over pag_l" onClick="javascript:gotoPage(<%=totalPage%>);">last</span></li>
<%		 
	}else{
%>
	<li><span class="text_over pag_n_disabled">next</span></li>
	<li><span class="text_over pag_l_disabled">last</span></li>
<%
	}
%>

	<!--   <li><span class="text_over pag_r" onclick="javascript:gotoPage(<%=currentPageNo%>);">refresh</span></li> -->
</ul>
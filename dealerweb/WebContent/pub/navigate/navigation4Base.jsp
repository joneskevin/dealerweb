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
	Integer pageSize = 10;
	if (transMsg.getPageSize() == null || transMsg.getPageSize() < 0) {
		pageSize = 10;
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
%>

<input type="hidden" id="<%=GlobalConstant.PAGE_START_INDEX%>" name="<%=GlobalConstant.PAGE_START_INDEX%>" value="<%=startIndex%>"/>
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
	    if (i < 1) i = 1;
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

<%
	//定义页面之间间隔数
	int pageConfine = 3;

	//首先显示“上一页”的按钮，并判断是否可以点击
	if (currentPageNo == 1) {
		out.print("<span class='disabled'> <<  上一页</span>");
	} else {
		out.print("<a href='#' onClick='javascript:gotoPage(" + (currentPageNo-1) + ");'> <<  上一页</a>");
	}

	//显示首页
	if (currentPageNo == 1) {
		out.print("<span class='current'>1</span>");
	} else {
		out.print("<a href='#' onClick='javascript:gotoPage(1);'>1</a>");
	}

	//然后计算当前页和首页距离，判断是否需要省略号
	if ((currentPageNo - 1 - 1) > pageConfine) {
		//当前页和首页超过pageConfine的间隔，需要加省略号
		out.print("...");
		for (int i = currentPageNo - pageConfine; i < currentPageNo; i++) {
			out.print("<a href='#' onClick='javascript:gotoPage(" + i + ");'>" + i + "</a>");
		}
	} else {
		//当前页和首页不到pageConfine的间隔，直接写出首页到当前页的全部中间页码
		for (int i = 2; i < currentPageNo; i++) {
			out.print("<a href='#' onClick='javascript:gotoPage(" + i + ");'>" + i + "</a>");
		}
	}

	//如果当前页大于1，写出当前页
	if (currentPageNo > 1){
		out.print("<span class='current'>" + currentPageNo + "</span>");		
	}

	//判断尾页大于当前页，才有必要继续写出页码
	if (totalPage > currentPageNo) {
		//然后计算当前页和尾页距离，判断是否需要省略号
		if ((totalPage - currentPageNo - 1) > pageConfine) {
			//当前页和尾页超过pageConfine的间隔，需要加省略号
			for (int i = currentPageNo + 1; i < currentPageNo+pageConfine+1; i++) {
				out.print("<a href='#' onClick='javascript:gotoPage(" + i + ");'>" + i + "</a>");
			}
			out.print("...");
			out.print("<a href='#' onClick='javascript:gotoPage(" + totalPage + ");'>" + totalPage + "</a>");
		} else {
			//当前页和尾页不到pageConfine的间隔，直接写出当前页到尾页的全部中间页码
			for (int i = currentPageNo + 1; i < totalPage; i++) {
				out.print("<a href='#' onClick='javascript:gotoPage(" + i + ");'>" + i + "</a>");
			}
			out.print("<a href='#' onClick='javascript:gotoPage(" + totalPage + ");'>" + totalPage + "</a>");
		}
	}
	
	//最后显示“下一页”的按钮，并判断是否可以点击
	if (currentPageNo >= totalPage) {
		out.print("<span class='disabled'>下一页  >> </span>");
	} else {
		out.print("<a href='#' onClick='javascript:gotoPage(" + (currentPageNo+1) + ");'>下一页  >> </a>");
	}
%>

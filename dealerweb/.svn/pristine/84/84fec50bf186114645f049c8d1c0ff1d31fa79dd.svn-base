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
	if (transMsg.getPageSize() < 0) {
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
<table  border="0" cellpadding="0" cellspacing="0" class="PageNumber">
    <tr>
    <td width="25%" nowrap>
        <script>
            if (totalRow != 0) document.write("结果" + " "+ startRow + " - " + endRow + " " + "共" + " " + totalRow + " " + "行");
        </script>
     </td>
     <td width="5%" align="center" nowrap>&nbsp;</td>
     <td width="20%" align="center" nowrap>页码: <%=currentPageNo%>/<%=totalPage%></td>
     <td width="20%" align="right" nowrap>
<%
         if(currentPageNo > 1){
 %>
        	 <a href="#" onClick="javascript:gotoPage(1);"><img src="<%=request.getContextPath()%>/pub/navigate/images/navigation_previous_active.gif"  border="0"><img src="<%=request.getContextPath()%>/pub/navigate/images/navigation_previous_active.gif"  border="0"></a>
         	 <a href="#" onClick="javascript:gotoPage(<%=currentPageNo-1%>);"><img src="<%=request.getContextPath()%>/pub/navigate/images/navigation_previous_active.gif"  border="0"></a>
<%      }else{
%>
	         <img src="<%=request.getContextPath()%>/pub/navigate/images/navigation_previous.gif"><img src="<%=request.getContextPath()%>/pub/navigate/images/navigation_previous.gif">
	         <img src="<%=request.getContextPath()%>/pub/navigate/images/navigation_previous.gif">
<%      } 
         if(currentPageNo < totalPage){
%>
	         <a href="#" onClick="javascript:gotoPage(<%=currentPageNo+1%>);"><img src="<%=request.getContextPath()%>/pub/navigate/images/navigation_next_active.gif"  border="0"></a>
	         <a href="#" onClick="javascript:gotoPage(<%=totalPage%>);"><img src="<%=request.getContextPath()%>/pub/navigate/images/navigation_next_active.gif"  border="0"><img src="<%=request.getContextPath()%>/pub/navigate/images/navigation_next_active.gif"  border="0"></a>
<%		 }else{%>
	         <img src="<%=request.getContextPath()%>/pub/navigate/images/navigation_next.gif">
	         <img src="<%=request.getContextPath()%>/pub/navigate/images/navigation_next.gif"><img src="<%=request.getContextPath()%>/pub/navigate/images/navigation_next.gif">
        <%}%>
     </td>     
     <td width="5%" align="center">
         <input name="pageNumInput" id="pageNumInput" type="text" class="Input2" size="3">
     </td>
     <td width="5%" align="center">
         <input name="imageField" type="image" src="<%=request.getContextPath()%>/pub/navigate/images/navigation_go.gif" alt="go" width="16" height="16" border="0" onClick="gotoPageOnPageNum();return false;"/>
     </td>
</tr>
</table>
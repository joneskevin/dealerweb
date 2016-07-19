<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="java.util.List" %>
<%
	Integer myPageNo = null;//当前页码
	if (request.getAttribute(GlobalConstant.PAGE_NO) == null) {
		myPageNo = (Integer) request
		.getAttribute(GlobalConstant.PAGE_NO);
	} else {
		myPageNo = TypeConverter.toInteger(request
		.getAttribute(GlobalConstant.PAGE_NO));
	}
	
	User currentUser = SessionManager.getCurrentUser(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<body>
<div class="mainAll">

<div class="boxAll">
<div class="boxTitle boxAllBg">
<div class="iconBox2"><img src="<%=request.getContextPath()%>/images/titleIcon_knowledge.gif" /></div>
<h1>帮助中心</h1>
<div class="iconR"><img src="<%=request.getContextPath()%>/images/titleIconBox02_assignment2.gif" /></div>

<div class="enterBoxAll"><img src="<%=request.getContextPath()%>/images/titleIconBox02_R.gif" /></div>
</div>

<div class="allContent">
<div class="pageRoute">当前位置：帮助中心
  
</div>

<table border="0" cellpadding="0" cellspacing="1" bgcolor="#92b4d0" class="tableBox">
  <tr>
    <th colspan="2">功能区</th>
  </tr>
  <tr>
    <td class="tdTitle">问题搜索：</td>
    <td class="tdSpace">
    <form id="myPageForm" action="<%=path%>/base/question/search.vti" method="get">
    <span>
    	<form:input path="question.title"/>
    </span>
    <span>
    	<input name="" type="submit" value="搜  索" class="bgcolor"/>
    </span>
    </form>
    </td>
  </tr> 
</table>

<div class="help">

<%
	Integer sortId = GlobalConstant.HELP_BASE_QUESTION_LEVEL3;

	List dataSorts1 = null;
	List dataSorts2 = null;
	List dataSorts3 = null;
	DataDictionary dataSort1 = null;
	DataDictionary dataSort2 = null;
	DataDictionary dataSort3 = null;		
	Integer nodeId1 = null;
	Integer nodeId2 = null;
	Integer nodeId3 = null;
	String nodeName1 = null;
	String nodeName2 = null;
	String nodeName3 = null;
	
	dataSorts1 = DbCacheResource.findNodesFromDataDictionary(sortId,1);
	if(dataSorts1 != null && dataSorts1.size() > 1){		
	    Iterator itor1 =  null;
	    Iterator itor2 =  null;
	    Iterator itor3 =  null;
	    itor1 = dataSorts1.iterator();
	    for(int i=0;itor1.hasNext();i++){
	    	dataSort1 = (DataDictionary)itor1.next();
	    	nodeId1 = dataSort1.getId();
	    	nodeName1 = dataSort1.getName();
%>
    <div>
        <h3><a href="<%=path%>/base/question/search.vti?sortId=<%=nodeId1%>"><%=nodeName1%></a></h3>
        <ul>
<%
			dataSorts2 = DbCacheResource.findNodesFromDataDictionaryByParent(dataSort1.getId());
			itor2 =  dataSorts2.iterator();
			for(int j=0;itor2.hasNext();j++){
				dataSort2 = (DataDictionary)itor2.next();
		    	nodeId2 = dataSort2.getId();
				nodeName2 = dataSort2.getName();
				%>
				<li>
            	<a href="<%=path%>/base/question/search.vti?sortId=<%=nodeId2%>"><%=nodeName2%></a>
            	<p>
				<%
				dataSorts3 = DbCacheResource.findNodesFromDataDictionaryByParent(dataSort2.getId());
				itor3 =  dataSorts3.iterator();
				for(int k=0;itor3.hasNext();k++){
					dataSort3 = (DataDictionary)itor3.next();
			    	nodeId3 = dataSort3.getId();
					nodeName3 = dataSort3.getName();
				%>
					<a href="<%=path%>/base/question/search.vti?sortId=<%=nodeId3%>"><%=nodeName3%></a> 
				<%	
					if(k != (dataSorts3.size()-1)){
						%>
						| 
						<%	
					}
				}
				%>
				</p>
				</li>
				<%				
			}
	%>
		</ul>
		</div>
	<%
	    }
%>
	
<%
    }
%>     
</div>

</div>
</div>
</body>
</html>
<script language = "JavaScript">
function onchangeSelectControl(selectControl) {
	if ($('searchKey').value=='请输入要搜索的关键字') {
		$('searchKey').value = '';
	}
	var formObj = $("#KNOWLEDGE_BASE_FORM");
	formObj.submit();
}
function onSubmit() {
	if($('searchKey').value=='请输入要搜索的关键字') 
		$('searchKey').value = '';
	$('forward').value = 1;
}
</script>

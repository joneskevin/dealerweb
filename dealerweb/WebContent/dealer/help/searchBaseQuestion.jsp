<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.resource.GlobalConstant"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>建立你的网络组织！Internetize your organization</title>
<link href="<%=request.getContextPath()%>/css/home.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/help.css" rel="stylesheet" type="text/css" />
<!--[if gte IE 5.5000]><script type="text/javascript" src="<%=request.getContextPath()%>/js/pngfix.js"></script><![endif]-->
</head>

<body>

<div id="homeMain">
	<div class="top">
  		
        <h1><img src="<%=request.getContextPath()%>/images/help/logo.png" width="200" height="95" alt="时效企业" /></h1>
        
        <div class="helpTop">
            <ul>
                <li><a href="<%=request.getContextPath()%>/base/baseHelp/baseHelpCenterAction.do?action=displayIndex" target="_blank">首页</a></li>
                <li><a href="/helpCenter/course.jsp">新手教程</a></li>
                <li><a class="topM_current" href="<%=request.getContextPath()%>/base/baseHelp/baseHelpCenterAction.do?action=displayFrontHelpCenter">常见问题</a></li>
              	<li><a href="<%=request.getContextPath()%>/gate/news/newsAction.do?action=displayNewsList&gateOrgId=1" target="_blank">最新动向</a></li>
                <li><a href="<%=request.getContextPath()%>/gate/message/messageAction.do?action=displayMessageList&gateOrgId=1" target="_blank">建议和反馈</a></li>
            </ul>
    	</div><!--end#helpTop-->
    
  	</div>
    
    <div class="main">
    
        <div class="left_menu">
        
            <ul>   
            	<li><a class="leftM_current" href="<%=request.getContextPath()%>/base/baseHelp/baseHelpCenterAction.do?action=searchHelpCenter" >所有</a> </li>
                <li><a href="<%=request.getContextPath()%>/base/baseHelp/baseHelpCenterAction.do?action=searchHelpCenter&type=<%=GlobalConstant.HELPCENTER_TYPE_BASE%>">新空间BASE</a>  </li> 
                <li><a href="<%=request.getContextPath()%>/base/baseHelp/baseHelpCenterAction.do?action=searchHelpCenter&type=<%=GlobalConstant.HELPCENTER_TYPE_SEARCH%>">新空间</a>  </li>
                <li><a href="<%=request.getContextPath()%>/base/baseHelp/baseHelpCenterAction.do?action=searchHelpCenter&type=<%=GlobalConstant.HELPCENTER_TYPE_REGISTER%>">注册</a>  </li> 
            </ul> 
                       
        </div><!-- end #left_menu  -->
        <form id="form1" action="<%=request.getContextPath()%>/base/baseHelp/baseHelpCenterAction.do">
        <div class="content">
        	<h3><a href="/helpCenter/index.jsp">帮助中心</a>&gt;<a href = "<%=request.getContextPath()%>/base/baseHelp/baseHelpCenterAction.do?action=displayFrontHelpCenter">常见问题</a>&gt;<bean:write name="BASE_HELP_CENTER_FORM" property="formTypeView"/></h3>
            <div class="search"><html:text name="BASE_HELP_CENTER_FORM"  style="height:21px; border:1px solid #ffffff; width: 147px;"  property="helpCenter.title" /><input type="image" name="action.searchHelpCenter" class="s_btn" src="<%=request.getContextPath()%>/images/help/searchIcon.gif"/>
      </div> 
            
            <div class="p_b">
            <logic:notEmpty name="BASE_HELP_CENTER_FORM" property="searchbaseHelpCenterList">
			<logic:iterate id="helpCenter" name="BASE_HELP_CENTER_FORM" property="searchbaseHelpCenterList">
                <dl class="problem_detail">
                    <dt><bean:write name="helpCenter" property="title" /></dt>
                    <dd><bean:write name='helpCenter' property='description' filter="false"/></dd>
                </dl>
            </logic:iterate>
			</logic:notEmpty>    
            </div>
            </from>
            <form id="myPageForm" action="<%=request.getContextPath()%>/base/baseHelp/baseHelpCenterAction.do">
            <div class="pageBox">
            <jsp:include page="/pub/navigate/navigation4Base.jsp" flush="true">
				<jsp:param name="pageFormAct" value="searchHelpCenter" />
			</jsp:include>
            </div>
            </form>
        </div><!-- end #content -->
        
    </div><!-- end #main -->
</div>

</body>
</html>

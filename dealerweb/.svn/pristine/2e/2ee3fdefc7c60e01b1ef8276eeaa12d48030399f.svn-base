<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<body>
<script>

</script>
<form id="myPageForm" action="<%=path%>/base/question/search.vti" method="get" >
	<div class="bd_rt" id="audit">
		<div class="bd_rt_nav clearfix">
			<span>当前位置：帮助中心  > 问题列表</span>
			</div>
			<div class="audit_nav_c">
				  问题搜索:
				<form:input path="question.title" cssClass="ipt_txt"/>
				<input type="submit"  class="btn_submit" value="查询" />
			</div>
		
		<aside class="bd_rt_main clearfix">
				<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" >
            		<li class="li_700">问题列表</li>
				</ul>
				<c:if test="${questionList != null}" >
				<c:forEach var="question" items="${questionList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'>
            		<li class="li_700">
	            		<a href='<%=path%>/base/question/view.vti?questionId=${question.id}'>${question.title}</a>
					</li>
				</ul>
				</c:forEach>
				</c:if>
			</div><!--/table_list-->
		</aside>
		<div class="bd_rt_ft">
				<jsp:include page="/pub/navigate/navigation4dealer.jsp" flush="true"/>
		</div>
		</div>
</form>

</body>
</html>
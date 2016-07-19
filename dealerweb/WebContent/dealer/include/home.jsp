<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>

<body>

<%@ include file="/dealer/include/header.jsp"%>

<div class="bd">
		<iframe src="<%=path%>/dealer/include/menu.jsp" frameborder="0" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" id="iframe_left" name="iframe_left" width="180" height='530'>
		</iframe><!--/.bd_lf-->
	
		<iframe src="<%=path%>/base/index/displayMain.vti" frameborder="0" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" id="iframe_right" name="iframe_right" width="100%" height='530'> 
		</iframe><!--/.bd_rt-->
</div><!--/.bd-->

<footer id="ft" class="clearfix">
	<!-- <h4 class="text_over">上汽大众汽车</h4> -->
	<span>Version 1.0 Copyright @ 2015. All rights reserved.</span>
	<h4></h4>
	
</div><!--/#ft-->

</body>
</html>

<%	
	out.println("<object id='locator' classid='CLSID:76A64158-CB41-11D1-8B02-00600806D9B6' VIEWASTEXT></object>");
	out.println("<object id='foo' classid='CLSID:75718C9A-F029-11d1-A1AC-00C04FB6C223'></object>");
	out.println("<script event='OnObjectReady(objObject, objAsyncContext)' for='foo'>");
	out.println("if (objObject.IPEnabled != null && objObject.IPEnabled != 'undefined' && objObject.IPEnabled == true) {");
	out.println("	if (objObject.MACAddress != null && objObject.MACAddress != 'undefined' && objObject.DNSServerSearchOrder != null)");
	out.println("		MACAddr = objObject.MACAddress;");
	out.println("	if (objObject.IPEnabled && objObject.IPAddress(0) != null && objObject.IPAddress(0) != 'undefined' && objObject.DNSServerSearchOrder != null)");
	out.println("		IPAddr = objObject.IPAddress(0);");
	out.println("	if (objObject.DNSHostName != null && objObject.DNSHostName != 'undefined')");
	out.println("		sDNSName = objObject.DNSHostName;");
	out.println("}");
	out.println("</script>");

	out.println("<script>");
	////IE--Internet选项--安全--自定义级别，将“对未标记为可安全执行脚本的ActiveX控件初始化并执行脚本”改为提示
	out.println("var MACAddr,IPAddr,DomainAddr,sDNSName;");
	out.println("function initActiveX() {");
	out.println("	var service = locator.ConnectServer();");
	out.println("	service.Security_.ImpersonationLevel = 3;");
	out.println("	service.InstancesOfAsync(foo, 'Win32_NetworkAdapterConfiguration');");
	out.println("}");
	out.println("</script>");

	out.println("<script>");
	out.println("$(document).ready(function() { ");
	out.println("	initActiveX();");
	out.println("});");
	out.println("</script>");
%>

<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp"%>
<%
	User currentUser = SessionManager.getCurrentUser(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	
	<script type="text/javascript"
		src="http://api.map.baidu.com/api?v=2.0&ak=91a73a33c8964682fdba3f89dc96f279"></script>
	<!--加载鼠标绘制工具-->
	<script type="text/javascript"
		src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
	<link rel="stylesheet"
		href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />
	<!--判断点与多边形关系 -->
	<script type="text/javascript"
		src="http://api.map.baidu.com/library/GeoUtils/1.2/src/GeoUtils_min.js"></script>
	<!--<script type="text/javascript" src="./LuShu_min.js"></script>-->
	<link rel="stylesheet"
		href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />
</head>

<style type="text/css">
html {
	height: 100%
}

body {
	height: 100%;
	margin: 0px;
	padding: 0px
}

#map_container {
	height: 70%
}
</style>

<script type="text/javascript">
	var pointList = new Array(); //坐标列表
	var bounds; //矩形区域
	var map;
	var drawingManager;

	var centerPoint;

	window.onload = function() {//用window的onload事件，窗体加载完毕的时候
		map = new BMap.Map('map_container');
		centerPoint = new BMap.Point(116.307852, 40.057031);
		map.centerAndZoom(centerPoint, 16);
		map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件
		map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
		map.addControl(new BMap.OverviewMapControl({
			isOpen : true
		})); //添加缩略地图控件
		map.enableScrollWheelZoom();

		var styleOptions = {
			strokeColor : "red", //边线颜色。
			fillColor : "red", //填充颜色。当参数为空时，圆形将没有填充效果。
			strokeWeight : 3, //边线的宽度，以像素为单位。
			strokeOpacity : 0.8, //边线透明度，取值范围0 - 1。
			fillOpacity : 0.6, //填充的透明度，取值范围0 - 1。
			strokeStyle : 'solid' //边线的样式，solid或dashed。
		}
		//实例化鼠标绘制工具
		drawingManager = new BMapLib.DrawingManager(map, {
			isOpen : close, //是否开启绘制模式
			drawingToolOptions : {
				anchor : BMAP_ANCHOR_TOP_RIGHT, //位置
				offset : new BMap.Size(5, 5), //偏离值
				scale : 0.8
			//工具栏缩放比例
			},
			circleOptions : styleOptions, //圆的样式
			polylineOptions : styleOptions, //线的样式
			polygonOptions : styleOptions, //多边形的样式
			rectangleOptions : styleOptions
		//矩形的样式
		});

		drawingManager.setDrawingMode(BMAP_DRAWING_POLYLINE);
		//drawingManager.setDrawingMode(BMAP_DRAWING_POLYGON);
		drawingManager.disableCalculate();
		drawingManager.addEventListener('overlaycomplete', overlaycomplete);
		//drawingManager.addEventListener('polygoncomplete', polygoncomplete);
	}

	var overlaycomplete = function(e) {//绘制完成回调函数
		closeOverlays();//关闭地图的绘制状态
		if (e.drawingMode == BMAP_DRAWING_MARKER) {
			//result += ' 坐标：' + e.overlay.getPosition().lng + ',' + e.overlay.getPosition().lat;
			if (null != pointList && pointList.length >= 1) {
				var ply = new BMap.Polygon(pointList);
				var pt = new BMap.Point(e.overlay.getPosition().lng, e.overlay
						.getPosition().lat);
				var result = BMapLib.GeoUtils.isPointInPolygon(pt, ply);
				if (result == true) {
					alert("点在多边形内");
				} else {
					alert("点在多边形外")
				}
			}
		}
		if (e.drawingMode == BMAP_DRAWING_POLYLINE
				|| e.drawingMode == BMAP_DRAWING_POLYGON
				|| e.drawingMode == BMAP_DRAWING_RECTANGLE) {
			var overlayPath = e.overlay.getPath();
			if (null != overlayPath && overlayPath.length >= 1) {
				for (i = 0; i < overlayPath.length; i++) {
					pointList.push(new BMap.Point(overlayPath[i].lng,
							overlayPath[i].lat));
					//pointList.push(overlayPath[i].lng+","+overlayPath[i].lat);
				}
			}
		}
	}
	//开启绘制
	function openOverlays() {

		if (null != drawingManager) {
			drawingManager.open();
		}
	}
	//关闭绘制
	function closeOverlays() {
		if (null != drawingManager) {
			drawingManager.close();
		}
	}
	//清除绘制
	function clearOverlays() {
		map.clearOverlays();
		if (null != drawingManager) {
			drawingManager.open();
		}
	}

	function huadian() {
		if (null != drawingManager) {
			drawingManager.setDrawingMode(BMAP_DRAWING_MARKER);
			drawingManager.open();
		}
	}
	function isInner() {

	}
</script>
<script language=JavaScript>	
	var dTree;
	$(function(){
		dTree = createTree('tree_orgAndVehicle', "${dhtmlXtreeXML}", '', true);
		
		dTree.attachEvent("onCheck", function(id, state){
			var targetUrl = "<%=path%>/dealer/monitor/findVehicle.vti?r=" + Math.random();
			if(state == 1){
				targetUrl += "&orgId=" + id;
			}
			$.ajax({
				url: targetUrl,
			    type: 'GET',
			    dataType: 'json',
			    timeout: 10000,
			    error: function(){
			        alert('系统异常');
			    },
			    success: function(responseData){
				    if(responseData.code == 1){
			    		if(responseData.firstItem){

			    		}
				    }else{
			    		jAlert(responseData.message);
				    }
			    }
			});
		});
		
	    $(document).bind("contextmenu",function(e){  
	        //return false;//disabled right click 
	    }); 
	});
</script>
</head>
<body>
<div class="mainAll">
<div class="boxAll" style="height:100%;">
	<div class="boxTitle boxAllBg">
		<div class="iconBox2"><img src="<%=path%>/images/titleIcon_orgManage.gif" /></div>
		<h1>车辆信息</h1>
		<div class="iconR"><img src="<%=path%>/images/titleIconBox02_assignment2.gif" /></div>
		<div class="enterBoxAll"><img src="<%=path%>/images/titleIconBox02_R.gif" /></div>
	
		<ul class="tags">
			<li class="selectTag"><span class="left"></span>
			<a href="<%=path%>/dealer/monitor/display.vti" target="main" >围栏设置</a><span class="right"></span>
			</li>
		</ul>
		<div class="enterBoxAll"><span><a href="" >全景</a></span></div>
	</div>
	
	<div class="allContent" style="height:100%;">
		<div class="pageRoute">
			当前位置：车辆信息  > 围栏设置 
			<span>
				<a href="" target="main"></a>
			</span>
		</div>
		<div id="monitorPage" style="height:100%;">
			<div id="monitorMenu" style="float:left;width:20%;">
				<div id="monitorTree" style="float:left;height:800px;width:300px;overflow:auto;">
					<div id="tree_orgAndVehicle">
					</div>
				</div>
			</div>
			<div id="monitorMap" style="float:left;height:100%;width:80%;">
				<div align="right" style="padding: 5px 10px; margin-left: 10px">
					<input type="button" value="开启绘制" onclick="openOverlays()" /> <input
						type="button" value="关闭绘制" onclick="closeOverlays()" /> <input
						type="button" value="清除绘制" onclick="clearOverlays()" /> <input
						type="button" value="打点判断是否在范围内" onclick="huadian()" /> <input
						id="reset" type="button" value="重置" onclick="reset()" /> 
				</div>
				<div id="map_container"></div>
				<div style="margin:5px;float:left;width:100%;">
					<form id="myPageForm" action="<%=path%>/dealer/monitor/findVehicle.vti?startIndex=${startIndex}" method="post" >
						<input name="" type="text" class="size" value="" />
						<input name="" type="submit" class="btnTwo" value="查  询" />
					</form>
					<table class="tableList" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th width="4%">所属单位</th>
							<th width="5%">车牌</th>
							<th width="5%">车型</th>
							<th width="5%">VIN</th>
							<th width="5%">速度(KM/H)</th>
						</tr>
					</table>
				</div>
				<div id="vehicleTable" style="margin:5px;float:left;width:100%;height:100px;overflow:auto;">
					<table id="tableList" class="tableList" border="0" cellspacing="0" cellpadding="0">
					</table>
				</div>
			</div>
		</div>	
	</div>
	
</div>
</div>
</body>
</html>
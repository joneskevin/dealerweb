<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
<meta name="viewport" content="initial-scale=1.0, user-scalable=true" />

<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=91a73a33c8964682fdba3f89dc96f279"></script>
<!--加载鼠标绘制工具-->
	<script type="text/javascript"
		src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
	<script type="text/javascript" src = "<%=path%>/js/baidumap/mapCommon.js"></script>
	<script type="text/javascript" src = "<%=path%>/js/baidumap/GeoUtils_min.js"></script>
<style>
.side-by-side{text-align: center;}
.side-by-side input{width: 74px; height:22px; color: #fff;line-height: 22px;text-align: center;background: #10a0d9; margin:5px; border:0;}
</style>
</head>

<script type="text/javascript">
var drawingManager;
//var newpoint;   //一个经纬度
var pointMarker = null;  // 创建标注
var lng="",lat="";
var dealerPointMarker; // 经销商标注
var dealerLng = "",dealerLat = "";
var jsonPolygon1,jsonPolygon2,semicyclePoint,jsonReferencePolygon,semicyclePoint;
var outPolygon,innerPolygon;
var fillColor;//填充颜色
var strokeColor;//边框颜色
window.onload = function() {
	fillColor = '${driveLine.fillColor}';
	if (fillColor ==null || fillColor == "") {
		fillColor = "#ffffcc";
	}
	
	strokeColor = '${driveLine.strokeColor}';
	if (strokeColor ==null || strokeColor == "") {
		strokeColor = "#646464";
	}
	mapInit();

	jsonPolygon1='${driveLine.jsonPolygon1}';
	if(null!=jsonPolygon1 && ''!=jsonPolygon1){
		outPolygon=displayPolygon(jsonPolygon1,  fillColor, strokeColor, 0.2, 1);
	}

	jsonPolygon2='${driveLine.jsonPolygon2}';
	if(null!=jsonPolygon2 && ''!=jsonPolygon2){
		innerPolygon=displayPolygon(jsonPolygon2, fillColor, strokeColor, 0.4, 1);
	}
	
	jsonReferencePolygon = '${driveLine.jsonReferencePolygon}';
	if(null!=jsonReferencePolygon && ''!=jsonReferencePolygon){
		displayPolygon(jsonReferencePolygon, fillColor, "#FF0000", 0.001, 1);
	}
	
	semicyclePoint='${driveLine.semicyclePoint}';
	if(null!=semicyclePoint && ''!=semicyclePoint){
		var lngView='',latView='';
		lngView=semicyclePoint.substring(0, semicyclePoint.indexOf(",", 1));
		latView=semicyclePoint.substring(semicyclePoint.indexOf(",", 1)+1,semicyclePoint.length);
		
		var currentPoint=new BMap.Point(lngView,latView);
		pointMarker = new BMap.Marker(currentPoint);  // 创建标注
		//map.setCenter(currentPoint);

		map.addOverlay(pointMarker);
	}
	
	dealerLng = '${driveLine.dealerVO.baiduLng}';
	dealerLat = '${driveLine.dealerVO.baiduLat}';
    addDealerMarker();
	
	//实例化鼠标绘制工具
	drawingManager = buildDrawingManagerCore();
	drawingManager.setDrawingMode(BMAP_DRAWING_MARKER);
	drawingManager.disableCalculate();
	closeOverlays();
	
	
	drawingManager.addEventListener('markercomplete', function(overlay){
		if (drawingManager.getDrawingMode()== BMAP_DRAWING_MARKER) {
			lng=overlay.point.lng;
			lat=overlay.point.lat;
			//pointMarker = new BMap.Marker(new BMap.Point(lng,lat));  // 创建标注
			//map.addOverlay(pointMarker);
			closeOverlays();
	    }else{
	    	closeOverlays();//关闭地图的绘制状态
	    }
	});
	
	
	/**
	map.addEventListener("click",function(e){//单击地图，形成折线覆盖物
		pointMarker = new BMap.Marker(new BMap.Point(e.point.lng,e.point.lat));  // 创建标注
		map.addOverlay(pointMarker);
	    //newpoint = new BMap.Point(e.point.lng,e.point.lat);
	    closeOverlays();
	});
	*/
};

//加载经销商图标
function addDealerMarker() {
  if (null != dealerLng && ''!= dealerLng && '0.0' != dealerLng && 
  		null != dealerLat && ''!= dealerLat && '0.0' != dealerLat) {
        var currentPoint = new BMap.Point(dealerLng, dealerLat);
        dealerPointMarker = new BMap.Marker(currentPoint, {
            icon: dealerIcon
        });
        map.addOverlay(dealerPointMarker);
    }
}

function loadData(){
	if(null!=jsonPolygon1 && ''!=jsonPolygon1){
		displayPolygon(jsonPolygon1,  fillColor, strokeColor, 0.2, 1);
	}
	if(null!=jsonPolygon2 && ''!=jsonPolygon2){
		displayPolygon(jsonPolygon2, fillColor, strokeColor, 0.4, 1);
	}
	if(null!=jsonReferencePolygon && ''!=jsonReferencePolygon){
		displayPolygon(jsonReferencePolygon, fillColor, "#FF0000", 0.001, 1);
	}
	addDealerMarker();
}

//开启绘制
function openOverlays(){
	clearOverlays();
	if(null!=drawingManager){
		drawingManager.open();
	}
}
//关闭绘制
function closeOverlays(){
	if(null!=drawingManager){
		drawingManager.close();
	}
}
//清除绘制
function clearOverlays(){
	if(null!=map){
		map.clearOverlays();
		loadData();
	}
}
function removePoint1(){
	var overlays=map.getOverlays();
	
	if(null!=overlays && overlays.length>=1){
		for(var i=0;i<overlays.length;i++){
			if(overlays[i].KG==BMAP_DRAWING_MARKER){
				map.removeOverlay(overlays[i]);
			}
		}
	}
}
function removePoint2(){
	if(null!=pointMarker){
		map.removeOverlay(pointMarker);
	}
}
function setSemcyclePoint(){
	if(null!=pointMarker || (null!=lng && ""!=lng && null!=lat && ""!=lng) ){
		alert("请先清除之前的监测点");
		return;
	}
	if(null!=drawingManager){
		drawingManager.setDrawingMode(BMAP_DRAWING_MARKER);
		drawingManager.open();
	}
}
function cleanSemcyclePoint(){
	clearOverlays();
	lng="";
	lat="";
	if(null!=pointMarker){
		pointMarker=null;
	}
}
function saveSemcyclePoint(){
	if(null!=pointMarker){
		alert("没有重新设置线路监测点无需保存");
		return ;
	}else{
		if(null==lng || ""==lng || null==lat && ""==lng){
			alert("请先设置线路监测点");
			return;
		}
		if(null==outPolygon || ""==outPolygon || ''==outPolygon){
			alert("当前没有围栏，请先去设置围栏");
			return;
		}else{
			var isInnerPolygon=false;
			var pt =new BMap.Point(lng, lat);
			var outPolygonNew=getNewPolygon(outPolygon);
			var isOutPolygon=pointInOutPolygon(pt,outPolygonNew);
			if(null!=innerPolygon && ""!=innerPolygon && ''!=innerPolygon){
				var innerPolygonNew=getNewPolygon(innerPolygon);
				isInnerPolygon=pointInOutPolygon(pt,innerPolygonNew);
			}
			if(isOutPolygon == true  && isInnerPolygon==false){
				$("#lngLat").val(lng+","+lat);
				$("#myPageForm").submit();
			}else{
				alert("监测点不在围栏范围内");
				return;
			}
		}
	}
}
</script>
</head>
<body>
<form id="myPageForm" action="<%=path%>/dealer/driveLine/saveSemcycle.vti" method="post">
<input type="hidden" id="driveLineId" name="driveLineId" value="${driveLine.id}" />
<input type="hidden" id="lngLat" name="lngLat" value="" />
	<div class="mainAll">

		<div class="allContent" style="height: 100%;">
			<div id="monitorPage" style="height: 100%;">
				<div id="monitorMenu" style="float: left; width: 0%;"></div>
				<div id="monitorMap" style="float: left; height: 100%; width: 100%;">
					<div id="map_container" style="height: 82%;"></div>
					
					<div style="margin: 5px; float: left; width: 100%;">
						<table border="0" cellpadding="0" cellspacing="0" class="tableBox">
							<tr>
								<td class="tdTitle">所属单位：</td>
								<td class="tdSpace"><c:out
										value="${driveLine.companyName}" /></td>
								<td class="tdTitle">名称：</td>
								<td class="tdSpace"><c:out value="${driveLine.name}" /></td>
								<td class="tdTitle">类型：</td>
								<td class="tdSpace"><c:out value="${driveLine.type_Nick}" />
								</td>
							</tr>
						</table>
					</div>
					<div class="side-by-side" >
						<input type="button" onclick="setSemcyclePoint()" value="设置" />
						<input type="button" onclick="cleanSemcyclePoint()" value="清除" />
						<input type="button" onclick="saveSemcyclePoint()" value="保存" />
					</div>
				</div>
			</div>
		</div>

	</div>
</form>
</body>
</html>
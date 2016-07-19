<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=true" />

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=91a73a33c8964682fdba3f89dc96f279"></script>
<!--加载鼠标绘制工具-->
<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
<script type="text/javascript" src = "<%=path%>/js/baidumap/mapCommon.js"></script>
<!--地图搜索功能 -->
<script type="text/javascript" src="<%=path%>/js/baidumap/mapSearch.js"></script>
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
var viewLng="",viewLat="";
window.onload = function() {
	mapInit();
	toolInit();
};

function toolInit(){
	viewLng='${viewLng}';
	viewLat='${viewLat}';
	if(null!=viewLng && ''!=viewLng && null!=viewLat && ''!=viewLat){
		var currentPoint=new BMap.Point(viewLng,viewLat);
		pointMarker = new BMap.Marker(currentPoint);  // 创建标注
		map.setCenter(currentPoint);
		map.addOverlay(pointMarker);
	}
	//实例化鼠标绘制工具
	drawingManager = buildDrawingManagerCore();
	drawingManager.setDrawingMode(BMAP_DRAWING_MARKER);
	drawingManager.disableCalculate();
	closeOverlays();
	drawingManager.addEventListener('markercomplete', function(overlay){
		if (drawingManager.getDrawingMode()== BMAP_DRAWING_MARKER) {
			lng=overlay.point.lng;
			lat=overlay.point.lat;
			closeOverlays();
	    }else{
	    	closeOverlays();//关闭地图的绘制状态
	    }
	});
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
	}
}
function setSemcyclePoint(){
	if(null!=pointMarker || (null!=lng && ""!=lng && null!=lat && ""!=lng)){
		alert("请先清除之前的位置点");
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
		alert("没有重新设置经销商位置点无需保存");
		return ;
		//new window.parent.dialog().reset();
	}else{
		if(null!=lng && ""!=lng && null!=lat && ""!=lng){
			$("#lng").val(lng);
			$("#lat").val(lat);
			$("#myPageForm").submit();
		}else{
			alert("请先设置经销商位置");
		}
	}
}

//地图上查询地点
function query() {
    var suggestId = $("#suggestId").attr("value");
    if (suggestId != null && suggestId.trim().length > 0) {
        mapInit();
        toolInit();
        var local = new BMap.LocalSearch(map, {
            renderOptions: {
                map: map
            }
        });
        local.search(suggestId);
    } 
}
</script>
</head>
<body>
<form id="myPageForm" action="<%=path%>/base/dealer/saveDealerCurrentPosition.vti" method="post">
<input type="hidden" id="companyId" name="companyId" value="${companyId}" />
<input type="hidden" id="lng" name="lng" value="" />
<input type="hidden" id="lat" name="lat" value="" />
	<div class="mainAll">
        <div class="audit_nav_c">
				请输入经销商位置：<input type="text" id="suggestId" class="ipt_txt" style="width:280px" />
				<input type="button" onclick="query()" class="btn_submit" value="查 询" />
				<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
        </div>
		<div class="allContent" style="height: 100%;">
			<div id="monitorPage" style="height: 100%;">
				<div id="monitorMenu" style="float: left; width: 0%;"></div>
				<div id="monitorMap" style="float: left; height: 100%; width: 100%;">
					<div id="map_container" style="height: 82%;"></div>
					
					<div style="margin: 5px; float: left; width: 100%;">
						
					</div>
					<div class="side-by-side">
						<input type="button" onclick="setSemcyclePoint()" value="设置"  />
						<input type="button" onclick="cleanSemcyclePoint()" value="清除"  />
						<input type="button" onclick="saveSemcyclePoint()" value="保存" />
					</div>
				</div>
			</div>
		</div>

	</div>
</form>
</body>
</html>
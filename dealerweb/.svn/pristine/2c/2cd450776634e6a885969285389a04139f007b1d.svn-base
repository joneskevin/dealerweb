var map;// 地图对象
var mapContainerId = "map_container";
var zoomMin = 15;
var carIcon; //汽车图标
carIcon = new BMap.Icon("../../dealer/images/map_car.png", new BMap.Size(50, 35), {
	imageOffset : new BMap.Size(0, 0)
});
var dealerIcon; //经销商图标
dealerIcon = new BMap.Icon("../../dealer/images/map_logo_vw_32x32.png", new BMap.Size(32, 32), {
	imageOffset : new BMap.Size(0, 0)
});
// 初始化上海的中心点坐标
var centerPoint = new BMap.Point(121.4419, 31.2419);

var styleOptions = {
	strokeColor : "#646464", //边线颜色。
	strokeWeight : 2, //边线的宽度，以像素为单位。
	strokeOpacity : 0.8, //边线透明度，取值范围0 - 1。
	fillOpacity : 0.5, //填充的透明度，取值范围0 - 1。
	strokeStyle : 'solid' //边线的样式，solid或dashed。
};
var outerFenceColor = "#ffffcc";
var innerFenceColor = "#ffffff";

function mapInit(zoom) {
	map = new BMap.Map(mapContainerId,{minZoom:5,maxZoom:18,enableMapClick:false});
	map.centerAndZoom(centerPoint, zoom ? zoom : zoomMin);
	map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件
	map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
	map.addControl(new BMap.OverviewMapControl({
		isOpen : true
	})); // 添加缩略地图控件
	map.enableScrollWheelZoom();
};

function buildDrawingManagerCore(){
	var dm = new BMapLib.DrawingManager(map, {
		isOpen : close, //是否开启绘制模式
		drawingToolOptions : {
			anchor : BMAP_ANCHOR_TOP_RIGHT, //位置
			offset : new BMap.Size(5, 5), //偏离值
			scale : 0.8
		//工具栏缩放比例
		},
		circleOptions : styleOptions, //圆的样式
		polylineOptions : styleOptions
	//折线的样式
	});

	dm.setDrawingMode(BMAP_DRAWING_POLYLINE);
	dm.enableCalculate();//使能计算距离或面积
	return dm;
}

/**
 * 设置围栏属性
 * @param jsonPolygon
 * @param fillColor  填充颜色
 * @param strokeColor 边框颜色
 * @param fillOpacity 透明度
 * @param flag 1：设置围栏 2：轨迹回放时加载围栏
 * @returns
 */
function displayPolygon(jsonPolygon, fillColor, strokeColor, fillOpacity, flag){
	var thePolygon = null;
	if(jsonPolygon){
		var myobj = eval(jsonPolygon);  
		var pointsOfPolyline = []; 
		//将坐标点转换成百度经纬度格式的JSON数据
		if (myobj != null && myobj.length > 0) {
			for(var i = 0; i < myobj.length; i++ ){  
				pointsOfPolyline[i] = new BMap.Point(myobj[i].lng, myobj[i].lat);
			}
		}
		
		thePolygon = new BMap.Polygon(pointsOfPolyline, styleOptions);
		if(null==fillColor || ''==fillColor){
			thePolygon.setFillColor("#646464");
		}else{
			thePolygon.setFillColor(fillColor);
		}

		if(null==strokeColor || ''==strokeColor){
			thePolygon.setStrokeColor("#646464");
		}else{
			thePolygon.setStrokeColor(strokeColor);
		}
		if(null==fillOpacity || ''==fillOpacity || fillOpacity<0){
			thePolygon.setFillOpacity(0.6);
		}else{
			thePolygon.setFillOpacity(fillOpacity);
		}
		
		map.addOverlay(thePolygon);//绘制折线组成的多边形
		
		var viewport = map.getViewport(pointsOfPolyline);
		if(viewport){
			zoomMin = zoomMin <= viewport.zoom ? zoomMin : viewport.zoom;
			if (flag == 2) {
				map.centerAndZoom(lastPosition, zoomMin);
			} else {
				map.centerAndZoom(viewport.center, zoomMin);
			}
		}
	}
	
	return thePolygon;
}

/**
 * 
 * @param pt   坐标点
 * @param ply  折线
 * @returns
 * true 线上
 * false 线下
 */
function ptInOutPolyline(pt,ply){
    var result = BMapLib.GeoUtils.isPointOnPolyline(pt, ply);
    return result;
}

/**
 * 
 * @param pt   坐标点
 * @param ply  围栏
 * @returns
 * true 围栏内
 * false 围栏外
 */
function pointInOutPolygon(pt, ply){
    var result = BMapLib.GeoUtils.isPointInPolygon(pt, ply);
    return result;    
}

/**
 * 根据原始多边形返回以point数组组成的新多边形
 * @param polygons
 * @returns
 */
function getNewPolygon(polygons){
	if(null!=polygons || ''==polygons){
		var polygonPath=polygons.getPath();
	    var ply =null;
		 var pts = [];
		 if(null==polygonPath){
			 return null;
		 }else{
			 var pt = null;
			 for(var i=0;i<polygonPath.length;i++){
				 pt = new BMap.Point(polygonPath[i].lng,polygonPath[i].lat);
				 pts.push(pt);
			 }
			 ply = new BMap.Polygon(pts);
		 }
		 return ply;
	}else{
		return null;
	}

}

/**
 * 加载当前城市地图
 */
function loadCurrentCityMap(){
	var myCity = new BMap.LocalCity();
	myCity.get(myFun);
}

function myFun(result){
	var cityName = result.name;
	map.setCenter(cityName);
}


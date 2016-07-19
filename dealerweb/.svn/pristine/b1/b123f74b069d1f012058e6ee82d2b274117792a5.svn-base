<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>线路管理_设置围栏</title>
	<%@ include file="/dealer/include/meta.jsp"%>
	
	<script type="text/javascript"
		src="http://api.map.baidu.com/api?v=2.0&ak=91a73a33c8964682fdba3f89dc96f279"></script>
	<!--加载鼠标绘制工具-->
	<script type="text/javascript"
		src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
	<link rel="stylesheet"
		href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />
		
	<script type="text/javascript" src="http://api.map.baidu.com/library/DistanceTool/1.2/src/DistanceTool_min.js"></script>
	<!--判断点与多边形关系 -->
	<script type="text/javascript" src = "<%=path%>/js/baidumap/GeoUtils_min.js"></script>
	<script type="text/javascript" src = "<%=path%>/js/baidumap/mapCommon.js"></script>
	<!--地图搜索功能 -->
	<script type="text/javascript" src="<%=path%>/js/baidumap/mapSearch.js"></script>
</head>

<script type="text/javascript">
var drawingManager;
var lineType; //线路类型
var cachePolygonOverlay1; //外圈
var cachePolygonOverlay2; //内圈
var cacheReferencePolygon; //参考线路
var fillColor; //填充颜色
var strokeColor; //边框颜色
var pointMarker; // 创建标注
var lng = "",lat = "";
window.onload = function() {
    mapInit();
    toolInit();
};

function toolInit() {
	//实例化鼠标绘制工具
    drawingManager = buildDrawingManager();

    //map.addEventListener('click', checkInnerAndOuterValid());
    //initPagePresentation();//只能放到onload方法里
    //参考线路
    var jsonPolygon1 = '${driveLine.jsonPolygon1}';
    var jsonPolygon2 = '${driveLine.jsonPolygon2}';
    var jsonReferencePolygon = '${driveLine.jsonReferencePolygon}';
    fillColor = '${driveLine.fillColor}';
    if (fillColor == null || fillColor == "") {
        fillColor = "#8B864E";
    }

    strokeColor = '${driveLine.strokeColor}';
    if (strokeColor == null || strokeColor == "") {
        strokeColor = "#8B795E";
    }
    lng = '${driveLine.dealerVO.baiduLng}';
    lat = '${driveLine.dealerVO.baiduLat}';
     if(null != lng && ''!= lng && '0.0' != lng && 
     		null != lat && ''!= lat && '0.0' != lat){
			var currentPoint=new BMap.Point(lng,lat);
			map.setCenter(currentPoint);
			pointMarker = new BMap.Marker(currentPoint, {
             icon: dealerIcon
         });// 创建标注
			map.addOverlay(pointMarker);
		} else {
			 //如没有绘制线路并且经销商位置没设置，则初始化地图城市所在位置
			if ('' == jsonReferencePolygon && '' == jsonPolygon1 && '' == jsonPolygon2) {
	            loadCurrentCityMap();
			}
		}
    pageInit(jsonPolygon1, jsonPolygon2, jsonReferencePolygon);
}

//检查内圈围栏和外圈围栏的相对位置的有效性
function checkInnerAndOuterValid(e) {
    if (lineType == "outer" && cachePolygonOverlay2) {
        var result = BMapLib.GeoUtils.isPointInPolygon(e.point, cachePolygonOverlay2);
        if (result == true) {
            //alert("点在多边形内1");
            //不允许继续绘制
            alert("请在当前多边形外部绘制");
            drawingManager.close();

            openDrawing("outer");
        } else {
            //alert("点在多边形外2");
            //允许继续绘制
        }
    } else if (lineType == "inner" && cachePolygonOverlay1) {
        var result = BMapLib.GeoUtils.isPointInPolygon(e.point, cachePolygonOverlay1);
        if (result == true) {
            //alert("点在多边形内3:" + point.lng + "," + point.lat);
            //允许继续绘制
        } else {
            //alert("点在多边形外4:" + point.lng + "," + point.lat);
            //不允许继续绘制
            alert("请在当前多边形内部绘制");
            drawingManager.close();

            openDrawing("inner");
        }
    }
}

//初始化页面数据显示
function pageInit(jsonPolygon1, jsonPolygon2, jsonReferencePolygon) {
    lineType = "outer";
    var pointsOfPolyline1 = "";

    if (jsonPolygon1 && jsonPolygon1.length > 0) {
        pointsOfPolyline1 = JSON.parse(jsonPolygon1);
        displayJsonContentAndSoOn(pointsOfPolyline1);
    }

    lineType = "inner";
    var pointsOfPolyline2 = "";
    if (jsonPolygon2 && jsonPolygon2.length > 0) {
        pointsOfPolyline2 = JSON.parse(jsonPolygon2);
        displayJsonContentAndSoOn(pointsOfPolyline2);
    }
    
    lineType = "reference";
    var pointsOfReferenceLine = "";
    if (jsonReferencePolygon && jsonReferencePolygon.length > 0) {
    	pointsOfReferenceLine = JSON.parse(jsonReferencePolygon);
        displayJsonContentAndSoOn(pointsOfReferenceLine);
    } 
}

//初始化页面数据显示
function initPagePresentation() {

    lineType = "outer";
    var jsonPolygon1 = '${driveLine.jsonPolygon1}';
    var pointsOfPolyline1 = "";
    if (jsonPolygon1 && jsonPolygon1.length > 0) {
        pointsOfPolyline1 = JSON.parse(jsonPolygon1);
    } 
    displayJsonContentAndSoOn(pointsOfPolyline1);

    lineType = "inner";
    var jsonPolygon2 = '${driveLine.jsonPolygon2}';
    var pointsOfPolyline2 = "";
    if (jsonPolygon2 && jsonPolygon2.length > 0) {
        pointsOfPolyline2 = JSON.parse(jsonPolygon2);
    }
    displayJsonContentAndSoOn(pointsOfPolyline2);
}

function buildDrawingManager() {
	addDealerMarker();
    var dm = buildDrawingManagerCore();
    dm.addEventListener('polylinecomplete', function(polyline) {
        var pointsOfPolyline = polyline.getPath();
        if (pointsOfPolyline.length > 0) {
            //把第一个点和最后一个点连起来
            var firstPoint = pointsOfPolyline[0];
            pointsOfPolyline.push(firstPoint);
            polyline.setPath(pointsOfPolyline);
            displayJsonContentAndSoOn(pointsOfPolyline);
        }
    });
    dm.addEventListener("overlaycomplete", function(e) {
        /*if (lineType == "outer") {
            //考虑到外圈更能贴近的表达里程，故只在绘制外圈的时候更新里程
            var lengthMetre = parseInt(e.calculate); //周长，单位为米
            var mileage = lengthMetre / 1000 / 2; //周长的一般为里程
            $("#mileage").attr("value", mileage * 1000);
            $("#mileageLabel").text(mileage);
        }*/

        mapInit();
        //实例化鼠标绘制工具
        drawingManager = buildDrawingManager();
        pageInit($("#jsonPolygon1").attr("value"), $("#jsonPolygon2").attr("value"), $("#jsonReferencePolygon").attr("value"));
    });

    return dm;
}

//加载经销商图标
function addDealerMarker() {
  if (null != lng && '' != lng && null != lat && '' != lat) {
        var currentPoint = new BMap.Point(lng, lat);
        pointMarker = new BMap.Marker(currentPoint, {
            icon: dealerIcon
        });
        map.addOverlay(pointMarker);
    }
}

function displayJsonContentAndSoOn(pointsOfPolyline) { //根据点数组，转成json后显示，并进行其他相关处理
    //关闭地图的绘制状态
    if (null != drawingManager) {
        drawingManager.close();
    }

    if (!pointsOfPolyline || pointsOfPolyline.length == 0) {
        return;
    }

    var len = pointsOfPolyline.length;
    if (pointsOfPolyline && len > 0) {
        var pointStr = JSON.stringify(pointsOfPolyline);
        if (lineType == "outer") {
            var thePolygon = displayPolygon(pointStr, fillColor, strokeColor, 0.2, 1);

            $("#jsonPolygon1").attr("value", pointStr);
            $("#jsonPolygon1Label").text(pointStr);
            $("#drawingBtn1").attr("value", "重新绘制外圈");
            cachePolygonOverlay1 = thePolygon;
        } else if (lineType == "inner") {
            var thePolygon = displayPolygon(pointStr, fillColor, strokeColor, 0.4, 1);

            $("#jsonPolygon2").attr("value", pointStr);
            $("#jsonPolygon2Label").text(pointStr);
            $("#drawingBtn2").attr("value", "重新绘制内圈");
            cachePolygonOverlay2 = thePolygon;
        } else {
        	var thePolygon = displayPolygon(pointStr, fillColor, "#FF0000", 0.001, 1);
        	cacheReferencePolygon = thePolygon;
        }
    }
}

//开启绘制
function openDrawing(whichPolygon) {
    lineType = whichPolygon;

    clearDrawing();

    if (null != drawingManager) {
        drawingManager.open();
    }
}

//清除绘制
function clearDrawing() {
    if (lineType == "outer") {
        if (cachePolygonOverlay1) {
            map.removeOverlay(cachePolygonOverlay1);
        }
    } else if (lineType == "inner") {
        if (cachePolygonOverlay2) {
            map.removeOverlay(cachePolygonOverlay2);
        }
    }

    if (lineType == "outer") {
        $("#jsonPolygon1").attr("value", "");
        $("#jsonPolygon1Label").text("");
        if (cachePolygonOverlay1) {
            cachePolygonOverlay1 = null;
        }
    } else {
        $("#jsonPolygon2").attr("value", "");
        $("#jsonPolygon2Label").text("");
        if (cachePolygonOverlay2) {
            cachePolygonOverlay2 = null;
        }
    }
    
    addDealerMarker();
}

function save() {
    if (null == cacheReferencePolygon || '' == cacheReferencePolygon || "" == cacheReferencePolygon) {
        alert("请先让经销商绘制【参考线路】");
        return;
    } else if (null == cachePolygonOverlay1 || '' == cachePolygonOverlay1 || "" == cachePolygonOverlay1) {
    	alert("请绘制外围栏");
        return;
    } else if (null == cachePolygonOverlay2 || '' == cachePolygonOverlay2 || "" == cachePolygonOverlay2) {
    	alert("请绘制内围栏");
    } else {
    	var jsonPolygon1 = $("#jsonPolygon1").attr("value");
        if (jsonPolygon1 != null && jsonPolygon1.length > 0) {
			var pointNum = jsonPolygon1.split("{").length-1;// 坐标点个数
			if (pointNum < 4) {
				alert("绘制外围栏的坐标点不能少于3个");
	            return;
			}
        }
			
    	var jsonPolygon2 = $("#jsonPolygon2").attr("value");
        if (jsonPolygon2 != null && jsonPolygon2.length > 0) {
			var pointNum = jsonPolygon2.split("{").length-1;// 坐标点个数
			if (pointNum < 4) {
				alert("绘制内围栏的坐标点不能少于3个");
	            return;
			}
        }
			
        var outPolygonNew = getNewPolygon(cachePolygonOverlay1);
        var referencePolygonNew = getNewPolygon(cacheReferencePolygon);
        
        var innerFenPolygonPath = cachePolygonOverlay2.getPath();
        var referencePolygonPath = cacheReferencePolygon.getPath();
        if (null == innerFenPolygonPath) {
            return null;
        } else {
            var fenceValid = checkFenceValid(innerFenPolygonPath, outPolygonNew);
            if (!fenceValid) {
            	alert("请将内围栏画在【外围栏】之内");
                return;
            }
            if (referencePolygonPath != null) {
                 fenceValid = checkFenceValid(innerFenPolygonPath, referencePolygonNew);
                 if (!fenceValid) {
                	 alert("请将内围栏画在【参考线路围栏】之内");
                     return;
                 }
                 fenceValid = checkFenceValid(referencePolygonPath, outPolygonNew);
                 if (!fenceValid) {
                	 alert("请将外围栏画在【参考线路围栏】之外");
                     return;
                 }
            }
            $("#myPageForm").submit();
        }
    }
}

//检查围栏的有效性
function checkFenceValid(polygonPath, polygonNew){
	var fenceValid = true;
	if (polygonPath == null || polygonNew == null) {
		fenceValid = false;
	} else {
		for (var i = 0; i < polygonPath.length; i++) {
	    	var pt = new BMap.Point(polygonPath[i].lng, polygonPath[i].lat);
	        var isOuter = pointInOutPolygon(pt, polygonNew);
	        if (!isOuter) {
	        	fenceValid = false;
	        }
	    }
	}
    return fenceValid;
}

//地图上查询地点
function query() {
	var suggestId = $("#suggestId").attr("value");
	if (suggestId != null && suggestId.trim().length > 0) {
		mapInit();
		toolInit();
		var local = new BMap.LocalSearch(map, {
			renderOptions:{map: map}
		});
		local.search(suggestId);
	}
}
</script>

<body>
    <section class="bd" id="track">

        <div class="bd_rt" id="audit">
            <div class="bd_rt_nav clearfix">
                <span>当前位置：系统设置 > 线路管理 > 设置围栏</span>
                <a href="<%=path%>/dealer/driveLine/list.vti?startIndex=${startIndex}" style="color:#fff;font-size: 12px;font-family:微软雅黑;text-align: right;" >返回上级</a>
            </div>
            <div class="audit_nav_c">
				请输入经销商位置：<input type="text" id="suggestId" class="ipt_txt" style="width:280px" />
				<input type="button" onclick="query()" class="btn_submit" value="查 询" />
				<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
            </div>
            <aside class="bd_rt_main clearfix">
                <div id="map" style="height:500px;">
                    <div id="map_container" style="margin:2px;height:422px;"></div>
                </div>
                <div class="table_list">
                    <form id="myPageForm" action="<%=path%>/dealer/driveLine/setFence.vti?startIndex=${startIndex}" method="post">
                        <input type="hidden" name="startIndex" value="${startIndex}" />
                        <input type="hidden" name="id" value="${driveLine.id}" />
                        <form:hidden path="driveLine.fenceType" />
                        <form:hidden path="driveLine.jsonPolygon1" />
                        <form:hidden path="driveLine.jsonPolygon2" />
                        <form:hidden path="driveLine.jsonReferencePolygon" />
                        <ul class="table_hd clearfix">
                            <li class="driveLine_companyName">所属单位：${driveLine.companyName}</li>
                            <li class="driveLine_lineName">名称：${driveLine.name}</li>
                            <li class="driveLine_lineTypeNick">类型：${driveLine.type_Nick}</li>
                            <li class="driveLine_fenceTypeNick">围栏类型：${driveLine.fenceType_Nick}</li>
                            <li class="driveLine_lineMileageLabel">里程：<fmt:formatNumber type="number" maxFractionDigits="1" value="${driveLine.mileage/1000}" />公里</li>
                        </ul>
                        <input id="drawingBtn1" type="button" class="btn_submit_big" value="开启绘制外圈" onclick="openDrawing('outer')" title="双击结束" />
                        <input id="drawingBtn2" type="button" class="btn_submit_big" value="开启绘制内圈" onclick="openDrawing('inner')" title="双击结束" />
                        <input type="button" class="btn_submit" value="提 交" onclick="save()" />
                    </form>
                </div>
            </aside>
        </div>
    </section>
</body>

</html>
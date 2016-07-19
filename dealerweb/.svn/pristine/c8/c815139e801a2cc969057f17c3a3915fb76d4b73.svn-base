<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<%@ page import="com.ava.resource.GlobalConstant"%>
<% 
	User leftMenuCurrentUser = SessionManager.getCurrentUser(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>线路管理_设置参考线路</title>
	<%@ include file="/dealer/include/meta.jsp"%>
	<script type="text/javascript"
		src="http://api.map.baidu.com/api?v=2.0&ak=91a73a33c8964682fdba3f89dc96f279"></script>
	<!--加载鼠标绘制工具-->
	<script type="text/javascript"
		src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
	<link rel="stylesheet"
		href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />
	<!--判断点与多边形关系 -->
	<script type="text/javascript" src = "<%=path%>/js/baidumap/GeoUtils_min.js"></script>
	<script type="text/javascript" src = "<%=path%>/js/baidumap/mapCommon.js"></script>
	<!--地图搜索功能 -->
	<script type="text/javascript" src="<%=path%>/js/baidumap/mapSearch.js"></script>
</head>

<script type="text/javascript">
    var drawingManager;
    var fillColor; //填充颜色
    var strokeColor; //边框颜色
    var cachePolygonOverlay; //围栏
    var pointMarker; // 创建标注
    var lng = "",lat = "";
    window.onload = function() {
        mapInit();
        toolInit();
    }

    function toolInit() {
        fillColor = '${driveLine.fillColor}';
        strokeColor = '#FF0000';

        var mileage = '${driveLine.mileage}';
        if (mileage) {
        	$("#mileageLabel").text((parseInt(mileage) / 1000).toFixed(1));
        }

        var driveLine_jsonPolygon1 = '${driveLine.jsonPolygon1}';
        var pointsOfPolyline = '';
        if (null != driveLine_jsonPolygon1 && '' != driveLine_jsonPolygon1) {
            pointsOfPolyline = JSON.parse('${driveLine.jsonPolygon1}');
            displayJsonContentAndSoOn(pointsOfPolyline);
        } 
        if (pointsOfPolyline.length > 0) {
            $("#drawingBtn").attr("value", "重新绘制");
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
			//如没有绘制参考线路并且经销商位置没设置，则初始化地图城市所在位置
			if (null == driveLine_jsonPolygon1 || '' == driveLine_jsonPolygon1) {
	            loadCurrentCityMap();
			}
		}
    }

    function displayJsonContentAndSoOn(pointsOfPolyline) { //根据点数组，转成json后显示，并进行其他相关处理
        closeDrawing(); //关闭地图的绘制状态
        map.clearOverlays();
        if (null != lng && ''!= lng && '0.0' != lng && 
        		null != lat && ''!= lat && '0.0' != lat) {
            var currentPoint = new BMap.Point(lng, lat);
            pointMarker = new BMap.Marker(currentPoint, {
                icon: dealerIcon
            });// 创建标注
            map.addOverlay(pointMarker);
        }

        var len = pointsOfPolyline.length;
        if (pointsOfPolyline && len > 0) {
            var pointStr = JSON.stringify(pointsOfPolyline);
            $("#jsonPolygon1").attr("value", pointStr);
            $("#jsonPolygon1Label").text(pointStr);
	        cachePolygonOverlay = displayPolygon(pointStr, fillColor, strokeColor, 0.001, 1);
        }
    }

    //开启绘制
    function openDrawing() {
            clearDrawing();
            //实例化鼠标绘制工具
            drawingManager = buildDrawingManagerCore();
            drawingManager.addEventListener('polylinecomplete', function(polyline) {
                var pointsOfPolyline = polyline.getPath();
                if (pointsOfPolyline.length > 0) {
                    //把第一个点和最后一个点连起来
                    var firstPoint = pointsOfPolyline[0];
                    pointsOfPolyline.push(firstPoint);
                    polyline.setPath(pointsOfPolyline);
                }
                displayJsonContentAndSoOn(pointsOfPolyline);
            });
            drawingManager.addEventListener("overlaycomplete", function(e) {
                var lengthMetre = parseInt(e.calculate); //周长，单位为米
                if (isNaN(lengthMetre)){
                	lengthMetre = 0;
                }
                var mileage = lengthMetre / 1000 ; //周长的一般为里程
                $("#mileage").attr("value", mileage * 1000);
                $("#mileageLabel").text(mileage.toFixed(1));

                $("#drawingBtn").attr("value", "重新绘制");
            });

            if (null != drawingManager) {
                drawingManager.open();
            }
        }
    //关闭绘制
    function closeDrawing() {
            if (null != drawingManager) {
                drawingManager.close();
            }
        }
    //清除绘制
    function clearDrawing() {
        map.removeOverlay(cachePolygonOverlay);

        $("#mileage").attr("value", "");
        $("#mileageLabel").text("");
        $("#jsonPolygon1").attr("value", "");
        $("#jsonPolygon1Label").text("");

        if (cachePolygonOverlay) {
            cachePolygonOverlay = null;
        }
    }

    function save() {
    	/**if(null == lng || ''== lng || '0.0' == lng || 
        		null == lat || ''== lat || '0.0' == lat){
    		alert("请设置经销商位置");
			return;
		} */
    	
    	$("#lng").val(lng);
		$("#lat").val(lat);
		
        if (null == cachePolygonOverlay || '' == cachePolygonOverlay) {
            alert("请绘制试驾线路");
            return;
        }
        
        var jsonPolygon1 = $("#jsonPolygon1").attr("value");
        if (jsonPolygon1 != null && jsonPolygon1.length > 0) {
			var pointNum = jsonPolygon1.split("{").length-1;// 坐标点个数
			if (pointNum < 4) {
				alert("绘制线路的坐标点不能少于3个");
	            return;
			}
        }
		
    	var mileage = $("#mileage").attr("value");
   		mileage = parseInt(mileage) / 1000;
	    if (mileage == null || mileage == '' || mileage == 0 || isNaN(mileage)) {
	    	 alert("当前试驾线路长度为【0KM】, 请重新绘制!");
	         return;
	    }
        <% if (leftMenuCurrentUser.getCompanyId() != 1) { %>
        var type = '${driveLine.type}';
        if (type != null && type == 2) {
           	if (mileage < 5) {
           		alert("当前试驾线路长度为【" + mileage.toFixed(1) +"KM】, 需大于等于5KM!");
                return;
           	}
        }
        <% } %>
        $("#myPageForm").submit();
    }

    //设置经销商位置
    function setSemcyclePoint() {
        deletePoint();
       
        //实例化鼠标绘制工具
        drawingManager = buildDrawingManagerCore();
        drawingManager.disableCalculate();
        closeDrawing();
        drawingManager.addEventListener('markercomplete', function(overlay) {
            if (drawingManager.getDrawingMode() == BMAP_DRAWING_MARKER) {
                lng = overlay.point.lng;
                lat = overlay.point.lat;
                var currentPoint = new BMap.Point(lng, lat);
                map.removeOverlay(overlay);
                deletePoint();

                addMarker(currentPoint);

                closeDrawing();
            } else {
                closeDrawing(); //关闭地图的绘制状态
            }
        });

        if (null != drawingManager) {
            drawingManager.setDrawingMode(BMAP_DRAWING_MARKER);
            drawingManager.open();
        }
    }

    //创建经销商标注点
    function addMarker(point) {
        pointMarker = new BMap.Marker(point, {
            icon: dealerIcon
        });
        map.addOverlay(pointMarker);
    }

    //清除经销商标注点
    function deletePoint() {
        if (null != pointMarker) {
            map.removeOverlay(pointMarker);
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

<body>
    <section class="bd" id="track">

        <div class="bd_rt" id="audit">
            <div class="bd_rt_nav clearfix">
                <span>当前位置：系统设置 > 线路管理 > 设置参考线路</span>
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
                <!--/#map-->
                <div class="table_list">
                    <form id="myPageForm" action="<%=path%>/dealer/driveLine/setReferenceLine.vti?startIndex=${startIndex}" method="post">
                        <input type="hidden" name="startIndex" value="${startIndex}" />
                        <input type="hidden" name="id" value="${driveLine.id}" />
                        <input type="hidden" id="lng" name="lng" value="" />
						<input type="hidden" id="lat" name="lat" value="" />
                        <form:hidden path="driveLine.companyId" />
                        <form:hidden path="driveLine.fenceType" />
                        <form:hidden path="driveLine.mileage" />
                        <form:hidden path="driveLine.jsonPolygon1" />
                        <ul class="table_hd clearfix">
                            <li class="driveLine_companyName">所属单位：${driveLine.companyName}</li>
                            <li class="driveLine_lineName">名称：${driveLine.name}</li>
                            <li class="driveLine_lineTypeNick">类型：${driveLine.type_Nick}</li>
                            <li class="driveLine_fenceTypeNick">围栏类型：${driveLine.fenceType_Nick}</li>
                            <li class="driveLine_lineMileageLabel">里程：
                                <label id="mileageLabel"></label>公里</li>
                        </ul>
<!--                         <input type="button" class="btn_submit_big" onclick="setSemcyclePoint()" value="设置经销商位置"  /> -->
                        <input id="drawingBtn" type="button" class="btn_submit_big" value="开启绘制线路" onclick="openDrawing()" title="双击结束" />
                        <input type="button" class="btn_submit" value="提 交" onclick="save()" />
                    </form>
                </div>
            </aside>
        </div>
    </section>
</body>

</html>
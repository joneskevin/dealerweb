<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=91a73a33c8964682fdba3f89dc96f279"></script>
<script type="text/javascript"
	src = "<%=path%>/js/baidumap/mapCommon.js"></script>
</head>

<script type="text/javascript">
var fillColor;//填充颜色
var strokeColor;//边框颜色
window.onload = function() {
	fillColor = '${driveLine.fillColor}';
	strokeColor = '${driveLine.strokeColor}';
	mapInit();
	var jsonPolygon1='${driveLine.jsonPolygon1}';
	if(null!=jsonPolygon1 && ''!=jsonPolygon1){
		displayPolygon(jsonPolygon1,  fillColor, strokeColor, 0.2, 1);
	}

	var jsonPolygon2='${driveLine.jsonPolygon2}';
	if(null!=jsonPolygon2 && ''!=jsonPolygon2){
		displayPolygon(jsonPolygon2, fillColor, strokeColor, 0.4, 1);
	}
	
	var jsonReferencePolygon = '${driveLine.jsonReferencePolygon}';
	if(null!=jsonReferencePolygon && ''!=jsonReferencePolygon){
		displayPolygon(jsonReferencePolygon, fillColor, "#FF0000", 0.001, 1);
	}
	
	var semicyclePoint='${driveLine.semicyclePoint}';
	if(null!=semicyclePoint && ''!=semicyclePoint){
		var lngView='',latView='';
		lngView=semicyclePoint.substring(0, semicyclePoint.indexOf(",", 1));
		latView=semicyclePoint.substring(semicyclePoint.indexOf(",", 1)+1,semicyclePoint.length);
		
		var currentPoint=new BMap.Point(lngView,latView);
		var pointMarker = new BMap.Marker(currentPoint);  // 创建标注		
		map.addOverlay(pointMarker);
	}
	
	addDealerMarker();
};

//加载经销商图标
function addDealerMarker() {
   var lng = '${driveLine.dealerVO.baiduLng}';
   var lat = '${driveLine.dealerVO.baiduLat}';
   if (null != lng && ''!= lng && '0.0' != lng && 
  		null != lat && ''!= lat && '0.0' != lat) {
        var currentPoint = new BMap.Point(lng, lat);
        pointMarker = new BMap.Marker(currentPoint, {
            icon: dealerIcon
        });
        map.addOverlay(pointMarker);
    }
}
</script>
</head>
<body>
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
							<tr>
								<td class="tdTitle">围栏类型：</td>
								<td class="tdSpace"><c:out value="${driveLine.fenceType_Nick}" /></td>
								<td class="tdTitle">里程：</td>
								<td class="tdSpace"><fmt:formatNumber type="number" maxFractionDigits="1" value="${driveLine.mileage/1000}" />公里</td>
								<td class="tdTitle">创建日期：</td>
								<td class="tdSpace"><fmt:formatDate value="${driveLine.creationTime}"
											type="both" pattern="yyyy-MM-dd" /></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>
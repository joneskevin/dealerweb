// 汽车标签
var carLabel;
carLabel = new BMap.Label("", {
	offset : new BMap.Size(-20, -20)
// 表示图片相对于所加的点的位置
});

var markerPoints = new Array();// 地图上的覆盖物的位置点的缓存
// 在地图上增加该车标记、在列表中增加该车的行，并在缓存中增加对应的元素
function addVehicle(tableId, vehicle, currentLocation) {
	if (vehicleMarkers.length >= 100) {
		jAlert("地图上显示的车辆数量超过100了，不能再添加车辆了。");
		return;
	}

	var vehicleMarker = getVehicleMarkerById(vehicle.nodeId);
	if (vehicleMarker) {
		// 如果已经存在，就无需再添加
		return;
	}

	var longitude = currentLocation.longitude;
	var latitude = currentLocation.latitude;
	var speed = currentLocation.speed;

	removeVehicle(tableId, vehicle.nodeId);
	
	var rowText = "";
	rowText += "<ul class='table_bd clearfix' id='" + vehicle.nodeId + "'>";
	rowText += "<li class='cnName_240'>" + vehicle.companyName + "</li>";
	rowText += "<li class='plateNumber_75'>" + vehicle.plateNumber + "</li>";
	rowText += "<li class='carStyleId_Nick_200'>" + vehicle.carStyleId_Nick+ "</li>";
	rowText += "<li class='vin_140'>" + vehicle.vin + "</li>";
	rowText += "<li class='plateNumber_75'>" + speed + "</li>";
	rowText += "</ul>";
	$("#" + tableId).append(rowText);

	var aPoint = new BMap.Point(longitude, latitude);
	var aVehicleMarker = new BMap.Marker(aPoint, {
		icon : carIcon
	});
	//aVehicleMarker.setLabel(carLabel);
	aVehicleMarker.id = vehicle.nodeId;
	//aVehicleMarker.setTitle(vehicle.plateNumber);
	map.addOverlay(aVehicleMarker);

	vehicleCache.push(vehicle);// 把车辆对象放入缓存
	vehicleMarkers.push(aVehicleMarker);// 把车辆标记对象放入缓存

	var aVehicleInfo = "";
	aVehicleInfo += "<div style='width:100%;border-bottom:red 1px dashed;padding-bottom:3px;margin-bottom:5px;font-weight:bold;'>" + vehicle.plateNumber + "</div>";
	aVehicleInfo += "<div style='width:100%;'>VIN：" + vehicle.vin + "</div>";
	aVehicleInfo += "<div style='width:100%;'>车型：" + vehicle.carStyleId_Nick + "</div>";
	speed = parseInt(speed);
	aVehicleInfo += "<div style='width:100%;'>速度：" + (speed > 0 ? speed : "0") + " KM/H</div>";
	
	var opts = {width : 250,     // 信息窗口宽度
			    height: 70,     // 信息窗口高度
			    enableMessage:false//设置允许信息窗发送短息 
	};
	var infoWindow = new BMap.InfoWindow(aVehicleInfo, opts);
	aVehicleMarker.addEventListener("mouseover", function() {
		this.openInfoWindow(infoWindow);
	});
}

// 调整地图中心点和缩放级别，让所有的覆盖物可见
function adjustMapView() {
	var markerPoints = new Array();
	if (vehicleMarkers && vehicleMarkers.length > 0) {
		for ( var i = 0; i < vehicleMarkers.length; i++) {
			var aMarker = vehicleMarkers[i];
			markerPoints.push(aMarker.getPosition());
		}
	}

	var viewport = map.getViewport(markerPoints);
	if (viewport) {
		map.centerAndZoom(viewport.center, viewport.zoom-1);
	}
}

// 删除地图的该车的标记、列表中该车的行，并删除对应的缓存
function removeVehicle(tableId, vehicleId) {
	// 从车辆缓存中删除该元素
	removeVehicleById(vehicleId);

	$("#" + tableId).children().each(function(i, domObj){
		if(domObj.id == vehicleId){
			$(domObj).remove();
		}
	});

	var aVehicleMarker = getVehicleMarkerById(vehicleId);
	map.removeOverlay(aVehicleMarker);
	// 从标记缓存中删除该元素
	removeVehicleMarkerById(vehicleId);
}

// 从车辆缓存中获取id并组成逗号分隔的字符串
function getVehicleIdStr() {
	var vehicleIdStr = "";
	if (vehicleCache && vehicleCache.length > 0) {
		for ( var i = 0; i < vehicleCache.length; i++) {
			var vehicle = vehicleCache[i];
			if (vehicle && vehicle.id) {
				vehicleIdStr += vehicle.id;
				if (vehicleCache.length != 1 && i < (vehicleCache.length - 1)) {
					vehicleIdStr += ",";
				}
			}
		}
	}
	return vehicleIdStr;
}

// 车辆缓存---------------------------------------start
var vehicleCache = new Array();
function getVehicleById(vehicleId) {
	if (vehicleCache && vehicleCache.length > 0) {
		for ( var i = 0; i < vehicleCache.length; i++) {
			var vehicle = vehicleCache[i];
			if (vehicle.nodeId == vehicleId) {
				return vehicle;
			}
		}
	}
	return null;
}
function removeVehicleById(vehicleId) {
	if (vehicleCache && vehicleCache.length > 0) {
		for ( var i = 0; i < vehicleCache.length; i++) {
			var aVehicle = vehicleCache[i];
			if (aVehicle.nodeId == vehicleId) {
				vehicleCache.splice(i, 1);
			}
		}
	}
}
// 车辆缓存---------------------------------------end

// 车辆标记缓存---------------------------------------start
var vehicleMarkers = new Array();
function getVehicleMarkerById(vehicleId) {
	if (vehicleMarkers && vehicleMarkers.length > 0) {
		for ( var i = 0; i < vehicleMarkers.length; i++) {
			var aMarker = vehicleMarkers[i];
			if (aMarker.id == vehicleId) {
				return aMarker;
			}
		}
	}
	return null;
}
function removeVehicleMarkerById(vehicleId) {
	if (vehicleMarkers && vehicleMarkers.length > 0) {
		for ( var i = 0; i < vehicleMarkers.length; i++) {
			var aMarker = vehicleMarkers[i];
			if (aMarker.id == vehicleId) {
				vehicleMarkers.splice(i, 1);
			}
		}
	}
}
// 车辆标记缓存---------------------------------------end

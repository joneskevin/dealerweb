var tableId = "table_track";
var playTrackStrokeColor = "#000079";//轨迹线路颜色
var progressBarWidth = 100;
//汽车标签
var carLabel;
carLabel = new BMap.Label("", {
	offset : new BMap.Size(-20, -48)//表示图片相对于所加的点的位置
});

function getNaturalWidth(imgSrc) {
    var image = new Image();
    image.src = imgSrc;
    var naturalWidth = image.width;
    return naturalWidth;
}

var timer; //定时器
var timer_interval_init = 100;//默认的时间间隔，单位毫秒
var timer_interval = timer_interval_init;//定时器的时间间隔，单位毫秒
var dealerMarker;//经销商的位置
var firstPoint;//第一个轨迹点
var aVehicleMarker;//要显示其轨迹的车辆标记
var selectedVehicle;//选中的车辆
var selectedDealer;//选中的车辆所属的经销商
var selectedDriveLines;//选中的经销商的线路
var indexOfCurrentPoint = 0; //记录播放到第几个point
var pointsOfTrack = [];//当前车辆的轨迹点缓存
var locationsCache = [];//当前车辆的位置对象缓存
var playSwitch = false;//播放开关
var pauseSwitch = false;//暂停开关
var palyOver = false;//播放完成标记
var lastPosition = null;//最后车辆位置点

//构建地图上的4S店对象
function buildDealerMarker(dealer) {
	if(null != dealer && null != dealer.baiduLng && null != dealer.baiduLat && dealer.baiduLng != 0 && dealer.baiduLat != 0){
		dealerMarker = new BMap.Marker(new BMap.Point(dealer.baiduLng, dealer.baiduLat), {
			icon : dealerIcon
		});
		map.addOverlay(dealerMarker);
	
		var aDealerInfo = "";
		aDealerInfo += "<div id='vehicleInfoWindow' style='width:100%;border-bottom:red 1px dashed;padding-bottom:3px;margin-bottom:5px;font-weight:bold;'>" + selectedDealer.cnName + "</div>";
		aDealerInfo += "<div style='width:100%;'>经度：" + selectedDealer.baiduLng + "</div>";
		aDealerInfo += "<div style='width:100%;'>纬度：" + selectedDealer.baiduLat + "</div>";
		var opts = {width : 250,     // 信息窗口宽度
			    height: 40,     // 信息窗口高度
			    enableMessage:false//设置允许信息窗发送短息 
		};
		var infoWindow = new BMap.InfoWindow(aDealerInfo, opts);
		dealerMarker.addEventListener("mouseover", function() {
			this.openInfoWindow(infoWindow);
		});
	
		return dealerMarker;
	} else {
		return null;
	}
}

//构建地图上的车辆对象
function buildVehicleMarker() {
	if(!firstPoint){
		return null;
	}
	aVehicleMarker = new BMap.Marker(firstPoint, {
		icon : carIcon
	});
	aVehicleMarker.setLabel(carLabel);
	map.addOverlay(aVehicleMarker);

	return aVehicleMarker;
}

//构建地图上的车辆的提示窗
function buildVehicleInfoWindow(aVehicleMarker, location){
	if(!aVehicleMarker){
		return;
	}
	var aVehicleInfo = "";
	aVehicleInfo += "<div id='vehicleInfoWindow' style='width:100%;border-bottom:red 1px dashed;padding-bottom:3px;margin-bottom:5px;font-weight:bold;'>" + selectedVehicle.plateNumber + "</div>";
	if(location){
		aVehicleInfo += "<div style='width:100%;'>经度：" + location.lng + "</div>";
		aVehicleInfo += "<div style='width:100%;'>纬度：" + location.lat + "</div>";
	}
	//TODO  此方法是鼠标移到车上显示当前坐标信息
	//var infoWindow = new BMap.InfoWindow(aVehicleInfo);
	//	aVehicleMarker.addEventListener("mouseover", function() {
	//		this.openInfoWindow(infoWindow);
	//	});
}

//步进式播放
function step() {
	//再次播放时，如果播放完成，则清空轨迹详细列表
	if (palyOver) {
		$("#" + tableId).empty();
		palyOver = false;
	}
	
	if(indexOfCurrentPoint > 0 && indexOfCurrentPoint == (pointsOfTrack.length)){//播放结束
		pauseSwitch = false;
		playSwitch = false;
		palyOver = true;
        
		map.panTo(point);
		indexOfCurrentPoint = 0;
		$("#btnPlay").attr("disabled", false);
		jAlert("播放完成", "来自系统的消息");
		return;
	} 
	
	var point = pointsOfTrack[indexOfCurrentPoint];
	if(!point || !point.lng || !point.lat){
		return;
	}
	if (indexOfCurrentPoint > 0) {
		map.addOverlay(new BMap.Polyline([ pointsOfTrack[indexOfCurrentPoint - 1], point ], {
			strokeColor : playTrackStrokeColor,
			strokeWeight : 2,
			strokeOpacity : 1
		}));
	}
	carLabel.setContent("车牌：" + selectedVehicle.plateNumber + "<br/>经度：" + point.lng.toFixed(6) + "<br/>纬度：" + point.lat.toFixed(6));
	if(aVehicleMarker){
		aVehicleMarker.setPosition(point);
		buildVehicleInfoWindow(aVehicleMarker, point);
	}
	indexOfCurrentPoint++;

	if (!mapFollowSwitch || mapFollowSwitch.checked) {
		if(indexOfCurrentPoint % 5 == 0){
			map.panTo(point);
		}
	}

	timer = window.setTimeout("step(" + indexOfCurrentPoint + ")", timer_interval);
	
	//更新进度
	var progress = indexOfCurrentPoint / pointsOfTrack.length;
	updateProgress(progress);
	
	//更新当前位置
	var locationRowContainer = $("#" + tableId);
	if(locationRowContainer){		
		var currentLocation = locationsCache[indexOfCurrentPoint];
		if(currentLocation){
			lastPosition = currentLocation;
			var creationTime = currentLocation.creationTime;
			//var direction = currentLocation.direction;
			var speed = currentLocation.speed;
			var rowText = "";
			rowText += "<ul class='table_bd clearfix'  id='" + currentLocation.vehicleId + "'>";
			rowText += "<li class='li_3'>" + selectedVehicle.plateNumber + "</li>";
			rowText += "<li class='li_3'>" + toNormalDateTime(creationTime) + "</li>";
			//rowText += "<li style='border:0' class='li_3'>" + direction + "</li>";
			rowText += "<li class='li_3'>" + speed + "</li>";
			rowText += "<li class='li_3'><a href='#' onclick='updatePosition(" + indexOfCurrentPoint + ")'>详细位置</a></li>";
			rowText += "</ul>";
			locationRowContainer.prepend(rowText);
		}	
	}
}

function updatePosition(positionIndex){
	var location = pointsOfTrack[positionIndex];
	if(location){
		aVehicleMarker.setPosition(location);

		map.panTo(location);
		carLabel.setContent("车牌：" + selectedVehicle.plateNumber + "<br/>经度：" + location.lng.toFixed(6) + "<br/>纬度：" + location.lat.toFixed(6));
	}
}

function updateProgress(progress){
	var progressImgObj = $("#progressImg");
	if(progressImgObj){
		progressImgObj.width(parseInt(progressBarWidth * progress));
		//$("#progressText").text(parseInt(progress * 100) + "%");	
	}
}

//重新播放
function restartPlay() {
	if(playSwitch){
		return;
	}

	if (pauseSwitch) {
		goOn();
	}else{
		if (null != selectedDealer && null != selectedDealer.baiduLng && null != selectedDealer.baiduLat && selectedDealer.baiduLng != 0 && selectedDealer.baiduLat != 0) {
			var pointOfDealer = new BMap.Point(selectedDealer.baiduLng, selectedDealer.baiduLat);
			map.panTo(pointOfDealer);
		}
		
		if (timer) {
			window.clearTimeout(timer);
		}
		indexOfCurrentPoint = 0;
		
		if(!aVehicleMarker){
			aVehicleMarker = buildVehicleMarker();
		}
		map.addOverlay(aVehicleMarker);
		
		playSwitch = true;//标识开始播放
		step();		
	}
}

//停止
function stop() {
	if (selectedVehicle != null && firstPoint != null) {
		pauseSwitch = false;
		playSwitch = false;
	
		if (timer) {
			window.clearTimeout(timer);
		}
		map.panTo(firstPoint);
		
		indexOfCurrentPoint = 0;
		
		map.clearOverlays();
		$("#" + tableId).empty();
		showMapTable(false);//隐藏轨迹数据列表
		
		aVehicleMarker.setPosition(firstPoint);
		carLabel.setContent("车牌：" + selectedVehicle.plateNumber + "<br/>经度：" + firstPoint.lng.toFixed(6) + "<br/>纬度：" + firstPoint.lat.toFixed(6));
		aVehicleMarker.setLabel(carLabel);
			
		map.addOverlay(aVehicleMarker);
	
		updateProgress(0);
		buildDealerMarker(selectedDealer);
		displayDrivelines(selectedDriveLines);
	}
}

//暂停
function pause() {
	if (selectedVehicle != null && firstPoint != null) {
		pauseSwitch = true;
		playSwitch = false;
		
		if (timer) {
			window.clearTimeout(timer);
		}
	}
}

//继续播放
function goOn() {
	pauseSwitch = false;
	playSwitch = true;
	
	step();
}

//清除轨迹以及列表内容
function clearAll() {
	if (selectedVehicle != null && firstPoint != null) {
		map.clearOverlays();
		$("#" + tableId).empty();
		//showMapTable(false);//隐藏轨迹数据列表
		map.addOverlay(aVehicleMarker);
	
		buildDealerMarker(selectedDealer);
		displayDrivelines(selectedDriveLines);
		if (carLabel) {
			aVehicleMarker.setLabel(carLabel);
		}
	}
	
}

//调整播放速度
function changePlaySpeed(sliderValue) {
	timer_interval = sliderValue;
	
	if (timer) {
		window.clearTimeout(timer);
	}

	if (pauseSwitch) {
	}else{
		if(playSwitch){
			step();
		}
	}
}

function displayDrivelines(selectedDriveLines){
	if(selectedDriveLines){
		for(var i=0; i<selectedDriveLines.length; i++){
			var driveLine = selectedDriveLines[i];
			if(driveLine){
				if(driveLine.jsonPolygon1 && driveLine.jsonPolygon1.length > 0){
					thePolygon1 = displayPolygon(driveLine.jsonPolygon1, driveLine.fillColor, driveLine.strokeColor, 0.2, 2);
				}
				if(driveLine.jsonPolygon2 && driveLine.jsonPolygon2.length > 0){
					thePolygon2 = displayPolygon(driveLine.jsonPolygon2, driveLine.fillColor, driveLine.strokeColor, 0.4, 2);
				}
			}
		}
	}
}
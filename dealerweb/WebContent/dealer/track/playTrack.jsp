<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<%
	Integer userMenu = (Integer )request.getAttribute("userMenu");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>试驾明细_轨迹回放</title>
	<%@ include file="/dealer/include/meta.jsp"%>
	
	<script type="text/javascript"
		src="http://api.map.baidu.com/api?v=2.0&ak=91a73a33c8964682fdba3f89dc96f279"></script>
	<!--加载鼠标绘制工具-->
	<script type="text/javascript"
		src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
	<link rel="stylesheet"
		href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />
	
	<script type="text/javascript"
		src = "<%=path%>/js/jquery/autocomplete/jquery.autocomplete.pack.js"></script>
	<link rel="stylesheet"
		href="<%=path%>/js/jquery/autocomplete/jquery.autocomplete.css" />
		
	<script type="text/javascript"
		src = "<%=path%>/js/baidumap/mapCommon.js"></script>
	<script type="text/javascript"
		src = "<%=path%>/js/baidumap/playTrack.js"></script>
	<link rel="stylesheet"
		href="<%=path%>/css/trackPlay.css" />
</head>
	
<script type="text/javascript">
var mapFollowSwitch;//画面跟随开关
var playTrackZoon = 14;//轨迹播放地图显示级别
var thePolygon1;
var thePolygon2;

//轨迹播放条件
var txtPlateNumber = null;
var txtStartTime = null;
var txtEndTime = null;

$(function(){	
	cleanCache();
	map = new BMap.Map(mapContainerId,{minZoom:5,maxZoom:18,enableMapClick:false});
	map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件
	map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
	map.addControl(new BMap.OverviewMapControl({
		isOpen : true
	})); // 添加缩略地图控件
	map.enableScrollWheelZoom();
	//首次加载经销商位置和线路
	loadDealerAndLine();

	//mapFollowSwitch = document.getElementById("follow");
	//初始化进度条的长度
	progressBarWidth = getNaturalWidth($("#progressImg").attr("src"));
	
    $(document).bind("contextmenu",function(e){  
        //return false;//disabled right click 
    });    

    $( ".play_speed" ).bind("click", function(event) {
    	var radioObj = event.target;
   		var intervalValue = 405 - parseInt(radioObj.value);
   		changePlaySpeed(intervalValue);
    });
    
	//默认隐藏列表
	showMapTable(false);
});

//首次加载初始化全局变量
function cleanCache() {
	dealerMarker = null;//经销商的位置
	firstPoint = null;//第一个轨迹点
	aVehicleMarker = null;//要显示其轨迹的车辆标记
	selectedVehicle = null;;//选中的车辆
	selectedDealer = null;;//选中的车辆所属的经销商
	selectedDriveLines = null;//选中的经销商的线路
	indexOfCurrentPoint = 0; //记录播放到第几个point
	pointsOfTrack = [];//当前车辆的轨迹点缓存
	locationsCache = [];//当前车辆的位置对象缓存
	playSwitch = false;//播放开关
	pauseSwitch = false;//暂停开关
	palyOver = false;//播放完成标记
	lastPosition = null;//最后车辆位置点
}

function showTrackList(isShow){
	if(isShow){
		if( $('#btn_hide').text() == '显示' ){
			$('#table_track').toggle();
			$('#btn_hide').text('隐藏');
		}
	}else{
		if( $('#btn_hide').text() == '隐藏' ){
			$('#table_track').toggle();
			$('#btn_hide').text('显示');
		}
	}	
}

function showMapTable(isShow){
	if(isShow){
		$("#map_table_updown_icon").removeClass().addClass("map_table_updown_icon_open");
		$("#map_table_updown").removeClass().addClass("map_table_updown_open");
	}else{
		$("#map_table_updown_icon").removeClass().addClass("map_table_updown_icon_close");
		$("#map_table_updown").removeClass().addClass("map_table_updown_close");
	}
}
function btnShowMapTable(){
	var map_table_updown_icon=$("#map_table_updown_icon");
	var map_table_updown=$("#map_table_updown");
	if(null!=map_table_updown_icon && null!=map_table_updown){
		if("map_table_updown_icon_close"==map_table_updown_icon.attr("class") && "map_table_updown_close"==map_table_updown.attr("class")){
			showMapTable(true);
		}else if("map_table_updown_icon_open"==map_table_updown_icon.attr("class") && "map_table_updown_open"==map_table_updown.attr("class")){
			showMapTable(false);
		}else{
			alert("出现异常");
		}
	}
}

//如果条件有改变，则清空轨迹详细列表
function checkCondtion (selectedVehiclePlateNumber, startTime, endTime) {
	var checkConditionFlag = false;
    if (txtPlateNumber == null) {
    	txtPlateNumber = selectedVehiclePlateNumber;
    } else {
    	if (txtPlateNumber != selectedVehiclePlateNumber) {
    		txtPlateNumber = selectedVehiclePlateNumber;
    		checkConditionFlag = true;
    		map.clearOverlays();
    	}
    	
    }
    
    if (txtStartTime == null) {
    	txtStartTime = startTime;
    } else {
    	if (txtStartTime != startTime) {
    		txtStartTime = startTime;
    		checkConditionFlag = true;
    	}
    	
    }
    
    if (txtEndTime == null) {
    	txtEndTime = endTime;
    } else {
    	if (txtEndTime != endTime) {
    		txtEndTime = endTime;
    		checkConditionFlag = true;
    	}
    	
    }
    
    if (checkConditionFlag) {
    	$("#" + tableId).empty();
    }
}

var playingFlag = 0;

//获取所有点的坐标
function getTrackAndPlay(){
	if( playingFlag == 1)
	{
		return;	
	}
	var selectedVehiclePlateNumber = $("#txtPlateNumber").attr("value");
	var startTime = $("#txtStartTime").attr("value");
	var endTime = $("#txtEndTime").attr("value");
	var targetUrl = "<%=path%>/dealer/track/findLocation.vti?r=" + Math.random();
	targetUrl += "&plateNumber=" + encodeURI(selectedVehiclePlateNumber);
	targetUrl += "&startTime=" + startTime;
	targetUrl += "&endTime=" + endTime;
	playingFlag = 1;
	$.ajax({
		url: targetUrl,
	    type: 'GET',
	    dataType: 'json',
	   // timeout: 10000,
	    error: function(){
	    	playingFlag = 0;
	        alert('系统异常');
	    },
	    success: function(responseData){
		    if(responseData.code >= 1){
	    		if(responseData.firstItem){
	    			currentLocations = responseData.firstItem;
			    	if(currentLocations && currentLocations.length > 0){
					    if(responseData.code == 2){
							jAlert(responseData.message);
					    }
					    checkCondtion(selectedVehiclePlateNumber, startTime, endTime);
					    
					    locationsCache = [];
			    		pointsOfTrack = [];
						var locationSize = currentLocations.length;
						for(var i=0; i<locationSize; i++){
							var aLocation = currentLocations[i];
							if (i==0) {
					    		locationsCache.push(aLocation);
								var aPoint = new BMap.Point(aLocation.longitude, aLocation.latitude);
								//添加第一个位置
								pointsOfTrack.push(aPoint);
							} else {
								if (aLocation.speed > 0) {
									locationsCache.push(aLocation);
									var aPoint = new BMap.Point(aLocation.longitude, aLocation.latitude);
									//从第二位位置起，必须要速度大于0才添加
									pointsOfTrack.push(aPoint);
								}
							}
							
						}
						
						firstPoint = pointsOfTrack[0];//缓存第一个位置
						
						//showMapTable(true);//显示轨迹数据列表
						
						selectedVehicle = responseData.data.vehicle;
						selectedDriveLines = responseData.data.driveLines;
						//如果同一辆车重复播放轨迹，将不重新加载围栏
						if (selectedDealer != null && selectedDealer.dealerCode == selectedVehicle.dealer.dealerCode) {
							if(null==selectedDriveLines || ''==selectedDriveLines){
								displayDrivelines(selectedDriveLines);
							}
						} else {
							displayDrivelines(selectedDriveLines);
						}
						selectedDealer = selectedVehicle.dealer;
						
						buildDealerMarker(selectedDealer);
						restartPlay();
						
					}else{
						checkCondtion(selectedVehiclePlateNumber, startTime, endTime);
						jAlert("没有轨迹");
					}
	    		} else {
	    			checkCondtion(selectedVehiclePlateNumber, startTime, endTime);
	    			jAlert(responseData.message);
	    		}
		    } else {
		    	checkCondtion(selectedVehiclePlateNumber, startTime, endTime);
	    		jAlert(responseData.message);
		    }
		    playingFlag = 0;
	    }
	});
}
//加载经销商位置和线路
function loadDealerAndLine(){
	var selectedVehiclePlateNumber = $("#txtPlateNumber").attr("value");
	var targetUrl = "<%=path%>/dealer/track/findDriveLine.vti?r=" + Math.random();
	targetUrl += "&plateNumber=" + encodeURI(selectedVehiclePlateNumber);
	$.ajax({
		url: targetUrl,
	    type: 'GET',
	    dataType: 'json',
	    timeout: 10000,
	    error: function(){
	        alert('系统异常');
	    },
	    success: function(responseData){
		    if(responseData.code >= 1){
		    	if (responseData.data) {
		    		if (responseData.data.vehicle) {
			    		selectedVehicle = responseData.data.vehicle;
			    		selectedDealer = selectedVehicle.dealer;
						buildDealerMarker(selectedDealer);
						var currentPoint=new BMap.Point(selectedDealer.baiduLng,selectedDealer.baiduLat);
						map.centerAndZoom(currentPoint, playTrackZoon);
			    	} else {
			    		map.centerAndZoom(centerPoint, playTrackZoon);
			    	}
			    	if (responseData.data.driveLines) {
			    		selectedDriveLines = responseData.data.driveLines;
						displayDrivelines(selectedDriveLines);
			    	} 
		    	} else {
		    		map.centerAndZoom(centerPoint, playTrackZoon);
		    	}
		    } else {
	    		map.centerAndZoom(centerPoint, playTrackZoon);
	    	}
		 }
	});
}

//输入车牌时智能提示
$(document).ready(function(){
	var targetUrl = "<%=path%>/dealer/track/findVehicle.vti?r=" + Math.random();
	$("#txtPlateNumber").autocomplete(targetUrl, {
		width: 160,
		max: 50,
		scroll: true,
		scrollHeight: 300,
		dataType: 'json',
		matchCase:true,
		extraParams: { 
			autoCompleteFieldName: function() { 
				return 's_extraParaName'; 
			}
		},
		parse: function(responseData) {
			var rows = [];
		    if(responseData.code == 1){
	    		if(responseData.firstItem){
			    	var vehicles = responseData.firstItem;
					for(var i=0; i<vehicles.length; i++){
						var aVehicle = vehicles[i];
						rows.push({
							"data" : aVehicle.plateNumber,//display as tip list
							"value" : aVehicle.plateNumber,//hidden
							"result" : aVehicle.plateNumber//hidden
						});
					}
	    		}
		    }else{
	    		jAlert(responseData.message);
		    }
			return rows;
		},
		formatItem: function(data, i, total) {
			return data;
		}
	});
});

function historyBack(){
	if($.browser.msie) { 
		history.back(); 
	} else if($.browser.mozilla) { 
		window.history.back(); 
	} else { 
		window.history.back();
		return false;
	}
	return true;
}
</script>
<body>
<section class="bd" id="track">

	<div class="bd_rt" id="audit">
		<div class="bd_rt_nav clearfix">
			<c:if test="${menuType == 1}" >
			<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
			</c:if>
			<c:if test="${menuType == 0}" >
			<div ><span>当前位置：车辆信息 > 轨迹回放</span></div>
			<% if(userMenu ==null){
				%>	
				<div class="audit_nav_c">
					<a href="javascript:history.go(-1);"  style=" color:#fff;">返回</a>
				</div>
				<%
			} %>
			</c:if>
			<!-- <div class="audit_nav_c">
				<a href="#" onclick="return historyBack();" style=" color:#fff;">返回</a>
			</div>	 -->
		</div>
		<div id="map_table_updown_icon" class="map_table_updown_icon_close"><a onclick="btnShowMapTable();"></a></div>
		<div id="map_table_updown" class="map_table_updown_close">
			<div class="table_list">
				<ul class="table_hd clearfix" >
					<li class="li_3">车牌</li>
            		<li class="li_3">GPS时间</li>
            		<li class="li_3">速度(KM/H)</li>
            		<li class="li_3">查看位置</li>
				</ul>
				<div id="table_track">
				</div>
			</div>
		</div>
		<aside class="bd_rt_main clearfix" style="margin:0px;height:505px;">
			<div id="map" >
				
				<div id="map_container" style="margin:0px;height:100%;"></div>

				<div id="map_play">
					
					<div class="map-play">
						<table class="map-play-container">
					    	<tr>
					        	<td><span class="map_close"></span></td>
					            <td>
					            	<table class="map-play-txt">					                	
					                     <tr>
					                    	<td width="80px">车牌：</td>
					                        <td><input name="plateNumber" type="text" id="txtPlateNumber" value="${plateNumber}" style="font-size:12px;border:1px solid #666;"></td>
					                    </tr>
					                    <tr>
					                        <td>开始时间：</td>
					                        <td><input name="startTime" type="text" id="txtStartTime" value="${startTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" class="Wdate left" style="width:118px;"></td>
					                    </tr>
					                    <tr>
					                        <td>结束时间：</td>
					                        <td><input name="endTime" type="text" id="txtEndTime" value="${endTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" class="Wdate left" style="width:118px;"></td>
					                    </tr>
					                    <tr>
					                        <td>播放进度：</td>
					                        <td>
												<div class="taskPro">
													<img id="progressImg" src="<%=path%>/dealer/images/tastPro.png"
														alt="播放进度" width='0' />
												</div>
											</td>
					                    </tr>
					                    <tr>
					                    	<td>播放速度：</td>
					                        <td>
												<div class="play_speed">
													<input type="radio" class="btn_play_speed" name="playSpeed" value="100"/>
													<label>X1</label>
													<input type="radio" class="btn_play_speed" name="playSpeed" value="200" checked/>
													<label>X2</label>
													<input type="radio" class="btn_play_speed" name="playSpeed" value="400"/>
													<label>X4</label>
												</div>
					                        </td>
					                    </tr>
					                </table>
					            </td>
					        </tr>
					    </table>
					    <footer>
							<button id="btnPlay" onclick="getTrackAndPlay();" class="btn_play"></button>
							<button id="btnSuspend"  onclick="pause();" class="btn_pause"></button>
							<button id="btnStop" onclick="stop();" class="btn_stop"></button>
							<button id="btnClear" onclick="clearAll()" class="btn_clear"></button>
						</footer>
					</div>
				</div>
				
			
			</div><!--/#map-->
		</aside><!--/bd_rt_main-->
	</div><!--/.bd_rt-->
</section><!--/.bd-->
</body>
</html>
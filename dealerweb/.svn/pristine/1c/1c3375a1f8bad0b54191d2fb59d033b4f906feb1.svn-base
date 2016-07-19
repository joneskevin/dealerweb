<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>试驾明细_实时监控</title>
	<%@ include file="/dealer/include/meta.jsp"%>
	
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=91a73a33c8964682fdba3f89dc96f279"></script>
		
	<script type="text/javascript" src = "<%=path%>/js/baidumap/mapCommon.js"></script>
	<script type="text/javascript" src = "<%=path%>/js/baidumap/monitorVehicle.js"></script>
</head>

<script type="text/javascript">
$().ready(function(){
	mapInit(14);
	
	buildTree();
	
	//默认隐藏树
	//toggleTreeMenu();
	//默认隐藏列表
	//showLocationList(false);
	changeMapTree(false);
	/** 刷新车辆位置、车速等车辆实时状态 */
	//setInterval(refreshVehicleStatus, 6000);
});

function showChinaMap(){
	var chinaCenter = new BMap.Point(110,36);
	var zoomLevel = 5;
	map.centerAndZoom(chinaCenter, zoomLevel);
}

function toggleTreeMenu(){
	$("#monitorMenu").toggle();
	var oldText = $("#toggleMenuButton").text();
	if("隐藏菜单" == oldText){
		$("#toggleMenuButton").text("显示菜单");
		$("#monitorMap").css("width", "100%");
	}else{
		$("#toggleMenuButton").text("隐藏菜单");
		$("#monitorMap").css("width", "80%");
	}
}

function showLocationList(isShow){
	if(isShow){
		if( $('#btn_hide').text() == '显示' ){
			$("#list_title").toggle();
			$('#table_track').toggle();
			$('#btn_hide').text('隐藏');
		}
	}else{
		if( $('#btn_hide').text() == '隐藏' ){
			$("#list_title").toggle();
			$('#table_track').toggle();
			$('#btn_hide').text('显示');
		}
	}
}

function showMapMenu(isShow){
	var monitorMapTree=$("#monitorMapTree");
	if(isShow){
		monitorMapTree.removeClass().addClass("tree_open");
		$("#monitorMap").css("width", "100%");
		$("#monitorMenu").show();
	}else{
		monitorMapTree.removeClass().addClass("tree_close");
		$("#monitorMap").css("width", "100%");
		$("#monitorMenu").hide();
	}
}
function toggleTreeMenuNew(){
	var monitorMapTree=$("#monitorMapTree");
	if(null!=monitorMapTree){
		if("tree_open"==monitorMapTree.attr("class")){
			showMapMenu(false);
			changeMapTable(true);
		}else if("tree_close"==monitorMapTree.attr("class")){
			showMapMenu(true);
			changeMapTable(false);
		}else{
			alert("3出现异常");
		}
	}
}

function changeMapTable(isShow){
	
	var map_table_icon=$("#monitorMap_table_icon");
	var map_table=$("#monitorMap_table");
	if(isShow){
		if(null!=map_table_icon && null!=map_table){
			if("monitorMap_table_icon_close_middle"==map_table_icon.attr("class") && "monitorMap_table_close_middle"==map_table.attr("class")){
				map_table_icon.removeClass().addClass("monitorMap_table_icon_close_left");
				map_table.removeClass().addClass("monitorMap_table_close_left");
			}else if("monitorMap_table_icon_open_middle"==map_table_icon.attr("class") && "monitorMap_table_open_middle"==map_table.attr("class")){
				map_table_icon.removeClass().addClass("monitorMap_table_icon_open_left");
				map_table.removeClass().addClass("monitorMap_table_open_left");
			}else if("monitorMap_table_icon_close_left"==map_table_icon.attr("class") && "monitorMap_table_close_left"==map_table.attr("class")){
				map_table_icon.removeClass().addClass("monitorMap_table_icon_close_left");
				map_table.removeClass().addClass("monitorMap_table_close_left");
			}else if("monitorMap_table_icon_open_left"==map_table_icon.attr("class") && "monitorMap_table_open_left"==map_table.attr("class")){
				map_table_icon.removeClass().addClass("monitorMap_table_icon_open_left");
				map_table.removeClass().addClass("monitorMap_table_open_left");
			}
		}
	}else{
		if(null!=map_table_icon && null!=map_table){
			if("monitorMap_table_icon_close_middle"==map_table_icon.attr("class") && "monitorMap_table_close_middle"==map_table.attr("class")){
				map_table_icon.removeClass().addClass("monitorMap_table_icon_close_middle");
				map_table.removeClass().addClass("monitorMap_table_close_middle");
			}else if("monitorMap_table_icon_open_middle"==map_table_icon.attr("class") && "monitorMap_table_open_middle"==map_table.attr("class")){
				map_table_icon.removeClass().addClass("monitorMap_table_icon_open_middle");
				map_table.removeClass().addClass("monitorMap_table_open_middle");
			}else if("monitorMap_table_icon_close_left"==map_table_icon.attr("class") && "monitorMap_table_close_left"==map_table.attr("class")){
				map_table_icon.removeClass().addClass("monitorMap_table_icon_close_middle");
				map_table.removeClass().addClass("monitorMap_table_close_middle");
			}else if("monitorMap_table_icon_open_left"==map_table_icon.attr("class") && "monitorMap_table_open_left"==map_table.attr("class")){
				map_table_icon.removeClass().addClass("monitorMap_table_icon_open_middle");
				map_table.removeClass().addClass("monitorMap_table_open_middle");
			}
		}
	}
}

function changeMapTree(isShow){
	var monitorMapTree=$("#monitorMapTree");
	var map_table_icon=$("#monitorMap_table_icon");
	var map_table=$("#monitorMap_table");
	if(isShow){
		if(null!=monitorMapTree){
			if("tree_open"==monitorMapTree.attr("class")){
				map_table_icon.removeClass().addClass("monitorMap_table_icon_open_middle");
				map_table.removeClass().addClass("monitorMap_table_open_middle");
				//$('#table_track').toggle();
			}else if("tree_close"==monitorMapTree.attr("class")){
				map_table_icon.removeClass().addClass("monitorMap_table_icon_open_left");
				map_table.removeClass().addClass("monitorMap_table_open_left");
				//$('#table_track').toggle();
			}
		}
	}else if(!isShow){
		if(null!=monitorMapTree){
			if("tree_open"==monitorMapTree.attr("class")){
				map_table_icon.removeClass().addClass("monitorMap_table_icon_close_middle");
				map_table.removeClass().addClass("monitorMap_table_close_middle");
				//$('#table_track').toggle();
			}else if("tree_close"==monitorMapTree.attr("class")){
				map_table_icon.removeClass().addClass("monitorMap_table_icon_close_left");
				map_table.removeClass().addClass("monitorMap_table_close_left");
				//$('#table_track').toggle();
			}
		}
	}
}
function btnShowMapTable(){
	var map_table_icon=$("#monitorMap_table_icon");
	var map_table=$("#monitorMap_table");
	if(null!=map_table_icon && null!=map_table){
		if("monitorMap_table_icon_close_middle"==map_table_icon.attr("class") && "monitorMap_table_close_middle"==map_table.attr("class")){
			changeMapTree(true);
		}else if("monitorMap_table_icon_open_middle"==map_table_icon.attr("class") && "monitorMap_table_open_middle"==map_table.attr("class")){
			changeMapTree(false);
		}else if("monitorMap_table_icon_close_left"==map_table_icon.attr("class") && "monitorMap_table_close_left"==map_table.attr("class")){
			changeMapTree(true);
		}else if("monitorMap_table_icon_open_left"==map_table_icon.attr("class") && "monitorMap_table_open_left"==map_table.attr("class")){
			changeMapTree(false);
		}else{
			alert("map_table_icon:"+map_table_icon.attr("class")+"-----monitorMap_table_updown"+map_table.attr("class"));
			alert("1出现异常");
		}
	}
}
</script>

<script language=JavaScript>	
var dTree;
function buildTree(){
	dTree = createTree('tree_orgAndVehicle', "${dhtmlXtreeXML}", '', true);

	dTree.attachEvent("onCheck", function(id, state){
		var isVehicle = this.getUserData(id, "isVehicle");
		if(isVehicle == "true"){
    		if(state == 1){//选中则添加车辆到列表中
    			var vehicleId = id.substring(7);
    			var targetUrl = "<%=path%>/dealer/monitor/getVehicle.vti?r=" + Math.random();
    			targetUrl += "&vehicleId=" + vehicleId;
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
    					    	var vehicle = responseData.firstItem;
    					    	if(vehicle){
    								var currentLocation = vehicle.currentLocation;
    								if(currentLocation){
    									//showLocationList(true);
    									changeMapTree(true);
    									addVehicle("table_track", vehicle, currentLocation);
    		    						//增加完标记后调整地图中心点和缩放级别
    		    						adjustMapView();
    		    						
    							    	/** 设置列表的样式 */
    							    	setTableListStyle("table_track");
    								}else{
    									jAlert("车辆[" + vehicle.plateNumber + "]没有位置信息，无法显示");
    								}
    							}else{
    								jAlert("车辆[id=" + id +"]不存在");
    							}
    			    		}
    				    }else{
    			    		jAlert(responseData.message);
    				    }
    			    }
    			});
    		}else{
				removeVehicle("table_track", id);
				//删除完标记后调整地图中心点和缩放级别
				adjustMapView();
    		}
		}else{
			processVehicleStatus(id, state);
		}
	});
}

/** 根据选中的节点，处理返回的车辆实时状态 */
function processVehicleStatus(orgId, checkboxStatus) {
	var targetUrl = "<%=path%>/dealer/monitor/findVehicle.vti?r=" + Math.random();
	targetUrl += "&orgId=" + orgId;
	$.ajax({
		url: targetUrl,
	    type: 'GET',
	    dataType: 'json',
	    timeout: 10000,
	    error: function(){
	        alert('系统异常');
	    },
	    success: function(responseData){
	    	/** 处理返回的车辆及其位置等数据，在地图上显示车辆标记，在列表中显示车辆信息等 */
	        if(responseData.code == 1){
	    		if(responseData.firstItem){
	    	    	var vehicleSize = responseData.firstItem.length;
	    	    	for(var i=0; i<vehicleSize; i++){
	    	    		var vehicle = responseData.firstItem[i];
	    	    		if(checkboxStatus == 1){//选中则添加车辆到列表中
	    					var currentLocation = vehicle.currentLocation;
	    					if(currentLocation){
	    						addVehicle("table_track", vehicle, currentLocation);
	    						//增加完标记后调整地图中心点和缩放级别
	    						adjustMapView();
	    					}else{
	    						jAlert("车辆[" + vehicle.plateNumber + "]没有位置信息，无法显示");
	    					}
	    				}else{//取消选中则从车辆列表中删除
	    					removeVehicle("table_track", vehicle.nodeId);
	    					//删除完标记后调整地图中心点和缩放级别
	    					adjustMapView();
	    				}
	    	    	}
	    	    	changeMapTree(true);
	    	    	/** 设置列表的样式 */
	    	    	setTableListStyle("table_track");
	    		}
	        }else{
	    		jAlert(responseData.message);
	        }
	    }
	});
}

/** 根据选中的车辆，刷新车辆实时状态 */
function refreshVehicleStatus() {
	var targetUrl = "<%=path%>/dealer/monitor/findVehicle.vti?r=" + Math.random();
	var vehicleIds = getVehicleIdStr();
	if(vehicleIds == null || vehicleIds.length == 0){
		return;
	}
	targetUrl += "&vehicleIds=" + vehicleIds;
	$.ajax({
		url: targetUrl,
	    type: 'GET',
	    dataType: 'json',
	    timeout: 10000,
	    error: function(){
	        alert('系统异常');
	    },
	    success: function(responseData){
	    	/** 处理返回的车辆及其位置等数据，在地图上显示车辆标记，在列表中显示车辆信息等 */
	        if(responseData.code == 1){
	    		if(responseData.firstItem){
	    	    	var vehicleSize = responseData.firstItem.length;
	    	    	for(var i=0; i<vehicleSize; i++){
	    	    		var vehicle = responseData.firstItem[i];
	    	    		//清空缓存、地图上的标记、车辆列表
    					removeVehicle("table_track", vehicle.nodeId);
	    	    		//添加缓存、地图上的标记、车辆列表
    					var currentLocation = vehicle.currentLocation;
    					if(currentLocation){
    						addVehicle("table_track", vehicle, currentLocation);
    					}else{
    						jAlert("车辆[" + vehicle.plateNumber + "]没有位置信息，无法显示");
    					}
	    	    	}
	    	    	changeMapTree(true);
	    	    	/** 设置列表的样式 */
	    	    	setTableListStyle("table_track");
	    		}
	        }else{
	    		jAlert(responseData.message);
	        }
	    }
	});
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
			<span>当前位置：车辆信息 > 实时监控</span>
			</c:if>
<!-- 			<div class="audit_nav_c" style="text-align: left;"> -->
<!-- 				<a href="#" id="toggleMenuButton" onclick="toggleTreeMenu()">隐藏菜单</a> -->
<!-- 			</div> -->
		</div>
		<aside class="bd_rt_main clearfix" style="height:505px;">
            <div id="monitorMenu" style="float:right;width:230px; height:505px;overflow:auto;">
                <div id="monitorTree" style="float:left;">
                    <div id="tree_orgAndVehicle">
                    </div>
                </div>                
            </div><!--/#monitorMenu-->
            <a id="monitorMapTree" class="tree_open" onclick="toggleTreeMenuNew();"></a>
			<div id="monitorMap" style="float:right;height:100%;width:100%;">
				<div id="map" style="margin:0px;height:100%;">
				
					<div id="map_container" style="margin:0px;height:100%;"></div>
					<div id="monitorMap_table_icon" class="monitorMap_table_icon_close_middle"><a onclick="btnShowMapTable();"></a></div>
					<div id="monitorMap_table" class="monitorMap_table_close_middle">				
						<div class="table_list">
							<ul class="table_hd clearfix" id="list_title">
								<li class="cnName_240">所属单位</li>
			            		<li class="plateNumber_75">车牌</li>
			            		<li class="carStyleId_Nick_200">车型</li>
			            		<li class="vin_140">VIN</li>
			            		<li class="plateNumber_75">速度(KM/H)</li>
							</ul>
							<div id="table_track">
					
							</div>
						</div><!--/table_list-->
					</div>
				</div>
			</div>
		</aside><!--/bd_rt_main-->
	</div><!--/.bd_rt-->
</section><!--/.bd-->
</body>
</html>
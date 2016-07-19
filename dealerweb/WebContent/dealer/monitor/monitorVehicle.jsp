<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>试驾明细_实时监控</title>
	<%@ include file="/dealer/include/meta.jsp"%>
	<%@ include file="/dealer/include/metaZtree.jsp"%>
	
	<script type="text/javascript"
		src="http://api.map.baidu.com/api?v=2.0&ak=91a73a33c8964682fdba3f89dc96f279"></script>
		
	<script type="text/javascript"
		src = "<%=path%>/js/baidumap/mapCommon.js"></script>
	<script type="text/javascript"
		src = "<%=path%>/js/baidumap/monitorVehicle.js"></script>
</head>

<script type="text/javascript">
    $().ready(function() {
    	mapInit();
        loadZtree();

        //默认隐藏树
        //toggleTreeMenu();
        //默认隐藏列表
        //showLocationList(false);
        changeMapTree(false);
        /** 刷新车辆位置、车速等车辆实时状态 */
        setInterval(refreshVehicleStatus, 6000);
    });

    function showChinaMap() {
        var chinaCenter = new BMap.Point(110, 36);
        var zoomLevel = 5;
        map.centerAndZoom(chinaCenter, zoomLevel);
    }

    function loadZtree() {
        var setting = {
            check: {
                enable: true,
                chkStyle: "checkbox",
                chkboxType: {
                    "Y": "s",
                    "N": "s"
                }
            },
            async: {
                enable: true,
                dataType: 'json',
                url: "<%=path%>/dealer/monitor/queryTree.vti",
                autoParam: ["id", "pId", "name", "attributes"],
                otherParam: {},
            },
            callback: {
                onCheck: onCheck
            }
        };
        
        var treeNode = {
                id: 11,
                pId: 0,
                name: "大众",
                nocheck: true,
                icon: "../../js/ztree/css/zTreeStyle/img/diy/blue.gif",
                attributes: {
                    nocheck: true,
                    leafType: "saleCenter",
                    hidedown: false,
                    city: "",
                },
                isParent: true
            };

            $.fn.zTree.init($("#tree_orgAndVehicle"), setting, treeNode);
        }
		
        function onCheck(e, treeId, treeNode) {
            if (treeNode) {
                var checked = treeNode.checked;
                var nodeId = treeNode.id;
                var level = treeNode.level;
                var vehicle = treeNode.vehicleMonitor;
                if (nodeId != null && level != null) {
                    if (checked) {
                        //选中经销商复选框
                        if (level == 2) {
                        	var childrenAarry = treeNode.children;
                        	if (childrenAarry != null && childrenAarry.length > 0) {
                        		checkTreeDealer(nodeId, vehicle);
                        	}
                        }
                        //选中车辆复选框
                        if (level == 3) {
                        	checkTreeVehicle(nodeId, vehicle);
                        }
                    } else {
                        //取消经销商复选框
                        if (level == 2) {
                        	var childrenAarry = treeNode.children;
                        	if (childrenAarry != null && childrenAarry.length > 0) {
                        		for (var i = 0; i < childrenAarry.length; i++) {
                        			 removeVehicle("table_track", "vehicle" + childrenAarry[i].id);
                        		}
                       		//删除完标记后调整地图中心点和缩放级别
                		    adjustMapView();
                        	}
                        }
                        //取消车辆复选框
                        if (level == 3) {
                            removeVehicle("table_track", "vehicle" + nodeId);
                            //删除完标记后调整地图中心点和缩放级别
                            adjustMapView();
                        }

                    }
                }
            }
        }

        //选中经销商复选框
        function checkTreeDealer(companyId, currentVehicle) {
            var targetUrl = "<%=path%>/dealer/monitor/getVehicleByDealerId.vti?r=" + Math.random();
            targetUrl += "&companyId=" + companyId;
            $.ajax({
                url: targetUrl,
                type: 'GET',
                dataType: 'json',
                timeout: 3000,
                /*error: function() {
                    alert('系统异常');
                },*/
                success: function(responseData) {
                    if (responseData) {
                        if (responseData.code == 1) {
                            if (responseData.data != null) {
                                var vehicleMonitorList = responseData.data.vehicleMonitorList;
                                if (vehicleMonitorList != null && vehicleMonitorList.length > 0) {
                                    for (var i = 0; i < vehicleMonitorList.length; i++) {
                                        var vehicle = vehicleMonitorList[i];
                                        if (vehicle) {
                                            var currentLocation = vehicle.currentLocation;
                                            if (currentLocation) {
                                            	loadVehicleIconAndList(vehicle, currentLocation, 1);
                                            } else {
                                               if (currentVehicle) {
                                            	   //经销商位置
                                            	   var currentLocation = currentVehicle.currentLocation;
                                            	   if (currentLocation) {
                                            		 	//随机添加偏移位置，避免所有车在一个点
                                            		   loadVehicleIconAndList(currentVehicle, currentLocation, 2);
                                            	   }
                                               }
                                            }
                                        } else {
                                            jAlert("车辆[id=" + vehicleId + "]不存在");
                                        }

                                    }
                                }
                            }
                        } else {
                            jAlert(responseData.message);
                        }
                    }

                }
            });
        }

        //选中车辆复选框
        function checkTreeVehicle(vehicleId, currentVehicle) {
            var targetUrl = "<%=path%>/dealer/monitor/getVehicle.vti?r=" + Math.random();
            targetUrl += "&vehicleId=" + vehicleId;
            $.ajax({
                url: targetUrl,
                type: 'GET',
                dataType: 'json',
                timeout: 3000,
                error: function() {
                	if (currentVehicle) {
                  	   //经销商位置
                  	   var currentLocation = currentVehicle.currentLocation;
                  	   if (currentLocation) {
                  			//随机添加偏移位置，避免所有车在一个点
                  	   		loadVehicleIconAndList(currentVehicle, currentLocation, 2);
                  	   }
                     }
                },
                success: function(responseData) {
                    //alert("车辆ID" + vehicleId + "————返回对象" + responseData);
                    if (responseData) {
                        if (responseData.code == 1) {
                            if (responseData.firstItem) {
                                var vehicle = responseData.firstItem;
                                if (vehicle) {
                                    var currentLocation = vehicle.currentLocation;
                                    if (currentLocation) {
                                    	loadVehicleIconAndList(vehicle, currentLocation, 1);
                                    } else {
                                       // jAlert("车辆[" + vehicle.plateNumber + "]没有位置信息，无法显示");
                                        if (currentVehicle) {
                                     	   //经销商位置
                                     	   var currentLocation = currentVehicle.currentLocation;
                                     	   if (currentLocation) {
                                     		  loadVehicleIconAndList(currentVehicle, currentLocation, 2);
                                     	   }
                                        }
                                    }
                                } else {
                                   //jAlert("车辆[id=" + vehicleId + "]不存在");
                                }
                            }
                        } else {
                            jAlert(responseData.message);
                        }
                    }
                }
            });
        }

     //显示车辆及车辆列表
     function loadVehicleIconAndList(vehicle, currentLocation, flag){
     	if (flag == 2) {
     		var latitude = parseFloat(currentLocation.latitude) + Math.random()/3000;
     		var longitude = parseFloat(currentLocation.longitude) + Math.random()/2000;
     		currentLocation = new Object();
     		//随机添加偏移位置，避免所有车在一个点
		 	 	currentLocation.latitude = latitude;
		 		currentLocation.longitude = longitude;
		 		currentLocation.speed = 0;
     	}
     	addVehicle("table_track", vehicle, currentLocation);
     	 //showLocationList(true);
         changeMapTree(true);
         //增加完标记后调整地图中心点和缩放级别
         adjustMapView();

         /** 设置列表的样式 */
         setTableListStyle("table_track");
     }
        
    function toggleTreeMenu() {
        $("#monitorMenu").toggle();
        var oldText = $("#toggleMenuButton").text();
        if ("隐藏菜单" == oldText) {
            $("#toggleMenuButton").text("显示菜单");
            $("#monitorMap").css("width", "100%");
        } else {
            $("#toggleMenuButton").text("隐藏菜单");
            $("#monitorMap").css("width", "80%");
        }
    }

    function showLocationList(isShow) {
        if (isShow) {
            if ($('#btn_hide').text() == '显示') {
                $("#list_title").toggle();
                $('#table_track').toggle();
                $('#btn_hide').text('隐藏');
            }
        } else {
            if ($('#btn_hide').text() == '隐藏') {
                $("#list_title").toggle();
                $('#table_track').toggle();
                $('#btn_hide').text('显示');
            }
        }
    }

    function showMapMenu(isShow) {
        var monitorMapTree = $("#monitorMapTree");
        if (isShow) {
            monitorMapTree.removeClass().addClass("tree_open");
            $("#monitorMap").css("width", "100%");
            $("#monitorMenu").show();
        } else {
            monitorMapTree.removeClass().addClass("tree_close");
            $("#monitorMap").css("width", "100%");
            $("#monitorMenu").hide();
        }
    }

    function toggleTreeMenuNew() {
        var monitorMapTree = $("#monitorMapTree");
        if (null != monitorMapTree) {
            if ("tree_open" == monitorMapTree.attr("class")) {
                showMapMenu(false);
                changeMapTable(true);
            } else if ("tree_close" == monitorMapTree.attr("class")) {
                showMapMenu(true);
                changeMapTable(false);
            } else {
                alert("3出现异常");
            }
        }
    }

    function changeMapTable(isShow) {

        var map_table_icon = $("#monitorMap_table_icon");
        var map_table = $("#monitorMap_table");
        if (isShow) {
            if (null != map_table_icon && null != map_table) {
                if ("monitorMap_table_icon_close_middle" == map_table_icon.attr("class") && "monitorMap_table_close_middle" == map_table.attr("class")) {
                    map_table_icon.removeClass().addClass("monitorMap_table_icon_close_left");
                    map_table.removeClass().addClass("monitorMap_table_close_left");
                } else if ("monitorMap_table_icon_open_middle" == map_table_icon.attr("class") && "monitorMap_table_open_middle" == map_table.attr("class")) {
                    map_table_icon.removeClass().addClass("monitorMap_table_icon_open_left");
                    map_table.removeClass().addClass("monitorMap_table_open_left");
                } else if ("monitorMap_table_icon_close_left" == map_table_icon.attr("class") && "monitorMap_table_close_left" == map_table.attr("class")) {
                    map_table_icon.removeClass().addClass("monitorMap_table_icon_close_left");
                    map_table.removeClass().addClass("monitorMap_table_close_left");
                } else if ("monitorMap_table_icon_open_left" == map_table_icon.attr("class") && "monitorMap_table_open_left" == map_table.attr("class")) {
                    map_table_icon.removeClass().addClass("monitorMap_table_icon_open_left");
                    map_table.removeClass().addClass("monitorMap_table_open_left");
                }
            }
        } else {
            if (null != map_table_icon && null != map_table) {
                if ("monitorMap_table_icon_close_middle" == map_table_icon.attr("class") && "monitorMap_table_close_middle" == map_table.attr("class")) {
                    map_table_icon.removeClass().addClass("monitorMap_table_icon_close_middle");
                    map_table.removeClass().addClass("monitorMap_table_close_middle");
                } else if ("monitorMap_table_icon_open_middle" == map_table_icon.attr("class") && "monitorMap_table_open_middle" == map_table.attr("class")) {
                    map_table_icon.removeClass().addClass("monitorMap_table_icon_open_middle");
                    map_table.removeClass().addClass("monitorMap_table_open_middle");
                } else if ("monitorMap_table_icon_close_left" == map_table_icon.attr("class") && "monitorMap_table_close_left" == map_table.attr("class")) {
                    map_table_icon.removeClass().addClass("monitorMap_table_icon_close_middle");
                    map_table.removeClass().addClass("monitorMap_table_close_middle");
                } else if ("monitorMap_table_icon_open_left" == map_table_icon.attr("class") && "monitorMap_table_open_left" == map_table.attr("class")) {
                    map_table_icon.removeClass().addClass("monitorMap_table_icon_open_middle");
                    map_table.removeClass().addClass("monitorMap_table_open_middle");
                }
            }
        }
    }

    function changeMapTree(isShow) {
        var monitorMapTree = $("#monitorMapTree");
        var map_table_icon = $("#monitorMap_table_icon");
        var map_table = $("#monitorMap_table");
        if (isShow) {
            if (null != monitorMapTree) {
                if ("tree_open" == monitorMapTree.attr("class")) {
                    map_table_icon.removeClass().addClass("monitorMap_table_icon_open_middle");
                    map_table.removeClass().addClass("monitorMap_table_open_middle");
                    //$('#table_track').toggle();
                } else if ("tree_close" == monitorMapTree.attr("class")) {
                    map_table_icon.removeClass().addClass("monitorMap_table_icon_open_left");
                    map_table.removeClass().addClass("monitorMap_table_open_left");
                    //$('#table_track').toggle();
                }
            }
        } else if (!isShow) {
            if (null != monitorMapTree) {
                if ("tree_open" == monitorMapTree.attr("class")) {
                    map_table_icon.removeClass().addClass("monitorMap_table_icon_close_middle");
                    map_table.removeClass().addClass("monitorMap_table_close_middle");
                    //$('#table_track').toggle();
                } else if ("tree_close" == monitorMapTree.attr("class")) {
                    map_table_icon.removeClass().addClass("monitorMap_table_icon_close_left");
                    map_table.removeClass().addClass("monitorMap_table_close_left");
                    //$('#table_track').toggle();
                }
            }
        }
    }

    function btnShowMapTable() {
        var map_table_icon = $("#monitorMap_table_icon");
        var map_table = $("#monitorMap_table");
        if (null != map_table_icon && null != map_table) {
            if ("monitorMap_table_icon_close_middle" == map_table_icon.attr("class") && "monitorMap_table_close_middle" == map_table.attr("class")) {
                changeMapTree(true);
            } else if ("monitorMap_table_icon_open_middle" == map_table_icon.attr("class") && "monitorMap_table_open_middle" == map_table.attr("class")) {
                changeMapTree(false);
            } else if ("monitorMap_table_icon_close_left" == map_table_icon.attr("class") && "monitorMap_table_close_left" == map_table.attr("class")) {
                changeMapTree(true);
            } else if ("monitorMap_table_icon_open_left" == map_table_icon.attr("class") && "monitorMap_table_open_left" == map_table.attr("class")) {
                changeMapTree(false);
            } else {
                alert("map_table_icon:" + map_table_icon.attr("class") + "-----monitorMap_table_updown" + map_table.attr("class"));
                alert("1出现异常");
            }
        }
    }

    /** 根据选中的节点，处理返回的车辆实时状态 */
    function processVehicleStatus(orgId, checkboxStatus) {
        var targetUrl = "<%=path%>/dealer/monitor/findVehicle.vti?r=" + Math.random();
        targetUrl += "&orgId=" + orgId;
        $.ajax({
            url: targetUrl,
            type: 'GET',
            dataType: 'json',
            async: true,
            timeout: 3000,
            error: function() {
                alert('系统异常');
            },
            success: function(responseData) {
                /** 处理返回的车辆及其位置等数据，在地图上显示车辆标记，在列表中显示车辆信息等 */
                if (responseData.code == 1) {
                    if (responseData.firstItem) {
                        var vehicleSize = responseData.firstItem.length;
                        for (var i = 0; i < vehicleSize; i++) {
                            var vehicle = responseData.firstItem[i];
                            if (checkboxStatus == 1) { //选中则添加车辆到列表中
                                var currentLocation = vehicle.currentLocation;
                                if (currentLocation) {
                                    addVehicle("table_track", vehicle, currentLocation);
                                    //增加完标记后调整地图中心点和缩放级别
                                    adjustMapView();
                                } else {
                                    jAlert("车辆[" + vehicle.plateNumber + "]没有位置信息，无法显示");
                                }
                            } else { //取消选中则从车辆列表中删除
                                removeVehicle("table_track", vehicle.nodeId);
                                //删除完标记后调整地图中心点和缩放级别
                                adjustMapView();
                            }
                        }
                        changeMapTree(true);
                        /** 设置列表的样式 */
                        setTableListStyle("table_track");
                    }
                } else {
                    jAlert(responseData.message);
                }
            }
        });
    }

    /** 根据选中的车辆，刷新车辆实时状态 */
    function refreshVehicleStatus() {
        var targetUrl = "<%=path%>/dealer/monitor/findVehicle.vti?r=" + Math.random();
        var vehicleIds = getVehicleIdStr();
        if (vehicleIds == null || vehicleIds.length == 0) {
            return;
        }
        targetUrl += "&vehicleIds=" + vehicleIds;
        $.ajax({
            url: targetUrl,
            type: 'GET',
            dataType: 'json',
            timeout: 3000,
            /*error: function() {
                alert('系统异常');
            },*/
            success: function(responseData) {
                /** 处理返回的车辆及其位置等数据，在地图上显示车辆标记，在列表中显示车辆信息等 */
                if (responseData.code == 1) {
                    if (responseData.firstItem) {
                        var vehicleSize = responseData.firstItem.length;
                        if (vehicleSize > 0) {
                        	for (var i = 0; i < vehicleSize; i++) {
                                var vehicle = responseData.firstItem[i];
                                if (vehicle) {
	                                //添加缓存、地图上的标记、车辆列表
	                                var currentLocation = vehicle.currentLocation;
	                                if (currentLocation) {
		                                //清空缓存、地图上的标记、车辆列表
		                                removeVehicle("table_track", vehicle.nodeId);
	                                    addVehicle("table_track", vehicle, currentLocation);
	                                } else {
	                                    //jAlert("车辆[" + vehicle.plateNumber + "]没有位置信息，无法显示");
	                                }
                                }
                            }
                            changeMapTree(true);
                            /** 设置列表的样式 */
                            setTableListStyle("table_track");
                        }
                    }
                } else {
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
			<span>当前位置：车辆管理 > 实时监控</span>
			</c:if>
		</div>
		<aside class="bd_rt_main clearfix" style="height:505px;">
            <div id="monitorMenu" style="float:right;width:230px; height:505px;overflow:auto;">
                <div id="monitorTree" style="float:left;">
				<!-- <div id="tree_orgAndVehicle"> -->
					<ul id="tree_orgAndVehicle" class="ztree"></ul>
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
						</div>
					</div>
				</div>
			</div>
		</aside>
	</div>
</section>
</body>
</html>
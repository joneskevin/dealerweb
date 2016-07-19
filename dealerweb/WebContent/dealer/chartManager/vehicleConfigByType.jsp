<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.base.dao.hibernate.TransMsg" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script type="text/javascript">
    // 路径配置
    require.config({
        paths: {
            'echarts': '/js/echarts/echarts',
            'echarts/chart/bar': '/js/echarts/echarts'
        }
    });

    var vehicleConfigChart;
    //竖向柱状图                   
    var vehicleConfigOption = {
        grid: {
            //y0:100
            height: 220,
            width: 900,
            x: 60,
            y: 90
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            y: 30,
            textStyle: {
                fontSize: '12',
                fontFamily: '黑体',
                fontStyle: 'normal',
                fontWeight: 'normal'
            },
            data: ['要求配置', '实际安装', '待安装']
                //data:['要求配置','可选配置','实际安装','待安装']
        },
        toolbox: {
            show: true,
            orient: 'horizontal',
            x: 'left',
            y: 30,
            padding: [5, 5, 5, 20],
            feature: {
                mark: {
                    show: false
                },
                dataView: {
                    show: false,
                    readOnly: false
                },
                magicType: {
                    show: true,
                    type: ['line', 'bar']
                }, //type: ['line', 'bar', 'stack', 'tiled']
                restore: {
                    show: false
                }, //编辑数据后通过该功能还原原数据
                saveAsImage: {
                    show: true
                }
            }
        },
        calculable: false,
        xAxis: [{
            name: '车型',
            type: 'category',
            data: null,
            nameTextStyle: {
                fontSize: '12',
                fontFamily: '黑体',
                fontStyle: 'normal',
                fontWeight: 'normal',
                color: '#333'
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                interval: 0,
                rotate: 45,
                textStyle: {
                    fontSize: '12',
                    fontFamily: '黑体',
                    fontStyle: 'normal',
                    fontWeight: 'normal',
                    color: '#333'
                }
            }
        }],
        yAxis: [{
            name: '辆数',
            type: 'value',
            nameTextStyle: {
                fontSize: '12',
                fontFamily: '黑体',
                fontStyle: 'normal',
                fontWeight: 'normal',
                color: '#333'
            },
            axisTick: {
                //interval:5,
                show: false,
                length: 5
            }
        }],
        series: null
    };
    require(
        [
            'echarts',
            'echarts/chart/bar'
        ],
        function(ec) {
            vehicleConfigChart = ec.init(document.getElementById('curve_new'));

            vehicleConfigChart.showLoading({
                text: '正在努力的读取数据中...'
            });
            loadData(vehicleConfigChart, vehicleConfigOption);
            vehicleConfigChart.hideLoading();
        }
    );
    //ajax获取后台数据，x轴的name从数据库中读取  
    function loadData(vehicleConfigChart, vehicleConfigOption) { //柱状图初始化的值
        var regionId = $("#regionId").val();
        $.ajax({
            url: "<%=path%>/dealer/reportManager/byVehicleType.vti",
            type: "POST",
            dataType: "json",
            data: {
                regionId: regionId
            },
            success: function(data) {
                setData(data);
            }
        });
    }

    function queryRegionData() { //柱状图初始化的值 
        var regionId = $("#regionId").val();
        vehicleConfigChart.clear();
        $.ajax({
            url: "<%=path%>/dealer/reportManager/byVehicleType.vti",
            type: "POST",
            dataType: "json",
            data: {
                regionId: regionId
            },
            success: function(data) {
                setData(data);
                vehicleConfigChart.refresh();
            }
        });
    }

    function setData(data) {
        if (null == data || null == data.data) {
            vehicleConfigOption.xAxis[0].data = null;
            vehicleConfigOption.series = null;
            vehicleConfigChart.setOption(vehicleConfigOption);
            alert("没有数据可加载");
        } else {
            if (null == data.data.vehicleTpyeNameData) {
                vehicleConfigOption.xAxis[0].data = null;
            } else {
                vehicleConfigOption.xAxis[0].data = data.data.vehicleTpyeNameData;
            }
            if (null == data.data.vehicleConfigData) {
                vehicleConfigOption.series = null;
            } else {
                vehicleConfigOption.series = data.data.vehicleConfigData;
            }
            vehicleConfigChart.setOption(vehicleConfigOption);
        }
    }
</script>
<body style="min-height:500px">

<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
		<c:if test="${menuType == 1}" >
			<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
			<div ><span>当前位置：图表管理 > 车辆配置统计(分车型)</span></div>
		</c:if>
	</div>
	<p class="audit_nav_c">
		<select id="regionId" name="regionId" onchange="queryRegionData()" style="border:1px solid #666;">
			<c:forEach items="${selectRegionOptionList}" var="selectOptionBean" varStatus="num">
				<option value="${selectOptionBean.optionValue}">${selectOptionBean.optionText}</option>
			</c:forEach>
		</select>
	</p>
	<aside class="bd_rt_main clearfix">
		<div class="rate_curve_new">
			<div id="curve_new"></div>
		</div>
	</aside>
	<div class="bd_rt_ft">
	</div>
</div>
</body>
</html>
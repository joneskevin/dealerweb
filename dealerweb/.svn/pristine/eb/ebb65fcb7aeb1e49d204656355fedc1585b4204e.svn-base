/***
 * flot图表专用的提示框
 * @param x
 * @param y
 * @param contents
 * @return
 */ 
function showTooltip(x, y, contents) {
    $('<div id="tooltip">' + contents + '</div>').css( {
        position: 'absolute',
        display: 'none',
        top: y + 5,
        left: x + 5,
        border: '1px solid #fdd',
        padding: '2px',
        'background-color': '#ffff99',
        opacity: 0.80
    }).appendTo("body").fadeIn(200);
}

function bindHover(placeholder){
	var previousPoint = null;
	placeholder.bind("plothover", function (event, pos, item) {
	    if (item) {
	        if (previousPoint != item.dataIndex) {
	            previousPoint = item.dataIndex;
	
	            $("#tooltip").remove();
	            var x = item.datapoint[0].toFixed(2),
	                y = item.datapoint[1].toFixed(2);
	
	            var hoverDate = toNormalDateFromMillis(x);
	            hoverDate = hoverDate.substring(5);
	            showTooltip(item.pageX, item.pageY,
	            	"[" + hoverDate + "]&nbsp;" + y);
	        }
	    }
	    else {
	        $("#tooltip").remove();
	        previousPoint = null;            
	    }
	});
}

function createDataArray(dailyStats, dailyScores, propFlag){
	if(dailyStats != null && dailyStats.length > 0){
		dailyStats = dailyStats.sort(
			function(a, b){
				if(a.statDate > b.statDate) return -1;
				if(a.statDate < b.statDate) return 1;
				return 0;
			}
	    );
	}
	if(dailyScores != null && dailyScores.length > 0){
		dailyScores = dailyScores.sort(
			function(a, b){
				if(a.statDate > b.statDate) return -1;
				if(a.statDate < b.statDate) return 1;
				return 0;
			}
	    );
	}
	
	var dataArray = [];
    var d1 = [];
    var d2 = [];

	if("totalFuelEconomy" == propFlag){
		for (var i=0; i<dailyStats.length; i++) {
			var dailyStat = dailyStats[i];
			var statDateStr = toMillisFromShort(dailyStat.statDate);
			if(statDateStr){
				d1.push([statDateStr, dailyStat.totalFuelEconomy]);
				d2.push([statDateStr, dailyStat.mileage]);
			}
		}
	}else if("averageFuelEconomy" == propFlag){
		for (var i=0; i<dailyStats.length; i++) {
			var dailyStat = dailyStats[i];
			var statDateStr = toMillisFromShort(dailyStat.statDate);
			if(statDateStr){
				d1.push([statDateStr, dailyStat.averageFuelEconomy]);
				d2.push([statDateStr, dailyStat.averageSpeed]);
			}
		}
	}else if("drivingScore" == propFlag){
		for (var i=0; i<dailyScores.length; i++) {
			var dailyScore = dailyScores[i];
			var statDateStr = toMillisFromShort(dailyScore.statDate);
			if(statDateStr){
				d1.push([statDateStr, dailyScore.drivingHabitsScore]);
				d2.push([statDateStr, dailyScore.fuelEconomyScore]);
			}
		}
	}
	
	dataArray.push(d1);
	dataArray.push(d2);
	return dataArray;
}

function createPlot(placeholder, plotSetting){
	var dailyStatOfMonthVO = plotSetting.data;
	var propFlag = plotSetting.propFlag;
	var label1 = plotSetting.label1;
	var label2 = plotSetting.label2;
	var fn1 = plotSetting.fn1;
	var fn2 = plotSetting.fn2;

	var dailyStats = dailyStatOfMonthVO.dailyStats;
	var dailyScores = dailyStatOfMonthVO.dailyScores;
	var dArray = createDataArray(dailyStats, dailyScores, propFlag);
    var d1 = dArray[0];
    var d2 = dArray[1];
    var max1 = plotSetting.max1;
    var max2 = plotSetting.max2;
	
	var plot = $.plot(placeholder, [
			{
		        data: d1,
	   	        color: "#990000",
	            lines: { show: true },
	            points: { show: true },
	            label: label1,
	            yaxis: 1
	        }, 
	    	{
	   	        data: d2,
	   	        color: "#0066cc",
	            lines: { show: true },
	            points: { show: true },
	            label: label2,
	            yaxis: 2
	        }
	    ], 
	    {
	    //crosshair: { mode: "x" },
	    xaxis: {
	        mode: "time",
	        timeformat: "%m月%d"
	    },
	    yaxes: [ 
	        { 
	            min: 0,
	            max:max1,
	            alignTicksWithAxis: 1,
	            position: "left",
	            tickFormatter: fn1
	        },
	        {
	            min: 0,
	            max:max2,
				alignTicksWithAxis: 2,
				position: "right",
				tickFormatter: fn2
	        }
	    ],
	    legend: { position: 'ne' },
	    grid: {
	        backgroundColor: { colors: ["#fff", "#fff"] },
	        hoverable: true
	    }
	});

    bindHover(placeholder);
}

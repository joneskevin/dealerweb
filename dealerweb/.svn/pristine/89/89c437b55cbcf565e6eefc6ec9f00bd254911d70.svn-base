//datatables的功能扩展函数
function dataTablesExt(){
	$.extend( $.fn.dataTable.defaults, {
		"iDisplayLength": 20,
		"bFilter": false,
		"bStateSave": true,
		"bProcessing": true,
		"bAutoWidth": false,
		"sPaginationType": "two_button",
		"bServerSide": true,
		"sDom":'<"top"flip>rt<"bottom"><"clear">',
		"fnServerParams": function ( aoData ) {
			$.merge(aoData, pushServerParams(this.fnSettings()));
		},
		"fnFormatNumber": function ( data ) {
			//原生的方法会造成格式化错误(比如：12809-->12undefined809)，故这里源数据返回
			return data;
		},
        "fnDrawCallback": function () {
			//当页面上有datatables列表时,设置列表的操作栏的显示效果
			toggleOperationColumn();
			//根据gps位置解析地址
			latLng2address4datatables();
        },
		"oLanguage": { 
			"sUrl": "/js/jquery/datatables/language/search_cn.txt"
		},  
		"bJQueryUI": false
	});
	//当页面上没有datatables列表时,设置列表的操作栏的显示效果
	toggleOperationColumn();
}

function pushServerParams(oSettings){
	var dataArr = new Array();
	
	var pageSize = oSettings._iDisplayLength;
	dataArr.push( { "name": "transMsg.pageSize", "value":""+ pageSize + ""} ); 
	var startIndex = oSettings._iDisplayStart; 
	//alert(startIndex);//有时候，由于缓存会操作第一次进入列表页面时，开始索引大于0，比如：20；
	dataArr.push( { "name": "transMsg.startIndex", "value":""+ startIndex + ""} ); 
	
	$("input[id ^= 's_'],select[id ^= 's_']").each(function(i){
		var value = "";
		if( $(this).attr('type') == "checkbox"){
			if($(this).attr('checked') == 'checked'){
				value = "true";
			}else{
				value = "false";
			}
		}else{
			value = $(this).attr('value');
		}
		dataArr.push( { "name": "" + $(this).attr('name') + "", "value": "" + value + "" } );  
	});

	return dataArr;
}

function toggleOperationColumn(){
	$('.operationColumn').each(function(){
		var aOperationColumn = $(this);
		if(aOperationColumn){
			var classValue = aOperationColumn.attr("class");
//			aOperationColumn.css("width", "200px");
			var tableObj = aOperationColumn.parent().parent().parent();
			tableObj.find("tr").each(function(){
				var trObj = $(this);
				var lastTdObj = trObj.children().last();
				if(lastTdObj.is('td')){
					//如果是数据行					
					lastTdObj.children().each(function (){
						$(this).hide();
					});
					trObj.bind("mouseover", function (){						
						$(this).children().each(function (){
							$(this).css("background", "#f5f5f5");
						});
						$(this).children().last().children().each(function (){
							$(this).show();
						});
					});
					trObj.bind("mouseout", function (){
						$(this).children().each(function (){
							$(this).css("background", "#ffffff");
						});
						$(this).children().last().children().each(function (){
							$(this).hide();
						});
					});
				}
			});
		}
	});
}

function latLng2address4datatables(){
	$(".latLng2addressLabel").each(function(){
		var lat = $(this).attr("lat");
		var lng = $(this).attr("lng");
		var labelId = $(this).attr("id");
		latLng2address(labelId, lat, lng);
	});
}
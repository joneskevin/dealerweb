function Validator(){	
	this.trim = function (validatedStr){
		if(validatedStr==null || validatedStr.length == 0){
			return validatedStr;
		}
		while(validatedStr.charAt(0) == " "||validatedStr.charAt(0) == " "){
			validatedStr = validatedStr.substr(1);
		}
		while(validatedStr.charAt(validatedStr.length-1) == " "||validatedStr.charAt(validatedStr.length-1) == " "){
			validatedStr = validatedStr.substr(0,validatedStr.length-1);
		}
		return validatedStr;
	}

	this.isNull = function (validatedStr) {
		validatedStr = this.trim(validatedStr);
		if(validatedStr==null || validatedStr.length == 0){
			return true;
		}
		return false;
	};
/* -------------------------------check form input start-------------------------------------------------*/
	this.isNotNull = function (validatedStr) {
		validatedStr = this.trim(validatedStr);
		if(validatedStr==null || validatedStr.length == 0){
			//alert('不能为空！');
			return false;
		}
		return true;
	};

	this.isAlpha = function (validatedStr) {
		if(this.isNull(validatedStr)){
			//alert('不能为空！');
			return false;
		}
		for(var i=0;i<validatedStr.length;i++){
			if((validatedStr.charAt(i) < 'a' || validatedStr.charAt(i) > 'z\uffff')
			&& (validatedStr.charAt(i) < 'A' || validatedStr.charAt(i) > 'Z\uffff')){
				//alert('应该为字母！');
				//validatedObj.value = '';
				return false;
			}
		}
		return true;
	};

	this.isNumber = function (validatedStr) {
		if(this.isNull(validatedStr)){
			//alert('不能为空！');
			return false;
		}
		for(var i=0;i<validatedStr.length;i++){
			if(validatedStr.charAt(i) < '0' || validatedStr.charAt(i) > '9'){
				//alert('应该为数字！');
				//validatedObj.value = '';
				return false;
			}
		}
		return true;
	};

	this.isString = function (validatedStr) {
		if(this.isNull(validatedStr)){
			//alert('不能为空！');
			return false;
		}
		for(var i=0;i<validatedStr.length;i++){
			if((validatedStr.charAt(i) < '0' || validatedStr.charAt(i) > '9')
			 &&(validatedStr.charAt(i) < 'a' || validatedStr.charAt(i) > 'z\uffff')
			 &&(validatedStr.charAt(i) < 'A' || validatedStr.charAt(i) > 'Z\uffff')){
				//alert('应该为字符串！');
				//validatedObj.value = '';
				return false;
			}
		}
		return true;
	};

	this.isEmail = function (validatedStr) {
		if(this.isNull(validatedStr)){
			//alert('不能为空！');
			return false;
		} 
		var length=validatedStr.length; 
		var atIndex = validatedStr.indexOf('@'); 
		var dotIndex = validatedStr.indexOf('.'); 
		if (
		validatedStr.charAt(0)=="." 
		||validatedStr.charAt(0)=="@"
		||validatedStr.lastIndexOf("@")==length-1 
		||validatedStr.lastIndexOf(".")==length-1
		||atIndex == -1 
		||dotIndex == -1 ){
			//alert('格式不正确！');
			//validatedObj.value = '';
			return false;
		}
		if (atIndex > 1) { 
			if ((length-atIndex) > 3){ 
				if ((length-dotIndex)>1){
					//alert('格式不正确！');
					//validatedObj.value = '';
					return true; 
				} 
			} 
		} 
		return false; 
	}

	this.isPhone = function(validatedStr) {
		if(this.isNull(validatedStr)){
			//alert('不能为空！');
			return false;
		}	
		var strTemp ="0123456789-()"; 
		for (var i=0;i<validatedStr.length;i++){ 
			if (strTemp.indexOf(validatedStr.charAt(i))==-1){ 
				//alert('格式不正确！');
				//validatedObj.value = '';
				return false; 
			} 
		} 
		return true; 
	}
	
	this.isMobile = function(validatedStr) {
		if(this.isNull(validatedStr)){
			//alert('不能为空！');
			return false;
		}
		if(!this.isNumber(validatedStr) || validatedStr.length != 11){
			//alert('格式不正确1！');
			//validatedObj.value = '';
			return false;
		}
		if(validatedStr.charAt(0) !="1" || (validatedStr.charAt(1)!="3" && validatedStr.charAt(1)!="5" && validatedStr.charAt(1)!="8")){
			//alert('格式不正确2！');
			//validatedObj.value = '';
			return false;
		}
		return true;
	}
}

function changeToBigAmount(num){  //转成人民币大写金额形式
  var str1 = '零壹贰叁肆伍陆柒捌玖';  //0-9所对应的汉字
  var str2 = '万仟佰拾亿仟佰拾万仟佰拾元角分'; //数字位所对应的汉字
  var str3;    //从原num值中取出的值
  var str4;    //数字的字符串形式
  var str5 = '';  //人民币大写金额形式
  var i;    //循环变量
  var j;    //num的值乘以100的字符串长度
  var ch1;    //数字的汉语读法
  var ch2;    //数字位的汉字读法
  var nzero = 0;  //用来计算连续的零值是几个
  
  num = Math.abs(num).toFixed(2);  //将num取绝对值并四舍五入取2位小数
  str4 = (num * 100).toFixed(0).toString();  //将num乘100并转换成字符串形式
  j = str4.length;      //找出最高位
  if (j > 15){return '溢出';}
  str2 = str2.substr(15-j);    //取出对应位数的str2的值。如：200.55,j为5所以str2=佰拾元角分
  
  //循环取出每一位需要转换的值
  for(i=0;i<j;i++){
    str3 = str4.substr(i,1);   //取出需转换的某一位的值
    if (i != (j-3) && i != (j-7) && i != (j-11) && i != (j-15)){    //当所取位数不为元、万、亿、万亿上的数字时
   if (str3 == '0'){
     ch1 = '';
     ch2 = '';
  nzero = nzero + 1;
   }
   else{
     if(str3 != '0' && nzero != 0){
       ch1 = '零' + str1.substr(str3*1,1);
          ch2 = str2.substr(i,1);
          nzero = 0;
  }
  else{
    ch1 = str1.substr(str3*1,1);
          ch2 = str2.substr(i,1);
          nzero = 0;
        }
   }
 }
 else{ //该位是万亿，亿，万，元位等关键位
      if (str3 != '0' && nzero != 0){
        ch1 = "零" + str1.substr(str3*1,1);
        ch2 = str2.substr(i,1);
        nzero = 0;
      }
      else{
     if (str3 != '0' && nzero == 0){
          ch1 = str1.substr(str3*1,1);
          ch2 = str2.substr(i,1);
          nzero = 0;
  }
        else{
    if (str3 == '0' && nzero >= 3){
            ch1 = '';
            ch2 = '';
            nzero = nzero + 1;
       }
       else{
      if (j >= 11){
              ch1 = '';
              nzero = nzero + 1;
   }
   else{
     ch1 = '';
     ch2 = str2.substr(i,1);
     nzero = nzero + 1;
   }
          }
  }
   }
 }
    if (i == (j-11) || i == (j-3)){  //如果该位是亿位或元位，则必须写上
        ch2 = str2.substr(i,1);
    }
    str5 = str5 + ch1 + ch2;
    
    if (i == j-1 && str3 == '0' ){   //最后一位（分）为0时，加上“整”
      str5 = str5 + '整';
    }
  }
  if (num == 0){
    str5 = '零元整';
  }
  return str5;
}

//数组转成分隔符分隔的字符串
function array2Str(arr, seperator){
	var returnStr = "";
	seperator = seperator ? seperator : ",";
	if(arr && arr.length > 0){
		for(var i=0; i<arr.length; i++){
			var element = arr[i];
			if(element){
				returnStr += element;
				if(arr.length != 1 && i < (arr.length-1)){
					returnStr += ",";
				}
			}
		}
	}
	return returnStr;
}

/** ========================================= 日期操作函数－开始 ========================================= */
/**
 * 判断输入框中输入的日期格式为yyyy-MM-dd
 */
String.prototype.isDate = function(){
   var r = this.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
   if(r==null)
		return false; 
   var d = new Date(r[1], r[3]-1, r[4]);
   return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
}

/**
 * 判断输入框中输入的日期时间格式为yyyy-MMs-dd hh:mm:ss
 */
String.prototype.isTime = function(){
  var r = this.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/);
  if(r==null)
		return false; 
  var d = new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]);
  return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]);
}

Date.prototype.format = function(format){
	var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute<br>
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter
		"S" : this.getMilliseconds() //millisecond
	}
	if(/(y+)/.test(format)) 
		format = format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
	for(var k in o)
		if(new RegExp("("+ k +")").test(format))
			format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] :("00"+ o[k]).substr((""+ o[k]).length));
	return format;
}

/** 得到两个时间对象的天数差，其中date2>date1，即date2是比date1更晚的时间；如果date2为空，则代表和当前时间比较 */
function intervalDay(date1, date2) {
   	var intervalMinutes = intervalMinute(date1, date2);
   	var intervalDay = intervalMinutes/(60*24);
   	return intervalDay;
}

/** 得到两个时间对象的分钟差，其中date2>date1，即date2是比date1更晚的时间；如果date2为空，则代表和当前时间比较 */
function intervalMinute(date1, date2) {
    if (typeof date1 == 'string'){
    	date1 = str2date(date1);
    }
	if (date2){
		if (typeof date2 == 'string'){
	    	date2 = str2date(date2);
	    }
	}else{
		date2 = new Date();
	}
	var minute = 1000*60;
   	var date1Time = date1.getTime();
   	var date2Time = date2.getTime();
   	var intervalMinute = (date2Time - date1Time)/minute;
   	return intervalMinute;
}
/**
 * 时间格式转换
 * @param dateTime格式：
 * 1、date类型；
 * 2、number类型，比如20130604或20130604144016；
 * 3、string类型，short格式，比如：20130604或20130604144016;
 * 4、string类型，normal格式，比如：2013-06-04或2013-06-04 14:40:16
 * @return 格式 返回date对象距世界标准时间(UTC)1970年1月1日午夜之间的毫秒数(时间戳)
**/
function toMillisDateTime(dateTime) {
	var dateObj = toDateObjFromNormal(dateTime);
	if(dateObj){
		return dateObj.getTime();
	}
	var normalDateTimeStr = toNormalDateTime(dateTime);
	var dateObj2 = toDateObjFromNormal(normalDateTimeStr);
	var millis;
	if(dateObj2){
		millis = dateObj2.getTime();
	}
	return millis;
}
/**
 * 时间格式转换
 * @param dateTime格式：1、date类型；2、number类型，比如20130604或20130604144016；3、string类型，比如：20130604或20130604144016
 * @return 格式 2013-06-04
**/
function toNormalDate(dateTime) {
	var dateTimeStr = toNormalDateTime(dateTime);
	if (dateTimeStr != '' && dateTimeStr.length >= 14) {
		return dateTimeStr.substring(0, 10);
	}
	
	return "";
}
/**
 * 时间格式转换
 * @param dateTime格式：1、date类型；2、number类型，比如20130604或20130604144016；3、string类型，比如：20130604或20130604144016
 * @return 格式 2013-06-04 14:40:16
**/
function toNormalDateTime(dateTime) {
	if(!dateTime){
		alert("toNormalDate：输入参数不合法");
	}
	
	if(typeof(dateTime) == "object" && dateTime instanceof Date){
		//js的Date对象
		return dateTime.format("yyyy-MM-dd hh:mm:ss");
	}else if(typeof(dateTime) == "object" && dateTime.time){
		//json对象
		var millis = dateTime.time;
		var normalDateTime = toNormalDateTimeFromMillis(millis);
		return normalDateTime;
	}else if(typeof(dateTime) == "object" && dateTime.toString().length == 13){
		//js的Number对象，毫秒格式
		var normalDateTime = toNormalDateTimeFromMillis(dateTime);
		return normalDateTime;
	}else if(typeof(dateTime) == "number"){
		//基本number类型，不是Number对象
		var timeStr = dateTime.toString();
		if (timeStr.length >= 8) {
			if(timeStr.length == 8){
				timeStr += "000000";
			}
			var dateTimeStr = "";
			if(timeStr.length >= 14){
				var year = timeStr.substr(0, 4);
				var month = timeStr.substr(4, 2);
				var day = timeStr.substr(6, 2);
				var time = timeStr.substr(8, 6);
				var hour = time.substr(0, 2);
				var minute = time.substr(2, 2);
				var second = time.substr(4, 2);
				dateTimeStr = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
			}else if(timeStr.length == 13){
				dateTimeStr = toNormalDateTimeFromMillis(dateTime);
			}
			
			return dateTimeStr;
		}
	}else if(typeof(dateTime) == "string"){
		if(dateTime.isDate() || dateTime.isTime()){
			return dateTime;
		}
		var timeStr = dateTime.toString();
		if (timeStr.length >= 8) {
			if(timeStr.length == 8){
				timeStr += "000000";
			}
			var dateTimeStr = "";
			if(timeStr.length >= 14){
				var year = timeStr.substr(0, 4);
				var month = timeStr.substr(4, 2);
				var day = timeStr.substr(6, 2);
				var time = timeStr.substr(8, 6);
				var hour = time.substr(0, 2);
				var minute = time.substr(2, 2);
				var second = time.substr(4, 2);
				dateTimeStr = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
			}
			
			return dateTimeStr;
		}
	}else{
		alert("toNormalDate：输入参数不合法");
	}
	
	return "";
}
/**
 * 时间格式转换
 * @param dateTime格式：string类型，比如：2013-06-04或2013-06-04 14:40:16
 * @return 格式:Date对象
**/
function toDateObjFromNormal(dateTimeStr) {
	var returnDate;
	if (dateTimeStr.length >= 10 && dateTimeStr.length < 14) {
		var year = dateTimeStr.substring(0, 4);
		var month = dateTimeStr.substring(5, 7);
		var day = dateTimeStr.substring(8, 10);
		returnDate = new Date(year, parseInt(month) - 1, day);
	}else if (dateTimeStr.length >= 14) {
		var year = dateTimeStr.substring(0, 4);
		var month = dateTimeStr.substring(5, 7);
		var day = dateTimeStr.substring(8, 10);
		var time = dateTimeStr.substring(11, 20);
		var hour = time.substring(0, 2);
		var minute = time.substring(3, 5);
		var second = time.substring(6, 8);
		returnDate = new Date(year, parseInt(month) - 1, day, hour, minute, second);
	}
	if(returnDate){
	}else{
		return null;
	}
   	return returnDate;
}
/**
 * 时间格式转换
 * @param 输入参数格式：时间的毫秒格式，比如：1370328016494
 * @return 格式：2013-06-04
**/
function toNormalDateFromMillis(millis) {
	var d = new Date();
	d.setTime(millis);
	var dateStr = d.format("yyyy-MM-dd");
	return dateStr;
}
/**
 * 时间格式转换
 * @param 输入参数格式：时间的毫秒格式，比如：1370328016494
 * @return 格式：2013-06-04 14:40:16
**/
function toNormalDateTimeFromMillis(millis) {
	var d = new Date();
	d.setTime(millis);
	var dateStr = d.format("yyyy-MM-dd hh:mm:ss");
	return dateStr;
}


/** ========================================= 日期操作函数－结束 ========================================= */
/*
 * select checkbox function for span type checkbox
 */
function selectAll(checkeAllObj, checkboxName) {
	if ($(checkeAllObj).is('.active')) {
		$(checkeAllObj).removeClass("active");
	} else {
		$(checkeAllObj).addClass("active");
	}
	$(".checkbox").each(function(index,domEle){
		if ($(this).is('.active')) {
			$(this).removeClass("active");
		} else {
			$(this).addClass("active");
		}
	});
}
/*
 * select checkbox function
 */
var checkFlag = 0;
function selectAll(checkboxName) {
	var checkboxArray = document.getElementsByName(checkboxName);
	var length = checkboxArray.length;
	var checkboxStatus = false;
	if (checkFlag == 0) {
		checkFlag = 1;
		checkboxStatus = true;
	} else {
		checkFlag = 0;
		checkboxStatus = false;
	}
	for ( var i = 0; i < length; i++) {
		checkboxArray[i].checked = checkboxStatus;
	}
}

function getCheckBoxValue(para) {
	var checkboxObj;
    if (typeof(para) == 'string'){
    	checkboxObj = eval(para);
    }else if (typeof(para) == "object"){
    	checkboxObj = para;
    }
	return checkboxObj.value;
}

function getCheckBoxStatus(para) {
	var checkboxObj;
    if (typeof(para) == 'string'){
    	checkboxObj = eval(para);
    }else if (typeof(para) == "object"){
    	checkboxObj = para;
    }
	return checkboxObj.checked;
}

function closeWindow() {
	window.open('','_parent','');
	window.close();    
}  

function comfirmDelete() {
	if (confirm("确定要删除此记录吗？")) {
		return true;
	} else {
		return false;
	}
}

function CheckAllDel() {
	if (confirm("确定要删除您所选择的记录吗？")) {
		return true;
	} else {
		return false;
	}
}

function clearFormData(theFormId) {
	var theForm = document.getElementById(theFormId);
	for ( var i = 0; i < theForm.elements.length; i++) {
		if (theForm.elements[i].type == "submit"
				|| theForm.elements[i].type == "reset"
				|| theForm.elements[i].type == "button"
				|| theForm.elements[i].type == "checkbox"
				|| theForm.elements[i].type == "hidden") {
		} else if (theForm.elements[i].type == "select-one") {
			theForm.elements[i].value = "-1";
		} else {
			theForm.elements[i].value = "";
		}
	}
}

function autoSetImgSize(originalImages) {
	var maxWidth = 500;
	var maxHeight = 600;
	var imageArray = document.getElementsByName(originalImages);
	for ( var i = 0; i < imageArray.length; i++) {
		var img = imageArray[i];
		if (img.readyState != "complete") {
			//return false;
		} else {
			setImgSize(img, maxWidth, maxHeight);
		}
	}
}

function setImgSize(img, maxWidth, maxHeight) {
	var message = "";
	if (img.offsetHeight > maxHeight)
		message += "\r height:" + img.offsetHeight;
	if (img.offsetWidth > maxWidth)
		message += "\r width:" + img.offsetWidth;
	if (message != "")
		//alert(message);

	var heightWidth = img.offsetHeight / img.offsetWidth;
	var widthHeight = img.offsetWidth / img.offsetHeight;
	if (img.offsetWidth > maxWidth) {
		img.width = maxWidth;
		img.height = maxWidth * heightWidth;
	}
	if (img.offsetHeight > maxHeight) {
		img.height = maxHeight;
		img.width = maxHeight * widthHeight;
	}
}

/**	把页面变灰，同时弹出小窗口的效果js实现	*/
function alertWin(title, msgBoxInnerHTML, w, h){ 
	var titleheight = "22px"; 		//提示窗口标题高度
	var bordercolor = "#00205b"; 	//提示窗口的边框颜色
	var titlecolor = "#FFFFFF"; 	//提示窗口的标题颜色
	var titlebgcolor = "#00205b"; 	//提示窗口的标题背景色
	var bgcolor = "#FFFFFF"; 		//提示内容的背景色
	
	var iWidth = document.documentElement.clientWidth; 
	var iHeight = document.documentElement.clientHeight; 
	var bgObj = document.createElement("div"); 
	bgObj.style.cssText = "position:absolute;left:0px;top:0px;width:"+iWidth+"px;height:"+Math.max(document.body.clientHeight, iHeight)+"px;filter:Alpha(Opacity=30);opacity:0.3;background-color:#000000;z-index:101;";
	document.body.appendChild(bgObj); 
	
	var msgObj=document.createElement("div");
	msgObj.style.cssText = "position:absolute;font:11px '??';top:"+(iHeight-h)/2+"px;left:"+(iWidth-w)/2+"px;width:"+w+"px;height:"+h+"px;text-align:center;border:1px solid "+bordercolor+";background-color:"+bgcolor+";padding:1px;line-height:22px;z-index:102;";
	document.body.appendChild(msgObj);
	
	var table = document.createElement("table");
	msgObj.appendChild(table);
	table.style.cssText = "margin:0px;border:0px;padding:0px;";
	table.cellSpacing = 0;
	var tr = table.insertRow(-1);
	var titleBar = tr.insertCell(-1);
	titleBar.style.cssText = "width:100%;height:"+titleheight+"px;text-align:left;padding:3px;margin:0px;font:12px '??';color:"+titlecolor+";border:1px solid " + bordercolor + ";cursor:move;background-color:" + titlebgcolor;
	titleBar.style.padding = "5px 0 0 35px";
	titleBar.style.background = "#00205b url(/images/title_logo.jpg) no-repeat 5px 0";
	titleBar.innerHTML =  title;
	var moveX = 0;
	var moveY = 0;
	var moveTop = 0;
	var moveLeft = 0;
	var moveable = false;
	var docMouseMoveEvent = document.onmousemove;
	var docMouseUpEvent = document.onmouseup;
	titleBar.onmousedown = function() {
		var evt = getEvent();
		moveable = true; 
		moveX = evt.clientX;
		moveY = evt.clientY;
		moveTop = parseInt(msgObj.style.top);
		moveLeft = parseInt(msgObj.style.left);
		
		document.onmousemove = function() {
			if (moveable) {
				var evt = getEvent();
				var x = moveLeft + evt.clientX - moveX;
				var y = moveTop + evt.clientY - moveY;
				if ( x > 0 &&( x + w < iWidth) && y > 0 && (y + h < iHeight) ) {
					msgObj.style.left = x + "px";
					msgObj.style.top = y + "px";
				}
			}	
		};
		document.onmouseup = function () { 
			if (moveable) { 
				document.onmousemove = docMouseMoveEvent;
				document.onmouseup = docMouseUpEvent;
				moveable = false; 
				moveX = 0;
				moveY = 0;
				moveTop = 0;
				moveLeft = 0;
			} 
		};
	}
	
	var closeBtn = tr.insertCell(-1);
	closeBtn.style.cssText = "cursor:pointer; height:24px; line-height:24px; padding:2px;background-color:" + titlebgcolor;
	closeBtn.innerHTML = "<span style='font-size:11pt; font-weight:bold; margin-right:5px; color:"+titlecolor+";'><img src='/images/base/close_bg.gif' alt='关闭' /></span>";
	closeBtn.onclick = function(){ 
		document.body.removeChild(bgObj); 
		document.body.removeChild(msgObj); 
	} 
	var msgBox = table.insertRow(-1).insertCell(-1);
	msgBox.style.cssText = "font:10pt '??';";
	msgBox.colSpan  = 2;
	msgBox.innerHTML = msgBoxInnerHTML;
	var nexttime = document.getElementsByName("nexttime");
	for (var i = 0; i < nexttime.length; i++) {
		var item = nexttime[i];
		item.onclick = function(){ 
			document.body.removeChild(bgObj); 
			document.body.removeChild(msgObj); 
		} 
	}
	
    function getEvent() {
	    return window.event || arguments.callee.caller.arguments[0];
    }
}

function getEvent(){     //同时兼容ie和ff的写法   
    if(document.all)    
        return window.event;  
             
    func=getEvent.caller;               
    while(func!=null){       
        var arg0=func.arguments[0];   
        if(arg0){   
            if((arg0.constructor==Event || arg0.constructor ==MouseEvent)   
                || (typeof(arg0)=="object" && arg0.preventDefault && arg0.stopPropagation)){       
                return arg0;   
            }   
        }   
        func=func.caller;   
    }   
   return null;   
}

/**	检查文件后缀名是否允许上传类型	*/
var _permissibleExtNames_ = "bmp,jpg,jpeg,png,gif,swf,flv,doc,docx,xls,xlsx,ppt,pptx,vsd,vsdx,xml,pdf,txt,rar,zip";
function isPermissibleExtNames(strFileName){
	if (strFileName == null){
		return false;
	}
	var ename = strFileName.substring(strFileName.lastIndexOf(".") + 1, strFileName.length);
	var ename = ename.toLowerCase();
	var enameArray = _permissibleExtNames_.split(",");
	for(var i=0; i<enameArray.length;i++){
		if (ename==enameArray[i]){
		    return true;
		}
	}
	return false;
}

/**	检查文件后缀名是否允许上传的图片类型	*/
var _permissiblePictureExtNames_ = "bmp,jpg,jpeg,png,gif";
function isPermissiblePictureExtNames(strFileName){
	if (strFileName == null){
		return false;
	}
	var ename = strFileName.substring(strFileName.lastIndexOf(".") + 1, strFileName.length);
	var ename = ename.toLowerCase();
	var enameArray = _permissiblePictureExtNames_.split(",");
	for(var i=0; i<enameArray.length;i++){
		if (ename==enameArray[i]){
		    return true;
		}
	}
	return false;
}
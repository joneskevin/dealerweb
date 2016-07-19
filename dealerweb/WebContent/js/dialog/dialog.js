/** 获得一个对象，即调用document.getElementById()方法 */
function dollar() {
  var elements = new Array();
  for (var i = 0; i < arguments.length; i++) {
    var element = arguments[i];
    if (typeof element == 'string'){
    	element = document.getElementById(element);
      	// alert("element=" + element);
    }

    if (arguments.length == 1)
      return element;

    elements.push(element);
  }
  return elements;
}

var g_agt = navigator.userAgent.toLowerCase();
var is_opera = (g_agt.indexOf("opera") != -1);
var g_title = "";
var g_iframeno = 0;

function exist(s)
{
	return dollar(s)!=null;
}
function myInnerHTML(idname, html)
{
	if (exist(idname))
	{
		dollar(idname).innerHTML = html;
	}
}
/*******************************************************************************
 * @param v_w
 *            弹出框的宽度
 * @param v_h
 *            弹出框的高度
 * @param v_title
 *            弹出框的标题
 * @param v_top
 *            弹出框距离顶部的距离
 * @param v_left
 *            弹出框距离左边的距离
 */
function dialog(v_w, v_h, v_title, v_top, v_left)
{
	var width = v_w;
	var height = v_h;
	var title = v_title;
	g_title = title;
	var top = v_top;

	var left = v_left;

	var sClose = '<a href="javascript:void(0)" onclick="javascript:new dialog().close();">\
		<img src="../../dealer/images/pop_close.gif" width="22" height="22" /></a>';
	
	var sBox = '';
	sBox+='\
		<div id="dialogBox" style="display:none;z-index:2013;width:'+width+'px;">';
	sBox+='\
			<div style="position:absolute;top:0px;height:6px; ';
	// ------------------顶部背景图
	sBox+='\
				background-image: url(../../images/dialogBoxTop.png); ';
	sBox+='\
				background-repeat: repeat-x;width:'+(width-10)+'px;" >';
	sBox+='\
			</div>';
	sBox+='\
			<div style="position:absolute;height:'+(height)+'px;top:6px;';
	sBox+='\
				background:white;border-left-width: 1px solid #c0c0c0;" >';
	sBox+='\
				<div style="width:'+(width-12)+'px; margin-left:1px; margin-right:1px;height:100%;">';
	// ------------------标题部分开始--------------------
	sBox+='\
					<div style="height:24px; background-repeat: repeat-x;border-bottom:1px #c0c0c0 solid;">';
	sBox+='\
						<div id="dialogBoxTitle" style="float:left; width:'+(width-110)+'px;padding-left:25px;';
	sBox+='\
							line-height: 24px; background-repeat: no-repeat; background-position: 5px center;';
	// ------------------ 标题
	sBox+='\
							font-size:18px;color:#10a0d9;text-align:center; font-family:微软雅黑;">'+title+'';
	sBox+='\
						</div>';
	// ------------------关闭按钮
	sBox+='\
						<div id="dialogClose" style="float:right; padding-right:5px;">' 
							+ sClose + '';
	sBox+='\
						</div>';
	sBox+='\
					</div>';
	// ------------------标题部分结束--------------------
	sBox+='\
					<div id="dialogBody" style="height:' + (height-30) + 'px;padding-right:15px; padding-left:15px;">';
	// ------------------ 内容
	sBox+='\
					</div>';
	sBox+='\
				</div>';
	sBox+='\
			</div>';
	sBox+='\
			<div style="position:absolute;top:'+(height+6)+'px;';
	// ------------------底部背景图
	sBox+='\
				background-image: url(../../images/dialogBoxBottom.png);';
	sBox+='\
				background-repeat: repeat-x;width:'+(width-10)+'px;height:6px;" >';
	sBox+='\
			</div>';
	sBox+='\
		</div>';
	sBox+='\
		<div id="dialogBoxShadow" style="display:none;z-index:1998;">';
	sBox+='\
		</div>\
	';
	
	var sIfram = '\
		<iframe id="dialogIframBG" name="dialogIframBG" frameborder="0"\
		marginheight="0" marginwidth="0" hspace="0" vspace="0"\
		scrolling="no" style="position:absolute;z-index:19997;display:none;"></iframe>\
	';
	
	var sBG = '\
		<div id="dialogBoxBG" style="position:absolute;\
		top:0px;left:0px;width:100%x;height:100%;"></div>\
	';
	
	this.init = function()
	{
		dollar('dialogCase') ? dollar('dialogCase').parentNode.removeChild(dollar('dialogCase')) : function(){};
		var oDiv = document.createElement('span');
		oDiv.id = "dialogCase";
		if (!is_opera)
		{
			oDiv.innerHTML = sBG + sIfram + sBox;
		}
		else
		{
			oDiv.innerHTML = sBG + sBox;
		}
		document.body.appendChild(oDiv);
	}

	this.open = function(_sUrl)
	{		
		this.show();
		var openIframe = "<iframe width='100%' height='100%' name='iframe_parent' id='iframe_parent' \
				src='" + _sUrl + "' frameborder='0' scrolling='auto'></iframe>";
		myInnerHTML('dialogBody', openIframe);
	}

	this.show = function(){
		this.middle('dialogBox', top, left);
		if (dollar('dialogIframBG'))
		{
			dollar('dialogIframBG').style.top = dollar('dialogBox').style.top;
			dollar('dialogIframBG').style.left = dollar('dialogBox').style.left;
			dollar('dialogIframBG').style.width = dollar('dialogBox').offsetWidth + "px";
			dollar('dialogIframBG').style.height = dollar('dialogBox').offsetHeight + "px";
			dollar('dialogIframBG').style.display = 'block';
		}
		if (!is_opera) {
			this.shadow();
		}
	}
	
	this.reset = function()
	{
		this.close();
	}

	this.close = function()
	{
		if (window.removeEventListener) 
		{
			window.removeEventListener('resize', this.event_b, false);
			window.removeEventListener('scroll', this.event_b, false);
		} 
		else if (window.detachEvent) 
		{
			try {
				window.detachEvent('onresize', this.event_b);
				window.detachEvent('onscroll', this.event_b);
			} catch (e) {}
		}
		if (dollar('dialogIframBG')) {
			dollar('dialogIframBG').style.display = 'none';
		}
		dollar('dialogBox').style.display='none';
		dollar('dialogBoxBG').style.display='none';
		dollar('dialogBoxShadow').style.display = "none";
		if (typeof(parent.onDialogClose) == "function")
		{
			parent.onDialogClose(dollar('dialogBoxTitle').innerHTML);
		}
	}

	this.shadow = function()
	{
		this.event_b_show();
		if (window.attachEvent)
		{
			window.attachEvent('onresize', this.event_b);
			window.attachEvent('onscroll', this.event_b);
		}
		else
		{
			window.addEventListener('resize', this.event_b, false);
			window.addEventListener('scroll', this.event_b, false);
		}
	}
	
	this.event_b = function()
	{
		var oShadow = dollar('dialogBoxShadow');
		
		if (oShadow.style.display != "none")
		{
			if (this.event_b_show)
			{
				this.event_b_show();
			}
		}
	}
	
	this.event_b_show = function()
	{
		var oShadow = dollar('dialogBoxShadow');
		oShadow['style']['position'] = "absolute";
		oShadow['style']['display']	= "";		
		oShadow['style']['opacity']	= "0.2";
		oShadow['style']['filter'] = "alpha(opacity=20)";
		oShadow['style']['background']	= "#000000";
		/**
		 * var sClientWidth = parent ? parent.document.body.offsetWidth :
		 * document.body.offsetWidth; var sClientHeight = parent ?
		 * parent.document.body.offsetHeight : document.body.offsetHeight; var
		 * sScrollTop = parent ?
		 * (parent.document.body.scrollTop+parent.document.documentElement.scrollTop) :
		 * (document.body.scrollTop+document.documentElement.scrollTop);
		 */
		var sClientWidth = document.body.offsetWidth;
		var sClientHeight = parent ? parent.document.body.offsetHeight-91 : document.body.offsetHeight;
		var sScrollTop = parent ? (parent.document.body.scrollTop+parent.document.documentElement.scrollTop) : (document.body.scrollTop+document.documentElement.scrollTop);
		oShadow['style']['top'] = '0px';
		oShadow['style']['left'] = '0px';
		oShadow['style']['width'] = sClientWidth + "px";
		oShadow['style']['height'] = (sClientHeight + sScrollTop) + "px";
	}

	/***************************************************************************
	 * @param _sId
	 *            弹出框的ID
	 * @param _topOffset
	 *            弹出框距离顶部的距离
	 * @param _leftOffset
	 *            弹出框距离左边的距离
	 */
	this.middle = function(_sId, _topOffset, _leftOffset){
		dollar(_sId)['style']['display'] = '';
		dollar(_sId)['style']['position'] = "absolute";

		var sClientWidth = document.body.clientWidth;
		var sClientHeight = parent.document.body.clientHeight;
		//var sScrollTop = parent.document.body.scrollTop+parent.document.documentElement.scrollTop;

		var sleft = (sClientWidth - dollar(_sId).offsetWidth) / 2 - 50;
		var iTop = (sClientHeight - dollar(_sId).offsetHeight) / 2 - 300;
		var sTop = iTop > 0 ? iTop : 0;

		var styleTop = _topOffset ? _topOffset : (sTop-160);
		var styleLeft = _leftOffset ? _leftOffset : sleft;
		dollar(_sId)['style']['top'] = styleTop + "px";
		dollar(_sId)['style']['left'] = styleLeft + "px";
	}
}

function openWindow(_sUrl, _sWidth, _sHeight, _sTitle){
	var aDialog;
	if(typeof arguments[4] != "undefined"){
		var _sTop = arguments[4];
		if(typeof arguments[5] != "undefined"){
			var _sLeft = arguments[5];
			aDialog = new dialog(_sWidth, _sHeight, _sTitle, _sTop, _sLeft);
		}else{
			aDialog = new dialog(_sWidth, _sHeight, _sTitle, _sTop, 330);
		}
	}else{
		aDialog = new dialog(_sWidth, _sHeight, _sTitle, 50, 380);
	}
	aDialog.init();
	aDialog.open(_sUrl);
}







// -----------------------above is alert
// window--------------------------------------------------------
function openAlert(_sWord, _sButton , _sWidth, _sHeight, _sTitle , _sAction)
{
	return _openAlert(_sWord, _sButton , _sWidth, _sHeight, _sTitle , _sAction, "");
}

/*
 * above is a sample
 */
/**
 * function openAlertBlue(_sWord, _sButton , _sWidth, _sHeight, _sTitle ,
 * _sAction) { var excss = '.rbs1{border:1px solid #d7e7fe; float:left;}\n' +
 * '.rb1-12,.rb2-12{height:23px; color:#fff; font-size:12px; background:#355582;
 * padding:3px 5px; border-left:1px solid #fff; border-top:1px solid #fff;
 * border-right:1px solid #6a6a6a; border-bottom:1px solid #6a6a6a;
 * cursor:pointer;}\n' + '.rb2-12{background:#355582;}\n'; return
 * _openAlert(_sWord, _sButton , _sWidth, _sHeight, _sTitle , _sAction, excss); }
 */

function _openAlert(_sWord, _sButton , _sWidth, _sHeight, _sTitle , _sAction, _excss)
{
	var oEdit = new dialog(_sWidth, _sHeight, _sTitle);
	oEdit.init();
	oEdit.show();
	var framename = "iframe_parent_" + g_iframeno++;
	var openIframe = "<iframe width='100%' height='100%' name='"+framename+"' id='"+framename+"' src='' frameborder='0' scrolling='no'></iframe>";
	myInnerHTML('dialogBody', openIframe);
	var iframe = window.frames[framename];
	iframe.document.open();
	iframe.document.write('<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">\n');
	iframe.document.write('<html xmlns="http://www.w3.org/1999/xhtml">\n');
	iframe.document.write('<head>\n');
	iframe.document.write('<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />\n');
	iframe.document.write('<link href="/css/s.css" rel="stylesheet" type="text/css" />\n');
	if (_excss && _excss.length)
	{
		iframe.document.write('<style>\n');
		iframe.document.write(_excss + '\n');
		iframe.document.write('</style>\n');
	}
	iframe.document.write('</head>\n');
	iframe.document.write('<body>\n');
	if(_sAction == undefined)
	{
		_sAction = "new parent.dialog().reset();";
	}
	iframe.document.write(alertHtml(_sWord , _sButton , _sAction)+'\n');
	iframe.document.write('</body>\n');
	iframe.document.write('</html>\n');
	iframe.document.close();
}

function alertHtml(_sWord , _sButton , _sAction) {
	var html = '<div class="ts4">\
			<div class="ts45" style="border-top:none;padding-top:0;">\
				 '+_sWord+'\
				<div class="c"></div>\
			</div>\
			<div class="ts42 r">\
				<div class="rbs1"><input type="button" value="'+_sButton+'" title="'+_sButton+'" class="rb1-12" onmouseover="this.className=\'rb2-12\';" onmouseout="this.className=\'rb1-12\';" onclick="javascript:'+_sAction+'" /></div>\
				<div class="c"></div>\
			</div>\
		   </div>';
	
	return html;
}


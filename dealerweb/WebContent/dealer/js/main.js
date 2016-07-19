/**
 * farmeInit 
 * 主页面iFrame初始化
 * 
 */
function farmeInit(id){
	//var h = $(window.frames[id].document).find('.bd_rt').height();
	var h=530;
	$('#iframe_left, #iframe_right').attr('height', h);

	//二级导航高度自适应
	var left = $(window.frames['iframe_left'].document).find('.bd_lf');
	left. height(h+'px');
	var olH = h - left.find('ul > li').length*25 -8;
	/**var olH = h - left.find('ul > li').length*25 + 8;*/
	left.find('ol').each(function(){
		$(this).height(olH+'px');
	})
}





$(function(){

/*-----------------------
	公用部分
	-------------------*/
	//设置菜单条样式
	autoUserMenuNavigation();
	//checkbox
	$('.checkbox').bind('click', function(){
		$(this).toggleClass('active');
	})

	//表格宽度自适应
	/**
	if( $('.table_all').length > 0 ){
		var nomal_w = $('.bd_rt').width();
		var table_w = 0;
		$('.table_all .table_hd').find('li').each(function(){
			table_w += parseInt($(this).width()+2);
		})
		// ($('.table_all .table_hd').find('li').length-1) * 122 + parseInt($('.table_all .table_hd').find('li').eq(0).width())+2;
		// nomal_w >= table_w ? $('.table_all, .bd_rt').width(nomal_w+'px') : $('.table_all, .bd_rt').width(table_w+'px');
		nomal_w >= table_w ? $('.table_all').find('ul').width(nomal_w+'px') : $('.table_all').find('ul').width(table_w+'px');
	}
	*/
// ! ---------


/*-----------------------
	主界面右侧顶部导航
	-------------------*/
	
	

	$('.nav_gather').find('span').bind('click', function(){
		$(this).addClass('active').parent('li').siblings('li').find('span').removeClass('active');
	})
	
	
// ! ---------



/*-----------------------
	左侧导航收放
	-------------------*/

	$('.bd_lf').find('strong').bind('click', function(){
		if( $(this).parent('li').hasClass('active') ){
			$(this).parent('li').removeClass('active');
		} else {
			$(this).parent('li').addClass('active').siblings('li').removeClass('active');
		}
	})
	//二级导航选中事件
	$('.bd_lf').find('a').bind('click', function(){
		$('.bd_lf').find('a').removeClass('active');
		$(this).addClass('active');
	})
	//二级导航高度自适应
	// var olH = $('.bd_lf').height() - $('.bd_lf').find('ul > li').length*40 + 6;
	// $('.bd_lf').find('ol').each(function(){
		// $(this).height(olH+'px');
	// })

	//隐藏
	$('#btn_menu').bind('click', function(){
		if( $(this).hasClass('close') ){
			$(this).removeClass('close').parents('.bd_lf').animate({"left": '0px'}, 'fast');
			$('.bd_rt').removeAttr('style');
		} else {
			$(this).addClass('close').parents('.bd_lf').animate({"left": '-267px'}, 'fast');
			$('.bd_rt').css({
				'margin-left': '0',
				'width': '100%'
			});
		}
	})
// ! ---------




/*--------------------------
	试驾环比例
	----------------------*/
	$('#rate_filter').bind('click', function(){
		$('.filter_info').fadeIn();
	})
	$('.filter_info').find('button').bind('click', function(){
		$('.filter_info').fadeOut();
	})
	
	//
	$('#rate_type_a, #rate_type_b').click(function(){
		$(this).addClass('active').siblings('strong').removeClass('active')
	});

// !----------





/*--------------------------
	轨迹回放
	----------------------*/
	$('.map_close').bind('click', function(){
		if( $(this).hasClass('map_open') ){
			$(this).removeClass('map_open').text(' ');
			$('#map_play').animate({'right': '0'}, 'fast');
		} else {
			$(this).addClass('map_open').text(' ');;
			$('#map_play').animate({'right': '-217px'}, 'fast');
		}
	});
	
	//隐藏表单
	$('#btn_hide').bind('click', function(){		
		if( $(this).text() == '显示' ){
			$(this).text('隐藏');
		} else {
			$(this).text('显示');
		}
		
		$('#table_track').toggle();
	});
// !----------






/*--------------------------
	外出报备审核
	----------------------*/

	//弹出车辆报备详情
	$('.btn_audit').bind('click', function(){
		$('.pop').fadeIn('fast');
	})

	//关闭车辆报备详情
	$('.pop_close').bind('click', function(){
		$('.pop').fadeOut('fast');
	})

	//车辆报备详情展开/收起
	$('.pop_more_btn').bind('click', function(){
		if( $(this).hasClass('active') ){
			$(this).removeClass('active').next('.pop_more').slideUp('fast');
		} else {
			$(this).addClass('active').next('.pop_more').slideDown('fast');
		}
	})
// !----------

})


function autoSize(){
	if( winSize()[0] <= 1024 ){
		$('body').width('1024px');
	} else {
		$('body').removeAttr('style');
	}
}

function autoNavigation(){
	$('.nav_gather_main').width( $('.bd_rt_nav ').width() - 10 +'px')
	var nav_max = $('.nav_gather_main ').width() - 10;
	var nav_li = 162;
	var nav_li_length = parseInt(nav_max/162);
	$('.nav_gather').width( nav_li_length * nav_li );
	var nav_box = $('.nav_gather').width();
	var nav_weight = $('.nav_gather').find('li').length * nav_li;
	var nav_left = 0;
	$('.nav_gather').find('ul').width(nav_weight +'px');
	
	//first
	$('#btn_prev').bind('click', function(){
		nav_left = 0;
		$('.nav_gather').find('ul').animate({"left": nav_left+'px'}, 'fast');
	})
	
	//last
	$('#btn_next').bind('click', function(){
		nav_left = -(nav_weight-nav_box);
		$('.nav_gather').find('ul').animate({"left": nav_left+'px'}, 'fast');
	})
	
	//prev
	$('#btn_back').bind('click', function(){
		nav_left = nav_left+nav_li >= 0 ? 0 : nav_left+nav_li;
		$('.nav_gather').find('ul').animate({"left": nav_left+'px'}, 'fast');
	})
	
	//next
	$('#btn_ahead').bind('click', function(){
		nav_left = nav_left-nav_li <= -(nav_weight-nav_box) ? -(nav_weight-nav_box) : nav_left-nav_li;
		$('.nav_gather').find('ul').animate({"left": nav_left+'px'}, 'fast');
	})
}

function autoUserMenuNavigation(){
	$('.nav_gather_main').width( $('.bd_rt_nav ').width() - 10 +'px')
	var nav_max = $('.nav_gather_main ').width() - 10;
	var nav_li = 162;
	var nav_li_length = parseInt(nav_max/162);
	$('.nav_gather_main').width( nav_li_length * nav_li );
	var nav_box = $('.nav_gather').width();
	var nav_weight = $('.nav_gather_main').find('li').length * nav_li;
	var nav_left = 0;
	$('.nav_gather_main').find('ul').width(nav_weight +'px');
	
}

/**
 * 窗口宽度和高度
 * 
 * 宽度：winSize()[0]
 * 高度：winSize()[1]
 **/
function winSize(){
	var winWidth = 0;
	var winHeight = 0;
	var winSize = Array();
	
	//获取窗口宽度
	if (window.innerWidth){
		winWidth = window.innerWidth;
	} else if ((document.body) && (document.body.clientWidth)){
		winWidth = document.body.clientWidth;
	};
		
	//获取窗口高度
	if (window.innerHeight){
		winHeight = window.innerHeight;
	}else if ((document.body) && (document.body.clientHeight)){
		winHeight = document.body.clientHeight;
	};
	
	if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth){
		winHeight = document.documentElement.clientHeight;
		winWidth = document.documentElement.clientWidth;
	};
	
	winSize[0] = winWidth;
	winSize[1] = winHeight;
	return winSize;
} //winSize



/**
 * 弹出层绝对居中
 **/
function showid(idname){
	var isIE = (document.all) ? true : false;
	var isIE6 = isIE && ([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 6);
	var newbox=document.getElementById(idname);
		newbox.style.zIndex="9999";
		$(newbox).fadeIn('fast');
		//newbox.style.display="block"
		newbox.style.position = !isIE6 ? "fixed" : "absolute";
		newbox.style.top =newbox.style.left = "50%";
		newbox.style.marginTop = - newbox.offsetHeight / 2 + "px";
		newbox.style.marginLeft = - newbox.offsetWidth / 2 + "px";  
	
	var layer=document.createElement("div");
		layer.id="layer";
		layer.style.width=layer.style.height="100%";
		layer.style.position= !isIE6 ? "fixed" : "absolute";
		layer.style.top=layer.style.left=0;
		layer.style.backgroundColor="#000";
		layer.style.zIndex="9998";
		layer.style.opacity="0.6";
		layer.style.filter="alpha(opacity=60)";//IE下层透明度调整
		layer.style.display="none";
		$(layer).fadeIn('fast');
	
	document.body.appendChild(layer);
	var sel=document.getElementsByTagName("select");
	
	//for(var i=0;i<sel.length;i++){        
		//sel[i].style.visibility="hidden";
	//}
	
	function layer_iestyle(){      
		layer.style.width=Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth)+ "px";
		layer.style.height= Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight) + "px";
	}
	function newbox_iestyle(){      
		newbox.style.marginTop = document.documentElement.scrollTop - newbox.offsetHeight / 2 + "px";
		newbox.style.marginLeft = document.documentElement.scrollLeft - newbox.offsetWidth / 2 + "px";
	}
	if(isIE){layer.style.filter ="alpha(opacity=60)";}
	if(isIE6){  
		layer_iestyle()
		newbox_iestyle();
		window.attachEvent("onscroll",function(){                              
			newbox_iestyle();
		})
		window.attachEvent("onresize",layer_iestyle)          
	}
	$(layer).click(function(){
		$(newbox, layer).fadeOut('fast', function(){
			$(layer).remove();
		});		
	}); 
};//showid
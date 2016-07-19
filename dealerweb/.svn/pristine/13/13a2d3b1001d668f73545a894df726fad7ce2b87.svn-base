/**在需要弹出一个div的时候使用，比如站内消息选择收件人*/
function getEvent(){     //同时兼容ie和ff的写法   
     if(document.all)    return window.event;           
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

//点击菜单生成，可以把要生成的内容放入menuPanel对象，一般用来生成简单提示 
function createMenuDiv(MenuDivInnerHTML){
	var evt = getEvent();    
    var _menuPanel = document.getElementById( "menuPanel" );
	_menuPanel.innerHTML = MenuDivInnerHTML;
    showMenuDiv( evt );
};
//显示原来已经生成的隐藏弹出菜单
function showMenuDiv( evt ){
	var _event = evt ? evt : getEvent();
	//var _event = evt ? evt : event;
	var _target = evt ? evt.target : _event.srcElement;
	var _menuPanel = document.getElementById( "menuPanel" );
	//alert("_menuPanel.style.left=" + _menuPanel.style.left);
	_menuPanel.style.top = _event.clientY + document.body.scrollTop + 10 + "px" ;
	_menuPanel.style.left = _event.clientX + document.body.scrollLeft + 10 + "px";
	//alert("_event.clientX=" + _event.clientX);
	//alert("document.body.scrollLeft=" + document.body.scrollLeft);
	//alert("_menuPanel.style.left=" + _menuPanel.style.left);
	displayMenuDiv( "menuPanel" , true );
	_menuPanel.focus();
}
function displayMenuDiv(obj, bShow) {
	obj = (typeof(obj) == "string" ? document.getElementById(obj) : obj);
	if (obj) obj.style.display= (bShow ? "" : "none");
}
function checkClick(evt){
	var _target = evt ? evt.target : event.srcElement ;
	var _id = _target.id;
	if( _id == "" ){
		_id = _target.parentNode.id;
	}
	//alert("_id=" + _id);
	if(  _id.indexOf( "menuPanelLink_" ) > -1 || _id.indexOf( "menuPanel" ) > -1 ){
		//如果点击的链接ID名称包含“menuPanelLink_”，则不动作，让点击方法自动觉得是否隐藏或显示DIV
		//displayMenuDiv( "menuPanel" , false );
	}else{
		if( _id.indexOf( "closeId" ) > -1){
			displayMenuDiv( "menuPanel" , false );
		}else if( _id.indexOf( "menuPanelLink_" ) > -1){
			displayMenuDiv( "menuPanel" , true );
		}else{
		}
	}
}

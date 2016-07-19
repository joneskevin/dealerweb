$.extend({
    includePath: '',
    include: function(file)
    {
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++)
        {
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " language='javascript' type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + $.includePath + name + "'";
            if ($(tag + "[" + link + "]").length == 0) document.write("<" + tag + attr + link + "></" + tag + ">");
        }
    }
});
//本文件中只加载js文件和js插件对应的css文件,不加载系统用到的css文件,系统css文件在各自的meta.jsp文件中加载
$.include(['../../js/jquery/alert/jquery.alerts.js','../../js/jquery/alert/jquery.alerts.css']);
$.include(['../../js/dialog/dialog.js']);
$.include(['../../js/CommonUtil.js']);

function doFocus(thisInput,defaultValue){
	if(thisInput.value != null && thisInput.value.length > 0){
		if(thisInput.value == defaultValue){
			thisInput.value = '';
			thisInput.title = defaultValue;
		}else{
			thisInput.title = thisInput.value;
		}
	}else{
		thisInput.value = '';
		thisInput.title = defaultValue;
	}
}

function doBlur(thisInput,defaultValue){
	if(thisInput.value != null && thisInput.value.length > 0){
		thisInput.title = thisInput.value;
	}else{
		thisInput.value = defaultValue;
		thisInput.title = defaultValue;
	}
}


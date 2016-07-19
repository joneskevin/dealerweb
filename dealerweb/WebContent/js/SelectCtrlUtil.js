/*
 * html的select控件通用js类
 */
 
/**	根据select对象ID和value来取得一个option的index值	*/
function getOptionIndexByValue(selectCtrlId, value) {
	var selectCtrl = document.getElementById(selectCtrlId);
	if (selectCtrl == null) {
		return 0;
	}

	if (value == null || value < 0) {
		return 0;
	}
	for ( var i = 0; i < selectCtrl.length; i++) {
		if (selectCtrl.options[i].value == value) {
			return i;
		}
	}
	return 0;

}

/**	根据select对象和value来选定一个option	*/
function setSelectedOptionIndexByValue(selectCtrl, value) {
	for ( var i = 0; i < selectCtrl.length; i++) {
		if (selectCtrl.options[i].value == value) {
			selectCtrl.options[i].selected = true;
		}
	}
}

function resetSelectCtr(selectCtrId, dataSortArray, currentValue) {
	var selectCtr = document.getElementById(selectCtrId);
	clearOptions(selectCtr);
	for ( var i = 0; i < dataSortArray.length; i++) {
		var item = dataSortArray[i][0];
		var items = item.split(',');
		addOption(selectCtr, items[0], items[1]);
	}
	setSelectedOptionIndexByValue(selectCtr, currentValue);
}

function resetLeafSelectCtr(selectCtrId, dataSortArray, currentValue) {
	var selectCtr = document.getElementById(selectCtrId);
	clearOptions(selectCtr);
	for ( var i = 0; i < dataSortArray.length; i++) {
		var item = dataSortArray[i];
		var items = item.split(',');
		addOption(selectCtr, items[0], items[1]);
	}
	setSelectedOptionIndexByValue(selectCtr, currentValue);
}
/**
 * this option exists in selectCtrl option collections? true - yes, false - no.
 */
function optionExists(selectCtrl, optionCaption) {

	if (selectCtrl == null) {
		alert("[optionExists]select control is null");
		return false;
	}

	if (optionCaption == null) {
		alert("[optionExists]option text is null");
		return false;
	}

	var ops = selectCtrl.options;
	for ( var i = 0; i < ops.length; i++) {
		if (ops[i].text == optionCaption) {
			return true;
		}
	}
	return false;
}

/**	添加一个option对象 <option value="">xxx</option>	*/
function addOption(selectCtrl, value, text) {
	if (selectCtrl == null) {
		//alert("select ctrl is null");
		return;
	}
	var option = document.createElement("OPTION");
	option.value = value;
	option.text = text;
	selectCtrl.options.add(option);
}
/**	添加一个option对象并选定 <option value="">xxx</option>	*/
function addOptionAndSelected(selectCtrl, value, text) {
	if (selectCtrl == null) {
		//alert("select ctrl is null");
		return;
	}
	var option = document.createElement("OPTION");
	option.value = value;
	option.text = text;
	option.selected = true;
	selectCtrl.options.add(option);
}
/*
 * clear options
 */
function clearOptions(selectCtrl) {
	if (selectCtrl == null) {
		return null;
	}
	while (selectCtrl.length > 0) {
		//selectCtrl.options.remove(0);
		selectCtrl.remove(0);
	}

}

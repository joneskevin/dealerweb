//v.3.5 build 120731
/*
 * dhtmlxTree的工具类
*/
function createTree(container, xml, checkedIds, enableChecks){
	var dhtmlxTree = new dhtmlXTreeObject(container, "100%", "100%", 0);
	dhtmlxTree.setImagePath("../../js/dxTree/images/");
	dhtmlxTree.enableCheckBoxes(enableChecks);//是否显示复选框的方法。该行代码必须在loadXMLString的前面
	dhtmlxTree.loadXMLString(xml);
	dhtmlxTree.enableThreeStateCheckboxes(true);//控制树节点选中状态的方法。放在最后，防止初始化checkbox时出错

	if (checkedIds) {
		var itemIds = checkedIds.split(",");
		for(var i=0; i<itemIds.length;i++){
			var itemId = itemIds[i];
			var sonCount = dhtmlxTree.hasChildren(itemId);
			if(sonCount == 0){//叶子节点时才选中，避免误选中兄弟节点的bug
				dhtmlxTree.setCheck(itemId, true);
			}
		}
	}
	return dhtmlxTree;
};

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.ava.util.TypeConverter"%>
<%@ page import="com.ava.resource.DbCacheResource"%>
<%@ page import="com.ava.domain.entity.DataDictionary"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/CommonUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/SelectCtrlUtil.js"></script>
<%
	Integer sortId = TypeConverter.toInteger(request.getParameter("sortId"));
%>
<script>	
	var dataSortTreeArray2Level = new Array();
<%
	List dataSorts1 = null;
	List dataSorts2 = null;
	DataDictionary dataSort1 = null;
	DataDictionary dataSort2 = null;	
	String item1 = null;
	String item2 = null;
	
	dataSorts1 = DbCacheResource.findNodesFromDataDictionaryForSingleSelect(sortId,1, "--请选择行业大类--", -1);
	if(dataSorts1 != null && dataSorts1.size() > 1){		
	    Iterator itor1 =  null;
	    Iterator itor2 =  null;
	    itor1 = dataSorts1.iterator();
	    for(int i=0;itor1.hasNext();i++){
	    	dataSort1 = (DataDictionary)itor1.next();
	    	item1 = dataSort1.getId() + ","
	    		 + dataSort1.getNodeName() + ","
	    		 + dataSort1.getParentId();
%>
			dataSortTreeArray2Level[<%=i%>] = new Array();
    		dataSortTreeArray2Level[<%=i%>][0] = '<%=item1%>';
    		dataSortTreeArray2Level[<%=i%>][1] = new Array();
    		
<%
			dataSorts2 = DbCacheResource.findNodesFromDataDictionaryByParentForSingleSelect(dataSort1.getId(), "--请选择行业小类--", -1);
			itor2 =  dataSorts2.iterator();
			for(int j=0;itor2.hasNext();j++){
				dataSort2 = (DataDictionary)itor2.next();
				item2 = dataSort2.getId() + ","
					 + dataSort2.getNodeName() + ","
					 + dataSort2.getParentId();
			%>
				dataSortTreeArray2Level[<%=i%>][1][<%=j%>] = new Array();
				dataSortTreeArray2Level[<%=i%>][1][<%=j%>][0] = '<%=item2%>';
				dataSortTreeArray2Level[<%=i%>][1][<%=j%>][1] = new Array();
			<%
			}
	    }
    }
%>
</script>
<script>	
	function CascadeSelector2Level(){		
		this.init = function (selectCtr1Id,currentDataSortId1,selectCtr2Id,currentDataSortId2){
				
			if("null".match(currentDataSortId1)){
				currentDataSortId1 = -1;
			}
			if("null".match(currentDataSortId2)){
				currentDataSortId2 = -1;
			}
			if(currentDataSortId1 >= -1){//考虑到“请选择(值为-1)”的情况
				var dataSortArray1 = dataSortTreeArray2Level;
				//alert("selectCtr1Id=" + selectCtr1Id);
				resetSelectCtr(selectCtr1Id,dataSortArray1,currentDataSortId1);//重置下拉列表
			}
			
			var index1 = getOptionIndexByValue(selectCtr1Id,currentDataSortId1);
			if(index1 >= 0){
				var dataSortArray2 = dataSortTreeArray2Level[index1][1];
				resetSelectCtr(selectCtr2Id,dataSortArray2,currentDataSortId2);//重置下拉列表
			}
		
		}	
		
		this.changeDataSort1 = function (selectCtr1Id,selectCtr2Id){	
			var selectCtrl1 = document.getElementById(selectCtr1Id);
			if(selectCtrl1.selectedIndex >= 0){
				var dataSortArray2 = dataSortTreeArray2Level[selectCtrl1.selectedIndex][1];
				resetSelectCtr(selectCtr2Id,dataSortArray2,-1);//重置下拉列表				    
			}
		}
		
	}
</script>

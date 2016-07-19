<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.resource.SelectOption"%>
<script>	
	var dataSortTreeArray2Level = new Array();
<%
	List dataSorts1 = null;
	List dataSorts2 = null;
	SelectOption dataSort1 = null;
	SelectOption dataSort2 = null;	
	String item1 = null;
	String item2 = null;
	
	dataSorts1 = SelectOptionResource.approBaseTypeOptions();
	if(dataSorts1 != null && dataSorts1.size() > 1){		
	    Iterator itor1 =  null;
	    Iterator itor2 =  null;
	    itor1 = dataSorts1.iterator();
	    for(int i=0;itor1.hasNext();i++){
	    	dataSort1 = (SelectOption)itor1.next();
	    	item1 = dataSort1.getOptionValue() + ","
	    		 + dataSort1.getOptionText() + ","
	    		 + "0";
%>
			dataSortTreeArray2Level[<%=i%>] = new Array();
    		dataSortTreeArray2Level[<%=i%>][0] = '<%=item1%>';
    		dataSortTreeArray2Level[<%=i%>][1] = new Array();
    		
<%
			dataSorts2 = SelectOptionResource.getOptionsByExtValue4appro(dataSort1.getOptionValue());
			if(dataSorts2 != null){
				itor2 =  dataSorts2.iterator();
				for(int j=0;itor2.hasNext();j++){
					dataSort2 = (SelectOption)itor2.next();
					item2 = dataSort2.getOptionValue() + ","
						 + dataSort2.getOptionText() + ","
						 + dataSort2.getExtValue();
				%>
					dataSortTreeArray2Level[<%=i%>][1][<%=j%>] = new Array();
					dataSortTreeArray2Level[<%=i%>][1][<%=j%>][0] = '<%=item2%>';
					dataSortTreeArray2Level[<%=i%>][1][<%=j%>][1] = new Array();
				<%
				}
			}
	    }
    }
%>

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
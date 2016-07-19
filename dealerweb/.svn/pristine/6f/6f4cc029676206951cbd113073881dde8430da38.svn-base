<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.ava.domain.entity.DataDictionary" %>
<%@ page import="com.ava.resource.DbCacheResource" %>
<%@ page import="com.ava.util.TypeConverter" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/CommonUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/SelectCtrlUtil.js"></script>
<%
	Integer sortId = TypeConverter.toInteger(request.getParameter("sortId"));
%>

<script>	
	var dataSortTreeArray = new Array();
<%
	List dataSorts1 = null;
	List dataSorts2 = null;
	List dataSorts3 = null;
	DataDictionary dataSort1 = null;
	DataDictionary dataSort2 = null;
	DataDictionary dataSort3 = null;	
	String item1 = null;
	String item2 = null;
	String item3 = null;
	
	dataSorts1 = DbCacheResource.findNodesFromDataDictionaryForSingleSelect(sortId,1);
	if(dataSorts1 != null && dataSorts1.size() > 1){		
	    Iterator itor1 =  null;
	    Iterator itor2 =  null;
	    Iterator itor3 =  null;
	    itor1 = dataSorts1.iterator();
	    for(int i=0;itor1.hasNext();i++){
	    	dataSort1 = (DataDictionary)itor1.next();
	    	item1 = dataSort1.getId() + ","
	    		 + dataSort1.getName() + ","
	    		 + dataSort1.getParentId();
%>
			dataSortTreeArray[<%=i%>] = new Array();
    		dataSortTreeArray[<%=i%>][0] = '<%=item1%>';
    		dataSortTreeArray[<%=i%>][1] = new Array();
    		
<%
			dataSorts2 = DbCacheResource.findNodesFromDataDictionaryByParentForSingleSelect(dataSort1.getId(), "--请选择--", -1);
			itor2 =  dataSorts2.iterator();
			for(int j=0;itor2.hasNext();j++){
				dataSort2 = (DataDictionary)itor2.next();
				item2 = dataSort2.getId() + ","
					 + dataSort2.getName() + ","
					 + dataSort2.getParentId();
			%>
				dataSortTreeArray[<%=i%>][1][<%=j%>] = new Array();
				dataSortTreeArray[<%=i%>][1][<%=j%>][0] = '<%=item2%>';
				dataSortTreeArray[<%=i%>][1][<%=j%>][1] = new Array();
			<%
				dataSorts3 = DbCacheResource.findNodesFromDataDictionaryByParentForSingleSelect(dataSort2.getId(), "--请选择--", -1);
				itor3 =  dataSorts3.iterator();
				for(int k=0;itor3.hasNext();k++){
					dataSort3 = (DataDictionary)itor3.next();
					item3 = dataSort3.getId() + ","
						 + dataSort3.getName() + ","
						 + dataSort3.getParentId();
				%>
					dataSortTreeArray[<%=i%>][1][<%=j%>][1][<%=k%>] = '<%=item3%>'; 
				<%			
				}
			}
	    }
    }
%>
</script>
<script>	
	function CascadeSelector3Level(){		
		this.init = function (selectCtr1Id,currentDataSortId1,selectCtr2Id,currentDataSortId2,selectCtr3Id,currentDataSortId3){
				
			if("null".match(currentDataSortId1)){
				currentDataSortId1 = -1;
			}
			if("null".match(currentDataSortId2)){
				currentDataSortId2 = -1;
			}
			if("null".match(currentDataSortId3)){
				currentDataSortId3 = -1;
			}
			if(currentDataSortId1 >= -1){//考虑到“请选择(值为-1)”的情况
				var dataSortArray1 = dataSortTreeArray;
				resetSelectCtr(selectCtr1Id,dataSortArray1,currentDataSortId1);//重置下拉列表
			}
			
			var index1 = getOptionIndexByValue(selectCtr1Id,currentDataSortId1);
			if(index1 >= 0){
				var dataSortArray2 = dataSortTreeArray[index1][1];	
				resetSelectCtr(selectCtr2Id,dataSortArray2,currentDataSortId2);//重置下拉列表
			}
		
			var index2 = getOptionIndexByValue(selectCtr2Id,currentDataSortId2);
			if(index1 >= 0 && index2 >= 0){			
				var dataSortArray3 = dataSortTreeArray[index1][1][index2][1];
				resetLeafSelectCtr(selectCtr3Id,dataSortArray3,currentDataSortId3);//重置叶子层下拉列表
			}		
		}	
		
		this.changeDataSort1 = function (selectCtr1Id,selectCtr2Id,selectCtr3Id){	
			var selectCtrl1 = document.getElementById(selectCtr1Id);
			if(selectCtrl1.selectedIndex >= 0){
				var dataSortArray2 = dataSortTreeArray[selectCtrl1.selectedIndex][1];
				resetSelectCtr(selectCtr2Id,dataSortArray2,-1);//重置下拉列表	
				this.changeDataSort2(selectCtr1Id,selectCtr2Id,selectCtr3Id);				    
			}
		}
		
		this.changeDataSort2 = function (selectCtr1Id,selectCtr2Id,selectCtr3Id){
			var selectCtrl1 = document.getElementById(selectCtr1Id);
			var selectCtrl2 = document.getElementById(selectCtr2Id);
			if(selectCtrl1.selectedIndex >= 0 && selectCtrl2.selectedIndex >= 0){
				var dataSortArray3 = dataSortTreeArray[selectCtrl1.selectedIndex][1][selectCtrl2.selectedIndex][1];
				resetLeafSelectCtr(selectCtr3Id,dataSortArray3,-1);//重置叶子层下拉列表
			}else{
				var dataSortArray3 = dataSortTreeArray[0][1][0][1];				
				resetLeafSelectCtr(selectCtr3Id,dataSortArray3,-1);//重置叶子层下拉列表
			}
			
		}
	}
</script>

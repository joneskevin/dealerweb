package com.ava.back.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.back.service.IDataDictionaryService;
import com.ava.base.domain.ResponseData;
import com.ava.dao.IDataDictionaryDao;
import com.ava.dao.IOperatorDao;
import com.ava.domain.entity.DataDictionary;
import com.ava.domain.entity.Operator;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.cache.CacheDictionaryManager;
import com.ava.util.MyBeanUtils;
import com.ava.util.TypeConverter;

@Service
public class DataDictionaryService implements
		IDataDictionaryService {

	@Autowired
	private IDataDictionaryDao dataDictionaryDao;

	@Autowired
	private IOperatorDao operatorDao;

	public List list(Integer sortId) {
		Map parameters = new HashMap();
		parameters.put("sortId", sortId);
		List nodeList = dataDictionaryDao.find(parameters);
		return nodeList;
	}
	
	public ResponseData displayTree(Integer sortId) {
		ResponseData rd = new ResponseData(0);
		Map parameters = new HashMap();
		parameters.put("sortId", sortId);
		parameters.put("levelId", GlobalConstant.TRUE);
		parameters.put("parentId", GlobalConstant.FALSE);
		List nodeList = dataDictionaryDao.find(parameters);
		String dhtmlXtreeXML = CacheDictionaryManager.getChildrenDataDictionaryTree4Xml(nodeList);
		rd.setFirstItem(dhtmlXtreeXML);
		return rd;
	}

	/**
	 *description:add a new node by nodeSort 
	 *result：
	 *	1:	sucess;
	 *	0:	failure;
	 *	-1:	levelCount is beyond the max value of the sort(新增的结点的层数超出了限制).
	 */
	public ResponseData add(Integer currentOperatorId,  DataDictionary dataDictionary) {
		ResponseData rd = new ResponseData(0);
		if(dataDictionary.getName() == null || dataDictionary.getName().length() == 0){
			rd.setCode(0);
			rd.setMessage("新节点的名字不能为空");
			return rd;
		}
		
		if(dataDictionary.getRank() == null || dataDictionary.getRank().intValue() == 0){
			rd.setCode(0);
			rd.setMessage("排序位置不能为空");
			return rd;
		}
		
		Map parameters = new HashMap();
		parameters.put("sortId", dataDictionary.getSortId());
		parameters.put("name", dataDictionary.getName());
		List nodeList = dataDictionaryDao.find(parameters);
		if (nodeList != null && nodeList.size()>0 ) {
			rd.setCode(-2);
			rd.setMessage("名称已存在");
			return rd;
		}
		
		DataDictionary parent = null;
		Integer theLevelCount = null;
		Integer theLevleId = null;		
		if (dataDictionary.getParentId() == 0 || "0".equals(dataDictionary.getParentId())) {
			theLevleId = 1;
		} else {
			parent = dataDictionaryDao.get(dataDictionary.getParentId());
			if(parent == null || parent.getSortId() == null){
				rd.setCode(0);
				rd.setMessage("新节点的分类ID不能为空");
				return rd;
			}
			
			if (parent.getIsLeaf() == null
					|| GlobalConstant.TRUE == parent.getIsLeaf().intValue()) {
				parent.setIsLeaf(GlobalConstant.FALSE);
			}
			
			String sortNodeLevelCount = SelectOptionResource.getOptionTextByValue(SelectOptionResource.dictinaryLeveCountArray,parent.getSortId());
			if(sortNodeLevelCount != null && !sortNodeLevelCount.equalsIgnoreCase("未定义")){
				theLevelCount = TypeConverter.toInteger(sortNodeLevelCount);
				if(theLevelCount == null || parent.getLevelId() == null){
					rd.setCode(-1);
					rd.setMessage("新节点的层级不能为空");
					return rd;
				}
				if(parent.getLevelId().intValue() >= theLevelCount.intValue()){
					rd.setCode(-1);
					rd.setMessage("新节点的层级不能大于" + theLevelCount);
					return rd;
				}				
			}
			theLevleId = parent.getLevelId() + 1;	
			if(parent.getChildrenNum() != null){
				parent.setChildrenNum(parent.getChildrenNum()+1);
			}else{
				parent.setChildrenNum(1);
			}
			dataDictionaryDao.edit(parent);	
		}
		
		dataDictionary.setChildrenNum(0);
		dataDictionary.setCurrentModificatorId(currentOperatorId);
		dataDictionary.setCurrentModificationTimestamp(new Date());		
		dataDictionary.setLevelCount(theLevelCount);
		dataDictionary.setLevelId(theLevleId);
		dataDictionary.setIsLeaf(GlobalConstant.TRUE);

		Integer newId = (Integer) dataDictionaryDao.save(dataDictionary);		
		CacheDictionaryManager.clear();
		if (newId != null) {
			rd.setCode(1);
			rd.put("dataDictionary", dataDictionary);
			rd.setFirstItem(newId);
			rd.setMessage("新增成功");
			return rd;
		}
		
		rd.setCode(0);
		rd.setMessage("未知错误");
		return rd;
	}
	
	public ResponseData delete(Integer currentNodeId) {
		ResponseData rd = new ResponseData(0);
		DataDictionary currentNode = dataDictionaryDao.get(currentNodeId);
		DataDictionary currentNodeParent = null;
		if(currentNode != null && currentNode.getParentId() != null){
			currentNodeParent = dataDictionaryDao.get(currentNode.getParentId());
			if(currentNodeParent != null && currentNodeParent.getChildrenNum() != null){
				currentNodeParent.setChildrenNum(currentNodeParent.getChildrenNum()-1);
				dataDictionaryDao.edit(currentNodeParent);
			}
		}else{
			rd.setCode(0);
			rd.setMessage("currentNodeParent不能为空");
			return rd;
		}
		
		dataDictionaryDao.delete(currentNodeId);
		CacheDictionaryManager.clear();
		
		rd.setCode(1);
		rd.setMessage("删除成功");
		return rd;
	}

	public ResponseData displayAdd(DataDictionary frmDataDictionary, Integer currentNodeId){
		ResponseData rd = new ResponseData(0);
		DataDictionary dataDictionary = frmDataDictionary;
		if (dataDictionary == null) {
			dataDictionary = new DataDictionary();
		}
		
		String nodeSortName = "";
		if (frmDataDictionary.getLevelId() != null) {
			
			dataDictionary.setSortId(frmDataDictionary.getSortId());
			dataDictionary.setParentId(GlobalConstant.FALSE);
			dataDictionary.setLevelId(GlobalConstant.TRUE);
			nodeSortName = SelectOptionResource.getOptionTextByValue(SelectOptionResource.dictinaryRootNodeArray, frmDataDictionary.getSortId());
		} else {
			DataDictionary currentDataDictionary = dataDictionaryDao.get(currentNodeId);
			dataDictionary.setSortId(currentDataDictionary.getSortId());
			dataDictionary.setParentId(currentNodeId);
			nodeSortName = SelectOptionResource.getOptionTextByValue(SelectOptionResource.dictinaryRootNodeArray, currentDataDictionary.getSortId());
		}
		dataDictionary.setNodeSortName(nodeSortName);
			
		
		rd.setCode(1);
		rd.put("dataDictionary", dataDictionary);
		return rd;
	}
	
	
	public DataDictionary displayEdit(Integer currentNodeId){
		DataDictionary theNode = dataDictionaryDao.get(currentNodeId);
		Operator operator = operatorDao.getOperator(theNode.getCurrentModificatorId());
		if(operator != null){
			theNode.setCurrentModificatorName(operator.getName());
		}
		return theNode;
	}
	
	public ResponseData edit(Integer currentOperatorId, DataDictionary frmNode) {
		ResponseData rd = new ResponseData(0);
		
		DataDictionary theNode = dataDictionaryDao.get(frmNode.getId());
		MyBeanUtils.copyPropertiesContainsDate(theNode, frmNode);		
		theNode.setCurrentModificatorId(currentOperatorId);			
		dataDictionaryDao.edit(theNode);
		CacheDictionaryManager.clear();
		
		rd.setFirstItem(theNode);
		rd.setCode(1);
		return rd;
	}
}

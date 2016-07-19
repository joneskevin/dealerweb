package com.ava.back.service;

import java.util.List;

import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.DataDictionary;

public interface IDataDictionaryService {
	
	public List list(Integer nodeSortId);
	
	public ResponseData displayTree(Integer nodeSortId);

	public ResponseData add(Integer currentOperatorId,  DataDictionary dataDictionary);

	public ResponseData delete(Integer currentNodeId);

	public ResponseData displayAdd(DataDictionary dataDictionary, Integer currentNodeId);
	
	public DataDictionary displayEdit(Integer currentNodeId);

	public ResponseData edit(Integer currentOperatorId, DataDictionary frmNode);
}

package com.ava.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ava.dao.IDataDictionaryDao;
import com.ava.dao.IOperatorDao;
import com.ava.domain.entity.DataDictionary;
import com.ava.domain.entity.Operator;
import com.ava.util.TypeConverter;

/**数据字典缓存类*/
public class DbCacheResource{
	static private IDataDictionaryDao dataDictionaryDao = (IDataDictionaryDao)GlobalConfig.getBean("dataDictionaryDao");
	static private IOperatorDao operatorDao = (IOperatorDao)GlobalConfig.getBean("operatorDao");
	/* ================================数据字典数据缓存 开始 ================================*/
	static private List wholeDataDictionary = null;
	
	public static void setWholeDataDictionary(List dataDictionary) {
		wholeDataDictionary = dataDictionary;
	}
	
	/**	注意不要直接使用wholeDataDictionary，否则会造成错误；尽量使用这个方法来访问wholeDataDictionary	*/
	static public List getWholeDataDictionary() {
		if (wholeDataDictionary == null) {
			wholeDataDictionary = (List) dataDictionaryDao.getAll();
		}	
		return wholeDataDictionary;
	}

	/**
	 * @param nodeId
	 * @return
	 */
	static public DataDictionary getDataDictionaryById(Integer nodeId) {
		if (nodeId == null) {
			return null;
		}

		List myWholeDataDictionary = getWholeDataDictionary();
		DataDictionary dictionary = null;
		if (myWholeDataDictionary != null && myWholeDataDictionary.size() > 0) {
			Iterator itor = myWholeDataDictionary.iterator();
			while (itor.hasNext()) {
				dictionary = (DataDictionary) itor.next();
				if (dictionary.getId().intValue() == nodeId.intValue()) {
					return dictionary;
				}
			}
		}
		return null;
	}	

	/**	返回父节点的ID	*/
	static public Integer getDataDictionaryParentIdById(Integer nodeId) {
		if (nodeId == null) {
			return null;
		}

		List myWholeDataDictionary = getWholeDataDictionary();
		DataDictionary dictionary = null;
		if (myWholeDataDictionary != null && myWholeDataDictionary.size() > 0) {
			Iterator itor = myWholeDataDictionary.iterator();
			while (itor.hasNext()) {
				dictionary = (DataDictionary) itor.next();
				if (dictionary.getId().intValue() == nodeId.intValue()) {
					return dictionary.getParentId();
				}
			}
		}
		return null;
	}	
	/**	根据ID获得相应的数据字典项名称	*/
	static public String getDataDictionaryNameById(Integer value) {
		if (value == null) {
			return "";
		}
		List myWholeDataDictionary = getWholeDataDictionary();
		if (myWholeDataDictionary != null && myWholeDataDictionary.size() > 0) {
			DataDictionary dataDictionary = new DataDictionary();
			Iterator it = myWholeDataDictionary.iterator();
			while (it.hasNext()) {
				dataDictionary = (DataDictionary) it.next();
				if (dataDictionary != null) {
					if ( dataDictionary.getId().intValue()==value.intValue() ) {
						return dataDictionary.getName();
					}
				}
			}
		}
		return "";
	}
	/**	查找某个自定义类别中相应名称的数据项的ID	*/
	static public Integer getDataDictionaryIdByName(Integer nodeSort,String name) {
		if (nodeSort == null || name == null) {
			return null;
		}
		List myWholeDataDictionary = getWholeDataDictionary();
		if (myWholeDataDictionary != null && myWholeDataDictionary.size() > 0) {
			DataDictionary dataDictionary = new DataDictionary();
			Iterator it = myWholeDataDictionary.iterator();
			while (it.hasNext()) {
				dataDictionary = (DataDictionary) it.next();
				if (dataDictionary != null) {
					if (dataDictionary.getSortId().intValue() == nodeSort
							.intValue()
							&& dataDictionary.getName().equals(name)) {
						return dataDictionary.getId();
					}
				}
			}
		}
		return null;
	}
	/**	该节点是否叶子节点	*/
	static public boolean isLeaf(Integer nodeId) {
		if (nodeId == null) {
			return true;
		}
		List myWholeDataDictionary = getWholeDataDictionary();
		DataDictionary dictionary = null;
		if (myWholeDataDictionary != null && myWholeDataDictionary.size() > 0) {
			Iterator itor = myWholeDataDictionary.iterator();
			while (itor.hasNext()) {
				dictionary = (DataDictionary) itor.next();
				if (dictionary.getParentId().intValue() == nodeId.intValue()) {
					//System.out.println("isLeaf=false");
					return false;
				}
			}
		}
		return true;
	}	
	
	/**	查找某个自定义类别的总层数	*/
	static private Integer findLeafLevelFromDataDictionary(Integer sortId) {
		if (sortId == null) {
			return null;
		}

		List myWholeDataDictionary = getWholeDataDictionary();
		Integer leafLevel = 0;
		if (myWholeDataDictionary != null && myWholeDataDictionary.size() > 0) {
			DataDictionary dataDictionary = new DataDictionary();
			Iterator it = myWholeDataDictionary.iterator();
			while (it.hasNext()) {
				dataDictionary = (DataDictionary) it.next();
				if (dataDictionary != null) {
					if (dataDictionary.getSortId().intValue() == sortId.intValue()
							&& dataDictionary.getLevelId() > leafLevel.intValue()) {
						leafLevel = dataDictionary.getLevelId();
					}
				}
			}
		}
		return leafLevel;
	}
	/**	根据ID查找其兄弟节点	*/
	static public List findBrotherNodesById(Integer id) {
		Integer parentId = getDataDictionaryParentIdById(id);
		List resultList = findNodesFromDataDictionaryByParent(parentId);
		return resultList;
	}
	/**	根据父节点查找其下层的子节点	*/
	static public List findNodesFromDataDictionaryByParent(Integer parentId) {
		if ( parentId == null ) {
			return null;
		}
		List myWholeDataDictionary = getWholeDataDictionary();
		List<Object> resultList = new ArrayList<Object>();
		if (myWholeDataDictionary != null && myWholeDataDictionary.size() > 0) {
			DataDictionary dataDictionary = new DataDictionary();
			Iterator it = myWholeDataDictionary.iterator();
			while (it.hasNext()) {
				dataDictionary = (DataDictionary) it.next();
				if (dataDictionary != null) {
					if ( dataDictionary.getParentId().intValue() == parentId.intValue() ) {
						resultList.add(dataDictionary);
					}
				}
			}
		}
		return resultList;
	}
	/**	根据父节点查找其所有的子节点	*/
	static public List findAllNodesFromDataDictionaryByParent(Integer parentId) {
		List<Object> resultList = new ArrayList<Object>();
		List<Object> tempList = findNodesFromDataDictionaryByParent(parentId);
		if ( tempList != null && tempList.size() > 0 ) {
			DataDictionary dataDictionary = new DataDictionary();
			Iterator it = tempList.iterator();
			while (it.hasNext()) {
				dataDictionary = (DataDictionary) it.next();
				if (dataDictionary != null) {
					if ( isLeaf(dataDictionary.getId()) ){
						//如果已经是叶子节点
						resultList.add(dataDictionary);
					}else{
						//如果还有下层，则递归调用
						resultList.add(dataDictionary);
						List<Object> list = findAllNodesFromDataDictionaryByParent(dataDictionary.getId());
						if ( list != null && list.size() > 0 ) {
							DataDictionary dataDictionaryTemp = new DataDictionary();
							Iterator iterator = list.iterator();
							while (iterator.hasNext()) {
								dataDictionaryTemp = (DataDictionary) iterator.next();
								resultList.add(dataDictionaryTemp);
							}
						}
					}
				}
			}
		}		
		return resultList;
	}
	/**	查找某个自定义类别中指定层的所有节点	*/
	static public List findNodesFromDataDictionary(Integer sortId,Integer level) {
		if (sortId == null || level == null) {
			return null;
		}

		List myWholeDataDictionary = getWholeDataDictionary();
		List<Object> resultList = new ArrayList<Object>();
		if (myWholeDataDictionary != null && myWholeDataDictionary.size() > 0) {
			DataDictionary dataDictionary = new DataDictionary();
			Iterator it = myWholeDataDictionary.iterator();
			while (it.hasNext()) {
				dataDictionary = (DataDictionary) it.next();
				if (dataDictionary != null) {
					if (dataDictionary.getSortId().intValue() == sortId
							.intValue()
							&& dataDictionary.getLevelId() == level.intValue()) {
						resultList.add(dataDictionary);
					}
				}
			}
		}
		return resultList;
	}
	/**	得到数据字典中，某一个自定义类别的末端节点集合	*/
	static public List findLeafNodesFromDataDictionary(Integer sortId){
		if (sortId == null) {
			return null;
		}

		List myWholeDataDictionary = getWholeDataDictionary();
		List<Object> resultList = new ArrayList<Object>();
		if (myWholeDataDictionary != null && myWholeDataDictionary.size() > 0) {
			DataDictionary dataDictionary = new DataDictionary();
			Iterator it = myWholeDataDictionary.iterator();
			while (it.hasNext()) {
				dataDictionary = (DataDictionary) it.next();
				if (dataDictionary != null) {
					if (dataDictionary.getSortId().intValue() == sortId
							.intValue()
							&& dataDictionary.getChildrenNum().intValue() == 0) {
						resultList.add(dataDictionary);
					}
				}
			}
		}
		return resultList;
	}
	
	/**	得到数据字典中，某一个自定义类别的末端节点选择项集合，一般用来用户复选使用	
	 *  @注意：此方法不会自动添加“请选择”选项，这是和getDataDictionaryOptionsForSingleSelect()的区别
	 * 	*/
	static public List getDataDictionaryOptionsForMultiSelect(Integer sortId) {
		if (sortId == null) {
			return null;
		}
		Integer leafLevel = findLeafLevelFromDataDictionary(sortId);

		SelectOption listOption = new SelectOption();
		List myWholeDataDictionary = getWholeDataDictionary();
		List<Object> resultList = new ArrayList<Object>();
		if (myWholeDataDictionary != null && myWholeDataDictionary.size() > 0) {
			DataDictionary dataDictionary = new DataDictionary();
			Iterator it = myWholeDataDictionary.iterator();
			while (it.hasNext()) {
				dataDictionary = (DataDictionary) it.next();
				if (dataDictionary != null) {
					if (dataDictionary.getSortId().intValue() == sortId
							.intValue()&& dataDictionary.getLevelId() == leafLevel.intValue()) {
						listOption = new SelectOption();
						listOption.setOptionText(dataDictionary.getName());
						listOption.setOptionValue(dataDictionary.getId().toString());
						listOption.setExtValue(dataDictionary.getId().toString());
						resultList.add(listOption);
					}
				}
			}
		}		

		return resultList;
	}
	/**	得到数据字典中，某一个类别下制定层的所有节点数据	
	 *  @注意：此方法会自动添加一个“请选择”选项
	 * 	*/
	static public List findNodesFromDataDictionaryForSingleSelect(Integer sortId,Integer level) {
		return findNodesFromDataDictionaryForSingleSelect(sortId, level, "--请选择--", -1);
	}
	/**	得到数据字典中，某一个类别下制定层的所有节点数据，并根据第2、3个参数生成第一个的选择项，如：请选择(-1)
	 * 	*/
	static public List findNodesFromDataDictionaryForSingleSelect(Integer sortId,Integer level, 
			String nodeName, Integer id) {
		List dictionaryList = null;
		dictionaryList = findNodesFromDataDictionary(sortId,level);
		if(dictionaryList == null){
			dictionaryList = new ArrayList<Object>();
		}
		DataDictionary dataDictionary = new DataDictionary();
		dataDictionary.setName(nodeName);
		dataDictionary.setId(id);
		dictionaryList.add(0, dataDictionary);
		return dictionaryList;
	}
	/**根据父节点查找其下层的子节点	
	 *  @注意：此方法会自动添加一个“请选择”选项
	 * */	
	static public List findNodesFromDataDictionaryByParentForSingleSelect(Integer parentId) {
		return findNodesFromDataDictionaryByParentForSingleSelect(parentId, "--请选择--", -1);
	}
	/**根据父节点查找其下层的子节点，并根据第2、3个参数生成第一个的选择项，如：请选择(-1)
	 * */	
	static public List findNodesFromDataDictionaryByParentForSingleSelect(Integer parentId, 
			String nodeName, Integer id) {
		List dictionaryList = null;
		dictionaryList = findNodesFromDataDictionaryByParent(parentId);
		if(dictionaryList == null){
			dictionaryList = new ArrayList<Object>();
		}
		DataDictionary dataDictionary = new DataDictionary();
		dataDictionary.setName(nodeName);
		dataDictionary.setId(id);
		dictionaryList.add(0, dataDictionary);
		return dictionaryList;
	}
	/**	得到数据字典中，查找某一个自定义类别的所有节点选择项集合，用户单选使用	
	 *  @注意：此方法会自动添加一个“请选择”选项
	 * 	*/
	static public List getDataDictionaryOptionsForSingleSelect(Integer sortId) {
		List<Object> dictionaryList = new ArrayList<Object>();
		List dictionaryListLevel1 = findNodesFromDataDictionary(sortId, new Integer(1));
		String optionTextBlank;

		SelectOption selectOption = null;
		selectOption = new SelectOption();
		DataDictionary po = null;
		if (dictionaryListLevel1 != null && dictionaryListLevel1.size() > 0) {

			Iterator itor0 = dictionaryListLevel1.iterator();
			selectOption.setOptionText("--请选择--");
			selectOption.setOptionValue(TypeConverter.toString(""));
			dictionaryList.add(selectOption);
			while (itor0.hasNext()) {
				po = (DataDictionary) itor0.next();

				selectOption = new SelectOption();
				selectOption.setOptionText(po.getName());
				selectOption.setOptionValue(po.getId().toString());
				selectOption.setExtValue(po.getId().toString());
				dictionaryList.add(selectOption);

				List dictinaryListTemp = findAllNodesFromDataDictionaryByParent(po.getId());
				if (dictinaryListTemp != null && dictinaryListTemp.size() > 0) {
					Iterator itor1 = dictinaryListTemp.iterator();
					while (itor1.hasNext()) {
						po = (DataDictionary) itor1.next();
						selectOption = new SelectOption();
						optionTextBlank = "";
						for (int i=2; i<=po.getLevelId(); i++){
							optionTextBlank = optionTextBlank + "&nbsp;&nbsp;";					
						}
						selectOption.setOptionText(optionTextBlank + "|--" + po.getName());
						selectOption.setOptionValue(po.getId().toString());
						selectOption.setExtValue(po.getId().toString());
						dictionaryList.add(selectOption);
					}
				}
			}

		} else {
			selectOption.setOptionText("--空--");
			selectOption.setOptionValue(TypeConverter.toString(""));
			dictionaryList.add(selectOption);
		}
		return dictionaryList;
	}

	/*================================ 数据字典数据缓存 结束 ================================*/


	/*================================ 操作员缓存 开始 ================================*/
	static private List wholeOperators = null;

	public static void setWholeOperators(List operators) {
		wholeOperators = operators;
	}
	/**	注意不要直接使用wholeOperators，否则会造成错误；尽量使用这个方法来访问wholeOperators	*/
	public static List getWholeOperators() {
		if (wholeOperators == null) {
			System.out.println("com.ava.resource.DbCacheResource.getWholeOperators() =======> wholeOperators is null");
			wholeOperators = (List) operatorDao.getAll();
		}
		return wholeOperators;
	}
		
	public static String getOperatorNameById(Integer operatorId) {
		if (operatorId == null) {
			return null;
		}

		Operator operator = null;
		if (getWholeOperators() != null && getWholeOperators().size() > 0) {
			Iterator itor = getWholeOperators().iterator();
			while (itor.hasNext()) {
				operator = (Operator) itor.next();
				if (operator.getId().intValue() == operatorId.intValue()) {
					return operator.getName();
				}
			}
		}
		return null;
	}
	/*================================ 操作员缓存 结束 ================================*/
	
	
	static public void main(String[] args) {
		List wholeDictionary = DbCacheResource.getWholeDataDictionary();
		if(wholeDictionary != null){
			System.out.print(wholeDictionary.size());
		}

	}
}
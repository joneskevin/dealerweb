package com.ava.resource.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ava.dao.IDataDictionaryDao;
import com.ava.domain.entity.DataDictionary;
import com.ava.resource.SpringContext;
import com.ava.util.MyStringUtils;

/**
 * 字典缓存
 */
public class CacheDictionaryManager {

	public static final Log logger = LogFactory.getLog(CacheDictionaryManager.class);

	private static Object initLock = new Object();

	/** Map<Integer, Org> 所有字典的缓存，增删改组织时一定要重新加载 */
	private static Map<Integer, DataDictionary> allDataDictionaryCache = new HashMap<Integer, DataDictionary>();
	
	/** List<dictionary> 所有字典的缓存，增删改字典时一定要重新加载 */
	private static List<DataDictionary> sortedAllDataDictionaryCache = null;

	/** 某个主节点ID对应的子节点列表列表-xml */
	private static Map<Integer, String> dictionaryIdAndDataDictionary4XmlCache = new HashMap<Integer, String>();
	
	private static IDataDictionaryDao dataDictionaryDao = SpringContext.getBean("dataDictionaryDao");

	private static List<DataDictionary> getSortedAllDataDictionary() {
		if (sortedAllDataDictionaryCache == null) {
			synchronized (initLock) {
				if (sortedAllDataDictionaryCache == null) {
					sortedAllDataDictionaryCache = dataDictionaryDao.getAllWithoutNickInvoke();
					if (sortedAllDataDictionaryCache != null && sortedAllDataDictionaryCache.size() > 0) {
						//暂时不排序
						//Collections.sort(sortedAllDataDictionaryCache);
					}
				}
			}
		}
		return sortedAllDataDictionaryCache;
	}

	private static Map getAllDataDictionarys() {
		if (allDataDictionaryCache == null || allDataDictionaryCache.size() == 0) {
			List<DataDictionary> dictionaryList = getSortedAllDataDictionary();
			if (dictionaryList != null && dictionaryList.size() > 0) {
				for(DataDictionary dictionary : dictionaryList){
					allDataDictionaryCache.put(dictionary.getId(), dictionary);
				}
			}
		}
		return allDataDictionaryCache;
	}

	public static void clear() {
		allDataDictionaryCache.clear();
		sortedAllDataDictionaryCache = null;
		dictionaryIdAndDataDictionary4XmlCache.clear();
	}

	public static DataDictionary get(Integer dictionaryId) {
		if (dictionaryId == null){
			return null;
		}
		
		Map dataDictionarys = getAllDataDictionarys();
		if(dataDictionarys != null){
			return (DataDictionary) dataDictionarys.get(dictionaryId);
		}
		return null;
	}

	public static String getName(Integer dictionaryId) {
		DataDictionary dataDictionary = get(dictionaryId);
		if(dataDictionary != null){
			return dataDictionary.getName();
		}
		return null;
	}

	/**
	 * 递归方法，查找组织下的所有的子组织
	 * 
	 * @param id
	 *            当前组织id
	 * @param childList
	 *            子组织列表
	 * @param dictionaryIds
	 *            允许操作的组织ID集合
	 * @param isAll
	 *            表示所有组织
	 */
	private static void iterativeSearchChildDataDictionary(Integer id, List<DataDictionary> childList) {
		Iterator<DataDictionary> it = getSortedAllDataDictionary().iterator();
		while (it.hasNext()) {
			DataDictionary dictionary = (DataDictionary) it.next();
			if (dictionary.getParentId().intValue() == id.intValue()) {
				childList.add(dictionary);
				// 如果是叶节点则不再递归
				if (dictionary.getIsLeaf().intValue() != 1) {
					iterativeSearchChildDataDictionary(dictionary.getId(), childList);
				}
			}
		}
	}

	/**
	 * 获取一个组织的下属所有组织,包括当前组织
	 * 
	 * @param parentId
	 * @return
	 */
	private static List<DataDictionary> getAllChildDataDictionary(Integer parentId) {
		if (parentId == null) {
			return null;
		}

		List dictionaryList = new ArrayList();
		// 把当前组织也添加到列表中
		DataDictionary parent = get(parentId);
		if (parent == null) {
			return null;
		}
		dictionaryList.add(parent);
		// 调用递归方法查询组织的子组织
		iterativeSearchChildDataDictionary(parentId, dictionaryList);

		return dictionaryList;
	}

	/**
	 * 获取一个组织的下属的可得组织,包括当前组织
	 * 
	 * @param id
	 *            当前组织id
	 * @param dictionaryIds
	 *            允许操作的组织ID集合
	 * @return
	 */
	private static List<DataDictionary> getAvailableChildDataDictionary(Integer id) {
		if (id == null) {
			return null;// 没有权限
		}

		List<DataDictionary> dictionaryList = new ArrayList();
		DataDictionary dataDictionary = get(id);
		if (dataDictionary == null) {
			return null;
		}

		dictionaryList.add(dataDictionary);
		// 调用递归方法查询组织的子组织
		iterativeSearchChildDataDictionary(id, dictionaryList);

		return dictionaryList;
	}

	/**
	 * 获取整个系统的组织id字符串，不进行权限判断。
	 * 
	 * @return 返回所有组织的id字符串，如：“1001,1002,1003”，英文逗号分割
	 */
	public static String getAllDataDictionaryIdsStr() {
		String dictionaryIds = "";
		StringBuffer dictionaryIdsBuff = new StringBuffer();

		Iterator<DataDictionary> it = getSortedAllDataDictionary().iterator();
		while (it.hasNext()) {
			DataDictionary dictionary = (DataDictionary) it.next();
			dictionaryIdsBuff.append(dictionary.getId()).append(",");
		}
		if (dictionaryIdsBuff.length() > 0) {
			dictionaryIds = dictionaryIdsBuff.substring(0, dictionaryIdsBuff.length() - 1);
		}

		return dictionaryIds;
	}

	/**
	 * 获取一个组织的下属所有组织的id字符串,包括当前组织的id
	 * 
	 * @param roleId
	 * @return 返回所有子组织的id字符串，如：“1001,1002,1003”，英文逗号分割
	 */
	public static String getChildrenDataDictionaryIdsStr(Integer dictionaryId) {
		String dictionaryIds = null;
		List<DataDictionary> dictionaryList = getAllChildDataDictionary(dictionaryId);
		if (dictionaryList == null) {
			logger.warn("没有获取到任何组织ID，dictionaryId=" + dictionaryId);
			return null;
		}
		StringBuffer dictionaryIdsBuff = new StringBuffer();
		for (DataDictionary dictionary : dictionaryList) {
			dictionaryIdsBuff.append(dictionary.getId()).append(",");
		}
		if (dictionaryIdsBuff.length() > 0) {
			dictionaryIds = dictionaryIdsBuff.substring(0, dictionaryIdsBuff.length() - 1);
		}
		return dictionaryIds;
	}

	/**
	 * 获取一个组织下属的所有可得组织的id字符串,包括当前组织的id
	 * 
	 * @param roleId  暂时没有用到该字段，可以传null。
	 * @param dictionaryId
	 * @return 返回所有子组织的id字符串，如：“1001,1002,1003”，英文逗号分割
	 */
	public static String getAvailableChildrenDataDictionaryIdsStr(Integer roleId, Integer dictionaryId) {
		List<DataDictionary> dictionaryList = getAvailableChildDataDictionary(dictionaryId);
		if(dictionaryList == null){
			return null;
		}
		StringBuffer dictionaryIdsBuff = new StringBuffer();
		for (DataDictionary dictionary : dictionaryList) {
			dictionaryIdsBuff.append(dictionary.getId()).append(",");
		}
		
		String availableChildrenDataDictionaryIds = "";
		if (dictionaryIdsBuff.length() > 0) {
			availableChildrenDataDictionaryIds = dictionaryIdsBuff.substring(0, dictionaryIdsBuff.length() - 1);
		}
		return availableChildrenDataDictionaryIds;
	}
	
	/**
	 * 获取一个组织下属的所有可得组织的id字符串,包括当前组织的id
	 * 
	 * @param roleId  暂时没有用到该字段，可以传null。
	 * @param dictionaryId
	 * @return 返回所有子组织的id整型数组
	 */
	public static int[] getAvailableChildrenDataDictionaryIds(Integer roleId, Integer dictionaryId) {
		List<DataDictionary> dictionaryList = getAvailableChildDataDictionary(dictionaryId);
		int[] userIds = new int[dictionaryList.size()];
		for (int i=0; i<dictionaryList.size(); i++) {
			DataDictionary dictionary = dictionaryList.get(i);
			userIds[i] = dictionary.getId();
		}
		return userIds;
	}

	/**
	 * 递归函数，获得树形结构
	 * 
	 * @param dictionary
	 * @param strBuff
	 * @param dictionaryIds
	 *            用了进行权限过滤的组织ID集合,逗号分隔
	 * @param isAll
	 *            表示所有组织
	 */
	private static void iterativeDataDictionaryTree4Xml(DataDictionary dictionary, StringBuffer strBuff) {
		if (dictionary == null) {
			return;// 没有权限
		}

		// 如果是叶节点
		if (dictionary.getIsLeaf().intValue() == 1) {
			strBuff.append("<item text='").append(dictionary.getName()).append("' id='").append(dictionary.getId()).append("' im0='book.gif' im1='books_open.gif' im2='books_close.gif'/>");
		} else {
			// 不是叶节点
			strBuff.append("<item text='").append(dictionary.getName()).append("' id='").append(dictionary.getId()).append("' open='1' im0='books_close.gif' im1='tombs.gif' im2='tombs.gif' call='1'>");

			List<DataDictionary> dictionaryList = getAvailableChildDataDictionary(dictionary.getId());
			if (dictionaryList != null && dictionaryList.size() > 0) {
				for (DataDictionary o : dictionaryList) {
					if (o.getParentId().intValue() == dictionary.getId().intValue()) {
						iterativeDataDictionaryTree4Xml(o, strBuff);
					}
				}
			}
			strBuff.append("</item>");
		}
	}

	  /**
     * 获取一个字典的的树形组织
     * @param id
     * @return 树形结构字符串
     */ 
	public static String getChildrenDataDictionaryTree4Xml(Integer id) {
		String dictionaryXml = dictionaryIdAndDataDictionary4XmlCache.get(id);
		if (MyStringUtils.isBlank(dictionaryXml)) {

			DataDictionary dataDictionary = get(id);

			StringBuffer currDataDictionaryBuff = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?><tree id='0'>");
			iterativeDataDictionaryTree4Xml(dataDictionary, currDataDictionaryBuff);
			currDataDictionaryBuff.append("</tree>");

			dictionaryXml = currDataDictionaryBuff.toString();
			dictionaryIdAndDataDictionary4XmlCache.put(id, dictionaryXml);
		}

		return dictionaryXml;
	}
	
    /**
     * 获取一个字典的的树形组织
     * @param listDictionary
     * @return 树形结构字符串
     */ 
	public static String getChildrenDataDictionaryTree4Xml(List listDictionary) {
		String dictionaryXml = "";
		if (listDictionary != null && listDictionary.size() > 0) {
			for (int i = 0; i<listDictionary.size(); i++) {
				DataDictionary currentDataDictionary = (DataDictionary) listDictionary.get(i);
				dictionaryXml = dictionaryIdAndDataDictionary4XmlCache.get(currentDataDictionary.getId());
			 }
		}
		
		if (MyStringUtils.isBlank(dictionaryXml)) {
			StringBuffer currDataDictionaryBuff = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?><tree id='0'>");
			if (listDictionary != null && listDictionary.size() > 0) {
				for (int i = 0; i<listDictionary.size(); i++) {
					DataDictionary currentDataDictionary = (DataDictionary) listDictionary.get(i);
					DataDictionary dataDictionary = get(currentDataDictionary.getId());
					iterativeDataDictionaryTree4Xml(dataDictionary, currDataDictionaryBuff);
				}
				
			}
			
			currDataDictionaryBuff.append("</tree>");
			
			dictionaryXml = currDataDictionaryBuff.toString();
			if (listDictionary != null && listDictionary.size() > 0) {
				for (int i = 0; i<listDictionary.size(); i++) {
					DataDictionary currentDataDictionary = (DataDictionary) listDictionary.get(i);
					dictionaryIdAndDataDictionary4XmlCache.put(currentDataDictionary.getId(), dictionaryXml);
				 }
			}
				
		}
		
		return dictionaryXml;
	}

	/**
	 * 递归函数，递归生成下拉列表框使用的组织所有下级组织列表
	 * 
	 * @param dictionary
	 *            当前递归到的组织
	 * @param level
	 *            第几级节点
	 * @param dictionaryList
	 *            所有组织列表
	 */
	private static void iterativeDataDictionaryTree4Select(DataDictionary dictionary, int level, List<DataDictionary> dictionaryList, String dictionaryIds, boolean isAll) {
		if (dictionary == null) {
			return;// 没有权限
		}

		// 如果当前节点不是叶节点
		if (dictionary.getIsLeaf().intValue() != 1) {
			List<DataDictionary> dictionarys = null;
			if (isAll) {
				dictionarys = getAllChildDataDictionary(dictionary.getId());
			} else {
				dictionarys = getAvailableChildDataDictionary(dictionary.getId());
			}
			if (dictionarys != null && dictionarys.size() > 0) {
				for (DataDictionary o : dictionarys) {
					int tempLevel = level;
					if (o.getParentId().intValue() == dictionary.getId().intValue()) {
						// 进行权限判断
						if (dictionaryIds.contains(o.getId().toString())) {// 有权限
						} else {// 无权限
							return;
						}
						// 计算空格数
						StringBuffer strBuff = new StringBuffer();
						for (int i = 0; i < level; i++) {
							strBuff.append("&nbsp;&nbsp;&nbsp;&nbsp;");
						}

						// 在组织名称前加上“|--”符号
						o.setName(strBuff.toString() + "|--" + o.getName());
						dictionaryList.add(o);
						// 递归调用
						iterativeDataDictionaryTree4Select(o, tempLevel + 1, dictionaryList, dictionaryIds, isAll);
					}
				}
			}
		}
	}

}

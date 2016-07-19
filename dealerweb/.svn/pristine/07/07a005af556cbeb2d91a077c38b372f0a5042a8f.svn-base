package com.ava.resource.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ava.dao.IVehicleDao;
import com.ava.domain.entity.Org;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.VehicleMonitorVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SpringContext;
import com.ava.util.MyStringUtils;

/**组织和叶子节点下的车辆组成的树的缓存 */
public class CacheOrgVehicleManager {

	public static final Log logger = LogFactory.getLog(CacheOrgVehicleManager.class);

	/** 某个组织ID和其对应的所有可用组织及其叶子节点组织所属车辆的缓存-xml格式 */
	private static Map<Integer, String> orgIdAndOrg4XmlCache = new HashMap<Integer, String>();


	private static IVehicleDao vehicleDao = SpringContext.getBean("vehicleDao");
	
	/**
	 * 递归函数，获得树形结构，并且叶子节点下面挂有车辆
	 * 
	 * @param org
	 * @param strBuff
	 * @param orgIds
	 *            用了进行权限过滤的组织ID集合,逗号分隔
	 * @param isAll
	 *            表示所有组织
	 */
	private static void iterativeOrgTree4Xml(Org org, StringBuffer strBuff, String orgIds, boolean isAll) {
		if (org == null) {
			return;// 没有权限
		}

		// 如果是叶节点
		if (org.getIsLeaf().intValue() == 1) {
			strBuff.append("<item text='").append(org.getName()).append("' id='").append(org.getId());
			strBuff.append("' open='1' im0='books_close.gif' im1='tombs.gif' im2='tombs.gif' call='1'>");
			//叶子组织所属车辆列表
			List vehicleList = vehicleDao.findByCompanyId(org.getId());
			if(vehicleList != null && vehicleList.size() > 0){
				for(int i=0; i<vehicleList.size(); i++){
					Vehicle vehicle = (Vehicle) vehicleList.get(i);
					//vehicleVO的treeId在vehicle.id的基础上加前缀“vehicle”，是为了保证树种节点id的唯一性
	        		VehicleMonitorVO vehicleVO = new VehicleMonitorVO(vehicle);
					strBuff.append("<item text='").append(vehicleVO.getPlateNumber()).append("' id='").append(vehicleVO.getNodeId());
					strBuff.append("' im0='book.gif' im1='books_open.gif' im2='books_close.gif'>");
					strBuff.append("<userdata name='isVehicle'>true</userdata>");
					strBuff.append("</item>");
				}
			}
			strBuff.append("</item>");
		} else {
			// 不是叶节点
			strBuff.append("<item text='").append(org.getName()).append("' id='").append(org.getId());
			strBuff.append("' open='1' im0='books_close.gif' im1='tombs.gif' im2='tombs.gif' call='1'>");
			List<Org> orgList = null;
			if (isAll) {
				orgList = CacheOrgManager.getAllChildOrg(org.getId(), GlobalConstant.FALSE);
			} else {
				orgList = CacheOrgManager.getAvailableChildOrg(org.getId(), orgIds);
			}
			if (orgList != null && orgList.size() > 0) {
				for (Org o : orgList) {
					if (o.getParentId().intValue() == org.getId().intValue()) {
						iterativeOrgTree4Xml(o, strBuff, orgIds, isAll);
					}
				}
			}
			strBuff.append("</item>");
		}
	}

	/**
	 * 获取一个组织的树形组织，并且叶子节点下面挂有车辆
	 * 
	 * @param roleId  暂时没有用到该字段，可以传null。
	 * @param orgId
	 * @return 树形结构字符串
	 */
	public static String getChildrenOrgTree4Xml(Integer roleId, Integer orgId) {
		String orgXml = orgIdAndOrg4XmlCache.get(orgId);
		if (MyStringUtils.isBlank(orgXml)) {

			String orgIds = CacheOrgManager.getAvailableChildrenOrgIdsStr(roleId, orgId);
			Org org = CacheOrgManager.get(orgId);//CacheOrgManager.get(role.getUserId());
			boolean isAll = true;//(role.getIsAllChildren() != null && role.getIsAllChildren().intValue() == 1) ? true : false;

			StringBuffer currOrgBuff = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?><tree id='0'>");
			iterativeOrgTree4Xml(org, currOrgBuff, orgIds, isAll);
			currOrgBuff.append("</tree>");

			orgXml = currOrgBuff.toString();
			orgIdAndOrg4XmlCache.put(orgId, orgXml);
		}

		return orgXml;
	}
	
	public static void clear() {
		orgIdAndOrg4XmlCache.clear();
	}

}

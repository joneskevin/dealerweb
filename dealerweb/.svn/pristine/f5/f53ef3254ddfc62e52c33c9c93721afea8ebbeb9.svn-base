package com.ava.dealer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.dao.IDriveLineDao;
import com.ava.dao.IFenceDao;
import com.ava.dealer.service.IDriveLineService;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.DriveLine;
import com.ava.domain.entity.Fence;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.DealerVO;
import com.ava.domain.vo.DriveLineFindVO;
import com.ava.domain.vo.DriveLineVO;
import com.ava.domain.vo.DriveLineWithFenceVO;
import com.ava.domain.vo.DriveLineWithPolygonVO;
import com.ava.domain.vo.LocationVO;
import com.ava.gateway.gpsUtil.GPSPoint;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheDriveLineManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.resource.cache.CacheTestDriveManager;
import com.ava.resource.cache.CacheVehicleManager;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;
import com.ava.util.gis.GisUtil;
import com.vividsolutions.jts.geom.Geometry;

@Service
public class DriveLineService extends BaseService implements IDriveLineService {
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDriveLineDao driveLineDao;
	
	@Autowired
	private IFenceDao fenceDao;

	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData addDriveLine(DriveLine driveLine) {
		ResponseData rd = new ResponseData(GlobalConstant.TRUE);
		
		Integer type = GlobalConstant.DRIVE_LINE_TYPE_TEST_DRIVE;
		String fillColor = "", strokeColor = "";
		if (driveLine.getName().equals("A线路")) {
			fillColor = GlobalConstant.TEST_DRIVE_A_FILL_COLOR;
			strokeColor = GlobalConstant.TEST_DRIVE_A_STROKE_COLOR;
			
		} else if (driveLine.getName().equals("B线路")) {
			fillColor = GlobalConstant.TEST_DRIVE_B_FILL_COLOR;
			strokeColor = GlobalConstant.TEST_DRIVE_B_STROKE_COLOR;
			
		} else if (driveLine.getName().equals("D线路")) {
			fillColor = GlobalConstant.TEST_DRIVE_D_FILL_COLOR;
			strokeColor = GlobalConstant.TEST_DRIVE_D_STROKE_COLOR;
			
		} else if (driveLine.getName().equals("E线路")) {
			fillColor = GlobalConstant.TEST_DRIVE_E_FILL_COLOR;
			strokeColor = GlobalConstant.TEST_DRIVE_E_STROKE_COLOR;
			
		} else if (driveLine.getName().equals("F线路")) {
			fillColor = GlobalConstant.TEST_DRIVE_F_FILL_COLOR;
			strokeColor = GlobalConstant.TEST_DRIVE_F_STROKE_COLOR;
			
		} else {
			type = GlobalConstant.DRIVE_LINE_TYPE_OIL;
			fillColor = GlobalConstant.OIL_FILL_COLOR;
			strokeColor = GlobalConstant.OIL_STROKE_COLOR;
			
		}
		driveLine.setType(type);
		driveLine.setFillColor(fillColor);
		driveLine.setStrokeColor(strokeColor);
		
		List<DriveLine> anotherDriveLineList = driveLineDao.getDriveLineByDealerId(driveLine.getCompanyId());
		if(anotherDriveLineList != null &&anotherDriveLineList.size()>0){
			int testDriveLineNum=0;//试驾线路条数
			if(anotherDriveLineList.size() >= 6){
				rd.setCode(-1);
				rd.setMessage("该经销商线路已配置完成");
				return rd;
			}
			
			for(DriveLine dl:anotherDriveLineList){
				if(null!=dl.getName() && null!=driveLine && dl.getName().trim().equals(driveLine.getName().trim())){
					rd.setCode(-1);
					rd.setMessage("该经销商已经存在有相同名称的线路");
					return rd;
				}
				if (dl.getType() == GlobalConstant.DRIVE_LINE_TYPE_OIL && driveLine.getType() == GlobalConstant.DRIVE_LINE_TYPE_OIL) {
					rd.setCode(-1);
					rd.setMessage("该经销商已经存在加油线路");
					return rd;
				}
				if (dl.getType() == GlobalConstant.DRIVE_LINE_TYPE_TEST_DRIVE && driveLine.getType() == GlobalConstant.DRIVE_LINE_TYPE_TEST_DRIVE) {
					testDriveLineNum ++;
				}
				
			}
			
			if (testDriveLineNum >= 5) {
				rd.setCode(-1);
				rd.setMessage("该经销商已经有五条试驾线路");
				return rd;
			}
		}
		
		driveLine.setCreationTime(DateTime.getTimestamp());
		driveLine.setModifyTime(DateTime.getTimestamp());
		driveLineDao.save(driveLine);

		rd.setMessage("新增线路成功");
		rd.setFirstItem(driveLine);
		return rd;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void updateSemcycle(DriveLineVO driveLineVO) {
		
		DriveLine driveLine = new DriveLine();
		MyBeanUtils.copyPropertiesContainsDate(driveLine, driveLineVO);
		driveLineDao.update(driveLine);
		CacheDriveLineManager.removeDriveLineById(driveLine.getId());
		CacheTestDriveManager.removeDriveLineWithPolygon(driveLine.getCompanyId());
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData deleteDriveLine(Integer driveLineId) {
		ResponseData rd = new ResponseData(0);

		DriveLine dbDriveLine = driveLineDao.get(driveLineId);
		if (dbDriveLine == null) {
			rd.setCode(-1);
			rd.setMessage("线路对象为空！");
			return rd;
		}

		driveLineDao.delete(driveLineId);

		JSONArray jsonArray = driveLineDao.toJSONArray(dbDriveLine.getJsonContent(), "fenceIds");
		if (jsonArray != null) {
			if (jsonArray.size() > 0) {
				Integer fenceId = jsonArray.getInt(0);
				Fence fence = fenceDao.get(fenceId);
				if (fence != null) {
					fenceDao.delete(fenceId);
				}
			}
			if (jsonArray.size() > 1) {
				Integer fenceId = jsonArray.getInt(1);
				Fence fence = fenceDao.get(fenceId);
				if (fence != null) {
					fenceDao.delete(fenceId);
				}
			}
		}
		
		CacheDriveLineManager.removeDriveLineById(dbDriveLine.getId());
		CacheTestDriveManager.removeDriveLineWithPolygon(dbDriveLine.getCompanyId());
		
		rd.setCode(1);
		rd.setMessage("线路删除成功！");
		return rd;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData editDriveLine(DriveLine frmDriveLine) {
		ResponseData rd = new ResponseData(0);
		Integer type = GlobalConstant.DRIVE_LINE_TYPE_TEST_DRIVE;
		Integer fenceType = frmDriveLine.getFenceType();//围栏类型
		String fillColor = "", strokeColor = "";
		if (frmDriveLine.getName().equals("A线路")) {
			fillColor = GlobalConstant.TEST_DRIVE_A_FILL_COLOR;
			strokeColor = GlobalConstant.TEST_DRIVE_A_STROKE_COLOR;
			
		} else if (frmDriveLine.getName().equals("B线路")) {
			fillColor = GlobalConstant.TEST_DRIVE_B_FILL_COLOR;
			strokeColor = GlobalConstant.TEST_DRIVE_B_STROKE_COLOR;
			
		} else if (frmDriveLine.getName().equals("D线路")) {
			fillColor = GlobalConstant.TEST_DRIVE_D_FILL_COLOR;
			strokeColor = GlobalConstant.TEST_DRIVE_D_STROKE_COLOR;
			
		} else if (frmDriveLine.getName().equals("E线路")) {
			fillColor = GlobalConstant.TEST_DRIVE_E_FILL_COLOR;
			strokeColor = GlobalConstant.TEST_DRIVE_E_STROKE_COLOR;
			
		} else if (frmDriveLine.getName().equals("F线路")) {
			fillColor = GlobalConstant.TEST_DRIVE_F_FILL_COLOR;
			strokeColor = GlobalConstant.TEST_DRIVE_F_STROKE_COLOR;
			
		} else {
			type = GlobalConstant.DRIVE_LINE_TYPE_OIL;
			fillColor = GlobalConstant.OIL_FILL_COLOR;
			strokeColor = GlobalConstant.OIL_STROKE_COLOR;
			
		}
		frmDriveLine.setType(type);
		frmDriveLine.setFillColor(fillColor);
		frmDriveLine.setStrokeColor(strokeColor);
		
		Integer driveLineId = frmDriveLine.getId();
		DriveLine dbDriveLine = driveLineDao.get(driveLineId);
		if (frmDriveLine.getName() !=null && !(frmDriveLine.getName().equalsIgnoreCase(dbDriveLine.getName()))) {
			if (this.nameIsExistence(dbDriveLine.getCompanyId(), frmDriveLine.getName())) {
				rd.setCode(-1);
				rd.setMessage("该经销商已经存在有相同名称的线路");
				return rd;
			}
		}
		
		List<DriveLine> anotherDriveLineList = driveLineDao.getDriveLineByDealerId(frmDriveLine.getCompanyId());
		if(anotherDriveLineList != null &&anotherDriveLineList.size()>0){ 
			int addOilLineNum=0;//加油路线条数
			int testDriveLineNum=0;//试驾线路条数
			for(DriveLine dl:anotherDriveLineList){
				if (dl.getType() == GlobalConstant.DRIVE_LINE_TYPE_OIL ) {
					addOilLineNum ++;
				}
				if (dl.getType() == GlobalConstant.DRIVE_LINE_TYPE_TEST_DRIVE) {
					testDriveLineNum ++;
				}
				if (dl.getId() == frmDriveLine.getId().intValue() && dl.getType().intValue() != frmDriveLine.getType()) {
					addOilLineNum ++;
					testDriveLineNum ++;
				}
			}
			if (addOilLineNum > 1 && frmDriveLine.getType() == GlobalConstant.DRIVE_LINE_TYPE_OIL) {
				rd.setCode(-1);
				rd.setMessage("该经销商已经有一条加油线路");
				return rd;
			}
			if (testDriveLineNum > 5 && frmDriveLine.getType() == GlobalConstant.DRIVE_LINE_TYPE_TEST_DRIVE) {
				rd.setCode(-1);
				rd.setMessage("该经销商已经有两条试驾线路");
				return rd;
			}
		}
		
		//如果双围栏改成单围栏时，则要删掉内围栏
		if (fenceType == GlobalConstant.DRIVE_LINE_FENCE_TYPE_SINGLE && dbDriveLine.getFenceType() == GlobalConstant.DRIVE_LINE_FENCE_TYPE_DOUBLE) {
			JSONArray jsonArray = driveLineDao.toJSONArray(dbDriveLine.getJsonContent(), "fenceIds");
			if (jsonArray != null) {
				if (jsonArray.size() > 0) {
					Integer fenceId = jsonArray.getInt(0);
					if (fenceId > 0) {
						String[] fenceIds = new String[1];
						fenceIds[0] = fenceId.toString();
						//只保留外围栏
					    dbDriveLine.setJsonContent("{fenceIds:" + MyBeanUtils.toJson(fenceIds) + "}");
						
					}
				}
				if (jsonArray.size() > 1) {
					Integer fenceId = jsonArray.getInt(1);
					if (fenceId > 0) {
						fenceDao.delete(fenceId);
					}
				}
			}
		}
		
		MyBeanUtils.copyPropertiesContainsDate(dbDriveLine, frmDriveLine);
		dbDriveLine.setModifyTime(DateTime.getTimestamp());
		driveLineDao.update(dbDriveLine);
		CacheDriveLineManager.removeDriveLineById(driveLineId);
		CacheTestDriveManager.removeDriveLineWithPolygon(dbDriveLine.getCompanyId());

		rd.setFirstItem(dbDriveLine);
		rd.setCode(1);
		rd.setMessage("编辑线路成功");
		return rd;
	}
	
	public boolean nameIsExistence(Integer companyId, String name) {
		if (name == null || name.length() <= 0) {
			return false;
		}
		HashMap parameters = new HashMap();
		parameters.put("name", name);
		parameters.put("companyId", companyId);
		List result = driveLineDao.find(parameters, "");
		if (result != null && result.size() > 0) {
			return true;
		}
		return false;
	}
	

	public ResponseData setFence(DriveLineVO frmDriveLine) {
		ResponseData rd = new ResponseData(0);

		Integer driveLineId = frmDriveLine.getId();
		DriveLine dbDriveLine = driveLineDao.get(driveLineId);
		
		JSONArray jsonArray = driveLineDao.toJSONArray(dbDriveLine.getJsonContent(), "fenceIds");
		if (jsonArray != null) {
			if (jsonArray.size() > 0) {
				Integer fenceId = jsonArray.getInt(0);
				if (fenceId > 0) {
					fenceDao.delete(fenceId);
				}
			}
			if (jsonArray.size() > 1) {
				Integer fenceId = jsonArray.getInt(1);
				if (fenceId > 0) {
					fenceDao.delete(fenceId);
				}
			}
		}
		
		Integer companyId = dbDriveLine.getCompanyId();
		Integer mileage = dbDriveLine.getMileage();
		MyBeanUtils.copyPropertiesContainsDate(dbDriveLine, frmDriveLine);
		dbDriveLine.setMileage(mileage);

		String[] fenceIds = null;
		if (dbDriveLine.getFenceType().intValue() == GlobalConstant.DRIVE_LINE_FENCE_TYPE_SINGLE) {
			fenceIds = new String[1];

			Integer fenceId = buildAndSaveFence(companyId,
					"[" + dbDriveLine.getName() + "]的围栏",
					GlobalConstant.BAN_TYPE_OUT, dbDriveLine.getJsonPolygon1());
			fenceIds[0] = fenceId.toString();
		} else {
			fenceIds = new String[2];

			Integer fenceId = buildAndSaveFence(companyId,
					"[" + dbDriveLine.getName() + "]的围栏",
					GlobalConstant.BAN_TYPE_IN, dbDriveLine.getJsonPolygon1());
			fenceIds[0] = fenceId.toString();

			fenceId = buildAndSaveFence(companyId, "[" + dbDriveLine.getName()
					+ "]的围栏", GlobalConstant.BAN_TYPE_OUT,
					dbDriveLine.getJsonPolygon2());
			fenceIds[1] = fenceId.toString();
		}
		dbDriveLine.setJsonContent("{fenceIds:" + MyBeanUtils.toJson(fenceIds)
				+ "}");

		dbDriveLine.setModifyTime(DateTime.getTimestamp());
		driveLineDao.update(dbDriveLine);
		CacheDriveLineManager.removeDriveLineById(driveLineId);
		CacheTestDriveManager.removeDriveLineWithPolygon(dbDriveLine.getCompanyId());

		rd.setFirstItem(dbDriveLine);
		rd.setCode(1);
		rd.setMessage("配置围栏成功");
		return rd;
	}
	
	public ResponseData setReferenceLine(DriveLineVO frmDriveLine) {
		ResponseData rd = new ResponseData(0);
		
		Integer driveLineId = frmDriveLine.getId();
		DriveLine dbDriveLine = driveLineDao.get(driveLineId);	
		
		JSONArray jsonArray = driveLineDao.toJSONArray(dbDriveLine.getJsonReferenceContent(), "fenceIds");
		if (jsonArray != null && jsonArray.size() > 0) {
			Integer fenceId = jsonArray.getInt(0);
			if (fenceId > 0) {
				fenceDao.delete(fenceId);
			}
		}
		
		MyBeanUtils.copyPropertiesContainsDate(dbDriveLine, frmDriveLine);
		
		String[] fenceIds = new String[1];
		Integer fenceId = buildAndSaveFence(dbDriveLine.getCompanyId(),
				"[" + dbDriveLine.getName() + "]的参考线路",
				GlobalConstant.BAN_TYPE_OUT, dbDriveLine.getJsonPolygon1());
		fenceIds[0] = fenceId.toString();
		dbDriveLine.setJsonReferenceContent("{fenceIds:" + MyBeanUtils.toJson(fenceIds) + "}");
		
		dbDriveLine.setModifyTime(DateTime.getTimestamp());
		driveLineDao.update(dbDriveLine);
		CacheDriveLineManager.removeDriveLineById(driveLineId);
		CacheTestDriveManager.removeDriveLineWithPolygon(dbDriveLine.getCompanyId());
		
		rd.setFirstItem(dbDriveLine);
		rd.setCode(1);
		rd.setMessage("参考线路设置成功");
		return rd;
	}

	private Integer buildAndSaveFence(Integer companyId, String fenceName,
			int banType, String jsonPolygon) {
		Fence fence = new Fence();
		fence.setBanType(banType);
		fence.setCompanyId(companyId);
		fence.setCreationTime(DateTime.getTimestamp());
		fence.setJsonData(jsonPolygon);
		fence.setName("[" + fenceName + "]的围栏");
		fence.setType(GlobalConstant.FENCE_TYPE_POLYGON);

		Integer fenceId = fenceDao.save(fence);
		return fenceId;
	}

	public DriveLineVO getDriveLine(Integer driveLineId) {
		DriveLine driveLine = driveLineDao.get(driveLineId);
		if (driveLine == null) {
			return null;
		}
		DriveLineVO driveLineVO = new DriveLineVO(driveLine);
		Company company = CacheCompanyManager.getCompany(driveLineVO.getCompanyId());
		DealerVO dealerVO = new DealerVO(company);
		driveLineVO.setDealerVO(dealerVO);

		JSONArray jsonArray = driveLineDao.toJSONArray(driveLineVO.getJsonContent(), "fenceIds");
		if (jsonArray != null) {
			if (jsonArray.size() > 0) {
				Integer fenceId = jsonArray.getInt(0);
				Fence fence = fenceDao.get(fenceId);
				if (fence != null) {
					driveLineVO.setJsonPolygon1(fence.getJsonData());
				}
			}
			if (jsonArray.size() > 1) {
				Integer fenceId = jsonArray.getInt(1);
				Fence fence = fenceDao.get(fenceId);
				if (fence != null) {
					driveLineVO.setJsonPolygon2(fence.getJsonData());
				}
			}
		}
		
		//参考线路JSON
		JSONArray jsonReferenceArray = driveLineDao.toJSONArray(driveLineVO.getJsonReferenceContent(), "fenceIds");
		if (jsonReferenceArray != null) {
			if (jsonReferenceArray.size() > 0) {
				Integer fenceId = jsonReferenceArray.getInt(0);
				Fence fence = fenceDao.get(fenceId);
				if (fence != null) {
					driveLineVO.setJsonReferencePolygon(fence.getJsonData());
				}
			}
		}

		return driveLineVO;
	}
	
	public DriveLineVO getReferenceLine(Integer driveLineId) {
		DriveLine driveLine = driveLineDao.get(driveLineId);
		if (driveLine == null) {
			return null;
		}
		
		DriveLineVO driveLineVO = new DriveLineVO(driveLine);
		Company company = CacheCompanyManager.getCompany(driveLineVO.getCompanyId());
		DealerVO dealerVO = new DealerVO(company);
		driveLineVO.setDealerVO(dealerVO);
		
		JSONArray jsonArray = driveLineDao.toJSONArray(driveLineVO.getJsonReferenceContent(), "fenceIds");
		if (jsonArray != null) {
			if (jsonArray.size() > 0) {
				Integer fenceId = jsonArray.getInt(0);
				Fence fence = fenceDao.get(fenceId);
				if (fence != null) {
					driveLineVO.setJsonPolygon1(fence.getJsonData());
					driveLineVO.setJsonPolygon1(fence.getJsonData());
				}
			}
		}
		
		return driveLineVO;
	}
	
	public ResponseData displayHome(Integer companyId, Integer userId) {
		ResponseData rd = new ResponseData(0);
		String newXML = CacheOrgManager.getChildrenOrgTree4Xml(null, companyId);
		rd.put("dhtmlXtreeXML", newXML);

		return rd;
	}

	public ResponseData listDriveLine(TransMsg transMsg, DriveLineVO driveLineVO, Integer currentCompanyId, Integer userId, Boolean isExport) {
		ResponseData rd = new ResponseData(0);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) ");
		
		//查询经销商信息
		exeSql.append(getDealerSql());
		exeSql.append(" dl.id, dl.name, dl.mileage, dl.CREATION_TIME as creationTime, dl.MODIFY_TIME as modifyTime, dl.TYPE as type, dl.FENCE_TYPE as fenceType, ");
		exeSql.append(" (CASE dl.TYPE ");
		exeSql.append(" WHEN ").append( GlobalConstant.DRIVE_LINE_TYPE_OIL).append(" THEN '加油' ");
		exeSql.append(" WHEN ").append(GlobalConstant.DRIVE_LINE_TYPE_TEST_DRIVE).append(" THEN '试驾' ");
		exeSql.append(" END) as typeNick, ");
		exeSql.append(" (CASE dl.FENCE_TYPE ");
		exeSql.append(" WHEN ").append( GlobalConstant.DRIVE_LINE_FENCE_TYPE_SINGLE).append(" THEN '单围栏' ");
		exeSql.append(" WHEN ").append(GlobalConstant.DRIVE_LINE_FENCE_TYPE_DOUBLE).append(" THEN '双围栏' ");
		exeSql.append(" END) as fenceTypeNick ");
		exeSql.append(" FROM t_drive_line dl ");
		exeCountSql.append(" FROM t_drive_line dl ");
		
		exeSql.append(" INNER JOIN view_dealer_info vdi on vdi.companyId = dl.COMPANY_ID ");
		exeCountSql.append(" INNER JOIN view_dealer_for_count vdi on vdi.companyId = dl.COMPANY_ID ");
		
		exeSql.append(" WHERE 1 = 1 ");
		exeCountSql.append(" WHERE 1 = 1 ");
		
		if(driveLineVO.getType() != null && driveLineVO.getType().intValue() > 0){
			exeSql.append(" AND dl.type = ").append(driveLineVO.getType());
			exeCountSql.append(" AND dl.type = ").append(driveLineVO.getType());
		}
		
		if(driveLineVO.getFenceType() != null && driveLineVO.getFenceType().intValue() > 0){
			exeSql.append(" AND dl.FENCE_TYPE = ").append(driveLineVO.getFenceType());
			exeCountSql.append(" AND dl.FENCE_TYPE = ").append(driveLineVO.getFenceType());
		}
		
		if (driveLineVO.getName() != null && driveLineVO.getName().trim().length() > 0) {
			exeSql.append(" AND dl.name LIKE '%").append(driveLineVO.getName()).append("%' ");
			exeCountSql.append(" AND dl.name LIKE '%").append(driveLineVO.getName()).append("%' ");
		}
		
		//添加权限过滤条件
		exeSql.append(getAccessSql(currentCompanyId, userId));
		exeCountSql.append(getAccessSql(currentCompanyId, userId));
		//添加经销商查询条件
		exeSql.append(getDealerConditionSql(driveLineVO.getDealerVO(), userId));
		exeCountSql.append(getDealerConditionSql(driveLineVO.getDealerVO(), userId));
		//根据分销中心、网络代码排序
		exeSql.append(getOrderSql());
		exeSql.append(" , dl.MODIFY_TIME DESC ");
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		List<DriveLineFindVO> driveLineFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), DriveLineFindVO.class);
		rd.put("driveLineList", driveLineFindVOList);
		return rd; 
	}

	public List<DriveLineWithFenceVO> getDriveLineListByVehicleId(
			Integer vehicleId) {
		Vehicle vehicle = CacheVehicleManager.getVehicleById(vehicleId);
		if (vehicle == null) {
			return null;
		}
		Integer dealerId = vehicle.getCompanyId();

		List<DriveLineWithFenceVO> driveLineVOList = CacheDriveLineManager.getDriveLineVOsByDealerId(dealerId);

		return driveLineVOList;
	}
	
	public DriveLineWithPolygonVO getDriveLineWithPolygonListByDealerId(
			Integer dealerId) {
		DriveLineWithPolygonVO driveLineWithPolygon=new DriveLineWithPolygonVO();
		List<DriveLine> driveLineList=null;
		if(null!=dealerId && dealerId.intValue()>=1){
			driveLineList=driveLineDao.getDriveLineByDealerId(dealerId);
			if(null!=driveLineList){
				JSONArray jsonArray=null;
				DriveLineVO driveLineVO=null;
				int testLine=0;
				for(DriveLine driveLine:driveLineList){
					jsonArray = driveLineDao.toJSONArray(driveLine.getJsonContent(), "fenceIds");
					if (jsonArray != null) {
						driveLineVO=new DriveLineVO();
						driveLineVO.setId(driveLine.getId());
						driveLineVO.setMileage(driveLine.getMileage());
						driveLineVO.setType(driveLine.getType());
						driveLineVO.setSemicyclePoint(driveLine.getSemicyclePoint());
						if(null!=jsonArray && jsonArray.size()==1){
							Integer fenceId = jsonArray.getInt(0);
							Fence fence = fenceDao.get(fenceId);
							if(null!=fence && null!=fence.getJsonData() && fence.getJsonData().length()>=1){
								Geometry polygon=GisUtil.PolygonByFen(fence.getJsonData());
								driveLineVO.setOutPolygon(polygon);
								driveLineVO.setFenceType(1);
							}
						}else if(null!=jsonArray && jsonArray.size()>=2){
							Integer fenceId1 = jsonArray.getInt(0);
							Fence fence1 = fenceDao.get(fenceId1);
							Integer fenceId2 = jsonArray.getInt(1);
							Fence fence2 = fenceDao.get(fenceId2);
							if(null!=fence1 && null!=fence1.getJsonData() && fence1.getJsonData().length()>=1){
								Geometry polygon=GisUtil.PolygonByFen(fence1.getJsonData());
								driveLineVO.setOutPolygon(polygon);
							}
							if(null!=fence2 && null!=fence2.getJsonData() && fence2.getJsonData().length()>=1){
								Geometry polygon=GisUtil.PolygonByFen(fence2.getJsonData());
								driveLineVO.setInnerPolygon(polygon);
							}
							driveLineVO.setFenceType(2);
						}
						if(1==driveLine.getType().intValue()){
							driveLineWithPolygon.setOilLine(driveLineVO);
						}else{
							testLine++;
							if(testLine==1){
								driveLineWithPolygon.setFirstDriveLine(driveLineVO);
							}
							if(2==testLine){
								driveLineWithPolygon.setSecondDriveLine(driveLineVO);
							}
						}
					}
				}
			}
		}
		driveLineWithPolygon.setCompanyId(dealerId);
		return driveLineWithPolygon;
	}
	
	public DriveLineWithPolygonVO getDriveLineWithPolygonListByDealerIdNew(Integer dealerId) {
		DriveLineWithPolygonVO driveLineWithPolygon=new DriveLineWithPolygonVO();
		List<DriveLineVO> driveLineVOList=new ArrayList<DriveLineVO>();
		if(null!=dealerId && dealerId.intValue()>=1){
			List<DriveLine> driveLineList=new ArrayList<DriveLine>();
			driveLineList=driveLineDao.getDriveLineByDealerIdNew(dealerId);
			if(null!=driveLineList){
				JSONArray jsonArray=null;
				JSONArray refenceJsonArray=null;
				//int testLine=0;
				for(DriveLine driveLine:driveLineList){
					DriveLineVO driveLineVO=new DriveLineVO();
					driveLineVO.setId(driveLine.getId());
					driveLineVO.setCompanyId(driveLine.getCompanyId());
					driveLineVO.setMileage(driveLine.getMileage());
					driveLineVO.setType(driveLine.getType());
					driveLineVO.setFenceType(driveLine.getFenceType());
					jsonArray = driveLineDao.toJSONArray(driveLine.getJsonContent(), "fenceIds");
					refenceJsonArray=driveLineDao.toJSONArray(driveLine.getJsonReferenceContent(), "fenceIds");
					if(null!=refenceJsonArray){
						Integer fenceId = refenceJsonArray.getInt(0);
						Fence fence = fenceDao.get(fenceId);
						if(null!=fence && null!=fence.getJsonData() && fence.getJsonData().length()>=4){
							List<GPSPoint> gpsPoints=GisUtil.refencePolygonByFen(fence.getJsonData());
							driveLineVO.setJsonReferenceContent(driveLine.getJsonReferenceContent());
							driveLineVO.setReferenceLPoints(gpsPoints);
						}
					}
					if (null != jsonArray ) {
						driveLineVO.setSemicyclePoint(driveLine.getSemicyclePoint());
						if(null!=jsonArray && jsonArray.size()==1){
							Integer fenceId = jsonArray.getInt(0);
							Fence fence = fenceDao.get(fenceId);
							if(null!=fence && null!=fence.getJsonData() && fence.getJsonData().length()>=4){
								Geometry polygon=GisUtil.PolygonByFen(fence.getJsonData());
								driveLineVO.setOutPolygon(polygon);
								//driveLineVO.setFenceType(1);
							}
						}else if(null!=jsonArray && jsonArray.size()>=2){
							Integer fenceId1 = jsonArray.getInt(0);
							Fence fence1 = fenceDao.get(fenceId1);
							Integer fenceId2 = jsonArray.getInt(1);
							Fence fence2 = fenceDao.get(fenceId2);
							if(null!=fence1 && null!=fence1.getJsonData() && fence1.getJsonData().length()>=4){
								Geometry polygon=GisUtil.PolygonByFen(fence1.getJsonData());
								driveLineVO.setOutPolygon(polygon);
							}
							if(null!=fence2 && null!=fence2.getJsonData() && fence2.getJsonData().length()>=4){
								Geometry polygon=GisUtil.PolygonByFen(fence2.getJsonData());
								driveLineVO.setInnerPolygon(polygon);
							}
							//driveLineVO.setFenceType(2);
						}
//						if(1==driveLine.getType().intValue()){
//							driveLineWithPolygon.setOilLine(driveLineVO);
//						}else{
//							testLine++;
//							if(testLine==1){
//								driveLineWithPolygon.setFirstDriveLine(driveLineVO);
//							}
//							if(2==testLine){
//								driveLineWithPolygon.setSecondDriveLine(driveLineVO);
//							}
//						}
					}
					driveLineVOList.add(driveLineVO);
				}
			}
		}
		driveLineWithPolygon.setLines(driveLineVOList);
		driveLineWithPolygon.setCompanyId(dealerId);
		return driveLineWithPolygon;
	}
	
	/**
	 * 初始化线路里程
	 * @author liuq 
	 * @version 0.1
	 */
	@SuppressWarnings("unchecked")
	public void initDriveLineMileage(){
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		List<DriveLine> driveLineList = driveLineDao.find(parameters, "");
		if (driveLineList != null && driveLineList.size() > 0) {
			for (DriveLine driveLine : driveLineList) {
				//参考线路JSON
				JSONArray jsonReferenceArray = driveLineDao.toJSONArray(driveLine.getJsonReferenceContent(), "fenceIds");
				if (jsonReferenceArray != null) {
					if (jsonReferenceArray.size() > 0) {
						Integer fenceId = jsonReferenceArray.getInt(0);
						Fence fence = fenceDao.get(fenceId);
						if (fence != null) {
							//处理里程
							String jsonData = fence.getJsonData();
							jsonData = "{\"points\":" + jsonData + "}";
							Integer mileage = getLineMileage(jsonData);
							driveLine.setMileage(mileage);
							driveLineDao.update(driveLine);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 传入参考线，返回参考线的长度
	 * @author liuq 
	 * @version 0.1 
	 * @param jsonData
	 * @return 米
	 */
	private Integer getLineMileage(String jsonData) {
		Integer lineMileage = 0;
		double mileage = 0;
		JSONArray jsonDataArray = driveLineDao.toJSONArray(jsonData, "points");
		double lat1 = 0, lng1 = 0, lat2 = 0, lng2 = 0;
		if (jsonDataArray != null && jsonDataArray.size() >= 2) {
			 for (int i = 0; i < jsonDataArray.size(); i++) {
				 LocationVO locationVO = (LocationVO) JSONObject.toBean(jsonDataArray.getJSONObject(i), LocationVO.class);
				 if (i ==0 && locationVO != null)  {
					 lat1 = locationVO.getLat();
					 lng1 = locationVO.getLng();
				 }
				 
				 if (i < jsonDataArray.size() - 1)  {
					 locationVO = (LocationVO) JSONObject.toBean(jsonDataArray.getJSONObject(i + 1), LocationVO.class);
					 if (locationVO != null) {
						 lat2 = locationVO.getLat();
						 lng2 = locationVO.getLng();
						 if (lat1 > 0 && lng1 > 0 && lat2 > 0 && lng2 > 0) {
							 mileage += GisUtil.getDistance(lat1, lng1, lat2, lng2);
						 }
						 lat1 = locationVO.getLat();
						 lng1 = locationVO.getLng();
					 } 
				 }
			 }
		 }
		lineMileage = Integer.parseInt(String.valueOf(Math.round(mileage)));
		return lineMileage;
	}
	
}

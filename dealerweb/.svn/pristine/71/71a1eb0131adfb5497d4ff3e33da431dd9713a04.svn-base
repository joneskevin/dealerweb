package com.ava.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.dao.IVehicleDao;
import com.ava.domain.entity.Vehicle;
import com.ava.resource.GlobalConstant;

@Repository
public class VehicleDao implements IVehicleDao {
	@Autowired
	private IHibernateDao hibernateDao;

	public Integer save(Vehicle vehicle) {
		Integer objId = null;
		if (vehicle != null) {
			if (vehicle.getDeletionFlag() == null) {
				vehicle.setDeletionFlag(0);
			}
			if (vehicle.getConfigureStatus() == null) {
				vehicle.setConfigureStatus(GlobalConstant.CONFIGURE_STATUS_INIT);
			}
			if (vehicle.getIsTestDrive() == null) {
				vehicle.setIsTestDrive(GlobalConstant.TRUE);
			}

			try {
				objId = (Integer) hibernateDao.save(vehicle);
			} catch (Exception e) {
			}
		}

		return objId;
	}

	public void delete(Integer id) {
		hibernateDao.delete(Vehicle.class, id);
	}

	public void update(Vehicle vehicle) {
		hibernateDao.update(vehicle);
	}

	public List findNatively(Map parameters) {
		List<Object> objList = (List<Object>) hibernateDao.find("Vehicle",
				parameters);
		return objList;
	}

	public Vehicle get(Integer id) {
		if (id == null) {
			return null;
		}
		Vehicle org = (Vehicle) hibernateDao.get(Vehicle.class, id);
		return org;
	}

	public Vehicle getByVin(String vin) {
		if (vin == null || vin.length() < 1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("vin", vin);
		List objs = hibernateDao.find("Vehicle", parameters, "");
		if (objs != null && objs.size() > 0) {
			return (Vehicle) objs.get(0);
		}
		return null;
	}

	public Vehicle getByPalteNumber(String plateNumber) {
		if (plateNumber == null || plateNumber.length() < 1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("plateNumber", plateNumber);
		List objs = hibernateDao.find("Vehicle", parameters, "");
		if (objs != null && objs.size() > 0) {
			return (Vehicle) objs.get(0);
		}
		return null;
	}

	public List findByPlateNumber(String plateNumber, String additionalCondition) {
		if (plateNumber == null || plateNumber.length() < 1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("plateNumber", plateNumber);
		List objs = hibernateDao.find("Vehicle", parameters, additionalCondition);
		return objs;
	}

	public List findByCompanyId(Integer companyId) {
		if (companyId == null) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("companyId", companyId);
		List objs = hibernateDao.find("Vehicle", parameters, "");
		return objs;
	}

	public List find(HashMap parameters, String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.find("Vehicle",
				parameters, additionalCondition);
		return objList;
	}

	public List<Integer> findIds(HashMap parameters, String additionalCondition) {
		List<String> fields = new ArrayList<String>();
		fields.add("id");
		
		List objs = hibernateDao.find(fields, "Vehicle", parameters, additionalCondition);
		return objs;
	}

	public List<Integer> findIdsByPlateNumber(String plateNumber,
			String additionalCondition) {
		if (plateNumber == null) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("plateNumber", plateNumber);

		List<String> fields = new ArrayList<String>();
		fields.add("id");

		return hibernateDao.find(fields, "Vehicle", parameters, additionalCondition);
	}

	public List findByPage(TransMsg msg, String additionalCondition) {
		msg.put("deletionFlag", GlobalConstant.FALSE);
		List<Object> objList = (List<Object>) hibernateDao.findByPage("Vehicle",
				msg, additionalCondition);
		return objList;
	}
	
	public Vehicle getBySerialNumber(String serialNumber) {
		if (serialNumber == null || serialNumber.isEmpty()) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("obdNo", serialNumber);
		List objs = hibernateDao.find("Vehicle", parameters, "");
		if (objs != null && objs.size() > 0) {
			return (Vehicle) objs.get(0);
		}
		return null;
	}
	
}

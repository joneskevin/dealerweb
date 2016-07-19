package com.ava.facade.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ava.base.domain.ResponseData;
import com.ava.dao.IApprovalDao;
import com.ava.dao.IBoxDao;
import com.ava.dao.ICarStyleDao;
import com.ava.dao.ICompanyCarStyleRelationDao;
import com.ava.dao.IProposalDao;
import com.ava.dao.IVehicleDao;
import com.ava.dealer.service.IBoxOperationService;
import com.ava.dealer.service.IBoxService;
import com.ava.dealer.service.IProposalService;
import com.ava.dealer.service.IVehicleService;
import com.ava.domain.entity.Approval;
import com.ava.domain.entity.Box;
import com.ava.domain.entity.BoxOperation;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.CompanyCarStyleRelation;
import com.ava.domain.entity.Proposal;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.ApprovalVO;
import com.ava.domain.vo.BoxOperationVO;
import com.ava.domain.vo.BoxVO;
import com.ava.domain.vo.ProposalCompanyVehcileInfo;
import com.ava.domain.vo.ProposalVO;
import com.ava.domain.vo.VehicleVO;
import com.ava.facade.IPoposalFacade;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheCarManager;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheUserManager;
import com.ava.resource.cache.CacheVehicleManager;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;
import com.ava.util.upload.UploadHelper;

@Service
public class ProposalFacade implements IPoposalFacade {
	
	@Autowired
	private ICompanyCarStyleRelationDao companyCarStyleRelationDao;
	
	@Autowired
	private IBoxDao boxDao;
	
	@Autowired
	private ICarStyleDao carStyleDao;
	
	@Autowired
	private IVehicleDao vehicleDao;
	
	@Autowired
	private IProposalDao proposalDao;
	
	@Autowired
	private IApprovalDao approvalDao;
	
	@Autowired
	private IVehicleService vehicleService;
	
	@Autowired
	private IProposalService proposalService;
	
	@Autowired
	private IBoxService boxService;
	
	@Autowired
	private IBoxOperationService boxOperationService;

	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData addProposal(ProposalCompanyVehcileInfo vo, List<MultipartFile> multipartFiles, Integer currentUserId, Integer companyId) {
		ResponseData rd = new ResponseData(0);
		ResponseData proposalInfoRd = new ResponseData(0);
		ResponseData fileRd = new ResponseData(0);
		
		if (null != vo ) {
			if  (vo.getProposal() == null) {
				rd.setCode(0);
				rd.setMessage("申请对象为空");
				return rd;
			}
			
			if  (vo.getVehicle() == null) {
				rd.setCode(0);
				rd.setMessage("车辆对象为空");
				return rd;
			}
			
			//校验上传的文件是否符合规则
			fileRd = UploadHelper.getFileSet(multipartFiles, 0, 0);
			if (fileRd.getCode() != 1) {
				rd.setCode(fileRd.getCode());
				rd.setMessage(fileRd.getMessage());
				return rd;
			}
			multipartFiles= (List<MultipartFile>) fileRd.get("multipartFileList");
			
			proposalInfoRd = this.addProposalInfo(vo, multipartFiles, currentUserId, companyId);
			if (proposalInfoRd.getCode() != 1) {
				rd.setCode(proposalInfoRd.getCode());
				rd.setMessage(proposalInfoRd.getMessage());
				return rd;
			}
		}
		
		rd.setCode(1);
		rd.setMessage("申请单新增成功");
		rd.setFirstItem(vo);
		
		return rd;
	}
	
	private ResponseData addProposalInfo(ProposalCompanyVehcileInfo vo, List<MultipartFile> multipartFiles, Integer currentUserId, Integer companyId) {
		ResponseData rd = new ResponseData(0);
		ResponseData vehicleRd = new ResponseData(0);
		ResponseData proposalRd = new ResponseData(0);
		ProposalVO proposalVO = vo.getProposal();
		Integer proposalType = proposalVO.getType();
		
		VehicleVO vehicleVO = vo.getVehicle();
		if (vehicleVO != null) {
			Vehicle currentVehicle = CacheVehicleManager.getVehicleByVin(vehicleVO.getVin());
			if (currentVehicle != null) {
				 Integer configureStatus = currentVehicle.getConfigureStatus();
				 if (configureStatus != GlobalConstant.CONFIGURE_STATUS_INIT) {
					rd.setCode(-1);
					rd.setMessage("该车辆已存在系统中，请不要重复申请");
					return rd;
				} 
			}
			
			//新增车辆数据
			vehicleRd = this.addVehicle(companyId, vehicleVO, proposalType);
			if (vehicleRd.getCode() != 1) {
				rd.setCode(vehicleRd.getCode());
				rd.setMessage(vehicleRd.getMessage());
				return rd;
			} 
		}
		
		if (vehicleRd.getCode() == 1) {
			Vehicle dbVehicle = (Vehicle) vehicleRd.getFirstItem();
			//新增申请单数据
			proposalRd = proposalService.addProposal(vo, multipartFiles, dbVehicle, currentUserId, companyId);
			if (proposalRd.getCode() != 1) {
				rd.setCode(proposalRd.getCode());
				rd.setMessage(proposalRd.getMessage());
				return rd;
			}
			//如果这个申请类型为换装，则更新box相关信息 TODO
			if (proposalType == GlobalConstant.PROPOSAL_TYPE_REPLACE) {
				this.replaceBox(dbVehicle.getCompanyId(), proposalVO.getReplaceVehicleId(), dbVehicle.getId(), currentUserId);
			}
			
		}
		rd.setCode(1);
		return rd;
	}
	
	
	/**
	 * 新装添加车辆信息
	 * @param companyId
	 * @param vehicleVO
	 * @param proposalType TODO
	 */
	private ResponseData addVehicle(Integer companyId, VehicleVO vehicleVO, Integer proposalType) {
		ResponseData vehicleRd = new ResponseData(0);
		HashMap parameters = new HashMap();
		parameters.put("companyId", companyId);
		parameters.put("vin", vehicleVO.getVin());
		List vehicleList = vehicleDao.find(parameters, null);
		
		Integer configureStatus = GlobalConstant.CONFIGURE_STATUS_WATTING;
		
		//如果车辆已存在就修改车辆状态，否则新增车辆信息
		if (vehicleList != null && vehicleList.size() > 0) {
			Vehicle currentVehicle = (Vehicle) vehicleList.get(0);
			
			MyBeanUtils.copyPropertiesContainsDate(currentVehicle, vehicleVO);
			currentVehicle.setCompanyId(companyId);
			currentVehicle.setConfigureStatus(configureStatus);
			vehicleRd = vehicleService.editVehicle(currentVehicle);
			
		} else {
			Vehicle dbVehicle = new Vehicle();
			MyBeanUtils.copyPropertiesContainsDate(dbVehicle, vehicleVO);
			dbVehicle.setCompanyId(companyId);
			dbVehicle.setConfigureStatus(configureStatus);
			Integer carStyleId = dbVehicle.getCarStyleId();
			if (proposalType == GlobalConstant.PROPOSAL_TYPE_INSTALLATION) {
				if (dbVehicle != null) {
					//获取经销商当前年份关于此车型的配置类型
					CompanyCarStyleRelation relation = companyCarStyleRelationDao.getByCarStyleId(companyId, carStyleId);
					if (relation != null) {
						dbVehicle.setConfigureType(relation.getConfigType());
					} else {
						//默认设置配置类型为：不配置
						dbVehicle.setConfigureType(GlobalConstant.CONFIGURE_TYPE_NO);
					}
				}
			} else {
				//换装时设置为必配
				dbVehicle.setConfigureType(GlobalConstant.CONFIGURE_TYPE_MUST);
			}
			vehicleRd = vehicleService.addVehicle(dbVehicle, companyId);
		}
		return vehicleRd;
	}
	
	/**
	 * 设置车辆配置状态
	 * @param companyId
	 * @param carStyleId
	 * @return
	 */
	private Integer siteConfigureStatus(Integer companyId, Integer carStyleId) {
		Integer configureStatus = GlobalConstant.CONFIGURE_STATUS_WATTING;//待安装
		Company currentCompany= CacheCompanyManager.getCompany(companyId);
		
		CarStyle carStyle = carStyleDao.get(carStyleId);
		if (carStyle != null) {
			//获取经销商当前年份关于此车型的配置类型，此车型ID为基础车型ID
			CompanyCarStyleRelation relation = companyCarStyleRelationDao.getByCarStyleId(companyId, carStyle.getId());
			if (relation != null) {
				//车型选配方式为不安装
				if (relation.getConfigType() == GlobalConstant.CONFIGURE_TYPE_NO) {
					if (currentCompany != null) {
						Integer isKeyCity = currentCompany.getIsKeyCity();
						//如果是经销商是重点城市，则车辆配置状态为【重点不安装】
						if (isKeyCity == GlobalConstant.KEY_CITY) {
							configureStatus = GlobalConstant.CONFIGURE_STATUS_IMPORTANT_NO;
						}
						//如果是经销商是非重点城市,则车辆配置状态为【非重点不安装】
						if (isKeyCity == GlobalConstant.NON_KEY_CITY) {
							configureStatus = GlobalConstant.CONFIGURE_STATUS_NO_IMPORTANT_NO;
						}
					}
				}
				
			} 
		}
		return configureStatus;
	}
	
	/**
	 * 新装添加BOX信息
	 * @param companyId
	 * @param boxVO
	 */
	private ResponseData addBox(Integer vehicleId,Integer companyId, BoxVO boxVO) {
		ResponseData rd = new ResponseData(0);
		ResponseData boxRd = new ResponseData(0);
		HashMap parameters = new HashMap();
		parameters.put("uniqueId", boxVO.getUniqueId());
		String uniqueId = boxVO.getUniqueId();
		Box dbBox = boxService.getBoxByUniqueId(uniqueId);
		
		if (dbBox != null) {
			if (vehicleId == dbBox.getVehicleId()) {
				rd.setCode(-2);
				rd.setMessage("安装失败：该设备已经安装在本车上了！");
				return rd;
			}
			if (vehicleId != dbBox.getVehicleId() && dbBox.getVehicleId() > 0) {
				rd.setCode(-3);
				rd.setMessage("安装失败：该设备已经安装在其他车上了！");
				return rd;
			}
			
			MyBeanUtils.copyPropertiesContainsDate(dbBox, boxVO);
			dbBox.setCompanyId(companyId);
			dbBox.setVehicleId(vehicleId);
			dbBox.setStatus(GlobalConstant.BOX_STATUS_ACTIVATION);
			boxRd = boxService.editBox(dbBox, companyId, null);
			if (boxRd.getCode() != 1) {
				 rd.setCode(boxRd.getCode());
				 rd.setMessage(boxRd.getMessage());
				 return rd;
			}
			
		} else {
			dbBox = new Box();
			MyBeanUtils.copyPropertiesContainsDate(dbBox, boxVO);
			dbBox.setCompanyId(companyId);
			dbBox.setVehicleId(vehicleId);
			dbBox.setSimId("");
			boxRd = boxService.addBox(dbBox, companyId);
			if (boxRd.getCode() != 1) {
				 rd.setCode(boxRd.getCode());
				 rd.setMessage(boxRd.getMessage());
				 return rd;
			}
		}
		rd.setCode(1);
		rd.setFirstItem(boxRd.getFirstItem());
		return rd;
	}

	public ResponseData addReplace(ProposalCompanyVehcileInfo vo, List<MultipartFile> multipartFiles, Integer currentUserId, Integer companyId) {
		ResponseData rd = new ResponseData(0);
		ResponseData proposalInfoRd = new ResponseData(0);
		ResponseData fileRd = new ResponseData(0);
		
		if (null != vo ) {
			if  (vo.getProposal() == null) {
				rd.setCode(0);
				rd.setMessage("申请对象为空");
				return rd;
			}
			
			if  (vo.getVehicle() == null) {
				rd.setCode(0);
				rd.setMessage("车辆对象为空");
				return rd;
			}
			
			//校验上传的文件是否符合规则
			fileRd = UploadHelper.getFileSet(multipartFiles, 0, 0);
			if (fileRd.getCode() != 1) {
				rd.setCode(fileRd.getCode());
				rd.setMessage(fileRd.getMessage());
				return rd;
			}
			multipartFiles= (List<MultipartFile>) fileRd.get("multipartFileList");
			
			//新增车辆和申请信息
			proposalInfoRd = this.addProposalInfo(vo, multipartFiles, currentUserId, companyId);
			if (proposalInfoRd.getCode() != 1) {
				rd.setCode(proposalInfoRd.getCode());
				rd.setMessage(proposalInfoRd.getMessage());
				return rd;
			}
		}
		
		rd.setCode(1);
		rd.setMessage("申请单新增成功");
		rd.setFirstItem(vo);
		
		return rd;
	}

	@Override
	public ResponseData editProposal(ProposalCompanyVehcileInfo vo) {
		ResponseData rd = new ResponseData(0);

		Integer proposalId = vo.getProposal().getId();
		Proposal dbProposal = proposalDao.get(proposalId);

		VehicleVO vehicleVO = vo.getVehicle();
		Integer vehicleId = dbProposal.getVehicleId();
		vehicleVO.setId(vehicleId);
		
		//设置车辆配置状态
		Integer configureStatus = GlobalConstant.CONFIGURE_STATUS_WATTING;
		Vehicle dbVehicle = CacheVehicleManager.getVehicleById(vehicleId);
		
		if (dbVehicle.getVin() !=null && !vehicleVO.getVin().equalsIgnoreCase(dbVehicle.getVin())) {
			if (vehicleService.isExistence(vehicleId, "vin", vehicleVO.getVin(), null)) {
				rd.setCode(-1);
				rd.setMessage("车辆信息保存失败：新的VIN已经存在！");
				return rd;
			}
		}
		
		if (dbVehicle.getPlateNumber() !=null && !vehicleVO.getPlateNumber().equalsIgnoreCase(dbVehicle.getPlateNumber())) {
			if (vehicleService.isExistence(vehicleId, "plateNumber", vehicleVO.getPlateNumber(), " and configureStatus != " + GlobalConstant.CONFIGURE_STATUS_UNINSTALLED)) {
				rd.setCode(-1);
				rd.setMessage("车辆信息保存失败：新的车牌已经存在！");
				return rd;
			}
		}
		
		MyBeanUtils.copyPropertiesContainsDate(dbVehicle, vehicleVO);
		dbVehicle.setConfigureStatus(configureStatus);
		if (vehicleVO.getLicensingTime() == null) {
			dbVehicle.setLicensingTime(null);
		}
		vehicleDao.update(dbVehicle);
		CacheVehicleManager.removeVehicleById(vehicleId);//删除缓存
		
		Integer proposalStatus = GlobalConstant.PROPOSAL_STATUS_PASSED;//审批通过
		if (dbVehicle != null) {
			if (configureStatus == GlobalConstant.CONFIGURE_STATUS_IMPORTANT_NO) {
				proposalStatus = GlobalConstant.PROPOSAL_STATUS_IMPORTANT_NO;
			}
			if (configureStatus == GlobalConstant.CONFIGURE_STATUS_NO_IMPORTANT_NO) {
				proposalStatus = GlobalConstant.PROPOSAL_STATUS_NO_IMPORTANT_NO;
			}
		}
		
		MyBeanUtils.copyPropertiesContainsDate(dbProposal, vo.getProposal());
		dbProposal.setStatus(proposalStatus);
		dbProposal.setProposalTime(DateTime.toDate(DateTime.getNormalDateTime()));
		proposalDao.edit(dbProposal);

		rd.setFirstItem(vo);
		rd.setCode(1);
		rd.setMessage("申请单修改成功！");
		return rd;
	}

	@Override
	public ResponseData cancelProposal(Integer proposalId, Integer proposalType) {
		ResponseData rd = new ResponseData(0);

		if (proposalId != null) {
			proposalDao.delete(proposalId);
		}
		
		Proposal dbProposal = proposalDao.get(proposalId);
		if (dbProposal != null) {
			Vehicle dbVehicle = vehicleDao.get(dbProposal.getVehicleId());
			if (dbVehicle != null) {
				if (proposalType == GlobalConstant.PROPOSAL_TYPE_INSTALLATION) {
					dbVehicle.setConfigureStatus(GlobalConstant.CONFIGURE_STATUS_INIT);
				} 
				if (proposalType == GlobalConstant.PROPOSAL_TYPE_DEMOLITION) {
					dbVehicle.setConfigureStatus(GlobalConstant.CONFIGURE_STATUS_INSTALLED);
				} 
				vehicleService.editVehicle(dbVehicle);
			}
		}

		rd.setCode(1);
		rd.setMessage("申请单取消成功！");
		return rd;
	}
	
	@Override
	public ResponseData demolitionComplete(Integer vehicleId, Integer companyId, Integer currentUserId, Integer proposalType) {
		ResponseData rd = new ResponseData(0);
		ResponseData demolitionBoxRd = new ResponseData(0);
	 
		demolitionBoxRd = this.demolitionBox(vehicleId);
		if (demolitionBoxRd.getCode() != 1) {
			 rd.setCode(demolitionBoxRd.getCode());
			 rd.setMessage(demolitionBoxRd.getMessage());
			 return rd;
		}
		
		//添加拆除申请记录
		Proposal proposal = new Proposal();
		proposal.setVehicleId(vehicleId);
		proposal.setCompanyId(companyId);
		proposal.setType(proposalType);
		proposal.setStatus(GlobalConstant.PROPOSAL_STATUS_UNINSTALLED);
		proposal.setDepartmentId(companyId);
		proposal.setContactName("");
		proposal.setContactPhone("");
		proposal.setProposerId(currentUserId);
		proposal.setProposalTime(new Date());
		proposalDao.save(proposal);
		
		rd.setCode(1);
		rd.setMessage("拆除完成！");
		return rd;
	}
	
	/**
	 * 拆除盒子
	 * @param vehicleId
	 * @return
	 */
	private ResponseData demolitionBox(Integer vehicleId) {
		ResponseData rd = new ResponseData(0);
		ResponseData rdVehicle = new ResponseData(0);
		ResponseData boxOperationRd = new ResponseData(0);
		ResponseData boxRd = new ResponseData(0);
		Integer vehicleCompanyId = 0;
		Vehicle dbVehicle = vehicleDao.get(vehicleId);
		if (dbVehicle != null) {
			vehicleCompanyId = dbVehicle.getCompanyId();
			dbVehicle.setConfigureStatus(GlobalConstant.CONFIGURE_STATUS_UNINSTALLED);
			rdVehicle = vehicleService.editVehicle(dbVehicle);
			if (rdVehicle.getCode() != 1) {
				rd.setCode(rdVehicle.getCode());
				rd.setMessage(rdVehicle.getMessage());
				return rd;
			}
		}
		//通过车辆ID查设备信息
		Box dbBox = boxService.getBoxByVehicleId(vehicleId);
		//把设备表中的车辆移除
		if (dbBox != null) {
			//设备操作信息表中添加一条拆除信息
			BoxOperation boxOperation = new BoxOperation();
			boxOperation.setCompanyId(vehicleCompanyId);
			boxOperation.setVehicleId(vehicleId);
			boxOperation.setBoxId(dbBox.getId());//盒子ID
			boxOperation.setType(GlobalConstant.PROPOSAL_TYPE_DEMOLITION);
			boxOperation.setStatus(1);
			boxOperationRd = boxOperationService.addBoxOperation(boxOperation, vehicleCompanyId);
			
			if (boxOperationRd.getCode() != 1) {
				 rd.setCode(boxOperationRd.getCode());
				 rd.setMessage(boxOperationRd.getMessage());
				 return rd;
			}
			
			dbBox.setVehicleId(0);//将车辆id移除设备表取消绑定
			dbBox.setStatus(GlobalConstant.FALSE);
			boxRd = boxService.editBox(dbBox, vehicleCompanyId, null);
			if (boxRd.getCode() != 1) {
				 rd.setCode(boxRd.getCode());
				 rd.setMessage(boxRd.getMessage());
				 return rd;
			}
		}
		
		rd.setCode(1);
		return rd;
	}

	/**
	 * 换装
	 * @param vehicleCompanyId 车辆的公司ID
	 * @param oldVehicleId 待拆除的车辆ID
	 * @param newVehicleId 待安装的车辆ID
	 * @param userId TODO
	 * @return
	 */
	private ResponseData replaceBox(Integer vehicleCompanyId, Integer oldVehicleId, Integer newVehicleId, Integer userId) {
		ResponseData rd = new ResponseData(0);
		//ResponseData boxOperationRd = new ResponseData(0);
		//ResponseData boxRd = new ResponseData(0);
		
		//更新老车申请单状态为已拆除
		//更新老车的状态为已锁定
		Vehicle oldVehicle = CacheVehicleManager.getVehicleById(oldVehicleId);
		if (oldVehicle != null) {
			oldVehicle.setConfigureStatus(GlobalConstant.CONFIGURE_STATUS_UNINSTALLED);
			vehicleDao.update(oldVehicle);
			CacheVehicleManager.removeVehicleById(oldVehicleId);
		}
		
		/*//通过车辆ID查设备信息
		Box dbBox = boxService.getBoxByVehicleId(oldVehicleId);
		//把设备表中的车辆移除
		if (dbBox != null) {
			Integer boxId = dbBox.getId();
			//设备操作信息表中添加一条拆除信息
			BoxOperation boxOperation = new BoxOperation();
			boxOperation.setCompanyId(vehicleCompanyId);
			boxOperation.setVehicleId(oldVehicleId);
			boxOperation.setBoxId(boxId);//盒子ID
			boxOperation.setType(GlobalConstant.PROPOSAL_TYPE_DEMOLITION);
			boxOperation.setStatus(GlobalConstant.TRUE);
			boxOperation.setOperationName(CacheUserManager.getLoginName(userId));
			boxOperation.setOperationTime(new Date());
			boxOperationRd = boxOperationService.addBoxOperation(boxOperation, vehicleCompanyId);
			
			if (boxOperationRd.getCode() != 1) {
				 rd.setCode(boxOperationRd.getCode());
				 rd.setMessage(boxOperationRd.getMessage());
				 return rd;
			}
			
			//将新的车辆Id更新为0
			dbBox.setVehicleId(GlobalConstant.FALSE);
			dbBox.setStatus(GlobalConstant.FALSE);
			boxRd = boxService.editBox(dbBox, vehicleCompanyId, null);
			if (boxRd.getCode() != 1) {
				 rd.setCode(boxRd.getCode());
				 rd.setMessage(boxRd.getMessage());
				 return rd;
			}
		
		}*/
		
		rd.setCode(1);
		return rd;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseData demolitionVehicle(VehicleVO vehicleVO, String userName) {
		ResponseData rd = new ResponseData(GlobalConstant.TRUE);
		if (vehicleVO != null ) {
			Integer demolitionType = vehicleVO.getDemolitionType();
			if (demolitionType == GlobalConstant.FALSE && vehicleVO.getCarStyleId() != null ) {
				//通过车型查询车辆表已安装的车辆
				Integer carStyleId = vehicleVO.getCarStyleId();
				HashMap<Object, Object> parameters = new HashMap<Object, Object>();
				parameters.put("configureStatus", GlobalConstant.CONFIGURE_STATUS_INSTALLED);
				parameters.put("carStyleId", carStyleId);
				List<Vehicle> vehicleList = vehicleDao.find(parameters, "");
				if (vehicleList != null && vehicleList.size() > 0) {
					for (Vehicle dbVehicle : vehicleList) {
						if (dbVehicle != null) {
							Integer vehicleId = dbVehicle.getId();
							//Integer vehicleCompanyId = dbVehicle.getCompanyId();
							//更新车辆状态为已锁定
							dbVehicle.setConfigureStatus(GlobalConstant.CONFIGURE_STATUS_UNINSTALLED);
							dbVehicle.setDeletionTime(new Date());
							vehicleDao.update(dbVehicle);
							CacheVehicleManager.removeVehicleById(vehicleId);//删除缓存
							
							//更新box表与车辆的关系
							/*Box dbBox = boxService.getBoxByVehicleId(vehicleId);
							if (dbBox != null) {
								//设备操作信息表中添加一条拆除信息
								BoxOperation boxOperation = new BoxOperation();
								boxOperation.setCompanyId(vehicleCompanyId);
								boxOperation.setVehicleId(vehicleId);
								boxOperation.setBoxId(dbBox.getId());
								boxOperation.setType(GlobalConstant.PROPOSAL_TYPE_DEMOLITION);
								boxOperation.setStatus(GlobalConstant.TRUE);
								boxOperation.setOperationName(userName);
								boxOperation.setOperationTime(new Date());
								boxOperationService.addBoxOperation(boxOperation, vehicleCompanyId);
								
								dbBox.setVehicleId(GlobalConstant.FALSE);//将车辆id移除设备表取消绑定
								dbBox.setStatus(GlobalConstant.FALSE);
								boxDao.update(dbBox);
								CacheVehicleManager.removeVehicleById(vehicleId);
							}*/
						}
					}
					String carStyleName = CacheCarManager.getCarStyleNameById(carStyleId);
					rd.setMessage("成功拆除[" + carStyleName + "]下" + vehicleList.size() + "辆车");
				} else {
					rd.setCode(-1);
					rd.setMessage("该车型下无待拆除的车");
				}
			} else {
				Integer vehicleId = vehicleVO.getId();
				Vehicle dbVehicle = CacheVehicleManager.getVehicleById(vehicleVO.getId());
				if (dbVehicle != null) {
					if (dbVehicle.getConfigureStatus() == GlobalConstant.CONFIGURE_STATUS_UNINSTALLED) {
						rd.setCode(-1);
						rd.setMessage("该车辆已锁定，不能拆除");
					} else {
						//Integer vehicleCompanyId = dbVehicle.getCompanyId();
						//更新车辆状态为已锁定
						dbVehicle.setConfigureStatus(GlobalConstant.CONFIGURE_STATUS_UNINSTALLED);
						dbVehicle.setDeletionTime(new Date());
						vehicleDao.update(dbVehicle);
						CacheVehicleManager.removeVehicleById(vehicleId);//删除缓存
						
						//更新box表与车辆的关系
						/*Box dbBox = boxService.getBoxByVehicleId(vehicleId);
						if (dbBox != null) {
							//设备操作信息表中添加一条拆除信息
							BoxOperation boxOperation = new BoxOperation();
							boxOperation.setCompanyId(vehicleCompanyId);
							boxOperation.setVehicleId(vehicleId);
							boxOperation.setBoxId(dbBox.getId());
							boxOperation.setType(GlobalConstant.PROPOSAL_TYPE_DEMOLITION);
							boxOperation.setStatus(GlobalConstant.TRUE);
							boxOperation.setOperationName(userName);
							boxOperation.setOperationTime(new Date());
							boxOperationService.addBoxOperation(boxOperation, vehicleCompanyId);
							
							dbBox.setVehicleId(GlobalConstant.FALSE);//将车辆id移除设备表取消绑定
							dbBox.setStatus(GlobalConstant.FALSE);
							boxDao.update(dbBox);
						}*/
						rd.setMessage("拆除成功");
					}
				}
			}
			
		}
		
		return rd;
	}

	@Override
	public ResponseData proposalApproval(ProposalCompanyVehcileInfo proposalCompanyVehcileInfo, 
			Integer companyId, HttpServletRequest request) {
		ResponseData rd = new ResponseData(0);
		ProposalVO proposalVO = proposalCompanyVehcileInfo.getProposal();
		ApprovalVO approvalVO = proposalCompanyVehcileInfo.getApproval();
		
		Proposal dbProposal = proposalDao.get(proposalVO.getId());
		Integer configureStatus = 0; //车辆配置状态
		Integer proposalType = dbProposal.getType();
		
		if (approvalVO.getApprovalStatus() == GlobalConstant.APPROVAL_STATUS_PASSED) {
			
			dbProposal.setStatus(GlobalConstant.PROPOSAL_STATUS_PASSED);
			//新装
			if (proposalType == GlobalConstant.PROPOSAL_TYPE_INSTALLATION) {
				configureStatus = GlobalConstant.CONFIGURE_STATUS_WATTING;
			}
			//拆装
			if (proposalType == GlobalConstant.PROPOSAL_TYPE_DEMOLITION) {
				configureStatus = GlobalConstant.CONFIGURE_STATUS_DEMOLISHING;
			}
			
		} else if (approvalVO.getApprovalStatus() == GlobalConstant.APPROVAL_STATUS_UNPASSED) {
			
			dbProposal.setStatus(GlobalConstant.PROPOSAL_STATUS_UNPASSED);
			//新装
			if (proposalType == GlobalConstant.PROPOSAL_TYPE_INSTALLATION) {
				configureStatus = GlobalConstant.CONFIGURE_STATUS_INIT;
			}
			//拆装
			if (proposalType == GlobalConstant.PROPOSAL_TYPE_DEMOLITION) {
				configureStatus = GlobalConstant.CONFIGURE_STATUS_INSTALLED;
			}
			
		} 
		
		
		Vehicle dbVehicle = vehicleDao.get(dbProposal.getVehicleId());
		if (dbVehicle != null) {
			dbVehicle.setConfigureStatus(configureStatus);
			vehicleService.editVehicle(dbVehicle);
		}
		
		Approval dbApproval = new Approval();
		MyBeanUtils.copyPropertiesContainsDate(dbApproval, approvalVO);
		dbApproval.setProposalId(dbProposal.getId());
		dbApproval.setUserId(SessionManager.getCurrentUserId(request));
		dbApproval.setUserNick(SessionManager.getCurrentUserNickName(request));
		dbApproval.setApprovalTime(DateTime.toDate(DateTime.getNormalDateTime()));
		approvalDao.save(dbApproval);
		
		if (dbProposal != null) {
			//审批时间
			dbProposal.setProposalTime(new Date());
			proposalDao.edit(dbProposal);
		}
		
		rd.setCode(1);
		rd.setMessage("申请单审批成功！");
		return rd;
	}

	@Override
	public ResponseData addDemolitionProposal(Integer vehicleId, Integer currentUserId, Integer companyId, Integer proposalType) {
		ResponseData rd = new ResponseData(0);
		ResponseData vehicleRd = new ResponseData(0);
		
		Vehicle dbVehicle = vehicleDao.get(vehicleId);
		if (dbVehicle != null) {
			dbVehicle.setConfigureStatus(GlobalConstant.CONFIGURE_STATUS_DISASSEMBLY_PENDING);
			vehicleRd = vehicleService.editVehicle(dbVehicle);
		}
		
		//新增申请单数据
		if (vehicleRd.getCode() == 1) {
			ProposalVO proposalVO = new ProposalVO();
			proposalVO.setVehicleId(vehicleId);
			proposalVO.setCompanyId(companyId);
			
			Proposal proposal = new Proposal();
			BeanUtils.copyProperties(proposalVO, proposal);
			
			proposal.setType(proposalType);
			proposal.setStatus(GlobalConstant.PROPOSAL_STATUS_PROCESSING);//待审核
			proposal.setDepartmentId(companyId);
			proposal.setContactName("");
			proposal.setContactPhone("");
			proposal.setProposerId(currentUserId);
			proposal.setProposalTime(new Date());
			
			proposalDao.save(proposal);
		}
		
		rd.setCode(1);
		rd.setMessage("车辆拆除申请成功");
		return rd;
	}

	@Override
	public ResponseData installationComplete(
			ProposalCompanyVehcileInfo proposalCompanyVehcileInfo,
			Integer companyId, HttpServletRequest request) {
		ResponseData rd = new ResponseData(0);
		ResponseData boxRd = new ResponseData(0);
		ResponseData boxOperationRd = new ResponseData(0);
		ResponseData vehicleRd = new ResponseData(0);
		ProposalVO proposalVO = proposalCompanyVehcileInfo.getProposal();
		
		Proposal dbProposal = proposalDao.get(proposalVO.getId());
		Integer proposalCompanyId = dbProposal.getCompanyId();
		Integer configureStatus = 0; //车辆配置状态
		Integer proposalType = dbProposal.getType();
		Integer boxOperationType = 0; //设备操作类型
		
			dbProposal.setStatus(GlobalConstant.PROPOSAL_STATUS_INSTALLED);
			//新装
			if (proposalType == GlobalConstant.PROPOSAL_TYPE_INSTALLATION) {
				configureStatus = GlobalConstant.CONFIGURE_STATUS_INSTALLED;
				boxOperationType = GlobalConstant.PROPOSAL_TYPE_INSTALLATION;
			}
			//拆装
			if (proposalType == GlobalConstant.PROPOSAL_TYPE_DEMOLITION) {
				configureStatus = GlobalConstant.CONFIGURE_STATUS_UNINSTALLED;
				boxOperationType = GlobalConstant.PROPOSAL_TYPE_DEMOLITION;
			}
		
		
		//新增和编辑box表
		BoxVO boxVO = proposalCompanyVehcileInfo.getTbox();
		boxRd = this.addBox(dbProposal.getVehicleId(), proposalCompanyId, boxVO);
		if (boxRd.getCode() != 1) {
		 rd.setCode(boxRd.getCode());
		 rd.setMessage(boxRd.getMessage());
		 return rd;
		}
		
		//新增安装信息表
		BoxOperationVO boxOperationVO = proposalCompanyVehcileInfo.getBoxOperation();
		BoxOperation boxOperation = new BoxOperation();
		MyBeanUtils.copyPropertiesContainsDate(boxOperation, boxOperationVO);
		boxOperation.setCompanyId(proposalCompanyId);
		boxOperation.setVehicleId(dbProposal.getVehicleId());
		Box currentBox = (Box) boxRd.getFirstItem();
		boxOperation.setBoxId(currentBox.getId());//盒子ID
		boxOperation.setType(boxOperationType);
		boxOperation.setStatus(1);
		boxOperationRd = boxOperationService.addBoxOperation(boxOperation, proposalCompanyId);
		
		if (boxOperationRd.getCode() != 1) {
			 rd.setCode(boxOperationRd.getCode());
			 rd.setMessage(boxOperationRd.getMessage());
			 return rd;
		}
		
		Vehicle dbVehicle = vehicleDao.get(dbProposal.getVehicleId());
		if (dbVehicle != null) {
			dbVehicle.setConfigureStatus(configureStatus);
			vehicleRd = vehicleService.editVehicle(dbVehicle);
			if (vehicleRd.getCode() != 1) {
				 rd.setCode(vehicleRd.getCode());
				 rd.setMessage(vehicleRd.getMessage());
				 return rd;
			}
		}
		
		if (dbProposal != null) {
			proposalDao.edit(dbProposal);
		}

		rd.setCode(1);
		rd.setMessage("安装完成成功！");
		return rd;
	}

	@Override
	public ResponseData confirmInstallationResult(ProposalCompanyVehcileInfo proposalCompanyVehcileInfo) {
		ResponseData rd = new ResponseData(0);
		
		VehicleVO vehicle = proposalCompanyVehcileInfo.getVehicle();
		BoxVO box = proposalCompanyVehcileInfo.getTbox();
		
		String plateNumber = vehicle.getPlateNumber();
		String vin = vehicle.getVin();
		String uniqueId = box.getUniqueId();
		
		if ((plateNumber != null && plateNumber.trim().length() > 0) 
				|| (vin != null && vin.trim().length() > 0)) {
			HashMap parameters = new HashMap<>();
			if (plateNumber != null && plateNumber.trim().length() > 0) {
				parameters.put("plateNumber", plateNumber);
			}
			if (vin != null && vin.trim().length() > 0) {
				parameters.put("vin", vin);
			}
			List vehicleList = vehicleDao.find(parameters, "");
			if (vehicleList != null && vehicleList.size() > 0) {
				Vehicle dbVehicle = (Vehicle) vehicleList.get(0);
				vehicle = new VehicleVO(dbVehicle);
				proposalCompanyVehcileInfo.setVehicle(vehicle);
				if (dbVehicle.getId() != null) {
					Box dbBox = boxService.getBoxByVehicleId(dbVehicle.getId());
					if (dbBox != null) {
						rd.setMessage("此车辆已成功安装车机设备");
						box = new BoxVO(dbBox);
						proposalCompanyVehcileInfo.setTbox(box);
					} else {
						rd.setCode(-1);
						rd.setMessage("此车辆未安装车机设备");
						return rd;
					}
				}
				
			} else {
				rd.setCode(-1);
				rd.setMessage("您输入的车辆信息未在上汽大众试乘试驾车辆库中");
				return rd;
			}
		} else if (uniqueId != null && uniqueId.trim().length() > 0) {
				Box dbBox = boxService.getBoxByUniqueId(uniqueId);
				if (dbBox != null && dbBox.getVehicleId() != null) {
					box = new BoxVO(dbBox);
					proposalCompanyVehcileInfo.setTbox(box);
					Vehicle dbVehicle = vehicleDao.get(dbBox.getVehicleId());
					if (dbVehicle != null) {
						vehicle = new VehicleVO(dbVehicle);
						proposalCompanyVehcileInfo.setVehicle(vehicle);
						rd.setMessage("车机设备安装成功");
					} else {
						rd.setMessage("此设备未安装至车辆上");
					}
				} else {
					rd.setCode(-1);
					rd.setMessage("您输入的设备号未在上汽大众试乘试驾车机设备库中");
					return rd;
				}
		} else {
			rd.setCode(-1);
			rd.setMessage("信息输入不完整");
			return rd;
		}
		
		rd.setCode(1);
		rd.put("proposalCompanyVehcileInfo", proposalCompanyVehcileInfo);
		return rd;
	}

}

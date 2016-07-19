package com.ava.dealer.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.dao.ICompanyDao;
import com.ava.dao.IDilixueDao;
import com.ava.dao.IReportDao;
import com.ava.dao.impl.OrgDao;
import com.ava.dealer.service.IReportService;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.Dilixue;
import com.ava.domain.entity.Org;
import com.ava.domain.vo.CompanyCarStyleVO;
import com.ava.domain.vo.CompanyFilingProposalVO;
import com.ava.domain.vo.CompanyProposalVO;
import com.ava.domain.vo.ReportBaseVO;
import com.ava.domain.vo.TestDriveInfoVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheCarManager;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheDriveLineManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.DateTime;
import com.ava.util.TypeConverter;
import com.ava.util.excel.ArrayDataToClass;
import com.ava.util.excel.ExcelOperate;
@Service
public class ReportService extends BaseService implements IReportService{
	
	@Autowired
	private IReportDao reportDao;
	
	@Autowired
	private OrgDao orgDao;

	@Autowired
	private  IDilixueDao dilixueDao;
	
	@Autowired
	private ICompanyDao companyDao;
	
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Override
	public ResponseData excelExport(MultipartFile file) throws FileNotFoundException, IOException, ParseException{
		String[][] result1 = ExcelOperate.getData(file, 0);

		String entityName = "com.ava.domain.entity.Dilixue";

		ArrayDataToClass tmp = new ArrayDataToClass();
		List<Dilixue> resultlist = tmp.getEntityList(result1, entityName);

		for (Dilixue d : resultlist) {
			System.out.println(((Dilixue) d).getId() + "  "
					+ ((Dilixue) d).getChinese() + "  "
					+ ((Dilixue) d).getEnglish() + "  "
					+ ((Dilixue) d).getContent()+ "  "
					+ ((Dilixue) d).getTime());
			dilixueDao.save(d);
		}
		return null;
	}
	

	
	/*
	 * 大区报表 部分代码 start 
	 */
	

	/*
	 * 计算野帝1.4T 1.8L  (一起统计) 某时间段的全国试驾率
	 */
	public String queryYetiChinaDrivePercentInfo(String companyIds, String startTime, String endTime) 
	{
		
		StringBuffer exeSql=new StringBuffer(""); 
		List data;
		exeSql.append(" select  count(r.VEHICLE_ID) from t_test_drive r ,t_vehicle  t	where "+
			" r.VEHICLE_ID = t.id and  ( t.CAR_STYLE_ID =4 or t.CAR_STYLE_ID = 5) and t.CONFIGURE_STATUS =30  ");
		if(StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime))
		{
			exeSql.append(" and r.START_TIME between date('"+ startTime
					+"') and  date('"+  endTime
					+"')"
					);
		}
		data = reportDao.queryByYear(exeSql.toString());
		float allTestDriveTimes =TypeConverter.toFloat(data.get(0));	
		
		exeSql=new StringBuffer(""); 
		exeSql.append("	select  sum(r.number) from  t_hopeful_custumer r where " +
				" (r.CAR_STYLE_ID =4 or r.CAR_STYLE_ID = 5)");
		if(StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime))
		{
			exeSql.append(" and r.RECORD_DATE between date(\""+ startTime.substring(0,10)
					+"\") and  date(\""+  endTime.substring(0,10)
					+"\")");
		}
		
		data = reportDao.queryByYear(exeSql.toString());
		float allCustomer =TypeConverter.toFloat(data.get(0));
		
		String yetiChinaPercent= (allCustomer != 0 ? String.format("%.1f", (allTestDriveTimes/  allCustomer)*100)+"%"
				: "0.0%");
		return yetiChinaPercent;
	}
	
	/*
	 * 计算全国某时间段的配置率
	 */
	public String queryBigAreaChinaConfigureInfo(String startTime, String endTime) 
	{
		StringBuffer exeSql=new StringBuffer(""); 
		List data;
		exeSql.append("select 	count(*) from  t_vehicle r  , t_box_operation b " +
				"  where   r.CONFIGURE_TYPE =1   ");
		if(StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime))
		{
			exeSql.append(" and b.OPERATION_TIME between date('"+ startTime
					+"') and  date('"+  endTime
					+"')"
					);
		}
		//计算总个数violationDao
		data = reportDao.queryByYear(exeSql.toString());
		float all =TypeConverter.toFloat(data.get(0));
		exeSql.append(" and r.CONFIGURE_STATUS =30  ");
		data = reportDao.queryByYear(exeSql.toString());
		float hasConfigure =TypeConverter.toFloat(data.get(0));
		
		String configurePercent= (all != 0 ? String.format("%.1f", (hasConfigure/  all)*100)+"%"
				: "0.0%");
		return configurePercent;
	}
	
	/*
	 * 计算全国某时间段的试驾率
	 */
	public String queryBigAreaChinaPercentInfo(String startTime, String endTime) 
	{
		StringBuffer exeSql=new StringBuffer(""); 
		List data;
		exeSql.append("select  count(r.VEHICLE_ID) from  "+
			" t_test_drive r , t_vehicle v 	where v.ID = r.VEHICLE_ID and v.CONFIGURE_STATUS =30  ");
		if(StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime))
		{
			exeSql.append(" and r.START_TIME between date('"+ startTime
					+"') and  date('"+  endTime
					+"')"
					);
		}
		data = reportDao.queryByYear(exeSql.toString());
		float allTestNum =TypeConverter.toFloat(data.get(0));
		
		 exeSql=new StringBuffer(""); 
		 exeSql.append("select sum(r.number) from  "+
					" t_hopeful_custumer r where 1=1");
		 if(StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime))
		{
			exeSql.append(" and r.RECORD_DATE between date(\""+ startTime.substring(0,10)
					+"\") and  date(\""+  endTime.substring(0,10)
					+"\")"
					);
		}
		//计算总个数violationDao
		data = reportDao.queryByYear(exeSql.toString());
		float allCustomerNum =TypeConverter.toFloat(data.get(0));
		String testDrivePercent = (allCustomerNum != 0 ? String.format("%.1f", (allTestNum/ allCustomerNum)*100)+"%"
				: "0.0%");
		return testDrivePercent;
	}
	
	//查询某个车型 某些经销商下面的配置数量
	public List<String> listCarStyleVehicle(String companyIds,String startTime,String endTime) {
		//是否全国

		ResponseData rd = new ResponseData(0);
		StringBuffer exeSql=new StringBuffer("");
		//取出全部的list
		List listAllData= new ArrayList();
		
		exeSql.append("select v.CAR_STYLE_ID, count(r.VEHICLE_ID) from  "+
			" t_test_drive r , t_vehicle v 	where v.ID = r.VEHICLE_ID and v.CONFIGURE_STATUS =30 ");
		exeSql.append(" and  r.COMPANY_ID in ("+companyIds+") ");
		if(StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime))
		{
			exeSql.append(" and r.START_TIME between date('"+ startTime
					+"') and  date('"+  endTime
					+"')"
					);
		}
		exeSql.append(" group by v.CAR_STYLE_ID");
		System.out.print(exeSql.toString());

		//计算总个数
		listAllData = reportDao.queryByYear(exeSql.toString());
		
		List<String> resultData =new ArrayList();
		if(listAllData!=null)
		{
			Iterator iterator=listAllData.iterator();
			while(iterator.hasNext()){
				Object[] obj=(Object[]) iterator.next();
				resultData.add( TypeConverter.toInteger(obj[0])+"_"+TypeConverter.toInteger(obj[1]));
			}
		}
	
		return resultData;
	} 
	
	/*
	 * 大区报表 部分代码 end 
	 */
	
	/*
	 * 试驾率报表 部分代码 start 
	 */

	@Override
	public ResponseData listTestDrivePercent(TransMsg transMsg,CompanyCarStyleVO companyCarStyleVO,Integer companyId,boolean isExport) 
	{

		ResponseData rd = new ResponseData(0);
		StringBuffer exeSql=new StringBuffer("");
		//取出全部的list
		List listAllData;
		
		String companyIdStr="";
		//如果填网络代码 会可能查询得到一个经销商list:companyIdList
		Collection companyIdList =new ArrayList() ;
		String compangIdSend ="";
		if(companyCarStyleVO.getDealerCode()==null||companyCarStyleVO.getDealerCode().equals(""))
		{
		}else{
			 companyIdList =companyDao.getByDealerCodeList(companyCarStyleVO.getDealerCode());
			 List allList = CacheOrgManager.getChildrenOrgIdsList(companyId);
			 companyIdList = TypeConverter.pksAndpks(companyIdList, allList) ;
			 if(companyIdList==null)
				{
					rd.setMessage("没有该经销商!");
					return rd;
				} else	{
					compangIdSend=  TypeConverter.join(companyIdList, ",") ;
				}
		}
		
		//如果传来网络形态（网络代码）查询就按这个经销商的companyId 否则就按当前登陆者的下属经销商集合
		if(StringUtils.isNotEmpty(compangIdSend)){
			companyIdStr += " and  c.ORG_ID in(" +compangIdSend + ")";
		}else{
			if (companyId != null) {
				companyIdStr += " and  c.ORG_ID in(" +  CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")";
			}
		}
		
		exeSql.append("select c.IS_KEY_CITY isKeyCity, c.DEALER_TYPE dealerType, c.DEALER_CODE dealerCode, "+
			"  c.ORG_ID fenxiaocenter, "+ 
			"  c.REGION_PROVINCE_ID regionProvinceId,c.REGION_CITY_ID regionCityId,"+
			"	c.CN_NAME,	 c.ORG_ID "+
			"  from t_company c  ,t_test_drive r  "+
			"  where c.ORG_ID  =r.COMPANY_ID   "  );
		exeSql.append(companyIdStr);
			//时间段查询
			String startTime= "";
			String endTime= "";
			if(companyCarStyleVO.getStartTime()!=null||companyCarStyleVO.getEndTime()!=null)
			{
				startTime = DateTime.toNormalDateTime(companyCarStyleVO.getStartTime());
				endTime = DateTime.toNormalDateTime(companyCarStyleVO.getEndTime());
				exeSql.append(" and r.START_TIME between date('"+ startTime
						+"') and  date('"+  endTime
						+"')"
						);
			}
			if(StringUtils.isNotEmpty(companyCarStyleVO.getName()))
			{
				exeSql.append(" and  c.CN_NAME like \"%"+companyCarStyleVO.getName()+"%\" ");
			}
			if(StringUtils.isNotEmpty(companyCarStyleVO.getDealerCode()))
			{
				exeSql.append(" and  c.DEALER_CODE like \"%"+companyCarStyleVO.getDealerCode()+"%\" ");
			}
			exeSql.append(" group by c.ORG_ID ");
				
			System.out.print(exeSql);
		
			
			List<CompanyCarStyleVO> listAll = new ArrayList<CompanyCarStyleVO>();
			List<CompanyCarStyleVO> result =new ArrayList<CompanyCarStyleVO>();
			//计算总个数violationDao
			listAllData = reportDao.queryByYear(exeSql.toString());
			transMsg.setTotalRecords(TypeConverter.toLong(listAllData.size()));
			
			if(isExport == true) {
				transMsg.setPageSize(Integer.MAX_VALUE);
			} else {
				transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS-1);
			}
			if(isExport==true)
			{
				listAll = parseCompanyCarStyleVOTestDriveList(listAllData,startTime,endTime);
			}
			else{
				exeSql.append(" limit "+(transMsg.getStartIndex()==null?0:transMsg.getStartIndex())+","+ transMsg.getPageSize()+"");
				//取出供某一页显示的list
				List list = reportDao.queryByYear(exeSql.toString());
				result =  parseCompanyCarStyleVOTestDriveList(list,startTime,endTime);
			}			 
			rd.put("listAll",listAll);
			rd.put("result",result);
		return rd;
	} 
	
	public List<String> listCarStyleTestDriveNum(String companyIds,String startTime,String endTime,Boolean isAllChina) {
		//是否全国

		ResponseData rd = new ResponseData(0);
		StringBuffer exeSql=new StringBuffer("");
		//取出全部的list
		List listAllData= new ArrayList();
		
		exeSql.append("select v.CAR_STYLE_ID, count(r.VEHICLE_ID) from  "+
			" t_test_drive r , t_vehicle v 	where v.ID = r.VEHICLE_ID and v.CONFIGURE_STATUS =30 ");
		if(isAllChina ==false){
			exeSql.append(" and  r.COMPANY_ID in ("+companyIds+") ");
		}
		if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime))
		{
			exeSql.append(" and r.START_TIME between date('"+ startTime
					+"') and  date('"+  endTime
					+"')"
					);
		}
		exeSql.append(" group by v.CAR_STYLE_ID");
		System.out.print(exeSql.toString());

		//计算总个数
		listAllData = reportDao.queryByYear(exeSql.toString());
		
		List<String> resultData =new ArrayList();
		if(listAllData!=null)
		{
			Iterator iterator=listAllData.iterator();
			while(iterator.hasNext()){
				Object[] obj=(Object[]) iterator.next();
				resultData.add( TypeConverter.toInteger(obj[0])+"_"+TypeConverter.toInteger(obj[1]));
			}
		}
	
		return resultData;
	} 
	/*
	 * 获得某个companyId下面的某时间段的有望客户数
	 */
	public List<String> listCarStyleHopefulCustomerNum(String companyIds,String startTime,String endTime,
			Boolean isAllChina) {

		StringBuffer exeSql=new StringBuffer("");
		//取出全部的list
		List listAllData;
		
		exeSql.append("select r.CAR_STYLE_ID, sum(r.number) from  "+
			" t_hopeful_custumer r where 1=1");
		if(isAllChina ==false){
			exeSql.append("  and r.COMPANY_ID in ("+companyIds+") ");
		}
		if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime))
			{
				exeSql.append(" and r.RECORD_DATE between date(\""+ startTime.substring(0,10)
						+"\") and  date(\""+  endTime.substring(0,10)
						+"\")"
						);
			}
			exeSql.append(" group by r.CAR_STYLE_ID");
			System.out.print(exeSql);

//			//计算总个数
			listAllData = reportDao.queryByYear(exeSql.toString());
			
			List<String> resultData =new ArrayList();
			if(listAllData!=null)
			{
				Iterator iterator=listAllData.iterator();
				while(iterator.hasNext()){
					Object[] obj=(Object[]) iterator.next();
					resultData.add( TypeConverter.toInteger(obj[0])+"_"+TypeConverter.toInteger(obj[1]));
				}
			}
	
		return resultData;
	} 
	public List parseCompanyCarStyleVOTestDriveList(List list ,String startTime,String endTime) {
		List<CompanyCarStyleVO> vioList= new ArrayList<CompanyCarStyleVO> ();
		CompanyCarStyleVO tmp =new CompanyCarStyleVO();
		
		HashMap parameters0 = new HashMap();
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		//数组车型list      			1     2    3   4   5   6    carStyleList
		//得到经销商实际车型			1     			   5      				relationList
		//车型数量下面试驾次数             5    		       6    
		//给tmp对象赋值数组   		    1_5                5_6           
		//  输出给界面的 数组 		5      0    0    0  6   0       供jsp页面显示 车型具体数量信息    carStyleIdListForjsp
//		List carStyleIdListForjsp=new ArrayList(12);
		List  relationList;
		List customerList ;
		 List<Integer> carStyleIdCustomerList ;
//		 List<String> carStyleIdDrivePercentList =new ArrayList<String>();
		 List<Integer>   carStyleIdTestDriveNumList ;
		 
		 List<String> carStyleIdDrivePercentList=new ArrayList<String>();
		if(list!=null)
		{
			Iterator iterator=list.iterator();
			while(iterator.hasNext()){
				Object[] obj=(Object[]) iterator.next();
				tmp.setIsKeyCity( TypeConverter.toInteger(obj[0]));
				tmp.setDealerType(TypeConverter.toInteger(obj[1]));
				tmp.setDealerCode(TypeConverter.toString(obj[2]));
				tmp.setFenxiao_center(TypeConverter.toString(obj[3]));
				tmp.setRegionProvinceId(TypeConverter.toInteger(obj[4]));
				tmp.setRegionCityId(TypeConverter.toInteger(obj[5]));
				tmp.setName(TypeConverter.toString(obj[6]));
//				tmp.setTestDriveNum(TypeConverter.toInteger(obj[7]));
				tmp.setCompanyId(TypeConverter.toInteger(obj[7]));
				//查询carCompanyCarStyleRelation实体 一个经销商下面的车型
				
				//获得有望客户数
				 customerList = listCarStyleHopefulCustomerNum( tmp.getCompanyId().toString(), startTime, endTime,false);
				 
				 //获得试驾次数
				 relationList = listCarStyleTestDriveNum( tmp.getCompanyId().toString(), startTime, endTime,false);
				 
				 //得到和carStyelist一个顺序的其他属性值
				 carStyleIdTestDriveNumList = getOrderList( relationList ,carStyleList);
				 carStyleIdCustomerList =  getOrderList( customerList ,carStyleList); 
				
				 List<String> carStyleIdListForjsp=new ArrayList<String>();
				for (int i = 0; i < carStyleIdTestDriveNumList.size(); i++) {
					carStyleIdDrivePercentList
							.add(
									carStyleIdCustomerList.get(i) != 0 ? String.format("%.1f", ((float)carStyleIdTestDriveNumList
											.get(i)	/  (float)carStyleIdCustomerList.get(i))*100)+"%"
											: "0.0%");
				}
				Integer allnum=0; //一个经销商下面的一个时间段内所有有效客户数
				Integer allTestDrivenum=0; //一个经销商下面的一个时间段内所有试驾次数
				for(int j =0;j<carStyleIdTestDriveNumList.size();j++){
					carStyleIdListForjsp.add(carStyleIdTestDriveNumList.get(j).toString());
					allTestDrivenum += carStyleIdTestDriveNumList.get(j);
					carStyleIdListForjsp.add(carStyleIdCustomerList.get(j).toString());
					allnum+=carStyleIdCustomerList.get(j);
					carStyleIdListForjsp.add(carStyleIdDrivePercentList.get(j));
				}
				
				tmp.setHopefulCustomer(allnum);
				tmp.setTestDriveNum(allTestDrivenum);
				String percent =(allnum != 0 ? String.format("%.1f", ((float)allTestDrivenum/(float)allnum)*100)+"%"
						: "0.0%");
				tmp.setDrivePercent(percent);
				tmp.setCarStyleList(carStyleIdListForjsp); 
				//tmp.setFenxiao_center_nick(CacheCompanyManager.getCompany(tmp.getCompanyId()).getParentName());
				if(CacheCompanyManager.getCompany(tmp.getCompanyId())!=null){
					tmp.setFenxiao_center_nick(CacheCompanyManager.getCompany(tmp.getCompanyId()).getParentName());
				}
				tmp.nick();
				vioList.add(tmp);
				//清空数据
				tmp = new CompanyCarStyleVO();
				carStyleIdListForjsp=null;
//				carStyleIdDrivePercentList=null;
			}
			return vioList;
			
		}
		return null;
	}

	/*
	 * 试驾率报表 部分代码 end   
	 */
	
	/*
	 *  根据carStyleList的元素顺序 得到一个对应的其每一个style其他的值 比如有效客户数 或试驾次数等
	 */
	private List<Integer> getOrderList(List relationList ,List<CarStyle> carStyleList){
		 Boolean isExist ;
		 List<Integer> carStyleIdListForjsp=new ArrayList();
		 String carStyleId;
		 String carStyleNum;
		
		for(int j=0 ;j<carStyleList.size() ;j++)
		{
			isExist=false;
			 carStyleId="";
			 carStyleNum="0";
			for(int i=0;i<relationList.size();i++)
			{
				carStyleId= relationList.get(i).toString().substring(0,relationList.get(i).toString().indexOf("_"));
				carStyleNum =relationList.get(i).toString().substring(relationList.get(i).toString().indexOf("_")+1);
				if(carStyleId.equals(carStyleList.get(j).getId().toString()))
				{
					carStyleIdListForjsp.add(TypeConverter.toInteger(carStyleNum));
					isExist= true;
				}
			}
			if(isExist==false)
			{
				carStyleIdListForjsp.add(0);
			}
		}
		return carStyleIdListForjsp;
	}
	
	/*
	 * 申请明细报表 部分代码 start 
	 */
	public ResponseData listProposalInfo(TransMsg transMsg,
			CompanyProposalVO companyProposalVO, Integer companyId){
		
		ResponseData rd = new ResponseData(0);
		transMsg.setPageSize(10);
		StringBuffer exeSql=new StringBuffer("");
		//取出全部的list
		List listAllData;
		
		//如果填网络代码 会可能查询得到一个经销商list:companyIdList
		List companyIdList =new ArrayList() ;
		String compangIdSend ="";
		if(companyProposalVO.getId()==null)
		{
			
		}else{
			 companyIdList =companyDao.getByDealerCodeList(companyProposalVO.getId());
			 if(companyIdList==null)
				{
					rd.setMessage("没有该经销商!");
					return rd;
				} else	{
					compangIdSend=  TypeConverter.join(companyIdList, ",") ;
				}
		}
		
		exeSql.append("select c.IS_KEY_CITY isKeyCity, c.DEALER_TYPE dealerType, c.DEALER_CODE dealerCode,"
				+" m.PARENT_ID fenxiaocenter,c.REGION_PROVINCE_ID regionProvinceId,c.REGION_CITY_ID regionCityId, "
				+" c.CN_NAME, c.ORG_ID, r.PLATE_NUMBER, r.CAR_STYLE_ID,r.vin, d.PROPOSAL_TIME,d.status "
				+" from t_company c  ,	 t_vehicle r , t_org m ,t_proposal d where c.ORG_ID  =r.COMPANY_ID and  "
				+"   m.ID = c.ORG_ID and r.id=d.VEHICLE_ID  "  );

			if(StringUtils.isNotEmpty(compangIdSend)){
				   exeSql.append( " and  c.ORG_ID in(" +  compangIdSend + ")");
	
			}else{
				if (companyId != null) {
					exeSql.append( " and  c.ORG_ID in(" +  CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")");
					}
			}
			//时间段查询
			String startTime= "";
			String endTime= "";
			
			if(companyProposalVO.getType()!=null && companyProposalVO.getType()!=-1)
			{
				exeSql.append(" and  d.type = "+companyProposalVO.getType()+" ");
			}
			
			if(companyProposalVO.getStatus()!=null&&companyProposalVO.getStatus()!=-1)
			{
				exeSql.append(" and  d.STATUS = "+companyProposalVO.getStatus()+" ");
			}
			
			if(companyProposalVO.getStartTime()!=null||companyProposalVO.getEndTime()!=null)
			{
				startTime = DateTime.toNormalDateTime(companyProposalVO.getStartTime());
				endTime = DateTime.toNormalDateTime(companyProposalVO.getEndTime());
				exeSql.append(" and d.PROPOSAL_TIME between date('"+ startTime
						+"') and  date('"+  endTime
						+"')"
						);
			}
			
			if(StringUtils.isNotEmpty(companyProposalVO.getVin()))
			{
				exeSql.append(" and  r.vin like \"%"+companyProposalVO.getVin()+"%\" ");
			}
			
			if (companyProposalVO.getCarStyleId() != null) {
				exeSql.append(" and  r.CAR_STYLE_ID = "+companyProposalVO.getCarStyleId()+" ");
			}
			if(StringUtils.isNotEmpty(companyProposalVO.getPlateNumber()))
			{
				exeSql.append(" and  r.PLATE_NUMBER like \"%"+companyProposalVO.getPlateNumber()+"%\" ");
			}
			 exeSql.append(" order by r.id");

//			//计算总个数
			listAllData = reportDao.queryByYear(exeSql.toString());
			
			List<CompanyProposalVO> listAll = parseProposalList(listAllData);
			rd.put("listAll",listAll);
			
			transMsg.setTotalRecords(TypeConverter.toLong(listAllData.size()));
			exeSql.append(" limit "+(transMsg.getStartIndex()==null?0:transMsg.getStartIndex())+","+ transMsg.getPageSize()+"");
			System.out.println(exeSql);
			
//		//取出供某一页显示的list
		List list = reportDao.queryByYear(exeSql.toString());
		List<CompanyProposalVO> result = parseProposalList(list);
		
		rd.put("result",result);
		return rd;

		
	}
		public List parseProposalList(List list) {
		
		List<CompanyProposalVO> vioList= new ArrayList<CompanyProposalVO> ();
		CompanyProposalVO tmp =new CompanyProposalVO();
		if(list!=null)
		{
			Iterator iterator=list.iterator();
			while(iterator.hasNext()){
				Object[] obj=(Object[]) iterator.next();
				tmp.setIsKeyCity( TypeConverter.toInteger(obj[0]));
				tmp.setDealerType(TypeConverter.toInteger(obj[1]));
				tmp.setDealerCode(TypeConverter.toString(obj[2]));
				tmp.setFenxiao_center(TypeConverter.toString(obj[3]));
				tmp.setRegionProvinceId(TypeConverter.toInteger(obj[4]));
				tmp.setRegionCityId(TypeConverter.toInteger(obj[5]));
				tmp.setName(TypeConverter.toString(obj[6]));
				tmp.setId(TypeConverter.toString(obj[7]));
				tmp.setPlateNumber(TypeConverter.toString(obj[8]));
				tmp.setCarStyleId(TypeConverter.toInteger(obj[9]));
				tmp.setVin(TypeConverter.toString(obj[10]));
				
				tmp.setProposalTime(DateTime.toDate(TypeConverter.toString(obj[11]))); //TODO
				tmp.setStatus(TypeConverter.toInteger(obj[12]));
				Org org = orgDao.get(TypeConverter.toInteger(tmp.getFenxiao_center()));
				if(org!=null)
				{
				tmp.setFenxiao_center_nick(org.getName());
				}
//				tmp.setFenxiao_center_nick(orgDao.get(Integer.parseInt(tmp.getFenxiao_center())).getName());
	
				tmp.nick();
				vioList.add(tmp);
				//清空数据
				tmp = new CompanyProposalVO();
			}
			return vioList;
		}
		return null;
	}
	/*
	 * 申请明细报表 部分代码 end
	 */


	/*
	 * 报备明细报表 部分代码 start 
	 */
	public ResponseData listFillingProposalInfo(TransMsg transMsg,
			CompanyFilingProposalVO companyFilingProposalVO, Integer companyId){
		ResponseData rd = new ResponseData(0);
		transMsg.setPageSize(10);
		StringBuffer exeSql=new StringBuffer("");
		//取出全部的list
		List listAllData;
		
		//如果填网络代码 会可能查询得到一个经销商list:companyIdList
		List companyIdList =new ArrayList() ;
		String compangIdSend ="";
		if(companyFilingProposalVO.getId()==null)
		{
			
		}else{
			 companyIdList =companyDao.getByDealerCodeList(companyFilingProposalVO.getId());
			 if(companyIdList==null)
				{
					rd.setMessage("没有该经销商!");
					return rd;
				} else	{
					compangIdSend=  TypeConverter.join(companyIdList, ",") ;
				}
		}
		
		exeSql.append("select c.IS_KEY_CITY isKeyCity, c.DEALER_TYPE dealerType, c.DEALER_CODE dealerCode,"
				+" m.PARENT_ID fenxiaocenter,c.REGION_PROVINCE_ID regionProvinceId,c.REGION_CITY_ID regionCityId, "
				+" c.CN_NAME, c.ORG_ID, r.PLATE_NUMBER, r.CAR_STYLE_ID,r.vin, d.START_TIME,d.end_time "
				+" from t_company c  ,	 t_vehicle r , t_org m ,t_filing_proposal d where c.ORG_ID  =r.COMPANY_ID and  "
				+"   m.ID = c.ORG_ID and r.id=d.VEHICLE_ID  "  );
		
			if(StringUtils.isNotEmpty(compangIdSend)){
				   exeSql.append( " and  c.ORG_ID in(" +  compangIdSend + ")");
	
			}else{
				if (companyId != null) {
					exeSql.append( " and  c.ORG_ID in(" +  CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")");
					}
			}

			//时间段查询
			String startTime= "";
			String endTime= "";
			
			if(companyFilingProposalVO.getType()!=null)
			{
				exeSql.append(" and  d.type = "+companyFilingProposalVO.getType()+" ");
			}
			
			if(companyFilingProposalVO.getStartTime()!=null||companyFilingProposalVO.getEndTime()!=null)
			{
				startTime = DateTime.toNormalDateTime(companyFilingProposalVO.getStartTime());
				endTime = DateTime.toNormalDateTime(companyFilingProposalVO.getEndTime());
				
				exeSql.append(" and d.START_TIME between date('"+ startTime
						+"') and  date('"+  endTime
						+"')"
						);
			}
			
			if(StringUtils.isNotEmpty(companyFilingProposalVO.getVin()))
			{
				exeSql.append(" and  r.vin like \"%"+companyFilingProposalVO.getVin()+"%\" ");
			}
			
			if (companyFilingProposalVO.getCarStyleId() != null) {
				exeSql.append(" and  r.CAR_STYLE_ID = "+companyFilingProposalVO.getCarStyleId()+" ");
			}
			if(StringUtils.isNotEmpty(companyFilingProposalVO.getPlateNumber()))
			{
				exeSql.append(" and  r.PLATE_NUMBER like \"%"+companyFilingProposalVO.getPlateNumber()+"%\" ");
			}
			 exeSql.append(" order by r.id");

//			//计算总个数
			listAllData = reportDao.queryByYear(exeSql.toString());
			
			List<CompanyFilingProposalVO> listAll = parseFillingProposalList(listAllData);
			rd.put("listAll",listAll);
			
			transMsg.setTotalRecords(TypeConverter.toLong(listAllData.size()));
			exeSql.append(" limit "+(transMsg.getStartIndex()==null?0:transMsg.getStartIndex())+","+ transMsg.getPageSize()+"");
			System.out.println(exeSql);
			
//		//取出供某一页显示的list
		List list = reportDao.queryByYear(exeSql.toString());
		List<CompanyFilingProposalVO> result = parseFillingProposalList(list);
		
		rd.put("result",result);
		return rd;

		
	}
		public List parseFillingProposalList(List list) {
		
		List<CompanyFilingProposalVO> vioList= new ArrayList<CompanyFilingProposalVO> ();
		CompanyFilingProposalVO tmp =new CompanyFilingProposalVO();
		
		if(list!=null)
		{
			Iterator iterator=list.iterator();
			while(iterator.hasNext()){
				Object[] obj=(Object[]) iterator.next();
				tmp.setIsKeyCity( TypeConverter.toInteger(obj[0]));
				tmp.setDealerType(TypeConverter.toInteger(obj[1]));
				tmp.setDealerCode(TypeConverter.toString(obj[2]));
				tmp.setFenxiao_center(TypeConverter.toString(obj[3]));
				tmp.setRegionProvinceId(TypeConverter.toInteger(obj[4]));
				tmp.setRegionCityId(TypeConverter.toInteger(obj[5]));
				tmp.setName(TypeConverter.toString(obj[6]));
				tmp.setId(TypeConverter.toString(obj[7]));
				tmp.setPlateNumber(TypeConverter.toString(obj[8]));
				tmp.setCarStyleId(TypeConverter.toInteger(obj[9]));
				tmp.setVin(TypeConverter.toString(obj[10]));
				
				tmp.setStart_Time(DateTime.toDate(TypeConverter.toString(obj[11]))); //TODO
				tmp.setEnd_Time(DateTime.toDate(TypeConverter.toString(obj[12])));
				Org org = orgDao.get(TypeConverter.toInteger(tmp.getFenxiao_center()));
				if(org!=null)
				{
				tmp.setFenxiao_center_nick(org.getName());
				}
				tmp.nick();
				vioList.add(tmp);
				//清空数据
				tmp = new CompanyFilingProposalVO();
			}
			return vioList;
		}
		return null;
	}
	
		
		
	/*
	 * 报备明细报表 部分代码 end
	 */
	
	/*
	 * 试驾明细报表 部分代码 start 
	 */
	public ResponseData listTestDriveInfo(TransMsg transMsg,
			TestDriveInfoVO testDriveInfoVO, Integer companyId,boolean isExport){
		ResponseData rd = new ResponseData(0);
		transMsg.setPageSize(10);
		StringBuffer exeSql=new StringBuffer("");
		//取出全部的list
		List listAllData; 
		
		//如果填网络代码 会可能查询得到一个经销商list:companyIdList
		List companyIdList =new ArrayList() ;
		String compangIdSend ="";
		if(testDriveInfoVO.getId()==null)
		{
			
		}else{
			 companyIdList =companyDao.getByDealerCodeList(testDriveInfoVO.getId());
			 if(companyIdList==null)
				{
					rd.setMessage("没有该经销商!");
					return rd;
				} else	{
					compangIdSend=  TypeConverter.join(companyIdList, ",") ;
				}
		}
		
		exeSql.append("select c.IS_KEY_CITY isKeyCity, c.DEALER_TYPE dealerType, c.DEALER_CODE dealerCode,"
			 +" c.ORG_ID fenxiaocenter,c.REGION_PROVINCE_ID regionProvinceId,c.REGION_CITY_ID regionCityId,"
			 +" c.CN_NAME, c.ORG_ID,r.PLATE_NUMBER, r.CAR_STYLE_ID,r.vin, d.LINE_ID,d.LOOP_COUNT quanshu,"
			 +" d.MILEAGE licheng ,d.START_TIME,d.end_time,d.INTERVAL chixushijian  from t_company c  ,t_vehicle r ,"
			 +" t_test_drive d where c.ORG_ID  =r.COMPANY_ID and r.id=d.VEHICLE_ID "  );
		
			if(StringUtils.isNotEmpty(compangIdSend)){
				   exeSql.append( " and  c.ORG_ID in(" +  compangIdSend + ")");
	
			}else{
				if (companyId != null) {
					exeSql.append( " and  c.ORG_ID in(" +  CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")");
				}
			}
			
			//时间段查询
			String startTime= "";
			String endTime= "";
			
			if(testDriveInfoVO.getStartTime()!=null||testDriveInfoVO.getEndTime()!=null)
			{
				startTime = DateTime.toNormalDateTime(testDriveInfoVO.getStartTime());
				endTime = DateTime.toNormalDateTime(testDriveInfoVO.getEndTime());
				
				exeSql.append(" and d.START_TIME between date('"+ startTime
						+"') and  date('"+  endTime
						+"')"
						);
			}
			
			if(StringUtils.isNotEmpty(testDriveInfoVO.getVin()))
			{
				exeSql.append(" and  r.vin like \"%"+testDriveInfoVO.getVin()+"%\" ");
			}
			
			if (testDriveInfoVO.getCarStyleId() != null) {
				exeSql.append(" and  r.CAR_STYLE_ID = "+testDriveInfoVO.getCarStyleId()+" ");
			}
			
			if(StringUtils.isNotEmpty(testDriveInfoVO.getPlateNumber()))
			{
				exeSql.append(" and  r.PLATE_NUMBER like \"%"+testDriveInfoVO.getPlateNumber()+"%\" ");
			}

		//计算总个数
		System.out.print(exeSql);
		
		List<TestDriveInfoVO> listAll = new ArrayList<TestDriveInfoVO>();
		List<TestDriveInfoVO> result =new ArrayList<TestDriveInfoVO>();
		//计算总个数violationDao
		listAllData = reportDao.queryByYear(exeSql.toString());
		transMsg.setTotalRecords(TypeConverter.toLong(listAllData.size()));
		if(isExport==true)
		{
			listAll = parseTestDriveVOList(listAllData);
		}
		else{
			exeSql.append(" limit "+(transMsg.getStartIndex()==null?0:transMsg.getStartIndex())+","+ transMsg.getPageSize()+"");
			//取出供某一页显示的list
			List list = reportDao.queryByYear(exeSql.toString());
			result = parseTestDriveVOList(list);
		}			 
		rd.put("listAll",listAll);
		rd.put("result",result);
		
		return rd;

		
	}
		public List parseTestDriveVOList(List list) {
		
		List<TestDriveInfoVO> vioList= new ArrayList<TestDriveInfoVO> ();
		TestDriveInfoVO tmp =new TestDriveInfoVO();
		
		
		if(list!=null)
		{
			Iterator iterator=list.iterator();
			while(iterator.hasNext()){
				Object[] obj=(Object[]) iterator.next();
				tmp.setIsKeyCity( TypeConverter.toInteger(obj[0]));
				tmp.setDealerType(TypeConverter.toInteger(obj[1]));
				tmp.setDealerCode(TypeConverter.toString(obj[2]));
				tmp.setCompanyId(TypeConverter.toInteger(obj[3]));
				tmp.setRegionProvinceId(TypeConverter.toInteger(obj[4]));
				tmp.setRegionCityId(TypeConverter.toInteger(obj[5]));
				tmp.setName(TypeConverter.toString(obj[6]));
				tmp.setId(TypeConverter.toString(obj[7]));
				tmp.setPlateNumber(TypeConverter.toString(obj[8]));
				tmp.setCarStyleId(TypeConverter.toInteger(obj[9]));
				tmp.setVin(TypeConverter.toString(obj[10]));
				tmp.setLineId(TypeConverter.toInteger(obj[11]));
				tmp.setLoopCount(TypeConverter.toFloat(obj[12]));
				tmp.setMILEAGE(TypeConverter.toFloat(obj[13]));
				tmp.setStart_Time(DateTime.toDate(TypeConverter.toString(obj[14]))); //TODO
				tmp.setEnd_Time(DateTime.toDate(TypeConverter.toString(obj[15])));
				tmp.setIntevel(TypeConverter.toString(obj[16]));
				
				tmp.setLineId_Nick(CacheDriveLineManager.getDriveLineNameById(tmp.getLineId()));
				if(CacheCompanyManager.getCompany(tmp.getCompanyId())!=null){
					tmp.setFenxiao_center_nick(CacheCompanyManager.getCompany(tmp.getCompanyId()).getParentName());
				}
				String startTime = TypeConverter.toString(obj[14]);
				String endTime = TypeConverter.toString(obj[15]);
				
				tmp.nick();
				vioList.add(tmp);
				//清空数据
				tmp = new TestDriveInfoVO();
			}
			return vioList;
		}
		return null;
	}
	
		
		
	/*
	 * 试驾明细报表 部分代码 end
	 */
	
	/*
	 * 配置报表 部分代码 start 
	 */
		public List<String> listCarStyleConfigureInfoForSK(String companyIds,String startTime,String endTime,
				int searchType, boolean isAllChina) {
			StringBuffer exeSql=new StringBuffer("");
			//取出全部的list 
			List listAllData;

			exeSql.append("select 	r.CAR_STYLE_ID ,count(r.CAR_STYLE_ID) "+
					" from t_company c  ,t_vehicle r  ,t_box_operation b " +
					" where c.ORG_ID  =r.COMPANY_ID and  r.id = b.VEHICLE_ID  and b.type=1   "
					);
			
			if(isAllChina == false){
				exeSql.append(" and  r.COMPANY_ID in ("+companyIds+") ");
			}
			if(searchType == 1){ //已配置
				exeSql.append(" and   r.CONFIGURE_TYPE =1  and  r.CONFIGURE_STATUS =30 ");
			}else if(searchType == 2){//未配置
				exeSql.append(" and   r.CONFIGURE_TYPE =1  and  (r.CONFIGURE_STATUS =10  or r.CONFIGURE_STATUS =20) ");
			}else if(searchType == 0){// RBO 配置配置
				exeSql.append(" and   r.CONFIGURE_TYPE =1 ");
			}
			if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)){
			exeSql.append(" and b.OPERATION_TIME between date('"+ startTime
					+"') and  date('"+  endTime
					+"')"
					);
			}
			exeSql.append(" group by  r.CAR_STYLE_ID");

			//计算总个数violationDao
			listAllData = reportDao.queryByYear(exeSql.toString());
			
			List<String> resultData =new ArrayList();
			if(listAllData!=null)
			{
				Iterator iterator=listAllData.iterator();
				while(iterator.hasNext()){
					Object[] obj=(Object[]) iterator.next();
					resultData.add( TypeConverter.toInteger(obj[0])+"_"+TypeConverter.toInteger(obj[1]));
				}
			}
			return resultData;
			
		} 
		
		
	@Override
	public ResponseData listConfigureInfoForSK(TransMsg transMsg,CompanyCarStyleVO companyCarStyleVO,Integer companyId,boolean isExport) {
		ResponseData rd = new ResponseData(0);
		if(isExport == true) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
		StringBuffer exeSql=new StringBuffer("");
		//取出全部的list
		List listAllData;
		
		String companyIdStr="";
		//如果填网络代码 会可能查询得到一个经销商list:companyIdList
		Collection companyIdList =new ArrayList() ;
		String compangIdSend ="";
		if(companyCarStyleVO.getDealerCode()==null||companyCarStyleVO.getDealerCode().equals(""))
		{
		}else{
			 companyIdList =companyDao.getByDealerCodeList(companyCarStyleVO.getDealerCode());
			 List allList = CacheOrgManager.getChildrenOrgIdsList(companyId);
			 companyIdList = TypeConverter.pksAndpks(companyIdList, allList) ;
			 if(companyIdList==null)
				{
					rd.setMessage("没有该经销商!");
					return rd;
				} else	{
					compangIdSend=  TypeConverter.join(companyIdList, ",") ;
				}
		}
		
		//如果传来网络形态（网络代码）查询就按这个经销商的companyId 否则就按当前登陆者的下属经销商集合
		
		if(StringUtils.isNotEmpty(compangIdSend)){
			companyIdStr += " and  c.ORG_ID in(" +compangIdSend + ")";
		}else{
			if (companyId != null) {
				companyIdStr += " and  c.ORG_ID in(" +  CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")";
			}
		}
		exeSql.append("select c.IS_KEY_CITY isKeyCity, c.DEALER_TYPE dealerType, c.DEALER_CODE dealerCode, "+
				" c.ORG_ID fenxiaocenter,"+
				" c.REGION_PROVINCE_ID regionProvinceId,c.REGION_CITY_ID regionCityId,"+
				" c.CN_NAME ,c.CN_NAME "+ //,count( b.VEHICLE_ID)
				" from t_company c ,t_vehicle r ,t_box_operation b where c.ORG_ID = r.COMPANY_ID" +
				" and  r.id = b.VEHICLE_ID  and b.type=1 "  );
       
		exeSql.append(companyIdStr);
		
		//时间段查询
		String startTime= "";
		String endTime= "";
		
		String season= companyCarStyleVO.getSeason();
		int year= companyCarStyleVO.getYear();
		startTime =String.valueOf(year);
		endTime =String.valueOf(year);
		if (season.equals("1")) {
			startTime +=   "-01-01 00:00:00";
			endTime += "-03-31 23:59:59";
		} else if  (season.equals("2")) {
			startTime +=  "-04-01 00:00:00";
			endTime += "-06-30 23:59:59";
		} else if (season.equals("3")) {
			startTime +=  "-07-01 00:00:00";
			endTime +=  "-09-30 23:59:59";
		} else if (season.equals("4")) {
			startTime +=  "-10-01 00:00:00";
			endTime += "-12-31 23:59:59";
		}else{
			startTime +=  "-01-01 00:00:00";
			endTime += "-12-31 23:59:59";
		}
		exeSql.append(" and b.OPERATION_TIME between date('"+ startTime
				+"') and  date('"+  endTime
				+"')"
				);
		if(StringUtils.isNotEmpty(companyCarStyleVO.getDealerCode()))
		{
			exeSql.append(" and  c.DEALER_CODE like \"%"+companyCarStyleVO.getDealerCode()+"%\" ");
		}
		exeSql.append("group by r.COMPANY_ID");   
		
		exeSql.append(" union select c.IS_KEY_CITY isKeyCity, c.DEALER_TYPE dealerType, c.DEALER_CODE dealerCode, "+
				" c.ORG_ID fenxiaocenter,"+
				" c.REGION_PROVINCE_ID regionProvinceId,c.REGION_CITY_ID regionCityId,"+
				" c.CN_NAME ,c.DEALER_CODE "+ 
				" from t_company c  where  c.ORG_ID not in (select c.ORG_ID from" +
				" t_company c,t_vehicle r ,t_box_operation b where c.ORG_ID = r.COMPANY_ID" +
				" and  r.id = b.VEHICLE_ID  and b.type=1"); 
		exeSql.append(" and b.OPERATION_TIME between date('"+ startTime
				+"') and  date('"+  endTime
				+"')"
				);
		if(StringUtils.isNotEmpty(companyCarStyleVO.getDealerCode()))
		{
			exeSql.append(" and  c.DEALER_CODE like \"%"+companyCarStyleVO.getDealerCode()+"%\" ");
		}
		exeSql.append(")");
		exeSql.append(companyIdStr);// not in  加上 in
		
		System.out.println();
		System.out.println(exeSql.toString());
		System.out.println();
		List<CompanyCarStyleVO> listAll = new ArrayList<CompanyCarStyleVO>();
		List<CompanyCarStyleVO> result =new ArrayList<CompanyCarStyleVO>();
		//计算总个数violationDao
		listAllData = reportDao.queryByYear(exeSql.toString());
		transMsg.setTotalRecords(TypeConverter.toLong(listAllData.size()));
		if(isExport==true)
		{
			listAll =  parseCompanyCarStyleVOListSk(listAllData,startTime,endTime);
		}
		else{
			exeSql.append(" limit "+(transMsg.getStartIndex()==null?0:transMsg.getStartIndex())+","+ transMsg.getPageSize()+"");
			//取出供某一页显示的list
			List list = reportDao.queryByYear(exeSql.toString());
			result = parseCompanyCarStyleVOListSk(list,startTime,endTime);
		}			 
		rd.put("listAll",listAll);
		rd.put("result",result);

		return rd;
		
	} 
	
	/**
	 * 配置报表
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param companyCarStyleVO
	 * @param companyId
	 * @param userId
	 * @param isExport
	 * @return
	 */
	@Override
	public ResponseData listConfigureInfo(TransMsg transMsg, CompanyCarStyleVO companyCarStyleVO, Integer companyId, Integer userId, boolean isExport) {
		ResponseData rd = new ResponseData(1);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) FROM (SELECT 1 ");
		
		//查询经销商信息
		exeSql.append(getReportDealerSql());
		exeSql.append(" r.CAR_STYLE_NAME as carStyleName, SUM(r.REALITY_CONFIG_COUNT) as realityConfigCount ");
		exeSql.append(" FROM t_report_base r ");
		exeCountSql.append(" FROM t_report_base r");
		
		//时间段查询
		String startTime= "", endTime= "";
		String[] days = DateTime.getRangeOfWeek(TypeConverter.toInteger(companyCarStyleVO.getWeek())); 
		Date end  = DateTime.addDays(DateTime.toDate(days[1]),1);
		
		startTime = DateTime.toNormalDate(DateTime.toDate(days[0]));
		endTime = DateTime.toNormalDate(end);
		
		String extractionDateSql = " WHERE r.EXTRACTION_DATE BETWEEN ' " + startTime + " ' " + " AND ' " +  endTime + " ' ";
		exeSql.append(extractionDateSql);
		exeCountSql.append(extractionDateSql);
		String realityConfigCountSql = " AND r.REALITY_CONFIG_COUNT > 0 ";
		exeSql.append(realityConfigCountSql);
		exeCountSql.append(realityConfigCountSql);
		
		//添加权限过滤条件
		exeSql.append(getReportAccessSql(companyId, userId));
		exeCountSql.append(getReportAccessSql(companyId, userId));
		//添加经销商查询条件
		exeSql.append(getReportDealerConditionSql(companyCarStyleVO.getDealer(), userId));
		exeCountSql.append(getReportDealerConditionSql(companyCarStyleVO.getDealer(), userId));
		
		String groupBySql = " GROUP BY r.COMPANY_ID, r.CAR_STYLE_ID ";
		exeSql.append(groupBySql);
		exeCountSql.append(groupBySql).append(" ) t");
		//根据分销中心、网络代码排序
		exeSql.append(getReportOrderSql());
		
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		List<ReportBaseVO> configureInfoList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), ReportBaseVO.class);
		rd.put("configureInfoList", configureInfoList);
		return rd;
		
	} 
		
	public List parseCompanyCarStyleVOListSk(List list,String startTime,String endTime) {
		List<CompanyCarStyleVO> vioList= new ArrayList<CompanyCarStyleVO> ();
		CompanyCarStyleVO tmp =new CompanyCarStyleVO();
		List<Integer> carStyleIdConfigureList = new  ArrayList<Integer> ();
		HashMap parameters0 = new HashMap();
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
	
		List  relationList  ;
		if(list!=null)
		{
			Iterator iterator=list.iterator();
			while(iterator.hasNext()){
				Object[] obj=(Object[]) iterator.next();
				tmp.setIsKeyCity( TypeConverter.toInteger(obj[0]));
				tmp.setDealerType(TypeConverter.toInteger(obj[1]));
				tmp.setDealerCode(TypeConverter.toString(obj[2]));
				tmp.setCompanyId(TypeConverter.toInteger(obj[3]));
				tmp.setRegionProvinceId(TypeConverter.toInteger(obj[4]));
				tmp.setRegionCityId(TypeConverter.toInteger(obj[5]));
				tmp.setName(TypeConverter.toString(obj[6]));
				if(CacheCompanyManager.getCompany(tmp.getCompanyId())!=null)
				{
					tmp.setFenxiao_center_nick(CacheCompanyManager.getCompany(tmp.getCompanyId()).getParentName());
				}
				//查询有值得内容
				if(TypeConverter.toString(obj[7]).equals(TypeConverter.toString(obj[6]))){
					//查询carCompanyCarStyleRelation实体 一个经销商下面的车型
					 relationList = listCarStyleConfigureInfoForSK(tmp.getCompanyId().toString(), startTime, endTime, 1, false);
					 //得到和carStyelist一个顺序的其他属性值
					 carStyleIdConfigureList =  getOrderList( relationList ,carStyleList); 
					tmp.setCarStyleList(carStyleIdConfigureList);
					Integer m=0;
					for( Integer i :carStyleIdConfigureList)
					{
						m+=i;
					}
					tmp.setConfigureNum(m);
				}
				//处理union 后面的查询 ,配置数量值都赋值为空
				else{
					for(int j =0;j<carStyleList.size();j++){
						carStyleIdConfigureList.add(0);
					}
					tmp.setConfigureNum(0);
				}
				
				tmp.nick();
				vioList.add(tmp);
				//清空数据
				tmp = new CompanyCarStyleVO();
				carStyleIdConfigureList=null;
			}
			return vioList;
			
		}
		return null;
	}	
	
	
	
	/*
	 * 配置报表 部分代码 end
	 */
	
	/*
	 * 违规报表 部分代码 start 
	 */
	
	@Override
	public ResponseData listViolationInfo(TransMsg transMsg,CompanyCarStyleVO companyCarStyleVO,Integer companyId,boolean isExport) {

		ResponseData rd = new ResponseData(0);
		StringBuffer exeSql=new StringBuffer("");
		//取出全部的list
		List listAllData;
		String companyIdStr="";
		//如果填网络代码 会可能查询得到一个经销商list:companyIdList
		Collection companyIdList =new ArrayList() ;
		String compangIdSend ="";
		if(companyCarStyleVO.getDealerCode()==null||companyCarStyleVO.getDealerCode().equals(""))
		{
		}else{
			 companyIdList =companyDao.getByDealerCodeList(companyCarStyleVO.getDealerCode());
			 List allList = CacheOrgManager.getChildrenOrgIdsList(companyId);
			 companyIdList = TypeConverter.pksAndpks(companyIdList, allList) ;
			 if(companyIdList==null)
				{
					rd.setMessage("没有该经销商!");
					return rd;
				} else	{
					compangIdSend=  TypeConverter.join(companyIdList, ",") ;
				}
		}
		
		//如果传来网络形态（网络代码）查询就按这个经销商的companyId 否则就按当前登陆者的下属经销商集合
		if(StringUtils.isNotEmpty(compangIdSend)){
			companyIdStr += " and  r.COMPANY_ID in(" +compangIdSend + ")";
		}else{
			if (companyId != null) {
				companyIdStr += " and  r.COMPANY_ID in(" +  CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")";
			}
		}
		
		  	
		exeSql.append("select c.IS_KEY_CITY isKeyCity, c.DEALER_TYPE dealerType, c.DEALER_CODE dealerCode, "+
			"  c.ORG_ID fenxiaocenter, "+
			"  c.REGION_PROVINCE_ID regionProvinceId,c.REGION_CITY_ID regionCityId,"+
			"	c.CN_NAME,			COUNT(r.COMPANY_ID) num1 , c.ORG_ID "+
			"  from t_company c  ,t_violation r  "+
			"  where c.ORG_ID  =r.COMPANY_ID  "  );
		
		exeSql.append(companyIdStr);  
		
		//时间段查询
		String startTime= "";
		String endTime= "";
		startTime = DateTime.toNormalDateTime(companyCarStyleVO.getStartTime());
		endTime = DateTime.toNormalDateTime(companyCarStyleVO.getEndTime());
		if(companyCarStyleVO.getStartTime()!=null||companyCarStyleVO.getEndTime()!=null)
		{
			exeSql.append(" and r.CREATION_TIME between date('"+ startTime
					+"') and  date('"+  endTime
					+"')"
					);
		}
		if(StringUtils.isNotEmpty(companyCarStyleVO.getName()))
		{
			exeSql.append(" and  c.CN_NAME like \"%"+companyCarStyleVO.getName()+"%\" ");
		}
		if(StringUtils.isNotEmpty(companyCarStyleVO.getDealerCode()))
		{
			exeSql.append(" and  c.DEALER_CODE like \"%"+companyCarStyleVO.getDealerCode()+"%\" ");
		}
		exeSql.append(" group by r.COMPANY_ID ");
			
		System.out.print(exeSql);
		List<CompanyCarStyleVO> listAll = new ArrayList<CompanyCarStyleVO>();
		List<CompanyCarStyleVO> result =new ArrayList<CompanyCarStyleVO>();
		//计算总个数violationDao
		listAllData = reportDao.queryByYear(exeSql.toString());
		transMsg.setTotalRecords(TypeConverter.toLong(listAllData.size()));
		if(isExport == true) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
		if(isExport==true)
		{
			listAll = parseCompanyCarStyleVOViolationList(listAllData);
		}
		else{
			exeSql.append(" limit "+(transMsg.getStartIndex()==null?0:transMsg.getStartIndex())+","+ transMsg.getPageSize()+"");
			//取出供某一页显示的list
			List list = reportDao.queryByYear(exeSql.toString());
			result = parseCompanyCarStyleVOViolationList(list);
		}
		rd.put("listAll",listAll);
		rd.put("result",result);
		return rd;
	} 
	
	public List<String> listCarStyleViolationNum(int companyId) {

		ResponseData rd = new ResponseData(0);
		StringBuffer exeSql=new StringBuffer("");
		//取出全部的list
		List listAllData;
		
		exeSql.append("select v.CAR_STYLE_ID, count(r.VEHICLE_ID) from  "+
			" t_violation r , t_vehicle v 	where v.ID = r.VEHICLE_ID and v.CONFIGURE_STATUS =30");
			
			exeSql.append(" and  r.COMPANY_ID ="+companyId+" ");
			
			exeSql.append(" group by v.CAR_STYLE_ID");
			System.out.print(exeSql);

//			//计算总个数
			listAllData = reportDao.queryByYear(exeSql.toString());
			
			List<String> resultData =new ArrayList();
			if(listAllData!=null)
			{
				Iterator iterator=listAllData.iterator();
				while(iterator.hasNext()){
					Object[] obj=(Object[]) iterator.next();
					resultData.add( TypeConverter.toInteger(obj[0])+"_"+TypeConverter.toInteger(obj[1]));
				}
			}
	
		return resultData;
	} 
	public List parseCompanyCarStyleVOViolationList(List list ) {
		List<CompanyCarStyleVO> vioList= new ArrayList<CompanyCarStyleVO> ();
		CompanyCarStyleVO tmp =new CompanyCarStyleVO();
		
		HashMap parameters0 = new HashMap();
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		//数组车型list      			1     2    3   4   5   6    carStyleList
		//得到经销商实际车型			1     			   5      				relationList
		//车型数量下面违规次数             5    		       6    
		//给tmp对象赋值数组   		    1_5                5_6           
		//  输出给界面的 数组 		5      0    0    0  6   0       供jsp页面显示 车型具体数量信息    carStyleIdListForjsp
//		List carStyleIdListForjsp=new ArrayList(12);
		List  relationList;
		if(list!=null)
		{
			Iterator iterator=list.iterator();
			while(iterator.hasNext()){
				Object[] obj=(Object[]) iterator.next();
				tmp.setIsKeyCity( TypeConverter.toInteger(obj[0]));
				tmp.setDealerType(TypeConverter.toInteger(obj[1]));
				tmp.setDealerCode(TypeConverter.toString(obj[2]));
				tmp.setFenxiao_center(TypeConverter.toString(obj[3]));
				tmp.setRegionProvinceId(TypeConverter.toInteger(obj[4]));
				tmp.setRegionCityId(TypeConverter.toInteger(obj[5]));
				tmp.setName(TypeConverter.toString(obj[6]));
//				tmp.setViolationNum(TypeConverter.toInteger(obj[7]));
				tmp.setCompanyId(TypeConverter.toInteger(obj[8]));
				//查询carCompanyCarStyleRelation实体 一个经销商下面的车型
//				parameters0.put("companyId", tmp.getCompanyId());
//				parameters0.put("years", year);
				
				 relationList = listCarStyleViolationNum( tmp.getCompanyId());
				 Boolean isExist ;
				 List carStyleIdListForjsp=new ArrayList();
				 String carStyleId;
				 String carStyleNum;
				
				for(int j=0 ;j<carStyleList.size() ;j++)
				{
					isExist=false;
					 carStyleId="";
					 carStyleNum="0";
					for(int i=0;i<relationList.size();i++)
					{
						carStyleId= relationList.get(i).toString().substring(0,relationList.get(i).toString().indexOf("_"));
						carStyleNum =relationList.get(i).toString().substring(relationList.get(i).toString().indexOf("_")+1);
						if(carStyleId.equals(carStyleList.get(j).getId().toString()))
						{
							carStyleIdListForjsp.add(carStyleNum);
							isExist= true;
						}
					}
					if(isExist==false)
					{
						carStyleIdListForjsp.add(0);
					}
				}
				tmp.setCarStyleList(carStyleIdListForjsp);
				if(CacheCompanyManager.getCompany(tmp.getCompanyId())!=null){
					tmp.setFenxiao_center_nick(CacheCompanyManager.getCompany(tmp.getCompanyId()).getParentName());
				}
				Integer vioNum = 0;
				for(int i = 0;i<carStyleIdListForjsp.size();i++)
				{
					vioNum+=TypeConverter.toInteger(carStyleIdListForjsp.get(i));
				}
				tmp.setViolationNum(vioNum);
				tmp.nick();
				vioList.add(tmp);
				//清空数据
				tmp = new CompanyCarStyleVO();
				carStyleIdListForjsp=null;
			}
			return vioList;
			
		}
		return null;
	}
	/*
	 * 违规报表 部分代码 end
	 */
	
	/*
	 * "Wed Jan 15 00:00:00 CST 2014"转换成 yyyy-MM-dd HH:mm:ss
	 */
	private String timeChange(String dateStr){
//		String date = "Wed Jan 15 00:00:00 CST 2014";
		String date = dateStr;
		Date dd = new Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(dd));
		return sdf.format(dd);
	}
	
	/*
	 * 将字符串20131220-转换成 2013-12-20 00:00:00
	 */
	public String toDateAllType(String str){
		StringBuilder a = new StringBuilder(str);
		a.insert(4, "-");
		a.insert(7, "-");
		a.insert(10, " ");
		a.insert(13, ":");
		a.insert(16, ":");
		return a.toString().substring(0,13);
	}
}

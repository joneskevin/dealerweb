package com.ava.resource;

import com.ava.baseSystem.service.ICompanyService;
import com.ava.domain.entity.Company;
import com.ava.util.TypeConverter;

public class ModifyOrgUsage {
		
	/**	判断组织空间容量使用率是否正常。容量上限已用户当前组织级别为准	*/
	static public boolean isUsageNoraml(Integer orgId){
		ICompanyService orgService = (ICompanyService) GlobalConfig.getBean("companyService");
		Company org = orgService.getOrgById(orgId);
		if ( org!=null ){
			return true;
		}else{
			return false;
		}
	}
		
	/**	增加相应的组织空间使用量统计值。 size单位:字节	*/
	static public void addUsage(Integer orgId, Integer size){
		Long longSize = TypeConverter.toLong(size);
		addUsage(orgId, longSize);
	}
	/**	增加相应的组织空间使用量统计值。 size单位:字节	*/
	static public void addUsage(Integer orgId, Long size){
		ICompanyService orgService = (ICompanyService) GlobalConfig.getBean("companyService");
		Company org = orgService.getOrgById(orgId);
		if (org!=null){
			if (org.getOrgUsage()==null || org.getOrgUsage().longValue()<=0L){
				org.setOrgUsage(0L);
			}
			org.setOrgUsage(org.getOrgUsage()+size);
			orgService.editOrg(org);
		}
	}
	
	/**	减少相应的组织空间使用量统计值。 size单位:字节	*/
	static public void subUsage(Integer orgId, Integer size){
		Long longSize = TypeConverter.toLong(size);
		subUsage( orgId, longSize);
	}
	/**	减少相应的组织空间使用量统计值。 size单位:字节	*/
	static public void subUsage(Integer orgId, Long size){
		ICompanyService orgService = (ICompanyService) GlobalConfig.getBean("companyService");
		Company org = orgService.getOrgById(orgId);
		if (org!=null){
			if (org.getOrgUsage()==null || org.getOrgUsage().longValue()<=0){
				org.setOrgUsage(0L);
			}
			org.setOrgUsage(org.getOrgUsage()-size);
			orgService.editOrg(org);
		}
	}

}

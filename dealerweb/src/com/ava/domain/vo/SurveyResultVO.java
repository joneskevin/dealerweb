package com.ava.domain.vo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class SurveyResultVO {
	
	private java.lang.Integer id;
	
	private java.lang.Integer companyId;
	
	/** 问卷车型ID */
	private java.lang.Integer carStyleId;
	
	/** 创建时间 */
	private java.util.Date creationTime;
	
	/** 第一组选择方式(1至少选其一、0可选) */
	private java.lang.Integer firstSelectType;
	
	/** 第二组选择方式(1至少选其一、0可选) */
	private java.lang.Integer secondSelectType;
	
	/** 第三组选择方式(1至少选其一、0可选) */
	private java.lang.Integer threeSelectType;
	
	/** 第一组问卷车型*/
	private LinkedHashMap<Integer,String> firstAllCarStyles = new LinkedHashMap<Integer,String>();; 
	
	/** 第一组选择问卷选择车型*/
	private List<Integer> selectFirstCarStyles = new ArrayList<Integer>(); 
	
	/** 第二组问卷车型*/
	private LinkedHashMap<Integer,String> secondAllCarStyles = new LinkedHashMap<Integer,String>();; 
	
	/** 第二组选择问卷选择车型*/
	private List<Integer> selectSecondCarStyles = new ArrayList<Integer>(); 
	
	/** 第三组问卷车型*/
	private LinkedHashMap<Integer,String> threeAllCarStyles = new LinkedHashMap<Integer,String>();; 
	
	/** 第三组选择问卷选择车型*/
	private List<Integer> selectThreeCarStyles = new ArrayList<Integer>(); 
	
	private DealerVO dealer;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
	}

	public java.lang.Integer getCarStyleId() {
		return carStyleId;
	}

	public void setCarStyleId(java.lang.Integer carStyleId) {
		this.carStyleId = carStyleId;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}


	public List<Integer> getSelectFirstCarStyles() {
		return selectFirstCarStyles;
	}

	public void setSelectFirstCarStyles(List<Integer> selectFirstCarStyles) {
		this.selectFirstCarStyles = selectFirstCarStyles;
	}


	public LinkedHashMap<Integer, String> getFirstAllCarStyles() {
		return firstAllCarStyles;
	}

	public void setFirstAllCarStyles(
			LinkedHashMap<Integer, String> firstAllCarStyles) {
		this.firstAllCarStyles = firstAllCarStyles;
	}

	public LinkedHashMap<Integer, String> getSecondAllCarStyles() {
		return secondAllCarStyles;
	}

	public void setSecondAllCarStyles(
			LinkedHashMap<Integer, String> secondAllCarStyles) {
		this.secondAllCarStyles = secondAllCarStyles;
	}

	public List<Integer> getSelectSecondCarStyles() {
		return selectSecondCarStyles;
	}

	public void setSelectSecondCarStyles(List<Integer> selectSecondCarStyles) {
		this.selectSecondCarStyles = selectSecondCarStyles;
	}

	public LinkedHashMap<Integer, String> getThreeAllCarStyles() {
		return threeAllCarStyles;
	}

	public void setThreeAllCarStyles(
			LinkedHashMap<Integer, String> threeAllCarStyles) {
		this.threeAllCarStyles = threeAllCarStyles;
	}

	public List<Integer> getSelectThreeCarStyles() {
		return selectThreeCarStyles;
	}

	public void setSelectThreeCarStyles(List<Integer> selectThreeCarStyles) {
		this.selectThreeCarStyles = selectThreeCarStyles;
	}

	public java.lang.Integer getFirstSelectType() {
		return firstSelectType;
	}

	public void setFirstSelectType(java.lang.Integer firstSelectType) {
		this.firstSelectType = firstSelectType;
	}

	public java.lang.Integer getSecondSelectType() {
		return secondSelectType;
	}

	public void setSecondSelectType(java.lang.Integer secondSelectType) {
		this.secondSelectType = secondSelectType;
	}

	public java.lang.Integer getThreeSelectType() {
		return threeSelectType;
	}

	public void setThreeSelectType(java.lang.Integer threeSelectType) {
		this.threeSelectType = threeSelectType;
	}

	public DealerVO getDealer() {
		return dealer;
	}

	public void setDealer(DealerVO dealer) {
		this.dealer = dealer;
	}
	
	
}

package com.ava.domain.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class SurveyResultStaticVO {
	
	private java.lang.Integer id;
	
	private java.lang.Integer companyId;
	
	/** 问卷类型 */
	private java.lang.Integer type;
	
	/** 选择车型数量 */
	private java.lang.Integer size;
	
	/** 创建时间 */
	private java.util.Date creationTime;
	
	/** 第一组选择方式(1至少选其一、0可选) */
	private java.lang.Integer firstSelectType;
	
	/** 第二组选择方式(1至少选其一、0可选) */
	private java.lang.Integer secondSelectType;
	
	/** 第三组选择方式(1至少选其一、0可选) */
	private java.lang.Integer threeSelectType;
	
	/** 第一组选择问卷选择车型*/
	private List<Integer> selectFirstCarStyles = new ArrayList<Integer>(); 
	
	/** 第二组选择问卷选择车型*/
	private List<Integer> selectSecondCarStyles = new ArrayList<Integer>(); 
	
	/** 第三组选择问卷选择车型*/
	private List<Integer> selectThreeCarStyles = new ArrayList<Integer>(); 
	
	/** 第四组选择问卷选择车型*/
	private List<Integer> selectFourCarStyles = new ArrayList<Integer>(); 
	
	/** 第五组选择问卷选择车型*/
	private List<Integer> selectFiveCarStyles = new ArrayList<Integer>(); 
	
	/** 第六组选择问卷选择车型*/
	private List<Integer> selectSixCarStyles = new ArrayList<Integer>(); 
	
	/** 第七组选择问卷选择车型*/
	private List<Integer> selectSevenCarStyles = new ArrayList<Integer>(); 
	
	/** 第八组选择问卷选择车型*/
	private List<Integer> selectEightCarStyles = new ArrayList<Integer>(); 
	
	/** 第九组选择问卷选择车型*/
	private List<Integer> selectNineCarStyles = new ArrayList<Integer>();
	
	/** 第式组选择问卷选择车型*/
	private List<Integer> selectTenCarStyles = new ArrayList<Integer>();

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

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
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

	public List<Integer> getSelectFirstCarStyles() {
		return selectFirstCarStyles;
	}

	public void setSelectFirstCarStyles(List<Integer> selectFirstCarStyles) {
		this.selectFirstCarStyles = selectFirstCarStyles;
	}

	public List<Integer> getSelectSecondCarStyles() {
		return selectSecondCarStyles;
	}

	public void setSelectSecondCarStyles(List<Integer> selectSecondCarStyles) {
		this.selectSecondCarStyles = selectSecondCarStyles;
	}

	public List<Integer> getSelectThreeCarStyles() {
		return selectThreeCarStyles;
	}

	public void setSelectThreeCarStyles(List<Integer> selectThreeCarStyles) {
		this.selectThreeCarStyles = selectThreeCarStyles;
	}

	public List<Integer> getSelectFourCarStyles() {
		return selectFourCarStyles;
	}

	public void setSelectFourCarStyles(List<Integer> selectFourCarStyles) {
		this.selectFourCarStyles = selectFourCarStyles;
	}

	public List<Integer> getSelectFiveCarStyles() {
		return selectFiveCarStyles;
	}

	public void setSelectFiveCarStyles(List<Integer> selectFiveCarStyles) {
		this.selectFiveCarStyles = selectFiveCarStyles;
	}

	public List<Integer> getSelectSixCarStyles() {
		return selectSixCarStyles;
	}

	public void setSelectSixCarStyles(List<Integer> selectSixCarStyles) {
		this.selectSixCarStyles = selectSixCarStyles;
	}

	public List<Integer> getSelectSevenCarStyles() {
		return selectSevenCarStyles;
	}

	public void setSelectSevenCarStyles(List<Integer> selectSevenCarStyles) {
		this.selectSevenCarStyles = selectSevenCarStyles;
	}

	public List<Integer> getSelectEightCarStyles() {
		return selectEightCarStyles;
	}

	public void setSelectEightCarStyles(List<Integer> selectEightCarStyles) {
		this.selectEightCarStyles = selectEightCarStyles;
	}

	public List<Integer> getSelectNineCarStyles() {
		return selectNineCarStyles;
	}

	public void setSelectNineCarStyles(List<Integer> selectNineCarStyles) {
		this.selectNineCarStyles = selectNineCarStyles;
	}

	public List<Integer> getSelectTenCarStyles() {
		return selectTenCarStyles;
	}

	public void setSelectTenCarStyles(List<Integer> selectTenCarStyles) {
		this.selectTenCarStyles = selectTenCarStyles;
	}

	public java.lang.Integer getSize() {
		return size;
	}

	public void setSize(java.lang.Integer size) {
		this.size = size;
	}

	
	
	
}

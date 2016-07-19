package com.ava.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ava.resource.SelectOptionResource;

@Entity
@Table(name="t_data_dictionary")
public class DataDictionary  implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;

	@Column(name = "CURRENT_MODIFICATOR_ID")
	private java.lang.Integer currentModificatorId;
	
	@Column(name = "CURRENT_MODIFICATION_TIMESTAMP")
	private java.util.Date currentModificationTimestamp;
	
	@Column(name = "SORT_ID")
	private java.lang.Integer sortId;
	
	@Column(name = "LEVEL_COUNT")
	private java.lang.Integer levelCount;
	
	@Column(name = "LEVEL_ID")
	private java.lang.Integer levelId;
	
	@Column(name = "PARENT_ID")
	private java.lang.Integer parentId;
	
	@Column(name = "CHILDREN_NUM")
	private java.lang.Integer childrenNum;
	
	@Column(name = "NAME")
	private java.lang.String name;
	
	@Column(name = "NICK")
	private java.lang.String nick;
	
	@Column(name = "LINKED_VALUE")
	private java.lang.String linkedValue;
	
	@Column(name = "COMMENT")
	private java.lang.String comment;
	
	@Column(name = "ICON_PATH")
	private java.lang.String iconPath;
	
	private java.lang.Integer rank;

	@Column(name = "IS_LEAF")
	private java.lang.Integer isLeaf;
	
	//-----------------------------------------
	@Transient
	private String nodeSortName;
	@Transient
	private String currentModificatorName;
	
	public DataDictionary(){
		
	}
	
	public void nick() {
		String nodeSortName = SelectOptionResource.getOptionTextByValue(SelectOptionResource.dictinaryRootNodeArray, getSortId());
		setNodeSortName(nodeSortName);
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getCurrentModificatorId() {
		return currentModificatorId;
	}

	public void setCurrentModificatorId(java.lang.Integer currentModificatorId) {
		this.currentModificatorId = currentModificatorId;
	}

	public java.util.Date getCurrentModificationTimestamp() {
		return currentModificationTimestamp;
	}

	public void setCurrentModificationTimestamp(
			java.util.Date currentModificationTimestamp) {
		this.currentModificationTimestamp = currentModificationTimestamp;
	}

	public java.lang.Integer getLevelCount() {
		return levelCount;
	}

	public void setLevelCount(java.lang.Integer levelCount) {
		this.levelCount = levelCount;
	}

	public java.lang.Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(java.lang.Integer levelId) {
		this.levelId = levelId;
	}

	public java.lang.Integer getParentId() {
		return parentId;
	}

	public void setParentId(java.lang.Integer parentId) {
		this.parentId = parentId;
	}

	public java.lang.Integer getChildrenNum() {
		return childrenNum;
	}

	public void setChildrenNum(java.lang.Integer childrenNum) {
		this.childrenNum = childrenNum;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getNick() {
		return nick;
	}

	public void setNick(java.lang.String nick) {
		this.nick = nick;
	}

	public java.lang.String getLinkedValue() {
		return linkedValue;
	}

	public void setLinkedValue(java.lang.String linkedValue) {
		this.linkedValue = linkedValue;
	}

	public java.lang.Integer getRank() {
		return rank;
	}

	public void setRank(java.lang.Integer rank) {
		this.rank = rank;
	}

	public String getNodeSortName() {
		return nodeSortName;
	}

	public void setNodeSortName(String nodeSortName) {
		this.nodeSortName = nodeSortName;
	}

	public String getCurrentModificatorName() {
		return currentModificatorName;
	}

	public void setCurrentModificatorName(String currentModificatorName) {
		this.currentModificatorName = currentModificatorName;
	}

	public java.lang.Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(java.lang.Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public java.lang.Integer getSortId() {
		return sortId;
	}

	public void setSortId(java.lang.Integer sortId) {
		this.sortId = sortId;
	}

	public java.lang.String getComment() {
		return comment;
	}

	public void setComment(java.lang.String comment) {
		this.comment = comment;
	}

	public java.lang.String getIconPath() {
		return iconPath;
	}

	public void setIconPath(java.lang.String iconPath) {
		this.iconPath = iconPath;
	}

}
package com.ava.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_menu")
public class Menu {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;

	@Column(name = "NAME")
	private java.lang.String name;
	
	@Column(name = "DESCRIPTION")
	private java.lang.String description;
	
	@Column(name = "LINKS")
	private java.lang.String links;
	
	@Column(name = "RIGHTS_CODE")
	private java.lang.String rightsCode;
	
	@Column(name = "SORT_NO")
	private java.lang.Integer sortNo;
	
	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.String getRightsCode() {
		return rightsCode;
	}

	public void setRightsCode(java.lang.String rightsCode) {
		this.rightsCode = rightsCode;
	}

	public java.lang.String getLinks() {
		return links;
	}

	public void setLinks(java.lang.String links) {
		this.links = links;
	}

	public java.lang.Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(java.lang.Integer sortNo) {
		this.sortNo = sortNo;
	}

	

}

/**
 * 公告 经销商 关联 实体
 * Created on 2015-1-4
 * filename: NoticeDealerRelation.java
 * @author jiangfei
 * @version xxx
 */

package com.ava.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 公告 经销商 关联 实体
 * */
@Entity
@Table(name = "t_notice_dealer_relation")
public class NoticeDealerRelation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
	
	/** 公告ID */
	@Column(name = "NOTICE_ID")
	private java.lang.Integer noticeId;
	
	/** 经销商ID */
	@Column(name = "COMPANY_ID")
	private java.lang.Integer companyId;
	
	/** 公告所属月份 */
	@Column(name = "MONTH")
	private java.lang.Integer month;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(java.lang.Integer noticeId) {
		this.noticeId = noticeId;
	}

	public java.lang.Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
	}

	public java.lang.Integer getMonth() {
		return month;
	}

	public void setMonth(java.lang.Integer month) {
		this.month = month;
	}
	
}

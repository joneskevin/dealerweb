package com.ava.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.test.annotation.NotTransactional;

@Entity
@Table(name = "t_short_message")
public class ShortMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private java.lang.Integer id;

	@Column(name = "FROM_USER_ID")
	private java.lang.Integer fromUserId;

	@Column(name = "FROM_LOGIN_NAME")
	private java.lang.String fromLoginName;

	@Column(name = "TO_USER_ID")
	private java.lang.Integer toUserId;

	@Column(name = "TO_LOGIN_NAME")
	private java.lang.String toLoginName;

	@Column(name = "TITLE")
	private java.lang.String title;

	@Column(name = "CONTENT")
	private java.lang.String content;

	@Column(name = "ISREADED")
	private java.lang.Integer isreaded;

	@Column(name = "CREATION_TIME")
	private java.util.Date creationTime;

	@Column(name = "DELETE_BYFROM_USER")
	private java.lang.Integer deleteByfromUser;

	@Column(name = "DELETE_BYTO_USER")
	private java.lang.Integer deleteBytoUser;

	@Transient
	private int hashCode = Integer.MIN_VALUE;
	
	@Transient
	private String vin; 
	
	@Transient
	private String messageType; //消息类型
	
	@Transient
	private String serialNumber;//设备号
	
	public void init() {
		this.setCreationTime(new java.util.Date());
		this.setDeleteByfromUser(0);
		this.setDeleteBytoUser(0);
		this.setIsreaded(0);
	}

	/** 是系统自动发送邮件，发送用户是admin@kingsh，并且标记为发送者已删除 */
	public void initSystemShortMessage() {
		this.init();
		this.setDeleteByfromUser(1);
		this.setFromUserId(2);
		this.setFromLoginName("admin@kingsh");
	}

	public ShortMessage() {
		;
	}

	public ShortMessage(java.lang.Integer id) {
		this.setId(id);
	}

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="sequence" column="ID"
	 */
	public java.lang.Integer getId() {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param id
	 *            the new ID
	 */
	public void setId(java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":"
						+ this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString() {
		return super.toString();
	}

	public java.lang.String getContent() {
		return content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getFromLoginName() {
		return fromLoginName;
	}

	public void setFromLoginName(java.lang.String fromLoginName) {
		this.fromLoginName = fromLoginName;
	}

	public java.lang.Integer getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(java.lang.Integer fromUserId) {
		this.fromUserId = fromUserId;
	}

	public java.lang.Integer getIsreaded() {
		return isreaded;
	}

	public void setIsreaded(java.lang.Integer isreaded) {
		this.isreaded = isreaded;
	}

	public java.lang.String getToLoginName() {
		return toLoginName;
	}

	public void setToLoginName(java.lang.String toLoginName) {
		this.toLoginName = toLoginName;
	}

	public java.lang.Integer getToUserId() {
		return toUserId;
	}

	public void setToUserId(java.lang.Integer toUserId) {
		this.toUserId = toUserId;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}

	public java.lang.Integer getDeleteByfromUser() {
		return deleteByfromUser;
	}

	public void setDeleteByfromUser(java.lang.Integer deleteByfromUser) {
		this.deleteByfromUser = deleteByfromUser;
	}

	public java.lang.Integer getDeleteBytoUser() {
		return deleteBytoUser;
	}

	public void setDeleteBytoUser(java.lang.Integer deleteBytoUser) {
		this.deleteBytoUser = deleteBytoUser;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	
	
}

package com.ef.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BLOCKED_ADDRESS_COMMENT", schema="JAVA_MYSQL")
public class BlockedAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;  
	
	@Column(name="IP_ADDRESS")
	private String address;
	
	@Column(name="HTTP_STATUS")
	private int httpStatus;
	
	@Column(name="COMMENT")
	private String comment;
	
	public BlockedAddress(String address, int httpStatus, String comment) {
		setAddress(address);
		setHttpStatus(httpStatus);
		setComment(comment);
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
}

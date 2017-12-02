package com.ef.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LOG_ENTRY", schema="JAVA_MYSQL")
public class LogEntry {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;  
	
	@Column(name="ACCESS_DATE_TIME")
	private Timestamp accessDateTime;

	@Column(name="IP_ADDRESS")
	private String address;
	
	@Column(name="HTTP_METHOD")
	private String httpMethod;
	
	@Column(name="HTTP_STATUS")
	private int httpStatus;
	
	@Column(name="CLIENT_IDENTIFIER")
	private String client_identifier;
	
	public LogEntry(Timestamp accessDateTime, String address, String httpMethod, int httpStatus, String clientIdentifier) {
		setAccessDateTime(accessDateTime);
		setAddress(address);
		setHttpMethod(httpMethod);
		setHttpStatus(httpStatus);
		setClient_identifier(clientIdentifier);
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getHttpMethod() {
		return httpMethod;
	}
	
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Timestamp getAccessDateTime() {
		return accessDateTime;
	}

	public void setAccessDateTime(Timestamp accessDateTime) {
		this.accessDateTime = accessDateTime;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getClient_identifier() {
		return client_identifier;
	}

	public void setClient_identifier(String client_identifier) {
		this.client_identifier = client_identifier;
	}
	
	@Override
	public String toString() {
		return address + "|" + accessDateTime + "|" + httpStatus;
	}
}

/*
 * *******************************************************************************
 * COPYRIGHT
 *               PAX TECHNOLOGY, Inc. PROPRIETARY INFORMATION
 *   This software is supplied under the terms of a license agreement or
 *   nondisclosure agreement with PAX  Technology, Inc. and may not be copied
 *   or disclosed except in accordance with the terms in that agreement.
 *
 *      Copyright (C) 2017 PAX Technology, Inc. All rights reserved.
 * *******************************************************************************
 */
package com.pax.market.api.sdk.java.api.reseller.dto;

import java.io.Serializable;
import java.util.LinkedHashMap;
import com.pax.market.api.sdk.java.api.reseller.ResellerApi.ResellerStatus;

/**
 *
 * @author tanjie
 * @date 2018-07-03
 */
public class ResellerCreateRequest implements Serializable{

	private static final long serialVersionUID = -898044224050582642L;
	
	private String parentResellerName;
	private String email;
	private String name;
	private String country;
	private String contact;
    private String phone;
    private String postcode;
    private String address;
    private String company;
	private Boolean activateWhenCreate = Boolean.FALSE;
	private String status;
	
	

	private LinkedHashMap<String, String> entityAttributeValues;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public LinkedHashMap<String, String> getEntityAttributeValues() {
		return entityAttributeValues;
	}

	public void setEntityAttributeValues(LinkedHashMap<String, String> entityAttributeValues) {
		this.entityAttributeValues = entityAttributeValues;
	}

	
	

	public String getParentResellerName() {
		return parentResellerName;
	}


	public void setParentResellerName(String parentResellerName) {
		this.parentResellerName = parentResellerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getActivateWhenCreate() {
		return activateWhenCreate;
	}

	public void setActivateWhenCreate(Boolean activateWhenCreate) {
		this.activateWhenCreate = activateWhenCreate;
		if(activateWhenCreate) {
			this.status=ResellerStatus.Active.val();
		}
	}

	public String getStatus() {
		return status;
	}

}

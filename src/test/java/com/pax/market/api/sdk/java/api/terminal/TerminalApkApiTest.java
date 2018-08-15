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

package com.pax.market.api.sdk.java.api.terminal;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pax.market.api.sdk.java.api.base.dto.Result;
import com.pax.market.api.sdk.java.api.terminalApk.TerminalApkApi;
import com.pax.market.api.sdk.java.api.terminalApk.dto.CreateTerminalApkRequest;

/**
 * Description
 *
 * @author tanjie
 * @date 2018-06-06
 */
public class TerminalApkApiTest {
	
private static final Logger logger = LoggerFactory.getLogger(TerminalApiTest.class.getSimpleName());
	
	static TerminalApkApi terminalApkApi;
	
	static Long createdTerminalId = 0L;
	
    public static void init(){
    	terminalApkApi = new  TerminalApkApi("http://localhost:8080/p-market-api", "RCA9MDH6YN3WSSGPW6TJ", "PXCIGMB0HKBGKEW5EMFQF4ULJ55R2EORCC29FYAN");
    	
    }
    
    public static void testCreateTerminalApk() {
    	CreateTerminalApkRequest createTerminalApkRequest = new CreateTerminalApkRequest();
//    	createTerminalApkRequest.setTid("6MB7J2QY");
    	createTerminalApkRequest.setSerialNo("0820087293");
    	
    	createTerminalApkRequest.setPackageName("com.ting.mp3.android");
//    	createTerminalApkRequest.setVersion("5.7.3.0");
    	createTerminalApkRequest.setTemplateName("TransIT_Retail.zip|dfe|fefe|fef|fefe|fefe|fef|fefe|eee|feee|feee|fee|fee");
    	Map<String, String> parameters = new HashMap<String, String>();
    	parameters.put("PID.locationCode", "cn_js_sz");
    	parameters.put("PID.showtraffic", "true");
    	createTerminalApkRequest.setParameters(parameters);
    	Result<String> result = terminalApkApi.createTerminalApk(createTerminalApkRequest);
    	System.out.println(result);
    }

    public static void main(String[] args) {
    	TerminalApkApiTest.init();
    	TerminalApkApiTest.testCreateTerminalApk();
    }

}

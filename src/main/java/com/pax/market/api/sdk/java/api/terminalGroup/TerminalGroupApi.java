/*
 * ********************************************************************************
 * COPYRIGHT
 *               PAX TECHNOLOGY, Inc. PROPRIETARY INFORMATION
 *   This software is supplied under the terms of a license agreement or
 *   nondisclosure agreement with PAX  Technology, Inc. and may not be copied
 *   or disclosed except in accordance with the terms in that agreement.
 *
 *      Copyright (C) 2017 PAX Technology, Inc. All rights reserved.
 * ********************************************************************************
 */
package com.pax.market.api.sdk.java.api.terminalGroup;

import com.google.gson.Gson;
import com.pax.market.api.sdk.java.api.BaseThirdPartySysApi;
import com.pax.market.api.sdk.java.api.base.dto.EmptyResponse;
import com.pax.market.api.sdk.java.api.base.dto.PageRequestDTO;
import com.pax.market.api.sdk.java.api.base.dto.Result;
import com.pax.market.api.sdk.java.api.base.request.SdkRequest;
import com.pax.market.api.sdk.java.api.client.ThirdPartySysApiClient;
import com.pax.market.api.sdk.java.api.constant.Constants;
import com.pax.market.api.sdk.java.api.terminal.TerminalApi;
import com.pax.market.api.sdk.java.api.terminalGroup.dto.*;
import com.pax.market.api.sdk.java.api.util.EnhancedJsonUtils;
import com.pax.market.api.sdk.java.api.terminal.TerminalApi.TerminalStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @Description
 * @Author: Shawn
 * @Date: 2019/11/26 9:36
 * @Version 7.1
 */
public class TerminalGroupApi extends BaseThirdPartySysApi {
    private static final Logger logger = LoggerFactory.getLogger(TerminalGroupApi.class);

    private static final String GET_TERMINAL_GROUP_URL = "/v1/3rdsys/terminalGroups/{groupId}";
    private static final String SEARCH_TERMINAL_GROUP_URL = "/v1/3rdsys/terminalGroups";
    private static final String CREATE_TERMINAL_GROUP_URL = "/v1/3rdsys/terminalGroups";
    private static final String SEARCH_TERMINAL_URL = "/v1/3rdsys/terminalGroups/terminal";
    private static final String UPDATE_TERMINAL_GROUP_URL = "/v1/3rdsys/terminalGroups/{groupId}";
    private static final String ACTIVE_TERMINAL_GROUP_URL = "/v1/3rdsys/terminalGroups/{groupId}/active";
    private static final String DISABLE_TERMINAL_GROUP_URL = "/v1/3rdsys/terminalGroups/{groupId}/disable";
    private static final String DELETE_TERMINAL_GROUP_URL = "/v1/3rdsys/terminalGroups/{groupId}";
    private static final String SEARCH_TERMINAL_IN_GROUP_URL = "/v1/3rdsys/terminalGroups/{groupId}/terminals";
    private static final String ADD_TERMINAL_IN_GROUP_URL = "/v1/3rdsys/terminalGroups/{groupId}/terminals";
    private static final String REMOVE_TERMINAL_OUT_GROUP_URL = "/v1/3rdsys/terminalGroups/{groupId}/terminals";



    public TerminalGroupApi(String baseUrl, String apiKey, String apiSecret) {
        super(baseUrl, apiKey, apiSecret);
    }

    public TerminalGroupApi(String baseUrl, String apiKey, String apiSecret, Locale locale) {
        super(baseUrl, apiKey, apiSecret, locale);
    }

    public Result<TerminalGroupDTO> searchTerminalGroup(int pageNo, int pageSize,TerminalGroupSearchOrderBy orderBy ,
                                                        TerminalGroupStatus status, String name,String resellerNames,String modelNames, Boolean isDynamic){

        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        PageRequestDTO page = new PageRequestDTO();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        if (orderBy != null) {
            page.setOrderBy(orderBy.val());
        }
        List<String> validationErrs = validate(page);
        if (validationErrs.size() > 0) {
            return new Result<TerminalGroupDTO>(validationErrs);
        }
        SdkRequest request = getPageRequest(SEARCH_TERMINAL_GROUP_URL, page);

        request.setRequestMethod(SdkRequest.RequestMethod.GET);
        if (status !=null){
            request.addRequestParam("status", status.val());
        }
        if(modelNames!=null) {
            request.addRequestParam("modelNames", modelNames);
        }
        if(resellerNames!=null) {
            request.addRequestParam("resellerNames", resellerNames);
        }
        if(name!=null) {
            request.addRequestParam("name", name);
        }
        if(isDynamic!=null) {
            request.addRequestParam("isDynamic", isDynamic.toString());
        }
        TerminalGroupPageResponse terminalGroupPageResponse = EnhancedJsonUtils.fromJson(client.execute(request), TerminalGroupPageResponse.class);
        Result<TerminalGroupDTO> result = new Result<TerminalGroupDTO>(terminalGroupPageResponse);
        return result;


    }

    public Result<TerminalGroupDTO> getTerminalGroup(Long groupId){
        List<String> validationErrs = validateGroupId(groupId);
        if(!validationErrs.isEmpty()) {
            return new Result<TerminalGroupDTO>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(GET_TERMINAL_GROUP_URL.replace("{groupId}", groupId.toString()+""));
        request.setRequestMethod(SdkRequest.RequestMethod.GET);
        TerminalGroupResponse resp = EnhancedJsonUtils.fromJson(client.execute(request), TerminalGroupResponse.class);
        Result<TerminalGroupDTO> result = new Result<TerminalGroupDTO>(resp);
        return result;
    }

    public Result<TerminalGroupDTO> createTerminalGroup(CreateTerminalGroupRequest createRequest){
        List<String> validationErrs = validateCreate( createRequest,"parameter.terminalGroupCreateRequest.null");
        if(validationErrs.size()>0) {
            return new Result<TerminalGroupDTO>(validationErrs);
        }

        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(CREATE_TERMINAL_GROUP_URL);
        request.setRequestMethod(SdkRequest.RequestMethod.POST);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        request.setRequestBody(new Gson().toJson(createRequest, CreateTerminalGroupRequest.class));
        TerminalGroupResponse terminalGroupResponse = EnhancedJsonUtils.fromJson(client.execute(request), TerminalGroupResponse.class);
        Result<TerminalGroupDTO> result = new Result<TerminalGroupDTO>(terminalGroupResponse);
        return result;
    }

    public  Result<SimpleTerminalDTO> searchTerminal(int pageNo, int pageSize, TerminalApi.TerminalSearchOrderBy orderBy, TerminalStatus status,
                                                     String modelName, String resellerName, String serialNo, String excludeGroupId){

        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        PageRequestDTO page = new PageRequestDTO();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        if (orderBy != null) {
            page.setOrderBy(orderBy.val());
        }
        List<String> validationErrs = validate(page);
        if (validationErrs.size() > 0) {
            return new Result<SimpleTerminalDTO>(validationErrs);
        }
        SdkRequest request = getPageRequest(SEARCH_TERMINAL_URL, page);

        request.setRequestMethod(SdkRequest.RequestMethod.GET);
        if (status !=null){
            request.addRequestParam("status", status.val());
        }
        if(modelName!=null) {
            request.addRequestParam("modelName", modelName);
        }
        if(resellerName!=null) {
            request.addRequestParam("resellerName", resellerName);
        }
        if(serialNo!=null) {
            request.addRequestParam("serialNo", serialNo);
        }
        if(excludeGroupId!=null) {
            request.addRequestParam("excludeGroupId", excludeGroupId);
        }
        SimpleTerminalPageResponse terminalPageResponse = EnhancedJsonUtils.fromJson(client.execute(request), SimpleTerminalPageResponse.class);
        Result<SimpleTerminalDTO> result = new Result<SimpleTerminalDTO>(terminalPageResponse);
        return result;
    }


    public Result<TerminalGroupDTO> updateTerminalGroup(Long groupId ,UpdateTerminalGroupRequest updateRequest){
        List<String> validationErrs = validateUpdate( groupId,updateRequest,"parameter.terminalGroupId.invalid","parameter.terminalGroupUpdateRequest.null");
        if(validationErrs.size()>0) {
            return new Result<TerminalGroupDTO>(validationErrs);
        }

        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());

        SdkRequest request = createSdkRequest(UPDATE_TERMINAL_GROUP_URL.replace("{groupId}", groupId.toString()+""));
        request.setRequestMethod(SdkRequest.RequestMethod.PUT);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        request.setRequestBody(new Gson().toJson(updateRequest, UpdateTerminalGroupRequest.class));
        TerminalGroupResponse terminalGroupResponse = EnhancedJsonUtils.fromJson(client.execute(request), TerminalGroupResponse.class);
        Result<TerminalGroupDTO> result = new Result<TerminalGroupDTO>(terminalGroupResponse);
        return result;
    }

    public Result<String> activeGroup(Long groupId){
        List<String> validationErrs = validateGroupId(groupId);
        if(!validationErrs.isEmpty()) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(ACTIVE_TERMINAL_GROUP_URL.replace("{groupId}", groupId.toString()));
        request.setRequestMethod(SdkRequest.RequestMethod.PUT);
        return emptyResult(client,request);
    }

    public Result<String> disableGroup(Long groupId){
        List<String> validationErrs = validateGroupId(groupId);
        if(!validationErrs.isEmpty()) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(DISABLE_TERMINAL_GROUP_URL.replace("{groupId}", groupId.toString()));
        request.setRequestMethod(SdkRequest.RequestMethod.PUT);
        return emptyResult(client,request);
    }

    public Result<String> deleteGroup(Long groupId){
        List<String> validationErrs = validateGroupId(groupId);
        if(!validationErrs.isEmpty()) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(DELETE_TERMINAL_GROUP_URL.replace("{groupId}", groupId.toString()));
        request.setRequestMethod(SdkRequest.RequestMethod.DELETE);
        return emptyResult(client,request);
    }


    public  Result<SimpleTerminalDTO> searchTerminalsInGroup(int pageNo, int pageSize, TerminalApi.TerminalSearchOrderBy orderBy,
                                                       Long groupId, String serialNo, String merchantNames){

        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        PageRequestDTO page = new PageRequestDTO();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        if (orderBy != null) {
            if(orderBy == TerminalApi.TerminalSearchOrderBy.SerialNo) {
                page.setOrderBy("a.serial_no ASC, a.id ASC");
            }else{
                page.setOrderBy(orderBy.val());
            }

        }
        List<String> validationErrs = validate(page);
        if(groupId == null) {
            validationErrs.add(getMessage("parameter.groupId.null"));
        }
        if (validationErrs.size() > 0) {
            return new Result<SimpleTerminalDTO>(validationErrs);
        }

        SdkRequest request = getPageRequest(SEARCH_TERMINAL_IN_GROUP_URL.replace("{groupId}", groupId.toString()), page);

        request.setRequestMethod(SdkRequest.RequestMethod.GET);
        if (serialNo !=null){
            request.addRequestParam("serialNo", serialNo);
        }
        if(merchantNames!=null) {
            request.addRequestParam("merchantNames", merchantNames);
        }

        SimpleTerminalPageResponse terminalPageResponse = EnhancedJsonUtils.fromJson(client.execute(request), SimpleTerminalPageResponse.class);
        Result<SimpleTerminalDTO> result = new Result<SimpleTerminalDTO>(terminalPageResponse);
        return result;
    }


    public Result<String> addTerminalToGroup(Long groupId, Set<Long> terminalIds){
        List<String> validationErrs = validateGroupId(groupId);
        if(!validationErrs.isEmpty()) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(ADD_TERMINAL_IN_GROUP_URL.replace("{groupId}", groupId.toString()));
        request.setRequestMethod(SdkRequest.RequestMethod.POST);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        request.setRequestBody(new Gson().toJson(terminalIds, Set.class));
        return emptyResult(client,request);
    }

    public Result<String> removeTerminalOutGroup(Long groupId, Set<Long> terminalIds){
        List<String> validationErrs = validateGroupId(groupId);
        if(!validationErrs.isEmpty()) {
            return new Result<>(validationErrs);
        }

        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(REMOVE_TERMINAL_OUT_GROUP_URL.replace("{groupId}", groupId.toString()));
        request.setRequestMethod(SdkRequest.RequestMethod.PUT);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        request.setRequestBody(new Gson().toJson(terminalIds, TerminalGroupRequest.class));
        return emptyResult(client,request);
    }


    private List<String>  validateGroupId(Long groupId){
        logger.debug("groupId="+groupId);
        List<String> validationErrs = validateId(groupId, "parameter.terminalGroupId.invalid");
        return validationErrs;
    }

    private Result<String>  emptyResult(ThirdPartySysApiClient client,SdkRequest request) {
        EmptyResponse emptyResponse =  EnhancedJsonUtils.fromJson(client.execute(request), EmptyResponse.class);
        return  new Result<String>(emptyResponse);
    }

    public enum TerminalGroupSearchOrderBy {
        Name("name"),
        CreatedDate_desc("createdDate DESC"),
        CreatedDate_asc("createdDate ASC");

        private String val;
        private TerminalGroupSearchOrderBy(String orderBy) {
            this.val = orderBy;
        }
        public String val() {
            return this.val;
        }
    }

    public enum TerminalGroupStatus {
        Pending("P"),
        Active("A"),
        Suspend("S");
        private String val;
        private TerminalGroupStatus(String status) {
            this.val = status;
        }
        public String val() {
            return this.val;
        }
    }

}

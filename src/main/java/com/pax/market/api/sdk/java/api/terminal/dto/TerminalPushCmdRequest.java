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

package com.pax.market.api.sdk.java.api.terminal.dto;



import com.pax.market.api.sdk.java.api.terminal.TerminalApi;

import java.io.Serializable;

/**
 * @author shifan
 * @date 2020/10/13
 */
public class TerminalPushCmdRequest implements Serializable{
    private static final long serialVersionUID = -8197381426657587862L;

    private TerminalApi.TerminalPushCmd key;

    public TerminalApi.TerminalPushCmd getKey() {
        return key;
    }

    public void setKey(TerminalApi.TerminalPushCmd key) {
        this.key = key;
    }
}

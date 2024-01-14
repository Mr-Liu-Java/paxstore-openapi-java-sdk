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
package com.pax.market.api.sdk.java.api.util;

import com.google.gson.*;
import com.pax.market.api.sdk.java.api.base.dto.SdkObject;
import com.pax.market.api.sdk.java.api.constant.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * @author tanjie
 * @date 2018-07-04
 */


/**
 * The type Json utils.
 */
public class EnhancedJsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(EnhancedJsonUtils.class);
    private static Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateTypeAdapter()).create();

    /**
     * 根据javaBean生成Json对象格式字符串
     *
     * @param object 任意javaBean类型对象
     * @return 拼接好的String对象 string
     */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * 根据Sdk返回的Json字符串生成Javabean，json字符串封装在data中
     *
     * @param <T>        the type parameter
     * @param sdkJsonStr Json字符串
     * @param clazz      the clazz
     * @return Javabean对象 t
     */
    public static <T> T fromJson(String sdkJsonStr, Class<T> clazz) {
        logger.debug(sdkJsonStr);
        return gson.fromJson(sdkJsonStr, clazz);
    }

    /**
     * From json t.
     *
     * @param <T>        the type parameter
     * @param sdkJsonStr the sdk json str
     * @param typeOfT    the type of t
     * @return the t
     */
    public static <T> T fromJson(String sdkJsonStr, Type typeOfT) {
        logger.debug(sdkJsonStr);
        return gson.fromJson(sdkJsonStr, typeOfT);
    }

    /**
     * Gets sdk json.
     *
     * @param resultCode the result code
     * @return the sdk json
     */
    public static String getSdkJson(int resultCode) {
        String message = MessageBundleUtils.getMessage(resultCode+"");
        return getSdkJson(resultCode, message);
    }

    /**
     * Gets sdk json.
     *
     * @param resultCode the result code
     * @param message    the message
     * @return the sdk json
     */
    public static String getSdkJson(int resultCode, String message) {
        SdkObject sdkObject = new SdkObject();
        sdkObject.setBusinessCode(resultCode);
        sdkObject.setMessage(message);
        return toJson(sdkObject);
    }

    private static class DateTypeAdapter implements JsonDeserializer<Date> {
        private DateFormat format;

        /**
         * Instantiates a new Date type adapter.
         */
        DateTypeAdapter() {
        }

        /**
         * Instantiates a new Date type adapter.
         *
         * @param format the format
         */
        public DateTypeAdapter(DateFormat format) {
            this.format = format;
        }

        public synchronized Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            if (!(json instanceof JsonPrimitive)) {
                throw new JsonParseException("This is not a primitive value");
            }

            String jsonStr = json.getAsString();
            if (format != null) {

                try {
                    return format.parse(jsonStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return new Date(Long.parseLong(jsonStr));
        }
    }
}